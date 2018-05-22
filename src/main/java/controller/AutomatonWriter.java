package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


import static controller.Constants.JFLAP_FILE_HEADER;

public class AutomatonWriter {


    public String getJflapFileContentFromAutomaton(Automaton automaton) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        AutomatonStructure automatonStructure = new AutomatonStructure();
        automatonStructure.setType("fa");
        normalizeIds(automaton);
        fillXY(automaton);
        automatonStructure.setAutomaton(automaton);

        String content = convertJsonAutomatonToXml(automaton);
        String xml = JFLAP_FILE_HEADER + "\n" + content;
       // try {
         //   saveJflapFile(xml, "/home/gunna/");
       // } catch (IOException e) {
         //   e.printStackTrace();
        //}
        return xml;
    }

    public void saveJflapFile(String content, String path) throws IOException {
        File file = new File(path + String.format("/teste_%s.jff", String.valueOf(Math.random()).replace(".", "_")));
        file.createNewFile();
        FileOutputStream writer = new FileOutputStream(file, false);
        writer.write(content.getBytes());
        writer.close();
    }

    private String convertJsonAutomatonToXml(Automaton automaton) {
        String content = "<structure>\n<type>fa</type>\n<automaton>";
        content += "<!--The list of states.-->";
        for (State state : automaton.getStates()) {
            content += String.format("<state name=\"%s\" id=\"%s\">", state.getName(), state.getId());
            if (state.isInitialState())
                content += "<initial/>";
            if (state.isFinalState())
                content += "<final/>";
            content += String.format("<x>%s</x>\n<y>%s</y>", state.getxPosition(), state.getyPosition());
            content += "</state>";
        }
        content += "<!--The list of transitions.-->";
        for (Transition t : automaton.getTransitions()) {
            content += "<transition>";
            content += String.format("<from>%s</from>", t.getFromId());
            content += String.format("<to>%s</to>", t.getToId());
            content += String.format("<read>%s</read>", t.getValue());
            content += "</transition>";
        }
        content += "</automaton>\n</structure>";
        return content;
    }

    private void normalizeIds(Automaton automaton) {
        for (Transition t : automaton.getTransitions()) {
            State from = automaton.findStateById(t.getFromId());
            State to = automaton.findStateById(t.getToId());
            t.setFromId(t.getFromId().replace(",", "").replace("-", from.isInitialState() ? "-" : ""));
            t.setToId(t.getToId().replace(",", "").replace("-", to.isInitialState() ? "-" : ""));
        }
        for (State state : automaton.getStates()) {
            state.setName(state.getName()
                    .replace(",", "")
                    .replace("{", "")
                    .replace("-1", "0")
                    .replace("}", ""));
            state.setId(
                    state.getId()
                            .replace(",", "")
                            .replace("-", state.isInitialState() ? "-" : "")
            );
        }

    }

    private void fillXY(Automaton automaton) {
        for (State state : automaton.getStates()) {
            state.setxPosition((int) ((Math.random() * 100) * Math.random() * 10));
            state.setyPosition((int) ((Math.random() * 100) * Math.random() * 10));
        }
    }

}
