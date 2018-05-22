package controller;

import model.Automaton;
import model.State;
import model.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DfaAutomatonSimulator {

    public interface SimulationListener {
        void onAccept(String simulation);

        void onError(String msg, String simulation);
    }

    public void makeSimulation(Automaton automaton, String sentence, SimulationListener listener) {
        State currentState = automaton.getInitialState();
        String read = "";
        String simulation = "";
        for (int x = 0; x < sentence.length(); x++) {
            String input = String.valueOf(sentence.charAt(x));
            read += input;
            if (automaton.getAlphabet().contains(input)) {
                Set<Transition> transitions = automaton.getTransitionsByStateAndTerminal(currentState, input);
                if (transitions.size() > 1) {
                    listener.onError("Automato não é um DFA!", simulation);
                    return;
                } else if (transitions.size() == 0) {
                    if (!(currentState.isFinalState() && read.length() == sentence.length())) {
                        listener.onError("Entrada rejeitada! Não pertence a linguagem reconhecida pelo automato!", simulation);
                        return;
                    }
                } else if (transitions.size() == 1) {
                    Transition transition = new ArrayList<>(transitions).get(0);
                    State oldState = currentState;
                    currentState = automaton.findStateById(transition.getToId());
                    String simulationStep = String.format("[%s,%s] -> %s ", oldState.getName(), read, currentState.getName());
                    System.out.println(simulationStep);
                    simulation += simulationStep + "\n";
                }

            } else {
                listener.onError("Entrada rejeitada! Não pertence a linguagem reconhecida pelo automato!", simulation);
                return;
            }
        }
        if (currentState.isFinalState())
            listener.onAccept(simulation);
        else
            listener.onError("Entrada rejeitada! Não pertence a linguagem reconhecida pelo automato!", simulation);
    }


}
