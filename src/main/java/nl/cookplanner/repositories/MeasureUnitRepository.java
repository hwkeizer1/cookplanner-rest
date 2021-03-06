package nl.cookplanner.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.cookplanner.domain.MeasureUnit;

@Repository
public interface MeasureUnitRepository extends JpaRepository<MeasureUnit, Long>{

	public Optional<MeasureUnit> findByName(String name);
}
