package com.masflam.radxg.xmltags;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Resp {
	
	@JacksonXmlProperty(isAttribute = true)
	public int code;
	
	public String desc;
	public Body body;
}
