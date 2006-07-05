header{
package corner.expression.calc;
}
class ExprParser extends Parser;

options {
        buildAST=true;
}

expr:   mexpr ((PLUS^|MINUS^) mexpr)*
    ;

mexpr
    :   atom (STAR^ atom)*
    ;

atom:   INT
    |   LPAREN! expr RPAREN!
    ;

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
INT   : ('0'..'9')+ ;
WS    : ( ' '
        | '\r' '\n'
        | '\n'
        | '\t'
        )
        {$setType(Token.SKIP);}
      ;
class ExprTreeParser extends TreeParser;

options {
    importVocab=ExprParser;
}

expr returns [int r=0]
{ int a,b; }
    :   #(PLUS  a=expr b=expr)  {r = a+b;}
    |   #(MINUS a=expr b=expr)  {r = a-b;}
    |   #(STAR  a=expr b=expr)  {r = a*b;}
    |   i:INT                   {r = (int)Integer.parseInt(i.getText());}
    ;