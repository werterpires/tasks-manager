package manager.session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import manager.entities.User;

@ManagedBean
@SessionScoped
public class LoggedIn {

	private User user;

	public LoggedIn() {

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
