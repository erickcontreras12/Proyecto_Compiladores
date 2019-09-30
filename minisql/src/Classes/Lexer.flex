package Classes;
import static Classes.Token.*;
%%
%class Lexer
%type Token
%line
%column
L = [a-zA-Z]
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
("-")?{D}+ {lexeme=yytext(); return INT;}
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
TOP {lexeme=yytext(); return TOP;}
PERCENT {lexeme=yytext(); return PERCENT;}
FROM {lexeme=yytext(); return FROM;}
WHERE {lexeme=yytext(); return WHERE;}
SET {lexeme=yytext(); return SET;}
AS {lexeme=yytext(); return AS;}
DISTINCT {lexeme=yytext(); return DISTINCT;}
LEFT {lexeme=yytext(); return LEFT;}
RIGHT {lexeme=yytext(); return RIGHT;}
GROUP {lexeme=yytext(); return GROUP;}
ORDER {lexeme=yytext(); return ORDER;}
HAVING {lexeme=yytext(); return HAVING;}
BY {lexeme=yytext(); return BY;}
JOIN {lexeme=yytext(); return JOIN;}
OUTER {lexeme=yytext(); return OUTER;}
INNER {lexeme=yytext(); return INNER;}
FULL {lexeme=yytext(); return FULL;}
DESC {lexeme=yytext(); return DESC;}
ASC {lexeme=yytext(); return ASC;}
AVG {lexeme=yytext(); return AVG;}
SUM {lexeme=yytext(); return SUM;}
COUNT {lexeme=yytext(); return COUNT;}
MAX {lexeme=yytext(); return MAX;}
MIN {lexeme=yytext(); return MIN;}
CLUSTERED {lexeme=yytext(); return CLUSTERED;}
NONCLUSTERED {lexeme=yytext(); return NONCLUSTERED;}
INCLUDE {lexeme=yytext(); return INCLUDE;}
FOR {lexeme=yytext(); return FOR;}
EXTERNAL|PROCEDURE|FETCH|PUBLIC|FILE|RAISERROR|AND|FILLFACTOR|READ|ANY|READTEXT|AS|RECONFIGURE|
FREETEXT|AUTHORIZATION|FREETEXTTABLE|REPLICATION|BACKUP|RESTORE|BEGIN|RESTRICT|FUNCTION|RETURN|
BREAK|GOTO|REVERT|BROWSE|GRANT|REVOKE|BULK|ROLLBACK|CASCADE|HOLDLOCK|ROWCOUNT|CASE|ROWGUIDCOL|
IDENTITY_INSERT|RULE|CHECKPOINT|IDENTITYCOL|SAVE|CLOSE|SCHEMA|IN|SECURITYAUDIT|COALESCE|COLLATE|
SEMANTICKEYPHRASETABLE|SEMANTICSIMILARITYDETAILSTABLE|COMMIT|INTERSECT|SEMANTICSIMILARITYTABLE|COMPUTE|
SESSION_USER|IS|CONTAINS|SETUSER|CONTAINSTABLE|SHUTDOWN|CONTINUE|KILL|SOME|CONVERT|STATISTICS|
SYSTEM_USER|CROSS|LINENO|LOAD|TABLESAMPLE|CURRENT_DATE|MERGE|TEXTSIZE|CURRENT_TIME|NATIONA|THEN|CURRENT_TIMESTAMP|
NOCHECK|TO|CURRENT_USER|CURSOR|TRAN|TRANSACTION|DBCC|NULLIF|TRIGGER|DEALLOCATE|OF|
DECLARE|OFF|TRY_CONVERT|OFFSETS|TSEQUAL|UNION|DENY|OPEN|OPENDATASOURCE|UNPIVOT|DISK|OPENQUERY|
OPENROWSET|UPDATETEXT|DISTRIBUTED|OPENXML|USE|DOUBLE|OPTION|DUMP|VARYING|ELSE|END|OVER|WAITFOR|ERRLVL|
WHEN|ESCAPE|PIVOT|EXCEPT|PLAN|WHILE|EXEC|PRECISION|EXECUTE|WITHIN|PRINT|WRITETEXT|EXIT|PROC1ABSOLUTE|
XEC|OVERLAPS|ACTION1EXECUTE1PAD1ADA1EXISTS1PARTIAL|EXTERNAL|PASCAL|EXTRACT|POSITION|ALLOCATE|FALSE|PRECISION|FETCH|PREPARE|
FIRST|PRESERVE|ANY|ARE|PRIOR|PRIVILEGES|FORTRAN|PROCEDURE|ASSERTION|FOUND|PUBLIC|AT|READ|
AUTHORIZATION|REAL|GET|BEGIN|GLOBAL|RELATIVE|RESTRICT|GOTO|REVOKE|BIT_LENGTH|GRANT|
BOTH|ROLLBACK|ROWS|CASCADE|HOUR|SCHEMA|CASCADED|SCROLL|CASE|IMMEDIATE|SECOND|CAST|IN|SECTION|CATALOG|SESSION|CHAR_LENGTH|INDICATOR|
SESSION_USER|CHARACTER|INITIALLY|CHARACTER_LENGTH|SIZE|INPUT|SMALLINT|CLOSE|INSENSITIVE|SOME|COALESCE|INSERT|SPACE|
COLLATE|SQL|COLLATION|INTEGER|SQLCA|INTERSECT|SQLCODE|COMMIT|INTERVAL|SQLERROR|CONNECT|SQLSTATE|CONNECTION|IS|
SQLWARNING|ISOLATION|SUBSTRING|CONSTRAINTS|CONTINUE|SYSTEM_USER|CONVERT|LANGUAGE|CORRESPONDING|
LAST|TEMPORARY|LEADING|THEN|CROSS|LEVEL|TIMEZONE_HOUR|CURRENT_DATE|LOCAL|VALUE|
TIMEZONE_MINUTE|CURRENT_TIME|LOWER|TO|CURRENT_TIMESTAMP|MATCH|TRAILING|CURRENT_USER|TRANSACTION|CURSOR|TRANSLATE|
MINUTE|TRANSLATION|DAY|MODULE|TRIM|DEALLOCATE|MONTH|TRUE|DEC|NAMES|UNION|NATIONAL|DECLARE|NATURAL|UNKNOWN|
NCHAR|DEFERRABLE NEXT|UPPER|DEFERRED|NO|USAGE|DELETE|NONE|USING|DESCRIBE|DESCRIPTOR|NULLIF|
DIAGNOSTICS|DISCONNECT|OCTET_LENGTH|VARYING|OF|DOMAIN|WHEN|ONLY|WHENEVER|OPEN|
ELSE|OPTION|WITH|END|WORK|END-EXEC|WRITE|ESCAPE|YEAR|EXCEPT|OUTPUT|ZONE|EXCEPTION {lexeme=yytext(); return PALABRA_RESERVADA;}
{L}({L}|{D}|_)*| "["{L}({L}|{D}|_)*"]" {lexeme=yytext(); return ID;}
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

