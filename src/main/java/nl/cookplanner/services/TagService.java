package nl.cookplanner.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import nl.cookplanner.domain.Tag;
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
	
	public void save(Tag tag) {
		tagRepository.save(tag);
	}
	
	public Optional<Tag> get(Long id) {
		return tagRepository.findById(id);
	}
	
	public void delete(Long id) {
		tagRepository.deleteById(id);
	}
}
