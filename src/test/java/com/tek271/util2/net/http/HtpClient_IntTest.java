package com.tek271.util2.net.http;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
public class HtpClient_IntTest {
	private static final String URL_HTTP = "http://www.example.com/";
	private static final String URL_HTTPS = "https://www.example.com/";

	private HtpClient sut = new HtpClient();


	@Test
	public void testGet() {
		HtpResponse htpResponse = sut.url(URL_HTTP).get();
		assertTrue(htpResponse.isSuccess);
		assertEquals(HtpMediaType.textHtml.text, htpResponse.type);
		assertEquals("OK", htpResponse.reason);
		assertTrue(htpResponse.text.contains("<title>Example Domain</title>"));
	}

	@Test
	public void testSimpleGetHttps() {
		HtpResponse htpResponse = sut.trustAllSsl().url(URL_HTTPS).get();
		assertTrue(htpResponse.isSuccess);
		assertEquals(HtpMediaType.textHtml.text, htpResponse.type);
		assertEquals("OK", htpResponse.reason);
		assertTrue(htpResponse.text.contains("<title>Example Domain</title>"));
	}

	@Test
	public void testGetWithHeader() {
		HtpResponse htpResponse = sut.url(URL_HTTP).header("k", "v").get();
		assertTrue(htpResponse.isSuccess);
	}

	@Test
	public void testPost() {
		HtpResponse htpResponse = sut.url("http://httpbin.org/post").textToPost("hi").post();
		assertTrue(htpResponse.isSuccess);
		assertTrue(htpResponse.text.contains("\"data\": \"hi\""));
	}

	@Test
	public void testLoggingExcludedParams() {
		try {
			sut.url("http://bad.url.xyz0?a=1&p=2").loggingExcludedParams("p").get();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("p=" + RequestParams.MASK));
		}
	}

}