package cl.coursesandstudents.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.coursesandstudents.models.Course;


@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
	
	List<Course> findAll();
	
}
