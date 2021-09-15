package com.tek271.util2.net.http;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UrlTest {
	private final Url sut = new Url();


	@Test
	public void testParse() {
		sut.parse("a?b=1");
		assertEquals("a", sut.base);
		assertEquals(Pair.of("b", "1"), sut.requestParams.get(0));
	}

	@Test
	public void parseHandlesMultipleParams() {
		sut.parse("a?b=1&c=2");
		assertEquals("a", sut.base);
		assertEquals(Pair.of("b", "1"), sut.requestParams.get(0));
		assertEquals(Pair.of("c", "2"), sut.requestParams.get(1));
		assertEquals(2, sut.requestParams.size());
	}

	@Test
	public void parseHandlesRequestParamWithoutKey() {
		sut.parse("a?hi");
		assertEquals("a", sut.base);
		assertEquals(Pair.of(null, "hi"), sut.requestParams.get(0));
	}

	@Test
	public void parseHandlesRequestEmptyKeyOrValue() {
		sut.parse("a?=1&b=&3");
		assertEquals("a", sut.base);
		assertEquals(Pair.of("", "1"), sut.requestParams.get(0));
		assertEquals(Pair.of("b", ""), sut.requestParams.get(1));
		assertEquals(Pair.of(null, "3"), sut.requestParams.get(2));
	}

	@Test
	public void testGetText() {
		String text = "a?b=1";
		assertEquals(text, sut.parse(text).getText() );
	}

	@Test
	public void testLoggingExcludedParams() {
		String toString = sut.loggingExcludedParams("p").parse("a?p=secret").toString();
		assertEquals("a?p=*****", toString);
	}

	@Test
	public void toStringHandlesEmptyKeys() {
		assertEquals("a?b&c=1", sut.parse("a?b&c=1").toString());
	}

	@Test
	public void getTextHandlesEmptyKeys() {
		assertEquals("a?b", sut.parse("a?b").getText());
	}
}