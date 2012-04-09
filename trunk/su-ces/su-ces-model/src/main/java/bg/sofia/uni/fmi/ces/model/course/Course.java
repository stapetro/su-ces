package bg.sofia.uni.fmi.ces.model.course;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the courses database table.
 * 
 */
@Entity
@Table(name="courses")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	private int courseId;
	private List<CourseAssessment> courseAssessments;

    public Course() {
    }


	@Id
	@SequenceGenerator(name="COURSES_COURSEID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COURSES_COURSEID_GENERATOR")
	@Column(name="course_id", unique=true, nullable=false)
	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}


	//bi-directional many-to-one association to CourseAssessment
	@OneToMany(mappedBy="cours")
	public List<CourseAssessment> getCourseAssessments() {
		return this.courseAssessments;
	}

	public void setCourseAssessments(List<CourseAssessment> courseAssessments) {
		this.courseAssessments = courseAssessments;
	}
	
}