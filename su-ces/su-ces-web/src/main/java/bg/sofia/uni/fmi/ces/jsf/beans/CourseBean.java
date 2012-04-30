package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.facade.course.CoursePersistence;

@ManagedBean(name = "courseBean")
@ViewScoped
public class CourseBean implements Serializable {

	private static final long serialVersionUID = -1373151780844385607L;

	private Course course;

	private CoursePersistence coursePersistence;

	public CourseBean() {
		coursePersistence = new CoursePersistence();

		// TODO getting course 1...when course searching is implemented this one
		// should be finished
		course = coursePersistence.geCourseById(1);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
