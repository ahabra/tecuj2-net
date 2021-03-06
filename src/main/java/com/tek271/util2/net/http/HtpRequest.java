package com.tek271.util2.net.http;

import com.tek271.util2.collection.ListOfPairs;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HtpRequest {
	private HtpMethod method = HtpMethod.GET;
	private final Url url = new Url();
	private boolean trustAllSsl = false;
	private final ListOfPairs<String, String> headers = new ListOfPairs<>();
	private HtpMediaType contentType = HtpMediaType.textPlain;
	private String textToPost;
	private long timeout = 30;
	private TimeUnit timeoutUnit = TimeUnit.SECONDS;


	public HtpRequest method(HtpMethod htpMethod) {
		this.method = htpMethod;
		return this;
	}

	public HtpMethod getMethod() {
		return method;
	}

	public HtpRequest url(String text) {
		url.parse(text);
		return this;
	}

	public Url getUrl() {
		return url;
	}

	public HtpRequest loggingExcludedParams(String... excluded) {
		url.loggingExcludedParams(excluded);
		return this;
	}

	public Set<String> getLoggingExcludedParams() {
		return url.requestParams.getExcludedParams();
	}

	public HtpRequest trustAllSsl(boolean isTrust) {
		trustAllSsl = isTrust;
		return this;
	}

	public HtpRequest trustAllSsl() {
		return trustAllSsl(true);
	}

	public boolean isTrustAllSsl() {
		return trustAllSsl;
	}

	public HtpRequest header(String name, String value) {
		headers.add(name, value);
		return this;
	}

	public ListOfPairs<String, String> getHeaders() {
		return headers;
	}

	public HtpRequest parameter(String name, String value) {
		url.requestParams.add(name, value);
		return this;
	}

	public HtpRequest contentType(HtpMediaType contentType) {
		this.contentType = contentType;
		return this;
	}

	public HtpMediaType getContentType() {
		return contentType;
	}

	public HtpRequest textToPost(String text) {
		this.textToPost = text;
		return this;
	}

	public String getTextToPost() {
		return textToPost;
	}

	public HtpRequest timeout(long timeout, TimeUnit timeoutUnit) {
		this.timeout = timeout;
		this.timeoutUnit = timeoutUnit;
		return this;
	}

	public long getTimeoutMillis() {
		return timeoutUnit.toMillis(timeout);
	}

	@Override
	public String toString() {
		return method + " " + url.toString();
	}
}
