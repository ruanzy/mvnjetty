package com.rz.demo.action;

import java.io.FileReader;
import java.util.List;

import jone.R;
import jone.web.TokenUtil;
import jone.web.WebUtil;

import org.apache.commons.io.IOUtils;

import com.rz.demo.service.UserService;
import com.rz.demo.util.SubmitJobToSpark;

public class User
{
	public R login() {
		R ret = new R();
		String username = WebUtil.param("username");
		String password = WebUtil.param("password");
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
		R params = WebUtil.params();
		UserService.add(params);
	}
	
	public boolean validname()
	{
		String username = WebUtil.param("username");
		return UserService.validname(username);
	}

	public R list()
	{
		R params = WebUtil.params();
		int page = params.getInt("page");
		int pagesize = params.getInt("pagesize");
		return UserService.list(params, page, pagesize);
	}

	public void del()
	{
		String username = WebUtil.param("username");
		UserService.del(username);
	}

	public void cancel()
	{
		String username = WebUtil.param("username");
		UserService.cancel(username);
	}

	public List<R> getRoles()
	{
		String userid = WebUtil.param("username");
		return UserService.getRoles(userid);
	}

	public void setRole()
	{
		String username = WebUtil.param("username");
		String roles = WebUtil.param("roles");
		UserService.setRole(username, roles);
	}
	
	
	public int modifyPwd()
	{
		String username = WebUtil.param("username");
		String oldpassword = WebUtil.param("oldpassword");
		String password = WebUtil.param("password");
		return UserService.modifyPwd(username, oldpassword, password);
	}
	
	public int resetpwd()
	{
		String username = WebUtil.param("username");
		String password = WebUtil.param("password");
		return UserService.resetpwd(username, password);
	}
	
	public List<String> getPermission()
	{
		String username = WebUtil.param("username");
		return UserService.getPermission(username);
	}
	
	public R big()
	{
		R params = WebUtil.params();
		int page = params.getInt("page");
		int pagesize = params.getInt("pagesize");
		return UserService.big(params, page, pagesize);
	}
	
	public String spark()
	{
		SubmitJobToSpark.submitJob();
		String ret = "";
		try
		{
			ret = IOUtils.toString(new FileReader("d:/ret/part-00000"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}
}
