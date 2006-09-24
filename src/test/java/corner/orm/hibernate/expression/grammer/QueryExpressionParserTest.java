package corner.orm.hibernate.expression.grammer;

import java.io.StringReader;

import junit.framework.TestCase;

import org.testng.annotations.Test;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import corner.orm.hibernate.expression.CreateCriterionCallback;
@Test

public class QueryExpressionParserTest extends TestCase {

	public void testExpression() throws RecognitionException, TokenStreamException {
		assertEquals(" :20060501,~:20060902,",parseExpression("20060501 ~ 20060902"));
	}
	public void testUExpression() throws RecognitionException, TokenStreamException {
		assertEquals(" :20060501~20060902,",parseExpression("20060501~20060902"));
	}
	private String parseExpression(String in) throws RecognitionException, TokenStreamException{
		String str=in;
		final QueryExpressionLexer lexer =  new QueryExpressionLexer(new StringReader(str));
        final QueryExpressionParser parser =  new QueryExpressionParser(lexer);
        final StringBuffer sb=new StringBuffer();
        parser.setCreateCriterionCallback(new CreateCriterionCallback(){
        public void doCreateCriterion(String expression,String value){
        	sb.append(expression+":"+value+",");
        	
        } 
        });
        parser.expression();
        return sb.toString();

	}

}
