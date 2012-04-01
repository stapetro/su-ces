package bg.sofia.uni.fmi.ces.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "feedbackForm")
@SessionScoped
public class FeedbackForm {
	private int presentationSkills = 3;

	public int getPresentationSkills() {
		System.out.println("---> ");
		return presentationSkills;
	}

	public void setPresentationSkills(int presentationSkills) {
		this.presentationSkills = presentationSkills;
		
		System.out.println("setting ----  + presentationSkills");
	}

}
