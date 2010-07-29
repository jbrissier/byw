package de.jochenbrissier.backyard.util.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XMLParser {

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	Document document;

	/**
	 * Parser for the backyard.xml file
	 * @param path
	 */
	public XMLParser(String path) {
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new File(path));

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
 
	
	/**
	 * build a list of all servers in the xml 
	 * @return a list of XmlServerNodes 
	 */
	
	public List<XmlServerNode> getServers() {
		NodeChildList x = new NodeChildList(document
				.getElementsByTagName("server"));
		ArrayList<XmlServerNode> list = new ArrayList<XmlServerNode>();
		for (Node n : x) {

			list.add(new XmlServerNode(n));

		}
		return list;

	}
}
