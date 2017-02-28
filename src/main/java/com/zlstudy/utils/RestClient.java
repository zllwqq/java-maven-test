package com.zlstudy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClient {
	
	private Logger log = LoggerFactory.getLogger(RestClient.class);
	
	private int connectTimeout = -1;

	private int readTimeout = -1;
	
	public String get(String path) {
		String output = "ERROR";
		HttpURLConnection conn = null;
		
		try {
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setConnectTimeout(30000);
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			
			log.info("http返回code：" + conn.getResponseCode());

			BufferedReader br = new BufferedReader(new InputStreamReader(conn
					.getInputStream(), "utf-8"));

			System.out.println("Output from Server .... \n");
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			output = buffer.toString();
		} catch (Exception e) {
			log.error("GET调用接口{}错误：{}", path, e.getMessage());
		}
		return output;
	}
	
	public String post(String path, Object request) throws IOException {
		String output = "ERROR";
		HttpURLConnection conn = null;
		
		URL url = new URL(path);
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setConnectTimeout(30000);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		String jsonString = JsonUtils.toJson(request);
		
		OutputStream os = conn.getOutputStream();
		os.write(jsonString.getBytes("UTF-8"));
		os.flush();
		
		log.info("http返回code：" + conn.getResponseCode());

		BufferedReader br = new BufferedReader(new InputStreamReader(conn
				.getInputStream(), "utf-8"));

		System.out.println("Output from Server .... \n");
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		output = buffer.toString();
		
		return output;
	}
	
	public String put(String path, Object request) throws IOException {
		String output = "ERROR";
		HttpURLConnection conn = null;
		
		URL url = new URL(path);
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setConnectTimeout(30000);
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
		String jsonString = JsonUtils.toJson(request);
		
		OutputStream os = conn.getOutputStream();
		os.write(jsonString.getBytes("UTF-8"));
		os.flush();
		
		log.info("http返回code：" + conn.getResponseCode());

		BufferedReader br = new BufferedReader(new InputStreamReader(conn
				.getInputStream(), "utf-8"));

		System.out.println("Output from Server .... \n");
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		output = buffer.toString();
		
		return output;
	}
	
	
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		if (this.connectTimeout >= 0) {
			connection.setConnectTimeout(this.connectTimeout);
		}
		if (this.readTimeout >= 0) {
			connection.setReadTimeout(this.readTimeout);
		}

		connection.setDoInput(true);

		if ("GET".equals(httpMethod)) {
			connection.setInstanceFollowRedirects(true);
		} else {
			connection.setInstanceFollowRedirects(false);
		}

		if ("POST".equals(httpMethod) || "PUT".equals(httpMethod)
				|| "PATCH".equals(httpMethod) || "DELETE".equals(httpMethod)) {
			connection.setDoOutput(true);
		} else {
			connection.setDoOutput(false);
		}
		connection.setRequestMethod(httpMethod);
	}
}
