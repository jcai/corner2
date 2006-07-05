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
		String str="(1+2)*45";
		ExprLexer lexer = new ExprLexer(new StringReader(str));
        ExprParser parser = new ExprParser(lexer);
        parser.expr();
        AST t = parser.getAST();
        System.out.println(t.toStringTree());
        ExprTreeParser treeParser = new ExprTreeParser();
        int x = treeParser.expr(t);
        System.out.println(x);
	}
}
