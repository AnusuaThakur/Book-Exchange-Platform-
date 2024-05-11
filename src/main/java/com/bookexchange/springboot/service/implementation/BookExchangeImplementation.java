package com.bookexchange.springboot.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookexchange.springboot.entity.Book;
import com.bookexchange.springboot.entity.ExchangeRequest;
import com.bookexchange.springboot.repository.BookRepo;
import com.bookexchange.springboot.service.BookService;

@Service
public class BookExchangeImplementation implements BookService {

	@Autowired
	private BookRepo repo;

	@Override
	public Book createBook(Book book) {
		// TODO Auto-generated method stub
		return repo.save(book);
	}

	@Override
	public Book getBookbyId(Long bookId) {
		Optional<Book> optional = repo.findById(bookId);
		return optional.get();
	}

	@Override
	public List<Book> getAllBooks() {
		return repo.findAll();
	}

	@Override
	public Book updateBook(Book book) {
		Book existingBook = repo.findById(book.getId()).get();
		existingBook.setTitle(book.getTitle());
		existingBook.setAuthor(book.getAuthor());
		existingBook.setDescription(book.getDescription());
		Book updatedBook = repo.save(existingBook);
		return updatedBook;
	}

	@Override
	public void deleteBook(Long bookId) {
		// TODO Auto-generated method stub
		repo.deleteById(bookId);

	}

	@Override
	public ExchangeRequest sendExchangeRequest(ExchangeRequest request) {
		
		request.setRequestedBook(request.getRequestedBook());
		request.setDeliveryMethod(request.getDeliveryMethod());
		request.setDuration(request.getDuration());
		request.setStatus("Pending");
		return request;
	}
}
