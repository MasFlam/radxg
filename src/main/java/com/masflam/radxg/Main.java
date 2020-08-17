package com.masflam.radxg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	
	private static String generateHtml(Spec spec) {
		var outBuilder = new StringBuilder();
		
		outBuilder.append("<div id=\"docs\">");
		
		
		
		/*  <DATA TYPES>  */
		if(spec.types.size() > 0) {
			outBuilder.append("<div id=\"data-types\">")
				.append("<h2>API data types</h2>")
				.append("<table><thead><tr>")
				.append("<td>Type name</td><td>Explanation</td>")
				.append("</tr></thead><tbody>");
		}
		
		for(ApiType type : spec.types) {
			outBuilder.append("<tr>")
				.append("<td><code>").append(type.name).append("</code></td>")
				.append("<td>").append(type.desc).append("</td>")
				.append("</tr>");
		}
		
		if(spec.types.size() > 0) {
			outBuilder.append("</tbody></table>");
		}
		/*  </DATA TYPES>  */
		
		
		
		/*  <ENDPOINTS>  */
		outBuilder
			.append("</div><div id=\"endpoints\">")
			.append("<h2>API endpoints</h2>");
		
		for(Endpoint endpoint : spec.endpoints) {
			if(endpoint.desc == null) {
				endpoint.desc = "";
			}
			
			if(endpoint.reqBody != null) {
				if(endpoint.reqBody.desc == null) {
					endpoint.reqBody.desc = "";
				}
			}
			
			
			
			/*  <METHOD, PATH, DESC>  */
			outBuilder.append("<div id=\"").append(endpoint.method).append(':').append(endpoint.path).append("\">")
				.append("<h3><code>")
					.append(endpoint.method)
					.append("</code> <code>")
					.append(endpoint.path)
				.append("</code></h3>")
				.append("<p>").append(endpoint.desc).append("</p>");
			/*  </METHOD, PATH, DESC>  */
			
			
			
			/*  <REQUEST PARAMS>  */
			if(endpoint.reqParams.size() > 0) {
				outBuilder.append("<table><thead><tr>")
					.append("<td>Param name</td><td>Param type</td><td>Default value</td><td>Description</td>")
					.append("</tr></thead><tbody>");
			}
			
			for(ReqParam param : endpoint.reqParams) {
				String def;
				if(param.def == null) {
					def = "none";
				} else {
					def = "<code>" + param.def + "</code>";
				}
				
				if(param.desc == null) {
					param.desc = "";
				}
				
				outBuilder.append("<tr>")
					.append("<td>").append(param.name).append("</td>")
					.append("<td><code>").append(param.type).append("</code></td>")
					.append("<td>").append(def).append("</td>")
					.append("<td>").append(param.desc).append("</td>")
				.append("</tr>");
			}
			
			if(endpoint.reqParams.size() > 0) {
				outBuilder.append("</tbody></table>");
			}
			/*  </REQUEST PARAMS>  */
			
			
			
			/*  <REQUEST BODY>  */
			if(endpoint.reqBody != null) {
				outBuilder.append("<h4>Request body: <code>").append(endpoint.reqBody.type).append("</code></h4>")
					.append("<p>").append(endpoint.reqBody.desc).append("</p>");
			}
			/*  </REQUEST BODY>  */
			
			
			
			/*  <RESPONSES>  */
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
			/*  </RESPONSES>  */
		}
		
		outBuilder.append("</div>").append("</div>");
		/*  </ENDPOINTS>  */
		
		
		
		return outBuilder.toString();
	}
	
	private static String generateMd(Spec spec) {
		var outBuilder = new StringBuilder();
		
		
		
		/*  <DATA TYPES>  */
		outBuilder.append("## API data types\n")
			.append("| Type name | Explanation |\n")
			.append("| --------- | ----------- |\n");
		
		for(ApiType type : spec.types) {
			outBuilder.append("| `").append(type.name).append("` | ").append(type.desc).append(" |\n");
		}
		/*  </DATA TYPES>  */
		
		
		
		/*  <ENDPOINTS>  */
		outBuilder.append("\n## API endpoints\n\n");
		
		for(Endpoint endpoint : spec.endpoints) {
			if(endpoint.desc == null) {
				endpoint.desc = "";
			}
			
			if(endpoint.reqBody != null) {
				if(endpoint.reqBody.desc == null) {
					endpoint.reqBody.desc = "";
				}
			}
			
			
			
			/*  <METHOD, PATH, DESC>  */
			outBuilder.append("### `").append(endpoint.method).append("` `").append(endpoint.path).append("`\n")
				.append(endpoint.desc).append('\n');
			/*  </METHOD, PATH, DESC>  */
			
			
			
			/*  <REQUEST PARAMS>  */
			if(endpoint.reqParams.size() > 0) {
				outBuilder.append("| Param name | Param type | Default value | Description |\n")
					.append("| ---------- | ---------- | ------------- | ----------- |\n");
			}
			
			for(ReqParam param : endpoint.reqParams) {
				String def;
				if(param.def == null) {
					def = "none";
				} else {
					def = "`" + param.def + "`";
				}
				
				if(param.desc == null) {
					param.desc = "";
				}
				
				outBuilder.append("| ").append(param.name)
					.append(" | `").append(param.type)
					.append("` | ").append(def)
					.append(" | ").append(param.desc)
					.append(" |\n");
			}
			/*  </REQUEST PARAMS>  */
			
			
			
			/*  <REQUEST BODY>  */
			if(endpoint.reqBody != null) {
				outBuilder.append("#### Request body: `").append(endpoint.reqBody.type).append("`\n")
					.append(endpoint.reqBody.desc).append('\n');
			}
			/*  </REQUEST BODY>  */
			
			
			
			/*  <RESPONSES>  */
			outBuilder.append("#### Responses\n")
				.append("| Status code | Meaning | Body type | Explanation |\n")
				.append("| ----------- | ------- | --------- | ----------- |\n");
			
			for(Resp response : endpoint.responses) {
				outBuilder.append("| `").append(response.code)
					.append("` | ").append(response.desc)
					.append(" | `").append(response.body.type)
					.append("` | ").append(response.body.desc)
					.append(" |\n");
			}
			/*  </RESPONSES>  */
		}
		/*  </ENDPOINTS>  */
		
		
		
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
					
					for(ApiType type : spec.types) {
						type.desc = type.desc.trim();
					}
					for(Endpoint endpoint : spec.endpoints) {
						endpoint.desc = endpoint.desc.trim();
						for(ReqParam param : endpoint.reqParams) {
							param.desc = param.desc.trim();
						}
						for(Resp response : endpoint.responses) {
							response.desc = response.desc.trim();
							response.body.desc = response.body.desc.trim();
						}
					}
					
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
					
					try {
						String md = generateMd(spec);
						var mdFile = new File(arg + ".md");
						mdFile.createNewFile();
						try(var fileOutputStream = new FileOutputStream(mdFile)) {
							fileOutputStream.write(md.getBytes());
						}
					} catch(IOException e) {
						System.err.println("Failed to write file:\t" + arg + ".md");
					}
				} catch(IOException e) {
					System.err.println("Failed to read file:\t" + arg + ".xml");
					e.printStackTrace();
				}
			}
		}
	}
}
