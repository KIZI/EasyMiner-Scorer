package cz.jkuchar.easyminerscorer.rules;

/**
 * Bad Rule format exception
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class BadRuleFormatException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BadRuleFormatException(String message) {
		super(message);
	}

}
