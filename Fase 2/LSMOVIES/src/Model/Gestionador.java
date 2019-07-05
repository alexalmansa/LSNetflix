package Model;

import Database.ConectorDB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Gestionador {
private ConectorDB usuaris;
    public Gestionador(){

    }

    public boolean comprovaUser(String user, String password){
       ResultSet rs =  usuaris.selectQuery("SELECT login, pass FROM Usuari WHERE login =  '"+user+"'");
        try {
            String pass = "";
            if(rs.next()){
                pass = rs.getString("pass");
            }
            return pass.equals(password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public boolean comprovaExisteixUser(String user) {
        ResultSet rs =  usuaris.selectQuery("SELECT login  FROM Usuari WHERE login =  '"+user+"'");
        try {
            if(rs.next()){
                return false;
            }else {
                return true;
            }

        } catch (SQLException e) {

            return true;
        }    }

    public void addUser(String login, String password, ConectorDB local) throws SQLException{

        local.insertQuery("CALL Register( '"+login+"','"+password+"');");
    }
}
