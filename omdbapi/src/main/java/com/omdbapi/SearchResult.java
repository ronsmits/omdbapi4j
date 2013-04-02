package com.omdbapi;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SearchResult implements Serializable{
	private static final long serialVersionUID = -2610565920993691973L;

	private String title;
	private String year;
	private String imdbId;
	private String type;
	
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
	public String getType() {
		return type;
	}
	@XmlElement(name="Type")
	public void setType(String type) {
		this.type = type;
	}
}
