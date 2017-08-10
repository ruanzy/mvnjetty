package com.rz.demo.service;

import java.util.List;
import java.util.Map;

import jone.R;
import jone.data.db.DB;
import jone.data.db.DBs;
import jone.data.db.sql.SQLExecutor;

public class RoleService
{
	static DB db = DBs.getDB("h2");;

	public static List<R> listAll()
	{
		List<R> list = db.find("select * from role");
		return list;
	}

	public static Map<String, Object> list(R params,
			int page, int pagesize)
	{
		SQLExecutor executor = new SQLExecutor(db);
		return executor.pager("role.rolecount", "role.rolelist", params, page, pagesize);
	}

	public static void add(R params)
	{
		String rolename = params.getString("rolename");
		String sql = "insert into role(name) values(?)";
		db.begin();
		db.update(sql, new Object[] { rolename });
		db.close();
	}

	public static void setPermission(String roleid, String permissions)
	{
		String[] arr = permissions.split(",");
		String sql1 = "delete from roleres where roleid = ?";
		String sql2 = "insert into roleres values(?, ?)";
		db.begin();
		db.update(sql1, new Object[] { roleid });
		for (String str : arr)
		{
			db.update(sql2, new Object[] { roleid, str });
		}
		db.close();
	}

	public static List<R> getPermission(String roleid)
	{
		String sql = "select r.*, exists (select 1 from roleres where roleid=? and resid=r.id) as checked from resources r";
		List<R> list = db.find(sql, roleid);
		return list;
	}

	public static boolean validname(String name)
	{
		String sql = "select * from role where name=?";
		List<R> list = db.find(sql, name);
		return list == null || list.size() == 0;
	}

	public static void del(String roleid)
	{
		String sql1 = "delete from userrole where roleid = ?";
		String sql2 = "delete from roleres where roleid = ?";
		String sql3 = "delete from role where id = ?";
		db.begin();
		db.update(sql1, new Object[] { roleid });
		db.update(sql2, new Object[] { roleid });
		db.update(sql3, new Object[] { roleid });
		db.close();
	}
}
