package nl.cookplanner.execptions;

public class TagAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TagAlreadyExistsException(String message) {
		super(message);
	}
}
