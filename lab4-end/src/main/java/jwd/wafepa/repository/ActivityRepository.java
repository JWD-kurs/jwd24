package jwd.wafepa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jwd.wafepa.model.Activity;

@Repository
public interface ActivityRepository 
	extends JpaRepository<Activity, Long> {
	
	
	List<Activity> findByName(String name);

//	@Query("select a from Activity a where a.name = :name")
//	List<Activity> findByName(@Param(value = "name") String name);
}
