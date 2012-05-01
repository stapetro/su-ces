package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.CourseAssessment;
import bg.sofia.uni.fmi.ces.model.facade.course.CourseAssessmentPersistence;
import bg.sofia.uni.fmi.ces.utils.session.SessionUtils;

/**
 * This is the backing bean of the FeedbackForm. It provided the basic
 * functionality to operate with course assessment.F
 */
@ManagedBean(name = "feedbackForm")
@ViewScoped
public class FeedbackForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9168652297544014276L;
	/**
	 * For internal use only!!! Determine if the local
	 * <code>courseAssessment</code> is an existing record in the database or a
	 * newly created one
	 */
	private boolean isNewCourseAssessment;
	private CourseAssessment courseAssessment;

	private CourseAssessmentPersistence courseAssessmentFacade;

	public FeedbackForm() {
		courseAssessmentFacade = new CourseAssessmentPersistence();
		courseAssessmentFacade.beginTransaction();
		initCourseAssessment();
	}

	private void initCourseAssessment() {
		isNewCourseAssessment = false;

		// TODO should be set when the course page is implemented. the Course
		// Object will be access through the session
		Course course = new Course();
		course.setCourseId(1);

		String userName = SessionUtils.getLoggedUserName();
		courseAssessment = courseAssessmentFacade.getCourseAssassment(userName,
				course.getCourseId());

		if (courseAssessment == null) {
			courseAssessment = new CourseAssessment();
			courseAssessment.setCourse(course);

			courseAssessment.setUsersUserEmail(userName);

			isNewCourseAssessment = true;
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
		courseAssessment.setCourse(course);
	}

	/**
	 * Action listener for saving the course feedback assessment.
	 * 
	 * @param event
	 */
	public void saveCourseFeedbackForm(ActionEvent event) {
		if (isNewCourseAssessment == true) {
			courseAssessmentFacade.persist(courseAssessment);
			initCourseAssessment();
		}

		courseAssessmentFacade.commitTransaction();
		// start a new transaction in case of further changes
		courseAssessmentFacade.beginTransaction();
	}

}
