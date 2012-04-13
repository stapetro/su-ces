package bg.sofia.uni.fmi.ces.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.CourseAssessment;
import bg.sofia.uni.fmi.ces.model.facade.ModelFacade;
import bg.sofia.uni.fmi.ces.session.utils.SessionUtils;

/**
 * This is the backing bean of the FeedbackForm. It provided the basic
 * functionality to operate with course assessment.F
 */
@ManagedBean(name = "feedbackForm")
@ViewScoped
public class FeedbackForm {
	private CourseAssessment courseAssessment;

	public FeedbackForm() {
		courseAssessment = new CourseAssessment();

		// TODO should be set when the course page is implemented. the Course
		// Object will be access through the session
		Course course = new Course();
		course.setCourseId(1);
		courseAssessment.setCours(course);

	}

	public CourseAssessment getCourseAssessment() {
		return courseAssessment;
	}

	public void setCourseAssessment(CourseAssessment courseAssessment) {
		this.courseAssessment = courseAssessment;
		
		//TODO fix when course form is implemented
		Course course = new Course();
		course.setCourseId(1);
		courseAssessment.setCours(course);
	}

	/**
	 * Action listener for saving the course feedback assessment.
	 * 
	 * @param event
	 */
	public void saveCourseFeedbackForm(ActionEvent event) {
		String userName = SessionUtils.getLoggedUserName();
		courseAssessment.setUsersUserEmail(userName);

		ModelFacade modelFacade = new ModelFacade();
		modelFacade.persist(courseAssessment);
	}

}
