package com.omdbapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SearchResult {
	private String title;
	private String year;
	private String imdbId;
	public String getTitle() {
		return title;
	}
	@XmlElement(name="Title")
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	@XmlElement(name="Year")
	public void setYear(String year) {
		this.year = year;
	}
	public String getImdbID() {
		return imdbId;
	}
	public void setImdbID(String imdbId) {
		this.imdbId = imdbId;
	}
}
