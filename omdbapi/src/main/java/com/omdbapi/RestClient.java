package com.omdbapi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

class RestClient {
	private static final String OMDBURL="http://www.omdbapi.com/";
	private final Logger logger = Logger.getLogger("omdb");
	
	public String get(Object... path) throws IOException, URISyntaxException {
		return execute(new HttpGet(normalize(new URI(OMDBURL), path)));
	}

	private String execute(HttpRequestBase request) throws IOException {
		logger.info("executing " + request.toString());
		final HttpClient client = new DefaultHttpClient();
		final HttpResponse response = client.execute(request);

		final int code = response.getStatusLine().getStatusCode();
		final String message = asString(response);

		if (code < 200 || code >= 300) {
			throw new RuntimeException(response.getStatusLine().toString()
					+ " - " + message);
		}

		return message;
	}

	private static String asString(HttpResponse execute) throws IOException {
		return new BasicResponseHandler().handleResponse(execute);
	}
	
	private static URI normalize(URI uri, Object... path) {
        return URI.create(uri.toASCIIString() + "?" + join("&", path)).normalize();
    }

    private static String join(String delimiter, Object... collection) {
        if (collection.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : collection) {
            sb.append(obj).append(delimiter);
        }
        return sb.substring(0, sb.length() - delimiter.length());
    }
}