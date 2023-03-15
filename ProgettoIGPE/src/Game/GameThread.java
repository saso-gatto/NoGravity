package Game;

import javax.swing.JOptionPane;
import Game.Model.Game;


public class GameThread implements Runnable {
	
	private Game game;
	private int frequenza = 110;
	
	public GameThread () {
		game=Game.getInstance();
	}
	

	@Override
	public void run() {
		while (true) {
			try {
				game.update();
				Thread.sleep(frequenza);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Errore: thread interrotto");
				break;
			}
		}
	}
}
