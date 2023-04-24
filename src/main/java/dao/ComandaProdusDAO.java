package dao;

import connection.ConnectionFactory;
import model.ComandaProdus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComandaProdusDAO extends AbstractDAO<ComandaProdus>{
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insert = "INSERT INTO comandaprodus (idComanda,idProdus,cantitate)" + " VALUES (?,?,?)";
    private static final String findByIdComanda= "Select * from produs where idComanda=?";

    public static int adaugare(ComandaProdus comanda){
        //Primeste ca argument un Client si il insereaza in baza de date, in tabelul client
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps=null;
        int ID=-1;
        try{
            ps=dbConnection.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, comanda.getidCmd());
            ps.setInt(2, comanda.getIdProdus());
            ps.setInt(3, comanda.getCantitate());
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
    public static ComandaProdus gasireIDCmd(int id){
        //Primeste ca argument o valoare int si returneaza Clientul ce are acel id
        ComandaProdus toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = dbConnection.prepareStatement(findByIdComanda);
            ps.setLong(1, id);
            result = ps.executeQuery();
            if( result.next()) {
                int idComanda = result.getInt("idComanda");
                int idProdus = result.getInt("idProdus");
                int cantitate=result.getInt("cantitate");
                toReturn = new ComandaProdus(idComanda, idProdus,cantitate);
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
