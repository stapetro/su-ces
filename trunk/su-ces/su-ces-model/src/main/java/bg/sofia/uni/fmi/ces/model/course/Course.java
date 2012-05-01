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

}