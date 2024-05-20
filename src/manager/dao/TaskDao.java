package manager.dao;

import java.util.List;

import manager.entities.Task;
import manager.entities.Task.TaskStatus;
import manager.persistence.TaskRepository;

public class TaskDao {
	private static TaskDao instance;

	private TaskRepository taskRepository = new TaskRepository();

	private TaskDao() {

	}

	public static TaskDao getInstance() {
		if (instance == null) {
			instance = new TaskDao();
		}

		return instance;
	}

	public List<Task> getTasks() {
		return taskRepository.getTasks();
	}

	public List<Task> getCreatorTasks(int creatorId) {
		return taskRepository.selectCreatorTasks(creatorId);
	}

	public List<Task> getExecutorTasks(int executorId) {
		return taskRepository.selectExecutorTasks(executorId);
	}

	public Task getTask(int id) {
		return taskRepository.getTask(id);
	}

	public Task addTask(String name, String description, TaskStatus status, int categoryId, int creatorUserId,
			int executorUserId) {
		return taskRepository.addTask(name, description, status, categoryId, creatorUserId, executorUserId);
	}

	public Task updateTask(Task task) {
		return taskRepository.updateTask(task);
	}

	public void deleteTask(int id) {
		taskRepository.deleteTask(id);
	}

}
