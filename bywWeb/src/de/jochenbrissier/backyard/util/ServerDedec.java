package de.jochenbrissier.backyard.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

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

import de.jochenbrissier.backyard.util.xml.XMLParser;
import de.jochenbrissier.backyard.util.xml.XmlServerNode;

/**
 * Class is for server dedection. This class will load the correct modules for
 * the server. The modules are defined in the backyard xml.
 * 
 * You can also add your own modules to this xml class or change the existing
 * 
 * 
 * 
 * @author jochen
 * 
 */

public class ServerDedec {

	static Log log = LogFactory.getLog(ServerDedec.class);

	// matching attributes
	public static final String TOMCAT6 = "Apache Tomcat/6\\.0.*";
	public static final String JETTY7 = "jetty/7.*";
	public static final String JETTY6 = "jetty-6.*";
	public static final String GLASSFISHV3 = "GlassFish v3";
	public static final String UNKNOW = "unknow";

	// XML NAMES

	private static final String TOMCAT_XML = "Tomcat6";
	private static final String JETT7_XML = "Jetty7";
	private static final String GLASSFISH_XML = "Glassfishv3";

	private String path = "";
	private static final String XML_FILE = "Backyard.xml";

	// xml parser
	XMLParser xmlParser;
	// object that represents a xml note
	XmlServerNode server;

	
	
	
	
	public ServerDedec(Servlet serv) {
		URL url = getClass().getClassLoader().getResource(XML_FILE);
		log.debug("read xml: " + url.getPath());
		xmlParser = new XMLParser(url.getPath());
		log.debug("try to get servername from servlet context");
		String serverStr = getServer(serv);
		log.debug("Server: " + serverStr);
		this.server = getServerNode(serverStr);

	}

	public boolean getWebSocketSupport() {

		return server.isWebsocketSupport();

	}

	/**
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

		return serverString;
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

		return (Module) in.getInstance(module);

	}

	private XmlServerNode getServerNode(String server) {
		List<XmlServerNode> nodes = xmlParser.getServers();

		XmlServerNode serverinfo = null;

		for (XmlServerNode node : nodes) {

			if (server.matches(node.getPattern())) {
				log.info("Server  found:" + node.getName());
				serverinfo = node;
				break;
			}

		}

		return serverinfo;
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

	public Module getModuleClass() throws ServerNotSupportedException,
			ClassNotFoundException {

		if (this.server == null) {
			log.warn("Server not found");
			throw new ServerNotSupportedException();

		}

		String classformxml = this.server.getModule();
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
