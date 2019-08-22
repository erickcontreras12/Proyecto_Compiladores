package Classes;
import static Classes.Token.*;
%%
%class Lexer
%type Token
L = [a-zA-Z_]
D = [0-9]
BLANK = [ ,\t,\r,\n]
%{
public String lexeme;
%}
%%
{BLANK} {/*Ignore*/}
"--"(.)+ {/*Ignore*/}
"/*"([^]|\n)*"*/" {/*Ignore*/}
("0"|"1"|"NULL") {lexeme=yytext(); return BIT;}
"."{D}+(("E"|"e"|"E+"|"E-"|"e+"|"e-"){D}+)?  {lexeme=yytext(); return ERROR;}
{D}+"."{D}*(("E"|"e"|"E+"|"E-"|"e+"|"e-"){D}+)? {lexeme=yytext(); return FLOAT;}
("+"|"-")?{D}+ {lexeme=yytext(); return INT;}
"'"({L}|" ")+"'" {lexeme=yytext(); return STRING;}
(ADD)|(EXTERNAL) {lexeme=yytext(); return PALABRA_RESERVADA;}
{L}({L}|{D})* {lexeme=yytext(); return ID;}
"+" {return MAS;}
"-" {return MENOS;}
"*" {return MULT;}
"/" {return DIV;}
"%" {return RES;}
"<" {return MENOR;}
"<=" {return MENOR_O_IGUAL;}
">" {return MAYOR;}
">=" {return MAYOR_O_IGUAL;}
"=" {return IGUAL;}
"==" {return IGUAL_A;}
"!=" {return DIFERENTE;}
"&&" {return AND;}
"||" {return OR;}
"!" {return NOT;}
";" {return PUNTO_COMA;}
"," {return COMA;}
"." {return PUNTO;}
"[" {return CORCHETE_ABIERTO;}
"]" {return CORCHETE_CERRADO;}
"(" {return PARENTESIS_ABIERTO;}
")" {return PARENTESIS_CERRADO;}
"{" {return LLAVE_ABIERTO;}
"}" {return LLAVE_CERRADO;}
"[]" {return CORCHETES;}
"()" {return PARENTESIS;}
"{}" {return LLAVES;}
"@" {return ARROBA;}
"#" {return NUMERAL;}
"##" {return DOBLE_NUMERAL;}
. {lexeme=yytext(); return ERROR;}

