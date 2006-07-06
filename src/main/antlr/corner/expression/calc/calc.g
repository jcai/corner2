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
        buildAST=true;
}
tokens{
	DOT;
	NUM_DOUBLE;
	NUM_FLOAT;
	NUM_LONG;
}

expr:   mexpr ((PLUS^|MINUS^) mexpr)*
    ;

mexpr
    :   atom ((STAR^|DIV^) atom)*
    ;

atom:   NUM_INT|NUM_DOUBLE|NUM_LONG|NUM_FLOAT
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
    k=2; // needed for newline junk
    charVocabulary='\u0000'..'\u007F'; // allow ascii
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

//--- From the Java example grammar ---
// a numeric literal
NUM_INT
	{boolean isDecimal=false; Token t=null;}
	:   '.' {_ttype = DOT;}
			(	('0'..'9')+ (EXPONENT)? (f1:FLOAT_SUFFIX {t=f1;})?
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
	|	(	'0' {isDecimal = true;} // special case for just '0'
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
				if (t != null && t.getText().toUpperCase() .indexOf('F') >= 0)
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

// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
	:	('0'..'9'|'a'..'f')
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
    importVocab=ExprParser;
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
    ;

