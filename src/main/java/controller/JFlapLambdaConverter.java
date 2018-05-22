package controller;

import model.Automaton;
import model.AutomatonStructure;


import java.util.List;

public class JFlapLambdaConverter {

    public static void main(String[] args) {
        //JFileChooser chooser = new JFileChooser();
        //chooser.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos JFLAP4", ".jff"));
        //int returned = chooser.showOpenDialog(null);
        //if (returned == JFileChooser.APPROVE_OPTION) {
        AutomatonStructure structure = new AutomatonReader().readAutomatonFromFile("/home/gunna/JFLAP-Lambda-Converter/src/main/java/controller/teste2.jff");
        Automaton automaton = new NdfaConverter().getDfaFromNdfa(structure.getAutomaton());
        new DfaAutomatonSimulator().makeSimulation(automaton, "1111111010", new DfaAutomatonSimulator.SimulationListener() {
            @Override
            public void onAccept(List<String> simulation) {
                System.out.println("A entrada é reconhecida pelo automato!");
            }

            @Override
            public void onError(String msg, List<String> simulation) {
                System.out.println(msg);
            }
        });
        new AutomatonWriter().getJflapFileContentFromAutomaton(automaton);
        //}

    }
}
