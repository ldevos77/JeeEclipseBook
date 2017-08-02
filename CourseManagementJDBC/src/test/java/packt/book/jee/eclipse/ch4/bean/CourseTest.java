package packt.book.jee.eclipse.ch4.bean;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CourseTest extends TestCase {

	public void testIsValidCourse() {
		Course course = new Course();
		// First validate without any values set
		Assert.assertFalse(course.isValidCourse());
		// Set Name
		course.setName("course1");
		Assert.assertFalse(course.isValidCourse());
		// Set zero credits
		course.setCredits(0);
		Assert.assertFalse(course.isValidCourse());
		// Now set valid credits
		course.setCredits(4);
		Assert.assertTrue(course.isValidCourse());
		// Set empty course name
		course.setName("");
		Assert.assertFalse(course.isValidCourse());
	}
	
	public void testAddStudent() {
		// Create Course
		Course course = new Course();
		course.setId(1);
		course.setName("course1");
		course.setMaxStudents(2);
		// Create Student
		Student student = new Student();
		student.setFirstName("Student1");
		student.setId(1);
		// Now add student
		try {
			course.addStudent(student);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
