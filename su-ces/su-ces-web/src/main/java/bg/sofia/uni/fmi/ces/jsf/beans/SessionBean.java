package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.Serializable;
import java.security.Principal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4712255715383240545L;
	
	public SessionBean() {
		
	}
	
	public boolean isLoggedIn() {
    	String userName = getUserName();
    	if(userName != null && userName.isEmpty() == false) {
    		return true;
    	}
		return false;
	}
	
	/**
     * Logs the current user out by invalidating the session.
     * @return &quot;logout&quot; which is used by the {@literal faces-config.xml}
     * to redirect back to the {@literal home.xhtml} page.
     */	
	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.invalidateSession();
		return "logout";
	}
	
	public String getUserName() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Principal user = externalContext.getUserPrincipal();
        if(user != null) {
        	String userName = user.getName();
        	if(userName != null && userName.isEmpty() == false) {
        		return userName;
        	}
        }				
        return null;
	}

}
