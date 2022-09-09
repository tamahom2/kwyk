package core.operations;

import java.util.HashMap;
import java.util.Map;

import core.Kwyk;
import core.Operation;
import core.ReturnValue;
import jdk.jshell.JShell;

/**
 *
 * Cette classe permet de faire des évaluations arithmétiques dynamiques basée
 * sur JShell (syntaxe JS).
 *
 * @author Skander Jeddi
 *
 */

public class Eval implements Operation<Double> {
	private static final long serialVersionUID = -6982933541811599520L;

	public static final Map<String, String> lowercaseOpsToJSMappings = new HashMap<String, String>();

	static {
		Eval.lowercaseOpsToJSMappings.put("cos", "Math.cos");
		Eval.lowercaseOpsToJSMappings.put("sin", "Math.sin");
		Eval.lowercaseOpsToJSMappings.put("exp", "Math.exp");
		Eval.lowercaseOpsToJSMappings.put("ln", "Math.log");
		Eval.lowercaseOpsToJSMappings.put("log10", "Math.log10");
		Eval.lowercaseOpsToJSMappings.put("tan", "Math.tan");
		Eval.lowercaseOpsToJSMappings.put("pow", "Math.pow");
		Eval.lowercaseOpsToJSMappings.put("E", "Math.E");
		Eval.lowercaseOpsToJSMappings.put("PI", "Math.PI");
		// TODO: add more
	}

	private String content, result;

	/**
	 * Evaluera le résultat de l'opération précedente.
	 */
	public Eval() {
		this(null);
	}

	/**
	 * Evaluera le contenu de <i>content</i>
	 *
	 * @param content
	 */
	public Eval(String content) {
		this.content = content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public ReturnValue<Double> execute(final ReturnValue<?>... returnValues) {
		try {
			JShell js = JShell.create();
			js.onSnippetEvent(snip -> {
				if (snip.status() == jdk.jshell.Snippet.Status.VALID) {
					this.result = snip.value();
				}
			});
			if (this.content == null) {
				this.content = (String) returnValues[0].value();
			}
			char[] chars = this.content.toCharArray();
			String tpc = new String(this.content);
			int i = 0;
			while (tpc.contains("{") && tpc.contains("}")) {
				while (i < this.content.length()) {
					if (chars[i] == '{') {
						int length = 0;
						for (int k = i + 1; k < this.content.length(); k++) {
							if ((chars[k] == '}')) {
								break;
							}
							length += 1;
						}
						String var = this.content.substring(i + 1, i + length + 1);
						tpc = tpc.substring(0, i) + Kwyk.engine().getNumeric(var)
								+ tpc.substring(i + length + 2, tpc.length());
						this.content = tpc;
						i += length;
						chars = tpc.toCharArray();
					}
					i++;
				}
			}
			for (String key : Eval.lowercaseOpsToJSMappings.keySet()) {
				this.content = this.content.replaceAll(key, Eval.lowercaseOpsToJSMappings.get(key));
			}
			js.eval(this.content);
			return new ReturnValue<Double>(Double.valueOf(this.result));
		} catch (Exception exception) {
			System.err.println(
					"eval() function failed for input \"" + this.content + "\", sending a null ReturnValue...");
			return null;
		}
	}

	@Override
	public String description() {
		if (this.content != null)
			return this.content;
		else
			return "Evaluate last value";
	}
}
