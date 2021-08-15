package com.openopen;

import com.openopen.job.MyJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;



@SpringBootApplication
@ComponentScan(basePackages = {"com.openopen"})
@RestController
public class App {


    @Value("${APIENV}")
    private   String APIENV;



    @RequestMapping(value = "/")
    String hello() {

        return "Hello World!" + APIENV;
    }

    @RequestMapping(value = "/env")
    String getApiEnv() {
        return APIENV;
    }



    public static void main(String[] args) throws SchedulerException {






        SpringApplication.run(App.class, args);

        // [0] Grab the Scheduler instance from the Factory
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // and start it off
        scheduler.start();

        // [1] define the job
        JobDetail myJob = newJob(MyJob.class)
                .withIdentity("MyJob", "group1")
                .build();






        // [2] define the Trigger
        Trigger triggerMyJob = newTrigger()
                .withIdentity("triggerMyJob", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .forJob(myJob)
                .build();




        // [3] define the scheduleJob
        scheduler.scheduleJob(myJob, triggerMyJob);


    }


}