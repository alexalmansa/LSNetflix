package Vista;

import Controlador.ListenerBotons;

import javax.swing.*;
import java.awt.*;

/**
 * 
 * <p>
 * Pràctica 2 [BBDD] <br/>
 * LsMovie - El buscador definitiu <br/>
 * 
 * <b> Classe: PanelLogin </b> <br/>
 * Implementa un JPanel per realitzar login
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
public class PanelRegistrar extends JPanel {

	public final static String REGISTER = "3";

	private JPanel jpMain;

	private JButton jbLogin;
	private JLabel jlUsername;
	private JTextField jtfUsername;
	private JLabel jlPass;
	private JPasswordField jpfPass;
	private JButton jbBack;

	public PanelRegistrar () {

		jpMain = new JPanel();
		jpMain.setLayout(new GridLayout(3,3));

		jbLogin = new JButton("Registrar");
		jbLogin.setPreferredSize(new Dimension(25,25));
		jbBack = new JButton("Enrere");
		jbBack.setPreferredSize(new Dimension(25,25));

		jlUsername = new JLabel("Usuari: ");
		jtfUsername = new JTextField();
		jtfUsername.setPreferredSize(new Dimension(124,25));
		jpMain.add(jlUsername);
		jpMain.add(jtfUsername);

		jlPass = new JLabel("Contrasenya: ");
		jpfPass = new JPasswordField();
		jpfPass.setPreferredSize(new Dimension(124,25));
		jpMain.add(jlPass);
		jpMain.add(jpfPass);

		jpMain.add(jbBack);
		jpMain.add(jbLogin);


		this.add(jpMain);

	}
	
	public void registreControladorBotons(ListenerBotons controladorBotons) {

		jbLogin.addActionListener(controladorBotons);
		jbLogin.setActionCommand(REGISTER);
		jbBack.addActionListener(controladorBotons);
		jbBack.setActionCommand("BACK");
	}
	
	public String getNewLogin() {
		return jtfUsername.getText();
	}

	public String getNewPassword() {
		return new String(jpfPass.getPassword());
	}
	
}
