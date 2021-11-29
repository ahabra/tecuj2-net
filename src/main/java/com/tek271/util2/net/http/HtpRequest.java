package com.tek271.util2.net.http;

import com.tek271.util2.collection.ListOfPairs;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringRequestContent;

import java.nio.charset.StandardCharsets;

public class HtpRequest {
	HtpMethod htpMethod = HtpMethod.GET;
	Url url = new Url();
	boolean trustAllSsl = false;
	ListOfPairs<String, String> headers = new ListOfPairs<>();
	ListOfPairs<String, String> parameters = new ListOfPairs<>();
	String textToPost;
	HtpMediaType htpMediaType = HtpMediaType.textPlain;

	public HtpRequest url(String text) {
		url.parse(text);
		return this;
	}

	public HtpRequest addHeadersToRequest(Request request) {
		headers.forEach((k,v) -> {
			request.headers(httpFields -> httpFields.add(k, v));
		});
		return this;
	}

	public HtpRequest setRequestData(Request request) {
		if (textToPost != null && htpMethod == HtpMethod.POST) {
			StringRequestContent content = new StringRequestContent(textToPost, StandardCharsets.UTF_8);
			request.body(content);
		}
		parameters.forEach(request::param);
		return this;
	}

	@Override
	public String toString() {
		return htpMethod + " " + url.toString();
	}

}
