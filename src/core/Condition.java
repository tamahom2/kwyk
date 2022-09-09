package core;

import java.util.NoSuchElementException;

import core.operations.collections.If;
import kwyk.Material;
import kwyk.Matter;
import kwyk.exceptions.ExpressionException;
import machines.Furnace;
import machines.HeatMachine;
import machines.Machine;

/**
 *
 * Cette classe repr�sente une condition (un bool�en) et est utilis�e dans la
 * construction d'un bloc If (voir {@link If}).
 *
 * @author Skander Jeddi
 *
 */
public class Condition {
	protected enum Operator {
		LESS, GREATER, LESSE, GREATERE, EQUAL, UNEQUAL;
		
		public static Operator getOperator(String string) {
			switch(string) {
			case "==" :
					return Operator.EQUAL;
			case "!=" :
					return Operator.UNEQUAL;
			case ">=":
				return Operator.GREATERE;
			case "<=":
				return Operator.LESSE;
			case ">":
				return Operator.GREATER;
			case "<":
				return Operator.LESS;
			}
			return null;
		}
	}
	protected enum LogicGate {
		AND, OR, NOT, XOR;
		
		public static LogicGate getLogicGate(String string) {
			switch(string) {
			case "&&" :
					return LogicGate.AND;
			case "||" :
					return LogicGate.OR;
			case "^":
				return LogicGate.XOR;
			case "!":
				return LogicGate.NOT;
			}
			return null;
		}
	}
	protected enum VariableType {
		KWYK, CONSTANT, HEAT, TOPMATTER, NBRMAT, TOTALSIZE;
	}

	public Machine m;
	protected String[] args;
	protected TinyBool root;
	
//	public static void main(String[] args)
//	{
//		Condition cond;
//		try {
//			String condtostr;
//			condtostr = "( TOP == ROUGE ) && ( HEAT > -2 )";
//			cond = new Condition(new Furnace(), condtostr);
//			System.out.println(cond.toString() + "\n" + cond.m.toString());
//			System.out.println(cond.check());
//		} catch (ExpressionException e) {
//			System.out.println(e.getMessage());
//		}
//	}
	
	public Condition (Machine m, String format) throws ExpressionException {
		this.m = m;
		args = format.split(" ");
		if (args.length <= 2) {
			throw new ExpressionException("Trop peu d'arguments");
		}
		root = new TinyBool(0, args.length);
	}
	
	public boolean check() throws ExpressionException {
		return root.check();
	}
	
	public String toString() {
		String s = "";
		for (String f : args)
			s += f;
		return (s);
	}
	
	private class TinyBool {
		// In case TinyBool is made of other Tiny Bool
		private LogicGate link;
		private TinyBool one;
		private TinyBool two;
		
		// In Case TinyBool is the end of the chain
		private Operator signe;
		private VariableType vt;
		private VariableType wt;
		private String var;
		private String war;
		private double dcons = 0;
		private Matter mcons = null;
		private double dcons2 = 0;
		private Matter mcons2 = null;
		
		public TinyBool(VariableType vvt, VariableType wwt, String var, String war, Operator sig) {
			vt = vvt;
			wt = wwt;
			this.var = var;
			this.war = war;
			signe = sig;
		}
		
		public TinyBool(int start, int end) throws ExpressionException {
			this(start, end, null);
		}

		public TinyBool(int start, int end, TinyBool o) throws ExpressionException {
			this.one = o;
			int i = start;
			while(i < end) {
				switch(args[i]) {
				case "(" :
					if (checkSurrondParenthese(start, end)) {
						start++;
						end--;
					} else {
						i = addTiny(i, end);
					}
					break;
				case "&&", "||", "^" :
					if (one == null) {
						if (vt != null && signe != null && wt != null) {
							one = new TinyBool(vt, wt, var, war, signe);
							vt = null; wt = null; var = null; war = null; signe = null;
						}
						else {
							throw new ExpressionException(args[i] + " logic operator not preceded by logic expression.");
						}
					}
					if (link == null) {
						link = LogicGate.getLogicGate(args[i]);
					}
					break;
				case "==", "!=", "<", "<=", ">=", ">" :
					if (vt == null)
						throw new ExpressionException(args[i] + " operator preceded by nothing.");
					if (signe != null)
						throw new ExpressionException(args[i] + " can't be preceded by another comparaison.");
					signe = Operator.getOperator(args[i]);
					break;
				case "!" :
					if (one != null)
						throw new ExpressionException("! logic operator can't be preceded by something.");
					if (link == null) {
						link = LogicGate.getLogicGate(args[i]);
					}
				case "HEAT" :
					if (!(m instanceof HeatMachine))
						throw new ExpressionException("Can't use local variable heat on non heating machine.");
					if (vt != null) {
						wt = VariableType.HEAT;
						war = "HEAT";
					} else {
						vt = VariableType.HEAT;
						var = "HEAT";
					}
					break;
				case "TOP" :
					if (vt != null) {
						wt = VariableType.TOPMATTER;war = "TOP";
					} else {
						vt = VariableType.TOPMATTER;var = "TOP";
					} break;
				case "NBR" :
					if (vt != null) {
						wt = VariableType.NBRMAT;war = "NBR";
					} else {
						vt = VariableType.NBRMAT;var = "NBR";
					} break;
				case "SIZE" :
					if (vt != null) {
						wt = VariableType.TOTALSIZE;war = "SIZE";
					} else {
						vt = VariableType.TOTALSIZE;var = "SIZE";
					} break;
				default:
					if (!checkNumeric(args[i]) && !checkMatter(args[i])) {
						if (vt != null) {
							wt = VariableType.KWYK;war = args[i];
						} else {
							vt = VariableType.KWYK;var = args[i];
						}
					}						
					break;
				}
				i++;
			}
			if (one != null && vt != null && signe != null && wt != null) {
				two = new TinyBool(vt, wt, var, war, signe);
				vt = null; wt = null; var = null; war = null; signe = null;
			}
		}
		
		private boolean checkMatter(String string) {
			Matter mat = null;
			switch (string) {
			case "Rouge", "ROUGE" :
				mat = Matter.ROUGE;
				break;
			case "Jaune", "JAUNE" :
				mat = Matter.JAUNE;
				break;
			case "Bleu", "BLEU" :
				mat = Matter.BLEU;
				break;
			case "Argent", "ARGENT" :
				mat = Matter.ARGENT;
				break;
			}
			if (mat == null)
				return false;
			else {
				if (vt != null) {
					wt = VariableType.CONSTANT;war = string;
					mcons2 = mat;
				} else {
					vt = VariableType.CONSTANT;var = string;
					mcons = mat;
				}
				return true;
			}
		}

		private boolean checkSurrondParenthese(int start, int end) {
			int i = start + 1;
			int depth = 0;
			while (++i < end) {
				if (args[i].equals("("))
					depth++;
				if (args[i].equals(")")) {
					if (depth == 0) {
						if (i == end - 1)
							return true;
						else
							return false;
					} else {
						depth--;
					}
				}
			}
			return false;
		}
		
		private boolean checkNumeric(String s) {
			try {
				double d = Double.valueOf(s);
				if (vt != null) {
					wt = VariableType.CONSTANT;war = s;
					dcons2 = d;
				} else {
					vt = VariableType.CONSTANT;var = s;
					dcons = d;
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		private int addTiny(int start, int end) throws ExpressionException {
			int i = start + 1;
			int depth = 0;
			while (++i < end) {
				if (args[i].equals("("))
					depth++;
				if (args[i].equals(")")) {
					if (depth == 0) {
						if (this.one == null) {
							this.one = new TinyBool(start + 1, i);return i;}
						else if (two == null) {
							if (link == null)
								throw new ExpressionException("Two conditions not separated by logic gates.");
							two = new TinyBool(start + 1, i);return i;}
						else {
							two = new TinyBool(start - 1, i, two);return i;}
					} else {
						depth--;
					}
				}
			}
			throw new ExpressionException("Parentheses empty or not closed");
		}
		
		public boolean check() throws ExpressionException {
			if (link != null) //Si TinyBool est faite de plusieurs conditions
				return checklink();
			//Si TinyBool renvoie directement un boolean
			boolean numerictest = true;
			double dvar = 0;
			Matter mvar = null;
			switch (vt) {
			case CONSTANT:
				if (mcons == null)
					dvar = dcons;
				else {
					mvar = mcons;
					numerictest = false;
				}
				break;
			case HEAT:
				if (m instanceof HeatMachine)
					dvar = ((HeatMachine)(m)).getHeat();
				else
					return false;
				break;
			case KWYK:
				Object res = Kwyk.engine().get(var);
				if (res == null)
					throw new ExpressionException("Variable " + var + " undeclared.");
				if (res instanceof Matter) {
					mvar = (Matter) res;
					numerictest = false;
				} else {
					dvar = (double) res;
				}
				break;
			case NBRMAT:
				dvar = m.getListMaterial().size();
				break;
			case TOPMATTER:
				try {
					mvar = m.getTopMaterial().name;
					numerictest = false;
				} catch (NoSuchElementException e) {
					throw new ExpressionException("Not enough materials to search for Matter.");
				}
				break;
			case TOTALSIZE:
				dvar = m.getStock_size();
				break;
			default:
				break;
			}
			Matter mwar = null;
			double dwar = 0;
			// Finding out what is the second Variable
			switch (wt) {
			case CONSTANT:
				if (mcons2 == null) {
					dwar = dcons2;
				} else {
					mwar = mcons2;
				}
				break;
			case HEAT:
				if (m instanceof HeatMachine)
					dwar = ((HeatMachine)(m)).getHeat();
				else
					return false;
				break;
			case KWYK:
				Object res = Kwyk.engine().get(var);
				if (res instanceof Matter) {
					mwar = (Matter) res;
				} else {
					dwar = (double) res;
				}
				break;
			case NBRMAT:
				dwar = m.getListMaterial().size();
				break;
			case TOPMATTER:
				mwar = m.getTopMaterial().name;
				break;
			case TOTALSIZE:
				dwar = m.getStock_size();
				break;
			default:
				break;
			}
			if (mwar == null && !numerictest)
				throw new ExpressionException("Trying to compare numerical variable " + war + " with " + var);
			if (mwar != null && numerictest)
				throw new ExpressionException("Trying to compare numerical variable " + var + " with " + war);
			// Finally testing
			if (!numerictest) {
				if (signe == Operator.EQUAL)
					return (mvar == mwar);
				else if (signe == Operator.UNEQUAL)
					return (mvar != mwar);
			} else {
				switch (signe) {
				case EQUAL:
					return (dvar == dwar);
				case GREATER:
					return (dvar > dwar);
				case GREATERE:
					return (dvar >= dwar);
				case LESS:
					return (dvar < dwar);
				case LESSE:
					return (dvar <= dwar);
				case UNEQUAL:
					return (dvar != dwar);
				default:
					return false;
				}
			}
				
			return false;
		}
		
		public boolean checklink() throws ExpressionException {
			switch (link) {
			case AND:
				return (one.check() && two.check());
			case NOT:
				return (!one.check());
			case OR:
				return (one.check() || two.check());
			case XOR:
				return (one.check() ^ two.check());
			default:
				return false;
			}
		}
	}
}
