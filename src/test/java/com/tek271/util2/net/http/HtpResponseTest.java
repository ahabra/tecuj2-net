package com.tek271.util2.net.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtpResponseTest {

	@Test
	public void testOk() {
		HtpResponse sut = HtpResponse.ok("hi");
		assertEquals("hi", sut.text);
		assertEquals("text/html", sut.type);
		assertEquals(200, sut.status);
		assertEquals("OK", sut.reason);
		assertEquals(true, sut.isSuccess);
	}

	@Test
	public void testToString() {
		HtpResponse sut = new HtpResponse("hi", "text", 200, "OK");
		String expected = "HtpResponse[isSuccess=true,reason=OK,status=200,text=hi,type=text]";
		assertEquals(expected, sut.toString());
	}

}