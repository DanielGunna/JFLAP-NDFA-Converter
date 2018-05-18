package controller;


import model.Automaton;
import model.AutomatonStructure;
import model.State;
import model.Transition;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.bind.JAXBContext;

import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Scanner;


public class AutomatonReader {

    private Automaton  readFA(BufferedReader reader)
            throws IOException {
        Automaton fa = new Automaton();
        // Generic states.
        State[] states = readStateCreate(fa, reader);
        String[][][] groups = readTransitionGroups(2, 1, states.length, reader);
        for (int s = 0; s < groups.length; s++) {
            for (int g = 0; g < groups[s].length; g++) {
                String[] group = groups[s][g];
                State to = states[Integer.parseInt(group[1]) - 1], from = states[s];
                if (group[0].equals("null"))
                    group[0] = "";
                Transition t = new Transition(from, to,
                        group[0]);
                fa.addTransition(t);
            }
        }
        readStateMove(states, reader);
        return fa;
    }

    private void readStateMove(State[] states, BufferedReader reader)
            throws IOException {
        for (int i = 0; i < states.length; i++) {
            int x, y;
            String[] tokens = reader.readLine().split("\\s+");
            try {
                x = Integer.parseInt(tokens[1]);
                y = Integer.parseInt(tokens[2]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("State " + (i + 1)
                        + "'s position badly formatted.");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new RuntimeException("State " + (i + 1)
                        + "'s position string too short.");
            }
            states[i].getPoint().setLocation(x, y);
        }
    }



    private State[] readStateCreate(Automaton automaton, BufferedReader reader)
            throws IOException {
        // Read the number of states.
        State[] states = null;
        try {
            int numStates = Integer.parseInt(reader.readLine());
            if (numStates < 0)
                throw new RuntimeException("Number of states cannot be "
                        + numStates + "!");
            states = new State[numStates];
        } catch (NumberFormatException e) {
            throw new RuntimeException("Bad format for number of states!");
        }
        for (int i = 0; i < states.length; i++)
            states[i] = automaton.createState(new java.awt.Point(0, 0));
        // Next possibly two lines have something to do with alphabet.
        reader.readLine();
        // Read the ID of the initial state.
        try {
            int initStateID = Integer.parseInt(reader.readLine());
            if (initStateID < 1 || initStateID > states.length)
                throw new RuntimeException("Initial state cannot be "
                        + initStateID + ".");
            automaton.setInitialState(states[initStateID - 1]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Bad format for initial state ID!");
        }
        // Read the IDs of the final states.
        String line = reader.readLine();
        String[] lineTokens = line.split("\\s+");
        if (lineTokens.length == 0)
            throw new RuntimeException("Final state list is empty line!");
        try {
            int last = Integer.parseInt(lineTokens[lineTokens.length - 1]);
            if (last != 0)
                throw new RuntimeException(
                        "Final state list not terminated with 0!");
            try {
                for (int i = 0; i < lineTokens.length - 1; i++) {
                    automaton.addFinalState(states[Integer
                            .parseInt(lineTokens[i]) - 1]);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new RuntimeException("Bad final state ID read!");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Bad format in final state list!");
        }
        return states;
    }


    private String[][][] readTransitionGroups(int groupSize, int idPosition,
                                              int numStates, BufferedReader reader) throws IOException {
        String[][][] groups = new String[numStates][][];
        for (int s = 0; s < numStates; s++) {
            RuntimeException p = new RuntimeException("Transition line " + (s + 1)
                    + " badly formatted.");
            String[] tokens = reader.readLine().split("\\s+");
            if ((tokens.length % groupSize) != 1
                    || !tokens[tokens.length - 1].equals("EOL"))
                throw p;
            groups[s] = new String[tokens.length / groupSize][];
            for (int g = 0; g < groups[s].length; g++) {
                groups[s][g] = new String[groupSize];
                for (int i = 0; i < groupSize; i++)
                    groups[s][g][i] = tokens[groupSize * g + i];
                try {
                    int i = Integer.parseInt(groups[s][g][idPosition]);
                    if (i < 1 || i > numStates)
                        throw p;
                } catch (NumberFormatException e) {
                    throw p;
                }
            }
        }
        return groups;1
    }

    public void readAutomatonFromFile(String file){
        try {
            Scanner in = new Scanner(new File(file));
            StringBuilder reader = new StringBuilder();
            while (in.hasNext()) {
                reader.append(in.next());
            }
            in.close();
            convertXmltoObject(reader.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private AutomatonStructure convertXmltoObject(String xmlFile) throws Exception{
        JSONObject jsonObject = null;
        try {
            jsonObject = XML.toJSONObject(xmlFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AutomatonStructure();
    }

}
