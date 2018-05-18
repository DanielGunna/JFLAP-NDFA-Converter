package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transition {
    @SerializedName("read")
    private String value;
    @SerializedName("from")
    private int fromId;
    @SerializedName("to")
    private int toId;

    public Transition(String value, int fromId, int toId) {
        this.value = value;
        this.fromId = fromId;
        this.toId = toId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }
}
