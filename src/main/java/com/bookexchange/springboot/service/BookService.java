package com.bookexchange.springboot.service;

import java.util.List;

import com.bookexchange.springboot.entity.Book;
import com.bookexchange.springboot.entity.ExchangeRequest;

public interface BookService {

	Book createBook(Book book);
	
	Book getBookbyId(Long bookId);
	
	List<Book> getAllBooks();
	
	Book updateBook(Book book);
	
	void deleteBook(Long bookId);
	
	//void sendExchangeRequest(Book requestedBook, String status,String deliveryMethod, int duration);

	ExchangeRequest sendExchangeRequest(ExchangeRequest request);
}