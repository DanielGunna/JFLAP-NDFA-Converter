package controller;

import model.Automaton;
import model.State;
import model.Transition;

import java.util.*;

import static controller.Constants.DFA;

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
        List<State> dfaStates = buildDfaMatrixAndGetDfaStates(ndfaAutomaton);
        List<Transition> dfaTransitions = getTransitions(dfaStates);
        dfaAutomaton.setStates(new HashSet<>(dfaStates));
        dfaAutomaton.setType(DFA);
        dfaAutomaton.setDfaMatrix(dfaMatrix);
        dfaAutomaton.setAlphabet(ndfaAutomaton.getAlphabet());
        dfaAutomaton.setTransitions(new HashSet<>(dfaTransitions));
        dfaAutomaton.fillInitialAndFinalStates();
        return dfaAutomaton;
    }

    private List<Transition> getTransitions(List<State> dfaStates) {
        List<Transition> transitions = new ArrayList<>();
        for (Map.Entry<State, HashMap<String, State>> i : dfaMatrix.entrySet()) {
            for (Map.Entry<String, State> j : i.getValue().entrySet()) {
                transitions.add(new Transition(j.getKey(), i.getKey().getId(), j.getValue().getId()));
            }
        }
        return transitions;
    }

    private List<State> buildDfaMatrixAndGetDfaStates(Automaton ndfaAutomaton) {
        List<State> dfaStates = new ArrayList<>();
        dfaStates.add(ndfaAutomaton.getInitialState());
        dfaMatrix = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<String> nameAux = new ArrayList<>();
        for (int state = 0; state < dfaStates.size(); state++) {
            State currentState = dfaStates.get(state);
            HashMap<String, State> value = new HashMap<>();
            for (String terminal : ndfaAutomaton.getAlphabet()) {
                State newState = ndfaAutomatonMatrix.containsKey(currentState) ?
                        getMergedState(ndfaAutomatonMatrix.get(currentState).get(terminal)) :
                        getMergedState(currentState.getName(), terminal);
                if (newState != null) {
                    if (!nameAux.contains(newState.getName())) {
                        nameAux.add(newState.getName());
                        dfaStates.add(newState);
                        value.put(terminal, newState);
                    } else {
                        value.put(terminal, newState);
                    }
                }
            }

            if (!names.contains(currentState.getName())) {
                dfaMatrix.put(currentState, value);
                names.add(currentState.getName());
            } else {
                dfaMatrix.get(currentState).putAll(value);
            }
        }

        return verifyFinalAndInitialStates(removeInitials(dfaStates, nameAux), ndfaAutomaton.getInitialState());
    }

    private List<State> verifyFinalAndInitialStates(List<State> states, State initialState) {
        for (State state : states) {
            for (State s : state.getMergedStates()) {
                if (s.isFinalState())
                    state.setFinalState(true);
                if (s.getId().equals(initialState.getId()) && state.getMergedStates().size() == 1)
                    state.setInitialState(true);
            }
        }

        //TODO: remover estado inicial se ele possuir loop
        dfaMatrix.remove(initialState);
        return states;
    }

    private List<State> removeInitials(List<State> dfaStates, List<String> nameAux) {
        List<State> states = new ArrayList<>();
        for (State state : dfaStates) {
            if (nameAux.contains(state.getName())) {
                states.add(state);
            }
        }
        return states;
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

    private State getMergedState(String name, String terminal) {
        List<String> ids = Arrays.asList(name
                .replace("{", "").replace("}", "").split(","));
        Set<State> states = new HashSet<>();
        for (Map.Entry<State, HashMap<String, Set<State>>> i : ndfaAutomatonMatrix.entrySet()) {
            if (ids.contains(i.getKey().getId()) && i.getValue().get(terminal).size() > 0) {
                states.addAll(new ArrayList<>(i.getValue().get(terminal)));
            }
        }
        return getMergedState(states);
    }

    private State getMergedState(Set<State> states) {
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
