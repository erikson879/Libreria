package com.cleteci.tryout.Libreria.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {

	private Long id;
	private String nombre;
	private String apellido;
	private Set<Book> books = new HashSet<>();
		
	public Author() {}
	public Author(Long id) {
		this.id= id;
	}
		
	public Author(String nombre, String apellido, Set<Book> books) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.books = books;
	}
	
	public Author(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Set<Book> getBooks() {
		return books;
	}
	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", books=" + books + "]";
	}
	
	
}
