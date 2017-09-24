package com.rz.demo.util;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.Date;
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

			ThreadMXBean threadMXBean = ManagementFactory
					.newPlatformMXBeanProxy(mBeanServerConnection,
							ManagementFactory.THREAD_MXBEAN_NAME,
							ThreadMXBean.class);
			com.sun.management.OperatingSystemMXBean opMXbean = ManagementFactory
					.newPlatformMXBeanProxy(mBeanServerConnection,
							ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
							com.sun.management.OperatingSystemMXBean.class);
			map.put("hmu_init", 1.0 * heapMemoryUsage.getInit());
			map.put("hmu_used", 1.0 * heapMemoryUsage.getUsed());
			map.put("hmu_committed", 1.0 * heapMemoryUsage.getCommitted());
			map.put("hmu_max", 1.0 * heapMemoryUsage.getMax());
			System.out.println(heapMemoryUsage.getInit());
			System.out.println(heapMemoryUsage.getCommitted());
			System.out.println(heapMemoryUsage.getUsed());
			double mem_usage = heapMemoryUsage.getUsed() * 1.0
					/ heapMemoryUsage.getCommitted() * 100;
			map.put("mem_usage", mem_usage);
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
		RrdUtils.createRrd("192168001001_hmucommitted.rrd", "hmucommitted");
		RrdUtils.createRrd("192168001001_hmuused.rrd", "hmuused");
		RrdUtils.createRrd("192168001001_hmumax.rrd", "hmumax");
		new Timer().schedule(new TimerTask() {

			@Override
			public void run()
			{
				Map<String, Double> m = MonitorUtil.getMonitorInfo("localhost",
						"9401");
				Double hmucommitted = m.get("hmu_committed");
				Double hmuused = m.get("hmu_used");
				Double hmumax = m.get("hmu_max");
				RrdUtils.writeRrd("192168001001_hmucommitted.rrd", "hmucommitted",
						hmucommitted);
				RrdUtils.writeRrd("192168001001_hmuused.rrd", "hmuused",
						hmuused);
				RrdUtils.writeRrd("192168001001_hmumax.rrd", "hmumax",
						hmumax);
			}
		}, 1000, RrdUtils.STEP * 1000);
	}
}
