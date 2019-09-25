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
    String temp;
    ArrayList<String> Tokens = new ArrayList();
    ArrayList<String> errores = new ArrayList();
    ArrayList<DetalleToken> detalles = new ArrayList();

    public syntaxAnalyzer(ArrayList<String> Tokens, ArrayList<DetalleToken> Detalle) {
        cont = 0;
        this.Tokens = Tokens;
        detalles = Detalle;
    }

    /**
     *
     * @return
     */
    public String makeAnalysis() {
        String resultado = "";
        int returned_value = 0;
        while (cont < Tokens.size()) {
            String aux_token = Tokens.get(cont);

            if (returned_value == 0) {
                switch (aux_token) {
                    case "INSERT":
                        returned_value = G();
                        cont--;
                        break;
                    default:
                        break;
                }
            } else if (returned_value == 1) {
                if (aux_token.equals("INSERT") || aux_token.equals("SELECT")) {
                    //Le quito uno para que vuelva a leer el primer token de una sentencia nueva
                    cont--;
                    //Regreso el valor a 0
                    returned_value = 0;
                }
            } else {
                if (aux_token.equals("PUNTO_COMA") || aux_token.equals("GO")) {
                    System.out.println("Si se pudo");
                } else {
                    //Agregar error
                }
            }

            cont++;
        }
        return resultado;
    }

    /**
     *
     * @return devuelve un entero (2) para saber si todo va bien
     */
    private int G() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("INTO") || temp.equals("ID")) {
            if (nombre() == 1) {
                return 1;
            }

            if (B2() == 1) {
                return 1;
            }
        } else {
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
        } else {
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
                //error de id
                return 1;
            }
        } else if (temp.equals("PARENTESIS_ABIERTO")) {
            if (A2() == 1) {
                return 1;
            }
        } else {
            //Error de identificador
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
                //error de id
                return 1;
            }
        } else if (temp.equals("PARENTESIS_ABIERTO")) {
            if (A2() == 1) {
                return 1;
            }
            if (B2() == 1) {
                return 1;
            }
        } else {
            //Error de idetificador
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
            //Error de identificador
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
        }else if(temp.equals("PARENTESIS_CERRADO")){
        
        }else {
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
            //Falta una coma
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
                //Todo ok xd
            } else {
                //Missing words VALUES
                return 1;
            }
        } else if (temp.equals("VALUES")) {
            if (D2() == 1) {
                return 1;
            }
        } else {
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
            return 1;
        }
        return 2;
    }

    private int E2() {
        cont++;
        temp = Tokens.get(cont);
        if (temp.equals("STRING") || temp.equals("BIT") || temp.equals("INT") || temp.equals("FLOAT")) {
            //entre a F2 en teoria, que solo tiene los tipos de datos
            if (E2_prima() == 1) {
                return 1;
            }

        } else {
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
            return 1;
        }
        return 2;
    }
}
