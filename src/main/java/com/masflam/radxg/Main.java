package com.masflam.radxg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
	
	private static String generateHtml(Spec spec) {
		var outBuilder = new StringBuilder();
		
		outBuilder.append("<div id=\"docs\">")
			.append("<div id=\"data-types\">")
			.append("<h2>API data types</h2>")
			.append("<table><thead><tr>")
				.append("<td>Type name</td><td>Explanation</td>")
			.append("</tr></thead><tbody>");
		
		for(ApiType type : spec.types) {
			outBuilder.append("<tr>")
				.append("<td><code>").append(type.name).append("</code></td>")
				.append("<td>").append(type.desc).append("</td>")
				.append("</tr>");
		}
		
		outBuilder.append("</tbody></table>")
			.append("</div><div id=\"endpoints\">")
			.append("<h2>API endpoints</h2>");
		
		for(Endpoint endpoint : spec.endpoints) {
			if(endpoint.desc == null) {
				endpoint.desc = "";
			}
			
			if(endpoint.reqBody.desc == null) {
				endpoint.reqBody.desc = "";
			}
			
			outBuilder.append("<div id=\"").append(endpoint.method).append(':').append(endpoint.path).append("\">")
				.append("<h3><code>")
					.append(endpoint.method)
					.append("</code> <code>")
					.append(endpoint.path)
				.append("</code></h3>")
				.append("<p>").append(endpoint.desc).append("</p>")
				.append("<table><thead><tr>")
					.append("<td>Param name</td><td>Param type</td><td>Default value</td><td>Description</td>")
				.append("</tr></thead><tbody>");
			
			for(ReqParam param : endpoint.reqParams) {
				if(param.def == null) {
					param.def = "none";
				} else {
					param.def = "<code>" + param.def + "</code>";
				}
				
				if(param.desc == null) {
					param.desc = "";
				}
				
				outBuilder.append("<tr>")
					.append("<td>").append(param.name).append("</td>")
					.append("<td><code>").append(param.type).append("</code></td>")
					.append("<td>").append(param.def).append("</td>")
					.append("<td>").append(param.desc).append("</td>")
				.append("</tr>");
			}
			
			outBuilder.append("</tbody></table>")
				.append("<h4>Request body: <code>").append(endpoint.reqBody.type).append("</code></h4>")
				.append("<p>").append(endpoint.reqBody.desc).append("</p>");
			
			outBuilder.append("<h4>Responses</h4>")
				.append("<table><thead><tr>")
					.append("<td>Status code</td>")
					.append("<td>Meaning</td>")
					.append("<td>Body type</td>")
					.append("<td>Explanation</td>")
				.append("</tr></thead><tbody>");
			
			for(Resp response : endpoint.responses) {
				if(response.desc == null) {
					response.desc = "";
				}
				
				if(response.body.desc == null) {
					response.body.desc = "";
				}
				
				outBuilder.append("<tr>")
					.append("<td><code>").append(response.code).append("</code></td>")
					.append("<td>").append(response.desc).append("</td>")
					.append("<td><code>").append(response.body.type).append("</code></td>")
					.append("<td>").append(response.body.desc).append("</td>")
				.append("</tr>");
			}
			
			outBuilder.append("</tbody></table>");
		}
		
		outBuilder.append("</div>").append("</div>");
		
		return outBuilder.toString();
	}
	
	public static void main(String[] args) {
		if(args.length == 0) {
			System.err.println("Usage:\tradxg <doc-xml-basename>");
		} else {
			for(String arg : args) {
				System.out.println("Reading file:\t" + arg + ".xml");
				
				ObjectMapper xmlMapper = new XmlMapper();
				
				try {
					Spec spec = xmlMapper.readValue(new File(arg + ".xml"), Spec.class);
					
					System.out.println(xmlMapper.writeValueAsString(spec));
					
					try {
						String html = generateHtml(spec);
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
