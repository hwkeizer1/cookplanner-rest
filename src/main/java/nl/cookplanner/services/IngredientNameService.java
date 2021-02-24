package nl.cookplanner.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import nl.cookplanner.domain.IngredientName;
import nl.cookplanner.domain.IngredientType;
import nl.cookplanner.domain.ShopType;
import nl.cookplanner.execptions.IngredientNameAlreadyExistsException;
import nl.cookplanner.execptions.IngredientNameNotFoundException;
import nl.cookplanner.repositories.IngredientNameRepository;

@Slf4j
@Service
@Transactional
public class IngredientNameService {

	private final IngredientNameRepository ingredientNameRepository;

	public IngredientNameService(IngredientNameRepository ingredientNameRpository) {
		this.ingredientNameRepository = ingredientNameRpository;
	}
	
	public List<IngredientName> findAll() {
		return ingredientNameRepository.findAll();
	}
	
	public IngredientName save(IngredientName ingredientName) {
		if (ingredientNameRepository.findByName(ingredientName.getName()).isPresent()) {	
			throw new IngredientNameAlreadyExistsException("Ingrediënt naam " + ingredientName.getName() + " bestaat al");
		}
		return ingredientNameRepository.save(ingredientName);
	}
	
	public IngredientName update(IngredientName ingredientName, Long id) {
		Optional<IngredientName> optionalIngredientName = ingredientNameRepository.findByName(ingredientName.getName()); 
		if (optionalIngredientName.isPresent() && !id.equals(optionalIngredientName.get().getId())){
			throw new IngredientNameAlreadyExistsException("Ingrediënt naam " + ingredientName.getName() + " bestaat al");
		}
		ingredientName.setId(id);
		return ingredientNameRepository.save(ingredientName);
	}
	
	public IngredientName findById(Long id) {
		Optional<IngredientName> optionalIngredientName = ingredientNameRepository.findById(id);
		if (optionalIngredientName.isPresent()) {
			return optionalIngredientName.get();
		} else {
			throw new IngredientNameNotFoundException("Ingrediënt naam kan niet worden gevonden");
		}
	}
	
	public IngredientName delete(Long id) {
		// TODO: Build additional check if measureUnit is in use and handle accordingly
		Optional<IngredientName> optionalIngredientName = ingredientNameRepository.findById(id);
		if (optionalIngredientName.isPresent()) {
			ingredientNameRepository.deleteById(id);
			return optionalIngredientName.get();
		} else {
			throw new IngredientNameNotFoundException("Ingrediënt naam met id " + id + " kan niet worden gevonden");
		}
	}
	
	public List<String> findAllIngredientTypes() {
		return Stream.of(IngredientType.values())
				.map(IngredientType::name)
				.collect(Collectors.toList());
	}
	
	public List<String> findAllShopTypes() {
		return Stream.of(ShopType.values())
				.map(ShopType::name)
				.collect(Collectors.toList());
	}
}
