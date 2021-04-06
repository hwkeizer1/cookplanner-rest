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

import nl.cookplanner.domain.MeasureUnit;
import nl.cookplanner.execptions.MeasureUnitAlreadyExistsException;
import nl.cookplanner.execptions.MeasureUnitNotFoundException;
import nl.cookplanner.repositories.MeasureUnitRepository;

@ExtendWith(SpringExtension.class)
class MeasureUnitServiceTest {

	@Mock
	private MeasureUnitRepository measureUnitRepository;
	
	@InjectMocks MeasureUnitService measureUnitService;
	
	@Test
	void testSave_HappyPath() {
		MeasureUnit measureUnit = getMockMeasureUnit(1L, "test", "testen");
		when(measureUnitRepository.findByName("test")).thenReturn(Optional.empty());
		
		measureUnitService.save(measureUnit);
		
		verify(measureUnitRepository, times(1)).save(measureUnit);
	}
	
	@Test
	void testSave_AlreadyExists() {
		MeasureUnit measureUnit = getMockMeasureUnit(1L, "test", "testen");
		when(measureUnitRepository.findByName("test")).thenReturn(Optional.of(measureUnit));
		
		Exception exception = assertThrows(MeasureUnitAlreadyExistsException.class, () -> {
			measureUnitService.save(measureUnit);
		});
		
		String expectedMessage = "Maateenheid " + measureUnit.getName() + " bestaat al";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,	expectedMessage);
	}

	@Test
	void testUpdate_HappyPath() {
		MeasureUnit measureUnit = getMockMeasureUnit(1L, "test", "testen");
		when(measureUnitRepository.findByName("test")).thenReturn(Optional.of(measureUnit));
		
		MeasureUnit updatedMeasureUnit = measureUnitService.update(measureUnit, 1L);
		
		verify(measureUnitRepository, times(1)).save(measureUnit);
	}
	
	@Test
	void testUpdate_AlreadyExists() {
		MeasureUnit measureUnit = getMockMeasureUnit(1L, "test", "testen");
		MeasureUnit otherMeasureUnit = getMockMeasureUnit(2L, "test", "testen");
		when(measureUnitRepository.findByName("test")).thenReturn(Optional.of(otherMeasureUnit));
		
		Exception exception = assertThrows(MeasureUnitAlreadyExistsException.class, () -> {
			measureUnitService.update(measureUnit, 1L);
		});
		
		String expectedMessage = "Maateenheid " + measureUnit.getName() + " bestaat al";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	void testFindById_HappyPath() {
		MeasureUnit measureUnit = getMockMeasureUnit(1L, "test", "testen");
		when(measureUnitRepository.findById(1L)).thenReturn(Optional.of(measureUnit));
		
		MeasureUnit resultMeasureUnit = measureUnitService.findById(1L);
		
		assertEquals(measureUnit, resultMeasureUnit);
	}
	
	@Test
	void testFindById_NotFound() {
		when(measureUnitRepository.findById(1L)).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(MeasureUnitNotFoundException.class, () -> {
			measureUnitService.findById(1L);
		});
		
		String expectedMessage = "Maateenheid kan niet worden gevonden";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	void testDelete_HappyPath() {
		MeasureUnit measureUnit = getMockMeasureUnit(1L, "test", "testen");
		when(measureUnitRepository.findById(1L)).thenReturn(Optional.of(measureUnit));
		
		MeasureUnit resultMeasureUnit = measureUnitService.delete(1L);
		
		verify(measureUnitRepository, times(1)).deleteById(1L);
		assertEquals(measureUnit, resultMeasureUnit);
	}
	
	@Test
	void testDelete_NotFound() {
		Long id = 1L;
		when(measureUnitRepository.findById(id)).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(MeasureUnitNotFoundException.class, () -> {
			measureUnitService.delete(1L);
		});
		
		String expectedMessage = "Maateenheid met id " + id + " kan niet worden gevonden";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

	private MeasureUnit getMockMeasureUnit(Long id, String name, String pluralName) {
		MeasureUnit measureUnit = new MeasureUnit(name, pluralName);
		measureUnit.setId(id);
		return measureUnit;
	}
}
