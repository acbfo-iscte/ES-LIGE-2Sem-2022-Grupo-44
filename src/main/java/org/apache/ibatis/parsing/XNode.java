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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Clinton Begin
 */
public class XNode {

  private XNodeProduct2 xNodeProduct2;
private XNodeProduct xNodeProduct;
private final String name;
  private final Properties attributes;
  public XNode(XPathParser xpathParser, Node node, Properties variables) {
    this.xNodeProduct = new XNodeProduct(xpathParser, node, variables);
	this.name = node.getNodeName();
    this.attributes = xNodeProduct.parseAttributes(node);
	this.xNodeProduct2 = new XNodeProduct2(xNodeProduct.parseBody(node));
  }

  public XNode newXNode(Node node) {
    return xNodeProduct.newXNode(node);
  }

  public XNode getParent() {
    return xNodeProduct.getParent();
  }

  public String getPath() {
    return xNodeProduct.getPath();
  }

  public String getValueBasedIdentifier() {
    StringBuilder builder = new StringBuilder();
    XNode current = this;
    while (current != null) {
      current = getValueBasedIdentifier1_extracted(builder, current);
    }
    return builder.toString();
  }

private XNode getValueBasedIdentifier1_extracted(StringBuilder builder, XNode current) {
	if (current != this) {
        builder.insert(0, "_");
      }
      String value = current.getStringAttribute("id",
          current.getStringAttribute("value",
              current.getStringAttribute("property", (String) null)));
      if (value != null) {
        value = value.replace('.', '_');
        builder.insert(0, "]");
        builder.insert(0,
            value);
        builder.insert(0, "[");
      }
      builder.insert(0, current.getName());
      current = current.getParent();
	return current;
}

  public String evalString(String expression) {
    return xNodeProduct.getXpathParser().evalString(xNodeProduct.getNode(), expression);
  }

  public Boolean evalBoolean(String expression) {
    return xNodeProduct.getXpathParser().evalBoolean(xNodeProduct.getNode(), expression);
  }

  public Double evalDouble(String expression) {
    return xNodeProduct.getXpathParser().evalDouble(xNodeProduct.getNode(), expression);
  }

  public List<XNode> evalNodes(String expression) {
    return xNodeProduct.getXpathParser().evalNodes(xNodeProduct.getNode(), expression);
  }

  public XNode evalNode(String expression) {
    return xNodeProduct.getXpathParser().evalNode(xNodeProduct.getNode(), expression);
  }

  public Node getNode() {
    return xNodeProduct.getNode();
  }

  public String getName() {
    return name;
  }

  public String getStringBody() {
    return xNodeProduct2.getStringBody(null);
  }

  public String getStringBody(String def) {
    return xNodeProduct2.getStringBody(def);
  }

  public Boolean getBooleanBody() {
    return xNodeProduct2.getBooleanBody(null);
  }

  public Boolean getBooleanBody(Boolean def) {
    return xNodeProduct2.getBooleanBody(def);
  }

  public Integer getIntBody() {
    return xNodeProduct2.getIntBody(null);
  }

  public Integer getIntBody(Integer def) {
    return xNodeProduct2.getIntBody(def);
  }

  public Long getLongBody() {
    return xNodeProduct2.getLongBody(null);
  }

  public Long getLongBody(Long def) {
    return xNodeProduct2.getLongBody(def);
  }

  public Double getDoubleBody() {
    return xNodeProduct2.getDoubleBody(null);
  }

  public Double getDoubleBody(Double def) {
    return xNodeProduct2.getDoubleBody(def);
  }

  public Float getFloatBody() {
    return xNodeProduct2.getFloatBody(null);
  }

  public Float getFloatBody(Float def) {
    return xNodeProduct2.getFloatBody(def);
  }

  public <T extends Enum<T>> T getEnumAttribute(Class<T> enumType, String name) {
    return getEnumAttribute(enumType, name, null);
  }

  public <T extends Enum<T>> T getEnumAttribute(Class<T> enumType, String name, T def) {
    String value = getStringAttribute(name);
    return value == null ? def : Enum.valueOf(enumType,value);
  }

  /**
   * Return a attribute value as String.
   *
   * <p>
   * If attribute value is absent, return value that provided from supplier of default value.
   *
   * @param name
   *          attribute name
   * @param defSupplier
   *          a supplier of default value
   * @return the string attribute
   * @since 3.5.4
   */
  public String getStringAttribute(String name, Supplier<String> defSupplier) {
    String value = attributes.getProperty(name);
    return value == null ? defSupplier.get() : value;
  }

  public String getStringAttribute(String name) {
    return getStringAttribute(name, (String) null);
  }

  public String getStringAttribute(String name, String def) {
    String value = attributes.getProperty(name);
    return value == null ? def : value;
  }

  public Boolean getBooleanAttribute(String name) {
    return getBooleanAttribute(name, null);
  }

  public Boolean getBooleanAttribute(String name, Boolean def) {
    String value = attributes.getProperty(name);
    return value == null ? def : Boolean.valueOf(value);
  }

  public Integer getIntAttribute(String name) {
    return getIntAttribute(name, null);
  }

  public Integer getIntAttribute(String name, Integer def) {
    String value = attributes.getProperty(name);
    return value == null ? def : Integer.valueOf(value);
  }

  public Long getLongAttribute(String name) {
    return getLongAttribute(name, null);
  }

  public Long getLongAttribute(String name, Long def) {
    String value = attributes.getProperty(name);
    return value == null ? def : Long.valueOf(value);
  }

  public Double getDoubleAttribute(String name) {
    return getDoubleAttribute(name, null);
  }

  public Double getDoubleAttribute(String name, Double def) {
    String value = attributes.getProperty(name);
    return value == null ? def : Double.valueOf(value);
  }

  public Float getFloatAttribute(String name) {
    return getFloatAttribute(name, null);
  }

  public Float getFloatAttribute(String name, Float def) {
    String value = attributes.getProperty(name);
    return value == null ? def : Float.valueOf(value);
  }

  public List<XNode> getChildren() {
    return xNodeProduct.getChildren();
  }

  public Properties getChildrenAsProperties() {
    Properties properties = new Properties();
    for (XNode child : xNodeProduct.getChildren()) {
      String name = child.getStringAttribute("name");
      String value = child.getStringAttribute("value");
      if (name != null && value != null) {
        properties.setProperty(name, value);
      }
    }
    return properties;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    toString(builder, 0);
    return builder.toString();
  }

  private void toString(StringBuilder builder, int level) {
    builder.append("<");
    builder.append(name);
    for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
      builder.append(" ");
      builder.append(entry.getKey());
      builder.append("=\"");
      builder.append(entry.getValue());
      builder.append("\"");
    }
    List<XNode> children = xNodeProduct.getChildren();
    toString_extracted1(builder, level, children);
  }

private void toString_extracted1(StringBuilder builder, int level, List<XNode> children) {
	if (!children.isEmpty()) {
      builder.append(">\n");
      for (XNode child : children) {
        indent(builder, level + 1);
        child.toString(builder, level + 1);
      }
      indent(builder, level);
      builder.append("</");
      builder.append(name);
      builder.append(">");
    } else if (xNodeProduct2.getBody() != null) {
      builder.append(">");
      builder.append(xNodeProduct2.getBody());
      builder.append("</");
      builder.append(name);
      builder.append(">");
    } else {
      builder.append("/>");
      indent(builder, level);
    }
    builder.append("\n");
}

  private void indent(StringBuilder builder, int level) {
    for (int i = 0; i < level; i++) {
      builder.append("    ");
    }
  }

}
