package controller;

import model.Automaton;
import model.State;
import model.Transition;

import java.sql.SQLOutput;
import java.util.*;

public class NdfaConverter {
    private HashMap<State, HashMap<String, Set<State>>> ndfaAutomatonMatrix;
    private HashMap<State, HashMap<String, State>> dfaMatrix;


    public Automaton getDfaFromNdfa(Automaton ndfaAutomaton) {
        if (ndfaAutomaton.getAutomatonType().equals(Constants.NDFA)) {
            return convertNdfaAutomatonToDfa(ndfaAutomaton);
        }
        return ndfaAutomaton;
    }

    private Automaton convertNdfaAutomatonToDfa(Automaton ndfaAutomaton) {
        Automaton dfaAutomaton = new Automaton();
        fillNdfaAutomatonMatrix(ndfaAutomaton);
        buildDfaMatrix(ndfaAutomaton);


        return null;
    }

    private void buildDfaMatrix(Automaton ndfaAutomaton) {
        List<State> dfaStates = new ArrayList<>(ndfaAutomaton.getInitialStates());
        dfaMatrix = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<String> nameAux = new ArrayList<>();
        for (int state = 0; state < dfaStates.size(); state++) {
            State currentState = dfaStates.get(state);
            for (String terminal : ndfaAutomaton.getAlphabet()) {
                HashMap<String, State> value = new HashMap<>();
                State newState = ndfaAutomatonMatrix.containsKey(currentState) ?
                        getIds(ndfaAutomatonMatrix.get(currentState).get(terminal)) :
                        getIds(currentState.getName(), terminal);

                if (!nameAux.contains(newState.getName())) {
                    nameAux.add(newState.getName());
                    dfaStates.add(newState);
                    value.put(terminal, newState);
                }

                if (!names.contains(currentState.getName())) {
                    dfaMatrix.put(currentState, value);
                    names.add(currentState.getName());
                }
            }
            System.out.println(dfaStates.toString());
        }
        dfaStates.remove(ndfaAutomaton.getInitialStates());
    }

    private void fillNdfaAutomatonMatrix(Automaton ndfaAutomaton) {
        ndfaAutomatonMatrix = new HashMap<>();
        for (State state : ndfaAutomaton.getStates()) {
            HashMap<String, Set<State>> symbolsMap = new HashMap<>();
            for (String terminal : ndfaAutomaton.getAlphabet()) {
                symbolsMap.put(terminal, getStatesByTerminal(state, terminal, ndfaAutomaton));
            }
            ndfaAutomatonMatrix.put(state, symbolsMap);
        }
    }

    private State getIds(String name, String terminal) {
        List<String> ids = Arrays.asList(name.replace("{", "").replace("}", "").split(","));
        Set<State> states = new HashSet<>();
        for (Map.Entry<State, HashMap<String, Set<State>>> i : ndfaAutomatonMatrix.entrySet()) {
            if (ids.contains(i.getKey().getId()) && i.getValue().get(terminal).size() > 0) {
                states.addAll(new ArrayList<>(i.getValue().get(terminal)));
            }
        }
        return getIds(states);
    }

    private State getIds(Set<State> states) {
        String ids = "";
        State state = new State();
        if (states == null) return null;
        for (State s : states) {
            state.addMergedState(s);
            ids += s.getId() + ",";
        }
        state.setName(String.format("{%s}", ids.substring(0, ids.length() - 1)));
        state.setId(ids);
        return state;
    }

    private Set<State> getStatesByTerminal(State state, String terminal, Automaton ndfaAutomaton) {
        Set<Transition> transitions = ndfaAutomaton.getTransitionsByStateAndTerminal(state, terminal);
        Set<State> states = new HashSet<>();
        for (Transition t : transitions) {
            states.add(ndfaAutomaton.findStateById(t.getToId()));
        }
        return states;
    }


}
