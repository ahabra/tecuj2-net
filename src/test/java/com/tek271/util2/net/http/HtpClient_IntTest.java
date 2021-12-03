package com.tek271.util2.net.http;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
public class HtpClient_IntTest {
	private static final String URL_HTTP = "http://www.example.com/";
	private static final String URL_HTTPS = "https://www.example.com/";

	private final HtpClient sut = new HtpClient();


	@Test
	public void testGet() {
		HtpRequest request = new HtpRequest().url(URL_HTTP);
		HtpResponse htpResponse = sut.run(request);

		assertTrue(htpResponse.isSuccess);
		assertEquals(HtpMediaType.textHtml.text, htpResponse.type);
		assertEquals("OK", htpResponse.reason);
		assertTrue(htpResponse.text.contains("<title>Example Domain</title>"));
	}

	@Test
	public void testSimpleGetHttps() {
		HtpRequest request = new HtpRequest().url(URL_HTTPS).trustAllSsl();
		HtpResponse htpResponse = sut.run(request);
		assertTrue(htpResponse.isSuccess);
		assertEquals(HtpMediaType.textHtml.text, htpResponse.type);
		assertEquals("OK", htpResponse.reason);
		assertTrue(htpResponse.text.contains("<title>Example Domain</title>"));
	}

	@Test
	public void testGetWithHeader() {
		HtpRequest request = new HtpRequest().url(URL_HTTP).header("k", "v");
		HtpResponse htpResponse = sut.run(request);
		assertTrue(htpResponse.isSuccess);
	}

	@Test
	public void testPost() {
		HtpRequest request = new HtpRequest().url("http://httpbin.org/post")
				.textToPost("hi").method(HtpMethod.POST);
		HtpResponse htpResponse = sut.run(request);

		assertTrue(htpResponse.isSuccess);
		assertTrue(htpResponse.text.contains("\"data\": \"hi\""));
	}

	@Test
	public void testLoggingExcludedParams() {
		HtpRequest request = new HtpRequest().url("http://bad.url.xyz0?a=1&p=2").loggingExcludedParams("p");

		try {
			sut.run(request);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("p=" + RequestParams.MASK));
		}
	}
}
