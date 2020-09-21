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

import cl.coursesandstudents.models.Student;
import cl.coursesandstudents.services.StudentService;

@RestController
@RequestMapping("/students")
public class StudentApiController {
	
	@Autowired
	private StudentService studentService;
	
	//Paged List or students, 10 rows per page
	@GetMapping(value="/", produces={"application/json"})
	public ResponseEntity<Page<Student>> findAllPaged(@PageableDefault(size=10, page=0) Pageable pageable){
		Page<Student> students = studentService.findAllPaged(pageable);		
		if(students == null) {
			return new ResponseEntity<Page<Student>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Page<Student>>(students, HttpStatus.OK);
	}
	
	//List with all the rows for students
	@GetMapping(value="/all", produces={"application/json"})
	public ResponseEntity<List<Student>> findAll(){		
		List<Student> students = studentService.findAll();
		if(students == null) {
			return new ResponseEntity<List<Student>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
	}
	
	//Find and return a student by id
	@GetMapping(value="/{id}", produces={"application/json"})
	public ResponseEntity<Student> findStudent(@PathVariable("id") Long id) {
		Student student = studentService.findStudent(id);
		if(student == null) {
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}
	
	//Create a student
    @PostMapping(value="/", consumes={"application/json"})
    public ResponseEntity<Student> createStudent(@RequestBody Student st) { 
    	if(st.getRut().equals(" ") || st.getFirstName().equals(" ") || st.getLastName().equals(" ") || st.getAge() == null) {
    		return new ResponseEntity<Student>(HttpStatus.BAD_REQUEST);
    	}
    	if(st.getRut().equals(" ") || st.getFirstName().equals(" ") || st.getLastName().equals(" ") || st.getAge() == null) {
    		return new ResponseEntity<Student>(HttpStatus.BAD_REQUEST);
    	}    	
    	if(studentService.verifyNumbers(st.getFirstName()) || studentService.verifyNumbers(st.getLastName())) {
    		return new ResponseEntity<Student>(HttpStatus.BAD_REQUEST);
    	}
    	if(!studentService.validateRut(st.getRut())) {
    		return new ResponseEntity<Student>(HttpStatus.BAD_REQUEST);
    	}
    	String fRut = studentService.formatRut(st.getRut());
        Student nStudent = new Student(fRut,st.getFirstName(),st.getLastName(),st.getAge());
        studentService.createStudent(nStudent);        
        return new ResponseEntity<Student>(nStudent, HttpStatus.CREATED);
    }
    
    //Edit a student information
    @PutMapping(value="/{id}", consumes={"application/json"})
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student st) {
    	Student student = new Student();
    	if(st.getRut().equals(" ") || st.getFirstName().equals(" ") || st.getLastName().equals(" ") || st.getAge() == null) {
    		return new ResponseEntity<Student>(student, HttpStatus.BAD_REQUEST);
    	}    	
    	if(studentService.verifyNumbers(st.getFirstName()) || studentService.verifyNumbers(st.getLastName())) {
    		return new ResponseEntity<Student>(student, HttpStatus.BAD_REQUEST);
    	}
        Student mStudent = studentService.updateStudent(id, st.getRut(), st.getFirstName(), st.getLastName(), st.getAge());
        if(mStudent == null) {
        	return new ResponseEntity<Student>(student, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Student>(mStudent, HttpStatus.OK);
    }
    
    //Delete a student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
    	Boolean veri = studentService.deleteStudent(id);
    	if(veri == false) {
    		return new ResponseEntity<String>("Student Not Found", HttpStatus.NOT_FOUND);
    	}
        return new ResponseEntity<String>("Sucess...! Student Deleted", HttpStatus.OK);
    }

}
