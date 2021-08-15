package com.openopen.job;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openopen.http.Http;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyJob  implements Job {

    private static final Logger logger = LoggerFactory.getLogger(MyJob.class);

    Http http = new Http();

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");//設定日期格式
//        System.out.println(df.format(new Date()));// new Date()為獲取當前系統時間
//        System.out.println("MyJob Quartz!!!");


        logger.info("MyJob" + df.format(new Date()));


        List<JsonObject> list = new ArrayList<JsonObject>();
        JsonObject obj = new JsonObject();
        obj.addProperty("ac", Integer.parseInt(df.format(new Date())));
        obj.addProperty("userCreate", "OPENOPEN");


        list.add(obj);

        String json = new Gson().toJson(list);

        String url = "https://spring-boot-mybatis-druid-afu5qzwidq-de.a.run.app/api/add/tr001";
        http.sendPost(url,json);

        logger.info("==> send " + Integer.parseInt(df.format(new Date())) + " OK");








    }
}
