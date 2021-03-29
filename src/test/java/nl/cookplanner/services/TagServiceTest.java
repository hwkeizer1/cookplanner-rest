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

import nl.cookplanner.domain.Tag;
import nl.cookplanner.execptions.TagAlreadyExistsException;
import nl.cookplanner.execptions.TagNotFoundException;
import nl.cookplanner.repositories.TagRepository;

@ExtendWith(SpringExtension.class)
class TagServiceTest {

	@Mock
	private TagRepository tagRepository;
	
	@InjectMocks
	TagService tagService;
	
	@Test 
	void testSave_HappyPath() {
		Tag tag = getMockTag(1L, "test");
		when(tagRepository.findByName("test")).thenReturn(Optional.empty());
		
		tagService.save(tag);
		
		verify(tagRepository, times(1)).save(tag);
	}

	@Test
	void testSave_AlreadyExists() {
		Tag tag = getMockTag(1L, "test");
		when(tagRepository.findByName("test")).thenReturn(Optional.of(tag));
		
		Exception exception = assertThrows(TagAlreadyExistsException.class, () -> {
			tagService.save(tag);
		});
		
		String expectedMessage = "Categorie " + tag.getName() + " bestaat al";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	void testUpdate_HappyPath() {
		Tag tag = getMockTag(1L, "test");
		when(tagRepository.findByName("test")).thenReturn(Optional.empty());
		
		tagService.update(tag, 1L);
		
		verify(tagRepository, times(1)).save(tag);
	}
	
	@Test
	void testUpdate_HappyPathWithoutUpdate() {
		Tag tag = getMockTag(1L, "test");
		when(tagRepository.findByName("test")).thenReturn(Optional.of(tag));
		
		tagService.update(tag, 1L);
		
		verify(tagRepository, times(1)).save(tag);
	}
	
	@Test
	void testUpdate_AlreadyExists() {
		Tag tag = getMockTag(1L, "test");
		Tag otherTag = getMockTag(2L, "test");
		when(tagRepository.findByName("test")).thenReturn(Optional.of(otherTag));
		
		Exception exception = assertThrows(TagAlreadyExistsException.class, () -> {
			tagService.update(tag, 1L);
		});
		
		String expectedMessage = "Categorie " + tag.getName() + " bestaat al";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	void testFindById_HappyPath() {
		Tag tag = getMockTag(1L, "test");
		when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
		
		Tag resultTag = tagService.findById(1L);
		
		assertEquals(tag, resultTag);
	}
	
	@Test
	void testFindById_NotFound() {
		when(tagRepository.findById(1L)).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(TagNotFoundException.class, () -> {
			tagService.findById(1L);
		});
		
		String expectedMessage = "Categorie kan niet worden gevonden";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	void testDelete_HappyPath() {
		Tag tag = getMockTag(1L, "test");
		when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
		
		Tag resultTag = tagService.delete(1L);
		
		verify(tagRepository, times(1)).deleteById(1L);
		assertEquals(tag, resultTag);
	}
	
	@Test
	void testDelete_NotFound() {
		Long id = 1L;
		when(tagRepository.findById(id)).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(TagNotFoundException.class, () -> {
			tagService.delete(id);
		});
		
		String expectedMessage = "Categorie met id " + id + " kan niet worden gevonden";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

	private Tag getMockTag(Long id, String name) {
		Tag tag = new Tag(name);
		tag.setId(id);
		return tag;
	}
}
