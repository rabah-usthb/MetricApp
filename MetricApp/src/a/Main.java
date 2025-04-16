package a;

import application.BackEnd.RegularExpression;

public class Main {

	public static void main(String[] args) {
		 String subimp = "(\\w+|\\w+(<\\w+>)?)";
		String s ="FieldElement<BigFraction>";
		System.out.println(s.matches(subimp));
		String exp = "public class BigFraction extends Number implements FieldElement<BigFraction>, Comparable<BigFraction>, Serializable {";
		System.out.println(RegularExpression.IsClass(exp));
	}

}
