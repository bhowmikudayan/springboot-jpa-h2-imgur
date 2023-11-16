package com.imgur.demo.project.spring.h2.controller;

import java.time.Duration;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import com.imgur.demo.project.spring.h2.Image;
import com.imgur.demo.project.spring.h2.ImageService;
import com.imgur.demo.project.spring.h2.exception.ResourceNotFoundException;
import com.imgur.demo.project.spring.h2.model.Employee;
import com.imgur.demo.project.spring.h2.repository.EmployeeRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	private final Bucket bucket;

	@Autowired
	private ImageService imageService;

	public EmployeeController() {
		Bandwidth limit = Bandwidth.classic(100000, Refill.greedy(50, Duration.ofMinutes(1)));
		this.bucket = Bucket4j.builder()
				.addLimit(limit)
				.build();
	}

	@GetMapping("/image/{id}")
	public Image getImage(@PathVariable("id") String id) {
		LOGGER.info("getImage called for id {}", id);

		if(bucket.tryConsume(1)){
			return imageService.getImage(id);
		}
		return imageService.getImage(id);

	}

	@GetMapping("/images")
	public List<Image> getAllImages() {

		return imageService.getAllImages();
	}

	@PostMapping(value = "/uploadImage/{id}",
			consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE} )

	public void upload(@PathVariable String id, @RequestParam("file")MultipartFile multipartFile){
		LOGGER.info("upload called for imageurl {}", multipartFile.getName());
			imageService.postFile(id, multipartFile);

	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@PostMapping("/register")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@GetMapping("/greeting")
	public String greeting(Authentication authentication) {

		String userName = authentication.getName();

		return "Spring Security In-memory Authentication Example - Welcome " + userName;
	}
}
