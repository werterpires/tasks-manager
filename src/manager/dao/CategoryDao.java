package manager.dao;

import java.util.List;

import manager.entities.Category;
import manager.persistence.CategoryRepository;

public class CategoryDao {
	private static CategoryDao instance;

	private CategoryRepository categoryRepository = new CategoryRepository();

	private CategoryDao() {

	}

	public static CategoryDao getInstance() {
		if (instance == null) {
			instance = new CategoryDao();
		}

		return instance;
	}

	public List<Category> getCategories() {
		return categoryRepository.getCategories();
	}

	public Category getCategory(int id) {
		return categoryRepository.getCategory(id);
	}

	public Category addCategory(String name) {
		return categoryRepository.addCategory(name);
	}

	public Category updateCategory(Category category) {
		return categoryRepository.updateCategory(category);
	}

	public void deleteCategory(int id) {
		categoryRepository.deleteCategory(id);
	}

}
