package a;

import application.BackEnd.RegularExpression;

public class Main {

	public static void main(String[] args) {
		String MethodPattern = "("+RegularExpression.MethodModifierPattern+")("+RegularExpression.TypeParameterGen+")("+RegularExpression.ReturnType+")\\s*\\w+\\s*\\([^\n]*\\)\\s*("+RegularExpression.ThrowsPattern+")(("+RegularExpression.CurlyBraces+")|\\s*;\\s*)";
		   
		// String subimp = "(\\w+|\\w+(<\\w+>)?)";
		String s ="public double taylor(final double ... delta) throws MathArithmeticException {";
		String re = "(\\w+|\\w+\\s*\\<[^\n]+\\>)";
		String se = "Class<? extends FieldElement<DerivativeStructure>>";
		System.out.println(RegularExpression.fetchExtends(s));
		System.out.println(s.matches(MethodPattern));
	//	String exp = "public class BigFraction extends Number implements FieldElement<BigFraction>, Comparable<BigFraction>, Serializable {";
		//System.out.println(RegularExpression.IsClass(exp));
	}

}
