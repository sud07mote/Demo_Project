package com.innovecture.utility;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PredictTemperatureUtility {

	@Autowired
	RestTemplate restTemplate;

	private PredictTemperatureUtility() {
		super();
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> restGetCall(String url, Map<String, Object> requestParam, final Class<?> className) {

		return (ResponseEntity<Object>) restTemplate.exchange(url, HttpMethod.GET, getRequestBody(requestParam),
				className);
	}

	private HttpEntity<Object> getRequestBody(Map<String, Object> requestParam) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(requestParam, headers);
	}

	public Object fromObjToClass(Object obj, Class<?> className) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(obj, className);
	}

	public Object fromStringToObject(final Object obj, final Class<?> className) throws IOException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(obj.toString(), className);

	}

}
