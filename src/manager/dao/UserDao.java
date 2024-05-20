package manager.dao;

import java.util.List;

import manager.entities.User;
import manager.persistence.UserRepository;

public class UserDao {
	private static UserDao instance;

	private UserRepository userRepository = new UserRepository();

	private UserDao() {

	}

	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}

		return instance;
	}

	public List<User> getUsers() {
		return userRepository.getUsers();
	}

	public User getUser(int id) {
		return userRepository.getUser(id);
	}

	public User getUser(String email) {
		return userRepository.getUser(email);
	}

	public User addUser(String name, String email, String password, int role) {
		return userRepository.addUser(name, email, password, role);
	}

	public User updateUser(User user) {
		return userRepository.updateUser(user);
	}

	public void deleteUser(int id) {
		userRepository.deleteUser(id);
	}

}
