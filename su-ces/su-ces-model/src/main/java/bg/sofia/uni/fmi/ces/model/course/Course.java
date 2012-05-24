package bg.sofia.uni.fmi.ces.model.course;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the courses database table.
 * 
 */
@Entity
@Table(name = "courses")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	private int courseId;
	private String courseAnnotation;
	private String examinationForm;
	private String literature;
	private String preliminaryRequirements;
	private String summary;
	private int workload;
	private int year;
	private String courseName;
	private double rating;
	private int ratingCounter;
	private Semester semester;
	private Lecturer lecturer;
	private List<Grade> grades;
	private List<Specialty> specialties;
	private List<CourseAssessment> courseAssessments;

	public Course() {
	}

	@Id
	@SequenceGenerator(name = "COURSES_COURSEID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURSES_COURSEID_GENERATOR")
	@Column(name = "course_id", unique = true, nullable = false)
	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	@Column(name = "course_name", unique = true, nullable = false)
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		//TODO Use constant for max value of rating
		if (rating >= 0 && rating <= 6) {
			this.rating = rating;
		}
	}

	@Column(name = "rating_counter", nullable = false)
	public int getRatingCounter() {
		return ratingCounter;
	}

	public void setRatingCounter(int ratingCounter) {
		if (ratingCounter >= 0) {
			this.ratingCounter = ratingCounter;
		}
	}

	@Lob()
	@Column(name = "course_annotation")
	public String getCourseAnnotation() {
		return this.courseAnnotation;
	}

	public void setCourseAnnotation(String courseAnnotation) {
		this.courseAnnotation = courseAnnotation;
	}

	@Lob()
	@Column(name = "examination_form")
	public String getExaminationForm() {
		return this.examinationForm;
	}

	public void setExaminationForm(String examinationForm) {
		this.examinationForm = examinationForm;
	}

	@Lob()
	public String getLiterature() {
		return this.literature;
	}

	public void setLiterature(String literature) {
		this.literature = literature;
	}

	@Lob()
	@Column(name = "preliminary_requirements")
	public String getPreliminaryRequirements() {
		return this.preliminaryRequirements;
	}

	public void setPreliminaryRequirements(String preliminaryRequirements) {
		this.preliminaryRequirements = preliminaryRequirements;
	}

	@Lob()
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getWorkload() {
		return this.workload;
	}

	public void setWorkload(int workload) {
		this.workload = workload;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	// bi-directional many-to-one association to Semester
	@ManyToOne
	@JoinColumn(name = "semester_semester_id", nullable = false)
	public Semester getSemester() {
		return this.semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	// bi-directional many-to-one association to Lecturer
	@ManyToOne
	@JoinColumn(name = "lecturer_lecturer_id", nullable = false)
	public Lecturer getLecturer() {
		return this.lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	// bi-directional many-to-many association to Grade
	@ManyToMany
	@JoinTable(name = "courses_grade", joinColumns = { @JoinColumn(name = "courses_course_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "grade_grade_id", nullable = false) })
	public List<Grade> getGrades() {
		return this.grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

	// bi-directional many-to-many association to Specialty
	@ManyToMany
	@JoinTable(name = "courses_specialty", joinColumns = { @JoinColumn(name = "courses_course_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "specialty_specialty_id", nullable = false) })
	public List<Specialty> getSpecialties() {
		return this.specialties;
	}

	public void setSpecialties(List<Specialty> specialties) {
		this.specialties = specialties;
	}

	// bi-directional many-to-one association to CourseAssessment
	@OneToMany(mappedBy = "course")
	public List<CourseAssessment> getCourseAssessments() {
		return this.courseAssessments;
	}

	public void setCourseAssessments(List<CourseAssessment> courseAssessments) {
		this.courseAssessments = courseAssessments;
	}

	@Override
	public boolean equals(Object courseObj) {
		if (courseObj == null) {
			return false;
		}
		if (courseObj instanceof Course) {
			Course course = (Course) courseObj;
			return this.courseId == course.courseId
					&& ((this.courseName == null && course.courseName == null) || (this.courseName != null
							&& course.courseName != null && this.courseName
								.equals(course.courseName)))
					&& this.year == course.year
					&& this.workload == course.workload
					&& this.rating == course.rating
					&& this.ratingCounter == course.ratingCounter
					&& ((this.summary == null && course.summary == null) || (this.summary != null
							&& course.summary != null && this.summary
								.equals(course.summary)))
					&& ((this.examinationForm == null && course.examinationForm == null) || (this.examinationForm != null
							&& course.examinationForm != null && this.examinationForm
								.equals(course.examinationForm)))
					&& ((this.courseAnnotation == null && course.courseAnnotation == null) || (this.courseAnnotation != null
							&& course.courseAnnotation != null && this.courseAnnotation
								.equals(course.courseAnnotation)))
					&& ((this.preliminaryRequirements == null && course.preliminaryRequirements == null) || (this.preliminaryRequirements != null
							&& course.preliminaryRequirements != null && this.preliminaryRequirements
								.equals(course.preliminaryRequirements)))
					&& ((this.literature == null && course.literature == null) || (this.literature != null
							&& course.literature != null && this.literature
								.equals(course.literature)));
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = this.courseId ^ this.year ^ this.workload
				^ Double.valueOf(this.rating).hashCode() ^ this.ratingCounter;
		if (this.courseName != null && this.courseName.isEmpty() == false) {
			hashCode &= this.courseName.hashCode();
		}
		if (this.summary != null && this.summary.isEmpty() == false) {
			hashCode &= this.summary.hashCode();
		}
		if (this.examinationForm != null
				&& this.examinationForm.isEmpty() == false) {
			hashCode &= this.examinationForm.hashCode();
		}
		if (this.courseAnnotation != null
				&& this.courseAnnotation.isEmpty() == false) {
			hashCode &= this.courseAnnotation.hashCode();
		}
		if (this.preliminaryRequirements != null
				&& this.preliminaryRequirements.isEmpty() == false) {
			hashCode &= this.preliminaryRequirements.hashCode();
		}
		if (this.literature != null && this.literature.isEmpty() == false) {
			hashCode &= this.literature.hashCode();
		}
		return hashCode;
	}

}