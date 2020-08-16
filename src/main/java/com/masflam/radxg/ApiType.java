package com.masflam.radxg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class ApiType {
	
	@JacksonXmlProperty(isAttribute = true)
	public String name;
	
	@JacksonXmlText
	public String desc;
}
