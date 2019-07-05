package Database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConectorDB {
    public  String userName;

	private  String password;
	private  String db;
	private  int port;
	private  String url = "jdbc:mysql://";// "jdbc:mysql://puigpedros.salleurl.edu:3306/Movies";
	private  Connection conn = null;
	private  Statement s;

    public ConectorDB(String usr, String pass,String Server, int port, String db) {


        this.userName = usr;
        this.password = pass;
        this.db = db;
        this.port = port;
        this.url+=Server;
        this.url += ":"+port+"/";
        this.url += db;
        System.out.println("URL: "+ url);
    }

    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = (Connection) DriverManager.getConnection(url, userName, password);
            if (conn != null) {
                System.out.println("Conexió a base de dades "+url+" ... Ok");
            }
            return true;
        }
        catch(SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Problema al connecta-nos a la BBDD --> "+url);
            return false;
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return false;

    }
    
    public void insertQuery(String query) throws SQLException{

            s =(Statement) conn.createStatement();

            s.executeUpdate(query);






    }

    public void insertQueryProcedure(String query) throws SQLException{
        System.out.println(query);
        s =(Statement) conn.createStatement();

        s.executeUpdate("CALL queri("+'"'+query+'"'+" )");


    }

    public ResultSet selectQueryProcedure(String query) throws SQLException{
        System.out.println(query);
        s =(Statement) conn.createStatement();

       ResultSet a = s.executeQuery(query);

        return a;

    }
    
    public void updateQuery(String query){
    	 try {
             s =(Statement) conn.createStatement();
             s.executeUpdate(query);

         } catch (SQLException ex) {
             System.out.println("Problema al Modificar --> " + ex.getSQLState());
         }
    }
    
    public void deleteQuery(String query){
    	 try {
             s =(Statement) conn.createStatement();
             s.executeUpdate(query);
             
         } catch (SQLException ex) {
             System.out.println("Problema al Eliminar --> " + ex.getSQLState());
         }
    	
    }
    
    public ResultSet selectQuery(String query){
    	ResultSet rs = null;
    	 try {
             s =(Statement) conn.createStatement();
             rs = s.executeQuery (query);
             
         } catch (SQLException ex) {
             System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
             ex.printStackTrace();
         }
		return rs;
    }
    
    public void disconnect(){
    	try {
			conn.close();
            System.out.println("Desconnectat!");
		} catch (SQLException e) {
			System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
		}
    }

}
