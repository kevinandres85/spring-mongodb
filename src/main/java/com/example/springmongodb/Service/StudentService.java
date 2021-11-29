package com.example.springmongodb.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


import com.example.springmongodb.model.Book;
import com.example.springmongodb.model.Student;
import com.example.springmongodb.repository.StudentRespository;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor
@Service
public class StudentService {

    @Autowired
    private StudentRespository studentRespository;
    private List<Book> books;
    private List<Student> students;
    private static final Moshi MOSHI = new Moshi.Builder().build();
    private static final JsonAdapter<List<Book>> BOOKS_JSON_ADAPTER = MOSHI.adapter(
            Types.newParameterizedType(List.class, Book.class));
    public List<Student> getAllStudents() {
        HttpClient client = HttpClient.newHttpClient(); // creacion del cliente HTTP
        HttpRequest request = HttpRequest.newBuilder()// creacion del request
                .uri(URI.create("http://localhost:8080/findAllBooks"))
                .GET()
                .build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // creacion del repsonse
            books = BOOKS_JSON_ADAPTER.fromJson(response.body());
            System.out.println(books);
           students = studentRespository.findAll();
                    students.get(0).setBooks(books);
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return students;
        
    }

    public void saveStudent(Student student){
        studentRespository.save(student);
    }

}
