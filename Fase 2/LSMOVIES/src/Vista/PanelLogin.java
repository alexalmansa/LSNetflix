package Vista;

import java.awt.*;
import javax.swing.*;

import Controlador.ListenerBotons;
import com.sun.org.apache.regexp.internal.RE;

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
public class PanelLogin extends JPanel {

	public final static String LOGIN = "0";
	public final static String NEW_USER = "4";


	private JPanel jpMain;

	private JButton jbLogin;
	private JButton jbRegister;
	private JLabel jlUsername;
	private JTextField jtfUsername;
	private JLabel jlPass;
	private JPasswordField jpfPass;
	
	public PanelLogin () {

		jpMain = new JPanel();
		jpMain.setLayout(new GridLayout(3,3));

		jbRegister = new JButton("No tinc compte");

		jbLogin = new JButton("Entrar");
		jbLogin.setPreferredSize(new Dimension(25,25));

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
		jpMain.add(jbRegister);
		jpMain.add(jbLogin);




		this.add(jpMain);

	}
	
	public void registreControladorBotons(ListenerBotons controladorBotons) {

		jbLogin.addActionListener(controladorBotons);
		jbLogin.setActionCommand(LOGIN);
		jbRegister.addActionListener(controladorBotons);
		jbRegister.setActionCommand(NEW_USER);
	}
	
	public String getLogin() {
		return jtfUsername.getText();
	}

	public String getPassword() {
		return new String(jpfPass.getPassword());
	}
	
}
