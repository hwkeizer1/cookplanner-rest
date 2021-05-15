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

import nl.cookplanner.domain.Recipe;
import nl.cookplanner.execptions.ErrorResponse;
import nl.cookplanner.execptions.RecipeAlreadyExistsException;
import nl.cookplanner.execptions.RecipeNotFoundException;
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
	
	@GetMapping("/recipetypes")
	public ResponseEntity<List<String>> findAllRecipeTypes() {
		return ResponseEntity.ok(recipeService.findAllRecipeTypes());
	}
	
	@ExceptionHandler({RecipeNotFoundException.class})
    public ResponseEntity<ErrorResponse> recipeNotFound(RecipeNotFoundException ex){

        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage(), 404) , HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler({RecipeAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> recipeNotFound(RecipeAlreadyExistsException ex){

        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage(), 400) , HttpStatus.BAD_REQUEST);
    }
	
}
