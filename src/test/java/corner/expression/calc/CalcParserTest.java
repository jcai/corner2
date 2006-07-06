package corner.expression.calc;

import java.io.DataInputStream;
import java.io.StringReader;

import antlr.CommonAST;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import junit.framework.TestCase;

public class CalcParserTest extends TestCase {
	public void testExpr() throws RecognitionException, TokenStreamException{
		expr("1+1",2);
		expr("(10+23)*5",165);
		expr("(0x23+3)*0x42",2508);
		expr("(15.23+23.12)*12.2",467.87);
		expr("2.5*3.4+2.2+(15.23+23.12)*12.2",478.57);

	}
	/**
	 *
	 * @param str 欲求值的字符串。
	 * @param expected 期望值。
	 */
	private void expr(String str,double expected){
		ExprLexer lexer = new ExprLexer(new StringReader(str));
        ExprParser parser = new ExprParser(lexer);
        try {
			parser.expr();
		} catch (RecognitionException e) {
			fail(e.getMessage());
			fail(e.getMessage());
		} catch (TokenStreamException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
        AST t = parser.getAST();
        System.out.println(t.toStringTree());
        ExprTreeParser treeParser = new ExprTreeParser();
        double x;
		try {
			x = treeParser.expr(t);
			assertEquals(expected,x);
		} catch (RecognitionException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
}
