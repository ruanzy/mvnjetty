package com.rz.demo.plugin;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rz.data.db.DB;
import com.rz.data.db.DBs;
import com.rz.web.Plugin;

public class Initialize implements Plugin
{
	static final Logger logger = LoggerFactory.getLogger(Initialize.class);

	@Override
	public void start()
	{
		String table = "USERROLE";
		String sqlScript = "init.sql";
		String sql = "SELECT COUNT(*) FROM  information_schema.tables where table_schema='PUBLIC' and table_name=?";
		try
		{
			DB db = DBs.getDB("h2");
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
	}

	@Override
	public void stop()
	{

	}
}
