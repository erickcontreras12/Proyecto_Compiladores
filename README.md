# Proyecto_Compiladores
## Fase #1 
La primera fase del proyecto de curso de Compiladores consiste en un analizador léxico para mini-SQL. De forma que al ingresar un archivo 
de prueba el analizador es capaz de reconocer las palabras que pertenecen a dicho lenguaje.

El procedimiento que sigue el proyecto es que al ejecutar el programa se abre una ventana que permite seleccionar un archivo de prueba, del 
cual se obtiene el contenido y se muestra en un área de texto para que sea visible para el usuario. Luego de eso ya se habilita el botón de 
"Analizar" él cúal arranca el procedimiento que evalua el contenido del archivo seleccionado y determina que Tokens pertenecen al lenguaje 
y a que tipo se refieren. De igual manera devuelve el número de linea, el número de la primera y última columna en la que está el token.

Al terminar el análisis, el programa devuelve mensaje indicando al usuario que se escribió un archivo con el mismo nombre del seleccionado
y una extensión *.out* en el mismo directorio del original. Y de la misma manera se muestra el resultado en un pequeño cuadro de texto para
que el usuario pueda verificar desde el mismo programa.

Los errores que valida el programa son:
- Errores de longitud en identificadores: los identificadores en Mini-SQL permiten una longitud máxima de 31 caracteres por lo que con 
identificadores que superan esa cantidad, el analizador realiza un truncado desechando los caracteres extras y mostrando los 31 que si son 
válidos.
- Errores de cadenas inválidas: En el caso de los comentarios multilinea cuando estos no son cerrados el analizador muestra un error y 
regresa a leer y analizar el resto de texto, empezando en la linea inferior al inicio del comentario.
- Errores de caracteres inválidos: Cuando el analizador encuentra un símbolo o caracter que no es aceptado muestra el mensaje con el error 
y el caracter.
