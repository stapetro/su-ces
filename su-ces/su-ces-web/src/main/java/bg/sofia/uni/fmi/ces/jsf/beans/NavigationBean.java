package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

/**
 * Responsible for navigation.
 * 
 * @author Staskata
 * 
 */
@ManagedBean(name = "navBean")
@SessionScoped
public class NavigationBean extends SucesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3643596564602593988L;
	private MenuModel sucesMenuModel;
	private MenuModel breadCrumbsModel;

	@PostConstruct
	public void init() {
		constructNavigation();
	}

	public MenuModel getSucesMenuModel() {
		return sucesMenuModel;
	}

	public void setSucesMenuModel(MenuModel sucesMenuModel) {
		this.sucesMenuModel = sucesMenuModel;
	}

	public MenuModel getBreadCrumbsModel() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		String currentUri = request.getRequestURI();
		if (currentUri != null && currentUri.isEmpty() == false) {
			breadCrumbsModel = constructBreadCrumbsModel(sucesMenuModel,
					currentUri);
		}
		return breadCrumbsModel;
	}

	/**
	 * Constructs navigation structure.
	 */
	private void constructNavigation() {
		sucesMenuModel = new DefaultMenuModel();
		addHomeItemToMenuModel(sucesMenuModel);
		MenuItem course = new MenuItem();
		course.setValue("Course");
		course.setUrl("/pages/faces/courseSearch.xhtml");
		sucesMenuModel.addMenuItem(course);
		breadCrumbsModel = new DefaultMenuModel();
		addHomeItemToMenuModel(breadCrumbsModel);
	}

	/**
	 * Adds home menu item to a particular menu model.
	 * 
	 * @param menuModel
	 *            Menu model to be specified.
	 */
	private void addHomeItemToMenuModel(MenuModel menuModel) {
		MenuItem home = new MenuItem();
		home.setValue("Home");
		home.setUrl("/pages/faces/home.xhtml");
		menuModel.addMenuItem(home);
	}

	/**
	 * Dynamically constructs bread crumbs from the navigation menu model.
	 * 
	 * @param menuModel
	 *            Menu model to be specified.
	 * @param requestUri
	 *            Request URI address.
	 * @return Dynamically calculated bread crumbs model.
	 */
	private MenuModel constructBreadCrumbsModel(MenuModel menuModel,
			String requestUri) {
		List<UIComponent> menuContents = menuModel.getContents();
		if (menuContents != null && menuContents.size() > 0) {
			List<UIComponent> breadCrumbContents = new ArrayList<UIComponent>();
			UIComponent foundComponent = constructBreadCrumbsPath(menuContents,
					requestUri, breadCrumbContents);
			if (foundComponent != null) {
				List<UIComponent> oldContents = breadCrumbsModel.getContents();
				if (oldContents != null) {
					oldContents.clear();
				}
				for (UIComponent uiComponent : breadCrumbContents) {
					MenuItem breadCrumbMenuItem = new MenuItem();
					boolean canAddItem = false;
					if (uiComponent instanceof MenuItem) {
						MenuItem menuItem = (MenuItem) uiComponent;
						breadCrumbMenuItem.setValue(menuItem.getValue());
						breadCrumbMenuItem.setUrl(menuItem.getUrl());
						canAddItem = true;
					} else if (uiComponent instanceof Submenu) {
						Submenu submenu = (Submenu) uiComponent;
						breadCrumbMenuItem.setValue(submenu.getLabel());
						canAddItem = true;
					}
					if (canAddItem) {
						breadCrumbsModel.addMenuItem(breadCrumbMenuItem);
					}
				}
			}
		}
		return breadCrumbsModel;
	}

	/**
	 * Constructs bread crumbs from component tree and request URI.
	 * 
	 * @param components
	 *            Navigation UI components (menu items) to be specified.
	 * @param uri
	 *            Request uri that indicates what is the current page.
	 * @param breadCrumbContents
	 *            Dynamically added bread crumbs component.
	 * @return UI component that represents link to current page.
	 */
	private UIComponent constructBreadCrumbsPath(List<UIComponent> components,
			String uri, List<UIComponent> breadCrumbContents) {
		for (UIComponent uiComponent : components) {
			if (uiComponent instanceof MenuItem) {
				MenuItem currMenuItem = (MenuItem) uiComponent;
				String currItemUrl = currMenuItem.getUrl();
				if (currItemUrl != null && uri.indexOf(currItemUrl) > 0) {
					breadCrumbContents.add(0, currMenuItem);
					return currMenuItem;
				}
			}
			if (uiComponent.getChildCount() > 0) {
				UIComponent foundChildComponent = constructBreadCrumbsPath(
						uiComponent.getChildren(), uri, breadCrumbContents);
				if (foundChildComponent != null) {
					breadCrumbContents.add(0, uiComponent);
					return foundChildComponent;
				}
			}
		}
		return null;
	}
}
