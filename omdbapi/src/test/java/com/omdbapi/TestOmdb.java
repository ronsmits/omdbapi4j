package com.omdbapi;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.omdbapi.Omdb;
import com.omdbapi.OmdbMovieNotFoundException;
import com.omdbapi.SearchResult;

public class TestOmdb {

	@Test
	public void testForStarWars() throws OmdbMovieNotFoundException {
		List<SearchResult> results = new Omdb().search("Star wars");
		assertEquals(4, results.size());
	}

	@Test(expected=OmdbMovieNotFoundException.class)
	public void testForDoesNotExist() throws OmdbMovieNotFoundException {
		new Omdb().search("doesnot exist");
	}
}
