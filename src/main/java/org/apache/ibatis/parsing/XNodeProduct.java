package org.apache.ibatis.parsing;


import org.w3c.dom.Node;
import java.util.Properties;
import org.w3c.dom.Element;
import java.util.List;
import java.util.ArrayList;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.CharacterData;

public class XNodeProduct {
	private final Node node;
	private final Properties variables;
	private final XPathParser xpathParser;

	public XNodeProduct(XPathParser xpathParser, Node node, Properties variables) {
		this.xpathParser = xpathParser;
		this.node = node;
		this.variables = variables;
	}

	public Node getNode() {
		return node;
	}

	public XPathParser getXpathParser() {
		return xpathParser;
	}

	public String getPath() {
		StringBuilder builder = new StringBuilder();
		Node current = node;
		while (current instanceof Element) {
			if (current != node) {
				builder.insert(0, "/");
			}
			builder.insert(0, current.getNodeName());
			current = current.getParentNode();
		}
		return builder.toString();
	}

	public XNode newXNode(Node node) {
		return new XNode(xpathParser, node, variables);
	}

	public XNode getParent() {
		Node parent = node.getParentNode();
		if (!(parent instanceof Element)) {
			return null;
		} else {
			return new XNode(xpathParser, parent, variables);
		}
	}

	public List<XNode> getChildren() {
		List<XNode> children = new ArrayList<>();
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0, n = nodeList.getLength(); i < n; i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					children.add(new XNode(xpathParser, node, variables));
				}
			}
		}
		return children;
	}

	public Properties parseAttributes(Node n) {
		Properties attributes = new Properties();
		NamedNodeMap attributeNodes = n.getAttributes();
		if (attributeNodes != null) {
			for (int i = 0; i < attributeNodes.getLength(); i++) {
				Node attribute = attributeNodes.item(i);
				String value = PropertyParser.parse(attribute.getNodeValue(), variables);
				attributes.put(attribute.getNodeName(), value);
			}
		}
		return attributes;
	}

	public String getBodyData(Node child) {
		if (child.getNodeType() == Node.CDATA_SECTION_NODE || child.getNodeType() == Node.TEXT_NODE) {
			String data = ((CharacterData) child).getData();
			data = PropertyParser.parse(data, variables);
			return data;
		}
		return null;
	}

	public String parseBody(Node node) {
		String data = getBodyData(node);
		if (data == null) {
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				data = getBodyData(child);
				if (data != null) {
					break;
				}
			}
		}
		return data;
	}
}