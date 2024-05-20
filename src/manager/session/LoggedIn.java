package manager.session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import manager.entities.User;

@ManagedBean
@SessionScoped
public class LoggedIn {

	private static LoggedIn instance;

	private User user;

	public LoggedIn() {

	}

	public static LoggedIn getInstance() {
		if (instance == null) {
			instance = new LoggedIn();
		}

		return instance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isLogged() {
		return user != null;
	}

}
