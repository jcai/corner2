//==============================================================================
//file :        CornerPropertiesPersister.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

header{
	
package corner.expression.calc;
}
/**
 * expression parser
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
class ExprParser extends Parser;

options {
    k=2;
	defaultErrorHandler = false;     // Don't generate parser error handlers
	buildAST = true;
	exportVocab =	Expr;
}
tokens{
	DOT;
}
imaginaryTokenDefinitions :
   SIGN_MINUS
   SIGN_PLUS
;

expr:    mexpr ((PLUS^|MINUS^) mexpr)*
    ;

mexpr
    :   signExpr ((STAR^|DIV^) atom)*
    ;
signExpr : (
         m:MINUS^ {#m.setType(SIGN_MINUS);}
         |	p:PLUS^  {#p.setType(SIGN_PLUS);}
         )? atom ;
atom:    NUM_INT|NUM_DOUBLE|NUM_LONG|NUM_FLOAT
    |   LPAREN! expr RPAREN!
    ;
/**
 *  expression lexer
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
class ExprLexer extends Lexer;

options {
    k=2;
	charVocabulary='\u0003'..'\u7FFE';
	exportVocab =	Expr;
}

LPAREN: '(' ;
RPAREN: ')' ;
PLUS  : '+' ;
MINUS : '-' ;
STAR  : '*' ;
DIV   : '/';

//INT   : ('0'..'9')+ ;
WS    : ( ' '
        | '\r' '\n'
        | '\n'
        | '\t'
        )
        {$setType(Token.SKIP);}
      ;

// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
	:	('0'..'9'|'a'..'f')
	;

//--- From the Java example grammar ---
// a numeric literal
NUM_INT
	{boolean isDecimal=false; Token t=null;}
	:'.' {_ttype = DOT;}
			(	 ('0'..'9')+ (EXPONENT)? (f1:FLOAT_SUFFIX {t=f1;})?
				{
					if (t != null && t.getText().toUpperCase().indexOf('F')>=0)
					{
						_ttype = NUM_FLOAT;
					}
					else
					{
						_ttype = NUM_DOUBLE; // assume double
					}
				}
			)?
	|(	'0' {isDecimal = true;} // special case for just '0'
			(	('x')
				(											// hex
					// the 'e'|'E' and float suffix stuff look
					// like hex digits, hence the (...)+ doesn't
					// know when to stop: ambig.  ANTLR resolves
					// it correctly by matching immediately.  It
					// is therefore ok to hush warning.
					options { warnWhenFollowAmbig=false; }
				:	HEX_DIGIT
				)+
			|	('0'..'7')+									// octal
			)?
		|	('1'..'9') ('0'..'9')*  {isDecimal=true;}		// non-zero decimal
		)
		(	('l') { _ttype = NUM_LONG; }

		// only check to see if it's a float if looks like decimal so far
		|	{isDecimal}?
			(   '.' ('0'..'9')* (EXPONENT)? (f2:FLOAT_SUFFIX {t=f2;})?
			|   EXPONENT (f3:FLOAT_SUFFIX {t=f3;})?
			|   f4:FLOAT_SUFFIX {t=f4;}
			)
			{
				if (t != null && t.getText().toUpperCase().indexOf('F') >= 0)
				{
					_ttype = NUM_FLOAT;
				}
				else
				{
					_ttype = NUM_DOUBLE; // assume double
				}
			}
		)?
	;



// a couple protected methods to assist in matching floating point numbers
protected
EXPONENT
	:	('e') ('+'|'-')? ('0'..'9')+
	;

protected
FLOAT_SUFFIX
	:	'f'|'d'
	;





/**
 * expression tree parser
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
class ExprTreeParser extends TreeParser;

options {
    importVocab=Expr;

}

expr returns [double r=0]
{ double a,b; }
    :   #(PLUS  a=expr b=expr)  {r = a+b;}
    |   #(MINUS a=expr b=expr)  {r = a-b;}
    |   #(STAR  a=expr b=expr)  {r = a*b;}
    |   #(DIV  a=expr b=expr)   {
    		if(b == 0){
				throw new ArithmeticException("by zero!");
    		}
    		r = a/b;
    		}
    |   i:NUM_INT                   {
				String str=	i.getText();
    			if(str.startsWith("0x")){
    				r=Integer.parseInt(str.substring(2),16);
    			}else{
					r = Integer.parseInt(str);
    			}
			}
    |	d:NUM_DOUBLE {r=Double.parseDouble(d.getText());}
    |	l:NUM_LONG	 {r=Long.parseLong(d.getText());}
    |	f:NUM_FLOAT	 {r=Float.parseFloat(d.getText());}
	| 	#(SIGN_MINUS a=expr)   { r=-1*a; }
	| 	#(SIGN_PLUS  a=expr)   { if(a<0)r=0-a; else r=a; }
    ;

