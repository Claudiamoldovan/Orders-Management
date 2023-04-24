package bll;

import dao.ComandaDAO;
import model.*;

public class ComandaBLL {
    private static ComandaDAO comandaD = new ComandaDAO();

    public static void adaugareComanda(Comanda comanda){
        //Primeste ca un Comanda, creeaza un obiect de tipul Comanda, mai apoi
        // apeleaza metoda adaugareTable din ComandaDAO cu comanda respectiv ca argument
        comandaD.adaugareTabel(comanda);
    }

    public static int findMaxId(){
        //Creeaza un obiect de tipul Comanda, mai apoi apeleaza metoda maxId din clasa ComandaDAO
       return comandaD.maxId();}

}
