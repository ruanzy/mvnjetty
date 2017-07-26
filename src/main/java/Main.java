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
		WebAppContext wc = new WebAppContext(WebRoot, "");
		wc.setDescriptor(WebRoot + "/WEB-INF/web.xml");
		wc.setResourceBase(WebRoot);
		wc.setInitParameter(
				"org.eclipse.jetty.servlet.Default.useFileMappedBuffer",
				"false");
		wc.setClassLoader(Thread.currentThread().getContextClassLoader());
		wc.setConfigurationDiscovered(true);
		wc.setParentLoaderPriority(true);
		server.setHandler(wc);
		server.start();
	}
}
