package controller;


import com.google.gson.Gson;
import model.Automaton;
import model.AutomatonStructure;

import model.AutomatonWrapper;
import model.State;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;


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
     * Function to fill some extra info about automaton and return on an {@link AutomatonStructure} object
     *
     * @param automatonWrapper An {@link AutomatonWrapper} object containing data about an automaton
     * @return {@link AutomatonStructure} object with extra infos filled
     */
    private AutomatonStructure fillAutomaton(AutomatonWrapper automatonWrapper) {
        Automaton automaton = automatonWrapper.getAutomatonStructure().getAutomaton();
        for (State s : automaton.getStates()) {
            if (s.isFinalState())
                automaton.addFinalState(s);
            if (s.isInitialState())
                automaton.addInitialState(s);
        }
        return automatonWrapper.getAutomatonStructure();
    }


    /**
     * Function to convert a xml content from automaton file into {@link AutomatonWrapper} object
     *
     * @param xmlFile
     * @return Returns an {@link AutomatonWrapper} object containing data from xml content
     * @throws Exception
     */
    private AutomatonWrapper convertXmlToObject(String xmlFile) throws Exception {
        JSONObject jsonObject = null;
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
