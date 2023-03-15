package Game;

import javax.swing.JOptionPane;

import Game.Model.Protagonista;
import Game.View.GraficaProtagonista;
import Game.View.PannelloGrafico;


public class ThreadGrafico implements Runnable {
	
	private PannelloGrafico pg;
	private int frequenza = 80;
	
	public ThreadGrafico (PannelloGrafico pg) {
		this.pg=pg;
	}

	public static void attendi () {
		try {
			GraficaProtagonista.getInstance().cambiaAnimazioni(Protagonista.DEATH);
			Thread.sleep(690);
			GraficaProtagonista.getInstance().cambiaAnimazioni(Protagonista.STOP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (true) { 
			try {
				pg.update();
				GraficaProtagonista.getInstance().update();
				Thread.sleep(frequenza);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Errore: thread interrotto");
				break;
			}
		}
	}
}
