/*
 * Copyright (C) 2019, Liberty Mutual Group
 *
 * Created on Apr 3, 2019
 */

package com.fse.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.projectmanager.mapper.ProjectResponse;
import com.fse.projectmanager.mapper.TaskRequestResponse;
import com.fse.projectmanager.model.Parent;
import com.fse.projectmanager.model.Project;
import com.fse.projectmanager.model.User;
import com.fse.projectmanager.service.ProjectManagerService;

/**
 * @author n0172808
 *
 */
@RestController  //@Response body is default with this annotation
@RequestMapping("api/projectManager/")
public class ProjectManagerController {

	@Autowired
	private ProjectManagerService projectManagerService;
	
	@GetMapping(value="findAllTasks")
	public List<TaskRequestResponse> findAllTasks(){
		return projectManagerService.findAllTasks();
	}
	
	@GetMapping(value="findAllProjects")
	public List<ProjectResponse> findAllProjects(){
		return projectManagerService.findAllProjects();
	}
	
	@GetMapping(value="findAllUsers")
	public List<User> findAllUsers(){
		return projectManagerService.findAllUsers();
	}
	
	@GetMapping(value="getProjectList")
	public List<Project> getProjectList(){
		return projectManagerService.getProjectList();
	}
	
	@GetMapping(value="getParentList")
	public List<Parent> getParentList(){
		return projectManagerService.getParentList();
	}
	
	@GetMapping(value="getManagerList")
	public List<User> getManagerList(){
		return projectManagerService.getManagerList();
	}
	
	@PostMapping(value="addUser")
	public User addUser(@RequestBody User request){
		return projectManagerService.addUser(request);
	}
	
	@PostMapping(value="updateUser")
	public User updateUser(@RequestBody User request){
		return projectManagerService.updateUser(request);
	}
	
	@DeleteMapping(value="deleteUser/{id}")
	public void deleteUser(@PathVariable Long id){
		projectManagerService.deleteUser(id);
	}
	
	@PostMapping(value="addTask")
	public TaskRequestResponse addTask(@RequestBody TaskRequestResponse request){
		return projectManagerService.addTask(request);
	}
	
	@PostMapping(value="updateTask")
	public TaskRequestResponse updateTask(@RequestBody TaskRequestResponse request){
		return projectManagerService.updateTask(request);
	}
	
	
}
