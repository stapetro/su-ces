package bg.sofia.uni.fmi.ces.utils.msg;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bg.sofia.uni.fmi.ces.constants.Messages;

public class MessageUtil {

	private Logger logger;
	private static MessageUtil msgUtil;

	public static String loadMessage(FacesContext context, String key) {
		return getMessageUtil().getMessage(context, key);
	}
	
	public static void addMessageToContext(FacesContext context, String clientId,
			String msgValue) {
		getMessageUtil().addMessage(context, clientId, msgValue);
	}

	private static MessageUtil getMessageUtil() {
		if (msgUtil == null) {
			msgUtil = new MessageUtil();
		}
		return msgUtil;
	}

	private MessageUtil() {

	}

	private String getMessage(FacesContext context, String key) {
		ResourceBundle resourceBundle = getRosourceBundle(context);
		String message = loadMessageFromBundle(resourceBundle, key);
		return message;
	}

	private ResourceBundle getRosourceBundle(FacesContext context) {
		ResourceBundle resourceBundle = context.getApplication()
				.getResourceBundle(context, "msg");
		return resourceBundle;
	}

	private String loadMessageFromBundle(ResourceBundle resourceBundle,
			String key) {
		String msgValue = "";
		if (resourceBundle == null) {
			getLogger().error("Resource bundle for app is NULL");
			msgValue = "Fatal system error occured. Please contact administrator!";
			return msgValue;
		}
		msgValue = resourceBundle.getString(key);
		if (msgValue == null || msgValue.isEmpty()) {
			getLogger().error(
					"Value for key '" + key + "' not found in bundle '"
							+ resourceBundle);
			msgValue = resourceBundle.getString(Messages.SYSTEM_ERROR_MSG_KEY);
			return msgValue;
		}
		return msgValue;
	}
	
	private void addMessage(FacesContext context, String clientId,
			String msgValue) {
		FacesMessage facesMsg = new FacesMessage(msgValue);
		context.addMessage(clientId, facesMsg);
	}

	private Logger getLogger() {
		if (logger == null) {
			logger = LogManager.getLogger(this.getClass());
		}
		return logger;
	}

}
