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

import nl.cookplanner.domain.IngredientName;
import nl.cookplanner.execptions.ErrorResponse;
import nl.cookplanner.execptions.IngredientNameAlreadyExistsException;
import nl.cookplanner.execptions.IngredientNameNotFoundException;
import nl.cookplanner.services.IngredientNameService;

@RestController
@RequestMapping("ingredient-names")
@CrossOrigin(origins = "http://localhost:4200")
public class IngredientNameController {

	private IngredientNameService ingredientNameService;

	public IngredientNameController(IngredientNameService ingredientNameService) {
		this.ingredientNameService = ingredientNameService;
	}
	
	@GetMapping()
	public ResponseEntity<List<IngredientName>> findAll() {
		return ResponseEntity.ok(ingredientNameService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<IngredientName> findById(@PathVariable Long id) {
		return ResponseEntity.ok(ingredientNameService.findById(id));
	}
	
	@PostMapping()
	public ResponseEntity<IngredientName> create(@RequestBody IngredientName ingredientName) {
		return ResponseEntity.ok(ingredientNameService.save(ingredientName));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<IngredientName> update(@RequestBody IngredientName ingredientName, @PathVariable Long id) {
		return ResponseEntity.ok(ingredientNameService.update(ingredientName, id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<IngredientName> delete(@PathVariable Long id) {
		return ResponseEntity.ok(ingredientNameService.delete(id));
	}
	
	@GetMapping("/ingredienttypes")
	public ResponseEntity<List<String>> findAllIngredientTypes() {
		return ResponseEntity.ok(ingredientNameService.findAllIngredientTypes());
	}
	
	@GetMapping("/shoptypes")
	public ResponseEntity<List<String>> findAllShoptypes() {
		return ResponseEntity.ok(ingredientNameService.findAllShopTypes());
	}
	
	@ExceptionHandler({IngredientNameNotFoundException.class})
    public ResponseEntity<ErrorResponse> ingredientNameNotFound(IngredientNameNotFoundException ex){

        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage(), 404) , HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler({IngredientNameAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> ingredientNameNotFound(IngredientNameAlreadyExistsException ex){

        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage(), 400) , HttpStatus.BAD_REQUEST);
    }
}
