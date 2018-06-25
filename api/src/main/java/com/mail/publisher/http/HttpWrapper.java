package com.mail.publisher.http;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * Wrapper class for Handling Http Requests to external clients
 * 
 * @author MadanMeenu
 *
 */
@Component
public class HttpWrapper{
	
	/**
	 * Method to handle http post request
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	public APIHttpResponse handlePost(APIHttpRequest request) throws Exception {
		
		HttpResponse<String> response = null;
		HttpRequestWithBody uniRequest = Unirest.post(request.getUrl());

		// Set authentication if available
		if(request.getAuthentication() != null) {
			uniRequest.basicAuth(request.getAuthentication().getKey(), request.getAuthentication().getValue());
		}
		
		// Add headers to request object if available
		if(MapUtils.isNotEmpty(request.getHeaders())) {
			request.getHeaders().forEach((key, value) -> uniRequest.header(key, value));
		}
		
		// Add params to query string if available
		if(MapUtils.isNotEmpty(request.getParams())) {
			request.getParams().forEach((key, value) -> uniRequest.queryString(key, value));
		}
		
		// Include body only if available
		if(StringUtils.isNotEmpty(request.getBody())) {
			uniRequest.body(request.getBody());
		}
		
		// Invoke request and fetch response
		response = uniRequest.asString();
		//System.out.println("Status ===> "+response.getStatus()+ " :: "+response.getBody());
		
		return handleResponse(response);
	}
	
	/**
	 * Method to handle http get request
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	public APIHttpResponse handleGet(APIHttpRequest request) throws Exception {
		GetRequest uniRequest = Unirest.get(request.getUrl());

		// Add headers to request object if available
		if(MapUtils.isNotEmpty(request.getHeaders())) {
			request.getHeaders().forEach((key, value) -> uniRequest.header(key, value));
		}
		
		// Add params to query string if available
		if(MapUtils.isNotEmpty(request.getParams())) {
			request.getParams().forEach((key, value) -> uniRequest.queryString(key, value));
		}
		
		HttpResponse<String> response = uniRequest.asString();
		
		return handleResponse(response);
	}
		

	/**
	 * Handle response for the request that was successful
	 * @param response
	 * @return response
	 * @throws Exception
	 */
	private APIHttpResponse handleResponse(HttpResponse<String> response) throws Exception {

		int code = response.getStatus();
		String message = response.getBody();
		
		return new APIHttpResponse(code, message);
	}
}
