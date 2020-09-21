package cl.coursesandstudents.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.coursesandstudents.models.Student;


@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
	
	List<Student> findAll();

}
