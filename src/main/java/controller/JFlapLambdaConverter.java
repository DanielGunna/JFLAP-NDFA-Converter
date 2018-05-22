package controller;

import model.Automaton;
import model.AutomatonStructure;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.List;

public class JFlapLambdaConverter {
    static boolean stop = false;
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos JFLAP4", ".jff"));
        int returned = chooser.showOpenDialog(null);
        if (returned == JFileChooser.APPROVE_OPTION) {
            AutomatonStructure structure = new AutomatonReader().readAutomatonFromFile(chooser.getSelectedFile().getAbsolutePath());
            Automaton automaton = new NdfaConverter().getDfaFromNdfa(structure.getAutomaton());

            while (!stop) {
                String input = JOptionPane.showInputDialog(null, "Insira a entrada:");
                new DfaAutomatonSimulator().makeSimulation(automaton, input, new DfaAutomatonSimulator.SimulationListener() {
                    @Override
                    public void onAccept(String simulation) {
                        if (JOptionPane.showConfirmDialog(null, simulation + "\nDeseja salvar o DFA gerado?") == JOptionPane.OK_OPTION) {
                            JFileChooser chooser = new JFileChooser();
                            chooser.setCurrentDirectory(new java.io.File("."));
                            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                            chooser.setAcceptAllFileFilterUsed(false);
                            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                AutomatonWriter writer = new AutomatonWriter();
                                try {
                                    writer.saveJflapFile(writer.getJflapFileContentFromAutomaton(automaton),chooser.getCurrentDirectory().getAbsolutePath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(String msg, String simulation) {
                        if (!(JOptionPane.showConfirmDialog(null, simulation + "\nDeseja continuar?") == JOptionPane.OK_OPTION)){
                            stop = true;
                        }
                    }
                });
            }


        }

    }
}
