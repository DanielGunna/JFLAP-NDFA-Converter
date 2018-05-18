package controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JFlapLambdaConverter {

    public static  void  main(String[] args){
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos JFLAP4", ".jff"));
        int returned = chooser.showOpenDialog(null);
        if (returned == JFileChooser.APPROVE_OPTION) {
            new AutomatonReader().readAutomatonFromFile(chooser.getSelectedFile().getAbsolutePath());
        }

    }
}
