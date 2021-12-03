package com.tek271.util2.net.http;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringRequestContent;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

/** General HTTP utils */
public class HttpTools {

	public static boolean isHttp(String path) {
		return startsWithIgnoreCase(path, "http://")
				|| startsWithIgnoreCase(path, "https://");
	}

	public static String get(String path) {
		URL url = Url.toURL(path);
		try {
			return IOUtils.toString(url, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Request htpRequestToJettyRequest(HtpRequest2 htpRequest, HttpClient jettyClient) {
		Request jettyRequest = jettyClient.newRequest(htpRequest.getUrl().getText());
		jettyRequest.method(htpRequest.getMethod().toString());
		jettyRequest.timeout(htpRequest.getTimeoutMillis(), TimeUnit.MILLISECONDS);

		htpRequest.getHeaders().forEach((k, v) -> jettyRequest.headers(httpFields -> httpFields.add(k, v)));

		addPostBody(htpRequest, jettyRequest);

		return jettyRequest;
	}

	private static void addPostBody(HtpRequest2 htpRequest, Request jettyRequest) {
		if (htpRequest.getMethod() != HtpMethod.POST) return;

		String textToPost = htpRequest.getTextToPost();
		if (textToPost == null) return;

		StringRequestContent content = new StringRequestContent(textToPost, StandardCharsets.UTF_8);
		jettyRequest.body(content);


		// FIXME should I add parameters ???
	}


}
