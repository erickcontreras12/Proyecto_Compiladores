package Classes;
import static Classes.Token.*;
%%
%class Lexer
%type Token
%line
%column
L = [a-zA-Z_]
D = [0-9]
BLANK = [ ,\t,\r,\n]
%{
public String lexeme;
public int getLine() { return yyline; }
public int getColumn() { return yycolumn; }
%}
%%
"," {lexeme=yytext(); return COMA;}
{BLANK} {/*Ignore*/}
"\-\-" [^\n]*  {/*Ignore*/}
"/*"~("*/") | "/*" (""+ "*/") {/*Ignore*/}
NULL {lexeme=yytext(); return NULL;}
"/*"[^*\/]* {lexeme=yytext(); return COMENTARIO_M;}
("0"|"1"|"NULL") {lexeme=yytext(); return BIT;}
"."{D}+(("E"|"e"|"E+"|"E-"|"e+"|"e-"){D}+)?  {lexeme=yytext(); return ERROR_F;}
{D}+"."{D}*(("E"|"e"|"E+"|"E-"|"e+"|"e-"){D}+)? {lexeme=yytext(); return FLOAT;}
("+"|"-")?{D}+ {lexeme=yytext(); return INT;}
"\'"[^\'\n]*"\'"|"\'\'" {lexeme=yytext(); return STRING;}
SELECT {lexeme=yytext(); return SELECT;}
INSERT {lexeme=yytext(); return INSERT;}
INTO {lexeme=yytext(); return INTO;}
VALUES {lexeme=yytext(); return VALUES;}
DEFAULT {lexeme=yytext(); return DEFAULT;}
DELETE {lexeme=yytext(); return DELETE;}
UPDATE {lexeme=yytext(); return UPDATE;}
CREATE {lexeme=yytext(); return CREATE;}
ALTER {lexeme=yytext(); return ALTER;}
TRUNCATE {lexeme=yytext(); return TRUNCATE;}
DROP {lexeme=yytext(); return DROP;}
DATABASE {lexeme=yytext(); return DATABASE;}
INDEX {lexeme=yytext(); return INDEX;}
TABLE {lexeme=yytext(); return TABLE;}
USER {lexeme=yytext(); return USER;}
VIEW {lexeme=yytext(); return VIEW;}
CURRENT {lexeme=yytext(); return CURRENT;}
MODIFY {lexeme=yytext(); return MODIFY;}
NAME {lexeme=yytext(); return NAME;}
ON {lexeme=yytext(); return ON;}
CONSTRAINT {lexeme=yytext(); return CONSTRAINT;}
DISABLE {lexeme=yytext(); return DISABLE;}
REBUILD {lexeme=yytext(); return REBUILD;}
UNUSABLE {lexeme=yytext(); return UNUSABLE;}
USABLE {lexeme=yytext(); return USABLE;}
ALL {lexeme=yytext(); return ALL;}
WITH {lexeme=yytext(); return WITH;}
DEFAULT_SCHEMA {lexeme=yytext(); return DEFAULT_SCHEMA;}
LOGIN {lexeme=yytext(); return LOGIN;}
PASSWORD {lexeme=yytext(); return PASSWORD;}
ADD {lexeme=yytext(); return ADD;}
COLUMN {lexeme=yytext(); return COLUMN;}
UNIQUE {lexeme=yytext(); return UNIQUE;}
CHAR {lexeme=yytext(); return CHAR;}
VARCHAR {lexeme=yytext(); return VARCHAR;}
FLOAT {lexeme=yytext(); return FLOAT_P;}
DECIMAL {lexeme=yytext(); return DECIMAL;}
NUMERIC {lexeme=yytext(); return NUMERIC;}
IMAGE {lexeme=yytext(); return IMAGE;}
VARBINARY {lexeme=yytext(); return VARBINARY;}
TEXT {lexeme=yytext(); return TEXT;}
DATE {lexeme=yytext(); return DATE;}
DATETIME {lexeme=yytext(); return DATETIME;}
DATETIME2 {lexeme=yytext(); return DATETIME2;}
SMALLDATETIME {lexeme=yytext(); return SMALLDATETIME;}
TIME {lexeme=yytext(); return TIME;}
DATETIMEOFFSET {lexeme=yytext(); return DATETIMEOFFSET;}
TIMESTAMP {lexeme=yytext(); return TIMESTAMP;}
MONEY {lexeme=yytext(); return MONEY;}
REAL {lexeme=yytext(); return REAL;}
BIT {lexeme=yytext(); return BIT_P;}
INT {lexeme=yytext(); return INT_P;}
BETWEEN {lexeme=yytext(); return BETWEEN;}
GO {lexeme=yytext(); return GO;}
LIKE {lexeme=yytext(); return LIKE;}
NOT {lexeme=yytext(); return NOT;}
PRIMARY {lexeme=yytext(); return PRIMARY;}
KEY {lexeme=yytext(); return KEY;}
IDENTITY {lexeme=yytext(); return IDENTITY;}
REFERENCES {lexeme=yytext(); return REFERENCES;}
AND {lexeme=yytext(); return AND;}
OR {lexeme=yytext(); return OR;}
FOREIGN {lexeme=yytext(); return FOREIGN;}
CHECK {lexeme=yytext(); return CHECK;}
IF {lexeme=yytext(); return IF;}
EXISTS {lexeme=yytext(); return EXISTS;}
EXTERNAL|PROCEDURE|FETCH|PUBLIC|FILE|RAISERROR|AND|FILLFACTOR|READ|ANY|FOR|READTEXT|AS|RECONFIGURE|ASC|
FREETEXT|AUTHORIZATION|FREETEXTTABLE|REPLICATION|BACKUP|FROM|RESTORE|BEGIN|FULL|RESTRICT|FUNCTION|RETURN|
BREAK|GOTO|REVERT|BROWSE|GRANT|REVOKE|BULK|GROUP|RIGHT|BY|HAVING|ROLLBACK|CASCADE|HOLDLOCK|ROWCOUNT|CASE|ROWGUIDCOL|
IDENTITY_INSERT|RULE|CHECKPOINT|IDENTITYCOL|SAVE|CLOSE|SCHEMA|CLUSTERED|IN|SECURITYAUDIT|COALESCE|COLLATE|
INNER|SEMANTICKEYPHRASETABLE|SEMANTICSIMILARITYDETAILSTABLE|COMMIT|INTERSECT|SEMANTICSIMILARITYTABLE|COMPUTE|
SESSION_USER|IS|SET|CONTAINS|JOIN|SETUSER|CONTAINSTABLE|SHUTDOWN|CONTINUE|KILL|SOME|CONVERT|LEFT|STATISTICS|
SYSTEM_USER|CROSS|LINENO|TABLE|LOAD|TABLESAMPLE|CURRENT_DATE|MERGE|TEXTSIZE|CURRENT_TIME|NATIONA|THEN|CURRENT_TIMESTAMP|
NOCHECK|TO|CURRENT_USER|NONCLUSTERED|TOP|CURSOR|TRAN|TRANSACTION|DBCC|NULLIF|TRIGGER|DEALLOCATE|OF|
DECLARE|OFF|TRY_CONVERT|OFFSETS|TSEQUAL|ON|UNION|DENY|OPEN|DESC|OPENDATASOURCE|UNPIVOT|DISK|OPENQUERY|DISTINCT|
OPENROWSET|UPDATETEXT|DISTRIBUTED|OPENXML|USE|DOUBLE|OPTION|OR|DUMP|ORDER|VARYING|ELSE|OUTER|END|OVER|WAITFOR|ERRLVL|PERCENT|
WHEN|ESCAPE|PIVOT|WHERE|EXCEPT|PLAN|WHILE|EXEC|PRECISION|EXECUTE|WITHIN|GROUP|PRINT|WRITETEXT|EXIT|PROC1ABSOLUTE|
XEC|OVERLAPS|ACTION1EXECUTE1PAD1ADA1EXISTS1PARTIAL|EXTERNAL|PASCAL|EXTRACT|POSITION|ALLOCATE|FALSE|PRECISION|FETCH|PREPARE|
AND|FIRST|PRESERVE|ANY|ARE|FOR|PRIOR|AS|PRIVILEGES|ASC|FORTRAN|PROCEDURE|ASSERTION|FOUND|PUBLIC|AT|FROM|READ|
AUTHORIZATION|FULL|REAL|AVG|GET|BEGIN|GLOBAL|RELATIVE|RESTRICT|GOTO|REVOKE|BIT_LENGTH|GRANT|
RIGHT|BOTH|GROUP|ROLLBACK|BY|HAVING|ROWS|CASCADE|HOUR|SCHEMA|CASCADED|SCROLL|CASE|IMMEDIATE|SECOND|CAST|IN|SECTION|CATALOG|INCLUDE|SESSION|CHAR_LENGTH|INDICATOR|
SESSION_USER|CHARACTER|INITIALLY|SET|CHARACTER_LENGTH|INNER|SIZE|INPUT|SMALLINT|CLOSE|INSENSITIVE|SOME|COALESCE|INSERT|SPACE|
COLLATE|SQL|COLLATION|INTEGER|SQLCA|INTERSECT|SQLCODE|COMMIT|INTERVAL|SQLERROR|CONNECT|SQLSTATE|CONNECTION|IS|
SQLWARNING|ISOLATION|SUBSTRING|CONSTRAINTS|JOIN|SUM|CONTINUE|SYSTEM_USER|CONVERT|LANGUAGE|CORRESPONDING|
LAST|TEMPORARY|COUNT|LEADING|THEN|LEFT|CROSS|LEVEL|TIMEZONE_HOUR|CURRENT_DATE|LOCAL|VALUE|
TIMEZONE_MINUTE|CURRENT_TIME|LOWER|TO|CURRENT_TIMESTAMP|MATCH|TRAILING|CURRENT_USER|MAX|TRANSACTION|CURSOR|MIN|TRANSLATE|
MINUTE|TRANSLATION|DAY|MODULE|TRIM|DEALLOCATE|MONTH|TRUE|DEC|NAMES|UNION|NATIONAL|DECLARE|NATURAL|UNKNOWN|
NCHAR|DEFERRABLE NEXT|UPPER|DEFERRED|NO|USAGE|DELETE|NONE|DESC|USING|DESCRIBE|DESCRIPTOR|NULLIF|
DIAGNOSTICS|DISCONNECT|OCTET_LENGTH|VARYING|DISTINCT|OF|DOMAIN|WHEN|ONLY|WHENEVER|OPEN|
WHERE|ELSE|OPTION|WITH|END|OR|WORK|END-EXEC|ORDER|WRITE|ESCAPE|OUTER|YEAR|EXCEPT|OUTPUT|ZONE|EXCEPTION {lexeme=yytext(); return PALABRA_RESERVADA;}
{L}({L}|{D})* {lexeme=yytext(); return ID;}
"+" {lexeme=yytext(); return MAS;}
"-" {lexeme=yytext(); return MENOS;}
"*" {lexeme=yytext(); return MULT;}
"/" {lexeme=yytext(); return DIV;}
"%" {lexeme=yytext(); return RES;}
"<" {lexeme=yytext(); return MENOR;}
"<=" {lexeme=yytext(); return MENOR_O_IGUAL;}
">" {lexeme=yytext(); return MAYOR;}
">=" {lexeme=yytext(); return MAYOR_O_IGUAL;}
"=" {lexeme=yytext(); return IGUAL;}
"==" {lexeme=yytext(); return IGUAL_A;}
"!=" {lexeme=yytext(); return DIFERENTE;}
"&&" {lexeme=yytext(); return AND_O;}
"||" {lexeme=yytext(); return OR_O;}
"!" {lexeme=yytext(); return EXCLAMACION;}
";" {lexeme=yytext(); return PUNTO_COMA;}
"." {lexeme=yytext(); return PUNTO;}
"[" {lexeme=yytext(); return CORCHETE_ABIERTO;}
"]" {lexeme=yytext(); return CORCHETE_CERRADO;}
"(" {lexeme=yytext(); return PARENTESIS_ABIERTO;}
")" {lexeme=yytext(); return PARENTESIS_CERRADO;}
"{" {lexeme=yytext(); return LLAVE_ABIERTO;}
"}" {lexeme=yytext(); return LLAVE_CERRADO;}
"[]" {lexeme=yytext(); return CORCHETES;}
"()" {lexeme=yytext(); return PARENTESIS;}
"{}" {lexeme=yytext(); return LLAVES;}
"@" {lexeme=yytext(); return ARROBA;}
"#" {lexeme=yytext(); return NUMERAL;}
"##" {lexeme=yytext(); return DOBLE_NUMERAL;}
. {lexeme=yytext(); return ERROR;}

