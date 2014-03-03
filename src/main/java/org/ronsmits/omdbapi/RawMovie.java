package org.ronsmits.omdbapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class RawMovie {
	
	public String getTitle() {
		return title;
	}
	public String getDirector() {
		return director;
	}
	public String getPlot() {
		return plot;
	}
	public String getRuntime() {
		return runtime;
	}
	public String getRated() {
		return rated;
	}
	public String getImdbId() {
		return imdbId;
	}
	public MovieType getType(){
		return type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMetascore() {
		return metascore;
	}
	public void setMetascore(String metascore) {
		this.metascore = metascore;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
	}
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
	@XmlElement(name="Type")
	protected MovieType type;
	@XmlElement(name="Country")
	protected String country;
	@XmlElement(name="Metascore")
	protected String metascore;
	@XmlElement(name="Language")
	protected String language;
	@XmlElement(name="Awards")
	private String awards;
}
