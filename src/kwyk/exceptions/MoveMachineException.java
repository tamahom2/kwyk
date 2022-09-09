package kwyk.exceptions;

public class MoveMachineException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7009226127670806191L;

	public MoveMachineException(String message) {
		super(message);
	}

	public MoveMachineException(Throwable cause) {
		super(cause);
	}

	public MoveMachineException(String message, Throwable cause) {
		super(message, cause);
	}

	public MoveMachineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
