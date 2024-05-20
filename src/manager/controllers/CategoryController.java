package manager.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import manager.dao.CategoryDao;
import manager.entities.Category;
/*import manager.session.LoggedIn;*/
import manager.session.LoggedIn;

@ManagedBean
@SessionScoped
public class CategoryController {

	private List<Category> categories;

	private Category category;

	private String name;

	// Getters and Setters

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getCategories() {
		if (categories == null) {
			categories = new ArrayList<>();
		}
		return categories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// CRUD Methods

	public void findCategories() {
		try {
			if (!isLogged()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
				return;
			}
			CategoryDao categoryDao = CategoryDao.getInstance();
			this.categories = categoryDao.getCategories();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar categorias");
		}
	}

	public String findCategoryById(int id) {
		try {
			if (!isLogged()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
				return null;
			}
			CategoryDao categoryDao = CategoryDao.getInstance();
			this.category = categoryDao.getCategory(id);
			return "category";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar categoria");
		}
	}

	public String addCategory() {
		try {
			if (!isLogged()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
				return null;
			}
			if (this.name == null || this.name.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo Nome é obrigatório.", null));
				return null;
			}

			String name = this.name;

			CategoryDao categoryDao = CategoryDao.getInstance();
			categoryDao.addCategory(name);
			getCategories();

			this.category = new Category();
			this.name = null;

			return "categories?faces-redirect=true";

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao adicionar categoria: " + e.getMessage(), null));
			return null;
		}
	}

	public String updateCategory() {
		try {
			if (!isLogged()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
				return null;
			}
			CategoryDao categoryDao = CategoryDao.getInstance();
			this.category = categoryDao.updateCategory(this.category);
			return "categories";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao atualizar categoria");
		}
	}

	public String deleteCategory(String id) {
		try {
			if (!isLogged()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
				return null;
			}
			System.out.println("deleting category with id: " + id);
			CategoryDao categoryDao = CategoryDao.getInstance();
			categoryDao.deleteCategory(Integer.parseInt(id));
			for (Category category : categories) {
				if (category.getId() == Integer.parseInt(id)) {
					categories.remove(category);
					break;
				}
			}

			return "categories?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao deletar categoria");
		}
	}

	public String selectCategory(String id) {
		if (!isLogged()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faça login para acessar esse recurso", null));
			return null;
		}
		int idInt = Integer.parseInt(id);
		findCategoryById(idInt);
		return "category";
	}

	public Boolean isLogged() {
		LoggedIn loggedIn = LoggedIn.getInstance();
		return loggedIn.isLogged();
	}

}
