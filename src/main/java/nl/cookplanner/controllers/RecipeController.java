package nl.cookplanner.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.cookplanner.domain.Recipe;
import nl.cookplanner.repositories.RecipeRepository;
import nl.cookplanner.repositories.TagRepository;
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
	
}
