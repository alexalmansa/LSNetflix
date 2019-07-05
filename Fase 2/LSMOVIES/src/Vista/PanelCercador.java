package Vista;

import Controlador.ListenerBotons;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * <p>
 * Pràctica 2 [BBDD] <br/>
 * LsMovie - El buscador definitiu <br/>
 *
 * <b> Classe: PanelCercador </b> <br/>
 * Implementa un JPanel per realitzar la cerca
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
public class PanelCercador extends JPanel {

    public final static String SEARCH = "1";
    public final static int MAX_RESULTS = 10;

    public final static String[] COLUMN_NAMES = {"Movie title",
            "Genre",
            "Director",
            "Country",
            "IMDB score"};;

    private JTextField jtfMovTitle;
    private JTextField jtfGenre;
    private JTextField jtfActor;
    private JTextField jtfDirector;
    private JTextField jtfCountry;
    private JButton jbSearch;
    private JTable jtResultats;
    private DefaultTableModel dtmResultats;

    private JComboBox jcbOrderWhat;
    private JComboBox jcbOrderHow;

    public PanelCercador () {
        JPanel jpMain = new JPanel();
        jpMain.setLayout(new BorderLayout());

        JPanel jpCenter = new JPanel(new GridLayout(7,2));
        jtfMovTitle = new JTextField();
        jtfMovTitle.setPreferredSize(new Dimension(200,5));
        jtfGenre = new JTextField();
        jtfGenre.setPreferredSize(new Dimension(200,5));
        jtfActor = new JTextField();
        jtfActor.setPreferredSize(new Dimension(200,5));
        jtfDirector = new JTextField();
        jtfDirector.setPreferredSize(new Dimension(200,5));
        jtfCountry = new JTextField();
        jtfCountry.setPreferredSize(new Dimension(200,5));
        jbSearch = new JButton("Search");
        jcbOrderWhat = new JComboBox(COLUMN_NAMES);
        jcbOrderHow = new JComboBox(new String[]{"ASC", "DESC"});
        JPanel jpOrder = new JPanel();
        jpOrder.add(jcbOrderWhat);
        jpOrder.add(jcbOrderHow);

        jpCenter.add(new JLabel("Movie title: "));
        jpCenter.add(jtfMovTitle);
        jpCenter.add(new JLabel("Genre: "));
        jpCenter.add(jtfGenre);
        jpCenter.add(new JLabel("Actor: "));
        jpCenter.add(jtfActor);
        jpCenter.add(new JLabel("Director: "));
        jpCenter.add(jtfDirector);
        jpCenter.add(new JLabel("Country: "));
        jpCenter.add(jtfCountry);
        jpCenter.add(new JLabel("Order by: "));
        jpCenter.add(jpOrder);
        jpCenter.add(new JPanel());
        jpCenter.add(jbSearch);

        dtmResultats = new DefaultTableModel(COLUMN_NAMES, MAX_RESULTS) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jtResultats = new JTable(dtmResultats);

        JPanel jpSouth = new JPanel();
        jpSouth.setLayout(new BoxLayout(jpSouth, BoxLayout.Y_AXIS));

        JPanel jpTable = new JPanel(new BorderLayout());
        jpTable.add(jtResultats.getTableHeader(), BorderLayout.NORTH);
        jpTable.add(jtResultats, BorderLayout.CENTER);

        jpSouth.add(jpTable);



        jpMain.add(jpCenter, BorderLayout.EAST);

        jpSouth.setBorder(BorderFactory.createTitledBorder("Results"));

        jpMain.add(jpSouth, BorderLayout.SOUTH);

        this.add(jpMain);



    }

    public void registreControladorBotons(ListenerBotons controladorBotons) {

        jbSearch.addActionListener(controladorBotons);
        jbSearch.setActionCommand(SEARCH);
    }

    public String getJtfMovTitle () {
        return jtfMovTitle.getText();
    }

    public String getJtfGenre () {
        return jtfGenre.getText();
    }

    public String getJtfActor () {
        return jtfActor.getText();
    }

    public String getJtfDirector () {
        return jtfDirector.getText();
    }

    public String getJtfCountry () {
        return jtfCountry.getText();
    }

    public String getOrderWhat(){
        return COLUMN_NAMES[jcbOrderWhat.getSelectedIndex()];
    }
    public String getOrderHow(){
        return (String)jcbOrderHow.getSelectedItem();
    }

    public void clearFields () {
        jtfActor.setText("");
        jtfCountry.setText("");
        jtfDirector.setText("");
        jtfGenre.setText("");
        jtfMovTitle.setText("");
        jcbOrderHow.setSelectedIndex(0);
        jcbOrderWhat.setSelectedIndex(0);
        for ( int i = 0; i < MAX_RESULTS; i++ ){
            addResultsRow(new String[]{});
        }
    }

    public void addResultsRow (String [] row) {
        dtmResultats.insertRow(0,row);
        dtmResultats.removeRow(MAX_RESULTS);
    }
}
