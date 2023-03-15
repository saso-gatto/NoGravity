package Game;

import javax.swing.JFrame;
import Game.Setting.Data;
import Game.View.Contenitore;


public class Main {
//Nella JFrame inseriamo esclusivamente la classe contenitore attraverso la quale gestiamo tutti i pannelli del progetto
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(1200,700);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		new Data(); //Classe che fornisce le informazioni riguardo le dimensioni dei blocchi e della finistra del gioco
		f.setUndecorated(true);
		f.add(Contenitore.getInstance());
		Contenitore.getInstance().impostaMenuStart();
		f.setLocationRelativeTo(null); //La finestra viene posizionata al centro
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
