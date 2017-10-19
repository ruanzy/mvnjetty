package com.rz.demo.job;

import java.util.List;

import jone.R;
import jone.quartz.QuartzUtil;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;

public class MyJob implements Job
{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		System.out.println("asdads");
		List<R> list = QuartzUtil.getAllJob();
		System.out.println(JSON.toJSONString(list));
	}

}
