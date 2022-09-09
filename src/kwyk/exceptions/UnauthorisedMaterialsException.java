package kwyk.exceptions;

public class UnauthorisedMaterialsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2813288308218525142L;

	public UnauthorisedMaterialsException(String message) {
		super(message);
	}

	public UnauthorisedMaterialsException(Throwable cause) {
		super(cause);
	}

	public UnauthorisedMaterialsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorisedMaterialsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
