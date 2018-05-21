package controller;

import model.Automaton;
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
        Automaton automaton = new NdfaConverter().getDfaFromNdfa(structure.getAutomaton());
        new DfaAutomatonSimulator().makeSimulation(automaton, "0101", new DfaAutomatonSimulator.SimulationListener() {
            @Override
            public void onAccept() {
                System.out.println("A entrada é reconhecida pelo automato!");
            }

            @Override
            public void onError(String msg) {
                System.out.println(msg);
            }
        });
        //}

    }
}
