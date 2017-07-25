package com.rz.demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main
{
	static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception
	{
		int port = 8089;
		String WebRoot = "src/main/webapp";
		logger.debug("Server start in port {}", port);
		Server server = new Server(port);
		WebAppContext webAppContext = new WebAppContext(WebRoot, "");
		webAppContext.setDescriptor(WebRoot + "/WEB-INF/web.xml");
		webAppContext.setResourceBase(WebRoot);
		webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
		webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		webAppContext.setConfigurationDiscovered(true);
		webAppContext.setParentLoaderPriority(true);
		server.setHandler(webAppContext);
		server.start();
	}
}
