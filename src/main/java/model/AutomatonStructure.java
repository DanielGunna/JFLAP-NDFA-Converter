package model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutomatonStructure {

   @SerializedName("type")
   private String type;
   @SerializedName("automaton")
   private Automaton automaton;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Automaton getAutomaton() {
        return automaton;
    }

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }
}
