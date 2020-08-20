package com.masflam.radxg;

import com.masflam.radxg.xmltags.ApiType;
import com.masflam.radxg.xmltags.Endpoint;
import com.masflam.radxg.xmltags.ReqParam;
import com.masflam.radxg.xmltags.Resp;
import com.masflam.radxg.xmltags.Spec;

public final class HtmlGen {
	private HtmlGen() {}
	
	public static String generateHtml(Spec spec) {
		var outBuilder = new StringBuilder();
		outBuilder.append("<div id=\"docs\">");
		
		genTypes(outBuilder, spec);
		genEndpoints(outBuilder, spec);
		
		return outBuilder.toString();
	}
	
	private static void genTypes(StringBuilder outBuilder, Spec spec) {
		if(spec.types.size() > 0) {
			outBuilder.append("<div id=\"data-types\">")
				.append("<h2>API data types</h2>")
				.append("<table><thead><tr>")
				.append("<td>Type name</td><td>Explanation</td>")
				.append("</tr></thead><tbody>");
		}
		
		for(ApiType type : spec.types) {
			genType(outBuilder, type);
		}
		
		if(spec.types.size() > 0) {
			outBuilder.append("</tbody></table>");
		}
	}
	
	private static void genType(StringBuilder outBuilder, ApiType type) {
		outBuilder.append("<tr>")
			.append("<td><code>").append(type.name).append("</code></td>")
			.append("<td>").append(type.desc).append("</td>")
			.append("</tr>");
	}
	
	private static void genEndpoints(StringBuilder outBuilder, Spec spec) {
		outBuilder
			.append("</div><div id=\"endpoints\">")
			.append("<h2>API endpoints</h2>");
		
		for(Endpoint endpoint : spec.endpoints) {
			genEndpoint(outBuilder, endpoint);
		}
		
		outBuilder.append("</div></div>");
	}
	
	private static void genEndpoint(StringBuilder outBuilder, Endpoint endpoint) {
		if(endpoint.desc == null) {
			endpoint.desc = "";
		}
		
		if(endpoint.reqBody != null) {
			if(endpoint.reqBody.desc == null) {
				endpoint.reqBody.desc = "";
			}
		}
		
		outBuilder.append("<div id=\"").append(endpoint.method).append(':').append(endpoint.path).append("\">");
		
		genMethodPathDesc(outBuilder, endpoint);
		genReqParams(outBuilder, endpoint);
		genReqBody(outBuilder, endpoint);
		genResponses(outBuilder, endpoint);
		
		outBuilder.append("</div>");
	}
	
	private static void genMethodPathDesc(StringBuilder outBuilder, Endpoint endpoint) {
		outBuilder.append("<h3><code>")
			.append(endpoint.method)
			.append("</code> <code>")
			.append(endpoint.path)
			.append("</code></h3>")
			.append("<p>").append(endpoint.desc).append("</p>");
	}
	
	private static void genReqParams(StringBuilder outBuilder, Endpoint endpoint) {
		if(endpoint.reqParams.size() > 0) {
			outBuilder.append("<table><thead><tr>")
				.append("<td>Param name</td><td>Param type</td><td>Default value</td><td>Description</td>")
				.append("</tr></thead><tbody>");
		}
		
		for(ReqParam param : endpoint.reqParams) {
			genReqParam(outBuilder, param);
		}
		
		if(endpoint.reqParams.size() > 0) {
			outBuilder.append("</tbody></table>");
		}
	}
	
	private static void genReqParam(StringBuilder outBuilder, ReqParam param) {
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
	
	private static void genReqBody(StringBuilder outBuilder, Endpoint endpoint) {
		if(endpoint.reqBody != null) {
			outBuilder.append("<h4>Request body: <code>").append(endpoint.reqBody.type).append("</code></h4>")
				.append("<p>").append(endpoint.reqBody.desc).append("</p>");
		}
	}
	
	private static void genResponses(StringBuilder outBuilder, Endpoint endpoint) {
		outBuilder.append("<h4>Responses</h4>")
			.append("<table><thead><tr>")
			.append("<td>Status code</td>")
			.append("<td>Meaning</td>")
			.append("<td>Body type</td>")
			.append("<td>Explanation</td>")
			.append("</tr></thead><tbody>");
		
		for(Resp response : endpoint.responses) {
			genResponse(outBuilder, response);
		}
		
		outBuilder.append("</tbody></table>");
	}
	
	private static void genResponse(StringBuilder outBuilder, Resp response) {
		if(response.desc == null) {
			response.desc = "";
		}
		
		if(response.body != null) {
			if(response.body.desc == null) {
				response.body.desc = "";
			}
			
			outBuilder.append("<tr>")
				.append("<td><code>").append(response.code).append("</code></td>")
				.append("<td>").append(response.desc).append("</td>")
				.append("<td><code>").append(response.body.type).append("</code></td>")
				.append("<td>").append(response.body.desc).append("</td>")
				.append("</tr>");
		} else {
			outBuilder.append("<tr>")
				.append("<td><code>").append(response.code).append("</code></td>")
				.append("<td>").append(response.desc).append("</td>")
				.append("<td>none</td>")
				.append("<td>none</td>")
				.append("</tr>");
		}
	}
	
}
