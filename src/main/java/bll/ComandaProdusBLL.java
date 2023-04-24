package bll;

import bll.validators.cantitateValida;
import bll.validators.Validator;
import dao.ComandaProdusDAO;
import model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ComandaProdusBLL {
    private static ComandaProdusDAO comandaD= new ComandaProdusDAO();
    private static List<Validator<ComandaProdus>> valid;

    public ComandaProdusBLL() {
        valid = new ArrayList<Validator<ComandaProdus>>();
        valid.add(new cantitateValida());
    }

    public static void adaugareComanda(ComandaProdus comanda){
        try{
            //Primeste ca un ComandaProdus, creeaza un obiect de tipul ComandaProdus, mai apoi apeleaza metoda
            // adaugareTable din ComandaProdusDAO cu obiectul ComandaProdus respectiv ca argument
            for (Validator<ComandaProdus> i : valid) {
                i.validate(comanda);
            }
            int i=comandaD.adaugare(comanda);

        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Date incorecte" );
        }


    }

    public static ComandaProdus gasireIDCmd(int id){
        //Primeste ca argument un int, creeaza un obiect de tipul ComandaProdus, mai apoi apleaza
        // metoda gasireID cu valoare respectiva siString-ul „ComandaProdus” ca argumente din clasa ComandaProdusDAO
        return comandaD.gasireID(id,"comandaprodus");
    }

    public static JTable afisareIdComanda(JTable tabel,int id){
        //Primeste ca argument un table de tipul Jtable in care introduce informatiile de afisat si o valoare int, returnand astfel tot un
        // JTable, apeland metoda afisareTabel2 cu tabelul dat ca argument
        return comandaD.afisareTabel2(tabel,"idComanda",String.valueOf(id));
    }


}
