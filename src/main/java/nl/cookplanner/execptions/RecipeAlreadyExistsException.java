package nl.cookplanner.execptions;

public class RecipeAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecipeAlreadyExistsException(String message) {
		super(message);
	}
}
