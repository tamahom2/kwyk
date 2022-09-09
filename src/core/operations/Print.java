package core.operations;

import core.Kwyk;
import core.Operation;
import core.ReturnValue;

/**
 *
 * @author Skander Jeddi
 *
 */

public class Print implements Operation<String> {
	private static final long serialVersionUID = -1288892268450871032L;

	private String content;

	public Print() {
		this("");
	}

	public Print(String content) {
		this.content = content;
	}

	@Override
	public ReturnValue<String> execute(final ReturnValue<?>... returnValues) {
		if (this.content.isEmpty()) {
			try {
				final String retValStr = String.valueOf(returnValues[0].value());
				System.out.print(retValStr);
				return new ReturnValue<String>(retValStr);
			} catch (Exception exception) {
				System.err.println("An error occurred and print statement was aborted: " + exception);
				return null;
			}
		} else {
			//Print("Hello {w}") avec w = "World!" 
			char[] contentChars = this.content.toCharArray();
			String copy = new String(this.content);
			int i = 0;
			while (copy.contains("{") && copy.contains("}")) {
				while (i < this.content.length()) {
					if (contentChars[i] == '{') {
						int length = 0;
						for (int k = i + 1; k < this.content.length(); k++) {
							if ((contentChars[k] == '}')) {
								break;
							} else {
								length += 1;
							}
						}
						String var = this.content.substring(i + 1, i + length + 1);
						String toPrint = "";
						if (Kwyk.engine().getNumeric(var) != Double.NaN) {
							toPrint = String.valueOf(Kwyk.engine().getNumeric(var));
						} else if (Kwyk.engine().getTextual(var) != null) {
							toPrint = Kwyk.engine().getTextual(var);
						}
						copy = copy.substring(0, i) + toPrint + copy.substring(i + length + 2, copy.length());
						this.content = copy;
						i += length;
						contentChars = copy.toCharArray();
					}
					i++;
				}
			}
			System.out.print(this.content);
			return new ReturnValue<String>(this.content);
		}
	}

	@Override
	public String description() {
		return "Print(\"" + this.content + "\")";
	}
}
