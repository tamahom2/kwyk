package kwyk.exceptions;

public class ArithmeticExpressionException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2807151221228479641L;

	public ArithmeticExpressionException(String message) {
		super(message);
	}

	public ArithmeticExpressionException(Throwable cause) {
		super(cause);
	}

	public ArithmeticExpressionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArithmeticExpressionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
