package com.masflam.radxg;

import com.masflam.radxg.xmltags.ApiType;
import com.masflam.radxg.xmltags.Endpoint;
import com.masflam.radxg.xmltags.ReqParam;
import com.masflam.radxg.xmltags.Resp;
import com.masflam.radxg.xmltags.Spec;

public final class HtmlGen {
	private HtmlGen() {}
	
	public static String CSS_DATA_TYPES_HEADER_CLASS = "radxg-data-types-header";
	public static String CSS_DATA_TYPES_TABLE_CLASS = "radxg-data-types-table";
	public static String CSS_DATA_TYPES_THEAD_CLASS = "radxg-data-types-thead";
	public static String CSS_DATA_TYPES_TBODY_CLASS = "radxg-data-types-tbody";
	public static String CSS_DATA_TYPE_NAME_TD_CLASS = "radxg-data-type-name-td";
	public static String CSS_DATA_TYPE_DESC_TD_CLASS = "radxg-data-type-desc-td";
	public static String CSS_ENDPOINTS_HEADER_CLASS = "radxg-endpoints-header";
	public static String CSS_ENDPOINT_DIV_CLASS = "radxg-endpoint-div";
	public static String CSS_ENDPOINT_HEADER_CLASS = "radxg-endpoint-header";
	public static String CSS_ENDPOINT_METHOD_CLASS = "radxg-endpoint-method";
	public static String CSS_ENDPOINT_PATH_CLASS = "radxg-endpoint-path";
	public static String CSS_ENDPOINT_DESC_CLASS = "radxg-endpoint-desc";
	public static String CSS_REQ_PARAMS_TABLE_CLASS = "radxg-req-params-table";
	public static String CSS_REQ_PARAMS_THEAD_CLASS = "radxg-req-params-thead";
	public static String CSS_REQ_PARAMS_TBODY_CLASS = "radxg-req-params-tbody";
	public static String CSS_REQ_PARAM_NAME_TD_CLASS = "radxg-req-param-name-td";
	public static String CSS_REQ_PARAM_TYPE_TD_CLASS = "radxg-req-param-type-td";
	public static String CSS_REQ_PARAM_DEFAULT_TD_CLASS = "radxg-req-param-default-td";
	public static String CSS_REQ_PARAM_DESC_TD_CLASS = "radxg-req-param-desc-td";
	public static String CSS_REQ_BODY_HEADER_CLASS = "radxg-req-body-header";
	public static String CSS_REQ_BODY_TYPE_CLASS = "radxg-req-body-type";
	public static String CSS_REQ_BODY_DESC_CLASS = "radxg-req-body-desc";
	public static String CSS_RESPONSES_HEADER_CLASS = "radxg-responses-header";
	public static String CSS_RESPONSES_TABLE_CLASS = "radxg-responses-table";
	public static String CSS_RESPONSES_THEAD_CLASS = "radxg-responses-thead";
	public static String CSS_RESPONSES_TBODY_CLASS = "radxg-responses-tbody";
	public static String CSS_RESPONSE_CODE_TD_CLASS = "radxg-response-code-td";
	public static String CSS_RESPONSE_MEANING_TD_CLASS = "radxg-response-meaning-td";
	public static String CSS_RESPONSE_BODY_TYPE_TD_CLASS = "radxg-response-body-type-td";
	public static String CSS_RESPONSE_BODY_DESC_TD_CLASS = "radxg-response-body-desc-td";
	
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
				.append("<h2 class=\"").append(CSS_DATA_TYPES_HEADER_CLASS).append("\">API data types</h2>")
				.append("<table class=\"").append(CSS_DATA_TYPES_TABLE_CLASS).append("\">")
				.append("<thead class=\"").append(CSS_DATA_TYPES_THEAD_CLASS).append("\"><tr>")
				.append("<td>Type name</td><td>Explanation</td>")
				.append("</tr></thead><tbody class=\"").append(CSS_DATA_TYPES_TBODY_CLASS).append("\">");
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
			.append("<td class=\"").append(CSS_DATA_TYPE_NAME_TD_CLASS)
				.append("\"><code>").append(type.name)
			.append("</code></td>")
			.append("<td class=\"").append(CSS_DATA_TYPE_DESC_TD_CLASS).append("\">")
				.append(type.desc)
			.append("</td>")
		.append("</tr>");
	}
	
	private static void genEndpoints(StringBuilder outBuilder, Spec spec) {
		outBuilder
			.append("</div><div id=\"endpoints\">")
			.append("<h2 class=\"").append(CSS_ENDPOINTS_HEADER_CLASS).append("\">API endpoints</h2>");
		
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
		
		outBuilder.append("<div id=\"")
			.append(endpoint.method).append(':').append(endpoint.path)
		.append("\" class=\"").append(CSS_ENDPOINT_DIV_CLASS).append("\">");
		
		genMethodPathDesc(outBuilder, endpoint);
		genReqParams(outBuilder, endpoint);
		genReqBody(outBuilder, endpoint);
		genResponses(outBuilder, endpoint);
		
		outBuilder.append("</div>");
	}
	
	private static void genMethodPathDesc(StringBuilder outBuilder, Endpoint endpoint) {
		outBuilder.append("<h3 class=\"").append(CSS_ENDPOINT_HEADER_CLASS)
			.append("\"><code class=\"").append(CSS_ENDPOINT_METHOD_CLASS).append("\">")
				.append(endpoint.method)
			.append("</code> <code class=\"").append(CSS_ENDPOINT_PATH_CLASS).append("\">")
				.append(endpoint.path)
			.append("</code></h3>")
			.append("<p class=\"").append(CSS_ENDPOINT_DESC_CLASS).append("\">")
				.append(endpoint.desc)
			.append("</p>");
	}
	
	private static void genReqParams(StringBuilder outBuilder, Endpoint endpoint) {
		if(endpoint.reqParams.size() > 0) {
			outBuilder.append("<table class=\"").append(CSS_REQ_PARAMS_TABLE_CLASS).append("\">")
				.append("<thead class=\"").append(CSS_REQ_PARAMS_THEAD_CLASS).append("\"><tr>")
				.append("<td>Param name</td><td>Param type</td><td>Default value</td><td>Description</td>")
				.append("</tr></thead><tbody class=\"").append(CSS_REQ_PARAMS_TBODY_CLASS).append("\">");
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
			.append("<td class=\"").append(CSS_REQ_PARAM_NAME_TD_CLASS).append("\">")
				.append(param.name)
			.append("</td>")
			.append("<td class=\"").append(CSS_REQ_PARAM_TYPE_TD_CLASS).append("\"><code>")
				.append(param.type)
			.append("</code></td>")
			.append("<td class=\"").append(CSS_REQ_PARAM_DEFAULT_TD_CLASS).append("\">")
				.append(def)
			.append("</td>")
			.append("<td class=\"").append(CSS_REQ_PARAM_DESC_TD_CLASS).append("\">")
				.append(param.desc)
			.append("</td>")
		.append("</tr>");
	}
	
	private static void genReqBody(StringBuilder outBuilder, Endpoint endpoint) {
		if(endpoint.reqBody != null) {
			outBuilder.append("<h4 class=\"").append(CSS_REQ_BODY_HEADER_CLASS)
				.append("\">Request body: <code class=\"").append(CSS_REQ_BODY_TYPE_CLASS).append("\">")
					.append(endpoint.reqBody.type)
				.append("</code></h4>")
				.append("<p class=\"").append(CSS_REQ_BODY_DESC_CLASS).append("\">")
					.append(endpoint.reqBody.desc)
				.append("</p>");
		}
	}
	
	private static void genResponses(StringBuilder outBuilder, Endpoint endpoint) {
		outBuilder.append("<h4 class=\"").append(CSS_RESPONSES_HEADER_CLASS).append("\">Responses</h4>")
			.append("<table class=\"").append(CSS_RESPONSES_TABLE_CLASS).append("\">")
			.append("<thead class=\"").append(CSS_RESPONSES_THEAD_CLASS).append("\"><tr>")
			.append("<td>Status code</td>")
			.append("<td>Meaning</td>")
			.append("<td>Body type</td>")
			.append("<td>Explanation</td>")
			.append("</tr></thead><tbody class=\"").append(CSS_RESPONSES_TBODY_CLASS).append("\">");
		
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
			
			genResponse0(
				outBuilder,
				response.code,
				response.desc,
				"<code>" + response.body.type + "</code>",
				response.body.desc
			);
		} else {
			genResponse0(outBuilder, response.code, response.desc, "", "");
		}
	}
	
	private static void genResponse0(StringBuilder outBuilder, int code, String desc, String bodyType, String bodyDesc) {
		outBuilder.append("<tr>")
			.append("<td class=\"").append(CSS_RESPONSE_CODE_TD_CLASS).append("\"><code>")
				.append(code)
			.append("</code></td>")
			.append("<td class=\"").append(CSS_RESPONSE_MEANING_TD_CLASS).append("\">")
				.append(desc)
			.append("</td>")
			.append("<td class=\"").append(CSS_RESPONSE_BODY_TYPE_TD_CLASS).append("\">")
				.append(bodyType)
			.append("</td>")
			.append("<td class=\"").append(CSS_RESPONSE_BODY_DESC_TD_CLASS).append("\">")
				.append(bodyDesc)
			.append("</td>")
		.append("</tr>");
	}
	
}
