//==============================================================================
//file :        $Id$
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision: 1444 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================
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

		String str="asdf and &okay ";
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
	caseSensitive = true;
}
protected
LETTER
    :   '\u0024' |
        '\u0041'..'\u005a' |
        '\u005f' |
        '\u0061'..'\u007a' |
        '\u00c0'..'\u00d6' |
        '\u00d8'..'\u00f6' |
        '\u00f8'..'\u00ff' |
        '\u0100'..'\u1fff' |
        '\u3040'..'\u318f' |
        '\u3300'..'\u337f' |
        '\u3400'..'\u3d2d' |
        '\u4e00'..'\u9fff' |
        '\uf900'..'\ufaff'
    ;
WORD:(LETTER|'\u0021'..'\u007e')+;
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