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
"GO;" {lexeme=yytext(); return PUNTO_COMA_GO;}
";GO" {lexeme=yytext(); return GO_PUNTO_COMA;}
IN {lexeme=yytext(); return IN;}
PROC {lexeme=yytext(); return PROC;}
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
NVARCHAR {lexeme=yytext(); return NVARCHAR;}
VARIABLE {lexeme=yytext(); return VARIABLE;}
INSENSITIVE {lexeme=yytext(); return INSENSITIVE;}
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
NAMES {lexeme=yytext(); return NAMES;}
FILE {lexeme=yytext(); return FILE;}
FILES {lexeme=yytext(); return FILES;}
FILENAME {lexeme=yytext(); return FILENAME;}
SIZE {lexeme=yytext(); return SIZE;}
MAXSIZE {lexeme=yytext(); return MAXSIZE;}
KB {lexeme=yytext(); return KB;}
MB {lexeme=yytext(); return MB;}
GB {lexeme=yytext(); return GB;}
TB {lexeme=yytext(); return TB;}
FILEGROWTH {lexeme=yytext(); return FILEGROWTH;} 
PROCEDURE {lexeme=yytext(); return PROCEDURE;}
FUNCTION {lexeme=yytext(); return FUNCTION;}
RETURN {lexeme=yytext(); return RETURN;}
RETURNS {lexeme=yytext(); return RETURNS;}
EXEC {lexeme=yytext(); return EXEC;}
EXECUTE {lexeme=yytext(); return EXECUTE;}
EXECUTE_AS_CLAUSE {lexeme=yytext(); return EXECUTE_AS_CLAUSE;}
USE {lexeme=yytext(); return USE;}
SCHEMA {lexeme=yytext(); return SCHEMA;}
BEGIN {lexeme=yytext(); return BEGIN;}
END {lexeme=yytext(); return END;}
TRAN {lexeme=yytext(); return TRAN;}
TRANSACTION {lexeme=yytext(); return TRANSACTION;}
COMMIT {lexeme=yytext(); return COMMIT;}
ROLLBACK {lexeme=yytext(); return ROLLBACK;}
SAVE {lexeme=yytext(); return SAVE;}
DECLARE {lexeme=yytext(); return DECLARE;}
VARYING {lexeme=yytext(); return VARYING;}
CURSOR {lexeme=yytext(); return CURSOR;}
TO {lexeme=yytext(); return TO;}
RECOMPILE {lexeme=yytext(); return RECOMPILE;}
ENCRYPTION {lexeme=yytext(); return ENCRYPTION;}
SELF {lexeme=yytext(); return SELF;}
OWNER {lexeme=yytext(); return OWNER;}
CALLER {lexeme=yytext(); return CALLER;}
OUT {lexeme=yytext(); return OUT;}
OUTPUT {lexeme=yytext(); return OUTPUT;}
READONLY {lexeme=yytext(); return READONLY;}
REPLICATION {lexeme=yytext(); return REPLICATION;}
OFF {lexeme=yytext(); return OFF;}
OF {lexeme=yytext(); return OF;}
SCHEMABINDING {lexeme=yytext(); return SCHEMABINDING;}
INLINE {lexeme=yytext(); return INLINE;}
CALLED {lexeme=yytext(); return CALLED;}
MARK {lexeme=yytext(); return MARK;}
STATIC {lexeme=yytext(); return STATIC;}
FORWARD_ONLY {lexeme=yytext(); return FORWARD_ONLY;}
KEYSET {lexeme=yytext(); return KEYSET;}
FAST_FORWARD {lexeme=yytext(); return FAST_FORWARD;}
DYNAMIC {lexeme=yytext(); return DYNAMIC;}
READ_ONLY {lexeme=yytext(); return READ_ONLY;}
SCROLL_LOCKS {lexeme=yytext(); return SCROLL_LOCKS;}
SCROLL {lexeme=yytext(); return SCROLL;}
OPTIMISTIC {lexeme=yytext(); return OPTIMISTIC;}
TYPE_WARNING {lexeme=yytext(); return TYPE_WARNING;}
INPUT {lexeme=yytext(); return INPUT;}
LOCAL {lexeme=yytext(); return LOCAL;}
GLOBAL {lexeme=yytext(); return GLOBAL;}
COLLATE {lexeme=yytext(); return COLLATE;}
INMEDIATE {lexeme=yytext(); return INMEDIATE;}
DELAYED_DURABILITY {lexeme=yytext(); return DELAYED_DURABILITY;}
ALLOW_ENCRYPTED_VALUE_MODIFICATIONS {lexeme=yytext(); return ALLOW_ENCRYPTED_VALUE_MODIFICATIONS;}
EXTERNAL|FETCH|PUBLIC|RAISERROR|FILLFACTOR|READ|ANY|READTEXT|RECONFIGURE|
FREETEXT|AUTHORIZATION|FREETEXTTABLE|BACKUP|RESTORE|RESTRICT|BREAK|GOTO|REVERT|BROWSE|GRANT|REVOKE|BULK|CASCADE|
HOLDLOCK|ROWCOUNT|CASE|ROWGUIDCOL|IDENTITY_INSERT|RULE|CHECKPOINT|IDENTITYCOL|CLOSE|SECURITYAUDIT|COALESCE|
SEMANTICKEYPHRASETABLE|SEMANTICSIMILARITYDETAILSTABLE|INTERSECT|SEMANTICSIMILARITYTABLE|COMPUTE|
SESSION_USER|IS|CONTAINS|SETUSER|CONTAINSTABLE|SHUTDOWN|CONTINUE|KILL|SOME|CONVERT|STATISTICS|
SYSTEM_USER|CROSS|LINENO|LOAD|TABLESAMPLE|CURRENT_DATE|MERGE|TEXTSIZE|CURRENT_TIME|NATIONA|THEN|CURRENT_TIMESTAMP|
NOCHECK|CURRENT_USER|DBCC|NULLIF|TRIGGER|DEALLOCATE|OF|TRY_CONVERT|OFFSETS|TSEQUAL|UNION|DENY|OPEN|OPENDATASOURCE|UNPIVOT|DISK|OPENQUERY|
OPENROWSET|UPDATETEXT|DISTRIBUTED|OPENXML|DOUBLE|OPTION|DUMP|ELSE|OVER|WAITFOR|ERRLVL|
WHEN|ESCAPE|PIVOT|EXCEPT|PLAN|WHILE|PRECISION|WITHIN|PRINT|WRITETEXT|EXIT|PROC1ABSOLUTE|
XEC|OVERLAPS|ACTION1EXECUTE1PAD1ADA1EXISTS1PARTIAL|EXTERNAL|PASCAL|EXTRACT|POSITION|ALLOCATE|FALSE|PRECISION|FETCH|PREPARE|
FIRST|PRESERVE|ANY|ARE|PRIOR|PRIVILEGES|FORTRAN|ASSERTION|FOUND|PUBLIC|AT|READ|
AUTHORIZATION|GET|RELATIVE|RESTRICT|GOTO|REVOKE|BIT_LENGTH|GRANT|
BOTH|ROWS|CASCADE|HOUR|CASCADED|CASE|IMMEDIATE|SECOND|CAST|SECTION|CATALOG|SESSION|CHAR_LENGTH|INDICATOR|
SESSION_USER|CHARACTER|INITIALLY|CHARACTER_LENGTH|SMALLINT|CLOSE|INSENSITIVE|SOME|COALESCE|INSERT|SPACE|
SQL|COLLATION|INTEGER|SQLCA|INTERSECT|SQLCODE|INTERVAL|SQLERROR|CONNECT|SQLSTATE|CONNECTION|IS|
SQLWARNING|ISOLATION|SUBSTRING|CONSTRAINTS|CONTINUE|SYSTEM_USER|CONVERT|LANGUAGE|CORRESPONDING|
LAST|TEMPORARY|LEADING|THEN|CROSS|LEVEL|TIMEZONE_HOUR|CURRENT_DATE|VALUE|
TIMEZONE_MINUTE|CURRENT_TIME|LOWER|CURRENT_TIMESTAMP|MATCH|TRAILING|CURRENT_USER|TRANSLATE|
MINUTE|TRANSLATION|DAY|MODULE|TRIM|DEALLOCATE|MONTH|TRUE|DEC|UNION|NATIONAL|NATURAL|UNKNOWN|
NCHAR|DEFERRABLE NEXT|UPPER|DEFERRED|NO|USAGE|DELETE|NONE|USING|DESCRIBE|DESCRIPTOR|NULLIF|
DIAGNOSTICS|DISCONNECT|OCTET_LENGTH|OF|DOMAIN|WHEN|ONLY|WHENEVER|OPEN|
ELSE|OPTION|WORK|END-EXEC|WRITE|ESCAPE|YEAR|EXCEPT|ZONE|EXCEPTION {lexeme=yytext(); return PALABRA_RESERVADA;}
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
