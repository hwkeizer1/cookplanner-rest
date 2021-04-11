package nl.cookplanner.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.cookplanner.domain.Recipe;
import nl.cookplanner.services.RecipeService;

@RestController
@RequestMapping("recipes")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {

	private final RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@GetMapping
	public ResponseEntity<List<Recipe>> findAll() {
		return ResponseEntity.ok(recipeService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Recipe> findById(@PathVariable Long id) {
		return ResponseEntity.ok(recipeService.findById(id));
	}
	
	@PostMapping()
	public ResponseEntity<Recipe> create(@RequestBody Recipe recipe) {
		return ResponseEntity.ok(recipeService.save(recipe));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Recipe> update(@RequestBody Recipe recipe, @PathVariable Long id) {
		return ResponseEntity.ok(recipeService.update(recipe, id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Recipe> delete(@PathVariable Long id) {
		return ResponseEntity.ok(recipeService.delete(id));
	}
	
}
