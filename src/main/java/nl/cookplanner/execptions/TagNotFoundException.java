package nl.cookplanner.execptions;

public class TagNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TagNotFoundException(String message) {
		super(message);
	}
}
