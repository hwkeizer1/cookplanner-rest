package nl.cookplanner.execptions;

public class MeasureUnitNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MeasureUnitNotFoundException(String message) {
		super(message);
	}
}
