package com.tek271.util2.net.http;

import com.tek271.util2.collection.ListOfPairs;
import com.tek271.util2.string.ToString;
import org.eclipse.jetty.client.api.ContentResponse;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class HtpResponse {
	public final String text;
	public final String type;
	public final int status;
	public final String reason;
	public final boolean isSuccess;
	public final ListOfPairs<String, String> headers = new ListOfPairs<>();


	public HtpResponse(String text, String type, int status, String reason) {
		this.text = text;
		this.type = type;
		this.status = status;
		this.reason = reason;
		this.isSuccess = status < 300;
	}

	public HtpResponse(ContentResponse res) {
		this(res.getContentAsString(), res.getMediaType(), res.getStatus(), res.getReason());
		res.getHeaders().forEach(f-> headers.add(f.getName(), f.getValue()));
	}

	public static HtpResponse ok(String text) {
		return new HtpResponse(text, HtpMediaType.textHtml.text, 200, "OK");
	}

	@Override
	public String toString() {
		return new ToString().style(SHORT_PREFIX_STYLE).exclude("headers").toString(this);
	}
}
