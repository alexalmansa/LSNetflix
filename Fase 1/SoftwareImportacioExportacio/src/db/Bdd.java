package db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Bdd {
    private  String userName;
    private  String password;
    private  String db;
    private  int port;
    private  String url = "jdbc:mysql://";// "jdbc:mysql://puigpedros.salleurl.edu:3306/Movies";
    private  Connection conn = null;
    private  Statement s;

    public Bdd(String usr, String pass,String Server, int port, String db) {

        this.userName = usr;
        this.password = pass;
        this.db = db;
        this.port = port;
        this.url+=Server;
        this.url += ":"+port+"/";
        this.url += db;
        System.out.println("URL: "+ url);
    }

}
