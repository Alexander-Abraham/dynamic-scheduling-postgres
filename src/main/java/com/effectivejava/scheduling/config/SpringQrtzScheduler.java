package com.effectivejava.scheduling.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@EnableAutoConfiguration
public class SpringQrtzScheduler {
	
	@Autowired
    DataSource dataSource;
 
    @Autowired
    private ApplicationContext applicationContext;
     
    @Autowired
    private TriggerListner triggerListner;

    @Autowired
    private JobsListener jobsListener;
    
    /**
     * create scheduler
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
 
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setQuartzProperties(quartzProperties());
        
        //Register listeners to get notification on Trigger misfire etc
        //factory.setGlobalTriggerListeners(triggerListner);
       // factory.setGlobalJobListeners(jobsListener);
        
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        factory.setJobFactory(jobFactory);
        
        return factory;
    }
 
    /**
     * Configure quartz using properties file
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("application.yml"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

}
