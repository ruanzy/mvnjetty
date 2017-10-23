package com.rz.demo.plugin;

import java.io.InputStream;
import java.io.InputStreamReader;

import jone.data.db.DB;
import jone.data.db.DBs;
import jone.quartz.QuartzUtil;
import jone.web.Plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rz.demo.job.MyJob;
import com.rz.demo.job.MyJob2;

public class Initialize implements Plugin
{
	static final Logger logger = LoggerFactory.getLogger(Initialize.class);

	@Override
	public void start()
	{
		String table = "USERROLE";
		String sqlScript = "init.sql";
		String sql = "SELECT COUNT(*) FROM  information_schema.tables where table_schema='PUBLIC' and table_name=?";
		DB db = DBs.getDB("h2");
		try
		{
			long count = db.count(sql, new Object[] { table });
			if (count == 0)
			{
				logger.info("execute " + sqlScript + "...");
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(sqlScript);
				db.runScript(new InputStreamReader(is, "UTF8"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
//		QuartzUtil.start(db);
//		QuartzUtil.addJob("job1", "group", "*/5 * * * * ?", MyJob.class, null);
//		QuartzUtil.addJob("job2", "group", "*/8 * * * * ?", MyJob2.class, null);
	}

	@Override
	public void stop()
	{

	}
}
