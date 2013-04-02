package com.omdbapi;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * The result of retrieving a movie is quite raw. Movie encapsulates the raw data stored in @see RawMovie and presents
 * in a more "java" like form. The attributes from @see RawMovie that we can use are exposed.
 * 
 * @author ron
 *
 */
public class Movie extends RawMovie implements Serializable{
	private static final long serialVersionUID = 1909376875052245393L;

	public List<String> getGenres() {
		return splitter(genre);
	}

	public List<String> getActors() {
		return splitter(actors);
	}

	public List<String> getWriters() {
		return splitter(writers);
	}
	
	public URL getPosterURL() throws MalformedURLException {
		return new URL(poster);
	}
	
	public String getPoster() {
		return poster;
	}
	public String getType(){
		return type;
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
	 * We dont know in wich country we are running. 8.2 is great in a lot of countries. However in several countries
	 * it should be 8,2. So we convert the string to a float. Let the implementors work it out.
	 * @return
	 */
	public float getImdbRating() {
		String ratingstr = imdbRating.replace(".", "");
		float rating = Float.parseFloat(ratingstr);
		rating = rating/10;
		return rating;
	}
	
	public long getImdbVotes() {
		String votestr = imdbVotes.replace(",", "");
		return Long.parseLong(votestr);
	}
}
