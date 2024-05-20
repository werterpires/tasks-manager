package manager.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import manager.dao.UserDao;
import manager.entities.User;
import manager.session.LoggedIn;

@ManagedBean
@RequestScoped
public class UserController {

	private List<User> users;

	private User user;

	private String name;
	private String email;
	private String password;
	private int level;

	// Getters and Setters

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		if (users == null) {
			users = new ArrayList<>();
		}
		return users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	// CRUD Methods

	public void findUsers() {
		try {
			UserDao userDao = UserDao.getInstance();
			this.users = userDao.getUsers();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar usuários");
		}
	}

	public String findUserById(int id) {
		try {
			UserDao userDao = UserDao.getInstance();
			this.user = userDao.getUser(id);
			return "user";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar usuário");
		}
	}

	public String addUser() {
		try {
			if (this.name == null || this.name.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo Nome é obrigatório.", null));
				return null;
			}
			if (this.email == null || this.email.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo E-mail é obrigatório.", null));
				return null;
			}
			if (this.password == null || this.password.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo Senha é obrigatório.", null));
				return null;
			}
			if (this.level == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um Nível.", null));
				return null;
			}

			String name = this.name;
			String email = this.email;
			String password = this.password;
			int level = this.level;

			UserDao userDao = UserDao.getInstance();
			User addedUser = userDao.addUser(name, email, password, level);
			getUsers().add(addedUser);

			this.user = new User();
			this.name = null;
			this.email = null;
			this.password = null;
			this.level = 0;

			return "login"; // Página de sucesso

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao adicionar usuário: " + e.getMessage(), null));
			return null;
		}
	}

	public String updateUser(User user) {
		try {
			UserDao userDao = UserDao.getInstance();
			this.user = userDao.updateUser(user);
			return "user";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao atualizar usuário");
		}
	}

	public String deleteUser(int id) {
		try {
			UserDao userDao = UserDao.getInstance();
			userDao.deleteUser(id);
			return "login_logon";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao deletar usuário");
		}
	}

	public String login() {

		try {
			if (this.email == null || this.email.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo E-mail é obrigatório.", null));
				return null;
			}

			if (this.password == null || this.password.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo Senha é obrigatório.", null));
				return null;
			}
			UserDao userDao = UserDao.getInstance();
			User user = userDao.getUser(this.email);

			if (user == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail ou senha inválidos.", null));
				return null;
			}

			if (!user.getPassword().equals(this.password)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail ou senha inválidos.", null));
				return null;
			}

			LoggedIn loggedIn = LoggedIn.getInstance();
			loggedIn.setUser(user);

			return "tasks";
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo E-mail é obrigatório.", null));
			return null;
		}

	}
}
