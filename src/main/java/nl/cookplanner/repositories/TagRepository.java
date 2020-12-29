package nl.cookplanner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.cookplanner.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

}
