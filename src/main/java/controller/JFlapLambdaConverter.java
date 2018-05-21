package controller;

import model.AutomatonStructure;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JFlapLambdaConverter {

    public static void main(String[] args) {
        //JFileChooser chooser = new JFileChooser();
        //chooser.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos JFLAP4", ".jff"));
        //int returned = chooser.showOpenDialog(null);
        //if (returned == JFileChooser.APPROVE_OPTION) {
        AutomatonStructure structure = new AutomatonReader().readAutomatonFromFile("/home/gunna/JFLAP-Lambda-Converter/src/main/java/controller/teste2.jff");
        new NdfaConverter().getDfaFromNdfa(structure.getAutomaton());
        //}

    }
}
