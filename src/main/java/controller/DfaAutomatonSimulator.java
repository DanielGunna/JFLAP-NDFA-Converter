package controller;

import model.Automaton;
import model.State;
import model.Transition;

import java.util.ArrayList;
import java.util.Set;

public class DfaAutomatonSimulator {

    public interface SimulationListener {
        void onAccept();

        void onError(String msg);
    }

    public void makeSimulation(Automaton automaton, String sentence, SimulationListener listener) {
        State currentState = automaton.getInitialState();
        String read = "";
        for (int x = 0; x < sentence.length(); x++) {
            String input = String.valueOf(sentence.charAt(x));
            read += input;
            if (!automaton.getAlphabet().contains(input)) {
                listener.onError("Entrada rejeitada! Não pertence a linguagem reconhecida pelo automato!");
                return;
            } else {
                Set<Transition> transitions = automaton.getTransitionsByStateAndTerminal(currentState, input);
                if (transitions.size() > 1) {
                    listener.onError("Automato não é um DFA!");
                    return;
                } else if (transitions.size() == 0) {
                    if (currentState.isFinalState() && read.length() == sentence.length()) {
                        listener.onAccept();
                    } else {
                        listener.onError("Entrada rejeitada! Não pertence a linguagem reconhecida pelo automato!");
                        return;
                    }
                } else if (transitions.size() == 1) {
                    Transition transition = new ArrayList<>(transitions).get(0);
                    State oldState = currentState;
                    currentState = automaton.findStateById(transition.getToId());
                    System.out.println(String.format("[%s,%s] -> %s ", oldState.getName(), read, currentState.getName()));
                }
            }
        }
        listener.onAccept();
    }


}
