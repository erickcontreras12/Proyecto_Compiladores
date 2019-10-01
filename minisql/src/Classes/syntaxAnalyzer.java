/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.ArrayList;

/**
 *
 * @author eecon
 */
public class syntaxAnalyzer {

    int cont;
    int cont_general;
    boolean itsBetween = false;
    boolean itsLike = false;
    String temp, resultado;
    ArrayList<String> Tokens = new ArrayList();
    ArrayList<String> lista_general = new ArrayList();
    ArrayList<DetalleToken> detalles = new ArrayList();

    public syntaxAnalyzer(ArrayList<String> Tokens, ArrayList<DetalleToken> Detalle) {
        cont = 0;
        resultado = "";
        lista_general = Tokens;
        detalles = Detalle;
    }

    public String makeTextAnalysis() {
        String response = "";
        cont_general = 0;
        while (cont_general < lista_general.size()) {
            String aux = "";
            while (true) {
                aux = lista_general.get(cont_general);
                if (aux.equals("PUNTO_COMA") || aux.equals("GO")) {
                    Tokens.add(lista_general.get(cont_general));
                    cont = 0;
                    if (Tokens.contains("ERROR") || Tokens.contains("ERROR_F")) {
                        for (int i = 0; i < Tokens.size(); i++) {
                            if (Tokens.get(i).equals("ERROR") || Tokens.get(i).equals("ERROR_F")) {
                                int position = cont_general - (Tokens.size() - i);
                                response += "Se encontro un error lexico. " + detalles.get(position).fila + " Columna: " + detalles.get(position).columna + "\n";
                            }
                        }
                    } else {
                        response += makeAnalysis();
                    }
                    Tokens.clear();
                } else {
                    Tokens.add(lista_general.get(cont_general));
                }
                cont_general++;

                if (cont_general == lista_general.size()) {
                    break;
                }
            }
        }

        return response;
    }

    /**
     *
     * @return
     */
    public String makeAnalysis() {
        int returned_value = 0;
        resultado = "";
        while (cont < Tokens.size()) {
            String aux_token = Tokens.get(cont);

            if (returned_value == 0) {
                switch (aux_token) {
                    case "INSERT":
                        returned_value = INSERT();
                        break;
                    case "SELECT":
                        returned_value = SELECT();
                        break;
                    case "ALTER":
                        returned_value = ALTER();
                        break;
                    case "CREATE":
                        returned_value = CREATE();
                        break;
                    case "TRUNCATE":
                        returned_value = TRUNCATE();
                        break;
                    case "DROP":
                        returned_value = DROP();
                        break;
                    case "UPDATE":
                        returned_value = UPDATE();
                        break;
                    case "DELETE":
                        returned_value = DELETE();
                        break;
                    case "GO":
                        returned_value = 1;
                        break;
                    default:
                        returned_value = 1;
                        resultado += "Error, inicio de sentencia invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        break;
                }
            } else if (returned_value == 1) {
                if (aux_token.equals("INSERT") || aux_token.equals("SELECT") || aux_token.equals("ALTER")) {
                    returned_value = 0;
                } else if (aux_token.equals("PUNTO_COMA") || aux_token.equals("GO")) {
                    returned_value = 0;
                    cont++;
                } else {
                    cont++;
                }
            } else {
                if (aux_token.equals("PUNTO_COMA") || aux_token.equals("GO")) {
                    returned_value = 0;
                    //resultado += "Sentencia correcta, finaliza en linea: " + detalles.get(cont_general-cont-1).fila + " y columna: " + detalles.get(cont_general-cont-1).columna + "\n";
                    cont++;
                } else {
                    resultado += "Error, falta punto y coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    returned_value = 1;
                }
            }
        }
        return resultado;
    }

    /**
     *
     * @return devuelve un entero (2) para saber si todo va bien
     */
    private int INSERT() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("INTO")) {
            int res = nombre();
            if (res == 1) {
                return 1;
            } else if (res == 2 && (cont + 1) == Tokens.size()) {
                return 2;
            }

            if (temp.equals("PUNTO_COMA")) {
                return 2;
            }
            if (B2() == 1) {
                return 1;
            }
        } else if (temp.equals("ID")) {
            if (nombre1() == 1) {
                return 1;
            }
            if (temp.equals("PUNTO_COMA")) {
                return 2;
            }
            if (B2() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un identificador o un INTO. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int nombre() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (nombre1() == 1) {
                return 1;
            }
        } else if (temp.equals("PARENTESIS_ABIERTO")) {
            if (A2() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int nombre1() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (nombre2() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("PARENTESIS_ABIERTO")) {
            if (A2() == 1) {
                return 1;
            }
        } else if (temp.equals("VALUES") || temp.equals("DEFAULT")) {
            if (temp.equals("VALUES")) {
                if (D2() == 1) {
                    return 1;
                }
            } else {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("VALUES")) {
                    cont++;
                } else {
                    resultado += "Error, se esperaba la palabra VALUES. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            }
        } else {
            resultado += "Error, se esperaba un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int nombre2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                //Todo ok
            } else {
                resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("PARENTESIS_ABIERTO")) {
            if (A2() == 1) {
                return 1;
            }
        } else if (temp.equals("VALUES") || temp.equals("DEFAULT")) {
            if (temp.equals("VALUES")) {
                if (D2() == 1) {
                    return 1;
                }
            } else {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("VALUES")) {
                    cont++;
                } else {
                    resultado += "Error, se esperaba la palabra VALUES. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            }
        } else {
            resultado += "Error, se esperaba un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int A2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (C2() == 1) {
                return 1;
            }
        } else {
            resultado += "Error de identificador, nombre de columna inválido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int C2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("COMA")) {
            if (C2_prima() == 1) {
                return 1;
            }
        } else if (temp.equals("PARENTESIS_CERRADO")) {

        } else {
            resultado += "Error, se esperaba una coma o cerrar el conjunto. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int C2_prima() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (C2() == 1) {
                return 1;
            }
        } else if (temp.equals("PARENTESIS_CERRADO")) {

        } else {
            resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int B2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("DEFAULT")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("VALUES")) {
                cont++;
            } else {
                resultado += "Error, se esperaba la palabra VALUES. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("VALUES")) {
            if (D2() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, falta declaracion de valores con VALUES. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int D2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PARENTESIS_ABIERTO")) {
            if (E2() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int E2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("STRING") || temp.equals("BIT") || temp.equals("INT") || temp.equals("FLOAT") || temp.equals("NULL")) {
            //entre a F2 en teoria, que solo tiene los tipos de datos
            if (E2_prima() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, tipo de dato invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int E2_prima() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("COMA")) {
            if (E2() == 1) {
                return 1;
            }
        } else if (temp.equals("PARENTESIS_CERRADO")) {
            if (D2_prima() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba una coma o parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int D2_prima() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("COMA")) {
            if (D2() == 1) {
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {

        } else {
            resultado += "Error, se esperaba una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int ALTER() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("DATABASE")) {
            if (database() == 1) {
                return 1;
            }
        } else if (temp.equals("INDEX")) {
            if (index() == 1) {
                return 1;
            }
        } else if (temp.equals("VIEW")) {
            if (a_view() == 1) {
                return 1;
            }
        } else if (temp.equals("TABLE")) {
            if (table() == 1) {
                return 1;
            }
        } else if (temp.equals("USER")) {
            if (user() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un objeto. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int database() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID") || temp.equals("CURRENT")) {
            if (tipo_alter_db() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, base de datos incorrecta. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int tipo_alter_db() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("MODIFY")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("NAME")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("IGUAL")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("ID")) {
                        cont++;
                    } else {
                        resultado += "Error, se espera un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, se espera un '='. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta la palabra reservada NAME. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else {
            resultado += "Error, se espera un MODIFY. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int a_view() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1V() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, nombre de vista incorrecto. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreV() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1V() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1V() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1V() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("ID") || temp.equals("AS") || temp.equals("COMA")) {
            if (temp.equals("ID")) {
                cont--;
                if (columna_view() == 1) {
                    return 1;
                }
            } else if (temp.equals("COMA")) {
                if (objeto_nombreV() == 1) {
                    return 1;
                }
            } else {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("SELECT")) {
                    if (SELECT() == 1) {
                        return 1;
                    }
                } else {
                    resultado += "Error, falta el query. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int columna_view() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1V() == 1) {
                return 1;
            }
        } else if (temp.equals("AS")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("SELECT")) {
                if (SELECT() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta el query. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else {
            resultado += "Error, falta una declaracion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int index() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID") || temp.equals("ALL")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ON")) {
                if (objeto_nombre() == 1) {
                    return 1;
                }

                cont++;
            } else {
                resultado += "Error, se esperaba un ON. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else {
            resultado += "Error, index inexistente. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombre() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("USABLE") || temp.equals("DISABLE") || temp.equals("REBUILD") || temp.equals("UNUSABLE")) {
            if (accion_index() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int accion_index() {
        if (temp.equals("USABLE") || temp.equals("DISABLE") || temp.equals("REBUILD") || temp.equals("UNUSABLE")) {
            return 2;
        } else {
            return 1;
        }
    }

    private int table() {
        if (objeto_nombreT() == 1) {
            return 1;
        }
        return 2;
    }

    private int objeto_nombreT() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1T() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1T() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1T() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("ALTER") || temp.equals("ADD") || temp.equals("DROP")) {
            if (accion_alter() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int accion_alter() {
        if (temp.equals("ALTER")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("COLUMN")) {
                if (add_column() == 1) {
                    return 1;
                }
                cont++;
            } else {
                resultado += "Error, falta la palabra COLUMN. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("ADD")) {
            if (tipo_add() == 1) {
                return 1;
            }
        } else if (temp.equals("DROP")) {
            if (drop_column() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no existe esa accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int add_column() {
        if (objeto_nombreAC() == 1) {
            return 1;
        }

        if (constraint() == 1) {
            return 1;
        }
        return 2;
    }

    private int objeto_nombreAC() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1AC() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1AC() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1AC() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("FLOAT_P") || temp.equals("CHAR") || temp.equals("VARCHAR") || temp.equals("DECIMAL") || temp.equals("NUMERIC")
                || temp.equals("TEXT") || temp.equals("IMAGE") || temp.equals("VARBINARY") || temp.equals("DATE") || temp.equals("DATETIME")
                || temp.equals("DATETIME2") || temp.equals("SMALLDATETIME") || temp.equals("TIME") || temp.equals("DATETIMEOFFSET")
                || temp.equals("TIMESTAMP") || temp.equals("MONEY") || temp.equals("REAL") || temp.equals("BIT_P") || temp.equals("INT_P")) {
            if (tipo_dato() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int tipo_dato() {
        if (temp.equals("FLOAT_P") || temp.equals("CHAR") || temp.equals("VARCHAR")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("INT")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("PARENTESIS_CERRADO")) {

                    } else {
                        resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("DECIMAL") || temp.equals("NUMERIC")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("INT")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("COMA")) {
                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("INT")) {
                            cont++;
                            temp = Tokens.get(cont);
                            if (temp.equals("PARENTESIS_CERRADO")) {

                            } else {
                                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                                return 1;
                            }
                        } else {
                            resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }
                    } else {
                        resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("TEXT") || temp.equals("IMAGE") || temp.equals("VARBINARY") || temp.equals("DATE") || temp.equals("DATETIME") || temp.equals("DATETIME2")
                || temp.equals("SMALLDATETIME") || temp.equals("TIME") || temp.equals("DATETIMEOFFSET") || temp.equals("TIMESTAMP") || temp.equals("MONEY")
                || temp.equals("REAL") || temp.equals("BIT_P") || temp.equals("INT_P")) {
            //Todo ok
        } else {
            resultado += "Error, se esperaba un tipo de dato. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreKEY() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1KEY() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1KEY() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1KEY() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("COMA")) {
            if (objeto_nombreKEY() == 1) {
                return 1;
            }
        } else if (temp.equals("PARENTESIS_CERRADO") || temp.equals("PUNTO_COMA")) {

        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreR() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1R() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1R() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1R() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("PARENTESIS_ABIERTO")) {

        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int constraint() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("NULL") || temp.equals("UNIQUE") || temp.equals("NOT")) {
            if (temp.equals("NOT")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("NULL")) {
                    //todo ok
                } else {
                    resultado += "Error, falta un NULL. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            }
            //todo ok
        } else if (temp.equals("PRIMARY") || temp.equals("FOREIGN")) {
            String helper = temp;
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("KEY")) {
                if (helper.equals("FOREIGN")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("PARENTESIS_ABIERTO")) {
                        if (objeto_nombreKEY() == 1) {
                            return 1;
                        }

                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("REFERENCES")) {
                            if (objeto_nombreR() == 1) {
                                return 1;
                            }

                            if (objeto_nombreKEY() == 1) {
                                return 1;
                            }
                        } else {
                            resultado += "Error, falta una referencia. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }

                    } else {
                        resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                }
            } else {
                resultado += "Error, falta la palabra key. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("DEFAULT")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("STRING") || temp.equals("BIT") || temp.equals("INT") || temp.equals("FLOAT")) {
                //todo ok
            } else {
                resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("IDENTITY")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("INT")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("COMA")) {
                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("INT")) {
                            cont++;
                            temp = Tokens.get(cont);
                            if (temp.equals("PARENTESIS_CERRADO")) {
                                //todo ok
                            } else {
                                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                                return 1;
                            }
                        } else {

                            resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }
                    } else {
                        resultado += "Error, falta una coma . Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("PUNTO_COMA")) {
            //Viene sin constraint, entonces le resto 1 para que luego agarre el punto y coma arriba
            cont--;
        } else {
            resultado += "Error, no existe el tipo de constraint. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int tipo_add() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("CONSTRAINT")) {
            if (objeto_nombreCT() == 1) {
                return 1;
            }
        } else if (temp.equals("FOREIGN") || temp.equals("CHECK") || temp.equals("UNIQUE") || temp.equals("PRIMARY")) {
            if (tipo_constraint() == 1) {
                return 1;
            }
        } else if (columna() == 2) {
            return 1;
        } else if (columna() == 2) {
            return 2;
        } else {
            resultado += "Error, propiedad invalida. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int columna() {
        cont--;
        if (add_column() == 1) {
            return 1;
        }

        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("COMA")) {
            cont++;
            if (columna() == 1) {
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {

        } else {
            return 1;
        }

        return 2;
    }

    private int drop_column() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("CONSTRAINT") || temp.equals("COLUMN") || temp.equals("INDEX")) {
            if (temp.equals("COLUMN")) {
                if (objeto_nombreDPC() == 1) {
                    return 1;
                }
            } else {
                if (objeto_nombreDP() == 1) {
                    return 1;
                }
            }
        } else {
            resultado += "Error, accion invalida. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreDP() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1DP() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1DP() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1DP() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("PUNTO_COMA")) {

        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreDPC() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1DPC() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1DPC() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1DPC() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("PUNTO_COMA") || temp.equals("COMA")) {
            if (temp.equals("COMA")) {
                if (objeto_nombreDPC() == 1) {
                    return 1;
                }
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreCT() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1CT() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1CT() {
        cont++;
        temp = Tokens.get(cont);
        int k;
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1CT() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("UNIQUE") || temp.equals("FOREIGN") || temp.equals("PRIMARY") || temp.equals("CHECK")) {
            if (tipo_constraint() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int tipo_constraint() {
        if (temp.equals("UNIQUE")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                if (objeto_nombreKEY() == 1) {
                    return 1;
                }
                cont++;
            } else {
                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("FOREIGN") || temp.equals("PRIMARY")) {
            String helper = temp;
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("KEY")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("PARENTESIS_ABIERTO")) {
                    if (objeto_nombreKEY() == 1) {
                        return 1;
                    }

                    if (helper.equals("FOREIGN")) {
                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("REFERENCES")) {
                            if (objeto_nombreR() == 1) {
                                return 1;
                            }

                            if (objeto_nombreKEY() == 1) {
                                return 1;
                            }
                            cont++;
                        } else {
                            resultado += "Error, falta la referencia. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }
                    }
                } else {
                    resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta la palabra key. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("CHECK")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                if (expresion_logica() == 1) {
                    return 1;
                }
                cont++;
            }
        } else {
            resultado += "Error, constraint invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int expresion_logica() {
        if (expresion_logica1() == 1) {
            return 1;
        }

        return 2;
    }

    private int expresion_logica1() {
        if (objeto_nombreO() == 1) {
            return 1;
        }

        if (data_tipo() == 1) {
            return 1;
        }

        if (expresion_logicaPrima() == 1) {
            return 1;
        }
        return 2;
    }

    private int expresion_logicaPrima() {
        if (operadores_logicos() == 1) {
            return 1;
        } else {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_CERRADO") || temp.equals("PUNTO_COMA")) {
                if (temp.equals("PUNTO_COMA")) {
                    cont--;
                }
                return 2;
            } else {
                cont--;
            }
        }

        if (expresion_logica() == 1) {
            return 1;
        }
        return 2;
    }

    private int objeto_nombreO() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1O() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1O() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1O() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("MENOR") || temp.equals("MAYOR") || temp.equals("MENOR_O_IGUAL") || temp.equals("MAYOR_O_IGUAL")
                || temp.equals("IGUAL_A") || temp.equals("DIFERENTE") || temp.equals("LIKE") || temp.equals("BETWEEN")) {
            if (operadores() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int operadores() {
        if (temp.equals("MENOR") || temp.equals("MAYOR") || temp.equals("MENOR_O_IGUAL") || temp.equals("MAYOR_O_IGUAL")
                || temp.equals("IGUAL") || temp.equals("DIFERENTE") || temp.equals("LIKE") || temp.equals("BETWEEN")) {
            //todo ok
            if (temp.equals("BETWEEN")) {
                itsBetween = true;
            } else if (temp.equals("LIKE")) {
                itsLike = true;
            }
        } else {
            resultado += "Error, se esperaba un operador condicional. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int operadores_logicos() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("AND") || temp.equals("AND_O") || temp.equals("OR") || temp.equals("OR_O")) {

        } else if (temp.equals("PARENTESIS_CERRADO")) {
            //todo ok
        } else {
            resultado += "Error, operador logico no valido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int data_tipo() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("STRING") || temp.equals("BIT") || temp.equals("INT") || temp.equals("FLOAT")) {
            if (itsBetween && temp.equals("INT")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("AND")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("INT")) {
                        //todo ok
                    } else {
                        resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, falta un AND. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
                itsBetween = false;
            } else if (itsLike && temp.equals("STRING")) {
                itsLike = false;
                //todo ok
            } else {
                if (itsBetween) {
                    itsBetween = false;
                    resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
                if (itsLike) {
                    itsLike = false;
                    resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            }
        } else {
            resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int user() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("WITH")) {
                if (set_item() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta la palabra WITH. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else {
            resultado += "Error, usuario inexistente. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int set_item() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("NAME") || temp.equals("DEFAULT_SCHEMA") || temp.equals("NULL") || temp.equals("LOGIN") || temp.equals("PASSWORD")) {
            if (temp.equals("NULL")) {
                if (set_item1() == 1) {
                    return 1;
                }
            } else {
                String helper = temp;
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("IGUAL")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (helper.equals("PASSWORD")) {
                        if (temp.equals("STRING")) {
                            if (set_item1() == 1) {
                                return 1;
                            }
                        } else {
                            resultado += "Error, se esperaba un String. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }
                    } else {
                        if (temp.equals("ID")) {
                            if (set_item1() == 1) {
                                return 1;
                            }
                        } else {
                            resultado += "Error, identificador invalido . Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }
                    }
                } else {
                    resultado += "Error, falta un '='. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            }
        } else {
            resultado += "Error, accion invalida. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int set_item1() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("COMA")) {
            if (set_item() == 1) {
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {

        } else {
            resultado += "Error, se esperaba una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int TRUNCATE() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("TABLE")) {
            if (objeto_nombreDP() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, falta definir la tabla. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int DROP() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("TABLE")) {
            if (if_opT() == 1) {
                return 1;
            }
        } else if (temp.equals("INDEX")) {
            if (if_opI() == 1) {
                return 1;
            }
        } else if (temp.equals("DATABASE")) {
            if (if_opD() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, objeto invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int if_opT() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("IF")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("EXISTS")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("ID")) {
                    if (objeto_nombre1DPC() == 1) {
                        return 1;
                    }
                } else {
                    resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta un Exist. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("ID")) {
            if (objeto_nombre1DPC() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, movimiento invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int if_opD() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("IF")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("EXISTS")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("ID")) {
                    if (objeto_nombreDROPDB() == 1) {
                        return 1;
                    }
                } else {
                    resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta un Exist. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("ID")) {
            if (objeto_nombreDROPDB() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, movimiento invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreDROPDB() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("COMA")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombreDROPDB() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {

        } else {
            resultado += "Error, se esperaba una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int if_opI() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("IF")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("EXISTS")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("ID")) {
                    if (relational_compatible() == 1) {
                        return 1;
                    }
                } else {
                    resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta un Exist. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("ID")) {
            if (relational_compatible() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, movimiento invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int relational_compatible() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ON")) {
            if (objeto_nombreDPC2() == 1) {
                return 1;
            }
        } else if (temp.equals("PUNTO")) {
            cont--; //Como encontro el punto y el metodo de abajo tambien debe encontrarlo, le resto 1
            if (objeto_nombre1DPC() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, palabra invalida. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreDPC2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1DPC2() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1DPC2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1DPC2() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("PUNTO_COMA") || temp.equals("COMA")) {
            if (temp.equals("COMA")) {
                if (objeto_nombreDPC2() == 1) {
                    return 1;
                }
            }
        } else if (temp.equals("ON")) {
            if (objeto_nombreDPC2() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int DELETE() {
        if (opciones_delete() == 1) {
            return 1;
        }
        return 2;
    }

    private int opciones_delete() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("TOP")) {
            if (expression() == 1) {
                return 1;
            }

            if (porcentaje() == 1) {
                return 1;
            }

        } else if (temp.equals("FROM")) {
            if (objeto_nombreF() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, falta FROM. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int expression() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("INT") || temp.equals("BIT")) {

        } else if (temp.equals("PARENTESIS_ABIERTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("INT") || temp.equals("BIT")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("PARENTESIS_CERRADO")) {

                } else {
                    resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int porcentaje() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PERCENT")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("FROM")) {
                if (objeto_nombreF() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta FROM. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("FROM")) {
            if (objeto_nombreF() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, falta FROM. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreF() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1F() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1F() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1F() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("WHERE")) {
            if (condicionales() == 1) {
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {
            //todo ok
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int condicionales() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (operadores1() == 1) {
                return 1;
            }

            if (tipo_dato1() == 1) {
                return 1;
            }

            if (condicionales1() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int condicionales1() {
        if (operadores_logicos1() == 1) {
            return 1;
        }
        return 2;
    }

    private int operadores1() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("MENOR") || temp.equals("MAYOR") || temp.equals("MAYOR_O_IGUAL") || temp.equals("MENOR_O_IGUAL")
                || temp.equals("IGUAL") || temp.equals("DIFERENTE") || temp.equals("LIKE") || temp.equals("BETWEEN")) {
            if (temp.equals("BETWEEN")) {
                itsBetween = true;
            } else if (temp.equals("LIKE")) {
                itsLike = true;
            }
        } else {
            resultado += "Error, se necesita un operador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int operadores_logicos1() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("AND") || temp.equals("AND_O") || temp.equals("OR") || temp.equals("OR_O")) {
            if (condicionales() == 1) {
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {
            //todo ok
        } else {
            resultado += "Error, operador logico no valido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int tipo_dato1() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("STRING") || temp.equals("BIT") || temp.equals("INT") || temp.equals("FLOAT")) {
            if (itsBetween && (temp.equals("INT") || temp.equals("BIT"))) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("AND")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("INT")) {
                        //todo ok
                    } else {
                        resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, falta un AND. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
                itsBetween = false;
            } else if (itsLike && temp.equals("STRING")) {
                itsLike = false;
                //todo ok
            } else {
                if (itsBetween) {
                    itsBetween = false;
                    resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
                if (itsLike) {
                    itsLike = false;
                    resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            }
        } else {
            resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int CREATE() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("DATABASE")) {
            if (database_create() == 1) {
                return 1;
            }
        } else if (temp.equals("VIEW")) {
            if (view_create() == 1) {
                return 1;
            }
        } else if (temp.equals("TABLE")) {
            if (table_create() == 1) {
                return 1;
            }
        } else if (temp.equals("USER")) {
            if (user_create() == 1) {
                return 1;
            }
        } else if (temp.equals("UNIQUE") || temp.equals("CLUSTERED") || temp.equals("NONCLUSTERED") || temp.equals("INDEX")) {
            if (!temp.equals("INDEX")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("INDEX")) {
                    if (index_create() == 1) {
                        return 1;
                    }
                } else {
                    resultado += "Error, se esperaba un objeto. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                if (index_create() == 1) {
                    return 1;
                }
            }
        } else {
            resultado += "Error, se esperaba un objeto. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int database_create() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1DP() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int user_create() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("FOR")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("LOGIN")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("ID")) {
                        cont++;
                    } else {
                        resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, falta LOGIN. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta FOR. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int index_create() {
        if (objeto_nombreIC() == 1) {
            return 1;
        }

        if (objeto_nombreIN() == 1) {
            return 1;
        }

        if (objeto_nombreCL() == 1) {
            return 1;
        }

        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO_COMA")) {
            return 2;
        } else if (temp.equals("INCLUDE")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                if (objeto_nombreCL() == 1) {
                    return 1;
                }
                cont++;
            } else {
                resultado += "Error, se esperaba un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else {
            return 1;
        }

        return 2;
    }

    private int objeto_nombreIC() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1IC() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1IC() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1IC() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("ON")) {

        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreIN() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1IN() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1IN() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1IN() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("PARENTESIS_ABIERTO")) {

        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreCL() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1CL() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1CL() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1CL() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("PARENTESIS_CERRADO") || temp.equals("COMA")) {
            if (temp.equals("COMA")) {
                if (objeto_nombreCL() == 1) {
                    return 1;
                }
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int table_create() {
        if (objeto_nombreIN() == 1) {
            return 1;
        }
        /**
         * viene parentesis abierto por lo que tengo que hacer una seleccion y
         * ver si viene un campo o una constraint
         */
        if (seleccionar_add() == 1) {
            return 1;
        }//Si llega a este punto tendria que reconocer luego un punto y coma
        cont++;

        return 2;
    }

    private int seleccionar_add() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            cont--;     //para que vuelva a buscar el ID cuando ingrese al metodo
            if (add_column1() == 1) {
                return 1;
            }
        } else if (temp.equals("CONSTRAINT")) {
            if (add_constraint() == 1) {
                return 1;
            }
        } else if (temp.equals("FOREIGN") || temp.equals("PRIMARY")) {
            cont--; //Para que vuelva a reconocer
            if (add_constraint() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un campo o una constraint. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int add_constraint() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (constraint2() == 1) {
                return 1;
            }
        } else {
            cont--;
            if (constraint2() == 1) {
                return 1;
            }
        }

        temp = Tokens.get(cont);
        if (temp.equals("COMA")) {
            if (seleccionar_add() == 1) {
                return 1;
            }
        }
        return 2;
    }

    private int add_column1() {
        if (objeto_nombreAC() == 1) {
            return 1;
        }

        if (constraint_list() == 1) {
            return 1;
        }
        if (temp.equals("COMA")) {

        } else {
            cont++;
        }
        if (temp.equals("COMA")) {
            if (seleccionar_add() == 1) {
                return 1;
            }
        }
        return 2;
    }

    private int constraint_list() {
        if (constraint1() == 1) {
            return 1;
        }

        temp = Tokens.get(cont);
        if (temp.equals("PARENTESIS_CERRADO") || temp.equals("COMA")) {
            return 2;
        } else {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_CERRADO") || temp.equals("COMA")) {
                return 2;
            }
            cont--; //Tiene que venir otra palabra reservada de las constraint entonces resto otro
            if (constraint_list() == 1) {
                return 1;
            }
        }
        return 2;
    }

    private int constraint1() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("NULL") || temp.equals("UNIQUE") || temp.equals("NOT")) {
            if (temp.equals("NOT")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("NULL")) {
                    //todo ok
                } else {
                    resultado += "Error, falta un NULL. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            }
            //todo ok
        } else if (temp.equals("PRIMARY") || temp.equals("FOREIGN")) {
            String helper = temp;
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("KEY")) {
                if (helper.equals("FOREIGN")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("PARENTESIS_ABIERTO")) {
                        if (objeto_nombreKEY() == 1) {
                            return 1;
                        }

                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("REFERENCES")) {
                            if (objeto_nombreR() == 1) {
                                return 1;
                            }

                            if (objeto_nombreKEY() == 1) {
                                return 1;
                            }
                        } else {
                            resultado += "Error, falta una referencia. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }

                    } else {
                        resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                }
            } else {
                resultado += "Error, falta la palabra key. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("DEFAULT")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("STRING") || temp.equals("BIT") || temp.equals("INT") || temp.equals("FLOAT")) {
                //todo ok
            } else {
                resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("IDENTITY")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("INT") || temp.equals("BIT")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("COMA")) {
                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("INT") || temp.equals("BIT")) {
                            cont++;
                            temp = Tokens.get(cont);
                            if (temp.equals("PARENTESIS_CERRADO")) {
                                cont++;
                            } else {
                                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                                return 1;
                            }
                        } else {

                            resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }
                    } else {
                        resultado += "Error, falta una coma . Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("CHECK")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                if (expresion_logica() == 1) {
                    return 1;
                }
                cont++;
            }
        } else if (temp.equals("COMA") || temp.equals("PARENTESIS_CERRADO")) {
            //no viene ningun constraint
            if (temp.equals("PARENTESIS_CERRADO")) {
                cont++;
            }
        } else {
            if (temp.equals("NULL") || temp.equals("UNIQUE") || temp.equals("NOT") || temp.equals("CHECK") || temp.equals("IDENITY")
                    || temp.equals("DEFAULT") || temp.equals("PRIMARY") || temp.equals("FOREIGN")) {
                cont--;     //le resto uno para que vuelva a buscar la palabra del constraint
                if (constraint1() == 1) {
                    return 1;
                }
                return 2;
            } else {
                resultado += "Error, no existe el tipo de constraint. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        }
        return 2;
    }

    private int constraint2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("NULL") || temp.equals("UNIQUE") || temp.equals("NOT")) {
            if (temp.equals("NOT")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("NULL")) {
                    //todo ok
                } else {
                    resultado += "Error, falta un NULL. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            }
            //todo ok
        } else if (temp.equals("PRIMARY") || temp.equals("FOREIGN")) {
            String helper = temp;
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("KEY")) {
                if (helper.equals("FOREIGN")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("PARENTESIS_ABIERTO")) {
                        if (objeto_nombreKEY() == 1) {
                            return 1;
                        }

                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("REFERENCES")) {
                            if (objeto_nombreR() == 1) {
                                return 1;
                            }

                            if (objeto_nombreKEY() == 1) {
                                return 1;
                            }
                            cont++;
                        } else {
                            resultado += "Error, falta una referencia. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }

                    } else {
                        resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("PARENTESIS_ABIERTO")) {
                        if (primary_list() == 1) {
                            return 1;
                        }
                    }
                    cont++;
                }
            } else {
                resultado += "Error, falta la palabra key. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("DEFAULT")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("STRING") || temp.equals("BIT") || temp.equals("INT") || temp.equals("FLOAT")) {
                //todo ok
            } else {
                resultado += "Error, tipo de dato erroneo. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("IDENTITY")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("INT")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("COMA")) {
                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("INT")) {
                            cont++;
                            temp = Tokens.get(cont);
                            if (temp.equals("PARENTESIS_CERRADO")) {
                                cont++;
                            } else {
                                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                                return 1;
                            }
                        } else {

                            resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }
                    } else {
                        resultado += "Error, falta una coma . Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, se esperaba un numero. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("CHECK")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                if (expresion_logica() == 1) {
                    return 1;
                }
                cont++;
            }
        } else if (temp.equals("COMA") || temp.equals("PARENTESIS_CERRADO")) {
            if (temp.equals("PARENTESIS_CERRADO")) {
                cont++;
            }
            //no viene ningun constraint
        } else {
            resultado += "Error, no existe el tipo de constraint. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int primary_list() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (primary_list() == 1) {
                return 1;
            }
        } else if (temp.equals("PARENTESIS_CERRADO") || temp.equals("COMA")) {
            if (temp.equals("COMA")) {
                if (primary_list() == 1) {
                    return 1;
                }
            }
        } else {
            resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int view_create() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1V() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, nombre de vista incorrecto. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int UPDATE() {
        if (opciones_update() == 1) {
            return 1;
        }
        return 2;
    }

    private int opciones_update() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("TOP")) {
            if (expression() == 1) {
                return 1;
            }
            if (porcentaje2() == 1) {
                return 1;
            }

            if (objeto_nombreU() == 1) {
                return 1;
            }
        } else if (temp.equals("ID")) {
            if (objeto_nombre1U() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, falta FROM. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int porcentaje2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PERCENT")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                cont--;
            } else {
                resultado += "Error, falta FROM. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("ID")) {
            cont--;
        } else {
            resultado += "Error, falta FROM. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreU() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1U() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombre1U() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1U() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("SET")) {
            if (objeto_nombreCU() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreCU() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1CU() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1CU() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1CU() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("IGUAL")) {
            if (asignaciones() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int asignaciones() {
        if (asignaciones1() == 1) {
            return 1;
        }
        return 2;
    }

    private int asignaciones1() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("BIT") || temp.equals("STRING") || temp.equals("INT") || temp.equals("FLOAT") || temp.equals("ID")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("MAS") || temp.equals("MENOS") || temp.equals("MULT") || temp.equals("DIV") || temp.equals("RES")) {
                if (asignaciones1() == 1) {
                    return 1;
                }
            } else if (temp.equals("PARENTESIS_CERRADO")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("MAS") || temp.equals("MENOS") || temp.equals("MULT") || temp.equals("DIV") || temp.equals("RES")) {
                    if (asignaciones1() == 1) {
                        return 1;
                    }
                } else if (temp.equals("COMA")) {
                    if (objeto_nombreCU() == 1) {
                        return 1;
                    }
                } else {
                    cont--;
                }
            } else if (temp.equals("WHERE")) {
                if (condicionales() == 1) {
                    return 1;
                }
            } else if (temp.equals("COMA")) {
                if (objeto_nombreCU() == 1) {
                    return 1;
                }
            } else if (temp.equals("PUNTO_COMA")) {
                //todo ok
            } else {
                resultado += "Error, se esperaba un operador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("PARENTESIS_ABIERTO")) {
            if (asignaciones1() == 1) {
                return 1;
            }
        } else if (temp.equals("PARENTESIS_CERRADO") || temp.equals("WHERE")) {
            //todo ok que salga
            if (temp.equals("WHERE")) {
                if (condicionales() == 1) {
                    return 1;
                }
            } else {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("MAS") || temp.equals("MENOS") || temp.equals("MULT") || temp.equals("DIV") || temp.equals("RES")) {
                    if (asignaciones1() == 1) {
                        return 1;
                    }
                } else {
                    cont--;
                }
            }

        } else {
            resultado += "Error, tipo de dato invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int SELECT() {
        if (opcion_select() == 1) {
            return 1;
        }

        //Busco el FROM
        temp = Tokens.get(cont);
        if (temp.equals("FROM")) {
            if (origen() == 1) {
                return 1;
            }
        }
        return 2;
    }

    private int opcion_select() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("MULT")) {
            //todo ok
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("FROM")) {

            } else {
                resultado += "Error, falta FROM. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("DISTINCT") || temp.equals("ALL") || temp.equals("TOP")) {
            if (temp.equals("TOP")) {
                if (expression() == 1) {
                    return 1;
                }
            }
            if (columna_select() == 1) {
                return 1;
            }

        } else if (temp.equals("SUM") || temp.equals("COUNT") || temp.equals("AVG") || temp.equals("MAX") || temp.equals("MIN")
                || temp.equals("BIT") || temp.equals("STRING") || temp.equals("INT") || temp.equals("FLOAT") || temp.equals("ID")) {
            cont--;     //para que vuelva a reconocer el nombre, dato o funcion
            if (columna_select() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba una seleccion de columnas. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int columna_select() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("SUM") || temp.equals("COUNT") || temp.equals("AVG") || temp.equals("MAX") || temp.equals("MIN")) {
            if (funciones() == 1) {
                return 1;
            }

        } else if (temp.equals("BIT") || temp.equals("STRING") || temp.equals("INT") || temp.equals("FLOAT")
                || temp.equals("ID") || temp.equals("PARENTESIS_ABIERTO")) {
            cont--;     //resto para que reconozca otra vez
            if (opcion_columna() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, tipo de columna incorrecta. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int opcion_columna() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("BIT") || temp.equals("STRING") || temp.equals("INT") || temp.equals("FLOAT") || temp.equals("ID")) {
            String helper = temp;
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("MAS") || temp.equals("MENOS") || temp.equals("MULT") || temp.equals("DIV") || temp.equals("RES") || temp.equals("IGUAL")) {
                if (opcion_columna() == 1) {
                    return 1;
                }
            } else if (temp.equals("PARENTESIS_CERRADO")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("MAS") || temp.equals("MENOS") || temp.equals("MULT") || temp.equals("DIV") || temp.equals("RES") || temp.equals("IGUAL")) {
                    if (opcion_columna() == 1) {
                        return 1;
                    }
                } else if (temp.equals("AS")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("ID") || temp.equals("STRING")) {
                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("COMA")) {
                            if (columna_select() == 1) {
                                return 1;
                            }
                        } else if (temp.equals("FROM")) {

                        } else {
                            resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }
                    } else {
                        resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else if (temp.equals("FROM")) {

                } else {
                    cont--;
                }
            } else if (temp.equals("AS")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("ID") || temp.equals("STRING")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("COMA")) {
                        if (columna_select() == 1) {
                            return 1;
                        }
                    } else if (temp.equals("FROM")) {

                    } else {
                        resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else if (temp.equals("FROM")) {
                //todo ok
            } else if (temp.equals("COMA")) {
                if (columna_select() == 1) {
                    return 1;
                }
            } else if (temp.equals("PUNTO") && helper.equals("ID")) {
                cont--;
                if (objeto_nombre1C() == 1) {
                    return 1;
                }
                if (temp.equals("FROM")) {
                    return 2;
                }

                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("AS")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("ID") || temp.equals("STRING")) {
                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("COMA")) {
                            if (columna_select() == 1) {
                                return 1;
                            }
                        } else if (temp.equals("FROM")) {

                        } else {
                            resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }
                    } else {
                        resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else if (temp.equals("FROM")) {
                    //todo ok
                } else if (temp.equals("COMA")) {
                    if (columna_select() == 1) {
                        return 1;
                    }
                } else {
                    resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }

            } else {
                resultado += "Error, se esperaba un operador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("PARENTESIS_ABIERTO")) {
            if (opcion_columna() == 1) {
                return 1;
            }
        } else if (temp.equals("PARENTESIS_CERRADO") || temp.equals("FROM")) {
            //todo ok que salga
            if (temp.equals("FROM")) {
                //todo ok
            } else {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("MAS") || temp.equals("MENOS") || temp.equals("MULT") || temp.equals("DIV") || temp.equals("RES") || temp.equals("IGUAL")) {
                    if (opcion_columna() == 1) {
                        return 1;
                    }
                } else if (temp.equals("AS")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("ID") || temp.equals("STRING")) {
                        cont++;
                        temp = Tokens.get(cont);
                        if (temp.equals("COMA")) {
                            if (columna_select() == 1) {
                                return 1;
                            }
                        } else if (temp.equals("FROM")) {

                        } else {
                            resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                            return 1;
                        }
                    } else {
                        resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else if (temp.equals("FROM")) {

                } else {
                    cont--;
                }
            }

        } else {
            resultado += "Error, tipo de dato invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombre1C() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1C() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("COMA")) {
            if (columna_select() == 1) {
                return 1;
            }
        } else if (temp.equals("AS")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID") || temp.equals("STRING")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("COMA")) {
                    if (columna_select() == 1) {
                        return 1;
                    }
                } else if (temp.equals("FROM")) {

                } else {
                    resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else {
                resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("FROM")) {

        } else {
            resultado += "Error, falta un punto o un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int funciones() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PARENTESIS_ABIERTO")) {
            if (valor() == 1) {
                return 1;
            }

            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("COMA")) {
                if (columna_select() == 1) {
                    return 1;
                }
            } else if (temp.equals("AS")) {
                cont++;
                temp = Tokens.get(cont);
                if (temp.equals("ID") || temp.equals("STRING")) {
                    cont++;
                    temp = Tokens.get(cont);
                    if (temp.equals("COMA")) {
                        if (columna_select() == 1) {
                            return 1;
                        }
                    } else if (temp.equals("FROM")) {

                    } else {
                        resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                        return 1;
                    }
                } else {
                    resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                    return 1;
                }
            } else if (temp.equals("FROM")) {

            } else {
                resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else {
            resultado += "Error, se necesita un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int valor() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("MULT") || temp.equals("BIT") || temp.equals("INT")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_CERRADO")) {
                //todo ok
            } else {
                resultado += "Error, falta un parentesis. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("DISTINCT")) {
            if (objeto_nombreKEY() == 1) {
                return 1;
            }
        } else if (temp.equals("ID")) {
            if (objeto_nombre1KEY() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se puede operar con la funcion de agregacion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int origen() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1OT() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreOT() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1OT() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1OT() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1OT() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("AS")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID") || temp.equals("STRING")) {
                if (objeto_nombre1OT() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("ID")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("COMA")) {
                if (objeto_nombreOT() == 1) {
                    return 1;
                }
            } else if (temp.equals("PUNTO_COMA")) {

            } else if (temp.equals("WHERE")) {
                if (condicionales2() == 1) {
                    return 1;
                }
            } else if (temp.equals("GROUP")) {
                if (agrupado() == 1) {
                    return 1;
                }
            } else if (temp.equals("HAVING")) {
                if (teniendo() == 1) {
                    return 1;
                }
            } else if (temp.equals("ORDER")) {
                if (ordenado() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("COMA")) {
            if (objeto_nombreOT() == 1) {
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {
            //todo ok
        } else if (temp.equals("INNER") || temp.equals("RIGHT") || temp.equals("LEFT") || temp.equals("FULL") || temp.equals("JOIN")) {
            if (temp.equals("INNER")) {
                if (join() == 1) {
                    return 1;
                }
            } else if (temp.equals("JOIN")) {
                cont--;
                if (join() == 1) {
                    return 1;
                }
            } else {
                if (tipo_join() == 1) {
                    return 1;
                }
            }
        } else if (temp.equals("WHERE")) {
            if (condicionales2() == 1) {
                return 1;
            }
        } else if (temp.equals("GROUP")) {
            if (agrupado() == 1) {
                return 1;
            }
        } else if (temp.equals("HAVING")) {
            if (teniendo() == 1) {
                return 1;
            }
        } else if (temp.equals("ORDER")) {
            if (ordenado() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int tipo_join() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("OUTER")) {
            if (join() == 1) {
                return 1;
            }
        } else if (temp.equals("JOIN")) {
            cont--;
            if (join() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, falta la palabra JOIN. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int join() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("JOIN")) {
            if (objeto_nombreJT() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, falta la palabra JOIN. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreJT() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1JT() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombre1JT() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1JT() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("AS")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID") || temp.equals("STRING")) {
                if (objeto_nombre1JT() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("ID")) {
            if (objeto_nombre1JT() == 1) {
                return 1;
            }
        } else if (temp.equals("ON")) {
            if (objeto_nombreCU2() == 1) {
                return 1;
            }

        } else {
            resultado += "Error, la palabra ON. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreCU2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1CU2() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1CU2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1CU2() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }

        } else if (temp.equals("IGUAL")) {
            if (objeto_nombreJ() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreJ() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1J() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;

    }

    private int objeto_nombre1J() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1J() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("INNER") || temp.equals("RIGHT") || temp.equals("LEFT") || temp.equals("FULL") || temp.equals("JOIN")) {
            if (temp.equals("INNER")) {
                if (join() == 1) {
                    return 1;
                }
            } else if (temp.equals("JOIN")) {
                cont--;
                if (join() == 1) {
                    return 1;
                }
            } else {
                if (tipo_join() == 1) {
                    return 1;
                }
            }
        } else if (temp.equals("PUNTO_COMA")) {
            //todo ok
        } else if (temp.equals("WHERE")) {
            if (condicionales2() == 1) {
                return 1;
            }
        } else if (temp.equals("GROUP")) {
            if (agrupado() == 1) {
                return 1;
            }
        } else if (temp.equals("HAVING")) {
            if (teniendo() == 1) {
                return 1;
            }
        } else if (temp.equals("ORDER")) {
            if (ordenado() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int condicionales2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (operadores1() == 1) {
                return 1;
            }

            if (tipo_dato1() == 1) {
                return 1;
            }

            if (condicionales12() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, se esperaba un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int condicionales12() {
        if (operadores_logicos12() == 1) {
            return 1;
        }
        return 2;
    }

    private int operadores_logicos12() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("AND") || temp.equals("AND_O") || temp.equals("OR") || temp.equals("OR_O")) {
            if (condicionales2() == 1) {
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {
            //todo ok
        } else if (temp.equals("GROUP")) {
            if (agrupado() == 1) {
                return 1;
            }
        } else if (temp.equals("HAVING")) {
            if (teniendo() == 1) {
                return 1;
            }
        } else if (temp.equals("ORDER")) {
            if (ordenado() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, operador logico no valido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int agrupado() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("BY")) {
            if (objeto_nombreG() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, falta BY. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreG() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1G() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombre1G() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1G() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("COMA")) {
            if (objeto_nombreG() == 1) {
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {
            //todo ok
        } else if (temp.equals("HAVING")) {
            if (teniendo() == 1) {
                return 1;
            }
        } else if (temp.equals("ORDER")) {
            if (ordenado() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int teniendo() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("SUM") || temp.equals("COUNT") || temp.equals("AVG") || temp.equals("MAX") || temp.equals("MIN")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                if (valor() == 1) {
                    return 1;
                }

                if (operadores1() == 1) {
                    return 1;
                }

                if (tipo_dato1() == 1) {
                    return 1;
                }

                if (condicionales123() == 1) {
                    return 1;
                }

            } else {

            }
        } else if (temp.equals("ID")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PUNTO")) {
                if (teniendo() == 1) {
                    return 1;
                }
            } else {
                cont--;
                if (operadores1() == 1) {
                    return 1;
                }

                if (tipo_dato1() == 1) {
                    return 1;
                }

                if (condicionales123() == 1) {
                    return 1;
                }
            }

        } else {
            resultado += "Error, se esperaba una funcion de agregacion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int condicionales123() {
        if (operadores_logicos123() == 1) {
            return 1;
        }
        return 2;
    }

    private int operadores_logicos123() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("AND") || temp.equals("AND_O") || temp.equals("OR") || temp.equals("OR_O")) {
            if (teniendo() == 1) {
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {
            //todo ok
        } else if (temp.equals("ORDER")) {
            if (ordenado() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, operador logico no valido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int ordenado() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("BY")) {
            if (objeto_nombreOB() == 1) {
                return 1;
            }
        } else {
            resultado += "Error, falta BY. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombreOB() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("ID")) {
            if (objeto_nombre1OB() == 1) {
                return 1;
            }
        } else if (temp.equals("SUM") || temp.equals("COUNT") || temp.equals("AVG") || temp.equals("MAX") || temp.equals("MIN")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("PARENTESIS_ABIERTO")) {
                if (valor() == 1) {
                    return 1;
                }
                if (objeto_nombre1OB() == 1) {
                    return 1;
                }
            }
        } else {
            resultado += "Error, identificador invalido. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int objeto_nombre1OB() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("PUNTO")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("ID")) {
                if (objeto_nombre1OB() == 1) {
                    return 1;
                }
            } else {
                resultado += "Error, falta un identificador. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("COMA")) {
            if (objeto_nombreOB() == 1) {
                return 1;
            }
        } else if (temp.equals("ASC") || temp.equals("DESC")) {
            cont++;
            temp = Tokens.get(cont);
            if (temp.equals("COMA")) {
                if (objeto_nombreOB() == 1) {
                    return 1;
                }
            } else if (temp.equals("PUNTO_COMA")) {
                //todo ok
            } else {
                resultado += "Error, falta una coma. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
                return 1;
            }
        } else if (temp.equals("PUNTO_COMA")) {
            //todo ok
        } else {
            resultado += "Error, no se realizo ninguna accion. Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }

    private int formato() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("")) {

        } else {
            resultado += "Error, . Linea: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).fila + " Columna: " + detalles.get(cont_general - (Tokens.size() - cont) + 1).columna + "\n";
            return 1;
        }
        return 2;
    }
}
