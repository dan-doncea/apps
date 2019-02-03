package com.iosapp.exception;

import java.util.Map;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
		Throwable ex = getError(request);
		
        Map<String, Object> map = super.getErrorAttributes(request, includeStackTrace);
              map.put("status", checkHttpStatus(ex.getClass().getSimpleName()));
              map.put("message", ex.getMessage());
              map.put("error", null);
              return map;
	}

	private static HttpStatus checkHttpStatus(String exName) {
		System.out.println(exName);
	        switch (exName) {
	        	case "AuthenticationCredentialsNotFoundException":
	        		return HttpStatus.FORBIDDEN;
	            case "AuthenticationException":
	                return HttpStatus.UNAUTHORIZED;
	            case "NoSuchElementException":
	                return HttpStatus.NOT_FOUND;
	            default:
	            	return HttpStatus.INTERNAL_SERVER_ERROR;
	        }
	    }
	
}
