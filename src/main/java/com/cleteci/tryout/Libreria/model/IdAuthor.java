package com.cleteci.tryout.Libreria.model;

import java.util.HashSet;
import java.util.Set;

public class IdAuthor {
	private Set<Long> authorsId = new HashSet<Long>();
	
	public IdAuthor() {}

	public Set<Long> getAuthorsId() {
		return authorsId;
	}

	public void setAuthorsId(Set<Long> authorsId) {
		this.authorsId = authorsId;
	}
	
}
