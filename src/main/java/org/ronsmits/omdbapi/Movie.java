package org.ronsmits.omdbapi;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * The result of retrieving a movie is quite raw. Movie encapsulates the raw
 * data stored in @see RawMovie and presents in a more "java" like form. The
 * attributes from @see RawMovie that we can use are exposed.
 *
 * @author ron
 *
 */
public class Movie extends RawMovie implements Serializable {

    private static final long serialVersionUID = 1909376875052245393L;

    /**
     * All the genres for the movie
     *
     * @return the genres in an List.
     */
    public List<String> getGenres() {
	return splitter(genre);
    }

    /**
     * The actors in the movie.
     *
     * @return the actors in an List.
     */
    public List<String> getActors() {
	return splitter(actors);
    }

    /**
     * The writers of the movie.
     *
     * @return the writers in a List.
     */
    public List<String> getWriters() {
	return splitter(writers);
    }

    /**
     * The poster as an URL. This can be used to get the image.
     *
     * @return an URL pointing to the poster of the movie.
     * @throws MalformedURLException
     */
    public URL getPosterURL() throws MalformedURLException {
	return new URL(poster);
    }

    /**
     * the Poster url as a string
     *
     * @return The url of the poster as a string.
     */
    public String getPoster() {
	return poster;
    }

    /**
     * Convenience method to split a string with ", " in it up to an array.
     *
     * @param splitthis
     * @return
     */
    private List<String> splitter(String splitthis) {
	return Arrays.asList(splitthis.split(", "));
    }

    /**
     * We dont know in wich country we are running. 8.2 is great in a lot of
     * countries. However in several countries it should be 8,2. So we convert
     * the string to a float. Let the implementors work it out.
     *
     * @return
     */
    public float getImdbRating() {
	String ratingstr = imdbRating.replace(".", "");
	try {
	    float rating = Float.parseFloat(ratingstr);
	    rating = rating / 10;
	    return rating;

	} catch (NumberFormatException nfe) {
	    return -1;
	}
    }

    /**
     * The votes given on IMDB for this movie.
     *
     * @return the votes or -1 if no votes are given.
     */
    public long getImdbVotes() {
	String votestr = imdbVotes.replace(",", "");
	try {
	    return Long.parseLong(votestr);
	} catch (NumberFormatException nfe) {
	    return -1;
	}
    }
}
