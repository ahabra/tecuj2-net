package com.tek271.util2.net.http;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

public class HttpTools {
	private static final Logger LOGGER = LogManager.getLogger(HttpTools.class);
	private static final String UTF8 = "UTF-8";

	public static URL url(String path) {
		try {
			return new URL(path);
		} catch (MalformedURLException e) {
			LOGGER.error("Cannot create URL from " + path, e);
			throw new RuntimeException(e);
		}
	}

	public static boolean isHttp(String path) {
		return startsWithIgnoreCase(path, "http://")
				|| startsWithIgnoreCase(path, "https://");
	}

	public static String get(String path) {
		URL url = url(path);
		try {
			return IOUtils.toString(url, UTF8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
