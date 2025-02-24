package com.brh;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField lengthTextField;
    @FXML
    private TextField widthTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private ComboBox<String> sizeMenu;
    private final double[] PLATESAREA = { 0.06, 0.16, 0.25, 0.24  };
    private final String[] PLATESTYPE = { "20x30", "40x40", "50x50", "40x60" };

    /**
     * Wird vom Java FX Framework ausgeführt bei Start des Controllers
     */
    @FXML
    private void initialize(){
        sizeMenu.getItems().addAll(PLATESTYPE);
    }

    /**
     * Bei Click auf den Berechnen-Button, werden Kosten und Materialbedarf errechnet
     */
    @FXML
    protected void onCalculateClick() {

         double width = getValueOfTextfield(widthTextField);
         double length = getValueOfTextfield(lengthTextField);
         double pricePerSquareMeter = getValueOfTextfield(priceTextField);

         int plateType = sizeMenu.getSelectionModel().getSelectedIndex();
         double areaPerPlate = PLATESAREA[plateType];

         double area = width * length;

         double price = pricePerSquareMeter * area;
         int platesCount = (int) Math.ceil(area / areaPerPlate);

         String text = getResultText(price, platesCount, plateType);
         showInfoWindow( "Ergebnis", text);

         boolean success = LogDAO.getInstance().addCalcToLog(price, platesCount, plateType);

         if(success){
             LogDAO.getInstance().saveAllLogData();
         }
    }

    /**
     * Anzeigen der Log-Daten
     */
    @FXML
    protected void onLogClick(){
         var logList = LogDAO.getInstance().getLogList();

         if( logList.isEmpty() ){
            showInfoWindow("Info","Es sind noch keine Daten gespeichert im Log");
            return;
         }

         String text ="<// Log---------------//>\n";

         for( var log : logList){
             text += "Zeitstempel: "+log.getTimeStamp()+"\n";

             text += getResultText(log.getPrice(),
                         log.getPlatesCount(),
                         log.getPlateType()
                     );
             text+="\n\n";
         }

         showInfoWindow("Log", text);
    }

    /**
     * Bei Click auf "Log Löschen" aufgerufen, löscht Log
     */
    @FXML
    protected void onLogClearClick(){
        LogDAO.getInstance().clearLogList();
    }

    /**
     * Holt den Text eines gegebenen Textfeldes, führt auch Fehlerbehnadlung
     * bei evtl. Parsefehler durch und gibt in diesem Fall den Wert 0.0 zurueck
     * @param tf Textfeld
     * @return Wert als double
     */
    private double getValueOfTextfield( TextField tf ){
        double value = 0;
        try{
            String text = tf.getText();
            text = text.replace(",", ".");

            value = Double.parseDouble(text);
            if(value < 0){
                throw new NumberFormatException();
            }
        }
        catch( NumberFormatException e){
            System.err.println( e.getMessage() );
        }

        return value;
    }

    /**
     * Zeigt eine Meldung in einem Infofenster an
     * @param title Titel des Fensters
     * @param message Nachricht
     */
    private void showInfoWindow(String title, String message){
        Alert window = new Alert(Alert.AlertType.INFORMATION);
        showWindow(title, message, window);
    }

    /**
     * Zeigt eine Meldung in einem Error fenster an
     * @param title Titel des Fensters
     * @param message Nachricht
     */
    private void showErrorWindow(String title, String message){
        Alert window = new Alert(Alert.AlertType.ERROR);
        showWindow(title, message, window);
    }

    private void showWindow(String title, String message, Alert window){
        window.setTitle(title);
        window.setHeaderText(null);
        window.setContentText( message );
        window.showAndWait();
    }

    /**
     * Erzeugt aus dem Preis und der Plattenanzahl einen ausgebaren Text
     * mit entsprechenden Umbrüchen
     * @param price Preis
     * @param plates Plattenanzahl
     * @return
     */
    private String getResultText( double price, int plates, int plateIndex ){
        String text = "Es wurde folgendes berechnet:\n";
        text+= "  Plattentyp: " + PLATESTYPE[plateIndex]+"\n";
        text+= "  Anzahl Platten: "+plates+"\n";
        text+= "  Gesamtpreis: "+ price;
        return text;
    }
}