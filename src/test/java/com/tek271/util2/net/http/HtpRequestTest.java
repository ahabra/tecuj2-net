package com.tek271.util2.net.http;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class HtpRequestTest {

	@Test
	void toString_simpleGET() {
		HtpRequest sut = new HtpRequest();
		sut.url("a.b");
		assertEquals("GET a.b", sut.toString());
	}

	@Test
	void toString_inlineParams() {
		HtpRequest sut = new HtpRequest();
		sut.url("www.a.com?a=1");
		assertEquals("GET www.a.com?a=1", sut.toString());
	}

	@Test
	void toString_params() {
		HtpRequest sut = new HtpRequest();
		sut.url("www.a.com");
		sut.parameter("a", "1");
		assertEquals("GET www.a.com?a=1", sut.toString());
	}

	@Test
	void toString_fullParams() {
		HtpRequest sut = new HtpRequest();
		sut.url("www.a.com?a=1");
		sut.parameter("b", "2");
		assertEquals("GET www.a.com?a=1&b=2", sut.toString());
	}

	@Test
	void toString_excludedParams() {
		HtpRequest sut = new HtpRequest();
		sut.url("www.a.com");
		sut.parameter("a", "1");
		sut.parameter("password", "secret");
		sut.loggingExcludedParams("password");

		assertEquals("GET www.a.com?a=1&password=*****", sut.toString());
	}

	@Test
	void testTimeout() {
		HtpRequest sut = new HtpRequest();
		sut.timeout(1, TimeUnit.SECONDS);
		assertEquals(1000, sut.getTimeoutMillis());
	}

}