package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.CourseAssessment;
import bg.sofia.uni.fmi.ces.model.facade.course.CourseAssessmentPersistence;
import bg.sofia.uni.fmi.ces.model.facade.course.CoursePersistence;
import bg.sofia.uni.fmi.ces.utils.session.SessionUtils;

/**
 * This is the backing bean of the FeedbackForm. It provided the basic
 * functionality to operate with course assessment.F
 */
@ManagedBean(name = "feedbackFormBean")
@ViewScoped
public class FeedbackFormBean extends SucesBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9168652297544014276L;

	private CourseAssessment courseAssessment;

	private CourseAssessmentPersistence courseAssessmentFacade;

	private CoursePersistence coursePersistence;

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		String courseIdAsString = externalContext.getRequestParameterMap().get(
				"courseId");

		int courseId = 0;
		if (courseIdAsString != null) {
			courseId = Integer.parseInt(courseIdAsString);
		}

		courseAssessmentFacade = new CourseAssessmentPersistence();
		coursePersistence = new CoursePersistence();

		String userName = SessionUtils.getLoggedUserName();
		courseAssessment = courseAssessmentFacade.getCourseAssassment(userName,
				courseId);
		if (courseAssessment == null) {
			Course course = coursePersistence.getCourseById(courseId);
			courseAssessment = new CourseAssessment();
			courseAssessment.setCourse(course);

			courseAssessment.setUsersUserEmail(userName);
		}
	}

	public CourseAssessment getCourseAssessment() {
		return courseAssessment;
	}

	public void setCourseAssessment(CourseAssessment courseAssessment) {
		this.courseAssessment = courseAssessment;
	}

	/**
	 * Action listener for saving the course feedback assessment.
	 * 
	 * @param event
	 */
	public void saveCourseFeedbackForm(ActionEvent event) {
		courseAssessmentFacade.beginTransaction();
		courseAssessmentFacade.persist(courseAssessment);
		courseAssessmentFacade.commitTransaction();
	}
}
