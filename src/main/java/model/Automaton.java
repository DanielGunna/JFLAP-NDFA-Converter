package model;


import com.google.gson.annotations.SerializedName;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Automaton {

    @SerializedName("state")
    private List<State> states;
    @SerializedName("transition")
    private List<Transition> transitions;

    private String type;
    private List<State> initialStates;
    private List<State> finalStates;

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public void addTransition(Transition t) {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<State> getInitialStates() {
        return initialStates;
    }

    public void setInitialStates(List<State> initialStates) {
        this.initialStates = initialStates;
    }

    public List<State> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(List<State> finalStates) {
        this.finalStates = finalStates;
    }

    public void addInitialState(State state) {
        if (initialStates == null) initialStates = new ArrayList<>();
        initialStates.add(state);
    }

    public void addFinalState(State state) {
        if (finalStates == null) finalStates = new ArrayList<>();
        finalStates.add(state);
    }

    public State createState(Point point) {
        return null;
    }
}
