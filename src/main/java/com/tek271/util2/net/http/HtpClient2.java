package com.tek271.util2.net.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.dynamic.HttpClientTransportDynamic;
import org.eclipse.jetty.io.ClientConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class HtpClient2 {
	private static final Logger LOGGER = LogManager.getLogger(HtpClient2.class);


	public HtpResponse run(HtpRequest2 request) {
		LOGGER.debug(request.toString());
		HttpClient jettyClient = createJettyClient(request);
		start(jettyClient, request);

		Request jettyRequest = HttpTools.htpRequestToJettyRequest(request, jettyClient);
		ContentResponse contentResponse = send(jettyRequest, request);
		stop(jettyClient, request);
		return new HtpResponse(contentResponse);
	}


	private static HttpClient createJettyClient(HtpRequest2 request) {
		ClientConnector clientConnector = new ClientConnector();
		clientConnector.setSslContextFactory(createSslContextFactory(request));
		HttpClientTransportDynamic transport = new HttpClientTransportDynamic(clientConnector);

		HttpClient jettyClient = new HttpClient(transport);

		jettyClient.setFollowRedirects(false); // ???
		return jettyClient;
	}

	private static SslContextFactory.Client createSslContextFactory(HtpRequest2 request) {
		if (!request.isTrustAllSsl()) return null;
		SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
		sslContextFactory.setTrustAll(true);
		return sslContextFactory;
	}

	private static void start(HttpClient httpClient, HtpRequest2 request) {
		try {
			httpClient.start();
		} catch (Exception e) {
			throw new RuntimeException("Start failed: " + request , e);
		}
	}

	private static void stop(HttpClient httpClient, HtpRequest2 request) {
		try {
			httpClient.stop();
		} catch (Exception e) {
			throw new RuntimeException("Stop failed: " + request , e);
		}
	}

	private static ContentResponse send(Request jettyRequest, HtpRequest2 request) {
		try {
			return jettyRequest.send();
		} catch (Exception e) {
			throw new RuntimeException("Send failed: " + request, e);
		}
	}
}
