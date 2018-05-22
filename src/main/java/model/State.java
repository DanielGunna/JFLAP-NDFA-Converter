package model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.Set;


public class State {

    @Expose
    @SerializedName("initial")
    private boolean initialState;
    @Expose
    @SerializedName("final")
    private boolean finalState;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("x")
    private int xPosition;
    @Expose
    @SerializedName("y")
    private int yPosition;
    @Expose
    @SerializedName("id")
    private String id;

    @Override
    public String toString() {
        return name;
    }

    private Set<State> mergedStates;


    public State(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public State() {

    }


    public boolean isMerged() {
        return mergedStates != null && mergedStates.size() > 0;
    }


    public Set<State> getMergedStates() {
        return mergedStates;
    }

    public void setMergedStates(Set<State> mergedStates) {
        this.mergedStates = mergedStates;
    }

    public boolean isInitialState() {
        return isMerged() ? verifyMergedStatesInital() : initialState;
    }

    private boolean verifyMergedStatesInital() {
        for (State state : mergedStates) {
            if (state.isInitialState())
                return mergedStates.size() == 1;
        }
        return false;

    }

    public void setInitialState(boolean initialState) {
        this.initialState = initialState;
    }

    public boolean isFinalState() {
        return isMerged() ? verifyMergedStates() : finalState;
    }

    private boolean verifyMergedStates() {
        for (State state : mergedStates) {
            if (state.isFinalState())
                return true;
        }
        return false;
    }

    public void setFinalState(boolean finalState) {
        this.finalState = finalState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addMergedState(State state) {
        if (mergedStates == null) mergedStates = new HashSet<>();
        mergedStates.add(state);
    }
}
