package com.rz.demo;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class App
{

	public static void main(String[] args)
	{
		int port = 9090;
		Server server = new Server(port);
		try {
			server.setStopAtShutdown(true);
            ProtectionDomain domain = App.class.getProtectionDomain();
            URL warLocation = domain.getCodeSource().getLocation();
            String classpath = new File(warLocation.getFile()).getParentFile().getAbsolutePath();
            WebAppContext webapp = new WebAppContext();
            webapp.setContextPath("/");
            webapp.setDescriptor(warLocation.toExternalForm() + "/WEB-INF/web.xml");
            webapp.setServer(server);
            webapp.setWar(warLocation.toExternalForm());
            webapp.setTempDirectory(new File("./work"));
            webapp.setExtraClasspath(classpath);
            server.setHandler(webapp);
            server.start();
            server.join();
            System.out.println("server is started in port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
}
