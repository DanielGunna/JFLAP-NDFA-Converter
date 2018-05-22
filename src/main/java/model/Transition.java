package model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import controller.Constants;

public class Transition {
    @Expose
    @SerializedName("read")
    private String value;
    @Expose
    @SerializedName("from")
    private String fromId;
    @Expose
    @SerializedName("to")
    private String toId;

    public Transition(String value, String fromId, String toId) {
        this.value = value;
        this.fromId = fromId;
        this.toId = toId;
    }

    @Override
    public String toString() {
        return fromId + "," + value + " : -> " + toId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public boolean isLambdaTransition() {
        return value.equals(Constants.LAMBDA);
    }
}
