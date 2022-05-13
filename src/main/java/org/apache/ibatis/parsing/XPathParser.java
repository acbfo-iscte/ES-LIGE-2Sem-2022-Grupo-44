/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.ibatis.builder.BuilderException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Clinton Begin
 * @author Kazuki Shimizu
 */
public class XPathParser {

  private XPathParserProduct2 xPathParserProduct2 = new XPathParserProduct2();
private final Document document;
  public XPathParser(String xml) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(false, null, null);
    this.document = createDocument(new InputSource(new StringReader(xml)));
  }

  public XPathParser(Reader reader) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(false, null, null);
    this.document = createDocument(new InputSource(reader));
  }

  public XPathParser(InputStream inputStream) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(false, null, null);
    this.document = createDocument(new InputSource(inputStream));
  }

  public XPathParser(Document document) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(false, null, null);
    this.document = document;
  }

  public XPathParser(String xml, boolean validation) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, null, null);
    this.document = createDocument(new InputSource(new StringReader(xml)));
  }

  public XPathParser(Reader reader, boolean validation) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, null, null);
    this.document = createDocument(new InputSource(reader));
  }

  public XPathParser(InputStream inputStream, boolean validation) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, null, null);
    this.document = createDocument(new InputSource(inputStream));
  }

  public XPathParser(Document document, boolean validation) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, null, null);
    this.document = document;
  }

  public XPathParser(String xml, boolean validation, Properties variables) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, variables, null);
    this.document = createDocument(new InputSource(new StringReader(xml)));
  }

  public XPathParser(Reader reader, boolean validation, Properties variables) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, variables, null);
    this.document = createDocument(new InputSource(reader));
  }

  public XPathParser(InputStream inputStream, boolean validation, Properties variables) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, variables, null);
    this.document = createDocument(new InputSource(inputStream));
  }

  public XPathParser(Document document, boolean validation, Properties variables) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, variables, null);
    this.document = document;
  }

  public XPathParser(String xml, boolean validation, Properties variables, EntityResolver entityResolver) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, variables, entityResolver);
    this.document = createDocument(new InputSource(new StringReader(xml)));
  }

  public XPathParser(Reader reader, boolean validation, Properties variables, EntityResolver entityResolver) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, variables, entityResolver);
    this.document = createDocument(new InputSource(reader));
  }

  public XPathParser(InputStream inputStream, boolean validation, Properties variables, EntityResolver entityResolver) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, variables, entityResolver);
    this.document = createDocument(new InputSource(inputStream));
  }

  public XPathParser(Document document, boolean validation, Properties variables, EntityResolver entityResolver) {
    xPathParserProduct2.getXPathParserProduct().commonConstructor(validation, variables, entityResolver);
    this.document = document;
  }

  public void setVariables(Properties variables) {
    xPathParserProduct2.getXPathParserProduct().setVariables(variables);
  }

  public String evalString(String expression) {
    return xPathParserProduct2.getXPathParserProduct().evalString(document, expression);
  }

  public String evalString(Object root, String expression) {
    return xPathParserProduct2.getXPathParserProduct().evalString(root, expression);
  }

  public Boolean evalBoolean(String expression) {
    return xPathParserProduct2.getXPathParserProduct().evalBoolean(document, expression);
  }

  public Boolean evalBoolean(Object root, String expression) {
    return xPathParserProduct2.getXPathParserProduct().evalBoolean(root, expression);
  }

  public Short evalShort(String expression) {
    return xPathParserProduct2.evalShort(document, expression);
  }

  public Short evalShort(Object root, String expression) {
    return xPathParserProduct2.evalShort(root, expression);
  }

  public Integer evalInteger(String expression) {
    return xPathParserProduct2.evalInteger(document, expression);
  }

  public Integer evalInteger(Object root, String expression) {
    return xPathParserProduct2.evalInteger(root, expression);
  }

  public Long evalLong(String expression) {
    return xPathParserProduct2.evalLong(document, expression);
  }

  public Long evalLong(Object root, String expression) {
    return xPathParserProduct2.evalLong(root, expression);
  }

  public Float evalFloat(String expression) {
    return xPathParserProduct2.evalFloat(document, expression);
  }

  public Float evalFloat(Object root, String expression) {
    return xPathParserProduct2.evalFloat(root, expression);
  }

  public Double evalDouble(String expression) {
    return xPathParserProduct2.getXPathParserProduct().evalDouble(document, expression);
  }

  public Double evalDouble(Object root, String expression) {
    return xPathParserProduct2.getXPathParserProduct().evalDouble(root, expression);
  }

  public List<XNode> evalNodes(String expression) {
    return xPathParserProduct2.getXPathParserProduct().evalNodes(document, expression, this);
  }

  public List<XNode> evalNodes(Object root, String expression) {
    return xPathParserProduct2.getXPathParserProduct().evalNodes(root, expression, this);
  }

  public XNode evalNode(String expression) {
    return xPathParserProduct2.getXPathParserProduct().evalNode(document, expression, this);
  }

  public XNode evalNode(Object root, String expression) {
    return xPathParserProduct2.getXPathParserProduct().evalNode(root, expression, this);
  }

  private Document createDocument(InputSource inputSource) {
    // important: this must only be called AFTER common constructor
    try {
      return createDocument1_extracted(inputSource);
    } catch (Exception e) {
      throw new BuilderException("Error creating document instance.  Cause: " + e, e);
    }
  }

private Document createDocument1_extracted(InputSource inputSource)
		throws ParserConfigurationException, SAXException, IOException {
	return xPathParserProduct2.getXPathParserProduct().createDocument2_extracted(inputSource);
}

}
