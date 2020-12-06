package nl.cookplanner.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class TestController {
	
	@GetMapping("/test-api")
	public String testApiResponse() {
		return "Received test-api request";
	}
}
