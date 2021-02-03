package nl.cookplanner.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.cookplanner.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	public Optional<Tag> findByName(String name);
}
