//Authors: Elizabeth Bauch and Danella Bunavi
package clueGame;

public class BadConfigFormatException extends Exception {
	String message;

	public BadConfigFormatException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
