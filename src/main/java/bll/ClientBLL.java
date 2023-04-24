package bll;

import bll.validators.emailValid;
import bll.validators.adresaValida;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ClientBLL {
    private static ClientDAO c=new ClientDAO();
    private static List<Validator<Client>> valid=new ArrayList<Validator<Client>>();

    public ClientBLL(){
        valid.add(new emailValid());
        valid.add(new adresaValida());

    }

    public static void adaugareClient(Client client){
        //Primeste ca un Client, creeaza un obiect de tipul Client,
        // mai apoi apeleaza metoda adaugareTable din ClientDAO cu clientul respectiv ca argument
        try{
            for (Validator<Client> i : valid) {
                i.validate(client);
            }
            c.adaugareTabel(client);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Inserare client nereusita" );

        }
    }

    public static Client gasireID(int id){
        //Primeste ca argument un int, creeaza un obiect de tipul Client, mai apoi apleaza metoda
        // gasireID cu valoare respectiva si String-ul „client” ca argumente din clasa ClientDAO
        return c.gasireID(id,"client");
    }

    public static void stergereClient(Client client,int id){
        try{
            //Identic cu adaugareClient, creaza un obiect de tipul Client
            // din argumentele primite si apeleaza metoda de stergere din ClientDAO
            for (Validator<Client> i : valid) {
                i.validate(client);
            }
            c.stergereTabel(client,id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Stergere client nereusita" );

        }
    }

    public static void updateClient(int id,Client client){
        try{
            //Identic cu adauh=gareClient, creaza un obiect de tipul Client
            // din argumentele primite si apeleaza metoda de update din ClientDAO
            c.updateTable(client,id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Editare client nereusita" );

        }

    }

    public static int maxId(){
        //Creeaza un obiect de tipul Client, mai apoi apeleaza metoda gasireIDPlus1 din clasa ClientDAO

        return c.gasireIDPlus1();
    }
    public static JTable afisareClient(JTable tabel){
//Primeste ca argument un table de tipul Jtable in care introduce informatiile de afisat, returnand astfel
// tot un Jtable, apeland metoda afisareTabel cu tabelul dat ca argument
        return c.afisareTabel(tabel);
    }
}
