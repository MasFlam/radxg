package com.masflam.radxg;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class ReqParam {
	
	@JacksonXmlProperty(isAttribute = true)
	public String name;
	
	@JacksonXmlProperty(isAttribute = true)
	public String type;
	
	@JacksonXmlProperty(localName = "default", isAttribute = true)
	public String def;
	
	@JacksonXmlText
	public String desc;
}
