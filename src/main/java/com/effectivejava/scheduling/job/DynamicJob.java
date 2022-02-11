package com.effectivejava.scheduling.job;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.effectivejava.scheduling.controller.model.QueryModel;

@Component
public class DynamicJob {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	public void createSchedule(QueryModel queryModel) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(String.valueOf(queryModel.getId()), queryModel);
		JobKey jobKey = new JobKey(String.valueOf(queryModel.getId()));
		JobDetail job = JobBuilder.newJob(QueryJob.class).withIdentity(jobKey).setJobData(jobDataMap).build();
		Trigger trigger = createTrigger(queryModel);
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job,trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void updateScheduleJob(QueryModel queryModel) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey jobKey = new JobKey(String.valueOf(queryModel.getId()));	
			scheduler.deleteJob(jobKey);
			createSchedule(queryModel);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	private Trigger createTrigger(QueryModel queryModel) {
		return TriggerBuilder.newTrigger().withIdentity(String.valueOf(queryModel.getId()))
				.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever((int)(queryModel.getFrequency()/1000))).build();
	}

	public void deleteJob(QueryModel queryModel) {
			
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey jobKey = new JobKey(String.valueOf(queryModel.getId()));	
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
