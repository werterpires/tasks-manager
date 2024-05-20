package manager.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import manager.dao.TaskDao;
import manager.dao.UserDao;
import manager.entities.Task;
import manager.entities.Task.TaskStatus;
import manager.entities.User;
import manager.session.LoggedIn;

@ManagedBean
@SessionScoped
public class TaskController {

	private List<Task> tasks;

	private List<Task> cratorTasks;

	private List<Task> executorTasks;

	private List<User> subordinates;

	private Task task;

	private String name;

	private String description;
	private TaskStatus status;
	private int categoryId;
	private int creatorUserId;
	private int executerUserId;

	// Getters and Setters

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<Task> getTasks() {
		if (tasks == null) {
			tasks = new ArrayList<>();
		}
		return tasks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getCreatorUserId() {
		return creatorUserId;
	}

	public void setCreatorUserId(int creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	public int getExecuterUserId() {
		return executerUserId;
	}

	public void setExecuterUserId(int executerUserId) {
		this.executerUserId = executerUserId;
	}

	public List<Task> getCratorTasks() {
		return cratorTasks;
	}

	public void setCratorTasks(List<Task> cratorTasks) {
		this.cratorTasks = cratorTasks;
	}

	public List<Task> getExecutorTasks() {
		return executorTasks;
	}

	public void setExecutorTasks(List<Task> executorTasks) {
		this.executorTasks = executorTasks;
	}

	public List<User> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<User> subordinates) {
		this.subordinates = subordinates;
	}

	// CRUD Methods

	public void initializeTasks() {
		try {
			System.out.println("initializeTasks");

			if (!isLogged()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
				return;
			}
			LoggedIn loggedIn = LoggedIn.getInstance();
			UserDao userDao = UserDao.getInstance();

			User user = loggedIn.getUser();
			System.out.println("user:" + user);
			List<User> subordinates = userDao.getSubordinates(user.getLevel());
			System.out.println("Subordinados" + subordinates);
			subordinates.add(user);
			this.subordinates = subordinates;
			int userId = user.getId();
			this.findCreatorTasks(userId);
			this.findExecutorTasks(userId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar inicializar recursos");
		}
	}

	public void findCreatorTasks(int userId) {
		try {

			TaskDao taskDao = TaskDao.getInstance();
			this.cratorTasks = taskDao.getCreatorTasks(userId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar tarefas");
		}
	}

	public void findExecutorTasks(int userId) {
		try {

			TaskDao taskDao = TaskDao.getInstance();
			this.executorTasks = taskDao.getExecutorTasks(userId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar tarefas");
		}
	}

	public String findTaskById(int id) {
		try {
			if (!isLogged()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
				return null;
			}
			TaskDao taskDao = TaskDao.getInstance();
			this.task = taskDao.getTask(id);
			return "task";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar categoria");
		}
	}

	public String addTask() {
		try {
			if (!isLogged()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
				return null;
			}
			if (this.name == null || this.name.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo Nome é obrigatório.", null));
				return null;
			}

			if (this.description == null || this.description.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo Descricão é obrigatório.", null));
				return null;
			}

			if (this.status == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um status.", null));
				return null;
			}

			if (this.categoryId == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione uma categoria.", null));
				return null;
			}

			if (this.executerUserId == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um executantor para a tarefa.", null));
				return null;
			}

			LoggedIn loggedIn = LoggedIn.getInstance();
			User user = loggedIn.getUser();
			int creatorUserId = user.getId();

			String name = this.name;
			String description = this.description;
			TaskStatus status = this.status;
			int categoryId = this.categoryId;
			int executerUserId = this.executerUserId;

			TaskDao taskDao = TaskDao.getInstance();
			taskDao.addTask(name, description, status, categoryId, creatorUserId, executerUserId);
			getTasks();

			this.task = new Task();
			this.name = null;
			this.description = null;
			this.status = null;
			this.categoryId = 0;
			this.creatorUserId = 0;
			this.executerUserId = 0;

			return "tasks?faces-redirect=true";

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao adicionar tarefa: " + e.getMessage(), null));
			return null;
		}
	}

	public String updateTask() {
		try {
			if (!isLogged()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
				return null;
			}
			TaskDao taskDao = TaskDao.getInstance();
			this.task = taskDao.updateTask(this.task);
			return "tasks";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao atualizar categoria");
		}
	}

	public String deleteTask(String id) {
		try {
			if (!isLogged()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
				return null;
			}
			System.out.println("deleting task with id: " + id);
			TaskDao taskDao = TaskDao.getInstance();
			taskDao.deleteTask(Integer.parseInt(id));
			for (Task task : tasks) {
				if (task.getId() == Integer.parseInt(id)) {
					tasks.remove(task);
					break;
				}
			}

			return "tasks?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao deletar tarefa");
		}
	}

	public String selectTask(String id) {
		if (!isLogged()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
			return null;
		}
		int idInt = Integer.parseInt(id);
		findTaskById(idInt);
		return "task";
	}

	public Boolean isLogged() {
		LoggedIn loggedIn = LoggedIn.getInstance();
		return loggedIn.isLogged();
	}

}
