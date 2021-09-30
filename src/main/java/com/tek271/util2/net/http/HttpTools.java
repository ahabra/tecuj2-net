package com.tek271.util2.net.http;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

/** General HTTP utils */
public class HttpTools {

	public static boolean isHttp(String path) {
		return startsWithIgnoreCase(path, "http://")
				|| startsWithIgnoreCase(path, "https://");
	}

	public static String get(String path) {
		URL url = Url.toURL(path);
		try {
			return IOUtils.toString(url, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
