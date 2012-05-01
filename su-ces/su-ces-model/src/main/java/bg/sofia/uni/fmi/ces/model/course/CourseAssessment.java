package bg.sofia.uni.fmi.ces.model.course;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the course_assessment database table.
 * 
 */
@Entity
@Table(name = "course_assessment")
public class CourseAssessment implements Serializable {
	private static final long serialVersionUID = 1L;
	private int courseAssessmentId;
	private int assessmentCorrectness;
	private int courseAttitude;
	private int courseDifficulty;
	private int courseEngagements;
	private int courseInPlan;
	private int courseInderstanding;
	private int courseOrganization;
	private int coursePresentation;
	private int couseUsefullness;
	private int studentsAttitude;
	private String usersUserEmail;
	private Course course;

	public CourseAssessment() {
	}

	@Id
	@SequenceGenerator(name = "COURSE_ASSESSMENT_COURSEASSESSMENTID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURSE_ASSESSMENT_COURSEASSESSMENTID_GENERATOR")
	@Column(name = "course_assessment_id", unique = true, nullable = false)
	public int getCourseAssessmentId() {
		return this.courseAssessmentId;
	}

	public void setCourseAssessmentId(int courseAssessmentId) {
		this.courseAssessmentId = courseAssessmentId;
	}

	@Column(name = "assessment_correctness")
	public int getAssessmentCorrectness() {
		return this.assessmentCorrectness;
	}

	public void setAssessmentCorrectness(int assessmentCorrectness) {
		this.assessmentCorrectness = assessmentCorrectness;
	}

	@Column(name = "course_attitude")
	public int getCourseAttitude() {
		return this.courseAttitude;
	}

	public void setCourseAttitude(int courseAttitude) {
		this.courseAttitude = courseAttitude;
	}

	@Column(name = "course_difficulty")
	public int getCourseDifficulty() {
		return this.courseDifficulty;
	}

	public void setCourseDifficulty(int courseDifficulty) {
		this.courseDifficulty = courseDifficulty;
	}

	@Column(name = "course_engagements")
	public int getCourseEngagements() {
		return this.courseEngagements;
	}

	public void setCourseEngagements(int courseEngagements) {
		this.courseEngagements = courseEngagements;
	}

	@Column(name = "course_in_plan")
	public int getCourseInPlan() {
		return this.courseInPlan;
	}

	public void setCourseInPlan(int courseInPlan) {
		this.courseInPlan = courseInPlan;
	}

	@Column(name = "course_inderstanding")
	public int getCourseInderstanding() {
		return this.courseInderstanding;
	}

	public void setCourseInderstanding(int courseInderstanding) {
		this.courseInderstanding = courseInderstanding;
	}

	@Column(name = "course_organization")
	public int getCourseOrganization() {
		return this.courseOrganization;
	}

	public void setCourseOrganization(int courseOrganization) {
		this.courseOrganization = courseOrganization;
	}

	@Column(name = "course_presentation")
	public int getCoursePresentation() {
		return this.coursePresentation;
	}

	public void setCoursePresentation(int coursePresentation) {
		this.coursePresentation = coursePresentation;
	}

	@Column(name = "couse_usefullness")
	public int getCouseUsefullness() {
		return this.couseUsefullness;
	}

	public void setCouseUsefullness(int couseUsefullness) {
		this.couseUsefullness = couseUsefullness;
	}

	@Column(name = "students_attitude")
	public int getStudentsAttitude() {
		return this.studentsAttitude;
	}

	public void setStudentsAttitude(int studentsAttitude) {
		this.studentsAttitude = studentsAttitude;
	}

	@Column(name = "users_user_email", nullable = false, length = 64)
	public String getUsersUserEmail() {
		return this.usersUserEmail;
	}

	public void setUsersUserEmail(String usersUserEmail) {
		this.usersUserEmail = usersUserEmail;
	}

	// bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name = "courses_course_id", nullable = false)
	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}