package model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutomatonWrapper {
    @Expose
    @SerializedName("structure")
    private AutomatonStructure automatonStructure;

    public AutomatonWrapper(AutomatonStructure automatonStructure) {
        this.automatonStructure = automatonStructure;
    }

    public AutomatonStructure getAutomatonStructure() {
        return automatonStructure;
    }

    public void setAutomatonStructure(AutomatonStructure automatonStructure) {
        this.automatonStructure = automatonStructure;
    }
}
