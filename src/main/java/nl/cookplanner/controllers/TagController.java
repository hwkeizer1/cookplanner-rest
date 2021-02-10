package nl.cookplanner.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.cookplanner.domain.Tag;
import nl.cookplanner.execptions.ErrorResponse;
import nl.cookplanner.execptions.TagAlreadyExistsException;
import nl.cookplanner.execptions.TagNotFoundException;
import nl.cookplanner.services.TagService;

@RestController
@RequestMapping("tags")
@CrossOrigin(origins = "http://localhost:4200")
public class TagController {

	private final TagService tagService;
	
	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	@GetMapping()
	public ResponseEntity<List<Tag>> findAll() {
		return ResponseEntity.ok(tagService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tag> findById(@PathVariable Long id) {
		return ResponseEntity.ok(tagService.findById(id));
	}
	
	@PostMapping()
	public ResponseEntity<Tag> create(@RequestBody Tag tag) {
		return ResponseEntity.ok(tagService.save(tag));
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Tag> update(@RequestBody Tag tag, @PathVariable Long id) {
		return ResponseEntity.ok(tagService.update(tag, id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Tag> delete(@PathVariable Long id) {
		return ResponseEntity.ok(tagService.delete(id));
	}
	
	@ExceptionHandler({TagNotFoundException.class})
    public ResponseEntity<ErrorResponse> tagNotFound(TagNotFoundException ex){

        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage(), 404) , HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler({TagAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> tagNotFound(TagAlreadyExistsException ex){

        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage(), 400) , HttpStatus.BAD_REQUEST);
    }
}
