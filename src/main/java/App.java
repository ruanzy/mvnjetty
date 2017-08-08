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
		try
		{
			server.setStopAtShutdown(true);
			ProtectionDomain domain = App.class.getProtectionDomain();
			URL location = domain.getCodeSource().getLocation();
			String warFile = location.toExternalForm();
			String currentDir = new File(location.getPath()).getParent();
			File workDir = new File(currentDir, "work");
			WebAppContext webapp = new WebAppContext(warFile, "/");
			webapp.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
			webapp.setDescriptor(warFile + "/WEB-INF/web.xml");
			webapp.setServer(server);
			webapp.setTempDirectory(workDir);
			webapp.setExtraClasspath(currentDir + "/conf");
			server.setHandler(webapp);
			server.start();
			server.join();
			System.out.println("server is started in port " + port);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				server.stop();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
