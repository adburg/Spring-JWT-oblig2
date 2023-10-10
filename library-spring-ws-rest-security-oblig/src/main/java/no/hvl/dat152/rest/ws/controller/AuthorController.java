/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.service.AuthorService;

/**
 * 
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	@GetMapping("/authors")
	public ResponseEntity<Object> getAllBooks(){
		
		List<Author> authors = authorService.findAll();
		
		if(authors.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<>(authors, HttpStatus.OK);		
	}
	
	@GetMapping("/authors/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Author> getAuthor(@PathVariable("id") Long id) throws AuthorNotFoundException {
		
		Author author = authorService.findById(id);
		
		return new ResponseEntity<>(author, HttpStatus.OK);		
	}
	
	@GetMapping("/authors/{id}/books")
	public ResponseEntity<Object> getAuthorsBooks(@PathVariable("id") long id) {
		Author author;
		try {
			author = authorService.findById(id);
		} catch(AuthorNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(author.getBooks(), HttpStatus.OK);
	}
	
	@PostMapping("/authors")
	public ResponseEntity<Author> createAuthor(@RequestBody Author author){
		
		Author nauthor = authorService.saveAuthor(author);
		
		return new ResponseEntity<>(nauthor, HttpStatus.CREATED);
	}
	
	@PostMapping("/updateauthor")
	public ResponseEntity<Author> updateAuthor(@RequestBody Author author, long id) throws AuthorNotFoundException{

		Author nauthor = authorService.updateAuthor(author, id);
		
		return new ResponseEntity<>(nauthor, HttpStatus.OK);
	}
	
	@PutMapping("/authors/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> updateAuthors(@RequestBody Author author, @PathVariable("id") int id) {
		Author uauthor;
		try {
			uauthor = authorService.updateAuthor(author, id);
		} catch(AuthorNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(uauthor, HttpStatus.OK);
	}


}
