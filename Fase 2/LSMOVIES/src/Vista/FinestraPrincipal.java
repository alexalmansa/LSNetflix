package Vista;

import Controlador.ListenerBotons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * <p>
 * Pràctica 2 [BBDD] <br/>
 * LsMovie - El buscador definitiu <br/>
 *
 * <b> Classe: FinestraPrincipal </b> <br/>
 * Implementa un JFrame
 * </p>
 *
 * @version 1.0
 * @author  Clàudia Peiró - cpeiro@salleurl.edu <br/>
 * 			Xavier Roma - xroma@salleurl.edu <br/>
 * 			Arxius i Bases de Dades <br/>
 * 			La Salle - Universitat Ramon Llull. <br/>
 * 			<a href="http://www.salle.url.edu" target="_blank">www.salle.url.edu</a>
 *
 */
public class FinestraPrincipal extends JFrame{

    private PanelLogin login;
    private PanelRegistrar register;
    private PanelCercador cercador;

    private static final Color HEADER_COLOR = new Color(25, 23, 24);
    private static final Color FONT_COLOR = Color.white;

    private JPanel jpContingut;
    private JPanel jpCapcalera;

    public FinestraPrincipal(){
        setSize(600,600);
        setLocationRelativeTo(null);
        setTitle("LSMovie");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        construeixCapçalera();
        this.add(jpCapcalera, BorderLayout.NORTH);

        login = new PanelLogin();
        cercador = new PanelCercador();
        register = new PanelRegistrar();
        jpContingut = new JPanel(new GridBagLayout());

        jpContingut.setAlignmentX(Component.CENTER_ALIGNMENT);
        jpContingut.add(login);

        this.add(jpContingut, BorderLayout.CENTER);




    }

    public void addUser (Image image, String login){

        JPanel jpEast = new JPanel();
        jpEast.setLayout(new BoxLayout(jpEast, BoxLayout.LINE_AXIS));
        JLabel jlLogin = new JLabel(login);
        jlLogin.setForeground(FONT_COLOR);
        jpEast.add(jlLogin);
        ImageIcon  imageIcon = new ImageIcon();
        imageIcon.setImage(image.getScaledInstance(48,71,0));
        JLabel fotoUsuari = new JLabel(imageIcon);
        fotoUsuari.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpEast.add(fotoUsuari);
        jpEast.setBackground(HEADER_COLOR);

        jpCapcalera.add(jpEast, BorderLayout.EAST);
    }

    private void construeixCapçalera (){
        jpCapcalera = new JPanel();
        jpCapcalera.setLayout(new BorderLayout());

        try {
            ImageIcon  imageIcon = null;
            imageIcon = new ImageIcon(ImageIO.read(new File("./img/logo.png")));

            JLabel logoPic = new JLabel(imageIcon);
            jpCapcalera.add(logoPic, BorderLayout.CENTER);
        } catch (IOException e) {

        }

        jpCapcalera.setBackground(HEADER_COLOR);

    }

    public void registreControladorBotons(ListenerBotons controladorBotons) {

        login.registreControladorBotons(controladorBotons);
        cercador.registreControladorBotons(controladorBotons);
        register.registreControladorBotons(controladorBotons);
    }


    public void swapToSearchPanel () {
        login.setVisible(false);
        register.setVisible(false);
        cercador.setVisible(true);
        jpContingut.add(cercador);
    }

    public void swapToRegisterPanel () {
        login.setVisible(false);
        register.setVisible(true);
        cercador.setVisible(false);
        jpContingut.add(register);
    }

    public void swapToLoginPanel () {
        login.setVisible(true);
        register.setVisible(false);
        cercador.setVisible(false);
        jpContingut.add(login);
    }

    public String getLogin() {
        return login.getLogin();
    }


    public String getPassword() {
        return login.getPassword();
    }

    public String getNewLogin() {
        return register.getNewLogin();
    }


    public String getNewPassword() {
        return register.getNewPassword();
    }

    public String getJtfMovTitle () {
        return cercador.getJtfMovTitle();
    }

    public String getJtfGenre () {
        return cercador.getJtfGenre();
    }

    public String getJtfActor () {
        return cercador.getJtfActor();
    }

    public String getJtfDirector () {
        return cercador.getJtfDirector();
    }

    public String getJtfCountry () {
        return cercador.getJtfCountry();
    }

    public String getOrderWhat(){
        return cercador.getOrderWhat();
    }
    public String getOrderHow(){
        return cercador.getOrderHow();
    }

    public void clearFields () {
        cercador.clearFields();
    }

    /**
     * Afegeix una nova fila a la primera posició i es corren les presents
     *
     * @param row fila per afegir
     */
    public void addResultsRow (String [] row) {
        cercador.addResultsRow(row);
    }
}

//https://estudy.salle.url.edu/fotos2/eac/claudia.peiro.2015.jpg