package manager.entities;

public class Task {
	private int id;
	private String name;
	private String description;
	private TaskStatus status;
	private int categoryId;
	private int creatorUserId;
	private int executerUserId;

	private String categoryName;
	private String creatorName;
	private String executerName;

	private String creatorEmail;
	private String executerEmail;

	public enum TaskStatus {
		TODO, DONE, DOING;
	}

	public Task() {

	}

	public Task(int id, String name, String description, TaskStatus status, int categoryId, int creatorUserId,
			int executerUserId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.categoryId = categoryId;
		this.creatorUserId = creatorUserId;
		this.executerUserId = executerUserId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getExecuterName() {
		return executerName;
	}

	public void setExecuterName(String executerName) {
		this.executerName = executerName;
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	public String getExecuterEmail() {
		return executerEmail;
	}

	public void setExecuterEmail(String executerEmail) {
		this.executerEmail = executerEmail;
	}

}
