package nl.teunwillems.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import nl.teunwillems.sort.Parser;

import java.net.URL;
import java.util.*;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class WoordenController implements Initializable {

    private static final String DEFAULT_TEXT = "Een, twee, drie, vier\n" +
            "Hoedje van, hoedje van\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van papier\n" +
            "\n" +
            "Heb je dan geen hoedje meer\n" +
            "Maak er één van bordpapier\n" +
            "Eén, twee, drie, vier\n" +
            "Hoedje van papier\n" +
            "\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van, hoedje van\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van papier\n" +
            "\n" +
            "En als het hoedje dan niet past\n" +
            "Zetten we 't in de glazenkas\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van papier";

    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    private Parser parser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
        parser = new Parser();
    }

    @FXML
    private void aantalAction(ActionEvent event) {
        String[] words = parser.getWords(taInput.getText());
        taOutput.setText(String.valueOf(words.length));
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
        taOutput.clear();
        parser.getUniqueWords(taInput.getText()).forEach(this::outputLine);
    }

    @FXML
    private void frequentieAction(ActionEvent event) {
        Map<String, Integer> frequenties = new TreeMap<>();
        for (String str : parser.getWords(taInput.getText())) {
            if (frequenties.containsKey(str)) {
                int i = frequenties.get(str);
                frequenties.put(str, i + 1);
            } else {
                frequenties.put(str, 1);
            }
        }

        taOutput.clear();
        frequenties = sortByValue(frequenties);
        frequenties.forEach((k, v) -> outputLine(k + ": " + v));
    }

    @FXML
    private void concordatieAction(ActionEvent event) {
        StringBuilder concordantieString = new StringBuilder();

        TreeMap<String, Set<Integer>> concordantie = (TreeMap<String, Set<Integer>>) parser.getConcordantie(taInput.getText());
        for (String key : concordantie.keySet()) {
            concordantieString.append(String.format("%s : %s\n", key, concordantie.get(key)));
        }
        taOutput.setText(concordantieString.toString());
    }

    public void outputLine(String line) {
        taOutput.appendText(line + System.lineSeparator());
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }


}
