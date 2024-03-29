/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author eecon
 */
public class minisql extends javax.swing.JFrame {

    String root;
    File file;
    FileManager manager = new FileManager();

    /**
     * Creates new form minisql
     */
    public minisql() {
        initComponents();
        startLexer();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBtnSearchFile = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxtAFileContent = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jBtnAnalyze = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jBtnSearchFile.setText("Buscar archivo");
        jBtnSearchFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSearchFileActionPerformed(evt);
            }
        });

        jTxtAFileContent.setEditable(false);
        jTxtAFileContent.setColumns(20);
        jTxtAFileContent.setRows(5);
        jScrollPane1.setViewportView(jTxtAFileContent);

        jLabel1.setText("Contenido de archivo:");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jBtnAnalyze.setText("Realizar analisis");
        jBtnAnalyze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAnalyzeActionPerformed(evt);
            }
        });

        jLabel2.setText("Analisis de tokens:");

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\eecon\\OneDrive\\Escritorio\\URL\\2019\\Segundo ciclo\\Compis\\Row_opt.png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jBtnSearchFile))
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(356, 356, 356))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnAnalyze)
                        .addGap(220, 220, 220))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnSearchFile)
                            .addComponent(jBtnAnalyze))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(113, 113, 113)
                                .addComponent(jLabel3)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSearchFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSearchFileActionPerformed
        // TODO add your handling code here:
        JFileChooser file_chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de Texto", "txt");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Archivo SQL", "sql");
        file_chooser.addChoosableFileFilter(filter);
        file_chooser.addChoosableFileFilter(filter2);
        int value = file_chooser.showOpenDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
            file = file_chooser.getSelectedFile();
            root = file.getPath();

            String text = "";
            try {
                text = manager.readFile(root);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(minisql.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!text.isEmpty()) {
                jTxtAFileContent.setText(text);
                try {
                    lexicalAnalyzer();
                } catch (IOException ex) {
                    Logger.getLogger(minisql.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }//GEN-LAST:event_jBtnSearchFileActionPerformed

    private void jBtnAnalyzeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAnalyzeActionPerformed
        if (jTxtAFileContent.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un archivo primero");
        } else {

        }
    }//GEN-LAST:event_jBtnAnalyzeActionPerformed

    public void startLexer() {
        String absolutePath = new File(".").getAbsolutePath();
        absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
        absolutePath += "src\\Classes\\Lexer.flex";
        File lexerFile = new File(absolutePath);
        jflex.Main.generate(lexerFile);
    }

    public void lexicalAnalyzer() throws IOException {
        validateTokens();
        String file_name = file.getName();
        String aux_root = root.substring(0, root.length() - file_name.length());
        file_name = file_name.substring(0, file_name.length() - 4);
        manager.writeFile(file_name, jTextArea1.getText(), root, aux_root, "out");
        JOptionPane.showMessageDialog(null, "El archivo " + file_name + " .out se escribió en\nla ubicación del archivo original");
    }

    public void validateTokens() throws FileNotFoundException, IOException {
        Reader reader = new BufferedReader(new FileReader(file));

        Lexer lexer = new Lexer(reader);
        String resultado = "";
        while (true) {
            Token token = lexer.yylex();
            if (token == null) {
                jTextArea1.setText(resultado);
                break;
            }
            switch (token) {
                case ERROR:
                    resultado += "Error caracter invalido, " + lexer.lexeme + "  Line: " + (lexer.getLine() + 1) + "   PrimeraColumna: " + lexer.getColumn() + "   UltimaColumna: " + (lexer.getColumn() + lexer.lexeme.length()) + "\n";
                    break;
                case ERROR_F:
                    resultado += "Error float invalido, " + lexer.lexeme + "  Line: " + (lexer.getLine() + 1) + "   PrimeraColumna: " + lexer.getColumn() + "   UltimaColumna: " + (lexer.getColumn() + lexer.lexeme.length()) + "\n";
                    break;
                case ID:
                    String string = "";
                    if (lexer.lexeme.length() > 31) {
                        string = lexer.lexeme.substring(0, 31);
                        resultado += "Error de truncado, " + token + " " + string + "  Line: " + (lexer.getLine() + 1) + "   FirstCol: " + lexer.getColumn() + "   LastCol: " + (lexer.getColumn() + lexer.lexeme.length()) + "\n";
                    } else {
                        resultado += "TOKEN: " + token + " " + lexer.lexeme + "  Line: " + (lexer.getLine() + 1) + "   FirstCol: " + lexer.getColumn() + "   LastCol: " + (lexer.getColumn() + lexer.lexeme.length()) + "\n";
                    }
                    break;
                case COMENTARIO_M:
                    resultado += "Error, comentario multilinea " + token + "  Line: " + (lexer.getLine() + 1) + "   FirstCol: " + lexer.getColumn() + "   LastCol: " + (lexer.getColumn() + lexer.lexeme.length()) + "\n";
                    int firstNewLinePosition = 0;
                    for (int i = 0; i < lexer.lexeme.length(); i++) {
                        if (lexer.lexeme.charAt(i) == '\n') {
                            firstNewLinePosition = i;
                            break;
                        }
                    }

                    if (firstNewLinePosition != 0) {
                        lexer.yypushback(lexer.lexeme.length() - firstNewLinePosition);
                    }
                    break;
                default:
                    resultado += "TOKEN: " + token + "  Line: " + (lexer.getLine() + 1) + "   FirstCol: " + lexer.getColumn() + "   LastCol: " + (lexer.getColumn() + lexer.lexeme.length()) + "\n";
                    break;
            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(minisql.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(minisql.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(minisql.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(minisql.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new minisql().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAnalyze;
    private javax.swing.JButton jBtnSearchFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTxtAFileContent;
    // End of variables declaration//GEN-END:variables
}
