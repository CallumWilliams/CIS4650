/*
  Created By: Fei Song
  File Name: tiny.flex
  To Build: jflex tiny.flex

  and then after the parser is created
    javac Lexer.java
*/

/* --------------------------Usercode Section------------------------ */

import java_cup.runtime.*;

%%

/* -----------------Options and Declarations Section----------------- */

/*
   The name of the class JFlex will create will be Lexer.
   Will write the code to the file Lexer.java.
*/
%class Lexer

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column

/*
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup

/*
  Declarations

  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.
*/
%{
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }

    Boolean inComment = false;
%}


/*
  Macro Declarations

  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.
*/

/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n

/* White space is a line terminator, space, tab, or form feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]

/* A literal integer is is a number beginning with a number between
   one and nine followed by zero or more numbers between zero and nine
   or just a zero.  */
digit = [0-9]
number = {digit}+

/* A identifier integer is a word beginning a letter between A and
   Z, a and z, or an underscore followed by zero or more letters
   between A and Z, a and z, zero and nine, or an underscore. */
letter = [a-zA-Z]
identifier = {letter}+

%state CMT

%%
/* ------------------------Lexical Rules Section---------------------- */

/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */

<YYINITIAL> "if"          		{ if(!inComment) return symbol(sym.IF);     }
<YYINITIAL> "else"        		{ if(!inComment) return symbol(sym.ELSE);   }
<YYINITIAL> "int"			 	{ if(!inComment) return symbol(sym.INT);    }
<YYINITIAL> "return"			{ if(!inComment) return symbol(sym.RETURN); }
<YYINITIAL> "void"   		 	{ if(!inComment) return symbol(sym.VOID);   }
<YYINITIAL> "while"				{ if(!inComment) return symbol(sym.WHILE);  }
<YYINITIAL> "<="			    { if(!inComment) return symbol(sym.LTEQ);   }
<YYINITIAL> ">="			    { if(!inComment) return symbol(sym.GTEQ);   }
<YYINITIAL> "=="			    { if(!inComment) return symbol(sym.EQLTY);  }
<YYINITIAL> "!="			    { if(!inComment) return symbol(sym.NTEQ);   }
<YYINITIAL> ","				    { if(!inComment) return symbol(sym.COMMA);  }
<YYINITIAL> "["				    { if(!inComment) return symbol(sym.LSQR);   }
<YYINITIAL> "]"			     	{ if(!inComment) return symbol(sym.RSQR);   }
<YYINITIAL> "{"			     	{ if(!inComment) return symbol(sym.LCRL);   }
<YYINITIAL> "}"			     	{ if(!inComment) return symbol(sym.RCRL);   }

<YYINITIAL> "/*"			    { yybegin(CMT); }
<YYINITIAL> "("                	{ if(!inComment) return symbol(sym.LPAREN); }
<YYINITIAL> ")"                	{ if(!inComment) return symbol(sym.RPAREN); }
<YYINITIAL> "="                	{ if(!inComment) return symbol(sym.EQ);     }
<YYINITIAL> "<"                	{ if(!inComment) return symbol(sym.LT);     }
<YYINITIAL> ">"                	{ if(!inComment) return symbol(sym.GT);     }
<YYINITIAL> "+"                	{ if(!inComment) return symbol(sym.PLUS);   }
<YYINITIAL> "-"                	{ if(!inComment) return symbol(sym.MINUS);  }
<YYINITIAL> "*"                	{ if(!inComment) return symbol(sym.TIMES);  }
<YYINITIAL> "/"                	{ if(!inComment) return symbol(sym.OVER);   }
<YYINITIAL> ";"                	{ if(!inComment) return symbol(sym.SEMI);   }
<YYINITIAL> {number}           	{ if(!inComment) return symbol(sym.NUM, Integer.parseInt(yytext())); }
<YYINITIAL> {identifier}       	{ if(!inComment) return symbol(sym.ID, yytext());  }
<YYINITIAL> {WhiteSpace}*      	{ /* skip whitespace */ }
<YYINITIAL> .                  	{ return symbol(sym.ERROR); }

<CMT> "*/"			   			{ yybegin(YYINITIAL); }
<CMT> {WhiteSpace}*				{ /* ignore */ }
<CMT> .            				{ /* ignore */ }
