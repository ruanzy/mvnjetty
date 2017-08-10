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
		Map<String, String> params = WebUtil.getParameters();
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
		Map<String, String> params = WebUtil.getParameters();
		int page = Integer.valueOf(params.get("page"));
		int pagesize = Integer.valueOf(params.get("pagesize"));
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
