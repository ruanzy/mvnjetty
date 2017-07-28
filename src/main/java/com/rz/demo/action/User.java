package com.rz.demo.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.rz.common.R;
import com.rz.demo.service.UserService;
import com.rz.demo.util.SubmitJobToSpark;
import com.rz.web.TokenUtil;
import com.rz.web.WebUtil;

public class User
{
	public R login() {
		R ret = new R();
		String username = WebUtil.getParameter("username");
		String password = WebUtil.getParameter("password");
		boolean flag = UserService.login(username, password);
		if (flag) {
			String token = TokenUtil.generatorToken(username);
			ret.put("code", 0);
			ret.put("msg", "登录成功");
			ret.put("username", username);
			ret.put("token", token);
		} else {
			ret.put("code", 10001);
			ret.put("msg", "用户名或密码错误");
		}
		return ret;
	}
	
	public void add()
	{
		Map<String, String> params = WebUtil.getParameters();
		UserService.add(params);
	}
	
	public boolean validname()
	{
		String username = WebUtil.getParameter("username");
		return UserService.validname(username);
	}

	public R list()
	{
		Map<String, String> params = WebUtil.getParameters();
		int page = Integer.valueOf(params.get("page"));
		int pagesize = Integer.valueOf(params.get("pagesize"));
		return UserService.list(params, page, pagesize);
	}

	public void del()
	{
		String username = WebUtil.getParameter("username");
		UserService.del(username);
	}

	public void cancel()
	{
		String username = WebUtil.getParameter("username");
		UserService.cancel(username);
	}

	public List<R> getRoles()
	{
		String userid = WebUtil.getParameter("username");
		return UserService.getRoles(userid);
	}

	public void setRole()
	{
		String username = WebUtil.getParameter("username");
		String roles = WebUtil.getParameter("roles");
		UserService.setRole(username, roles);
	}
	
	
	public int modifyPwd()
	{
		String username = WebUtil.getParameter("username");
		String oldpassword = WebUtil.getParameter("oldpassword");
		String password = WebUtil.getParameter("password");
		return UserService.modifyPwd(username, oldpassword, password);
	}
	
	public int resetpwd()
	{
		String username = WebUtil.getParameter("username");
		String password = WebUtil.getParameter("password");
		return UserService.resetpwd(username, password);
	}
	
	public List<String> getPermission()
	{
		String username = WebUtil.getParameter("username");
		return UserService.getPermission(username);
	}
	
	public R big()
	{
		Map<String, String> params = WebUtil.getParameters();
		int page = Integer.valueOf(params.get("page"));
		int pagesize = Integer.valueOf(params.get("pagesize"));
		return UserService.big(params, page, pagesize);
	}
	
	public String spark()
	{
		SubmitJobToSpark.submitJob();
		String ret = "";
		try
		{
			ret = IOUtils.toString(new FileInputStream("d:/ret/part-00000"));
		}
		catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ret;
	}
}
