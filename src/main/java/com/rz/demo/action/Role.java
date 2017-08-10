package com.rz.demo.action;

import java.util.List;
import java.util.Map;

import jone.R;
import jone.web.WebUtil;

import com.rz.demo.service.RoleService;

public class Role
{
	public void add()
	{
		R params = WebUtil.params();
		RoleService.add(params);
	}
	
	public boolean validname()
	{
		String rolename = WebUtil.param("rolename");
		return RoleService.validname(rolename);
	}
	
	public List<R> listAll()
	{
		return RoleService.listAll();
	}

	public Map<String, Object> list()
	{
		R params = WebUtil.params();
		int page = params.getInt("page");
		int pagesize = params.getInt("pagesize");
		return RoleService.list(params, page, pagesize);
	}
	
	public void setPermission()
	{
		String roleid = WebUtil.param("roleid");
		String permissions = WebUtil.param("permissions");
		RoleService.setPermission(roleid, permissions);
	}
	
	public List<R> getPermission()
	{
		String roleid = WebUtil.param("roleid");
		return RoleService.getPermission(roleid);
	}
	
	public void del()
	{
		String id = WebUtil.param("id");
		RoleService.del(id);
	}
}
