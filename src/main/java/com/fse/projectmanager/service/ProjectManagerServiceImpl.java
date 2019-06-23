/*
 * Copyright (C) 2019, Liberty Mutual Group
 *
 * Created on Apr 3, 2019
 */

package com.fse.projectmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.projectmanager.mapper.ProjectResponse;
import com.fse.projectmanager.mapper.TaskRequestResponse;
import com.fse.projectmanager.model.Parent;
import com.fse.projectmanager.model.Project;
import com.fse.projectmanager.model.Task;
import com.fse.projectmanager.model.User;
import com.fse.projectmanager.repository.ParentTaskJpaRepository;
import com.fse.projectmanager.repository.ProjectJpaRepository;
import com.fse.projectmanager.repository.TaskJpaRepository;
import com.fse.projectmanager.repository.UserJpaRepository;

/**
 * @author n0172808
 *
 */
@Service
public class ProjectManagerServiceImpl implements ProjectManagerService {

	@Autowired
	private TaskJpaRepository taskJpaRepository;

	@Autowired
	private ParentTaskJpaRepository parentTaskJpaRepository;

	@Autowired
	private ProjectJpaRepository projectJpaRepository;

	@Autowired
	private UserJpaRepository userJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fse.projectmanager.service.ProjectManagerService#addUser(com.fse.
	 * projectmanager.mapper.UserObject)
	 */
	@Override
	@Transactional
	public User addUser(User request) {
		User user = new User();

		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmployeeId(request.getEmployeeId());
		user.setManager(request.isManager());

		return userJpaRepository.saveAndFlush(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fse.projectmanager.service.ProjectManagerService#deleteUser(java.lang
	 * .Long)
	 */
	@Override
	@Transactional
	public void deleteUser(Long id) {
		userJpaRepository.deleteById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fse.projectmanager.service.ProjectManagerService#findAllProjects()
	 */
	@Override
	public List<ProjectResponse> findAllProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fse.projectmanager.service.ProjectManagerService#findAllTasks()
	 */
	@Override
	public List<TaskRequestResponse> findAllTasks() {

		// Map Entity to required JSON payload
		List<Task> tasks = new ArrayList<>();
		tasks = taskJpaRepository.findAll();

		List<TaskRequestResponse> taskResps = new ArrayList<>();

		for (Task task : tasks) {

			TaskRequestResponse taskResp = taskResponseMapper(task);

			taskResps.add(taskResp);
		}

		return taskResps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fse.projectmanager.service.ProjectManagerService#findAllUsers()
	 */
	@Override
	public List<User> findAllUsers() {
		return userJpaRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fse.projectmanager.service.ProjectManagerService#getManagerList()
	 */
	@Override
	public List<User> getManagerList() {
		return userJpaRepository.findByManager(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fse.projectmanager.service.ProjectManagerService#getParentList()
	 */
	@Override
	public List<Parent> getParentList() {
		return parentTaskJpaRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fse.projectmanager.service.ProjectManagerService#getProjectList()
	 */
	@Override
	public List<Project> getProjectList() {
		return projectJpaRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fse.projectmanager.service.ProjectManagerService#getUserList()
	 */
	@Override
	public List<String> getUserList() {
		List<User> users = new ArrayList<>();
		users = userJpaRepository.findAll();

		List<String> userList = new ArrayList<>();

		for (User user : users) {
			userList.add(user.getFirstName() + ' ' + user.getLastName());
		}

		return userList;
	}

	/**
	 * @param task
	 * @return
	 */
	private TaskRequestResponse taskResponseMapper(Task task) {
		TaskRequestResponse taskResp = new TaskRequestResponse();

		taskResp.setId(task.getTaskId());
		taskResp.setTask(task.getTask());
		taskResp.setStartDate(task.getStartDate());
		taskResp.setEndDate(task.getEndDate());
		taskResp.setPriority(task.getPriority());
		taskResp.setParentId(task.getParent().getParentId());
		taskResp.setParentTask(task.getParent().getParentTask());
		taskResp.setProjectId(task.getProject().getProjectId());
		taskResp.setProject(task.getProject().getProject());
		taskResp.setProjectStartDate(task.getProject().getStartDate());
		taskResp.setProjectEndDate(task.getProject().getEndDate());
		taskResp.setProjectPriority(task.getProject().getPriority());
		taskResp.setUserId(task.getUser().getUserId());
		taskResp.setFirstName(task.getUser().getFirstName());
		taskResp.setLastName(task.getUser().getLastName());
		taskResp.setEmployeeId(task.getUser().getEmployeeId());
		return taskResp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fse.projectmanager.service.ProjectManagerService#updateUser(java.lang
	 * .Long, com.fse.projectmanager.mapper.UserObject)
	 */
	@Override
	@Transactional
	public User updateUser(User request) {
		Optional<User> fetchuser = userJpaRepository.findById(request.getUserId());
		User user = new User();

		if (fetchuser.isPresent()) {
			user = fetchuser.get();
		}

		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmployeeId(request.getEmployeeId());
		user.setManager(request.isManager());

		return userJpaRepository.save(user);
	}

	/* (non-Javadoc)
	 * @see com.fse.projectmanager.service.ProjectManagerService#addTask(com.fse.projectmanager.mapper.TaskRequestResponse)
	 */
	@Override
	@Transactional
	public TaskRequestResponse addTask(TaskRequestResponse request) {
		Task task = new Task();
		
		task.setTask(request.getTask());
		task.setStartDate(request.getStartDate());
		task.setEndDate(request.getEndDate());
		task.setPriority(request.getPriority());
		if(request.getParentId() != 0){
			task.setParent(parentTaskJpaRepository.getOne(request.getParentId()));
		}
		task.setProject(projectJpaRepository.getOne(request.getProjectId()));
		task.setUser(userJpaRepository.getOne(request.getUserId()));
		
		return taskResponseMapper(taskJpaRepository.saveAndFlush(task));
	}

	/* (non-Javadoc)
	 * @see com.fse.projectmanager.service.ProjectManagerService#updateTask(com.fse.projectmanager.mapper.TaskRequestResponse)
	 */
	@Override
	@Transactional
	public TaskRequestResponse updateTask(TaskRequestResponse request) {
		Task task = new Task();
		
		task.setTask(request.getTask());
		task.setStartDate(request.getStartDate());
		task.setEndDate(request.getEndDate());
		task.setPriority(request.getPriority());
		if(request.getParentId() != 0){
			task.setParent(parentTaskJpaRepository.getOne(request.getParentId()));
		}
		task.setProject(projectJpaRepository.getOne(request.getProjectId()));
		task.setUser(userJpaRepository.getOne(request.getUserId()));
		
		return taskResponseMapper(taskJpaRepository.save(task));
	}
	
	

}
