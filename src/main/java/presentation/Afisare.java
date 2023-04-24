package presentation;

import java.io.FileWriter;
import java.io.IOException;

public class Afisare {
    private String filetoWrite;
    public Afisare(String fileToWrite){
        this.filetoWrite=fileToWrite;
    }
    public void writeToFile(String sir){
        FileWriter f;
        try{
            f=new FileWriter(this.filetoWrite);
            f.write(sir);
            f.close();
        } catch (IOException e) {
            System.out.println("IOException Catched");
            e.printStackTrace();
        }
    }

}
