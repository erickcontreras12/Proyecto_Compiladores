/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author eecon
 */
public class FileManager {

    public String readFile(String root) throws FileNotFoundException {
        String aux = "";
        File file = new File(root);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            String line = reader.readLine();
            while (line != null) {
                aux += line + "\n";
                line = reader.readLine();
            }
        } catch (IOException e) {

        }

        return aux;
    }
}
