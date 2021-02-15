package nl.cookplanner.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.cookplanner.domain.MeasureUnit;
import nl.cookplanner.execptions.MeasureUnitAlreadyExistsException;
import nl.cookplanner.execptions.MeasureUnitNotFoundException;
import nl.cookplanner.repositories.MeasureUnitRepository;

@Service
@Transactional
public class MeasureUnitService {

	private final MeasureUnitRepository measureUnitRepository;

	public MeasureUnitService(MeasureUnitRepository measureUnitRepository) {
		this.measureUnitRepository = measureUnitRepository;
	}
	
	public List<MeasureUnit> findAll() {
		return measureUnitRepository.findAll();
	}
	
	public MeasureUnit save(MeasureUnit measureUnit) {
		if (measureUnitRepository.findByName(measureUnit.getName()).isPresent()) {	
			throw new MeasureUnitAlreadyExistsException("Maateenheid " + measureUnit.getName() + " bestaat al");
		}
		return measureUnitRepository.save(measureUnit);
	}
	
	public MeasureUnit update(MeasureUnit measureUnit, Long id) {
		Optional<MeasureUnit> optionalMeasureUnit = measureUnitRepository.findByName(measureUnit.getName()); 
		if (optionalMeasureUnit.isPresent() && id != optionalMeasureUnit.get().getId()) {
			throw new MeasureUnitAlreadyExistsException("Maateenheid " + measureUnit.getName() + " bestaat al");
		}
		measureUnit.setId(id);
		return measureUnitRepository.save(measureUnit);
	}
	
	public MeasureUnit findById(Long id) {
		Optional<MeasureUnit> optionalMeasureUnit = measureUnitRepository.findById(id);
		if (optionalMeasureUnit.isPresent()) {
			return optionalMeasureUnit.get();
		} else {
			throw new MeasureUnitNotFoundException("Maateenheid kan niet worden gevonden");
		}
	}
	
	public MeasureUnit delete(Long id) {
		// TODO: Build additional check if measureUnit is in use and handle accordingly
		Optional<MeasureUnit> optionalMeasureUnit = measureUnitRepository.findById(id);
		if (optionalMeasureUnit.isPresent()) {
			measureUnitRepository.deleteById(id);
			return optionalMeasureUnit.get();
		} else {
			throw new MeasureUnitNotFoundException("Maateenheid met id " + id + " kan niet worden gevonden");
		}
	}
}
