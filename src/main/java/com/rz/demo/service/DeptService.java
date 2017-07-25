package com.rz.demo.service;

import java.util.List;
import com.rz.common.R;
import com.rz.data.db.DB;
import com.rz.data.db.DBs;

public class DeptService
{
	static DB db = DBs.getDB("h2");

	public static List<R> tree()
	{
		String sql = "SELECT id, name, pid, deep, true as open, isParent FROM dept";
		List<R> list = db.find(sql);
		for (R r : list)
		{
			r.put("isParent", r.getInt("isparent") == 1);
		}
		return list;
	}

	public static void update(R r)
	{
		String sql = "update dept set name=? where id=?";
		db.update(sql, r.getString("name"), r.getString("id"));
	}

	public static void add(R r)
	{
		String sql = "insert into dept(name, deep, pid, isparent) values(?,?,?,?)";
		db.update(sql, r.getString("name"), r.getInt("plevel") + 1, r.getString("pid"), r.getString("isparent"));
	}

	public static List<R> getPermission(String id)
	{
		String sql = "select r.*, exists (select 1 from deptres where deptid=? and resid=r.id) as checked from resources r";
		List<R> list = db.find(sql, id);
		return list;
	}

	public static void setPermission(String deptid, String permissions)
	{
		String[] arr = permissions.split(",");
		String sql1 = "delete from deptres where deptid = ?";
		String sql2 = "insert into deptres values(?, ?)";
		db.begin();
		db.update(sql1, new Object[] { deptid });
		for (String str : arr)
		{
			db.update(sql2, new Object[] { deptid, str });
		}
		db.close();
	}

	public static int del(String id)
	{
		int code = 0;
		String sql = "select count(1) from users where depart in (select id from dept where pid=? or id=?)";
		long c = db.count(sql, id, id);
		if (c > 0)
		{
			return 20001;
		}
		try
		{
			String sql1 = "delete from deptres where deptid = ?";
			String sql2 = "delete from dept where id = ?";
			db.begin();
			db.update(sql1, id);
			db.update(sql2, id);
			db.commit();
		}
		catch (Exception e)
		{
			db.rollback();
			code = 20002;
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			db.close();
		}
		return code;
	}
}
