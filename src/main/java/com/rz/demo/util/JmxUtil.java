package com.rz.demo.util;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.management.OperatingSystemMXBean;

public class JmxUtil
{
	private static String ServiceURLFmt = "service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi";
	/**
	 * @param ip
	 * @param jmxPort
	 * @return init|commited|used|max
	 */
	public static double[] getMemory(String ip, int jmxPort)
	{
		JMXConnector connector = null;
		double[] arr = new double[4];
		try
		{
			String jmxURL = String.format(ServiceURLFmt, ip, jmxPort);
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			connector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			MemoryMXBean bean = ManagementFactory.newPlatformMXBeanProxy  
					(mbsc, ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
			MemoryUsage heapMemoryUsage = bean.getHeapMemoryUsage();
			long init = heapMemoryUsage.getInit();
			long commited = heapMemoryUsage.getCommitted();
			long used = heapMemoryUsage.getUsed();
			long max = heapMemoryUsage.getMax(); 
			arr[0]= init;
			arr[1]= commited;
			arr[2]= used;
			arr[3]= max;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(connector != null)
			{
				try
				{
					connector.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return arr;
	}
	
	/**
	 * @param ip
	 * @param jmxPort
	 * @return started|daemon|active
	 */
	public static double[] getThread(String ip, int jmxPort)
	{
		JMXConnector connector = null;
		double[] arr = new double[4];
		try
		{
			String jmxURL = String.format(ServiceURLFmt, ip, jmxPort);
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			connector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			ThreadMXBean bean = ManagementFactory.newPlatformMXBeanProxy  
					(mbsc, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
			long started = bean.getTotalStartedThreadCount();
			int daemon = bean.getDaemonThreadCount();
			int active = bean.getThreadCount();
			arr[0]= started;
			arr[1]= daemon;
			arr[2]= active;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(connector != null)
			{
				try
				{
					connector.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return arr;
	}

	/**
	 * @param ip
	 * @param jmxPort
	 * @return time
	 */
	public static long getCPU(String ip, int jmxPort)
	{
		JMXConnector connector = null;
		try
		{
			String jmxURL = String.format(ServiceURLFmt, ip, jmxPort);
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			connector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			OperatingSystemMXBean osbean =   
					ManagementFactory.newPlatformMXBeanProxy(mbsc,                 
					ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
			long time = osbean.getProcessCpuTime();
			return time;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return 0;
		}
		finally
		{
			if(connector != null)
			{
				try
				{
					connector.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static int getCore(String ip, int jmxPort)
	{
		JMXConnector connector = null;
		try
		{
			String jmxURL = String.format(ServiceURLFmt, ip, jmxPort);
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			connector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			OperatingSystemMXBean osbean =   
					ManagementFactory.newPlatformMXBeanProxy(mbsc,                 
					ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
			return osbean.getAvailableProcessors();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return 0;
		}
		finally
		{
			if(connector != null)
			{
				try
				{
					connector.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param ip
	 * @param jmxPort
	 * @return pid
	 */
	public static long getPID(String ip, int jmxPort)
	{
		JMXConnector connector = null;
		long pid = -1;
		try
		{
			String jmxURL = String.format(ServiceURLFmt, ip, jmxPort);
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			connector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			RuntimeMXBean rMXBean = ManagementFactory.newPlatformMXBeanProxy(mbsc,
					ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
			String processName = rMXBean.getName();
			processName = processName.split("@")[0];
			pid = Long.parseLong(processName);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(connector != null)
			{
				try
				{
					connector.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return pid;
	}
	
	/**
	 * @param ip
	 * @param jmxPort
	 * @return Map
	 */
	public static Map<String, String> getOS(String ip, int jmxPort)
	{
		JMXConnector connector = null;
		Map<String, String> ret = new HashMap<String, String>();
		try
		{
			String jmxURL = String.format(ServiceURLFmt, ip, jmxPort);
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			connector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			OperatingSystemMXBean osbean =  ManagementFactory.newPlatformMXBeanProxy(mbsc,                 
					ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
			String name = osbean.getName();
			String arch = osbean.getArch();
			String version = osbean.getVersion();
			ret.put("name", name);
			ret.put("arch", arch);
			ret.put("version", version);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(connector != null)
			{
				try
				{
					connector.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
}
