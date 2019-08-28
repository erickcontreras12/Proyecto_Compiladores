/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
    
    public boolean writeFile(String file_name, String content, String error, String root, String ext) {
        File file = new File(root + file_name + "." + ext);
        if (file.exists()) {
            file.delete();
        }

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();

            return true;
        } catch (IOException ex) {
            error = ex.getMessage();
            return false;
        }
    }
}
