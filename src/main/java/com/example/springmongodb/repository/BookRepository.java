package com.example.springmongodb.repository;

import com.example.springmongodb.model.Book;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface BookRepository extends MongoRepository<Book, Integer>{

}
