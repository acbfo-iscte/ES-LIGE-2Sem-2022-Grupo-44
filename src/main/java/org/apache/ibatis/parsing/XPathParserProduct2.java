package org.apache.ibatis.parsing;


public class XPathParserProduct2 {
	private XPathParserProduct xPathParserProduct = new XPathParserProduct();

	public XPathParserProduct getXPathParserProduct() {
		return xPathParserProduct;
	}

	public Short evalShort(Object root, String expression) {
		return Short.valueOf(xPathParserProduct.evalString(root, expression));
	}

	public Integer evalInteger(Object root, String expression) {
		return Integer.valueOf(xPathParserProduct.evalString(root, expression));
	}

	public Long evalLong(Object root, String expression) {
		return Long.valueOf(xPathParserProduct.evalString(root, expression));
	}

	public Float evalFloat(Object root, String expression) {
		return Float.valueOf(xPathParserProduct.evalString(root, expression));
	}
}