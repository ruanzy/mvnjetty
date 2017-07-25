package com.rz.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rz.common.R;
import com.rz.data.db.DB;
import com.rz.data.db.DBs;
import com.rz.data.db.sql.SQLExecutor;

public class RoleService
{
	static DB db = DBs.getDB("h2");;

	public static List<R> listAll()
	{
		List<R> list = db.find("select * from role");
		return list;
	}

	public static Map<String, Object> list(Map<String, String> params, int page, int pagesize)
	{
		Map<String, Object> ret = new HashMap<String, Object>();
		SQLExecutor executor = new SQLExecutor(db);
		int total = 0;
		Object o = executor.scalar("role.rolecount", params);
		if (o != null)
		{
			total = Integer.valueOf(o.toString());
		}
		List<R> list = executor.pager("role.rolelist", params, page, pagesize);
		ret.put("total", total);
		ret.put("data", list);
		return ret;
	}

	public static void add(Map<String, String> params)
	{
		String rolename = params.get("rolename");
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
