package com.example.springmongodb.repository;

import java.util.Optional;

import com.example.springmongodb.model.Student;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRespository extends MongoRepository<Student, String> {
  Optional<Student> findStudentByEmail(String email);
}
