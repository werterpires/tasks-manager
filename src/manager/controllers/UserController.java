package manager.controllers;

import java.util.List;

import manager.dao.UserDao;
import manager.entities.User;

public class UserController {

	private List<User> users;

	private User user;

	// Getters and Setters

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	// CRUD Methods

	public String findUsers() {
		try {
			UserDao userDao = UserDao.getInstance();
			this.users = userDao.getUsers();
			return "login_logon";
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

	public String addUser(User user) {
		try {
			UserDao userDao = UserDao.getInstance();
			User addedUser = userDao.addUser(user);
			return "login_logon";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao adicionar usuário");
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
}
