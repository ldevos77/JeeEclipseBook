package packt.book.jee.eclipse.ch7.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import packt.book.jee.eclipse.ch7.dto.CourseDTO;
import packt.book.jee.eclipse.ch7.jpa.Course;

/**
 * Session Bean implementation class CourseBean
 */
@Stateless
@LocalBean
public class CourseBean implements CourseBeanRemote {

	@PersistenceContext(unitName="CourseManagementEJBs")
	EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public CourseBean() {
    }

	@Override
	public List<CourseDTO> getCourses() {
		// Get courseEntities first
		List<Course> courseEntities = getCoursesEntities();
		// Create list of ourses DTOs. This is the result we will return
		List<CourseDTO> courses = new ArrayList<CourseDTO>();
		for (Course courseEntity : courseEntities) {
			// Create CourseDTO from Course entity
			CourseDTO course = new CourseDTO();
			course.setId(courseEntity.getId());
			course.setName(courseEntity.getName());
			course.setCredits(course.getCredits());
			courses.add(course);
		}
		return courses;
	}
	
	public List<Course> getCoursesEntities() {
		TypedQuery<Course> courseQuery = entityManager.createNamedQuery("Course.findAll",Course.class);
		return courseQuery.getResultList();
	}

}
