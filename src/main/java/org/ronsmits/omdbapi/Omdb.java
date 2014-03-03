package org.ronsmits.omdbapi;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Omdb is the main entrance to search for movies.
 * <p>
 * It uses a fluent api to set the search criteria but the last command in the
 * chain must be <code>search</code></p>
 * <p>
 * A typical use case would be :</p>
 * <code>
 * List&lt;SearchResult&gt; list = new Omdb().year(1982).type(MovieType.movie).search("blade runner");
 * </code>
 * <p>
 * The type() methode can be added several times.
 *
 * @author ron
 */
public class Omdb extends RestClient {

    private static final Logger logger = Logger.getLogger("omdb");
    private final List<String> options = new ArrayList<String>();
    private final ObjectMapper mapper = new ObjectMapper();

    private List<MovieType> typesToRestrict;

    /**
     * Construct the Omdb object and setup the jackson mapper.
     */
    public Omdb() {
	mapper.registerModule(new JaxbAnnotationModule());
    }

    /**
     * Final method in the string of fluent api. Search will get at most 10
     * responses from omdbapi.com rest service.
     *
     * @param searchString string containing the title or part of the title to
     * search for.
     * @return a list of @see SearchResult elements with the results of the
     * search.
     * @throws OmdbMovieNotFoundException returned when nothing is found.
     * @throws OmdbConnectionErrorException returned when there is no connection
     * to omdbapi.com.
     * @throws OmdbSyntaxErrorException returned when omdbapi says our URL is
     * not correct.
     */
    public List<SearchResult> search(String searchString)
	    throws OmdbMovieNotFoundException, OmdbConnectionErrorException,
	    OmdbSyntaxErrorException {
	logger.fine("search started for " + searchString);
	try {
	    options.add("s=" + URLEncoder.encode(searchString, "UTF-8"));
	    JSONObject response = execute(searchString);
	    List<SearchResult> results = getTheSearchResults(response);
	    if (typesToRestrict != null && !typesToRestrict.isEmpty()) {
		results = filterTypes(results);
	    }
	    return results;
	} catch (IOException e) {
	    throw new OmdbConnectionErrorException(e.getMessage());
	} catch (URISyntaxException e) {
	    throw new OmdbSyntaxErrorException(e.getMessage());
	} catch (JSONException e) {
	    throw new OmdbSyntaxErrorException(e.getMessage());
	}
    }

    /**
     * Find one movie based on the imdb id. This is always a string use built up
     * as <code>tt&lt;6 digits&gt;</code>
     *
     * @param id to look for.
     * @return the found movie.
     * @throws OmdbConnectionErrorException returned when there is no connection
     * to omdbapi.com.
     * @throws OmdbSyntaxErrorException returned when omdbapi says our URL is
     * not correct.
     * @throws OmdbMovieNotFoundException returned when nothing is found.
     */
    public Movie getById(String id) throws OmdbConnectionErrorException,
	    OmdbSyntaxErrorException, OmdbMovieNotFoundException {
	options.add("i=" + id);
	return getOneMovie(id);
    }

    /**
     * Find one movie based on title. There is no guarantee this will be the
     * movie you looked for. The more precise the title, the more chance that
     * the right movie is returned.
     *
     * @param moviename title to look for.
     * @return the found movie.
     * @throws OmdbConnectionErrorException returned when there is no connection
     * to omdbapi.com.
     * @throws OmdbSyntaxErrorException returned when omdbapi says our URL is
     * not correct.
     * @throws OmdbMovieNotFoundException returned when nothing is found.
     */
    public Movie searchOneMovie(String moviename)
	    throws OmdbSyntaxErrorException, OmdbConnectionErrorException,
	    OmdbMovieNotFoundException {
	try {
	    options.add("t=" + URLEncoder.encode(moviename, "UTF-8"));
	    return getOneMovie(moviename);
	} catch (UnsupportedEncodingException e) {
	    throw new OmdbSyntaxErrorException(e.getMessage());
	}
    }

    private Movie getOneMovie(String id) throws OmdbConnectionErrorException,
	    OmdbSyntaxErrorException, OmdbMovieNotFoundException {
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

    /**
     * Limit the search to the given year
     *
     * @param year to look for. Must be in the 4 digits format e.q. 1998 or
     * 2010,
     * @return the Omdb object.
     */
    public Omdb year(int year) {
	options.add("y=" + String.valueOf(year));
	return this;
    }

    /**
     * Limit the search to a given type. This method can be called several times
     * to get specific types in the result list.
     *
     * @param type to filter on.
     * @return the Omdb object.
     */
    public Omdb type(MovieType type) {
	if (typesToRestrict == null) {
	    typesToRestrict = new ArrayList<MovieType>();
	}
	typesToRestrict.add(type);
	return this;
    }

    /**
     * Return the full plot. Omdb will return a summary if this is not in the
     * chain.
     *
     * @return the Omdb object.
     */
    public Omdb fullPlot() {
	options.add("plot=full");
	return this;
    }

    /*
     * switched of at the moment.
     *
     */
    private Omdb tomatoScore() {
	// options.add("tomatoes=true");
	return this;
    }

    private JSONObject execute(String string) throws IOException,
	    URISyntaxException, JSONException, OmdbMovieNotFoundException {
	String found = get(options.toArray());
	JSONObject response = resultToJson(string, found);
	return response;
    }

    private Movie getFoundMovie(JSONObject executeResult)
	    throws JsonParseException, JsonMappingException, IOException {
	Movie fm = (Movie) mapper.readValue(executeResult.toString(),
		Movie.class);
	return fm;
    }

    private JSONObject resultToJson(String string, String found)
	    throws JSONException, OmdbMovieNotFoundException {
	logger.fine("received " + found);
	JSONObject response = new JSONObject(found);
	checkReturnCode(string, response);
	return response;
    }

    private List<SearchResult> getTheSearchResults(JSONObject response)
	    throws JSONException, IOException, JsonParseException,
	    JsonMappingException {
	Object searchresult = response.get("Search");
	List<SearchResult> results = mapper.readValue(searchresult.toString(),
		new TypeReference<List<SearchResult>>() {
		});
	return results;
    }

    private void checkReturnCode(String string, JSONObject response)
	    throws JSONException, OmdbMovieNotFoundException {
	if (response.has("Response")
		&& response.get("Response").equals("False")) {
	    throw new OmdbMovieNotFoundException(string);
	}
    }

    private List<SearchResult> filterTypes(List<SearchResult> results) {
	List<SearchResult> list = new ArrayList<SearchResult>();
	for (MovieType type : typesToRestrict) {
	    for (SearchResult result : results) {
		if (result.getType().equals(type.name())) {
		    list.add(result);
		}
	    }
	}

	return list;
    }

}
