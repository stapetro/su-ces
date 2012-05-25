package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.facade.course.CoursePersistence;

@ManagedBean(name = "courseSearchBean")
@RequestScoped
public class CourseSearchBean extends SucesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8100419846989013656L;

	private int selectedCourseId;

	private List<Course> coursesList;

	/**
	 * Reference to the course persistence object used to managed work with the
	 * persistence entities and DB
	 */
	private CoursePersistence coursePersistence;

	public CourseSearchBean() {
		coursePersistence = new CoursePersistence();

		this.coursesList = coursePersistence.getAllCourses();
	}

	public void viewCourse() {
		this.redirectToCoursePage(false);
	}

	public void createNewCourse() {
		this.redirectToCoursePage(true);
	}

	private void redirectToCoursePage(boolean isNewCourse) {
		FacesContext currContext = FacesContext.getCurrentInstance();
		ExternalContext externalCxt = currContext.getExternalContext();

		try {
			String redirectUri = "course.xhtml";
			if (isNewCourse == false) {
				redirectUri += "?courseId=" + selectedCourseId;
			}
			externalCxt.redirect(redirectUri);
		} catch (IOException e) {
			getLogger().error(e);
		}
	}

	// ================ PROPERTY GETTERS AND SETTERS ===================

	public int getSelectedCourseId() {
		return selectedCourseId;
	}

	public void setSelectedCourseId(int selectedCourseId) {
		this.selectedCourseId = selectedCourseId;
	}

	public List<Course> getCoursesList() {
		return coursesList;
	}

	public void setCoursesList(List<Course> coursesList) {
		this.coursesList = coursesList;
	}
}
