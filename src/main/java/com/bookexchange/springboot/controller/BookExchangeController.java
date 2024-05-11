package com.bookexchange.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bookexchange.springboot.entity.Book;
import com.bookexchange.springboot.entity.ExchangeRequest;
import com.bookexchange.springboot.service.BookService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/books")
public class BookExchangeController {

	@Autowired
	private BookService bookService;

	//http://localhost:8082/api/books
	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book savedBook = bookService.createBook(book);
		return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
	}

	// Get book by id
	@GetMapping("{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId) {
		Book book = bookService.getBookbyId(bookId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	// Get all books
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> bookList = bookService.getAllBooks();
		return new ResponseEntity<>(bookList, HttpStatus.OK);
	}

	// Update a book
	@PutMapping("{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") Long bookId, @RequestBody Book book) {
		book.setId(bookId);
		Book updatedBook = bookService.updateBook(book);
		return new ResponseEntity<>(updatedBook, HttpStatus.OK);
	}
	//Delete book by id
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId){
		bookService.deleteBook(bookId);
		return new ResponseEntity<>("Book deletion successful", HttpStatus.OK);
	}
	
	@PostMapping("/request")
	public ResponseEntity<ExchangeRequest> requestExchange(@RequestBody ExchangeRequest req) {
		ExchangeRequest reqBook = bookService.sendExchangeRequest(req);
		return new ResponseEntity<>(reqBook, HttpStatus.CREATED);
	}
	
	@PostMapping("/request/{id}/accept")
	  public ResponseEntity<String> acceptRequest(@PathVariable Long id) {
	    // Update the exchange request status to "Accepted"

	    //return ResponseEntity.ok().build();
	    return new ResponseEntity<>("exchange request Accepted", HttpStatus.OK);
	  }

	  @PostMapping("/request/{id}/decline")
	  public ResponseEntity<String> declineRequest(@PathVariable Long id) {
	    // Update the exchange request status to "Declined"
	  
		    return new ResponseEntity<>("exchange request Declined", HttpStatus.OK);
	  }
	

}
