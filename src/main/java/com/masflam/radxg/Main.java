package com.masflam.radxg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.masflam.radxg.xmltags.ApiType;
import com.masflam.radxg.xmltags.Endpoint;
import com.masflam.radxg.xmltags.ReqParam;
import com.masflam.radxg.xmltags.Resp;
import com.masflam.radxg.xmltags.Spec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		if(args.length == 0) {
			System.err.println("Usage:\tradxg <doc-xml-basename>");
		} else {
			for(String arg : args) {
				System.out.println("Reading file:\t" + arg + ".xml");
				
				ObjectMapper xmlMapper = new XmlMapper();
				
				try {
					Spec spec = xmlMapper.readValue(new File(arg + ".xml"), Spec.class);
					
					for(Endpoint endpoint : spec.endpoints) {
						if(endpoint.reqParams == null) {
							endpoint.reqParams = new ArrayList<>();
						}
					}
					
					System.out.println(xmlMapper.writeValueAsString(spec));
					
					try {
						String html = HtmlGen.generateHtml(spec);
						var htmlFile = new File(arg + ".html");
						htmlFile.createNewFile();
						try(var fileOutputStream = new FileOutputStream(htmlFile)) {
							fileOutputStream.write(html.getBytes());
						}
					} catch(IOException e) {
						System.err.println("Failed to write file:\t" + arg + ".html");
					}
				} catch(IOException e) {
					System.err.println("Failed to read file:\t" + arg + ".xml");
					e.printStackTrace();
				}
			}
		}
	}
}
