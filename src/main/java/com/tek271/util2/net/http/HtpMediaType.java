package com.tek271.util2.net.http;

/** Different supported media types */
public enum HtpMediaType {
	textPlain("text/plain"),
	textHtml("text/html"),
	textXml("text/xml"),
	json("application/json"),
	form("application/x-www-form-urlencoded"),
	multipart("multipart/form-data");

	public final String text;

	HtpMediaType(String text) { this.text = text; }

}
