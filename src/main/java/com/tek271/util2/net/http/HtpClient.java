package com.tek271.util2.net.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.dynamic.HttpClientTransportDynamic;
import org.eclipse.jetty.io.ClientConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.util.concurrent.TimeUnit;

public class HtpClient {
	private static final Logger LOGGER = LogManager.getLogger(HtpClient.class);

	public final HtpRequest htpRequest = new HtpRequest();
	long timeout = 30;
	TimeUnit timeoutUnit = TimeUnit.SECONDS;

	public HtpClient url(String text) {
		htpRequest.url(text);
		return this;
	}

	public HtpClient loggingExcludedParams(String... excluded) {
		htpRequest.url.loggingExcludedParams(excluded);
		return this;
	}

	public HtpClient trustAllSsl(boolean isTrust) {
		htpRequest.trustAllSsl = isTrust;
		return this;
	}

	public HtpClient trustAllSsl() {
		return trustAllSsl(true);
	}

	public HtpClient header(String name, String value) {
		htpRequest.headers.add(name, value);
		return this;
	}

	public HtpClient requestParam(String name, String value) {
		htpRequest.parameters.add(name, value);
		return this;
	}

	public HtpClient requestContentType(HtpMediaType mediaType) {
		htpRequest.htpMediaType = mediaType;
		return this;
	}

	public HtpClient textToPost(String text) {
		htpRequest.textToPost = text;
		return this;
	}

	public HtpClient timeout(long timeout, TimeUnit timeoutUnit) {
		this.timeout = timeout;
		this.timeoutUnit = timeoutUnit;
		return this;
	}

	public HtpResponse get() {
		htpRequest.htpMethod = HtpMethod.GET;
		return run();
	}

	public HtpResponse post() {
		htpRequest.htpMethod = HtpMethod.POST;
		return run();
	}

	private HtpResponse run() {
		LOGGER.debug(htpRequest.htpMethod + " " + htpRequest.url.toString());
		HttpClient httpClient = createHttpClient();
		start(httpClient);
		Request request = httpClient.newRequest(htpRequest.url.getText())
				.method(htpRequest.htpMethod.toString())
				.timeout(timeout, timeoutUnit);

		htpRequest.addHeadersToRequest(request);
		htpRequest.setRequestData(request);
		ContentResponse contentResponse = send(request);
		stop(httpClient);
		return new HtpResponse(contentResponse);
	}

	private HttpClient createHttpClient() {
		ClientConnector clientConnector = new ClientConnector();
		clientConnector.setSslContextFactory(createSslContextFactory());
		HttpClientTransportDynamic transport = new HttpClientTransportDynamic(clientConnector);

		HttpClient httpClient = new HttpClient(transport);

		httpClient.setFollowRedirects(false);
		return httpClient;
	}

	private SslContextFactory.Client createSslContextFactory() {
		if (! htpRequest.trustAllSsl) return null;
		SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
		sslContextFactory.setTrustAll(true);
		return sslContextFactory;
	}

	private void start(HttpClient httpClient) {
		try {
			httpClient.start();
		} catch (Exception e) {
			throw new RuntimeException("Start failed: " + htpRequest , e);
		}
	}

	private void stop(HttpClient httpClient) {
		try {
			httpClient.stop();
		} catch (Exception e) {
			throw new RuntimeException("Stop failed: " + htpRequest , e);
		}
	}

	private ContentResponse send(Request request) {
		try {
			return request.send();
		} catch (Exception e) {
			throw new RuntimeException("Send failed: " + htpRequest, e);
		}
	}

}
