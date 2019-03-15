package com.personal.test.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class TestUtil {
	
	private static final int PORT = 8888;
	private static final String HOST = "http://localhost";

	/**
	 * 
	 * @param path The request path.
	 * @param body Body of the request.
	 * @param headers Request headers.
	 * @return the HTTPResponse
	 * @throws ClientProtocolException
	 * @throws IOException 
	 */
	public static HttpResponse executePostRequest(String path, String body, Map<String, String> headers)
			throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		String uri = HOST + ":" + PORT + "/" + path;
		HttpUriRequest request = new HttpPost(uri);
		if (body != null) {
			((HttpPost) request).setEntity(new ByteArrayEntity(body.getBytes("UTF-8")));
		}
		if (headers != null) {
			for (Map.Entry<String, String> h : headers.entrySet()) {
				request.addHeader(h.getKey(), h.getValue());
			}
		}
		return client.execute(request);
	}

	/**
	 * 
	 * @param path The request path
	 * @param headers The headers
	 * @return The HttpResponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse executeGetRequest(String path, Map<String, String> headers)
			throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		String uri = HOST + ":" + PORT + "/" + path;
		HttpUriRequest request = new HttpGet(uri);
		if (headers != null) {
			for (Map.Entry<String, String> h : headers.entrySet()) {
				request.addHeader(h.getKey(), h.getValue());
			}
		}
		return client.execute(request);
	}
	
	/**
	 * 
	 * @param size The size of the list.
	 * @return a list of integers
	 */
	public static List<Integer> generateIntegerIds(int size) {
        return IntStream.range(0, size).boxed().collect(Collectors.toList());
    }
	
	/**
	 * 
	 * @param size The size of the list.
	 * @return a list of random string ids
	 */
	public static List<String> generateRandomStrings(int size) {
		List<String> randomIds = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			 randomIds.add(UUID.randomUUID().toString());
		}
		return randomIds;
	}
	
}
