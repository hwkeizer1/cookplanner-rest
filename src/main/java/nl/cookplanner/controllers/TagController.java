package nl.cookplanner.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.cookplanner.domain.Tag;
import nl.cookplanner.services.TagService;

@RestController
public class TagController {

	private final TagService tagService;
	
	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	@GetMapping("/tags")
	public List<Tag> getTags() {
		return tagService.listAll();
	}
	
	@GetMapping("/tags/{id}")
	public ResponseEntity<Tag> get(@PathVariable Long id) {
		Optional<Tag> optionalTag = tagService.get(id);
		if (optionalTag.isPresent()) {
			return new ResponseEntity<>(optionalTag.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/tags")
	public void add(@RequestBody Tag tag) {
		tagService.save(tag);
	}
	
	@PutMapping("/tags/{id}")
	public ResponseEntity<Tag> update(@RequestBody Tag tag, @PathVariable Long id) {
		Optional<Tag> optionalTag = tagService.get(id);
		if (optionalTag.isPresent()) {
			tagService.save(tag);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("tags/{id}")
	public void delete(@PathVariable Long id) {
		tagService.delete(id);
	}
}
