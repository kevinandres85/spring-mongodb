package com.example.springmongodb.resource;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import com.example.springmongodb.model.Book;
import com.example.springmongodb.repository.BookRepository;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BookController {

	@Autowired
	private BookRepository repository;

	@PostMapping("/addBook")
	public String saveBook(@RequestBody Book book) {
		repository.save(book);
		return "Added book with id : " + book.getId();
	}

	@GetMapping("/findAllBooks")
	public List<Book> getBooks() {
		List<Book> books = repository.findAll();
		return books;
	}

	@GetMapping("/findAllBooks/{id}")
	public Optional<Book> getBook(@PathVariable int id) {
		return repository.findById(id);
	}

	@PutMapping("/updateBook/{id}")
	public String updateBook(@PathVariable int id, @RequestBody Book book) {

		if(repository.findById(id).isPresent()){
			repository.save(book);
			return "book update with id : " + id;
		}else{
			return "book doesnt exist with id : " + id;
		}
		
	}

	@DeleteMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id) {
		repository.deleteById(id);
		return "book deleted with id : " + id;
	}

	@GetMapping(path = "/generate-calendar")
	public ResponseEntity generateCalenderFile() {

		/* Event start and end time in milliseconds */
		Long startDateTimeInMillis = 1615956275000L;
		Long endDateTimeInMillis = 1615959875000L;

		java.util.Calendar calendarStartTime = new GregorianCalendar();
		calendarStartTime.setTimeInMillis(startDateTimeInMillis);

		// Time zone info
		TimeZone tz = calendarStartTime.getTimeZone();
		ZoneId zid = tz.toZoneId();


		/* Generate unique identifier */
		UidGenerator ug = new RandomUidGenerator();
		Uid uid = ug.generateUid();

		/* Create the event */
		String eventSummary = "Happy New Year";
		Date start = new Date();
		Date end = new Date();
		VEvent event = new VEvent(start, end, eventSummary);
		event.getProperties().add(uid);

		/* Create calendar */
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);

		/* Add event to calendar */
		calendar.getComponents().add(event);


		byte[] calendarByte = calendar.toString().getBytes();
		Resource resource = new ByteArrayResource(calendarByte);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mycalendar.ics");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		return ResponseEntity.ok().headers(header).contentType(MediaType.
						APPLICATION_OCTET_STREAM)
				.body(resource);
	}


}
