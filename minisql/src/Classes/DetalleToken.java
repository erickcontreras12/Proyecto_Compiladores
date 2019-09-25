/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author eecon
 */
public class DetalleToken {
    public int columna, fila;
    public String descripcion;
    
    public DetalleToken(int c, int f, String desc){
        columna = c;
        fila = f;
        descripcion = desc;
    }
}
