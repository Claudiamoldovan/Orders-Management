package dao;

import connection.ConnectionFactory;
import model.*;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdusDAO extends AbstractDAO<Produs>{
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insert = "INSERT INTO produs (nume,cantitate,pret)" + " VALUES (?,?,?)";
    private static final String delete = "DELETE FROM produs where nume = ? AND cantitate = ? AND pret = ? AND id = ?";
    private static final String update =  "UPDATE produs SET nume = ?, cantitate = ?, pret =? WHERE id = ?";
    private static final String findById= "Select * from produs where id=?";

    public static int adaugare(Produs produs){
        //Primeste ca argument un Produs si il insereaza in baza de date, in tabelul produs
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps=null;
        int ID=-1;
        try{
            ps=dbConnection.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,produs.getNume());
            ps.setInt(2, produs.getCantitate());
            ps.setFloat(3, produs.getPret());
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

    public static int stergere(Produs produs,int id){
        //Primeste ca argument un Produs si id-ul sau si il sterge din baza de date, din tabelul produs
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps=null;
        int ID=-1;
        try{
            ps=dbConnection.prepareStatement(delete, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,produs.getNume());
            ps.setInt(2, produs.getCantitate());
            ps.setFloat(3, produs.getPret());
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

    public static void update(int id, Produs produs){
        //Primeste ca argument un Produs si id-ul sau si il editeaza in baza de date, in tabelul produs
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        try {
            ps = dbConnection.prepareStatement(update);
            ps.setString(1,produs.getNume());
            ps.setInt(2, produs.getCantitate());
            ps.setFloat(3, produs.getPret());
            ps.setInt(4,id);
            ps.executeUpdate();
        }catch(SQLException e) {
            System.out.println("Client dao update: " + e.getMessage());
        }finally {
            ConnectionFactory.close(ps);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static Produs gasireID(int id){
        //Primeste ca argument o valoare int si returneaza Produs ce are acel id din tabelul produs
        Produs toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = dbConnection.prepareStatement(findById);
            ps.setLong(1, id);
            result = ps.executeQuery();
            if( result.next()) {
                String nume = result.getString("nume");
                int cantitate = result.getInt("cantitate");
                int pret=result.getInt("pret");
                toReturn = new Produs(nume, cantitate,pret);
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
}
