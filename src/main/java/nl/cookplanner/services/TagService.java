package nl.cookplanner.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.cookplanner.domain.Tag;
import nl.cookplanner.execptions.TagAlreadyExistsException;
import nl.cookplanner.execptions.TagNotFoundException;
import nl.cookplanner.repositories.TagRepository;

@Service
@Transactional
public class TagService {

	private final TagRepository tagRepository;

	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	public List<Tag> findAll() {
		return tagRepository.findAll();
	}
	
	public Tag save(Tag tag) {
		if (tagRepository.findByName(tag.getName()).isPresent()) {	
			throw new TagAlreadyExistsException("Categorie " + tag.getName() + " bestaat al");
		}
		return tagRepository.save(tag);
	}
	
	public Tag update(Tag tag, Long id) {
		Optional<Tag> optionalTag = tagRepository.findByName(tag.getName()); 
		if (optionalTag.isPresent() && id != optionalTag.get().getId()) {
			throw new TagAlreadyExistsException("Categorie " + tag.getName() + " bestaat al");
		}
		tag.setId(id);
		return tagRepository.save(tag);
	}
	
	public Tag findById(Long id) {
		Optional<Tag> optionalTag = tagRepository.findById(id);
		if (optionalTag.isPresent()) {
			return optionalTag.get();
		} else {
			throw new TagNotFoundException("Categorie kan niet worden gevonden");
		}
	}
	
	public Tag delete(Long id) {
		// TODO: Build additional check if category is in use and handle accordingly
		Optional<Tag> optionalTag = tagRepository.findById(id);
		if (optionalTag.isPresent()) {
			tagRepository.deleteById(id);
			return optionalTag.get();
		} else {
			throw new TagNotFoundException("Categorie met id " + id + " kan niet worden gevonden");
		}
	}
}
