package com.anotherpackage;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.omdbapi.Movie;
import com.omdbapi.Omdb;
import com.omdbapi.OmdbConnectionErrorException;
import com.omdbapi.OmdbMovieNotFoundException;
import com.omdbapi.OmdbSyntaxErrorException;

public class TestMovie {

	private Movie movie;
	
	@Before
	public void setup() throws OmdbConnectionErrorException, OmdbSyntaxErrorException, OmdbMovieNotFoundException {
		movie = new Omdb().getById("tt0083658");
	}
	@Test
	public void testgetTitle() {
		assertEquals("Blade Runner", movie.getTitle());
	}
	
	@Test
	public void testGetGenres() {
		List<String> genres = movie.getGenres();
		assertEquals(3, genres.size());
	}
	@Test
	public void testGetActors() {
		List<String> genres = movie.getActors();
		assertEquals(4, genres.size());		
	}
	
	@Test
	public void testImdbRating() {
		float imdbRating = movie.getImdbRating();
		assertEquals(imdbRating, 8.3f, 0);
	}
}
