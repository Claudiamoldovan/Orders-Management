package dao;

import connection.ConnectionFactory;
import model.Client;
import model.ComandaProdus;
import model.Produs;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {

    protected static final Logger LOGGER= Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> tip;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.tip = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private StringBuilder creareInserare(Object a){
        //Metoda care creeaza prin tehnica reflectiei query-ul necesar pentru a introduce date in tabela data de
        // numele clasei obiectului care este primit ca argument, returneaza un StringBuilder care reprezinta query-ul
        StringBuilder stringb1=new StringBuilder();
        stringb1.append("Insert into "+a.getClass().getSimpleName()+" ( ");
        StringBuilder stringb2=new StringBuilder();
        stringb2.append("VALUES(");
        Field field[]=a.getClass().getDeclaredFields();

        for(int i=1;i< field.length;i++){
            field[i].setAccessible(true);
            try {
                stringb1.append(field[i].getName()+(i!=field.length - 1 ? "," : ""));
                stringb2.append((field[i].getType().equals(String.class) ? "'" : "") + field[i].get(a) + (field[i].getType().equals(String.class) ? "'" : "") + (i != field.length - 1 ? "," : ""));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        stringb1.append(" )");
        stringb2.append(" )");
        stringb1.append(stringb2);
        return stringb1;
    }

    public int adaugareTabel(Object a) {
        //Aceasta metoda insereaza in baza de date obiectul primit ca si argument care poate sa fie de tipul unuia
        // din cele 3 clase care mapeaza baza de date. Query-ul este format prin apelarea creareInserare prezentate
        // mai sus. Se returneaza un int care reprezinta un cod de eroare ce va fi folosit pentru a verifica tipul
        // erorii pentru cazul in care se insereaza un produs deja existent, in cazul acesta se va face update la
        // cantitatea produsului
        String query = creareInserare(a).toString();
        Connection conectiune = null;
        PreparedStatement statement = null;
        try {
            conectiune = ConnectionFactory.getConnection();
            statement = conectiune.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            return e.getErrorCode(); // returneaza codul de eroare
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conectiune);
        }
        return 0;
    }

    private StringBuilder creareStergere(Object a,int id){
        //Metoda care creeaza prin tehnica reflectiei query-ul necesar pentru a sterge date in tabela data de
        // numele clasei obiectului care este primit ca argument, returneaza un StringBuilder care reprezinta query-ul
        StringBuilder stringb1=new StringBuilder();
        Field field[]=a.getClass().getDeclaredFields();
        stringb1.append("Delete From "+a.getClass().getSimpleName()+" WHERE id = "+id +" and ");

        for(int i=1;i< field.length;i++){
            field[i].setAccessible(true);
            try {
                stringb1.append(field[i].getName()+" = "+(field[i].getType().equals(String.class) ? "'" : "") + field[i].get(a) + (field[i].getType().equals(String.class) ? "'" : "") +(i!=field.length - 1 ? " and " : ""));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return stringb1;
    }

    public int stergereTabel(Object a,int id) {
        //Aceasta metoda sterge in baza de date obiectul primit ca si argument care poate sa fie de tipul unuia
        // din cele 3 clase care mapeaza baza de date. Query-ul este format prin apelarea creareInserare prezentate
        // mai sus. Se returneaza un int care reprezinta un cod de eroare ce va fi folosit pentru a verifica tipul
        // erorii pentru cazul in care se insereaza un produs deja existent, in cazul acesta se va face update la
        // cantitatea produsului
        String query = creareStergere(a,id).toString();
        Connection conectiune = null;
        PreparedStatement statement = null;
        try {
            conectiune = ConnectionFactory.getConnection();
            statement = conectiune.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            return e.getErrorCode(); 
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conectiune);
        }
        return 0;
    }


    private StringBuilder creareUpdate(Object a,int id){
        //Metoda care creeaza prin tehnica reflectiei query-ul necesar pentru a edita date in tabela data de
        // numele clasei obiectului care este primit ca argument, returneaza un StringBuilder care reprezinta query-ul
        StringBuilder stringb1=new StringBuilder();
        Field field[]=a.getClass().getDeclaredFields();
        stringb1.append("Update "+a.getClass().getSimpleName()+" SET ");

        for(int i=1;i< field.length;i++){
            field[i].setAccessible(true);
            try {
                stringb1.append(field[i].getName()+" = "+(field[i].getType().equals(String.class) ? "'" : "") + field[i].get(a) + (field[i].getType().equals(String.class) ? "'" : "") +(i!=field.length - 1 ? " , " : ""));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        stringb1.append(" Where id = "+id);
        return stringb1;
    }

    public int updateTable(Object a,int id) {
        //Aceasta metoda editeaza in baza de date obiectul primit ca si argument care poate sa fie de tipul unuia
        // din cele 3 clase care mapeaza baza de date. Query-ul este format prin apelarea creareInserare prezentate
        // mai sus. Se returneaza un int care reprezinta un cod de eroare ce va fi folosit pentru a verifica tipul
        // erorii pentru cazul in care se insereaza un produs deja existent, in cazul acesta se va face update la
        // cantitatea produsului
        String query = creareUpdate(a,id).toString();
        Connection conectiune = null;
        PreparedStatement statement = null;
        try {
            conectiune = ConnectionFactory.getConnection();
            statement = conectiune.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            return e.getErrorCode();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conectiune);
        }
        return 0;
    }

    private String creareSelectare(String field){
        //Metoda care creeaza prin tehnica reflectiei query-ul necesar pentru a selecta date din tabela data de
        // numele clasei obiectului care este primit ca argument si care are field-ul egal cu o valoare ceruta ulterior,
        // returneaza un StringBuilder care reprezinta query-ul
        StringBuilder stringb=new StringBuilder();
        stringb.append("SELECT ");
        stringb.append(" * ");
        stringb.append(" FROM ");
        stringb.append(tip.getSimpleName());
        StringBuilder append = stringb.append(" WHERE " + field + " = ?");
        return stringb.toString();
    }

    public T gasireID(int id,String numeTab){
        //Aceasta metoda un obiect de tipul unuia din cele 3 clase care mapeaza baza de date . Query-ul este format
        // prin apelarea creareSelectare prezentate mai sus.
        Connection conectiune=null;
        PreparedStatement statement=null;
        ResultSet result=null;
        String query=creareSelectare("id");
        try{conectiune= ConnectionFactory.getConnection();
            statement=conectiune.prepareStatement(query);
            statement.setInt(1,id);
            result=statement.executeQuery();
            if(numeTab.equals("produs")){
                if(result.next()) {
                String nume = result.getString("nume");
                int cantitate = result.getInt("cantitate");
                int pret=result.getInt("pret");
                return (T) new Produs(nume, cantitate,pret);}}
            else if(numeTab.equals("client")){
                    if(result.next()) {
                        String nume = result.getString("nume");
                        String adresa = result.getString("adresa");
                        String mail = result.getString("mail");
                        return (T) new Client(nume, adresa, mail);}}
            else if(numeTab.equals("comandaprodus")){
                    if(result.next()) {
                        int idComanda = result.getInt("idComanda");
                        int idProdus = result.getInt("idProdus");
                        int cantitate=result.getInt("cantitate");
                        return (T) new ComandaProdus(idComanda, idProdus,cantitate);}}
        } catch (SQLException e) {LOGGER.log(Level.WARNING,tip.getName()+"DAO:findByID "+e.getMessage());}
        finally{ConnectionFactory.close(result);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conectiune);}
        return null;}

    public JTable afisareTabel(JTable tabel){
        //Va executa query-ul realizat in interiorul  metodei, in urma caruia se va returna un JTable care ulterior
        // va fi afisat in interfata in momentul apasarii unui anumit buton
        StringBuilder stringb=new StringBuilder();
        stringb.append("SELECT  *  FROM "    +tip.getSimpleName());
        Connection conectiune=null;
        PreparedStatement statement=null;
        ResultSet result=null;
        JTable table=tabel;
        try{
            conectiune= ConnectionFactory.getConnection();
            statement=conectiune.prepareStatement(stringb.toString());
            result=statement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(result));}
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            ConnectionFactory.close(result);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conectiune);
        }
        return table;
    }

    public JTable afisareTabel2(JTable tabel,String field, String a){
        //Va executa query-ul realizat in interiorul  metodei, in urma caruia se va returna un Jtable
        // ce contine doar inregistrarile in care campul are valoarea ceruta drept al treilea argument  si ulterior va fi afisat in interfata in momentul apasarii unui anumit buton
        StringBuilder stringb=new StringBuilder();
        stringb.append("SELECT  *  FROM "    +tip.getSimpleName()+" Where "+ field+" = "+a);
        Connection conectiune=null;
        PreparedStatement statement=null;
        ResultSet result=null;
        JTable table=tabel;
        try{
            conectiune= ConnectionFactory.getConnection();
            statement=conectiune.prepareStatement(stringb.toString());
            result=statement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(result));}
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            ConnectionFactory.close(result);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conectiune);
        }
        return table;
    }


    public int gasireIDPlus1(){
        StringBuilder stringb=new StringBuilder();
        stringb.append("SELECT Max(id)+1 from "+tip.getSimpleName());
        Connection conectiune=null;
        PreparedStatement ps=null;
        ResultSet result=null;
        int i = 0;
        try {
            conectiune= ConnectionFactory.getConnection();
            ps=conectiune.prepareStatement(stringb.toString());

            result = ps.executeQuery();
            if (result.next()) {
                i=result.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            ConnectionFactory.close(result);
            ConnectionFactory.close(ps);
            ConnectionFactory.close(conectiune);
        }return i ;
    }

}

