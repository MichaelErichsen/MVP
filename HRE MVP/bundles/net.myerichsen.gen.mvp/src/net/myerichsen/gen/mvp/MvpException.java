package net.myerichsen.gen.mvp;

/**
 * Application specific exceptions
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 15. sep. 2018
 *
 */
public class MvpException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param message The exception message
	 */
	public MvpException(String message) {
		super(message);
	}

}
