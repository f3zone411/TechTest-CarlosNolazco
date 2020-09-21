package cl.coursesandstudents.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cl.coursesandstudents.models.Course;
import cl.coursesandstudents.repositories.CourseRepo;


@Service
public class CourseService {
	
	@Autowired
	private CourseRepo courseRepo;
	
	//Returns a list with all the students in the database
	public List<Course> findAll(){
		return courseRepo.findAll();
	}
	
	//Returns a paged list based in the params given in the pageable object
	public Page<Course> findAllPaged(Pageable pageable){
		return courseRepo.findAll(pageable);
	}
	
	//Returns a course by his id if its exists in the database
	public Course findCourse(Long id) {
		Optional<Course> opt = courseRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	//Save a new course in the database with the object given in the params
	public Course createCourse(Course c) {
		return courseRepo.save(c);		
	}
	
	//Update the information of a course with the new information
	public Course updateCourse(Long id, String name, String code) {
		Course course = findCourse(id);
		if(course == null) {
			return null;
		}
		course.setName(name);
		course.setCode(code);
		return courseRepo.save(course);
	}
	
	//Delete a student using the id given
	public boolean deleteCourse(Long id) {
		boolean flag = false;
		Course c = findCourse(id);
		if(c == null) {
			return flag;
		}		
		c.getStudents().clear();
		courseRepo.deleteById(id);
		c = findCourse(id);
		if(c == null) {
			return flag = true;
		}
		return flag;	
	}
}
