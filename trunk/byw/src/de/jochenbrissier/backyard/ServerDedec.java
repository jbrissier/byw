package de.jochenbrissier.backyard;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.internal.api.ServerContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class ServerDedec {

	static Log log = LogFactory.getLog(ServerDedec.class);

	// matching attributes
	private static final String TOMCAT6 = "Apache Tomcat/6\\.0.*";
	private static final String JETTY7 = "jetty/7.*";
	private static final String JETTY6 = "jetty-6.*";
	private static final String GLASSFISHV3 = "GlassFish v3";
	private static final String UNKNOW = "unknow";

	// XML NAMES

	private static final String TOMCAT_XML = "Tomcat6";
	private static final String JETT7_XML = "Jetty7";
	private static final String GLASSFISH_XML = "Glassfishv3";

	private String path = "";
	private static final String XML_FILE = "Backyard.xml";

	private String getServer(Servlet servlet) {
		// get Server String

		ServletContext context = servlet.getServletConfig().getServletContext();

		String serverString = context.getServerInfo();

		if (serverString.matches(TOMCAT6))
			return TOMCAT6;

		if (serverString.matches(JETTY7))
			return JETTY6;

		if (serverString.matches(GLASSFISHV3))
			return GLASSFISHV3;

		if (serverString.matches(JETTY7))
			return JETTY7;

		return UNKNOW;
	}

	public Module getModuleClass(String clas) throws ClassNotFoundException {

		Class module = Class.forName(clas);

		Injector in = Guice.createInjector();
		return in.getInstance(module);

	}

	public Module getModuleClass(Servlet serv)
			throws ServerNotSupportedException, ClassNotFoundException {

		String server = getServer(serv);

		if (server.equals(UNKNOW))
			throw new ServerNotSupportedException();

		String classformxml = getServerModuleClass(getServerClass(server));

		return getModuleClass(classformxml);

	}

	private static String getServerClass(String server) {

		if (server.equals(TOMCAT6))
			return TOMCAT_XML;

		if (server.equals(GLASSFISHV3))
			return GLASSFISH_XML;

		if (server.equals(JETTY7))
			return JETT7_XML;

		return UNKNOW;
	}

	private String getServerModuleClass(String server) {
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			URL url = getClass().getClassLoader().getResource(XML_FILE);

			System.out.println(url.getPath());

//			 URL url = ClassLoader.getSystemResource("Backyard.xml");
//			 System.out.println(url.getPath());
			Document doc = builder.parse(new File(url.getPath()));

			Node el = doc.getElementsByTagName(server).item(0);
			return el.getTextContent();

		} catch (FileNotFoundException e) {

			log.warn("can't read or find xml file in classpath");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log.warn("can't parse xml file");
			return null;
		}

	}

}
