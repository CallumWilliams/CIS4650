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

%state PARSE

%%
/* ------------------------Lexical Rules Section---------------------- */

/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */

<PARSE> "if"          { if(!inComment) return symbol(sym.IF);     }
<PARSE> "else"        { if(!inComment) return symbol(sym.ELSE);   }
<PARSE> "int"			   { if(!inComment) return symbol(sym.INT);    }
<PARSE> "return"		   { if(!inComment) return symbol(sym.RETURN); }
<PARSE> "void"        { if(!inComment) return symbol(sym.VOID);   }
<PARSE> "while"			 { if(!inComment) return symbol(sym.WHILE);  }
<PARSE> "<="			     { if(!inComment) return symbol(sym.LTEQ);   }
<PARSE> ">="			     { if(!inComment) return symbol(sym.GTEQ);   }
<PARSE> "=="			     { if(!inComment) return symbol(sym.EQLTY);  }
<PARSE> "!="			     { if(!inComment) return symbol(sym.NTEQ);   }
<PARSE> ","			     { if(!inComment) return symbol(sym.COMMA); }
<PARSE> "["			     { if(!inComment) return symbol(sym.LSQR);   }
<PARSE> "]"			     { if(!inComment) return symbol(sym.RSQR);   }
<PARSE> "{"			     { if(!inComment) return symbol(sym.LCRL);   }
<PARSE> "}"			     { if(!inComment) return symbol(sym.RCRL);   }

<PARSE> "/*"			     { yybegin(COMMENT); }
<PARSE> "("                { if(!inComment) return symbol(sym.LPAREN); }
<PARSE> ")"                { if(!inComment) return symbol(sym.RPAREN); }
<PARSE> "="                { if(!inComment) return symbol(sym.EQ);     }
<PARSE> "<"                { if(!inComment) return symbol(sym.LT);     }
<PARSE> ">"                { if(!inComment) return symbol(sym.GT);     }
<PARSE> "+"                { if(!inComment) return symbol(sym.PLUS);   }
<PARSE> "-"                { if(!inComment) return symbol(sym.MINUS);  }
<PARSE> "*"                { if(!inComment) return symbol(sym.TIMES);  }
<PARSE> "/"                { if(!inComment) return symbol(sym.OVER);   }
<PARSE> ";"                { if(!inComment) return symbol(sym.SEMI);   }
<PARSE> {number}           { if(!inComment) return symbol(sym.NUM, yytext()); }
<PARSE> {identifier}       { if(!inComment) return symbol(sym.ID, yytext());  }
<PARSE> {WhiteSpace}*      { /* skip whitespace */ }
.                  { if(!inComment) return symbol(sym.ERROR); }

<COMMENT>"*/"			   { yybegin(PARSE); }
<COMMENT>.            {//ignore}
