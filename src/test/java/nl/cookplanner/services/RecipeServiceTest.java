package nl.cookplanner.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import nl.cookplanner.domain.IngredientName;
import nl.cookplanner.domain.Recipe;
import nl.cookplanner.execptions.RecipeAlreadyExistsException;
import nl.cookplanner.execptions.RecipeNotFoundException;
import nl.cookplanner.repositories.RecipeRepository;

@ExtendWith(SpringExtension.class)
class RecipeServiceTest {
	
	@Mock
	RecipeRepository recipeRepository;
	
	@InjectMocks
	RecipeService recipeService;

	@Test
	void testSave_HappyPath() {
		Recipe recipe = getMockRecipe(1L, "test");
		when(recipeRepository.findByName("test")).thenReturn(Optional.empty());
		
		recipeService.save(recipe);
		
		verify(recipeRepository, times(1)).save(recipe);
	}
	
	@Test
	void testSave_AlreadyExists() {
		Recipe recipe = getMockRecipe(1L, "test");
		when(recipeRepository.findByName("test")).thenReturn(Optional.of(recipe));
		
		Exception exception = assertThrows(RecipeAlreadyExistsException.class, () -> {
			recipeService.save(recipe);
		});
		
		String expectedMessage = "Recept " + recipe.getName() + " bestaat al";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,	expectedMessage);
	}
	
	@Test
	void testUpdate_HappyPath() {
		Recipe recipe = getMockRecipe(1L, "test");
		when(recipeRepository.findByName("test")).thenReturn(Optional.of(recipe));
		
		recipeService.update(recipe, 1L);
		
		verify(recipeRepository, times(1)).save(recipe);
	}
	
	@Test
	void testUpdate_AlreadyExists() {
		Recipe recipe = getMockRecipe(1L, "test");
		Recipe otherRecipe = getMockRecipe(2L, "test");
		when(recipeRepository.findByName("test")).thenReturn(Optional.of(otherRecipe));
		
		Exception exception = assertThrows(RecipeAlreadyExistsException.class, () -> {
			recipeService.update(recipe, 1L);
		});
		
		String expectedMessage = "Recept " + recipe.getName() + " bestaat al";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,	expectedMessage);
	}
	
	@Test
	void testFindById_HappyPath() {
		Recipe recipe = getMockRecipe(1L, "test");
		when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
		
		Recipe resultRecipe = recipeService.findById(1L);
		
		verify(recipeRepository, times(1)).findById(1L);
		assertEquals(recipe, resultRecipe);
	}
	
	@Test
	void testFindById_NotFound() {
		when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(RecipeNotFoundException.class, () -> {
			recipeService.findById(1L);
		});
		
		String expectedMessage = "Recept kan niet worden gevonden";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,	expectedMessage);
	}
	
	@Test
	void testDelete_HappyPath() {
		Recipe recipe = getMockRecipe(1L, "test");
		when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
		
		Recipe resultRecipe = recipeService.delete(1L);
		
		verify(recipeRepository, times(1)).deleteById(1L);
		assertEquals(recipe, resultRecipe);
	}
	
	@Test
	void testDelete_NotFound() {
		Long id = 1l;
		when(recipeRepository.findById(id)).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(RecipeNotFoundException.class, () -> {
			recipeService.delete(id);
		});
		
		String expectedMessage = "Recept met id " + id + " kan niet worden gevonden";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,	expectedMessage);
	}
	
	private Recipe getMockRecipe(Long id, String name) {
		Recipe recipe = new Recipe(name);
		recipe.setId(id);
		return recipe;
	}
}
