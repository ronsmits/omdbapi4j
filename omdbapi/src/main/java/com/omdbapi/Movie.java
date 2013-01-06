package com.omdbapi;

import java.util.Arrays;
import java.util.List;

/**
 * The result of retrieving a movie is quite raw. Movie encapsulates the raw data stored in @see RawMovie and presents
 * in a more "java" like form.
 * 
 * @author ron
 *
 */
public class Movie extends RawMovie {

	public String getTitle(){
		return title;
	}

	public List<String> getGenres() {
		String[] split = genre.split(", ");
		return Arrays.asList(split);
	}

	public List<String> getActors() {
		String[] split = actors.split(", ");
		return Arrays.asList(split);
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
}
