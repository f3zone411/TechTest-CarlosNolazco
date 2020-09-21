package cl.coursesandstudents.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cl.coursesandstudents.models.Course;
import cl.coursesandstudents.models.Student;
import cl.coursesandstudents.repositories.StudentRepo;


@Service
public class StudentService {
	
	@Autowired
	private StudentRepo studentRepo;
	
	//Returns a list with all the students in the database
	public List<Student> findAll(){
		return studentRepo.findAll();
	}
	
	//Returns a paged list based in the params given in the pageable object
	public Page<Student> findAllPaged(Pageable pageable){
		return studentRepo.findAll(pageable);
	}
	
	//Returns a student by his id if its exists in the database
	public Student findStudent(Long id) {
		Optional<Student> opt = studentRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	//Save a new student in the database with the object given in the params
	public Student createStudent(Student s) {
		return studentRepo.save(s);		
	}
	
	//Validate if the course is already in the courses list of a given student
	public boolean validCourse(Student s, Course c) {
		for (int i = 0; i < s.getCourses().size(); i++) {
			if(s.getCourses().get(i).getId() == c.getId()) {
				return true;
			}
		}
		return false;
	}
	
	//Add a course given in the params to a student also given in the params
	public Student addStudent(Student s, Course c) {
		s.getCourses().add(c);
		return studentRepo.save(s);		
	}
	
	//Update the information of a student with the new information
	public Student updateStudent(Long id, String rut, String firstName, String lastName, Integer age) {
		Student student = findStudent(id);
		if(student == null) {
			return null;
		}
		student.setRut(rut);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setAge(age);
		return studentRepo.save(student);
	}
	
	//Delete a student using the id given
	public boolean deleteStudent(Long id) {
		boolean flag = false;
		Student s = findStudent(id);
		if(s == null) {
			return flag;
		}
		s.getCourses().clear();
		studentRepo.deleteById(id);
		s = findStudent(id);
		if(s == null) {
			return flag = true;
		}
		return flag;		
	}
	
	//Verify if a string given has a numeric character
	public boolean verifyNumbers(String s) {
		char[] chArr = s.toCharArray(); 
		boolean flag = false;
		for(int i = 0; i < chArr.length; i++) {
			if(Character.isDigit(chArr[i])) {
				return flag = true;
			}
		}
		return flag;
	}
	
	//Verify if a string given has a alphabetic character
	public boolean verifyAlphab(String s) {
		char[] chArr = s.toCharArray(); 
		boolean flag = false;
		for(int i = 0; i < chArr.length; i++) {
			if(!Character.isDigit(chArr[i])) {
				return flag = true;
			}
		}
		return flag;
	}
	
	//Validate the rut
	public boolean validateRut(String s) {
		char[] chArr = s.toCharArray();
		int serie = 2;
		int acum = 0;
		char vDigit = chArr[chArr.length-1];
		for(int i = chArr.length-2; i >= 0; i--) {
			if(!Character.isDigit(chArr[i])){
				return false;
			}
			if(Character.isDigit(chArr[i])) {
				acum = acum + (serie * Character.getNumericValue(chArr[i]));
				if (serie == 7) {
					serie = 2;
				}else {
					serie++;
				}				
			}			
		}
		int digit = 11 - (acum % 11);
		if(digit == 10) {
			if (Character.compare(vDigit, 'K') == 0) {
				return true;
			}
		}else if(digit == 11) {
			if (Character.compare(vDigit, '0') == 0) {
				return true;
			}
		}else {
			if (Character.getNumericValue(vDigit) == digit) {
				return true;
			}
		}		
		return false;
	}
	
	//Format Rut, to save it with xxxxxxxx-x format
	public String formatRut(String s) {
		char[] chArr = s.toCharArray();
		String fRut = "";
		for(int i = 0; i < chArr.length; i++) {
			if (i == chArr.length-1) {
				fRut =  fRut + "-";
			}
			fRut = fRut + chArr[i];
		}
		return fRut;
	}
	
}
