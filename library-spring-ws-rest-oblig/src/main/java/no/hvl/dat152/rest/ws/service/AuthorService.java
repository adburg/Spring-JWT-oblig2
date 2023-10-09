/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.BookNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.repository.AuthorRepository;

/**
 * @author 
 */
@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	
	public Author saveAuthor (Author author) {
		if(author == null) return null;
		
		return authorRepository.save(author);
	}
	
	public List<Author> findAll(){
		
		return (List<Author>) authorRepository.findAll();
		
	}
	
	public Author findAllAuthorById(long id) throws AuthorNotFoundException {
		Author authors = findById(id);

		return authors;
	}
	
	public Author findById(long id) throws AuthorNotFoundException {
		
		Author author = authorRepository.findById(id)
				.orElseThrow(() -> new AuthorNotFoundException("Author with id = "+id+" not found!"));
		
		return author;
	}
	
	public Author updateAuthor(Author author, long id) throws AuthorNotFoundException{
		Author returnauthor = findById(id);
		
		returnauthor.setBooks(author.getBooks());
		returnauthor.setFirstname(author.getFirstname());
		returnauthor.setLastname(author.getLastname());
		
		returnauthor = authorRepository.save(returnauthor);
		
		return returnauthor;
	}
	
	public Author deleteAuthor(long id) throws AuthorNotFoundException {
		Author author = findById(id);
		
		if(author == null) {
			return null;
		}else {
			authorRepository.delete(author);
		}
		return author;
	}

}
