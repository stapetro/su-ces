package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

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
		constructMenuModel();
		breadCrumbsModel = new DefaultMenuModel();
	}

	public MenuModel getSucesMenuModel() {
		return sucesMenuModel;
	}

	public void setSucesMenuModel(MenuModel sucesMenuModel) {
		this.sucesMenuModel = sucesMenuModel;
	}

	// TODO To be implemented.
	public MenuModel getBreadCrumbsModel() {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = context.getViewRoot();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		String currentUri = request.getRequestURI();
		breadCrumbsModel = constructBreadCrumbsModel(sucesMenuModel, currentUri);
		return breadCrumbsModel;
	}

	private void constructMenuModel() {
		sucesMenuModel = new DefaultMenuModel();
		String[] itemValues = { "Home", "Course" };
		String[] itemUrls = { "/pages/faces/home.xhtml",
				"/pages/faces/courseSearch.xhtml" };
		for (int index = 0; index < itemValues.length; index++) {
			String itemValue = itemValues[index];
			String itemUrl = itemUrls[index];
			MenuItem menuItem = new MenuItem();
			menuItem.setValue(itemValue);
			menuItem.setUrl(itemUrl);
			sucesMenuModel.addMenuItem(menuItem);
		}
		// MenuItem home = new MenuItem();
		// home.setValue("Home");
		// home.setUrl("/pages/faces/home.xhtml");
		//
		// MenuItem course = new MenuItem();
		// home.setValue("Course");
		// home.setUrl("/pages/faces/courseSearch.xhtml");
		//
		// MenuItem course1 = new MenuItem();
		// home.setValue("Course1");
		// home.setUrl("/pages/faces/courseSearch.xhtml");
		//
		// MenuItem course2 = new MenuItem();
		// home.setValue("Course2");
		// home.setUrl("/pages/faces/courseSearch.xhtml");
		//
		// Submenu subMenu = new Submenu();
		// subMenu.getChildren().add(course1);
		// subMenu.getChildren().add(course2);
		// sucesMenuModel.addSubmenu(submenu)
	}

	private MenuModel constructBreadCrumbsModel(MenuModel menuModel,
			String requestUri) {
		List<UIComponent> menuContents = menuModel.getContents();
		if (menuContents != null && menuContents.size() > 0) {
			List<MenuItem> breadCrumbContents = new ArrayList<MenuItem>();
			UIComponent foundComponent = constructBreadCrumbsPath(menuContents,
					requestUri, breadCrumbContents);
			if (foundComponent != null) {
				List<UIComponent> oldContents = breadCrumbsModel.getContents();
				if (oldContents != null) {
					oldContents.clear();
				}
				for (MenuItem menuItem : breadCrumbContents) {
					MenuItem breadCrumbMenuItem = new MenuItem();
					breadCrumbMenuItem.setValue(menuItem.getValue());
					breadCrumbMenuItem.setUrl(menuItem.getUrl());
					breadCrumbsModel.addMenuItem(breadCrumbMenuItem);
				}
			}
		}
		return breadCrumbsModel;
	}

	private UIComponent constructBreadCrumbsPath(List<UIComponent> components,
			String uri, List<MenuItem> breadCrumbContents) {
		for (UIComponent uiComponent : components) {
			if (uiComponent instanceof MenuItem) {
				MenuItem currMenuItem = (MenuItem) uiComponent;
				String currItemUrl = currMenuItem.getUrl();
				breadCrumbContents.add(currMenuItem);
				if (uri.indexOf(currItemUrl) > 0) {
					// breadCrumbContents.add(0, currMenuItem);
					return currMenuItem;
				} else if (currMenuItem.getChildCount() > 0) {
					UIComponent foundChildComponent = constructBreadCrumbsPath(
							currMenuItem.getChildren(), uri, breadCrumbContents);
					if (foundChildComponent != null) {
						// breadCrumbContents.add(0, currMenuItem);
						return foundChildComponent;
					}
				}
				UIComponent parent = currMenuItem.getParent();
				if (parent != null && parent instanceof MenuItem) {
					breadCrumbContents.remove(currMenuItem);
				}
			} else {
				getLogger().debug(uiComponent.getId() + "' not a menu item");
			}
		}
		return null;
	}
}
