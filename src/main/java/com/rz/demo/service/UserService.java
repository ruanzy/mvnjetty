package com.rz.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jone.R;
import jone.data.db.DB;
import jone.data.db.DBs;
import jone.data.db.RowHandler;
import jone.data.db.sql.SQLExecutor;

import org.apache.commons.codec.digest.DigestUtils;

public class UserService
{
	static DB db = DBs.getDB("h2");
	
	public static boolean login(String username, String password)
	{
		String sql = "select  * from users where username=? and pwd=? and state = '1'";
		R r = db.findOne(sql, new Object[] { username, password });
		if (r == null)
		{
			return false;
		}
		return true;
	}

	public static R list(R params, int page, int pagesize)
	{
		SQLExecutor executor = new SQLExecutor(db);
		return executor.pager("user.count", "user.list", params, page, pagesize);
	}

	public static void add(R params)
	{
		String username = params.getString("username");
		String password = params.getString("password");
		String _password = DigestUtils.md5Hex(username + "_" + password);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		int deptid = params.getInt("deptid");
		String sql = "insert into users values(?,?,?,'2016-09-15',0,'email','888888',1,?,'')";
		db.begin();
		db.update(sql, new Object[] { username, _password, deptid, time});
		db.close();
	}

	public static void del(String username)
	{
		String sql1 = "delete from userrole where userid = ?";
		String sql2 = "delete from users where username = ?";
		db.begin();
		db.update(sql1, new Object[] { username });
		db.update(sql2, new Object[] { username });
		db.close();
	}

	public static void cancel(String username)
	{
		String sql = "update users set state=0 where username = ?";
		db.begin();
		db.update(sql, new Object[] { username });
		db.close();
	}

	public static void setRole(String username, String roles)
	{
		String[] arr = roles.split(",");
		String sql1 = "delete from userrole where userid = ?";
		String sql2 = "insert into userrole values(?, ?)";
		db.begin();
		db.update(sql1, new Object[] { username });
		for (String str : arr)
		{
			db.update(sql2, new Object[] { username, str });
		}
		db.close();
	}

	public static List<R> getRoles(String username)
	{
		String sql = "select r.id, r.name, exists (select 1 from userrole where userid=? and roleid=r.id) as checked from role r";
		return db.find(sql, username);
	}

	public static boolean validname(String username)
	{
		String sql = "select * from users where username=?";
		List<R> list = db.find(sql, username);
		return list == null || list.size() == 0;
	}

	public static int modifyPwd(String username, String oldpassword, String password)
	{
		R r = db.findOne("select * from users where username = ?", new Object[] { username });
		String oldpwd = DigestUtils.md5Hex(username + "_" + oldpassword);
		if(null == r || (!oldpwd.equals(r.getString("pwd")))){
			return 10000;
		}
		String sql = "update users set pwd=? where username = ?";
		String pwd = DigestUtils.md5Hex(username + "_" + password);
		db.begin();
		db.update(sql, new Object[] { pwd, username });
		db.close();
		return 0;
	}
	
	public static int resetpwd(String username, String password)
	{
		String sql = "update users set pwd=? where username = ?";
		String pwd = DigestUtils.md5Hex(username + "_" + password);
		db.begin();
		db.update(sql, new Object[] { pwd, username });
		db.close();
		return 0;
	}

	public static List<String> getPermission(String username)
	{
		String sql = "select id from resources where id in(select distinct resid from roleres where roleid in (select roleid from userrole where userid=?))";
		List<String> list = db.find(sql, new Object[] { username }, new RowHandler<String>()
		{
			public String handle(ResultSet rs) throws SQLException
			{
				return rs.getString(1);
			}
		});
		return list;
	}

	public static R big(R params, int page, int pagesize)
	{
		SQLExecutor executor = new SQLExecutor(db);
		return executor.pager("user.count", "user.list", params, page, pagesize);
	}

	public static void update(R params)
	{
		String sql = "update users set phone=?, email=? where username = ?";
		String username = params.getString("username");
		String phone = params.getString("phone");
		String email = params.getString("email");
		db.update(sql, new Object[] { phone, email, username });
	}
	
	public static R load(String username)
	{
		String sql = "select * from users where username = ?";
		return db.findOne(sql, new Object[] { username });
	}
}
