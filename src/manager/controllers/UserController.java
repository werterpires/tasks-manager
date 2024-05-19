package manager.controllers;

import java.util.List;

import manager.dao.UserDao;
import manager.entities.User;

public class UserController {

	public List<User> getUsers() {
		try {
			UserDao userDao = UserDao.getInstance();
			return userDao.getUsers();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar usuários");
		}
	}

	public User getUser(int id) {
		try {
			UserDao userDao = UserDao.getInstance();
			return userDao.getUser(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar usuário");
		}
	}

	public User getUser(String email) {
		try {
			UserDao userDao = UserDao.getInstance();
			return userDao.getUser(email);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar usuário");
		}
	}

	public User addUser(User user) {
		try {
			UserDao userDao = UserDao.getInstance();
			return userDao.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao adicionar usuário");
		}
	}

	public User updateUser(User user) {
		try {
			UserDao userDao = UserDao.getInstance();
			return userDao.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao atualizar usuário");
		}
	}

	public void deleteUser(int id) {
		try {
			UserDao userDao = UserDao.getInstance();
			userDao.deleteUser(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao deletar usuário");
		}
	}
}
