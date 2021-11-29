package com.example.springmongodb.resource;



import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.List;

import com.example.springmongodb.Service.StudentService;
import com.example.springmongodb.model.Student;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/findAllStudents")
    public List<Student> getStudents() {
		List<Student> students = studentService.getAllStudents();
		return students;
	}

    @PostMapping("/addStudent")
	public String saveStudent(@RequestBody Student student) {
		studentService.saveStudent(student);
		return "Added student with id : " + student;
	}
    
    
    
}
