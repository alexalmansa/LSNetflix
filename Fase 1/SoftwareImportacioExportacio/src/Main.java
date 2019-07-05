import db.ConectorDB;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {


        //Conexio amb bdd local
        ConectorDB local = new ConectorDB("root", "alex", "localhost", 3306, "Movies");
        local.connect();
        //conexio amb bdd server
        ConectorDB bbdd = new ConectorDB("grup4", "marcalex", "puigpedros.salleurl.edu", 3306, "Movies");
        bbdd.connect();
//Intent d'script que fa copia general de qualsevol bdd, crea la copia de qualsevol bdd però no insereix la info
        local.insertQuery("DROP DATABASE Movies");
        local.insertQuery("CREATE DATABASE Movies");
        local.insertQuery("Use Movies");
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


    }

}
