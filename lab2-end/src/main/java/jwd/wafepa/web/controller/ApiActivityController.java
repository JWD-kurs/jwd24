package jwd.wafepa.web.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jwd.wafepa.model.Activity;
import jwd.wafepa.service.ActivityService;

@RestController
@RequestMapping(value="/api/activities")
public class ApiActivityController {
	@Autowired
	private ActivityService activityService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Activity>> getActivities(){
		System.out.println("getActivities()");
		
		List<Activity> activities = activityService.findAll();
		
		return new ResponseEntity<>(activities,
				HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Activity> getActivity(
			@PathVariable Long id){
		System.out.println("getActivity(" + id + ")");
		
		Activity activity = activityService.findOne(id);
		
		if(activity == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(activity,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Activity> delete(
			@PathVariable Long id){
		System.out.println("delete(" + id + ")");
		
		Activity deleted = activityService.delete(id);
		
		return new ResponseEntity<>(deleted, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Activity> add(
			@RequestBody Activity postedActivity){
		
		Activity persisted =  activityService.save(postedActivity);
		
		return new ResponseEntity<Activity>(persisted,
				HttpStatus.CREATED);
	}
	
	@PostConstruct
	public void init(){
		activityService.save(new Activity("Swimming"));
		activityService.save(new Activity("Running"));
	}
}
