package corner.orm.hibernate.expression.grammer;

import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import corner.orm.hibernate.expression.CreateCriterionCallback;
import junit.framework.TestCase;

public class QueryExpressionParserTest extends TestCase {

	public void testExpression() throws RecognitionException, TokenStreamException {
		String str="20060501 ~ 20060902";
		final QueryExpressionLexer lexer =  new QueryExpressionLexer(new StringReader(str));
        final QueryExpressionParser parser =  new QueryExpressionParser(lexer);
        final StringBuffer sb=new StringBuffer();
        parser.setCreateCriterionCallback(new CreateCriterionCallback(){
        public void doCreateCriterion(String expression,String value){
        	sb.append(expression+":"+value+"\n");
        	
        } 
        });
        parser.expression();
        assertEquals(" :20060501,~:20060902,",sb.toString());
	}

}
