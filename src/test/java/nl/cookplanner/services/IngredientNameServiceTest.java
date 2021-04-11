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
import nl.cookplanner.domain.MeasureUnit;
import nl.cookplanner.execptions.IngredientNameAlreadyExistsException;
import nl.cookplanner.execptions.IngredientNameNotFoundException;
import nl.cookplanner.repositories.IngredientNameRepository;
import nl.cookplanner.repositories.IngredientRepository;

@ExtendWith(SpringExtension.class)
class IngredientNameServiceTest {

	@Mock
	IngredientNameRepository ingredientNameRepository;
	
	@InjectMocks
	IngredientNameService ingredientNameService;
	
	@Test
	void testSave_HappyPath() {
		IngredientName ingredientName = getMockIngredientName(1L, "test", "testen", true);
		when(ingredientNameRepository.findByName("test")).thenReturn(Optional.empty());
		
		ingredientNameService.save(ingredientName);
		
		verify(ingredientNameRepository, times(1)).save(ingredientName);
	}
	
	@Test
	void testSave_AlreadyExists() {
		IngredientName ingredientName = getMockIngredientName(1L, "test", "testen", true);
		when(ingredientNameRepository.findByName("test")).thenReturn(Optional.of(ingredientName));
		
		Exception exception = assertThrows(IngredientNameAlreadyExistsException.class, () -> {
			ingredientNameService.save(ingredientName);
		});
		
		String expectedMessage = "Ingrediënt naam " + ingredientName.getName() + " bestaat al";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,	expectedMessage);
	}

	@Test
	void testUpdate_HappyPath() {
		IngredientName ingredientName = getMockIngredientName(1L, "test", "testen", true);
		when(ingredientNameRepository.findByName("test")).thenReturn(Optional.of(ingredientName));
		
		ingredientNameService.update(ingredientName, 1L);
		
		verify(ingredientNameRepository, times(1)).save(ingredientName);
	}
	
	@Test
	void testUpdate_AlreadyExists() {
		IngredientName ingredientName = getMockIngredientName(1L, "test", "testen", true);
		IngredientName otherIngredientName = getMockIngredientName(2L, "test", "testen", false);
		when(ingredientNameRepository.findByName("test")).thenReturn(Optional.of(otherIngredientName));
		
		Exception exception = assertThrows(IngredientNameAlreadyExistsException.class, () -> {
			ingredientNameService.update(ingredientName, 1L);
		});
		
		String expectedMessage = "Ingrediënt naam " + ingredientName.getName() + " bestaat al";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,	expectedMessage);
	}

	@Test
	void testFindById_HappyPath() {
		IngredientName ingredientName = getMockIngredientName(1L, "test", "testen", true);
		when(ingredientNameRepository.findById(1L)).thenReturn(Optional.of(ingredientName));
		
		IngredientName resultIngredientName = ingredientNameService.findById(1L);
		
		verify(ingredientNameRepository, times(1)).findById(1L);
		assertEquals(ingredientName, resultIngredientName);
	}
	
	@Test
	void testFindById_NotFound() {
		when(ingredientNameRepository.findById(1L)).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(IngredientNameNotFoundException.class, () -> {
			ingredientNameService.findById(1L);
		});
		
		String expectedMessage = "Ingrediënt naam kan niet worden gevonden";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,	expectedMessage);
	}

	@Test
	void testDelete_HappyPath() {
		IngredientName ingredientName = getMockIngredientName(1L, "test", "testen", true);
		when(ingredientNameRepository.findById(1L)).thenReturn(Optional.of(ingredientName));
		
		IngredientName resultIngredientName = ingredientNameService.delete(1L);
		
		verify(ingredientNameRepository, times(1)).deleteById(1L);
		assertEquals(ingredientName, resultIngredientName);
	}
	
	@Test
	void testDelete_NotFound() {
		Long id = 1L;
		when(ingredientNameRepository.findById(1L)).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(IngredientNameNotFoundException.class, () -> {
			ingredientNameService.delete(1L);
		});
		
		String expectedMessage = "Ingrediënt naam met id " + id + " kan niet worden gevonden";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,	expectedMessage);
	}
	
	private IngredientName getMockIngredientName(Long id, String name, String pluralName, boolean stock) {
		IngredientName ingredientName = new IngredientName(name, pluralName, stock);
		ingredientName.setId(id);
		return ingredientName;
	}

}
