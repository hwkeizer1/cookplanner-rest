package nl.cookplanner.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.cookplanner.domain.IngredientName;

@Repository
public interface IngredientNameRepository extends JpaRepository<IngredientName, Long>{

	public Optional<IngredientName> findByName(String name);
}
