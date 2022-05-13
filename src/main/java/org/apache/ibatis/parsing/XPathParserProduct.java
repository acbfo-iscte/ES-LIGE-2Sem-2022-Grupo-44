package org.apache.ibatis.parsing;


import org.xml.sax.EntityResolver;
import java.util.Properties;
import javax.xml.xpath.XPath;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import javax.xml.xpath.XPathFactory;
import javax.xml.namespace.QName;
import org.apache.ibatis.builder.BuilderException;
import javax.xml.xpath.XPathConstants;
import java.util.List;
import java.util.ArrayList;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class XPathParserProduct {
	private boolean validation;
	private EntityResolver entityResolver;
	private Properties variables;
	private XPath xpath;

	public void setVariables(Properties variables) {
		this.variables = variables;
	}

	public Document createDocument2_extracted(InputSource inputSource)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		factory.setValidating(validation);
		factory.setNamespaceAware(false);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(false);
		factory.setCoalescing(false);
		factory.setExpandEntityReferences(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setEntityResolver(entityResolver);
		builder.setErrorHandler(new ErrorHandler() {
			@Override
			public void error(SAXParseException exception) throws SAXException {
				throw exception;
			}

			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				throw exception;
			}

			@Override
			public void warning(SAXParseException exception) throws SAXException {
			}
		});
		return builder.parse(inputSource);
	}

	public void commonConstructor(boolean validation, Properties variables, EntityResolver entityResolver) {
		this.validation = validation;
		this.entityResolver = entityResolver;
		this.variables = variables;
		XPathFactory factory = XPathFactory.newInstance();
		this.xpath = factory.newXPath();
	}

	public Object evaluate(String expression, Object root, QName returnType) {
		try {
			return xpath.evaluate(expression, root, returnType);
		} catch (Exception e) {
			throw new BuilderException("Error evaluating XPath.  Cause: " + e, e);
		}
	}

	public Boolean evalBoolean(Object root, String expression) {
		return (Boolean) evaluate(expression, root, XPathConstants.BOOLEAN);
	}

	public Double evalDouble(Object root, String expression) {
		return (Double) evaluate(expression, root, XPathConstants.NUMBER);
	}

	public String evalString(Object root, String expression) {
		String result = (String) evaluate(expression, root, XPathConstants.STRING);
		result = PropertyParser.parse(result, variables);
		return result;
	}

	public List<XNode> evalNodes(Object root, String expression, XPathParser xPathParser) {
		List<XNode> xnodes = new ArrayList<>();
		NodeList nodes = (NodeList) evaluate(expression, root, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			xnodes.add(new XNode(xPathParser, nodes.item(i), variables));
		}
		return xnodes;
	}

	public XNode evalNode(Object root, String expression, XPathParser xPathParser) {
		Node node = (Node) evaluate(expression, root, XPathConstants.NODE);
		if (node == null) {
			return null;
		}
		return new XNode(xPathParser, node, variables);
	}
}