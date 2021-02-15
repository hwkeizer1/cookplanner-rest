package nl.cookplanner.execptions;

public class MeasureUnitAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MeasureUnitAlreadyExistsException(String message) {
		super(message);
	}
}
