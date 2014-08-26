package Server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QueryParser {

	private static class KVP {

		final String key;
		final String value;

		KVP(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}
	List<KVP> query = new ArrayList<>();

	public QueryParser(String queryString) {
		parse(queryString);
	}

	public QueryParser() {
	}

	public void addParam(String key, String value) {
		if (key == null || value == null) {
			throw new NullPointerException("null parameter key or value");
		}
		query.add(new KVP(key, value));
	}

	private void parse(String queryString) {
		for (String pair : queryString.split("&")) {
			int eq = pair.indexOf("=");
			if (eq < 0) {
				// key with no value
				addParam(URLDecoder.decode(pair), "");
			} else {
				// key=value
				String key = URLDecoder.decode(pair.substring(0, eq));
				String value = URLDecoder.decode(pair.substring(eq + 1));
				query.add(new KVP(key, value));
			}
		}
	}

	public String toQueryString() {
		StringBuilder sb = new StringBuilder();
		for (KVP kvp : query) {
			if (sb.length() > 0) {
				sb.append('&');
			}
			try {
				sb.append(URLEncoder.encode(kvp.key, "ISO-8859-1"));
				if (!kvp.value.equals("")) {
					sb.append('=');
					sb.append(URLEncoder.encode(kvp.value, "ISO-8859-1"));
				}
			} catch (UnsupportedEncodingException uee) {
				uee.printStackTrace();
			}
		}
		return sb.toString();
	}

	public String getParameter(String key) {
		for (KVP kvp : query) {
			if (kvp.key.equals(key)) {
				return kvp.value;
			}
		}
		return null;
	}

	public List<String> getParameterValues(String key) {
		List<String> list = new LinkedList<>();
		for (KVP kvp : query) {
			if (kvp.key.equals(key)) {
				list.add(kvp.value);
			}
		}
		return list;
	}
}
