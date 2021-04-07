package nl.cookplanner.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import nl.cookplanner.domain.Recipe;
import nl.cookplanner.repositories.RecipeRepository;
import nl.cookplanner.repositories.TagRepository;

@Slf4j
@Service
@Transactional
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final TagRepository tagRepository;

	public RecipeService(RecipeRepository recipeRepository, TagRepository tagRepository) {
		this.recipeRepository = recipeRepository;
		this.tagRepository = tagRepository;
	}
	
	public List<Recipe> findAll() {
		return recipeRepository.findAll();
	}
	
}
