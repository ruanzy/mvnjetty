package com.rz.demo.action;

import java.util.List;

import jone.R;
import jone.web.WebUtil;

import com.rz.demo.service.DeptService;

public class Dept
{
	public List<R> tree()
	{
		return DeptService.tree();
	}
	
	public void update()
	{
		R r = WebUtil.getParams();
		DeptService.update(r);
	}
	
	public void add()
	{
		R r = WebUtil.getParams();
		DeptService.add(r);
	}
	
	public void del()
	{
		String id = WebUtil.getParameter("id");
		DeptService.del(id);
	}
	
	public List<R> getPermission()
	{
		String id = WebUtil.getParameter("id");
		return DeptService.getPermission(id);
	}
	
	public void setPermission()
	{
		String deptid = WebUtil.getParameter("deptid");
		String permissions = WebUtil.getParameter("permissions");
		DeptService.setPermission(deptid, permissions);
	}
}
