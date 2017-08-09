package com.rz.demo.service;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import jone.R;
import jone.data.db.DB;
import jone.data.db.DBs;

public class SqlService
{
	static DB db = DBs.getDB("h2");

	public static void exec(String sql)
	{
		Reader reader = new BufferedReader(new StringReader(sql));
		db.runScript(reader);
	}

	public static List<R> getTableData(String table)
	{
		String sql = "SELECT * from " + table;
		return db.find(sql);
	}

	public static List<R> tree(String id, String level)
	{
		List<R> list = new ArrayList<R>();
		if (id == null)
		{
			R r = new R();
			r.put("id", 1);
			r.put("pid", 0);
			r.put("open", true);
			r.put("isParent", true);
			r.put("name", "H2");
			list.add(r);
		}
		else if ("1".equals(id))
		{
			R r = new R();
			r.put("id", 2);
			r.put("pid", 1);
			r.put("isParent", true);
			r.put("name", "表");
			list.add(r);
			R r2 = new R();
			r2.put("id", 3);
			r2.put("pid", 1);
			r2.put("isParent", true);
			r2.put("name", "视图");
			list.add(r2);
		}
		else if ("2".equals(id))
		{
			String sql = "SELECT table_name as name, table_name as id, 2 as pid FROM  information_schema.tables where table_schema='PUBLIC' and table_type='TABLE'";
			return db.find(sql);
		}
		else if ("3".equals(id))
		{
			String sql = "SELECT table_name as name, table_name as id, 2 as pid FROM  information_schema.tables where table_schema='PUBLIC' and table_type='VIEW'";
			return db.find(sql);
		}
		return list;
	}
}
