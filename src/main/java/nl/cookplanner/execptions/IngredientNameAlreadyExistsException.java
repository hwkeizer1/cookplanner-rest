package nl.cookplanner.execptions;

public class IngredientNameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IngredientNameAlreadyExistsException(String message) {
		super(message);
	}
}
