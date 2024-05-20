package manager.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import manager.dao.CategoryDao;
import manager.dao.UserDao;
import manager.entities.Task;
import manager.entities.Task.TaskStatus;
import manager.entities.User;

@ManagedBean
@ApplicationScoped
public class TaskRepository {
	private List<Task> tasks = new ArrayList<>();

	public TaskRepository() {
		tasks.add(new Task(1, "Alimentar gatos", "Pegar a ração do outro lado do muro e colocar na vasilha do gatinho.",
				TaskStatus.TODO, 1, 3, 1));
		tasks.add(new Task(2, "Implementar API de Usuários", "Desenvolver endpoints CRUD para gerenciar usuários.",
				TaskStatus.DOING, 1, 1, 2));
		tasks.add(new Task(3, "Criar Interface de Login", "Desenvolver a tela de login com validação de campos.",
				TaskStatus.TODO, 2, 3, 3));
		tasks.add(new Task(4, "Preparar Reunião de Planejamento",
				"Criar agenda e tópicos para a reunião de planejamento.", TaskStatus.DONE, 3, 4, 1));
		tasks.add(new Task(5, "Gerar Relatório Financeiro", "Compilar dados e gerar relatório financeiro mensal.",
				TaskStatus.DOING, 4, 2, 4));
		tasks.add(new Task(6, "Realizar Pesquisa de Mercado", "Coletar dados de mercado para análise de concorrência.",
				TaskStatus.TODO, 5, 5, 3));
		tasks.add(new Task(7, "Desenvolver Componente de Cadastro",
				"Criar componente frontend para cadastro de novos usuários.", TaskStatus.TODO, 2, 2, 5));
		tasks.add(new Task(8, "Documentar API de Produtos",
				"Escrever documentação para a API de gerenciamento de produtos.", TaskStatus.DONE, 1, 1, 4));
		tasks.add(new Task(9, "Revisar Relatório de Vendas", "Analisar e revisar o relatório de vendas trimestral.",
				TaskStatus.DOING, 4, 3, 2));
		tasks.add(new Task(10, "Planejar Sprint", "Definir metas e tarefas para a próxima sprint.", TaskStatus.TODO, 3,
				4, 5));

	}

	public List<Task> getTasks() {
		return tasks;
	}

	public Task getTask(int id) {
		for (Task task : tasks) {
			if (task.getId() == id) {
				CategoryDao categoryDao = CategoryDao.getInstance();
				UserDao userDao = UserDao.getInstance();
				task.setCategoryName(categoryDao.getCategory(task.getCategoryId()).getName());
				task.setCreatorName(userDao.getUser(task.getCreatorUserId()).getName());
				task.setExecuterName(userDao.getUser(task.getExecuterUserId()).getName());
				task.setCreatorEmail(userDao.getUser(task.getCreatorUserId()).getEmail());
				task.setExecuterEmail(userDao.getUser(task.getExecuterUserId()).getEmail());

				return task;
			}
		}
		return null;
	}

	public List<Task> selectCreatorTasks(int creatorUserId) {
		List<Task> creatorTasks = new ArrayList<>();
		for (Task task : tasks) {
			if (task.getCreatorUserId() == creatorUserId) {
				creatorTasks.add(task);
			}
		}
		CategoryDao categoryDao = CategoryDao.getInstance();
		UserDao userDao = UserDao.getInstance();

		for (Task task : creatorTasks) {

			String categoryName = categoryDao.getCategory(task.getCategoryId()).getName();
			task.setCategoryName(categoryName);

			User creator = userDao.getUser(task.getCreatorUserId());
			task.setCreatorName(creator.getName());
			task.setCreatorEmail(creator.getEmail());

			User executer = userDao.getUser(task.getExecuterUserId());
			task.setExecuterName(executer.getName());
			task.setExecuterEmail(executer.getEmail());

		}

		return creatorTasks;
	}

	public List<Task> selectExecutorTasks(int executerUserId) {
		List<Task> executerTasks = new ArrayList<>();
		for (Task task : tasks) {
			if (task.getExecuterUserId() == executerUserId) {
				executerTasks.add(task);
			}
		}

		CategoryDao categoryDao = CategoryDao.getInstance();
		UserDao userDao = UserDao.getInstance();

		for (Task task : executerTasks) {

			String categoryName = categoryDao.getCategory(task.getCategoryId()).getName();
			task.setCategoryName(categoryName);

			User creator = userDao.getUser(task.getCreatorUserId());
			task.setCreatorName(creator.getName());
			task.setCreatorEmail(creator.getEmail());

			User executer = userDao.getUser(task.getExecuterUserId());
			task.setExecuterName(executer.getName());
			task.setExecuterEmail(executer.getEmail());

		}

		return executerTasks;
	}

	public Task addTask(String name, String description, TaskStatus status, int categoryId, int creatorUserId,
			int executerUserId) {

		int id = incrementId();
		Task task = new Task(id, name, description, status, categoryId, creatorUserId, executerUserId);
		tasks.add(task);
		return task;
	}

	public Task updateTask(Task task) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getId() == task.getId()) {
				tasks.set(i, task);
				return task;
			}
		}
		return null;
	}

	public Task updateStatus(int taskId, TaskStatus status) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getId() == taskId) {
				tasks.get(i).setStatus(status);
				return tasks.get(i);
			}
		}
		return null;
	}

	public void deleteTask(int id) {

		Task task = getTask(id);

		if (task == null) {
			throw new RuntimeException("Task not found");
		}

		tasks.removeIf(categ -> categ.getId() == id);
	}

	private int incrementId() {
		int maxId = getMaxTaskId();
		return maxId + 1;
	}

	private int getMaxTaskId() {
		if (tasks.isEmpty()) {
			return 0;
		}

		int maxId = tasks.get(0).getId();
		for (Task task : tasks) {
			if (task.getId() > maxId) {
				maxId = task.getId();
			}
		}

		return maxId;
	}

}
