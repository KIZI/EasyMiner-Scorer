package cz.jkuchar.easyminerscorer.utlis.transformation;

import java.util.function.Function;

/**
 * Discretize Bin transformation 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class DiscretizeBin implements Function<String, String> {

	private String closure;
	private double leftMargin;
	private double rightMargin;
	private String binValue;

	public DiscretizeBin(String closure, String leftMargin, String rightMargin,
			String binValue) {
		super();
		this.closure = new String(closure);
		this.leftMargin = Double.valueOf(leftMargin);
		this.rightMargin = Double.valueOf(rightMargin);
		this.binValue = new String(binValue);
	}

	@Override
	public String apply(String value) {
		try {
			double nValue = Double.valueOf(value);
			if (closure.equals("closedClosed") && nValue >= leftMargin
					&& nValue <= rightMargin) {
				return binValue;
			}
			if (closure.equals("closedOpen") && nValue >= leftMargin
					&& nValue < rightMargin) {
				return binValue;
			}
			if (closure.equals("openClosed") && nValue > leftMargin
					&& nValue <= rightMargin) {
				return binValue;
			}
			if (closure.equals("openOpen") && nValue > leftMargin
					&& nValue < rightMargin) {
				return binValue;
			}
		} catch (NumberFormatException ex) {
			return null;
		}
		return null;
	}

}
