package com.tek271.util2.net.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

public class Url {
	private static final Logger LOGGER = LogManager.getLogger(Url.class);
	static final String Q = "?";

	public String base;
	public RequestParams requestParams = new RequestParams();

	public Url parse(String path) {
		base = substringBefore(path, Q);
		requestParams.parse(substringAfter(path, Q));
		return this;
	}

	public Url loggingExcludedParams(String... excluded) {
		requestParams.loggingExcludedParams(excluded);
		return this;
	}

	@Override
	public String toString() {
		return base + Q + requestParams.toString();
	}

	public String getText() {
		return base + Q + requestParams.getText();
	}


	public static URL toURL(String path) {
		try {
			return new URL(path);
		} catch (MalformedURLException e) {
			LOGGER.error("Cannot create URL from " + path, e);
			throw new RuntimeException(e);
		}
	}

}
