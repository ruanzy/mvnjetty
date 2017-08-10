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
		R r = WebUtil.params();
		DeptService.update(r);
	}
	
	public void add()
	{
		R r = WebUtil.params();
		DeptService.add(r);
	}
	
	public void del()
	{
		String id = WebUtil.param("id");
		DeptService.del(id);
	}
	
	public List<R> getPermission()
	{
		String id = WebUtil.param("id");
		return DeptService.getPermission(id);
	}
	
	public void setPermission()
	{
		String deptid = WebUtil.param("deptid");
		String permissions = WebUtil.param("permissions");
		DeptService.setPermission(deptid, permissions);
	}
}
