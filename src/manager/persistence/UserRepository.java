package manager.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import manager.entities.User;

@ManagedBean
@ApplicationScoped
public class UserRepository {
	private List<User> users = new ArrayList<>();

	public UserRepository() {
		users.add(new User(1, "Beanderson Carlos", "beanderson@me.com", "123456", 1));
		users.add(new User(2, "Joaquim Tavates", "joaquim@me.com", "123456", 1));
		users.add(new User(3, "Maria Silva", "maria@me.com", "123456", 2));
		users.add(new User(4, "Ana Pereira", "ana@me.com", "123456", 3));
		users.add(new User(5, "Carlos Souza", "carlos@me.com", "123456", 2));
	}

	public List<User> getUsers() {
		return users;
	}

	public User getUser(int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public User getUser(String email) {
		for (User user : users) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}

	public User addUser(User user) {
		users.add(user);
		return user;
	}

	public User updateUser(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == user.getId()) {
				users.set(i, user);
				return user;
			}
		}
		return null;
	}

	public void deleteUser(int id) {
		users.removeIf(user -> user.getId() == id);
	}
}
