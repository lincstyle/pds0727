package com.ctdcn.pds.weixin.utils;


import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ctdcn.pds.project.model.ProjectLog;
import com.ctdcn.pds.project.service.ProjectLogService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpMessage;

/**
 * 微信日志推送非阻塞线程池方式
 * @author daixiaopan
 *
 */
public class WxSendLogtaskExecutor implements Runnable{

	private WxCpMessage wxCpMessage;
	private ProjectLog projectLog;
    
		
	WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
	private WxCpServiceImpl wxCpService = (WxCpServiceImpl)wac.getBean("wxCpService");
	private ProjectLogService projectLogService = (ProjectLogService)wac.getBean("projectLogService");
	private SchedulerFactoryBean schedulerFactory = (SchedulerFactoryBean)wac.getBean("&schedulerFactory");
	private SimpleTriggerFactoryBean simpleTrigger = (SimpleTriggerFactoryBean)wac.getBean("&simpleTrigger");
	private CronTriggerFactoryBean zntaskTrigger = (CronTriggerFactoryBean)wac.getBean("&zntaskTrigger");

	public WxSendLogtaskExecutor(WxCpMessage wxCpMessage,ProjectLog projectLog) {
		this.wxCpMessage = wxCpMessage;
		this.projectLog = projectLog;
	}
	
	public void run() {
		try {
			wxCpService.messageSend(wxCpMessage);
		} catch (Exception e) {
			ProjectLog updateLog = new ProjectLog();
			updateLog.setIsSuccess(0);
			projectLogService.updatePrLog(updateLog, projectLog);
			try {
				String state = schedulerFactory.getScheduler().getTriggerState(simpleTrigger.getObject().getKey()).name();
				if(state.equals("PAUSED")){//如果短定时任务为暂停状态，则立即启动，并重启长定时任务；否则不做任何操作
					schedulerFactory.getScheduler().pauseTrigger(zntaskTrigger.getObject().getKey());
					schedulerFactory.getScheduler().resumeTrigger(zntaskTrigger.getObject().getKey());
					schedulerFactory.getScheduler().resumeTrigger(simpleTrigger.getObject().getKey());
				}
			} catch (SchedulerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//e.printStackTrace();
		} 
	}
}
