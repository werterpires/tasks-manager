package manager.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import manager.dao.TaskDao;
import manager.entities.Category;
import manager.entities.Task;

@ManagedBean
@ApplicationScoped
public class CategoryRepository {
	private List<Category> categories = new ArrayList<>();

	public CategoryRepository() {
		categories.add(new Category(1, "Backend"));
		categories.add(new Category(2, "Frontend"));
		categories.add(new Category(3, "Reunião"));
		categories.add(new Category(4, "Relatório"));
		categories.add(new Category(5, "Pesquisa"));

	}

	public List<Category> getCategories() {
		return categories;
	}

	public Category getCategory(int id) {
		for (Category category : categories) {
			if (category.getId() == id) {
				return category;
			}
		}
		return null;
	}

	public Category addCategory(String name) {

		int id = incrementId();
		Category category = new Category(id, name);
		categories.add(category);
		return category;
	}

	public Category updateCategory(Category category) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).getId() == category.getId()) {
				categories.set(i, category);
				return category;
			}
		}
		return null;
	}

	public void deleteCategory(int id) {

		Category category = getCategory(id);

		if (category == null) {
			throw new RuntimeException("Category not found");
		}

		TaskDao taskDao = TaskDao.getInstance();
		List<Task> tasks = taskDao.getTasks();

		Task taskWithCategory = null;
		for (Task task : tasks) {
			if (task.getCategoryId() == id) {
				taskWithCategory = task;
				break;
			}
		}

		if (taskWithCategory != null) {
			throw new RuntimeException("Essa categoria não pode ser deletada, porque é usada por uma tarefa.");

		}

		categories.removeIf(categ -> categ.getId() == id);
	}

	private int incrementId() {
		int maxId = getMaxCategoryId();
		return maxId + 1;
	}

	private int getMaxCategoryId() {
		if (categories.isEmpty()) {
			return 0;
		}

		int maxId = categories.get(0).getId();
		for (Category category : categories) {
			if (category.getId() > maxId) {
				maxId = category.getId();
			}
		}

		return maxId;
	}

}
