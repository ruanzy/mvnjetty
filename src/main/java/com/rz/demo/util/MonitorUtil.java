package com.rz.demo.util;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import jone.util.RrdUtils;

public class MonitorUtil
{

	public static Map<String, Double> getMonitorInfo(String ip, String jmxPort)
	{

		Map<String, Double> map = new HashMap<>();
		JMXConnector jmxConnector = null;
		try
		{

			String jmxURL = "service:jmx:rmi:///jndi/rmi://" + ip + ":"
					+ jmxPort + "/jmxrmi";
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			jmxConnector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mBeanServerConnection = jmxConnector
					.getMBeanServerConnection();

			// Java 虚拟机内存系统

			MemoryMXBean memoryMXBean = ManagementFactory
					.newPlatformMXBeanProxy(mBeanServerConnection,
							ManagementFactory.MEMORY_MXBEAN_NAME,
							MemoryMXBean.class);
			MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
//			map.put("hmuinit", 1.0 * heapMemoryUsage.getInit());
			map.put("hmuused", 1.0 * heapMemoryUsage.getUsed());
			map.put("hmucommitted", 1.0 * heapMemoryUsage.getCommitted());
			map.put("hmumax", 1.0 * heapMemoryUsage.getMax());
//			double mem_usage = heapMemoryUsage.getUsed() * 1.0
//					/ heapMemoryUsage.getCommitted() * 100;
//			map.put("memusage", mem_usage);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				jmxConnector.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return map;
	}
	
	public static Map<String, Double> getThreadInfo(String ip, String jmxPort)
	{

		Map<String, Double> map = new HashMap<>();
		JMXConnector jmxConnector = null;
		try
		{

			String jmxURL = "service:jmx:rmi:///jndi/rmi://" + ip + ":"
					+ jmxPort + "/jmxrmi";
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			jmxConnector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mBeanServerConnection = jmxConnector
					.getMBeanServerConnection();


			ThreadMXBean threadMXBean = ManagementFactory
					.newPlatformMXBeanProxy(mBeanServerConnection,
							ManagementFactory.THREAD_MXBEAN_NAME,
							ThreadMXBean.class);
			double thread_daemon = threadMXBean.getDaemonThreadCount();
			double thread_started = threadMXBean.getTotalStartedThreadCount();
			double thread_active = threadMXBean.getThreadCount();
			map.put("thread_daemon", thread_daemon);
			map.put("thread_started", thread_started);
			map.put("thread_active", thread_active);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				jmxConnector.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return map;
	}

	public static void start()
	{
		RrdUtils.createRrd("hmucommitted.rrd", "hmucommitted");
		RrdUtils.createRrd("hmuused.rrd", "hmuused");
		RrdUtils.createRrd("hmumax.rrd", "hmumax");
		RrdUtils.createRrd("thread_daemon.rrd", "thread_daemon");
		RrdUtils.createRrd("thread_started.rrd", "thread_started");
		RrdUtils.createRrd("thread_active.rrd", "thread_active");
		new Timer().schedule(new TimerTask() {
			@Override
			public void run()
			{
				Map<String, Double> values1 = MonitorUtil.getMonitorInfo("localhost", "9401");
				Map<String, Double> values2 = MonitorUtil.getThreadInfo("localhost", "9401");
				RrdUtils.writeRrd("hmucommitted.rrd", "hmucommitted", values1.get("hmucommitted"));
				RrdUtils.writeRrd("hmuused.rrd", "hmuused", values1.get("hmuused"));
				RrdUtils.writeRrd("hmumax.rrd", "hmumax", values1.get("hmumax"));
				RrdUtils.writeRrd("thread_daemon.rrd", "thread_daemon", values2.get("thread_daemon"));
				RrdUtils.writeRrd("thread_started.rrd", "thread_started", values2.get("thread_started"));
				RrdUtils.writeRrd("thread_active.rrd", "thread_active", values2.get("thread_active"));
			}
		}, 1000, RrdUtils.STEP * 1000);
	}
}
