package com.masflam.radxg.xmltags;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Body {
	
	@JacksonXmlProperty(isAttribute = true)
	public String type;
	
	@JacksonXmlText
	public String desc;
}
