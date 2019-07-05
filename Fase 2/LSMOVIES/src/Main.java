
import javax.swing.SwingUtilities;

import Controlador.ListenerBotons;
import Database.ConectorDB;
import Vista.FinestraPrincipal;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * <p>
 * Pràctica 2 [BBDD] <br/>
 * LsMovie - El buscador definitiu <br/>
 *
 * <b> Classe: Main </b> <br/>
 * Cervell
 * </p>
 *
 * @author Clàudia Peiró - cpeiro@salleurl.edu <br/>
 * Xavier Roma - xroma@salleurl.edu <br/>
 * Arxius i Bases de Dades <br/>
 * La Salle - Universitat Ramon Llull. <br/>
 * <a href="http://www.salle.url.edu" target="_blank">www.salle.url.edu</a>
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {


                FinestraPrincipal vista = new FinestraPrincipal();
                ListenerBotons controlador = new ListenerBotons(vista);
                vista.registreControladorBotons(controlador);

                vista.setVisible(true);
            }
        });
    }


}
