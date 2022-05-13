package org.apache.ibatis.parsing;


public class XNodeProduct2 {
	private final String body;

	public XNodeProduct2(String parseBody) {
		this.body = parseBody;
	}

	public String getBody() {
		return body;
	}

	public String getStringBody(String def) {
		return body == null ? def : body;
	}

	public Boolean getBooleanBody(Boolean def) {
		return body == null ? def : Boolean.valueOf(body);
	}

	public Integer getIntBody(Integer def) {
		return body == null ? def : Integer.valueOf(body);
	}

	public Long getLongBody(Long def) {
		return body == null ? def : Long.valueOf(body);
	}

	public Double getDoubleBody(Double def) {
		return body == null ? def : Double.valueOf(body);
	}

	public Float getFloatBody(Float def) {
		return body == null ? def : Float.valueOf(body);
	}
}