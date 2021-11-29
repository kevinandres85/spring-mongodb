package com.example.springmongodb;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import com.example.springmongodb.model.Address;
import com.example.springmongodb.model.Gender;
import com.example.springmongodb.model.Student;
import com.example.springmongodb.repository.StudentRespository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;


@SpringBootApplication
public class SpringMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongodbApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRespository repository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address("England", "london", "NE9");
			String email = "jahmed@gmail.com";
			Student student = new Student("Jamila", "ahmed", email, Gender.FEMALE, address,
					List.of("Computer Science", "Maths", "chemistry"), List.of(), BigDecimal.TEN,
					LocalDateTime.now());


			// System.out.println(repository.findAll());

			// useMongoTemplateAndQuery(repository, mongoTemplate, email, student);

			repository.findStudentByEmail(email).ifPresentOrElse(s -> {
				System.out.println("Student exists " + student);
			}, () -> {
				System.out.println("inserting " + student);
				repository.insert(student);
			});
		};
	}

}

