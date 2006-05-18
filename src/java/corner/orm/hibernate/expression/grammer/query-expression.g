header{
package corner.orm.hibernate.expression.grammer;
import java.io.StringReader;

import corner.orm.hibernate.expression.CreateCriterionCallback;
}
/**
 * parse query expression
 * 
 * 
 * @author Jun Tsai
 *
 */
class QueryExpressionParser extends Parser;
options{
	k=3; 
	
}
tokens{
	AND="and";
	OR="or";
}
{
	private CreateCriterionCallback callback;
	
	public final static void main(String[] args)throws RecognitionException, TokenStreamException{
		String str="asdf or fordsa and okay ";
		final QueryExpressionLexer lexer =  new QueryExpressionLexer(new StringReader(str));
        final QueryExpressionParser parser =  new QueryExpressionParser(lexer);
        parser.setCreateCriterionCallback(new CreateCriterionCallback(){
        public void doCreateCriterion(String expression,String value){
        	System.out.println("expression:"+expression+" value:"+value);
        } 
        });
        parser.expression();
        
	}
	public void setCreateCriterionCallback(CreateCriterionCallback callback) {
		this.callback=callback;
		
	}
	
}
expression:
	t:WORD{this.callback.doCreateCriterion(" ",t.getText());} (term)* EOF;
term:
	{final String expression;final String value;}
	(mA:AND {expression=mA.getText();}|mO:OR {expression=mO.getText();}) n:WORD{value=n.getText();}
	{this.callback.doCreateCriterion(expression,value);}
	;
class QueryExpressionLexer extends Lexer;
options { 
	k=2;
	charVocabulary='\u0000'..'\uFFFE';
	caseSensitive = false;
}
WORD:('\u4e00'..'\u9fa5'|'a'..'z'|'0'..'9'|'%')+;
/*BLANK:
	'' { $setType(QueryExpressionParserTokenTypes.AND);}
	;
	*/
WS     : 

    (' '
    |'\t' 
    |'-'
    | '\r' '\n' { newline(); } 
    | '\n'      { newline(); }
    ) 
    { $setType(Token.SKIP); } 
  ;