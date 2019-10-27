package Classes;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
L = [a-zA-Z]
D = [0-9]
BLANK = [ ,\t,\r,\n]
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }

    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%
"," {return new Symbol(sym.COMA, yychar, yyline, yytext());}
{BLANK} {/*Ignore*/}
"\-\-" [^\n]*  {/*Ignore*/}
"/*"~("*/") | "/*" (""+ "*/") {/*Ignore*/}
NULL {return new Symbol(sym.NULL, yychar, yyline, yytext());}
"/*"[^*\/]* {return new Symbol(sym.COMENTARIO_M, yychar, yyline, yytext());}
("0"|"1"|"NULL") {return new Symbol(sym.BIT, yychar, yyline, yytext());}
"."{D}+(("E"|"e"|"E+"|"E-"|"e+"|"e-"){D}+)?  {return new Symbol(sym.ERROR_F, yychar, yyline, yytext());}
{D}+"."{D}*(("E"|"e"|"E+"|"E-"|"e+"|"e-"){D}+)? {return new Symbol(sym.FLOAT, yychar, yyline, yytext());}
("-")?{D}+ {return new Symbol(sym.INT, yychar, yyline, yytext());}
"\'"[^\'\n]*"\'"|"\'\'" {return new Symbol(sym.STRING, yychar, yyline, yytext());}
SELECT {return new Symbol(sym.SELECT, yychar, yyline, yytext());}
INSERT {return new Symbol(sym.INSERT, yychar, yyline, yytext());}
INTO {return new Symbol(sym.INTO, yychar, yyline, yytext());}
VALUES {return new Symbol(sym.VALUES, yychar, yyline, yytext());}
DEFAULT {return new Symbol(sym.DEFAULT, yychar, yyline, yytext());}
DELETE {return new Symbol(sym.DELETE, yychar, yyline, yytext());}
UPDATE {return new Symbol(sym.UPDATE, yychar, yyline, yytext());}
CREATE {return new Symbol(sym.CREATE, yychar, yyline, yytext());}
ALTER {return new Symbol(sym.ALTER, yychar, yyline, yytext());}
TRUNCATE {return new Symbol(sym.TRUNCATE, yychar, yyline, yytext());}
DROP {return new Symbol(sym.DROP, yychar, yyline, yytext());}
DATABASE {return new Symbol(sym.DATABASE, yychar, yyline, yytext());}
INDEX {return new Symbol(sym.INDEX, yychar, yyline, yytext());}
TABLE {return new Symbol(sym.TABLE, yychar, yyline, yytext());}
USER {return new Symbol(sym.USER, yychar, yyline, yytext());}
VIEW {return new Symbol(sym.VIEW, yychar, yyline, yytext());}
CURRENT {return new Symbol(sym.CURRENT, yychar, yyline, yytext());}
MODIFY {return new Symbol(sym.MODIFY, yychar, yyline, yytext());}
NAME {return new Symbol(sym.NAME, yychar, yyline, yytext());}
ON {return new Symbol(sym.ON, yychar, yyline, yytext());}
CONSTRAINT {return new Symbol(sym.CONSTRAINT, yychar, yyline, yytext());}
DISABLE {return new Symbol(sym.DISABLE, yychar, yyline, yytext());}
REBUILD {return new Symbol(sym.REBUILD, yychar, yyline, yytext());}
UNUSABLE {return new Symbol(sym.UNUSABLE, yychar, yyline, yytext());}
USABLE {return new Symbol(sym.USABLE, yychar, yyline, yytext());}
ALL {return new Symbol(sym.ALL, yychar, yyline, yytext());}
WITH {return new Symbol(sym.WITH, yychar, yyline, yytext());}
DEFAULT_SCHEMA {return new Symbol(sym.DEFAULT_SCHEMA, yychar, yyline, yytext());}
LOGIN {return new Symbol(sym.LOGIN, yychar, yyline, yytext());}
PASSWORD {return new Symbol(sym.PASSWORD, yychar, yyline, yytext());}
ADD {return new Symbol(sym.ADD, yychar, yyline, yytext());}
COLUMN {return new Symbol(sym.COLUMN, yychar, yyline, yytext());}
UNIQUE {return new Symbol(sym.UNIQUE, yychar, yyline, yytext());}
CHAR {return new Symbol(sym.CHAR, yychar, yyline, yytext());}
VARCHAR {return new Symbol(sym.VARCHAR, yychar, yyline, yytext());}
FLOAT {return new Symbol(sym.FLOAT_P, yychar, yyline, yytext());}
DECIMAL {return new Symbol(sym.DECIMAL, yychar, yyline, yytext());}
NUMERIC {return new Symbol(sym.NUMERIC, yychar, yyline, yytext());}
IMAGE {return new Symbol(sym.IMAGE, yychar, yyline, yytext());}
VARBINARY {return new Symbol(sym.VARBINARY, yychar, yyline, yytext());}
TEXT {return new Symbol(sym.TEXT, yychar, yyline, yytext());}
DATE {return new Symbol(sym.DATE, yychar, yyline, yytext());}
DATETIME {return new Symbol(sym.DATETIME, yychar, yyline, yytext());}
DATETIME2 {return new Symbol(sym.DATETIME2, yychar, yyline, yytext());}
SMALLDATETIME {return new Symbol(sym.SMALLDATETIME, yychar, yyline, yytext());}
TIME {return new Symbol(sym.TIME, yychar, yyline, yytext());}
DATETIMEOFFSET {return new Symbol(sym.DATETIMEOFFSET, yychar, yyline, yytext());}
TIMESTAMP {return new Symbol(sym.TIMESTAMP, yychar, yyline, yytext());}
MONEY {return new Symbol(sym.MONEY, yychar, yyline, yytext());}
REAL {return new Symbol(sym.REAL, yychar, yyline, yytext());}
BIT {return new Symbol(sym.BIT_P, yychar, yyline, yytext());}
INT {return new Symbol(sym.INT_P, yychar, yyline, yytext());}
BETWEEN {return new Symbol(sym.BETWEEN, yychar, yyline, yytext());}
GO {return new Symbol(sym.GO, yychar, yyline, yytext());}
LIKE {return new Symbol(sym.LIKE, yychar, yyline, yytext());}
NOT {return new Symbol(sym.NOT, yychar, yyline, yytext());}
PRIMARY {return new Symbol(sym.PRIMARY, yychar, yyline, yytext());}
KEY {return new Symbol(sym.KEY, yychar, yyline, yytext());}
IDENTITY {return new Symbol(sym.IDENTITY, yychar, yyline, yytext());}
REFERENCES {return new Symbol(sym.REFERENCES, yychar, yyline, yytext());}
AND {return new Symbol(sym.AND, yychar, yyline, yytext());}
OR {return new Symbol(sym.OR, yychar, yyline, yytext());}
FOREIGN {return new Symbol(sym.FOREIGN, yychar, yyline, yytext());}
CHECK {return new Symbol(sym.CHECK, yychar, yyline, yytext());}
IF {return new Symbol(sym.IF, yychar, yyline, yytext());}
EXISTS {return new Symbol(sym.EXISTS, yychar, yyline, yytext());}
TOP {return new Symbol(sym.TOP, yychar, yyline, yytext());}
PERCENT {return new Symbol(sym.PERCENT, yychar, yyline, yytext());}
FROM {return new Symbol(sym.FROM, yychar, yyline, yytext());}
WHERE {return new Symbol(sym.WHERE, yychar, yyline, yytext());}
SET {return new Symbol(sym.SET, yychar, yyline, yytext());}
AS {return new Symbol(sym.AS, yychar, yyline, yytext());}
DISTINCT {return new Symbol(sym.DISTINCT, yychar, yyline, yytext());}
LEFT {return new Symbol(sym.LEFT, yychar, yyline, yytext());}
RIGHT {return new Symbol(sym.RIGHT, yychar, yyline, yytext());}
GROUP {return new Symbol(sym.GROUP, yychar, yyline, yytext());}
ORDER {return new Symbol(sym.ORDER, yychar, yyline, yytext());}
HAVING {return new Symbol(sym.HAVING, yychar, yyline, yytext());}
BY {return new Symbol(sym.BY, yychar, yyline, yytext());}
JOIN {return new Symbol(sym.JOIN, yychar, yyline, yytext());}
OUTER {return new Symbol(sym.OUTER, yychar, yyline, yytext());}
INNER {return new Symbol(sym.INNER, yychar, yyline, yytext());}
FULL {return new Symbol(sym.FULL, yychar, yyline, yytext());}
DESC {return new Symbol(sym.DESC, yychar, yyline, yytext());}
ASC {return new Symbol(sym.ASC, yychar, yyline, yytext());}
AVG {return new Symbol(sym.AVG, yychar, yyline, yytext());}
SUM {return new Symbol(sym.SUM, yychar, yyline, yytext());}
COUNT {return new Symbol(sym.COUNT, yychar, yyline, yytext());}
MAX {return new Symbol(sym.MAX, yychar, yyline, yytext());}
MIN {return new Symbol(sym.MIN, yychar, yyline, yytext());}
CLUSTERED {return new Symbol(sym.CLUSTERED, yychar, yyline, yytext());}
NONCLUSTERED {return new Symbol(sym.NONCLUSTERED, yychar, yyline, yytext());}
INCLUDE {return new Symbol(sym.INCLUDE, yychar, yyline, yytext());}
FOR {return new Symbol(sym.FOR, yychar, yyline, yytext());}
NAMES {return new Symbol(sym.NAMES, yychar, yyline, yytext());}
FILE {return new Symbol(sym.FILE, yychar, yyline, yytext());}
FILES {return new Symbol(sym.FILES, yychar, yyline, yytext());}
FILENAME {return new Symbol(sym.FILENAME, yychar, yyline, yytext());}
SIZE {return new Symbol(sym.SIZE, yychar, yyline, yytext());}
MAXSIZE {return new Symbol(sym.MAXSIZE, yychar, yyline, yytext());}
KB {return new Symbol(sym.KB, yychar, yyline, yytext());}
MB {return new Symbol(sym.MB, yychar, yyline, yytext());}
GB {return new Symbol(sym.GB, yychar, yyline, yytext());}
TB {return new Symbol(sym.TB, yychar, yyline, yytext());}
FILEGROWTH {return new Symbol(sym.FILEGROWTH, yychar, yyline, yytext());} 
PROCEDURE {return new Symbol(sym.PROCEDURE, yychar, yyline, yytext());}
FUNCTION {return new Symbol(sym.FUNCTION, yychar, yyline, yytext());}
RETURN {return new Symbol(sym.RETURN, yychar, yyline, yytext());}
RETURNS {return new Symbol(sym.RETURNS, yychar, yyline, yytext());}
EXEC {return new Symbol(sym.EXEC, yychar, yyline, yytext());}
EXECUTE {return new Symbol(sym.EXECUTE, yychar, yyline, yytext());}
EXECUTE_AS_CLAUSE {return new Symbol(sym.EXECUTE_AS_CLAUSE, yychar, yyline, yytext());}
USE {return new Symbol(sym.USE, yychar, yyline, yytext());}
SCHEMA {return new Symbol(sym.SCHEMA, yychar, yyline, yytext());}
BEGIN {return new Symbol(sym.BEGIN, yychar, yyline, yytext());}
END {return new Symbol(sym.END, yychar, yyline, yytext());}
TRAN {return new Symbol(sym.TRAN, yychar, yyline, yytext());}
TRANSACTION {return new Symbol(sym.TRANSACTION, yychar, yyline, yytext());}
COMMIT {return new Symbol(sym.COMMIT, yychar, yyline, yytext());}
ROLLBACK {return new Symbol(sym.ROLLBACK, yychar, yyline, yytext());}
SAVE {return new Symbol(sym.SAVE, yychar, yyline, yytext());}
DECLARE {return new Symbol(sym.DECLARE, yychar, yyline, yytext());}
VARYING {return new Symbol(sym.VARYING, yychar, yyline, yytext());}
CURSOR {return new Symbol(sym.CURSOR, yychar, yyline, yytext());}
TO {return new Symbol(sym.TO, yychar, yyline, yytext());}
RECOMPILE {return new Symbol(sym.RECOMPILE, yychar, yyline, yytext());}
ENCRYPTION {return new Symbol(sym.ENCRYPTION, yychar, yyline, yytext());}
SELF {return new Symbol(sym.SELF, yychar, yyline, yytext());}
OWNER {return new Symbol(sym.OWNER, yychar, yyline, yytext());}
CALLER {return new Symbol(sym.CALLER, yychar, yyline, yytext());}
OUT {return new Symbol(sym.OUT, yychar, yyline, yytext());}
OUTPUT {return new Symbol(sym.OUTPUT, yychar, yyline, yytext());}
READONLY {return new Symbol(sym.READONLY, yychar, yyline, yytext());}
REPLICATION {return new Symbol(sym.REPLICATION, yychar, yyline, yytext());}
OFF {return new Symbol(sym.OFF, yychar, yyline, yytext());}
SCHEMABINDING {return new Symbol(sym.SCHEMABINDING, yychar, yyline, yytext());}
INLINE {return new Symbol(sym.INLINE, yychar, yyline, yytext());}
CALLED {return new Symbol(sym.CALLED, yychar, yyline, yytext());}
MARK {return new Symbol(sym.MARK, yychar, yyline, yytext());}
STATIC {return new Symbol(sym.STATIC, yychar, yyline, yytext());}
FORWARD_ONLY {return new Symbol(sym.FORWARD_ONLY, yychar, yyline, yytext());}
KEYSET {return new Symbol(sym.KEYSET, yychar, yyline, yytext());}
FAST_FORWARD {return new Symbol(sym.FAST_FORWARD, yychar, yyline, yytext());}
DYNAMIC {return new Symbol(sym.DYNAMIC, yychar, yyline, yytext());}
READ_ONLY {return new Symbol(sym.READ_ONLY, yychar, yyline, yytext());}
SCROLL_LOCKS {return new Symbol(sym.SCROLL_LOCKS, yychar, yyline, yytext());}
SCROLL {return new Symbol(sym.SCROLL, yychar, yyline, yytext());}
OPTIMISTIC {return new Symbol(sym.OPTIMISTIC, yychar, yyline, yytext());}
TYPE_WARNING {return new Symbol(sym.TYPE_WARNING, yychar, yyline, yytext());}
INPUT {return new Symbol(sym.INPUT, yychar, yyline, yytext());}
LOCAL {return new Symbol(sym.LOCAL, yychar, yyline, yytext());}
GLOBAL {return new Symbol(sym.GLOBAL, yychar, yyline, yytext());}
COLLATE {return new Symbol(sym.COLLATE, yychar, yyline, yytext());}
INMEDIATE {return new Symbol(sym.INMEDIATE, yychar, yyline, yytext());}
EXTERNAL|FETCH|PUBLIC|RAISERROR|FILLFACTOR|READ|ANY|READTEXT|RECONFIGURE|
FREETEXT|AUTHORIZATION|FREETEXTTABLE|BACKUP|RESTORE|RESTRICT|BREAK|GOTO|REVERT|BROWSE|GRANT|REVOKE|BULK|CASCADE|
HOLDLOCK|ROWCOUNT|CASE|ROWGUIDCOL|IDENTITY_INSERT|RULE|CHECKPOINT|IDENTITYCOL|CLOSE|IN|SECURITYAUDIT|COALESCE|
SEMANTICKEYPHRASETABLE|SEMANTICSIMILARITYDETAILSTABLE|INTERSECT|SEMANTICSIMILARITYTABLE|COMPUTE|
SESSION_USER|IS|CONTAINS|SETUSER|CONTAINSTABLE|SHUTDOWN|CONTINUE|KILL|SOME|CONVERT|STATISTICS|
SYSTEM_USER|CROSS|LINENO|LOAD|TABLESAMPLE|CURRENT_DATE|MERGE|TEXTSIZE|CURRENT_TIME|NATIONA|THEN|CURRENT_TIMESTAMP|
NOCHECK|CURRENT_USER|DBCC|NULLIF|TRIGGER|DEALLOCATE|OF|TRY_CONVERT|OFFSETS|TSEQUAL|UNION|DENY|OPEN|OPENDATASOURCE|UNPIVOT|DISK|OPENQUERY|
OPENROWSET|UPDATETEXT|DISTRIBUTED|OPENXML|DOUBLE|OPTION|DUMP|ELSE|OVER|WAITFOR|ERRLVL|
WHEN|ESCAPE|PIVOT|EXCEPT|PLAN|WHILE|PRECISION|WITHIN|PRINT|WRITETEXT|EXIT|PROC1ABSOLUTE|
XEC|OVERLAPS|ACTION1EXECUTE1PAD1ADA1EXISTS1PARTIAL|EXTERNAL|PASCAL|EXTRACT|POSITION|ALLOCATE|FALSE|PRECISION|FETCH|PREPARE|
FIRST|PRESERVE|ANY|ARE|PRIOR|PRIVILEGES|FORTRAN|ASSERTION|FOUND|PUBLIC|AT|READ|
AUTHORIZATION|GET|RELATIVE|RESTRICT|GOTO|REVOKE|BIT_LENGTH|GRANT|
BOTH|ROWS|CASCADE|HOUR|CASCADED|CASE|IMMEDIATE|SECOND|CAST|IN|SECTION|CATALOG|SESSION|CHAR_LENGTH|INDICATOR|
SESSION_USER|CHARACTER|INITIALLY|CHARACTER_LENGTH|SMALLINT|CLOSE|INSENSITIVE|SOME|COALESCE|INSERT|SPACE|
SQL|COLLATION|INTEGER|SQLCA|INTERSECT|SQLCODE|INTERVAL|SQLERROR|CONNECT|SQLSTATE|CONNECTION|IS|
SQLWARNING|ISOLATION|SUBSTRING|CONSTRAINTS|CONTINUE|SYSTEM_USER|CONVERT|LANGUAGE|CORRESPONDING|
LAST|TEMPORARY|LEADING|THEN|CROSS|LEVEL|TIMEZONE_HOUR|CURRENT_DATE|VALUE|
TIMEZONE_MINUTE|CURRENT_TIME|LOWER|CURRENT_TIMESTAMP|MATCH|TRAILING|CURRENT_USER|TRANSLATE|
MINUTE|TRANSLATION|DAY|MODULE|TRIM|DEALLOCATE|MONTH|TRUE|DEC|UNION|NATIONAL|NATURAL|UNKNOWN|
NCHAR|DEFERRABLE NEXT|UPPER|DEFERRED|NO|USAGE|DELETE|NONE|USING|DESCRIBE|DESCRIPTOR|NULLIF|
DIAGNOSTICS|DISCONNECT|OCTET_LENGTH|OF|DOMAIN|WHEN|ONLY|WHENEVER|OPEN|
ELSE|OPTION|WORK|END-EXEC|WRITE|ESCAPE|YEAR|EXCEPT|ZONE|EXCEPTION {return new Symbol(sym.PALABRA_RESERVADA, yychar, yyline, yytext());}
{L}({L}|{D}|_)*| "["{L}({L}|{D}|_)*"]" {return new Symbol(sym.ID, yychar, yyline, yytext());}
"+" {return new Symbol(sym.MAS, yychar, yyline, yytext());}
"-" {return new Symbol(sym.MENOS, yychar, yyline, yytext());}
"*" {return new Symbol(sym.MULT, yychar, yyline, yytext());}
"/" {return new Symbol(sym.DIV, yychar, yyline, yytext());}
"%" {return new Symbol(sym.RES, yychar, yyline, yytext());}
"<" {return new Symbol(sym.MENOR, yychar, yyline, yytext());}
"<=" {return new Symbol(sym.MENOR_O_IGUAL, yychar, yyline, yytext());}
">" {return new Symbol(sym.MAYOR, yychar, yyline, yytext());}
">=" {return new Symbol(sym.MAYOR_O_IGUAL, yychar, yyline, yytext());}
"=" {return new Symbol(sym.IGUAL, yychar, yyline, yytext());}
"==" {return new Symbol(sym.IGUAL_A, yychar, yyline, yytext());}
"!=" {return new Symbol(sym.DIFERENTE, yychar, yyline, yytext());}
"&&" {return new Symbol(sym.AND_O, yychar, yyline, yytext());}
"||" {return new Symbol(sym.OR_O, yychar, yyline, yytext());}
"!" {return new Symbol(sym.EXCLAMACION, yychar, yyline, yytext());}
";" {return new Symbol(sym.PUNTO_COMA, yychar, yyline, yytext());}
"." {return new Symbol(sym.PUNTO, yychar, yyline, yytext());}
"[" {return new Symbol(sym.CORCHETE_ABIERTO, yychar, yyline, yytext());}
"]" {return new Symbol(sym.CORCHETE_CERRADO, yychar, yyline, yytext());}
"(" {return new Symbol(sym.PARENTESIS_ABIERTO, yychar, yyline, yytext());}
")" {return new Symbol(sym.PARENTESIS_CERRADO, yychar, yyline, yytext());}
"{" {return new Symbol(sym.LLAVE_ABIERTO, yychar, yyline, yytext());}
"}" {return new Symbol(sym.LLAVE_CERRADO, yychar, yyline, yytext());}
"[]" {return new Symbol(sym.CORCHETES, yychar, yyline, yytext());}
"()" {return new Symbol(sym.PARENTESIS, yychar, yyline, yytext());}
"{}" {return new Symbol(sym.LLAVES, yychar, yyline, yytext());}
"@" {return new Symbol(sym.ARROBA, yychar, yyline, yytext());}
"#" {return new Symbol(sym.NUMERAL, yychar, yyline, yytext());}
"##" {return new Symbol(sym.DOBLE_NUMERAL, yychar, yyline, yytext());}
. {return new Symbol(sym.ERROR, yychar, yyline, yytext());}
