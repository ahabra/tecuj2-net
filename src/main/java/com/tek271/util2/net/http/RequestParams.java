package com.tek271.util2.net.http;

import com.google.common.base.Splitter;
import com.tek271.util2.collection.ListOfPairs;
import com.tek271.util2.string.EscapeTools;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

public class RequestParams extends ListOfPairs<String, String> {
	public static final String MASK = "*****";
	static final String EQ = "=";
	static final String AMP = "&";

	EscapeTools escapeTools = new EscapeTools();
	private Set<String> excludedParams = new HashSet<>();

	public RequestParams parse(String text) {
		clear();
		text = StringUtils.trimToEmpty(text);
		if (StringUtils.isNotBlank(text)) {
			Splitter.on(AMP).split(text).forEach(this::addTerm);
		}
		return this;
	}

	public RequestParams addTerm(String term) {
		if (!StringUtils.contains(term, EQ)) {
			add(null, term);
			return this;
		}
		String k = substringBefore(term, EQ).trim();
		String v = substringAfter(term, EQ);
		add(k, v);
		return this;
	}

	public RequestParams loggingExcludedParams(String... excluded) {
		Collections.addAll(excludedParams, excluded);
		return this;
	}

	public Set<String> getExcludedParams() {
		return excludedParams;
	}

	@Override
	public String toString() {
		return stream().map(this::termToString).collect(joining(AMP));
	}

	private String termToString(Pair<String, String> term) {
		String k = term.getKey();
		String v = term.getValue();
		if (excludedParams.contains(k)) {
			v = MASK;
		}
		if (k==null) return v;
		return k + EQ + v;
	}

	private String termToStringEncoded(Pair<String, String> term) {
		String k = term.getKey();
		String v = escapeTools.escapeUrl(term.getValue());
		if (k==null) return v;
		return k + EQ + v;
	}

	public String getText() {
		return stream().map(this::termToStringEncoded).collect(joining(AMP));
	}



}
