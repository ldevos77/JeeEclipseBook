package packt.book.jee.eclipse.ch4.jpa.service_bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import packt.book.jee.eclipse.ch4.jpa.bean.Course;
import packt.book.jee.eclipse.ch4.jpa.service.CourseService;

@ManagedBean(name="courseServiceBean")
@RequestScoped
public class CourseServiceBean {
	
	/*
	 * LDE - Comment
	 * @ManagedProperty : emFactoryBean is injected by
	 * JSF Runtime
	 */
	@ManagedProperty(value="#{course}")
	private Course course;
	
	private CourseService courseService;
	
	/*
	 * LDE - Comment
	 * @ManagedProperty : emFactoryBean is injected by
	 * JSF Runtime
	 */
	@ManagedProperty(value="#{emFactoryBean}")
	private EntityManagerFactoryBean factoryBean;
	
	private String errMsg=null;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public String addCourse() {
		courseService.addCourse(course);
		return "listCourse";
	}
	
	/*
	 * LDE - Comment
	 * @PostConstruct : needed because the factoryBean
	 * have to be fully constructed by JSF Runtime before it
	 * can be used by method
	 */
	@PostConstruct
	public void init() {
		courseService = new CourseService(factoryBean);
	}
	
	public List<Course> getCourses() {
		return courseService.getCourses();
	}

	/* 
	 * Setter method is required for the factoryBean to be injected
	 * by JSF Runtime
	*/
	public void setFactoryBean(EntityManagerFactoryBean factoryBean) {
		this.factoryBean = factoryBean;
	}
}
