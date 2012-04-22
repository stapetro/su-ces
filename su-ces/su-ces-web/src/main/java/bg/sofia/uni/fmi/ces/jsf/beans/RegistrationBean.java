package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bg.sofia.uni.fmi.ces.model.facade.user.RolePersistence;
import bg.sofia.uni.fmi.ces.model.facade.user.UserPersistence;
import bg.sofia.uni.fmi.ces.model.user.Role;
import bg.sofia.uni.fmi.ces.model.user.User;

@ManagedBean(name = "registrationBean")
@ViewScoped
public class RegistrationBean implements Serializable {

	private static final long serialVersionUID = -1018989257462312837L;

	private Logger logger;
	private RolePersistence rolePersistence;
	private UserPersistence userPersistence;
	private String email;
	private String password;
	private String confirmPassword;

	public RegistrationBean() {
		rolePersistence = new RolePersistence();
		userPersistence = new UserPersistence();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String register() {
		Role studentRole = rolePersistence
				.getRole(RolePersistence.STUDENT_ROLE_NAME);
		rolePersistence.close();
		getLogger().debug("Student role: " + studentRole);
		if (studentRole != null) {
			User user = new User();
			user.setUserEmail(getEmail());
			user.setPassword(getPassword());
			Locale currentLocale = FacesContext.getCurrentInstance()
					.getViewRoot().getLocale();
			Calendar todayCal = Calendar.getInstance(currentLocale);
			Timestamp today = new Timestamp(todayCal.getTimeInMillis());
			user.setCreateDate(today);
			user.addRole(studentRole);
			user = userPersistence.save(user);
			if (user != null) {
				return "login";
			}
		}
		// TODO Issue error msg when registration fails.
		return null;
	}

	public String cancel() {
		// TODO To be implemented
		return null;
	}

	public void validateEmail(FacesContext context,
			UIComponent toValidateEmail, Object value) {
		String message = "";
		if (value != null) {
			String email = value.toString();
			if(email.contains("@") == false) {
				if(toValidateEmail instanceof UIInput) {
					UIInput emailInput = (UIInput)toValidateEmail;
//					emailInput.setValid(false);
					//Sample code for retrieving error message.
//					message = CoffeeBreakBean.loadErrorMessage(context,
//							CoffeeBreakBean.CB_RESOURCE_BUNDLE_NAME,
//							"EMailError");
//					context.addMessage(toValidate.getClientId(context),
//							new FacesMessage(message));					
				}
			}
		}
	}

	private Logger getLogger() {
		if (logger == null) {
			logger = LogManager.getLogger(this.getClass());
		}
		return logger;
	}

}
