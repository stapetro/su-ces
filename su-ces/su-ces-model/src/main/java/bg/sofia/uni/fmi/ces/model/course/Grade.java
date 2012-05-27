package bg.sofia.uni.fmi.ces.model.course;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the grade database table.
 * 
 */
@Entity
@Table(name = "grade")
public class Grade implements Serializable {
	private static final long serialVersionUID = 1L;
	private int gradeId;
	private int gradeNumber;
	private List<Course> courses;

	public Grade() {
	}

	@Id
	@SequenceGenerator(name = "GRADE_GRADEID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GRADE_GRADEID_GENERATOR")
	@Column(name = "grade_id", unique = true, nullable = false)
	public int getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	@Column(name = "grade_number", nullable = false)
	public int getGradeNumber() {
		return this.gradeNumber;
	}

	public void setGradeNumber(int gradeNumber) {
		this.gradeNumber = gradeNumber;
	}

	// bi-directional many-to-many association to Course
	@ManyToMany(mappedBy = "grades")
	public List<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public boolean equals(Object gradeObj) {
		if (gradeObj == null) {
			return false;
		}
		if (gradeObj instanceof Grade) {
			Grade grade = (Grade) gradeObj;
			return (this.gradeId == grade.gradeId && this.gradeNumber == grade.gradeNumber);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = this.gradeId ^ this.gradeNumber;
		return hashCode;
	}
}