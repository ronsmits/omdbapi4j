package com.omdbapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class RawMovie {
	
	@XmlElement(name = "Title")
	protected String title;
	@XmlElement(name = "Genre")
	protected String genre;
	@XmlElement(name = "Year")
	protected String year;
	@XmlElement(name = "Director")
	protected String director;
	@XmlElement(name = "Actors")
	protected String actors;
	@XmlElement(name = "Rated")
	protected String rated;
	@XmlElement(name = "Released")
	protected String released;
	@XmlElement(name = "Runtime")
	protected String runtime;
	@XmlElement(name = "Writer")
	protected String writers;
	@XmlElement(name = "Plot")
	protected String plot;
	@XmlElement(name = "imdbID")
	protected String imdbId;
	protected String imdbRating;
	@XmlElement(name = "Poster")
	protected String poster;
	protected String imdbVotes;
	@XmlElement(name = "Response")
	protected String response;
}
