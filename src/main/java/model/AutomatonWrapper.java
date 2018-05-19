package model;


import com.google.gson.annotations.SerializedName;

public class AutomatonWrapper {
    @SerializedName("structure")
    private AutomatonStructure automatonStructure;


    public AutomatonStructure getAutomatonStructure() {
        return automatonStructure;
    }

    public void setAutomatonStructure(AutomatonStructure automatonStructure) {
        this.automatonStructure = automatonStructure;
    }
}
