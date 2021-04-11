package nl.cookplanner.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.cookplanner.domain.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	public Optional<Recipe> findByName(String name);
}
