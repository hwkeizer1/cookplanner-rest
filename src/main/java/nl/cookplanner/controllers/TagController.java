package nl.cookplanner.controllers;

import java.util.List;
import java.util.Optional;

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

import lombok.extern.slf4j.Slf4j;
import nl.cookplanner.domain.Tag;
import nl.cookplanner.execptions.ErrorResponse;
import nl.cookplanner.execptions.TagAlreadyExistsException;
import nl.cookplanner.execptions.TagNotFoundException;
import nl.cookplanner.services.TagService;

@Slf4j
@RestController
@RequestMapping("tag")
@CrossOrigin(origins = "http://localhost:4200")
public class TagController {

	private final TagService tagService;
	
	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	@GetMapping("/list")
	public ResponseEntity<List<Tag>> getTags() {
		return ResponseEntity.ok(tagService.listAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tag> get(@PathVariable Long id) {
		Optional<Tag> optionalTag = tagService.get(id);
		if (optionalTag.isPresent()) {
			return ResponseEntity.ok(optionalTag.get());
		} else {
			throw new TagNotFoundException("Categorie kan niet worden gevonden");
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Tag> create(@RequestBody Tag tag) throws TagAlreadyExistsException {
		return ResponseEntity.ok(tagService.save(tag));
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Tag> update(@RequestBody Tag tag, @PathVariable Long id) {
		Optional<Tag> optionalTag = tagService.get(id);
		if (optionalTag.isPresent()) {
			return ResponseEntity.ok(tagService.save(tag));
		} else {
			throw new TagNotFoundException("Categorie kan niet worden gevonden");
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Tag> delete(@PathVariable Long id) {
		// TODO: Build additional check if category is in use and handle accordingly
		Optional<Tag> optionalTag = tagService.get(id);
		if (optionalTag.isPresent()) {
			tagService.delete(id);
			return ResponseEntity.ok(optionalTag.get());
		} else {
			throw new TagNotFoundException("Categorie kan niet worden gevonden");
		}
		
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
