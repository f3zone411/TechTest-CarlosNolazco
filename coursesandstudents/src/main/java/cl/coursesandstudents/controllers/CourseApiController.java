package cl.coursesandstudents.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.coursesandstudents.models.Course;
import cl.coursesandstudents.models.Student;
import cl.coursesandstudents.services.CourseService;
import cl.coursesandstudents.services.StudentService;


@RestController
@RequestMapping("/courses")
public class CourseApiController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	//Paged List of courses, 10 rows per page
	@GetMapping(value="/", produces={"application/json"})
	public ResponseEntity<Page<Course>> findAllPaged(@PageableDefault(size=10, page=0) Pageable pageable){
		Page<Course> courses = courseService.findAllPaged(pageable);	
		if(courses == null) {
			return new ResponseEntity<Page<Course>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Page<Course>>(courses, HttpStatus.OK);
	}
	
	//List with all the rows for courses
	@GetMapping(value="/all", produces={"application/json"})
	public ResponseEntity<List<Course>> findAll(){		
		List<Course> courses = courseService.findAll();
		if(courses == null) {
			return new ResponseEntity<List<Course>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
	}	
	
	//Find and return a course by id
	@GetMapping(value="/{id}", produces={"application/json"})
	public ResponseEntity<Course> findCourse(@PathVariable("id") Long id) {
		Course course = courseService.findCourse(id);
		if(course == null) {
			return new ResponseEntity<Course>(course, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Course>(course, HttpStatus.OK);
	}
	
	//Create a course
    @PostMapping(value="/", consumes={"application/json"})
    public ResponseEntity<Course> create(@RequestBody Course co) {
    	if(co.getName().equals(" ") || co.getCode().equals(" ")) {
    		return new ResponseEntity<Course>(HttpStatus.BAD_REQUEST);
    	}
        Course course = new Course(co.getName(), co.getCode());
        courseService.createCourse(course);
        return new ResponseEntity<Course>(course, HttpStatus.CREATED);
    }
    
    //Add a student to an existing course
    @PostMapping("/addstudent/{idStudent}/{idCourse}")
    public ResponseEntity<String> addStudent(
    		@PathVariable(value="idStudent") Long idStudent,
    		@PathVariable(value="idCourse") Long idCourse    		
    ){
    	Course c = courseService.findCourse(idCourse);
    	Student s = studentService.findStudent(idStudent);
		if(c == null) {
			return new ResponseEntity<String>("No se encontro el curso", HttpStatus.NOT_FOUND);
		}
		if(s == null) {
			return new ResponseEntity<String>("No se encontro el estudiante", HttpStatus.NOT_FOUND);
		}
		if(studentService.validCourse(s, c)) {
			return new ResponseEntity<String>("El estudiante ya pertenece a este curso", HttpStatus.BAD_REQUEST);
		}
		studentService.addStudent(s, c);
		return new ResponseEntity<String>("Se agrego el estudiante al curso", HttpStatus.OK);		
    }
    
    //Edit a course information
    @PutMapping(value="/{id}", consumes={"application/json"})
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Long id, @RequestBody Course co) {
        Course course = courseService.updateCourse(id, co.getName(), co.getCode());        
        if(course == null) {
        	return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Course>(course, HttpStatus.OK);
    }
    
    //Delete a course
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id) {
    	Boolean veri = courseService.deleteCourse(id);
    	if(veri == false) {
    		return new ResponseEntity<String>("Course Not Found", HttpStatus.NOT_FOUND);
    	}
        return new ResponseEntity<String>("Sucess...! Course Deleted", HttpStatus.OK);        
    }

}
