package bg.sofia.uni.fmi.ces.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.CourseAssessment;
import bg.sofia.uni.fmi.ces.model.facade.CourseAssessmentFacade;
import bg.sofia.uni.fmi.ces.model.facade.ModelFacade;
import bg.sofia.uni.fmi.ces.session.utils.SessionUtils;

/**
 * This is the backing bean of the FeedbackForm. It provided the basic
 * functionality to operate with course assessment.F
 */
@ManagedBean(name = "feedbackForm")
@ViewScoped
public class FeedbackForm {
	/**
	 * For internal use only!!! Determine if the local
	 * <code>courseAssessment</code> is an existing record in the database or a
	 * newly created one
	 */
	private boolean isNewCourseAssessment;

	private CourseAssessment courseAssessment;

	public FeedbackForm() {
		isNewCourseAssessment = true;

		// TODO should be set when the course page is implemented. the Course
		// Object will be access through the session
		Course course = new Course();
		course.setCourseId(1);

		String userName = SessionUtils.getLoggedUserName();

		CourseAssessmentFacade courseAssessmentFacade = new CourseAssessmentFacade();
		courseAssessment = courseAssessmentFacade.getCourseAssassment(userName,
				course);

		if (courseAssessment == null) {
			courseAssessment = new CourseAssessment();
			courseAssessment.setCours(course);
			
			isNewCourseAssessment = false;
		}
	}

	public CourseAssessment getCourseAssessment() {
		return courseAssessment;
	}

	public void setCourseAssessment(CourseAssessment courseAssessment) {
		this.courseAssessment = courseAssessment;

		// TODO fix when course form is implemented
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

		System.out.println("---> ");

		ModelFacade modelFacade = new ModelFacade();
		modelFacade.persist(courseAssessment);
	}

}
