package controller;


import com.google.gson.Gson;
import model.*;

import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static controller.Constants.DFA;
import static controller.Constants.NDFA;

/**
 * @author Daniel Gunna
 * Class to handle parsing between  JFLAP file format (.jff) and project's models
 * {@link AutomatonStructure} and {@link AutomatonWrapper}
 */

public class AutomatonReader {


    /**
     * Function to read an automaton from a  JFLAP format file
     *
     * @param file Path to file
     * @return Returns an {@link AutomatonStructure} object containing data from file
     */
    public AutomatonStructure readAutomatonFromFile(String file) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            StringBuilder reader = new StringBuilder();
            String line;
            while ((line = fileReader.readLine()) != null) {
                reader.append(line);
            }
            return fillAutomaton(convertXmlToObject(reader.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to determinate automaton's alphabet
     *
     * @param automatonWrapper Automaton data wrapped by an {@link AutomatonWrapper} object
     * @return {@link AutomatonWrapper} object filled with automaton's alphabet
     */
    private AutomatonWrapper fillAlphabet(AutomatonWrapper automatonWrapper) {
        Automaton automaton = automatonWrapper.getAutomatonStructure().getAutomaton();
        for (Transition transition : automaton.getTransitions())
            automaton.addAlphabetSymbol(transition.getValue());

        return automatonWrapper;
    }

    /**
     * Function to fill some extra info about automaton and return on an {@link AutomatonStructure} object
     *
     * @param automatonWrapper An {@link AutomatonWrapper} object containing data about an automaton
     * @return {@link AutomatonStructure} object with extra infos filled
     */
    private AutomatonStructure fillAutomaton(AutomatonWrapper automatonWrapper) {
        return fillAutomatonType(
                fillInitialsAndFinalStates(
                        fillAlphabet(automatonWrapper)
                )
        ).getAutomatonStructure();
    }

    /**
     * Function to assign final and initial states
     *
     * @param automatonWrapper Automaton data wrapped by an {@link AutomatonWrapper} object
     * @return {@link AutomatonWrapper} object filled with automaton initial and final states
     */
    private AutomatonWrapper fillInitialsAndFinalStates(AutomatonWrapper automatonWrapper) {
        Automaton automaton = automatonWrapper.getAutomatonStructure().getAutomaton();
        automaton.fillInitialAndFinalStates();
        return automatonWrapper;
    }

    /**
     * Function to determinate automaton type.
     * Possibles values are {@link DFA}(Deterministic Finite Automaton) and {@link NDFA}
     * (Non-Deterministic Finite Automaton)
     *
     * @param automatonWrapper Automaton data wrapped by an {@link AutomatonWrapper} object
     * @return {@link AutomatonWrapper} object filled with automaton type
     */
    private AutomatonWrapper fillAutomatonType(AutomatonWrapper automatonWrapper) {
        Automaton automaton = automatonWrapper.getAutomatonStructure().getAutomaton();
        List<String> terminals = new ArrayList<>();
        //Assume that it's an DFA
        automaton.setType(DFA);
        for (Transition transition : automaton.getTransitions()) {
            if (!terminals.contains(transition.getValue())) {
                terminals.add(transition.getValue());
            } else {
                automaton.setType(NDFA);
                break;
            }
        }
        return automatonWrapper;
    }


    /**
     * Function to convert a xml content from JLAP automaton format file into {@link AutomatonWrapper} object
     *
     * @param xmlFile
     * @return Returns an {@link AutomatonWrapper} that wraps an object  containing automaton data from xml content
     * @throws Exception
     */
    private AutomatonWrapper convertXmlToObject(String xmlFile) throws Exception {
        JSONObject jsonObject;
        AutomatonWrapper automaton = null;
        try {
            jsonObject = XML.toJSONObject(xmlFile);
            automaton = new Gson().fromJson(cleanUpJson(jsonObject.toString()), AutomatonWrapper.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return automaton;
    }

    /**
     * Function to verify fields of xml content  replacing empty strings
     * on transitions tags  to {@link Constants.LAMBDA} value and assign initial and final states with a boolean
     *
     * @param xmlContent XML content
     * @return {@link AutomatonStructure} object with required adjusts
     */
    private String cleanUpJson(String xmlContent) {
        return xmlContent.replace("\"initial\":{}", "\"initial\": true")
                .replace("\"final\":{}", "\"final\": true")
                .replace("\"read\":{}", "\"read\": \"" + Constants.LAMBDA + "\" ");
    }

}
