package kwyk.exceptions;

public class HeatSettingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5611343122571026253L;

	public HeatSettingException(String message) {
		super(message);
	}

	public HeatSettingException(Throwable cause) {
		super(cause);
	}

	public HeatSettingException(String message, Throwable cause) {
		super(message, cause);
	}

	public HeatSettingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
