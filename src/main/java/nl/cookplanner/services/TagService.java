package nl.cookplanner.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import nl.cookplanner.domain.Tag;
import nl.cookplanner.execptions.TagAlreadyExistsException;
import nl.cookplanner.repositories.TagRepository;

@Service
@Transactional
public class TagService {

	private final TagRepository tagRepository;

	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	public List<Tag> listAll() {
		return tagRepository.findAll();
	}
	
	public Tag save(Tag tag) {
		if (tagRepository.findByName(tag.getName()).isPresent()) {
			throw new TagAlreadyExistsException("Categorie " + tag.getName() + " bestaat al");
		}
		return tagRepository.save(tag);
	}
	
	public Optional<Tag> get(Long id) {
		return tagRepository.findById(id);
	}
	
	public void delete(Long id) {
		tagRepository.deleteById(id);
	}
}
