package Controlador;

import Database.ConectorDB;
import Model.Gestionador;
import Vista.FinestraPrincipal;
import com.sun.org.apache.bcel.internal.generic.SWITCH;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import static Vista.PanelCercador.SEARCH;
import static Vista.PanelLogin.LOGIN;
import static Vista.PanelLogin.NEW_USER;
import static Vista.PanelRegistrar.REGISTER;

/**
 * <p>
 * Pràctica 2 [BBDD] <br/>
 * LsMovie - El buscador definitiu <br/>
 *
 * <b> Classe: ListenerBotons </b> <br/>
 * Implementa el controlador de FinestraPrincipal
 * </p>
 *
 * @author Clàudia Peiró - cpeiro@salleurl.edu
 * Xavier Roma - xroma@salleurl.edu <br/>
 * Arxius i Bases de Dades <br/>
 * La Salle - Universitat Ramon Llull. <br/>
 * <a href="http://www.salle.url.edu" target="_blank">www.salle.url.edu</a>
 * @version 1.0
 */
public class ListenerBotons implements ActionListener {

    private FinestraPrincipal finestraPrincipal;
    private Gestionador gestionador;
    private ConectorDB localurs;
    private ConectorDB local;


    //CONNNECTAR A BBDD LOCAL

    private static final String userbbdd = "root";
    private static final String passbbdd = "alex";
    private static final String nombbdd = "Movies";



    public ListenerBotons(FinestraPrincipal vista) {
        this.finestraPrincipal = vista;
        this.gestionador = new Gestionador();
        localurs = new ConectorDB(userbbdd, passbbdd, "localhost", 3306, nombbdd);
        localurs.connect();
        System.out.println("Iniciat");

    }

    private static Image getLoginFoto(String login) {
        Image image = null;
        try {
            URL url = new URL("https://estudy.salle.url.edu/fotos2/eac/" + login + ".jpg");
            image = ImageIO.read(url);
        } catch (IOException e) {
            try {
                image = ImageIO.read(new File("./img/default_profile.png"));
            } catch (IOException e1) {
                image = null;
            }
        }
        return image;
    }

    public void actionPerformed(ActionEvent event) {

        switch (event.getActionCommand()) {

            case LOGIN:
                System.out.println(finestraPrincipal.getLogin() + finestraPrincipal.getPassword());

                local = new ConectorDB(finestraPrincipal.getLogin(), finestraPrincipal.getPassword(), "localhost", 3306, "Movies");     //creem una nova connexio a la bbdd amb el usuari introiduiy
                boolean ok = local.connect();           //Si existeix, ens retorna un ok i entrem, en cas contrarti mostrem un error de que no s'ha pogut iniciar sessió
                if (ok) {
                    long startTime = System.nanoTime();

                    try {
                        local = baseDades();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000000000;
                    JOptionPane.showMessageDialog(finestraPrincipal, "Ha tardat: " + duration + " segons");         //Mostrem el temps que ha tardat a importar la bbdd

                    finestraPrincipal.addUser(getLoginFoto(finestraPrincipal.getLogin()), finestraPrincipal.getLogin());
                    finestraPrincipal.swapToSearchPanel();
                    break;

                } else {

                    JOptionPane.showMessageDialog(finestraPrincipal, "Error al iniciar sessió!");
                }
                break;

            case SEARCH:

                fesBusqueda();

                break;

            case NEW_USER:

                finestraPrincipal.swapToRegisterPanel();
                break;

            case REGISTER:

                //funció afegir user a la bbdd
                if (finestraPrincipal.getNewLogin().equals("") || finestraPrincipal.getNewPassword().equals("")) {
                    JOptionPane.showMessageDialog(finestraPrincipal, "Cal omplir tots els camps!");
                } else {
                    try {
                        gestionador.addUser(finestraPrincipal.getNewLogin(), finestraPrincipal.getNewPassword(), localurs);
                        JOptionPane.showMessageDialog(finestraPrincipal, "Usuari registrat amb exit!");
                        finestraPrincipal.swapToLoginPanel();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(finestraPrincipal, "Ja existeix el usuari!");

                    }
                }
                break;

            //Tornem la vista a la pantalla de login
            case "BACK":
                finestraPrincipal.swapToLoginPanel();

        }


    }

    private void fesBusqueda(){

        String orderwhat  = "";
        String orderhow = "";

        //SABER QUIN ORDRE HEM DE POSAR
        switch (finestraPrincipal.getOrderWhat()) {
            case "Movie title":

                orderwhat =  "m.title ";
                break;
            case "Genre":
                orderwhat =  "g.description ";
                break;
            case "Director":
                orderwhat =  "p.name ";
                break;

            case "Country":
                orderwhat=  "m.country ";
                break;

            case "IMDB score":
                orderwhat=  "m.IMDB_score ";
                break;

        }

        //POSEM ASC I DESC AL REVES PERQUE AL FER ADDROW ES GIRA TOT
        if (finestraPrincipal.getOrderHow().equals("ASC")) {
            orderhow= " DESC";
        } else {
            orderhow= " ASC";
        }


        //netejem camps de la busqueda


        ResultSet  rs = null;

            rs = local.selectQuery("CALL search('"+ finestraPrincipal.getJtfActor()+ "','"+ finestraPrincipal.getJtfMovTitle()+ "','"+ finestraPrincipal.getJtfGenre()+"','"+ finestraPrincipal.getJtfDirector()+"','"
                    +finestraPrincipal.getJtfCountry()+ "','"+ orderwhat +"','"+orderhow +"')");

        System.out.println("CALL search('"+ finestraPrincipal.getJtfActor()+ "','"+ finestraPrincipal.getJtfMovTitle()+ "','"+ finestraPrincipal.getJtfGenre()+"','"+ finestraPrincipal.getJtfDirector()+"','"
                +finestraPrincipal.getJtfCountry()+ "','"+ orderwhat +"','"+orderhow +"')");
        finestraPrincipal.clearFields();
        try {
            //Inserim el que ens retorna la querie a la taula

            ResultSetMetaData rsmd = rs.getMetaData();


            while (rs.next()) {



                String titol = rs.getString("title");
                String genere = rs.getString("g.description");
                String Director = rs.getString("p.name");
                String Country = rs.getString("m.country");
                String IMDB_score = rs.getString("m.imdb_score");
                String[] arr = {titol, genere, Director, Country, IMDB_score};
                finestraPrincipal.addResultsRow(arr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funció per descarregar totes les dades del server i importarles a la bbdd local
     * @return
     * @throws SQLException
     */
    public ConectorDB baseDades() throws SQLException {
        ArrayList<String> tables = new ArrayList<>();

        //conexio amb bdd server
        ConectorDB bbdd = new ConectorDB("grup4", "marcalex", "puigpedros.salleurl.edu", 3306, "Movies");
        bbdd.connect();
        //Intent d'script que fa copia general de qualsevol bdd, crea la copia de qualsevol bdd però no insereix la info

        //Guardem els noms de totes les taules que hi ha a la bdd
        ResultSet rs = bbdd.selectQuery("SHOW TABLES");

        while (rs.next()) {
            tables.add(rs.getString("Tables_in_movies"));
        }

        //iterem per totes les taules fent un select de totes les seves columnes i els corresponents tipus
        for (String t : tables) {
            System.out.println("TAULA : " + t);
            ResultSet rsss = bbdd.selectQuery("SHOW COLUMNS FROM " + t);
            ArrayList<String> info = new ArrayList<>();
            ArrayList<String> tipu = new ArrayList<>();

            while (rsss.next()) {

                info.add(rsss.getString("Field"));
                tipu.add(rsss.getString("Type"));
            }

            //creem la taula amb les columnes del mateix tipus que la original
            String querie;
            String querie1;
            int i = 0;
            querie1 = "DROP TABLE IF EXISTS " + t + ";";
            querie = " CREATE TABLE " + t + "(";

            while (i < info.size() - 1) {
                querie += info.get(i) + " " + tipu.get(i) + " , ";
                i++;
            }

            querie += info.get(i) + " " + tipu.get(i) + ");";
            System.out.println(querie1);
            local.insertQuery(querie1);
            System.out.println(querie);
            local.insertQuery(querie);

            //Inserim tota la informacio de les taules del servidor a les locals
            ResultSet rss = bbdd.selectQuery("SELECT * FROM " + t);


            switch (t) {

                case "Actor":
                    local.insertQuery("ALTER TABLE Actor ADD PRIMARY KEY (id_actor)");
                    while (rss.next()) {
                        for (String p : info) {

                            int f = rss.getInt("id_actor");
                            local.insertQuery("INSERT INTO Actor (id_actor) VALUE (" + f + ")");
                        }
                    }
                    break;

                case "Movie":
                    local.insertQuery("ALTER TABLE Movie ADD PRIMARY KEY (id_movie);");

                    while (rss.next()) {

                        int id_movie = rss.getInt("id_movie");
                        String title = rss.getString("title");
                        int id_director = rss.getInt("id_director");
                        int year = rss.getInt("year");
                        int duration = rss.getInt("duration");
                        String country = rss.getString("country");
                        int movie_facebook_likes = rss.getInt("movie_facebook_likes");
                        float imdb_score = rss.getFloat("imdb_score");
                        int gross = rss.getInt("gross");
                        BigDecimal budget = rss.getBigDecimal("budget");

                        local.insertQuery("INSERT INTO Movie(id_movie,title,id_director,year,duration,country, movie_facebook_likes, imdb_score, gross, budget) " +
                                " VALUES (" + id_movie + "," + '"' + title + '"' + "," + id_director + "," + year + "," + duration + ",'" + country + "'," + movie_facebook_likes + "," + imdb_score + "," + gross + "," + budget + ")");

                    }
                    break;
                case "Actor_movie":
                    local.insertQuery("ALTER TABLE actor_Movie ADD PRIMARY KEY (id_actor,id_movie);");
                    while (rss.next()) {
                        int id_actor = rss.getInt("id_actor");
                        int id_movie = rss.getInt("id_movie");
                        local.insertQuery("INSERT INTO Actor_movie (id_actor, id_movie) VALUES (" + id_actor + "," + id_movie + ")");

                    }
                    break;
                case "Director":
                    local.insertQuery("ALTER TABLE Director ADD PRIMARY KEY (id_director);");
                    while (rss.next()) {
                        int id_director = rss.getInt("id_director");

                        local.insertQuery("INSERT INTO Director (id_director) VALUES (" + id_director + ")");

                    }
                    break;

                case "Genre":
                    local.insertQuery("ALTER TABLE Genre ADD PRIMARY KEY (id_genre);");
                    while (rss.next()) {
                        int id_genre = rss.getInt("id_genre");

                        String description = rss.getString("description");

                        local.insertQuery("INSERT INTO Genre (id_genre, description) VALUES (" + id_genre + ",'" + description + "')");

                    }
                    break;

                case "Genre_Movie":

                    local.insertQuery("ALTER TABLE Genre_Movie ADD PRIMARY KEY (id_genre,id_movie);");

                    while (rss.next()) {
                        int id_genre = rss.getInt("id_genre");
                        int id_movie = rss.getInt("id_movie");
                        local.insertQuery("INSERT INTO Genre_Movie (id_genre, id_movie) VALUES (" + id_genre + "," + id_movie + ")");


                    }
                    break;

                case "Person":
                    local.insertQuery("ALTER TABLE Person ADD PRIMARY KEY (id_person );");
                    while (rss.next()) {
                        int id_person = rss.getInt("id_person");
                        String name = rss.getString("name");
                        int facebook_likes = rss.getInt("facebook_likes");

                        local.insertQuery("INSERT INTO Person (id_person, name, facebook_likes) VALUES (" + id_person + "," + '"' + name + '"' + "," + facebook_likes + ")");

                    }
                    break;

            }
        }

        //Afegim les fk a les taules locals
        local.insertQuery("ALTER TABLE Movie ADD FOREIGN KEY (id_director) REFERENCES Director (id_director);");
        local.insertQuery("ALTER TABLE Actor_movie ADD FOREIGN KEY (id_actor) REFERENCES Actor (id_actor);");
        local.insertQuery("ALTER TABLE Actor_movie ADD FOREIGN KEY (id_movie) REFERENCES Movie (id_movie);");
        local.insertQuery("ALTER TABLE Actor ADD FOREIGN KEY (id_actor) REFERENCES Person (id_person);");
        local.insertQuery("ALTER TABLE Director ADD FOREIGN KEY (id_director) REFERENCES Person (id_person);");
        local.insertQuery("ALTER TABLE Genre_movie ADD FOREIGN KEY (id_genre) REFERENCES Genre (id_genre);");
        local.insertQuery("ALTER TABLE Genre_movie ADD FOREIGN KEY (id_movie) REFERENCES Movie (id_movie);");

        return local;
    }


}