package com.masflam.radxg.xmltags;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "spec")
public class Spec {
	
	@JacksonXmlElementWrapper(localName = "types")
	@JsonProperty("type")
	public List<ApiType> types;
	
	@JacksonXmlElementWrapper(localName = "endpoints")
	@JsonProperty("endpoint")
	public List<Endpoint> endpoints;
}
