package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.awt.*;

public class State {

    @SerializedName("initial")
    private boolean initialState;
    @SerializedName("final")
    private boolean finalState;
    @SerializedName("name")
    private String name;
    @SerializedName("x")
    private int xPosition;
    @SerializedName("y")
    private int yPosition;
    @SerializedName("id")
    private int id;

    public State(String name, int xPosition, int yPosition, int id) {
        this.name = name;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.id = id;
    }


    public boolean isInitialState() {
        return initialState;
    }

    public void setInitialState(boolean initialState) {
        this.initialState = initialState;
    }

    public boolean isFinalState() {
        return finalState;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
