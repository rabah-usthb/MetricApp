package a;

import application.BackEnd.Comment;
import application.BackEnd.RegularExpression;

public class Main {

	public static void main(String[] args) {
		System.out.println("CJEJOFUEMBGOTHOGDVJSFHFFRMFMUTBJSEJFOTOSMIGEJOFJDNSRMPMFTTNEIGNUFTVPFPBTOJFVDGDUPV".length());
		   StringBuilder sb = new StringBuilder();
		 sb.append("public static int SUMMARY_LENGTH = 200;").append("\n");
	        sb.append("StringBuffer title = new StringBuffer(SUMMARY_LENGTH);").append("\n");
	        sb.append("StringBuffer summary = new StringBuffer(SUMMARY_LENGTH * 2);").append("\n");
	        sb.append("Properties metaTags = new Properties();").append("\n");
	        sb.append("String currentMetaTag = null;").append("\n");
	        sb.append("String currentMetaContent = null;").append("\n");
	        sb.append("int length = 0;").append("\n");
	        sb.append("boolean titleComplete = false;").append("\n");
	        sb.append("boolean inTitle = false;").append("\n");
	        sb.append("boolean inMetaTag = false;").append("\n");
	        sb.append("boolean inStyle = false;").append("\n");
	        sb.append("boolean afterTag = false;").append("\n");
	        sb.append("boolean afterSpace = false;").append("\n");
	        sb.append("String eol = System.getProperty();").append("\n");
	        sb.append("Reader pipeIn = null;").append("\n");
	        sb.append("Writer pipeOut;").append("\n");
	        sb.append("private MyPipedInputStream pipeInStream = null;").append("\n");
	        sb.append("private PipedOutputStream pipeOutStream = null;").append("\n");
	        sb.append("public MyPipedInputStream() {").append("\n");
	        sb.append("public MyPipedInputStream(PipedOutputStream src) throws IOException {").append("\n");
	        sb.append("public boolean full() throws IOException {").append("\n");
	        sb.append("public HTMLParser(File file) throws FileNotFoundException {").append("\n");
	        sb.append("public String getTitle() throws IOException, InterruptedException {").append("\n");
	        sb.append("public Properties getMetaTags() throws IOException, InterruptedException {").append("\n");
	        sb.append("public String getSummary() throws IOException, InterruptedException {").append("\n");
	        sb.append("public Reader getReader() throws IOException {").append("\n");
	        sb.append("void addToSummary(String text) {").append("\n");
	        sb.append("void addText(String text) throws IOException {").append("\n");
	        sb.append("void addMetaTag() {").append("\n");
	        sb.append("void addSpace() throws IOException {").append("\n");
	        sb.append("public final void HTMLDocument() throws ParseException, IOException {").append("\n");
	        sb.append("public final void Tag() throws ParseException, IOException {").append("\n");
	        sb.append("public final Token ArgValue() throws ParseException {").append("\n");
	        sb.append("public final Token Decl() throws ParseException {").append("\n");
	        sb.append("public final void CommentTag() throws ParseException {").append("\n");
	        sb.append("public final void ScriptTag() throws ParseException {").append("\n");
	        sb.append("private final boolean jj_2_1(int xla) {").append("\n");
	        sb.append("private final boolean jj_2_2(int xla) {").append("\n");
	        sb.append("private final boolean jj_3_1() {").append("\n");
	        sb.append("private final boolean jj_3_2() {").append("\n");
	        sb.append("public HTMLParserTokenManager token_source;").append("\n");
	        sb.append("SimpleCharStream jj_input_stream;").append("\n");
	        sb.append("public Token token, jj_nt;").append("\n");
	        sb.append("private int jj_ntk;").append("\n");
	        sb.append("private Token jj_scanpos, jj_lastpos;").append("\n");
	        sb.append("private int jj_la;").append("\n");
	        sb.append("public boolean lookingAhead = false;").append("\n");
	        sb.append("private boolean jj_semLA;").append("\n");
	        sb.append("private int jj_gen;").append("\n");
	        sb.append("private final int[] jj_la1 = new int[14];").append("\n");
	        sb.append("private static int[] jj_la1_0;").append("\n");
	       
	        sb.append("private static void jj_la1_0() {").append("\n");
	        sb.append("private final JJCalls[] jj_2_rtns = new JJCalls[2];").append("\n");
	        sb.append("private boolean jj_rescan = false;").append("\n");
	        sb.append("private int jj_gc = 0;").append("\n");
	        sb.append("public HTMLParser(java.io.InputStream stream) {").append("\n");
	        sb.append("public HTMLParser(java.io.InputStream stream, String encoding) {").append("\n");
	        sb.append("public void ReInit(java.io.InputStream stream) {").append("\n");
	        sb.append("public void ReInit(java.io.InputStream stream, String encoding) {").append("\n");
	        sb.append("public HTMLParser(java.io.Reader stream) {").append("\n");
	        sb.append("public void ReInit(java.io.Reader stream) {").append("\n");
	        sb.append("public HTMLParser(HTMLParserTokenManager tm) {").append("\n");
	        sb.append("public void ReInit(HTMLParserTokenManager tm) {").append("\n");
	        sb.append("private final Token jj_consume_token(int kind) throws ParseException {").append("\n");
	        sb.append("private final LookaheadSuccess jj_ls = new LookaheadSuccess();").append("\n");
	        sb.append("private final boolean jj_scan_token(int kind) {").append("\n");
	        sb.append("public final Token getNextToken() {").append("\n");
	        sb.append("public final Token getToken(int index) {").append("\n");
	        sb.append("private final int jj_ntk() {").append("\n");
	        sb.append("private java.util.Vector jj_expentries = new java.util.Vector();").append("\n");
	        sb.append("private int[] jj_expentry;").append("\n");
	        sb.append("private int jj_kind = -1;").append("\n");
	        sb.append("private int[] jj_lasttokens = new int[100];").append("\n");
	        sb.append("private int jj_endpos;").append("\n");
	        sb.append("private void jj_add_error_token(int kind, int pos) {").append("\n");
	        sb.append("public ParseException generateParseException() {").append("\n");
	        sb.append("public final void enable_tracing() {}").append("\n");
	        sb.append("public final void disable_tracing() {}").append("\n");
	        sb.append("private final void jj_rescan_token() {").append("\n");
	        sb.append("private final void jj_save(int index, int xla) {").append("\n");
	        sb.append("int gen;").append("\n");
	        sb.append("Token first;").append("\n");
	        sb.append("int arg;").append("\n");
	        sb.append("JJCalls next;").append("\n");

	        // 2) Split into lines
	        String[] lines = sb.toString().split("\\r?\\n");

	        // 3) Loop through each line
	        for (String line : lines) {
	            if(!RegularExpression.IsVariable(line) && !RegularExpression.IsMethodPrototype(line)) {
	            System.out.println(line);
	            }
	        }
		
	      System.out.println("jj_scanpos, jj_lastpos".matches(RegularExpression.objectNameVar));
		String MethodPattern = "("+RegularExpression.MethodModifierPattern+")("+RegularExpression.TypeParameterGen+")("+RegularExpression.ReturnType+")\\s*\\w+\\s*\\([^\n]*\\)\\s*("+RegularExpression.ThrowsPattern+")(("+RegularExpression.CurlyBraces+")|\\s*;\\s*)";
		
		System.out.println(RegularExpression.IsVariable("private java.util.Vector jj_expentries = new java.util.Vector();"));
		
		
		System.out.println(RegularExpression.IsThrow("throw new MissingRequiredPropertyException(key);"));
		
		System.out.println(Comment.NotFinishedComment("/**"));
	
		
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
