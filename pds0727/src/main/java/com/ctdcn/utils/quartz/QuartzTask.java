package com.ctdcn.utils.quartz;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.organization.service.UserService;
import com.ctdcn.pds.project.model.ProjectLog;
import com.ctdcn.pds.project.model.ProjectUser;
import com.ctdcn.pds.project.service.ProjectLogService;
import com.ctdcn.pds.project.service.ProjectUserService;
import com.ctdcn.pds.weixin.service.WxMessageService;

public class QuartzTask {
	@Autowired
    private ProjectLogService projectLogService;
    @Autowired
    private WxMessageService wxMessageService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private SchedulerFactoryBean schedulerFactory;
    @Autowired
    private SimpleTriggerFactoryBean simpleTrigger;
    
    private static int count = 0;
    
	public void taskRun() {
			try {
				//System.out.println("----------------------------------定时任务1已开始---------------------------------------");
				ProjectLog projectLog = new ProjectLog();
				projectLog.setIsdelete(0);
				projectLog.setIsSuccess(0);
				// 查询未删除且未发送成功的的日志
				List<ProjectLog> prLogList = projectLogService.queryPrLog(projectLog);
				count++;
				if (prLogList.isEmpty() || count == 4) {//没有发送失败和日志或者循环第4次停止
					count=0;
					schedulerFactory.getScheduler().pauseTrigger(simpleTrigger.getObject().getKey());
					//System.out.println("----------------------------------定时任务1已停止---------------------------------------");
				}else{
					// 循环读取每一条日志，加入日志发送队列
					Integer pid = null;
					for (ProjectLog prLog : prLogList) {
						pid = prLog.getPid();
						// 获取 该项目 有联系的用户
						ProjectUser projectUser = new ProjectUser();
						projectUser.setPid(pid);
						projectUser.setIsreceive(1);
						List<ProjectUser> prUserList = projectUserService
								.queryAllProjectUser(projectUser);
						String[] userid_array = new String[prUserList.size()];
						for (int i = 0; i < prUserList.size(); i++) {
							Integer id = prUserList.get(i).getUserid();
							User user1 = userService.getUserById(id);
							userid_array[i] = user1.getAccount();
						}
						ProjectLog prLog_update = new ProjectLog();
						prLog_update.setIsSuccess(1);// 默认设置为发送成功
						projectLogService.updatePrLog(prLog_update, prLog);
						wxMessageService.sendProjectLog(prLog, null, userid_array);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	
	public void timerController(){
		//System.out.println("----------------------------------定时任务2已开始---------------------------------------");
		ProjectLog projectLog = new ProjectLog();
		projectLog.setIsdelete(0);
		projectLog.setIsSuccess(0);
		// 查询未删除且未发送成功的的日志
		List<ProjectLog> prLogList = projectLogService
				.queryPrLog(projectLog);
		try {
			String state = schedulerFactory.getScheduler().getTriggerState(simpleTrigger.getObject().getKey()).name();
			if(!prLogList.isEmpty() && state.equals("PAUSED")){
				schedulerFactory.getScheduler().resumeTrigger(simpleTrigger.getObject().getKey());
				//System.out.println("----------------------------------定时任务1将重新开始---------------------------------------");
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
