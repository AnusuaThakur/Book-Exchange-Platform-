package com.bookexchange.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookexchange.springboot.entity.Book;

public interface BookRepo extends JpaRepository<Book, Long>{
	
	

}
