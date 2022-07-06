package com.cleteci.tryout.Libreria.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Publisher {
	private Long id;
	private String name;
	
	
	public Publisher(String name) {
		this.name = name;
	}
	public Publisher() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}
