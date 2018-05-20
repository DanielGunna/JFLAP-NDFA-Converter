package model;


import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Automaton {

    @SerializedName("state")
    private Set<State> states;
    @SerializedName("transition")
    private Set<Transition> transitions;

    private String type;
    private Set<String> alphabet;
    private Set<State> initialStates;
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

    public Set<State> getInitialStates() {
        return initialStates;
    }

    public void setInitialStates(Set<State> initialStates) {
        this.initialStates = initialStates;
    }

    public Set<State> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<State> finalStates) {
        this.finalStates = finalStates;
    }

    public void addInitialState(State state) {
        if (initialStates == null) initialStates = new HashSet<>();
        initialStates.add(state);
    }

    public void addFinalState(State state) {
        if (finalStates == null) finalStates = new HashSet<>();
        finalStates.add(state);
    }

    public void addAlphabetSymbol(String value) {
        if (alphabet == null) alphabet = new HashSet<>();
        alphabet.add(value);
    }
}
