package bg.sofia.uni.fmi.ces.utils.faces;

import java.util.Iterator;

import javax.faces.component.UIComponent;

public class FacesContextUtil {

	/**
	 * Finds component with the given id
	 * 
	 * @param component
	 *            Parent UI component to be specified.
	 * @param id
	 *            Child UI component client id to be specified.
	 */
	// TODO Make it use instance.
	public static UIComponent findComponent(UIComponent component, String id) {
		if (id.equals(component.getId())) {
			return component;
		}
		Iterator<UIComponent> kids = component.getFacetsAndChildren();
		while (kids.hasNext()) {
			UIComponent found = findComponent(kids.next(), id);
			if (found != null) {
				return found;
			}
		}
		return null;
	}
}
