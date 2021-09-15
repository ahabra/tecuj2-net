package com.tek271.util2.net.http;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import static com.tek271.util2.net.http.RequestParams.MASK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestParamsTest {
	private RequestParams sut = new RequestParams();

	@Test
	public void testParseWithEmptyString() {
		assertEquals(0, sut.parse(null).size());
		assertEquals(0, sut.parse("").size());
		assertEquals(0, sut.parse("  ").size());
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testParse() {
		sut.parse("a=1&b=2");
		checkResult(2, Pair.of("a", "1"), Pair.of("b", "2"));

		sut.parse("a=");
		checkResult(1, Pair.of("a", ""));

		sut.parse("a");
		checkResult(1, Pair.of(null, "a"));

		sut.parse(" a =1 ");
		checkResult(1, Pair.of("a", "1"));
	}

	@SuppressWarnings("unchecked")
	private void checkResult(int size, Pair<String, String>... pairs) {
		assertEquals(size, sut.size());
		int i=0;
		for (Pair<String, String> p: pairs) {
			assertEquals(p, sut.get(i++));
		}
	}

	@Test
	public void testLoggingExcludedParams() {
		sut.parse("a=1&p=secret");
		sut.loggingExcludedParams("p");
		assertEquals("a=1&p=" + MASK, sut.toString());
	}

	@Test
	public void testGetText() {
		sut = new RequestParams();
		sut.add("a", "1").add("b", "2");
		assertEquals("a=1&b=2", sut.getText());
	}

	@Test
	public void testGetTextWithEscapedValues() {
		sut.add("a", "1 2");
		assertEquals("a=1+2", sut.getText());
	}

}