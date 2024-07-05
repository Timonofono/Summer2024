package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class DemoController {

	private static final Logger logger = LogManager.getLogger(DemoController.class);

	// Список для хранения контактов
	private List<Person> contacts = new ArrayList<>();

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		logger.info("Handling hello request");
		return ResponseEntity.ok("Hello, World!");
	}

	@GetMapping("/enterName")
	public String enterName() {
		return "enterName";
	}

	@PostMapping("/result")
	public ResponseEntity<Person> result(@RequestParam(name = "firstName") String firstName,
										 @RequestParam(name = "lastName") String lastName,
										 @RequestParam(name = "phoneNumber") String phoneNumber) {
		try {
			Person person = new Person(firstName, lastName, phoneNumber);
			contacts.add(person);
			return ResponseEntity.ok(person);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid input");
		}
	}

	@GetMapping("/allContacts")
	public String allContacts(Model model) {
		model.addAttribute("contacts", contacts); // Убедитесь, что переменная contacts определена выше
		return "allContacts";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Класс для представления контакта
	static class Person {
		private String firstName;
		private String lastName;
		private String phoneNumber;

		public Person(String firstName, String lastName, String phoneNumber) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.phoneNumber = phoneNumber;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
	}

	// Класс для представления ошибки
	static class ErrorResponse {
		private String errorMessage;

		public ErrorResponse(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
	}
}
