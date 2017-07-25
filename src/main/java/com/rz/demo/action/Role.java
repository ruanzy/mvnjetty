package com.rz.demo.action;

import java.util.List;
import java.util.Map;

import com.rz.common.R;
import com.rz.demo.service.RoleService;
import com.rz.web.WebUtil;

public class Role
{
	public void add()
	{
		Map<String, String> params = WebUtil.getParameters();
		RoleService.add(params);
	}
	
	public boolean validname()
	{
		String rolename = WebUtil.getParameter("rolename");
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
		String roleid = WebUtil.getParameter("roleid");
		String permissions = WebUtil.getParameter("permissions");
		RoleService.setPermission(roleid, permissions);
	}
	
	public List<R> getPermission()
	{
		String roleid = WebUtil.getParameter("roleid");
		return RoleService.getPermission(roleid);
	}
	
	public void del()
	{
		String id = WebUtil.getParameter("id");
		RoleService.del(id);
	}
}
