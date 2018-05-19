package controller;

import model.Automaton;

public class NdfaConverter {



    public Automaton getDfaFromNdfa(Automaton ndfaAutomaton){
        if(ndfaAutomaton.getType().equals(Constants.NDFA)){
            return convertNdfaToDfa(ndfaAutomaton);
        }
        return ndfaAutomaton;
    }

    private Automaton convertNdfaToDfa(Automaton ndfaAutomaton) {
        return null;
    }


}
