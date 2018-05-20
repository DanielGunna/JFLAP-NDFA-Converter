package controller;

import model.Automaton;
import model.State;

import java.util.*;

public class NdfaConverter {


    public Automaton getDfaFromNdfa(Automaton ndfaAutomaton) {
        if (ndfaAutomaton.getAutomatonType().equals(Constants.NDFA)) {
            return convertNdfaAutomatonoDfa(ndfaAutomaton);
        }
        return ndfaAutomaton;
    }

    private Automaton convertNdfaAutomatonoDfa(Automaton ndfaAutomaton) {
        //Cria novo conjunto de estados
        Set<State> dfaStates = new HashSet<>();
        //Add estado iniciial ao conjunto
        dfaStates.addAll(ndfaAutomaton.getInitialStates());
        dfaStates.addAll(generatePowSet(ndfaAutomaton));

        return null;
    }

    /**
     * A function to get list of states from  power set of  state's set  of an given automaton
     *
     * @param ndfaAutomaton
     * @return Set of states
     */
    private Set<State> generatePowSet(Automaton ndfaAutomaton) {
        Set<Set<State>> powerSets = powerSet(ndfaAutomaton.getStates());
        Set<State> newStates = new HashSet<>();
        for (Set<State> set : powerSets) {
            if (set.size() > 0) {
                State newState = new State();
                String stateName = "";
                for (State s : set) {
                    stateName += s.getName() + ",";
                    newState.addMergedState(s);
                    if(s.isFinalState()){
                        newState.setFinalState(true);
                    }
                }
                newState.setName(String.format("[%s]", stateName.substring(0, stateName.length() - 1)));
                newStates.add(newState);
            }
        }
        return newStates;
    }


    /**
     * A recursive function to calculate power set of a set of states
     *
     * @param originalSet
     * @return The power set of states
     */
    private Set<Set<State>> powerSet(Set<State> originalSet) {
        Set<Set<State>> sets = new HashSet<>();
        if (!originalSet.isEmpty()) {
            List<State> list = new ArrayList<>(originalSet);
            State head = list.get(0);
            Set<State> rest = new HashSet<>(list.subList(1, list.size()));
            for (Set<State> set : powerSet(rest)) {
                Set<State> newSet = new HashSet<>();
                newSet.add(head);
                newSet.addAll(set);
                sets.add(newSet);
                sets.add(set);
            }
        } else {
            sets.add(new HashSet<>());
        }
        return sets;
    }


}
