package com.effectivejava.scheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.effectivejava.scheduling.job.ScheduleConfiguration;

@SpringBootApplication
public class DynamicSchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicSchedulingApplication.class, args);
	}
	
	@Bean(initMethod = "createDynamicSchedules")
	public ScheduleConfiguration scheduleConfiguration() {
		return new ScheduleConfiguration();
		
	}

}
