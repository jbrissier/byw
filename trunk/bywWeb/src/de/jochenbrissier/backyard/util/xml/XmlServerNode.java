package de.jochenbrissier.backyard.util.xml;

import java.util.TreeMap;

import org.w3c.dom.Node;

public class XmlServerNode {
	private TreeMap<String, String> tm;
	private String name;
	private String pattern;
	private String module;
	private boolean websocketSupport = false;
	
	
	public XmlServerNode(Node nCl) {

		tm = new TreeMap<String, String>();
		for (Node no : new NodeChildList(nCl.getChildNodes())) {

			tm.put(no.getNodeName(), no.getTextContent());

		}

		this.name = tm.get("name");
		this.pattern = tm.get("pattern");
		this.module = tm.get("module");
		
		this.websocketSupport = "true".equalsIgnoreCase(tm.get("WebsocketSupport"));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	
	
	
	
	public boolean isWebsocketSupport() {
		return websocketSupport;
	}

	public void setWebsocketSupport(boolean websocketSupport) {
		this.websocketSupport = websocketSupport;
	}

	@Override
	public String toString() {
		return name + "," + pattern + "," + module + ","+ websocketSupport;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof XmlServerNode)
			return this.name.equals(((XmlServerNode) obj).getName());

		return super.equals(obj);
	}

}
