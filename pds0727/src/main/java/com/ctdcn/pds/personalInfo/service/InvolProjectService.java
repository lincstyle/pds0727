package com.ctdcn.pds.personalInfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctdcn.pds.project.dao.ProjectUserDao;
import com.ctdcn.pds.project.model.ProjectUser;

@Service
public class InvolProjectService {

	@Autowired
	private ProjectUserDao projectUserDao;
	
	@Transactional
	public void editIsReceive(ProjectUser proUser) {
		projectUserDao.editIsReceive(proUser);
	}
	@Transactional
	public void editIsResponse(ProjectUser projectUser)
	{
		projectUserDao.editIsResponse(projectUser);
	}


}
