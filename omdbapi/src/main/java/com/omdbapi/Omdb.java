package com.omdbapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

public class Omdb extends RestClient {
	private final Logger logger = Logger.getLogger("omdb");
	private List<String> options = new ArrayList<String>();
	private ObjectMapper mapper = new ObjectMapper();
	
	public Omdb() {
		mapper.registerModule(new JaxbAnnotationModule());
	}
	
	public List<SearchResult> search(String searchString)
			throws OmdbMovieNotFoundException, OmdbConnectionErrorException,
			OmdbSyntaxErrorException {
		logger.fine("search started for " + searchString);
		try {
			options.add("s=" + URLEncoder.encode(searchString, "UTF-8"));
			JSONObject response = execute(searchString);
			return getTheSearchResults(response);
		} catch (IOException e) {
			throw new OmdbConnectionErrorException(e.getMessage());
		} catch (URISyntaxException e) {
			throw new OmdbSyntaxErrorException(e.getMessage());
		} catch (JSONException e) {
			throw new OmdbSyntaxErrorException(e.getMessage());
		}
	}

	public Movie getById(String id) throws OmdbConnectionErrorException, OmdbSyntaxErrorException, OmdbMovieNotFoundException {
		options.add("i="+id);
		return getOneMovie(id);
	}
	
	public Movie searchOneMovie(String moviename) throws OmdbSyntaxErrorException, OmdbConnectionErrorException, OmdbMovieNotFoundException {
		try {
			options.add("t="+URLEncoder.encode(moviename, "UTF-8"));
			return getOneMovie(moviename);
		} catch (UnsupportedEncodingException e) {
			throw new OmdbSyntaxErrorException(e.getMessage());
		}
	}
	private Movie getOneMovie(String id) throws OmdbConnectionErrorException, OmdbSyntaxErrorException, OmdbMovieNotFoundException {
		try {
			JSONObject executeResult = execute(id);
			Movie movie = getFoundMovie(executeResult);
			return movie;
		} catch (IOException e) {
			throw new OmdbConnectionErrorException(e.getMessage());
		} catch (URISyntaxException e) {
			throw new OmdbSyntaxErrorException(e.getMessage());
		} catch (JSONException e) {
			throw new OmdbSyntaxErrorException(e.getMessage());
		}
	}
	public Omdb year(int year) {
		options.add("y="+String.valueOf(year));
		return this;
	}
	
	public Omdb fullPlot() {
		options.add("plot=full");
		return this;
	}
	public Omdb tomatoScore(){
		//options.add("tomatoes=true");
		return this;
	}

	private JSONObject execute(String string) throws IOException,
			URISyntaxException, JSONException, OmdbMovieNotFoundException {
		String found = get(options.toArray());
		JSONObject response = resultToJson(string, found);
		return response;
	}

	private Movie getFoundMovie(JSONObject executeResult) throws JsonParseException, JsonMappingException, IOException {
		Movie fm = (Movie) mapper.readValue(executeResult.toString(), Movie.class);
		return fm;
	}

	private JSONObject resultToJson(String string, String found)
			throws JSONException, OmdbMovieNotFoundException {
		logger.info("received " + found);
		JSONObject response = new JSONObject(found);
		checkReturnCode(string, response);
		return response;
	}

	private List<SearchResult> getTheSearchResults(JSONObject response)
			throws JSONException, IOException, JsonParseException,
			JsonMappingException {
		Object searchresult = response.get("Search");
		List<SearchResult> results = mapper.readValue(searchresult.toString(), 
				new TypeReference<List<SearchResult>>() {});
		return results;
	}

	private void checkReturnCode(String string, JSONObject response)
			throws JSONException, OmdbMovieNotFoundException {
		if (response.has("Response")
				&& response.get("Response").equals("False")) {
			throw new OmdbMovieNotFoundException(string);
		}
	}

}
