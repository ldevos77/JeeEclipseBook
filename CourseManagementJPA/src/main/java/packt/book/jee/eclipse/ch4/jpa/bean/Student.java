package packt.book.jee.eclipse.ch4.jpa.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@ManagedBean(name="student")
@RequestScoped
@Entity
public class Student extends Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	@Column(name="enrolled_since")
	private Date enrolledSince;
	
	@ManyToMany(cascade = { MERGE, REFRESH })
	@JoinTable(name = "Course_Student", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
	private List<Course> courses;

	public Date getEnrolledSince() {
		return enrolledSince;
	}

	public void setEnrolledSince(Date enrolledSince) {
		this.enrolledSince = enrolledSince;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	

}
