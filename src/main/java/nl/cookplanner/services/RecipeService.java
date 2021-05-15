package nl.cookplanner.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.cookplanner.domain.Recipe;
import nl.cookplanner.domain.RecipeType;
import nl.cookplanner.execptions.RecipeAlreadyExistsException;
import nl.cookplanner.execptions.RecipeNotFoundException;
import nl.cookplanner.repositories.RecipeRepository;

@Service
@Transactional
public class RecipeService {

	private final RecipeRepository recipeRepository;

	public RecipeService(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	
	public List<Recipe> findAll() {
		return recipeRepository.findAll();
	}
	
	public Recipe save(Recipe recipe) {
		if (recipeRepository.findByName(recipe.getName()).isPresent()) {	
			throw new RecipeAlreadyExistsException("Recept " + recipe.getName() + " bestaat al");
		}
		return recipeRepository.save(recipe);
	}
	
	public Recipe update(Recipe recipe, Long id) {
		Optional<Recipe> optionalRecipe = recipeRepository.findByName(recipe.getName()); 
		if (optionalRecipe.isPresent() && !id.equals(optionalRecipe.get().getId())){
			throw new RecipeAlreadyExistsException("Recept " + recipe.getName() + " bestaat al");
		}
		recipe.setId(id);
		return recipeRepository.save(recipe);
	}
	
	public Recipe findById(Long id) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
		if (optionalRecipe.isPresent()) {
			return optionalRecipe.get();
		} else {
			throw new RecipeNotFoundException("Recept kan niet worden gevonden");
		}
	}
	
	public Recipe delete(Long id) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
		if (optionalRecipe.isPresent()) {
			recipeRepository.deleteById(id);
			return optionalRecipe.get();
		} else {
			throw new RecipeNotFoundException("Recept met id " + id + " kan niet worden gevonden");
		}
	}
	
	public List<String> findAllRecipeTypes() {
		return Stream.of(RecipeType.values())
				.map(RecipeType::name)
				.collect(Collectors.toList());
	}
	
}
