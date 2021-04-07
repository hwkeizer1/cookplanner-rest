package nl.cookplanner.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import nl.cookplanner.repositories.IngredientRepository;

@Slf4j
@Service
@Transactional
public class IngredientService {

	private final IngredientRepository ingredientRepository;

	public IngredientService(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}
	
	
}
