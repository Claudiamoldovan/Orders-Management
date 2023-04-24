package dao;

import connection.ConnectionFactory;
import model.Comanda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ComandaDAO extends AbstractDAO<Comanda>{
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insert = "INSERT INTO comanda (idClient,numeClient,suma)" + " VALUES (?,?,?)";
    private static final String findMaxId="SELECT Max(id)+1 from comanda";

    public static int adaugare(Comanda comanda){
        //Primeste ca argument un Comanda si il insereaza in baza de date, in tabelul comanda
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps=null;
        int ID=-1;
        ResultSet result = null;
        try{
            ps=dbConnection.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, comanda.getIdClient());
            ps.setString(2, comanda.getNumeClient());
            ps.setFloat(3, comanda.getSuma());
            ps.executeUpdate();

            result=ps.getGeneratedKeys();
            if (result.next()) {
                ID=result.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(result);
            ConnectionFactory.close(ps);
            ConnectionFactory.close(dbConnection);
        }
        return ID;
    }
    
    public static int maxId(){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps=null;
        ResultSet result= null;
        int i = 0;
        try {
            ps=dbConnection.prepareStatement(findMaxId);
           
            result = ps.executeQuery();
            if (result.next()) {
                i=result.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionFactory.close(result);
            ConnectionFactory.close(ps);
            ConnectionFactory.close(dbConnection);
        }
       
        return i ;
    }


}
