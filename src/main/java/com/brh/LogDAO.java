package com.brh;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LogDAO {
    private ArrayList<CalcResult> logList;
    private String LOGFILENAME ="log.csv";


    public LogDAO() {
        logList = loadLogList();
    }

    /**
     * Speichert die Liste mit den Logs
     */
    public void saveAllLogData(){

        FileWriter writer = null;
        try {
            String path = getClass().getResource( LOGFILENAME ).getPath();
            writer = new FileWriter(path);
            for(var item : logList ){
                if(item != null) {
                    String line = item.getPrice()+","
                                  +item.getPlatesCount()+","
                                  +item.getPlateType()+","
                                  +item.getTimeStamp()+"\n";
                    System.out.println(line);
                    writer.write(line);
                }
            }
         }
         catch( IOException e){
             System.err.println("Error: "+e.getMessage());
         }
         finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch( IOException e ) {
                    System.err.println("Error" + e.getMessage() );
                }
            }
        }
    }

    /**
     * Laedt die Daten aus der log.csv
     * @return Rückgabe der Daten als Liste
     */
    private ArrayList<CalcResult> loadLogList(){
        var logList = new ArrayList<CalcResult>();

        FileReader reader = null;
        try{
            String path = getClass().getResource( LOGFILENAME ).getPath();
            reader = new FileReader( path );
            BufferedReader buffer = new BufferedReader( reader ); //Reader zum zeilenweise Lesen
            String line; //einzelne Zeile, zunächst leer
            logList.clear(); //alte Daten aus der LogList löschen

            //Schleife => Lese solange line nicht null ist
            while( ( line = buffer.readLine() ) != null){

                String[] data = line.split( "," );

                double price = Double.parseDouble(data[0]);
                int platesCount = Integer.parseInt(data[1]);
                int plateType = Integer.parseInt(data[2]);
                LocalDateTime date = LocalDateTime.parse(data[3]);

                CalcResult result = new CalcResult(price, platesCount, plateType, date );
                logList.add(result);

            }
        }
        catch( Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch( IOException e ) {
                    System.err.println("Error" + e.getMessage() );
                }
            }
        }

        return logList;
    }

    /**
     * Holt die logList
     * @return Rückgabe der logList
     */
    public ArrayList<CalcResult> getLogList(){

        return logList ;
    }

    /**
     * Fuegt der logList eine neue Berechnung hinzu
     * @param price Preis
     * @param platesCount Anzahl der Platten
     * @param plateType Plattentyp
     * @return Erfolgsmeldung
     */
    public boolean addCalcToLog(double price, int platesCount, int plateType){
        var calcResult = new CalcResult(price, platesCount, plateType);
        return logList.add( calcResult );
    }

}
