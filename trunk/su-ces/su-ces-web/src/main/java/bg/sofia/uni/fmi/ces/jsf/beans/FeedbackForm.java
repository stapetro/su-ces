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
	private int course_assessment_id;
	private int course_presentation;
	private int course_inderstanding;
	private int course_attitude;
	private int students_attitude;
	private int assessment_correctness;
	private int course_organization;
	private int couse_usefullness;
	private int course_in_plan;
	private int course_difficulty;
	private int course_engagements;

	/**
	 * Action listener for saving the course feedback assessment.
	 * 
	 * @param event
	 */
	public void saveCourseFeedbackForm(ActionEvent event) {
		CourseAssessment courseAssessment = new CourseAssessment();
		courseAssessment.setAssessmentCorrectness(assessment_correctness);

		// TODO should be set when the course page is implemented. the Course
		// Object will be access through the session
		Course course = new Course();
		course.setCourseId(1);
		courseAssessment.setCours(course);

		// course assessment ID is auto-incremented therefore not set
		courseAssessment.setCourseAttitude(course_attitude);
		courseAssessment.setCourseDifficulty(course_difficulty);
		courseAssessment.setCourseEngagements(course_engagements);
		courseAssessment.setCourseInderstanding(course_inderstanding);
		courseAssessment.setCourseInPlan(course_in_plan);
		courseAssessment.setCourseOrganization(course_organization);
		courseAssessment.setCoursePresentation(course_presentation);
		courseAssessment.setCouseUsefullness(couse_usefullness);
		courseAssessment.setStudentsAttitude(students_attitude);

		String userName = SessionUtils.getLoggedUserName();
		courseAssessment.setUsersUserEmail(userName);

		ModelFacade modelFacade = new ModelFacade();
		modelFacade.persist(courseAssessment);
	}

	public int getCourse_assessment_id() {
		return course_assessment_id;
	}

	public void setCourse_assessment_id(int course_assessment_id) {
		this.course_assessment_id = course_assessment_id;
	}

	public int getCourse_presentation() {
		return course_presentation;
	}

	public void setCourse_presentation(int course_presentation) {
		this.course_presentation = course_presentation;
	}

	public int getCourse_inderstanding() {
		return course_inderstanding;
	}

	public void setCourse_inderstanding(int course_inderstanding) {
		this.course_inderstanding = course_inderstanding;
	}

	public int getCourse_attitude() {
		return course_attitude;
	}

	public void setCourse_attitude(int course_attitude) {
		this.course_attitude = course_attitude;
	}

	public int getStudents_attitude() {
		return students_attitude;
	}

	public void setStudents_attitude(int students_attitude) {
		this.students_attitude = students_attitude;
	}

	public int getAssessment_correctness() {
		return assessment_correctness;
	}

	public void setAssessment_correctness(int assessment_correctness) {
		this.assessment_correctness = assessment_correctness;
	}

	public int getCourse_organization() {
		return course_organization;
	}

	public void setCourse_organization(int course_organization) {
		this.course_organization = course_organization;
	}

	public int getCouse_usefullness() {
		return couse_usefullness;
	}

	public void setCouse_usefullness(int couse_usefullness) {
		this.couse_usefullness = couse_usefullness;
	}

	public int getCourse_in_plan() {
		return course_in_plan;
	}

	public void setCourse_in_plan(int course_in_plan) {
		this.course_in_plan = course_in_plan;
	}

	public int getCourse_difficulty() {
		return course_difficulty;
	}

	public void setCourse_difficulty(int course_difficulty) {
		this.course_difficulty = course_difficulty;
	}

	public int getCourse_engagements() {
		return course_engagements;
	}

	public void setCourse_engagements(int course_engagements) {
		this.course_engagements = course_engagements;
	}

}
