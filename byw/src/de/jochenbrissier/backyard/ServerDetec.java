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

//TODO: Adding REGEX MATCHING FOR The Server -> so users can invoke their own modules
//TODO: BETTER XML PARSER e.g. with DOM4J or so 

public class ServerDetec {

	static Log log = LogFactory.getLog(ServerDetec.class);

	// matching attributes
	 static final String TOMCAT6 = "Apache Tomcat/6\\.0.*";
	 static final String JETTY7 = "jetty/7.*";
	 static final String JETTY6 = "jetty-6.*";
	 static final String GLASSFISHV3 = "GlassFish v3";
	 static final String UNKNOW = "unknow";

	// XML NAMES

	private static final String TOMCAT_XML = "Tomcat6";
	private static final String JETT7_XML = "Jetty7";
	private static final String GLASSFISH_XML = "Glassfishv3";

	private String path = "";
	private static final String XML_FILE = "Backyard.xml";

	/**
	 * 
	 * 
	 * regex matching the server info
	 * 
	 * @param servlet
	 * @return
	 */

	public static boolean isServer(String server, Servlet servlet) {

		ServletContext context = servlet.getServletConfig().getServletContext();
		return context.getServerInfo().matches(server);
	}

	private String getServer(Servlet servlet) {
		// get Server String

		ServletContext context = servlet.getServletConfig().getServletContext();

		String serverString = context.getServerInfo();

		if (serverString.matches(TOMCAT6)) {
			return TOMCAT6;
		}

		if (serverString.matches(JETTY7))
			return JETTY6;

		if (serverString.matches(GLASSFISHV3))
			return GLASSFISHV3;

		if (serverString.matches(JETTY7))
			return JETTY7;

		return UNKNOW;
	}

	/**
	 * loading a module by classename e.g. de.jochenbrissier.Tomcat6Module will
	 * return a instance of this module
	 * 
	 * @param clas
	 * @return
	 * @throws ClassNotFoundException
	 */

	public Module getModuleClass(String clas) throws ClassNotFoundException {
		log.debug("load class: " + clas);
		Class module = Class.forName(clas);

		Injector in = Guice.createInjector();

		log.debug("class loaded");

		return in.getInstance(module);

	}

	/**
	 * Find a module by the server context loading and returning the correct
	 * module
	 * 
	 * @param serv
	 * @return module for the server implementation
	 * @throws ServerNotSupportedException
	 * @throws ClassNotFoundException
	 */

	public Module getModuleClass(Servlet serv)
			throws ServerNotSupportedException, ClassNotFoundException {

		log.debug("try to get servername from servlet context");
		String server = getServer(serv);
		log.debug("Server: " + server);

		if (server.equals(UNKNOW)) {
			log.warn("Server not found");
			throw new ServerNotSupportedException();

		}
		log.debug("search in xml file for class");
		String classformxml = getServerModuleClass(getServerClass(server));
		log.debug("class found: " + classformxml);

		return getModuleClass(classformxml);

	}

	/**
	 * Translates the regex match string to xml file name
	 * 
	 * @param server
	 * @return
	 */
	private static String getServerClass(String server) {

		if (server.equals(TOMCAT6))
			return TOMCAT_XML;

		if (server.equals(GLASSFISHV3))
			return GLASSFISH_XML;

		if (server.equals(JETTY7))
			return JETT7_XML;

		if (server.equals(JETTY6))
			return JETT7_XML;

		return UNKNOW;
	}

	/**
	 * 
	 * Parsing the xml file and search by a name
	 * 
	 * 
	 * @param server
	 * @return class of server
	 */

	private String getServerModuleClass(String server) {
		try {
			log.debug("seach server with name : " + server + " in xml file");
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			URL url = getClass().getClassLoader().getResource(XML_FILE);


			// URL url = ClassLoader.getSystemResource("Backyard.xml");
			// System.out.println(url.getPath());
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
