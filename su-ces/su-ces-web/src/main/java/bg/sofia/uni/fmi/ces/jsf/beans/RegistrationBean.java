package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.catalina.realm.RealmBase;

import bg.sofia.uni.fmi.ces.constants.Messages;
import bg.sofia.uni.fmi.ces.model.facade.user.RolePersistence;
import bg.sofia.uni.fmi.ces.model.facade.user.UserPersistence;
import bg.sofia.uni.fmi.ces.model.user.Role;
import bg.sofia.uni.fmi.ces.model.user.User;
import bg.sofia.uni.fmi.ces.utils.faces.FacesContextUtil;
import bg.sofia.uni.fmi.ces.utils.msg.MessageUtil;

@ManagedBean(name = "registrationBean")
@ViewScoped
public class RegistrationBean extends SucesBean implements Serializable {

	private static final long serialVersionUID = -1018989257462312837L;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	private static final String EMAIL_PATTERN_MATCH_ERROR_MSG = "reg_email_format_error";
	private static final String EMAIL_UNIQUE_ERROR_MSG = "reg_email_unique_error";
	private static final String PASSWORD_MATCH_ERROR_MSG = "reg_password_match_error";

	private RolePersistence rolePersistence;
	private UserPersistence userPersistence;
	private Pattern pattern;
	private String email;
	private String password;
	private String confirmPassword;

	public RegistrationBean() {
		rolePersistence = new RolePersistence();
		userPersistence = new UserPersistence();
		try {
			pattern = Pattern.compile(EMAIL_PATTERN);
		} catch (PatternSyntaxException pse) {
			getLogger().error(pse);
		}
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
		final String registerStatusUrl = "registerStatus.xhtml?status=%s";
		String url = "";
		if (studentRole != null) {
			User user = new User();
			user.setUserEmail(getEmail());
			String password = getPassword();
			String digestedPass = RealmBase.Digest(password, "SHA",
					StandardCharsets.UTF_8.toString());
			user.setPassword(digestedPass);
			Locale currentLocale = FacesContext.getCurrentInstance()
					.getViewRoot().getLocale();
			Calendar todayCal = Calendar.getInstance(currentLocale);
			Timestamp today = new Timestamp(todayCal.getTimeInMillis());
			user.setCreateDate(today);
			user.addRole(studentRole);
			user = userPersistence.save(user);
			if (user != null) {
				url = String.format(registerStatusUrl, "ok");
				// return "registerStatus?status=ok";
			}
		} else {
			getLogger().error("Student role is not found in DB");
			url = String.format(registerStatusUrl, "fail");
		}
		// return "registerStatus.?status=fail";
		FacesContext currContext = FacesContext.getCurrentInstance();
		try {
			currContext.getExternalContext().redirect(url);
		} catch (IOException e) {
			getLogger().error(e);
		}
		return null;
	}

	public void validateEmail(FacesContext context,
			UIComponent toValidateEmail, Object value) {
		if (toValidateEmail instanceof UIInput) {
			UIInput emailInput = (UIInput) toValidateEmail;
			if (value != null) {
				String email = value.toString();
				String clientId = emailInput.getClientId(context);
				if (pattern == null) {
					String msgValue = MessageUtil.loadMessage(context,
							Messages.SYSTEM_ERROR_MSG_KEY);
					MessageUtil
							.addMessageToContext(context, clientId, msgValue);
					emailInput.setValid(false);
				}
				Matcher matcher = pattern.matcher(email);
				boolean validEmailFormat = (matcher != null && matcher
						.matches());
				if (validEmailFormat == false) {
					String msgValue = MessageUtil.loadMessage(context,
							EMAIL_PATTERN_MATCH_ERROR_MSG);
					MessageUtil
							.addMessageToContext(context, clientId, msgValue);
					emailInput.setValid(false);
					return;
				}
				boolean isEmailTaken = userPersistence.isUserExists(email);
				if (isEmailTaken) {
					String msgValue = MessageUtil.loadMessage(context,
							EMAIL_UNIQUE_ERROR_MSG);
					MessageUtil
							.addMessageToContext(context, clientId, msgValue);
					emailInput.setValid(false);
					return;
				}
			}
		} else {
			getLogger().error(
					"Email UI control is of type '"
							+ toValidateEmail.getClass() + "'");
		}
	}

	public void validateConfirmPassword(FacesContext context,
			UIComponent toValidateConfirmPasswod, Object value) {
		if (toValidateConfirmPasswod instanceof UIInput) {
			UIInput confirmPassInput = (UIInput) toValidateConfirmPasswod;
			String clientId = confirmPassInput.getClientId(context);
			if (value != null) {
				String confirmPassword = value.toString();
				String passwordValue = getUIPasswordValue(context);
				if (confirmPassword.equals(passwordValue) == false) {
					String msgValue = MessageUtil.loadMessage(context,
							PASSWORD_MATCH_ERROR_MSG);
					if (msgValue != null && msgValue.isEmpty() == false) {
						String passLbl = MessageUtil.loadMessage(context,
								Messages.REG_PASS_LABEL_KEY);
						if (passLbl == null) {
							passLbl = "";
						}
						String confirmPassLbl = MessageUtil.loadMessage(
								context, Messages.REG_CONFIRM_PASS_LABEL_KEY);
						if (confirmPassLbl == null) {
							confirmPassLbl = "";
						}
						try {
							String msgValueFormatted = String.format(msgValue,
									confirmPassLbl, passLbl);
							msgValue = msgValueFormatted;
						} catch (Exception ex) {
							getLogger().error(ex);
						}
					} else {
						msgValue = MessageUtil.loadMessage(context,
								Messages.SYSTEM_ERROR_MSG_KEY);
					}
					MessageUtil
							.addMessageToContext(context, clientId, msgValue);
					confirmPassInput.setValid(false);
					return;
				}
			} else {
				String msgValue = MessageUtil.loadMessage(context,
						Messages.SYSTEM_ERROR_MSG_KEY);
				MessageUtil.addMessageToContext(context, clientId, msgValue);
				confirmPassInput.setValid(false);
				return;
			}
		} else {
			getLogger().error(
					"Confirm Password UI control is of type '"
							+ toValidateConfirmPasswod.getClass() + "'");
		}
	}

	private String getUIPasswordValue(FacesContext context) {
		String passwordValue = "";
		UIComponent passwordComponent = FacesContextUtil.findComponent(
				context.getViewRoot(), "password");
		if (passwordComponent != null && passwordComponent instanceof UIInput) {
			Object passwordObject = ((UIInput) passwordComponent).getValue();
			if (passwordObject != null) {
				passwordValue = passwordObject.toString();
			}
		}
		return passwordValue;
	}
}
