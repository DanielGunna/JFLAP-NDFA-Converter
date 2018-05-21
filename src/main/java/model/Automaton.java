package model;


import com.google.gson.annotations.SerializedName;


import java.util.HashSet;
import java.util.Set;


public class Automaton {

    @SerializedName("state")
    private Set<State> states;
    @SerializedName("transition")
    private Set<Transition> transitions;

    private String type;
    private Set<String> alphabet;
    private State initialState;
    private Set<State> finalStates;

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public Set<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(Set<Transition> transitions) {
        this.transitions = transitions;
    }

    public String getType() {
        return type;
    }

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public void addTransition(Transition t) {
        if (transitions == null) transitions = new HashSet<>();
        transitions.add(t);
    }

    public String getAutomatonType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<String> alphabet) {
        this.alphabet = alphabet;
    }


    public Set<State> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<State> finalStates) {
        this.finalStates = finalStates;
    }

    public void addInitialState(State state) {
        if (initialState == null)
            initialState = state;
    }

    public void addFinalState(State state) {
        if (finalStates == null) finalStates = new HashSet<>();
        finalStates.add(state);
    }

    public void addAlphabetSymbol(String value) {
        if (alphabet == null) alphabet = new HashSet<>();
        alphabet.add(value);
    }

    public void fillInitialAndFinalStates() {
        for (State s : getStates()) {
            if (s.isFinalState())
                addFinalState(s);
            if (s.isInitialState())
                addInitialState(s);
        }
    }

    public Set<Transition> getTransitionsByStateAndTerminal(State s, String value) {
        Set<Transition> transitions = new HashSet<>();
        for (Transition t : getTransitions()) {
            if (t.getFromId().equals(s.getId()) && t.getValue().equals(value))
                transitions.add(t);
        }
        return transitions;
    }

    public State findStateById(String id) {
        for (State state : getStates()) {
            if (state.getId().equals(id))
                return state;
        }
        return null;
    }
}
