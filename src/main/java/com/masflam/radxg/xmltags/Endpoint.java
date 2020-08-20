package com.masflam.radxg.xmltags;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Endpoint {
	
	@JacksonXmlProperty(isAttribute = true)
	public String method;
	
	@JacksonXmlProperty(isAttribute = true)
	public String path;
	
	public String desc;
	
	@JacksonXmlElementWrapper(localName = "req-params")
	@JsonProperty("param")
	public List<ReqParam> reqParams;
	
	@JsonProperty("req-body")
	public Body reqBody;
	
	@JacksonXmlElementWrapper(localName = "responses")
	@JsonProperty("response")
	public List<Resp> responses;
}
