package packt.jee.eclipse.rest.ws.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import packt.jee.eclipse.rest.ws.dto.Course;
import packt.jee.eclipse.rest.ws.dto.Teacher;

@Path("/course")
public class CourseService {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("get/{CourseId}")
	public Course getCourseXML(@PathParam("CourseId") int id) {
		// To keep the example simple, we will return 
		// hardcoded values here. However, you could get
		// data from database using, for example, JDO or JDBC
		return new Course(id,"Course-"+id,5,new Teacher(2,"Teacher1"));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("get/{CourseId}")
	public Course getCourseJSON(@PathParam("CourseId") int id) {
		// To keep the example simple, we will return 
		// hardcoded values here. However, you could get
		// data from database using, for example, JDO or JDBC
		return createDummyCourse(id);
	}
	
	private Course createDummyCourse(int id) {
		// To keep the example simple, we will return 
		// hardcoded values here. However, you could get
		// data from database using, for example, JDO or JDBC
		return new Course(id,"Course-"+id,5,new Teacher(2,"Teacher1"));
	}
	
}
