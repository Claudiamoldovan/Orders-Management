package bll;

import bll.validators.numarValid;
import bll.validators.Validator;
import bll.validators.produsValid;
import dao.ProdusDAO;
import model.Produs;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProdusBLL {
    private static ProdusDAO produsD  = new ProdusDAO();
    private static List<Validator<Produs>> valid;

    public ProdusBLL(){
        valid=new ArrayList<Validator<Produs>>();
        valid.add(new numarValid());
        valid.add(new produsValid());
    }

    public static void adaugareProdus(Produs produs){
        //Primeste ca un Produs, creeaza un obiect de tipul Produs, mai apoi apeleaza
        // metoda adaugareTable din ProdusDAO cu Produsul respectiv ca argument
        try{
            for (Validator<Produs> i : valid) {
                i.validate(produs);
            }
            produsD.adaugareTabel(produs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Inserare produs nereusita" );

        }
    }

    public static void stergereProdus(Produs produs,int id){
        try{
            //Identic cu adaugareProdus, creaza un obiect de tipul Produs din
            // argumentele primite si apeleaza metoda de stergere din ProdusDAO
            for (Validator<Produs> i : valid) {
                i.validate(produs);
            }
            produsD.stergereTabel(produs,id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Stergere produs nereusita" );

        }
    }

    public static void updateProdus(int id,Produs produs){
        try{
            //Identic cu adaugareProdus, creaza un obiect de tipul Produs din
            // argumentele primite si apeleaza metoda de update din ProdusDAO
            produsD.updateTable(produs,id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Editare produs nereusita" );
        }
    }

    public static Produs gasireID(int id){
        //Primeste ca argument un int, creeaza un obiect de tipul Produs, mai apoi apleaza metoda gaisireID
        // cu valoare respectiva si String-ul „Produs” ca argumente din clasa ProdusDAO
        return produsD.gasireID(id,"produs");
    }

    public static int maxId(){
//Creeaza un obiect de tipul Produs, mai apoi apeleaza metoda gasireIDPlus1 din clasa ProdusDAO
        return produsD.gasireIDPlus1();
    }

    public static JTable afisareProduse(JTable tabel){
//Primeste ca argument un table de tipul Jtable in care introduce informatiile de afisat,
// returnand astfel tot un Jtable, apeland metoda afisareTabel cu tabelul dat ca argument
        return produsD.afisareTabel(tabel);
    }
}