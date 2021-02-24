package nl.cookplanner.execptions;

public class IngredientNameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IngredientNameNotFoundException(String message) {
		super(message);
	}
}
