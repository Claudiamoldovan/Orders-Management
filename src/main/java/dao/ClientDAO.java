package dao;

import connection.ConnectionFactory;
import model.*;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO extends AbstractDAO<Client>{
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insert = "INSERT INTO client (nume,adresa,mail)" + " VALUES (?,?,?)";
    private static final String delete = "DELETE FROM client where nume = ? AND adresa = ? AND mail = ? AND id=?";
    private static final String update =  "UPDATE client SET nume = ?, adresa = ?, mail =? WHERE id = ?";
    private static final String findById= "Select * from client where id = ?";


    public static Client gasireID(int id){
        Client toReturn = null;
//Primeste ca argument o valoare int si returneaza Clientul ce are acel id
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = dbConnection.prepareStatement(findById);
            ps.setLong(1, id);
            result = ps.executeQuery();
            if( result.next()) {
                String nume = result.getString("nume");
                String adresa = result.getString("adresa");
                String mail=result.getString("mail");
                toReturn = new Client(nume, adresa,mail);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(result);
            ConnectionFactory.close(ps);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int adaugare(Client client){
        //Primeste ca argument un Client si il insereaza in baza de date, in tabelul client
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps=null;
        int ID=-1;
        try{
            ps=dbConnection.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,client.getNume());
            ps.setString(2, client.getAdresa());
            ps.setString(3, client.getMail());
            ps.executeUpdate();

            ResultSet result=ps.getGeneratedKeys();
            if (result.next()) {
                ID=result.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(ps);
            ConnectionFactory.close(dbConnection);
        }
        return ID;
    }

    public static int stergere(Client client,int id){
        //Primeste ca argument un Client si id-ul sau si il sterge din baza de date, din tabelul client
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps=null;
        int ID=-1;
        try{
            ps=dbConnection.prepareStatement(delete, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,client.getNume());
            ps.setString(2, client.getAdresa());
            ps.setString(3, client.getMail());
            ps.setInt(4,id);
            ps.executeUpdate();

            ResultSet result=ps.getGeneratedKeys();
            if (result.next()) {
                ID=result.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(ps);
            ConnectionFactory.close(dbConnection);
        }
        return ID;
    }

    public static void update(int id, Client c){
        //Primeste ca argument un Client si id-ul sau si il editeaza in baza de date, in tabelul client
            Connection dbConnection = ConnectionFactory.getConnection();
            PreparedStatement ps = null;
            try {
                ps = dbConnection.prepareStatement(update);
                ps.setString(1, c.getNume());
                ps.setString(2, c.getAdresa());
                ps.setString(3, c.getMail());
                ps.setInt(4,id);
                ps.executeUpdate();
            }catch(SQLException e) {
                System.out.println("Client dao update: " + e.getMessage());
            }finally {
                ConnectionFactory.close(ps);
                ConnectionFactory.close(dbConnection);
            }
    }
    
}
