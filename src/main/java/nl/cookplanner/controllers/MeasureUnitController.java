package nl.cookplanner.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.cookplanner.domain.MeasureUnit;
import nl.cookplanner.execptions.ErrorResponse;
import nl.cookplanner.execptions.MeasureUnitAlreadyExistsException;
import nl.cookplanner.execptions.MeasureUnitNotFoundException;
import nl.cookplanner.services.MeasureUnitService;

@RestController
@RequestMapping("measure-units")
@CrossOrigin(origins = "http://localhost:4200")
public class MeasureUnitController {

	private final MeasureUnitService measureUnitService;

	public MeasureUnitController(MeasureUnitService measureUnitService) {
		this.measureUnitService = measureUnitService;
	}
	
	@GetMapping()
	public ResponseEntity<List<MeasureUnit>> findAll() {
		return ResponseEntity.ok(measureUnitService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MeasureUnit> findById(@PathVariable Long id) {
		return ResponseEntity.ok(measureUnitService.findById(id));
	}
	
	@PostMapping()
	public ResponseEntity<MeasureUnit> create(@RequestBody MeasureUnit measureUnit) {
		return ResponseEntity.ok(measureUnitService.save(measureUnit));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<MeasureUnit> update(@RequestBody MeasureUnit measureUnit, @PathVariable Long id) {
		return ResponseEntity.ok(measureUnitService.update(measureUnit, id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MeasureUnit> delete(@PathVariable Long id) {
		return ResponseEntity.ok(measureUnitService.delete(id));
	}
	
	@ExceptionHandler({MeasureUnitNotFoundException.class})
    public ResponseEntity<ErrorResponse> measureUnitNotFound(MeasureUnitNotFoundException ex){

        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage(), 404) , HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler({MeasureUnitAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> measureUnitNotFound(MeasureUnitAlreadyExistsException ex){

        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage(), 400) , HttpStatus.BAD_REQUEST);
    }
	
}
