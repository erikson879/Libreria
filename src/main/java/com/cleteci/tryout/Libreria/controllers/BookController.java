package com.cleteci.tryout.Libreria.controllers;


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cleteci.tryout.Libreria.model.Author;
import com.cleteci.tryout.Libreria.model.Book;
import com.cleteci.tryout.Libreria.model.IdAuthor;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class BookController {	
	String url = "http://localhost:8080";
	@RequestMapping("/books")
	public String getBooks(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper om = new ObjectMapper();
		String fooResourceUrl =url + "/api-rs/books";
		ResponseEntity<List> response = restTemplate.getForEntity(fooResourceUrl , List.class);
		chargerBooks(model, om, response);
		chargerAuthors(model, restTemplate, om, response);
		model.addAttribute("authorsId", new IdAuthor());
		model.addAttribute("book", new Book());
		return "books";
	}

	private void chargerBooks(Model model, ObjectMapper om, ResponseEntity<List> response) {
		if(HttpStatus.OK.equals(response.getStatusCode())) {
			Object[] lo = response.getBody().toArray();
			List<Book> books = Arrays.stream(lo)
			  .map(object -> om.convertValue(object, Book.class))
			  .collect(Collectors.toList());
			model.addAttribute("books",books);
		}else {
			model.addAttribute("books",response.getBody());
		}
	}

	private void chargerAuthors(Model model, RestTemplate restTemplate, ObjectMapper om, 
			ResponseEntity<List> response) {
		String fooUrlAuthors = url +"/api-rs/authors";
		ResponseEntity<List> response1 = restTemplate.getForEntity(fooUrlAuthors , List.class);
		if(HttpStatus.OK.equals(response1.getStatusCode())) {
			Object[] lo = response1.getBody().toArray();
			List<Author> authors = Arrays.stream(lo)
			  .map(object -> om.convertValue(object, Author.class))
			  .collect(Collectors.toList());
			model.addAttribute("authors",authors);
		}else {
			model.addAttribute("authors",response.getBody());
		}
	}
	
	@RequestMapping(value="/addBook", method = RequestMethod.POST)
	public String addBooks(@ModelAttribute Book book,IdAuthor authorsId, Model model) {
		ObjectMapper om = new ObjectMapper();
		Set<Author> authors = new HashSet<>();
		authorsId.getAuthorsId().stream().forEach(el->authors.add(new Author(el)));
		book.getAuthors().addAll(authors);
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = url +"/api-rs/books";
		ResponseEntity<Book> resultt = restTemplate.postForEntity(fooResourceUrl, book, Book.class);
		if(HttpStatus.OK.equals(resultt.getStatusCode())) {
			ResponseEntity<List> response = restTemplate.getForEntity(fooResourceUrl , List.class);
			chargerBooks(model, om, response);
			chargerAuthors(model, restTemplate, om, response);
		}else {
			model.addAttribute("books",resultt.getBody());
		}
		model.addAttribute("authorsId", new IdAuthor());
		model.addAttribute("book", new Book());
		return "books";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/query/books")
	public Book getBookViaAjax(@RequestBody Map<String,Long> data) {
		String fooResourceUrl = url +"/api-rs/books/"+data.get("id").toString();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Book> result = restTemplate.getForEntity(fooResourceUrl, Book.class);
		return result.getBody();

	}
	@RequestMapping(value = "/ajax/delete/books",method = RequestMethod.DELETE)
	public @ResponseBody String deleteBookViaAjax(@RequestBody Map<String,Long> data) {
		String fooResourceUrl = url +"/api-rs/books/"+data.get("id").toString();
		RestTemplate restTemplate = new RestTemplate();
		URL url=null;
		ResponseEntity<String> result =null;
		try {
			url = new URL(fooResourceUrl);
			restTemplate.delete(url.toURI());
			result = new ResponseEntity<>("book "+data.get("id").toString()+" was deleted",HttpStatus.ACCEPTED);
		} catch (MalformedURLException e) {
			result = new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (RestClientException e) {
			result = new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (URISyntaxException e) {
			result = new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return result.getBody();

	}
}
