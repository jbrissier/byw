package de.jochenbrissier.backyard.util.xml;

import java.util.Iterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class to help and iterate NodeLists because the don't implement the Iterable
 * interface
 * 
 * @author jochen
 * 
 */
public class NodeChildList implements NodeList, Iterable<Node> {

	private class NodeIterator implements Iterator {

		NodeList nl;
		int size;
		int pos = 0;

		public NodeIterator(NodeList nl) {
			size = nl.getLength();
			this.nl = nl;

		}

		@Override
		public boolean hasNext() {
			return pos < size;
		}

		@Override
		public Object next() {
			return nl.item(pos++);
		}

		@Override
		public void remove() {

		}

	}

	private NodeList nl;
	private NodeIterator ni;

	public NodeChildList(NodeList nl) {
		this.nl = nl;
		ni = new NodeIterator(nl);

	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return nl.getLength();
	}

	@Override
	public Node item(int paramInt) {
		// TODO Auto-generated method stub
		return nl.item(paramInt);
	}

	@Override
	public Iterator<Node> iterator() {
		// TODO Auto-generated method stub
		return ni;
	}
}
