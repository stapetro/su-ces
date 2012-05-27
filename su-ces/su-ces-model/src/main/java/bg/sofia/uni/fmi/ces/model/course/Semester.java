package bg.sofia.uni.fmi.ces.model.course;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the semester database table.
 * 
 */
@Entity
@Table(name = "semester")
public class Semester implements Serializable {
	private static final long serialVersionUID = 1L;
	private int semesterId;
	private String semesterName;
	private List<Course> courses;

	public Semester() {
	}

	@Id
	@SequenceGenerator(name = "SEMESTER_SEMESTERID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEMESTER_SEMESTERID_GENERATOR")
	@Column(name = "semester_id", unique = true, nullable = false)
	public int getSemesterId() {
		return this.semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	@Column(name = "semester_name", nullable = false, length = 255)
	public String getSemesterName() {
		return this.semesterName;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	// bi-directional many-to-one association to Course
	@OneToMany(mappedBy = "semester")
	public List<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public boolean equals(Object semesterObj) {
		if (semesterObj == null) {
			return false;
		}
		if (semesterObj instanceof Semester) {
			Semester semester = (Semester) semesterObj;
			return this.semesterId == semester.semesterId
					&& ((this.semesterName == null && semester.semesterName == null) || (this.semesterName != null
							&& semester.semesterName != null && this.semesterName
								.equals(semester.semesterName)));
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = this.semesterId;
		if (this.semesterName != null) {
			hashCode &= this.semesterName.hashCode();
		}
		return hashCode;
	}

}