package Game.Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Game.Model.Game;
import Game.Model.Protagonista;
import Game.View.GraficaProtagonista;

public class ControllerProtagonista extends KeyAdapter {
	private int posY;
	public static boolean move;
	public static boolean jump = false; // booleana che ricorda se il comando vk right sia premuto o meno

	public ControllerProtagonista() {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			Game.getInstance().cambioGravita();
			this.inAvanti();
			jump = false;
			break;
		case KeyEvent.VK_RIGHT:
			move = false;
			// Se non teniamo premuto abbastanza il tasto right ed il personaggio non si è spostato, questo si muoverà di uno
			if (posY == Game.getInstance().getPersonaggio().getY()) 
				Game.getInstance().move();
			GraficaProtagonista.getInstance().cambiaAnimazioni(Protagonista.STOP);
			break;
		}
	}
		
	
	// Se tengo premuto space ed il protagonista tocca il terreno, aggiorno qui la sua animazione
	// Senza questo controllo, tenendo premuto Space, quando il protagonista
	// arrivava a toccare pavimento
	// la sua animazione non si sarebbe subito aggiornata.
	
	private void inAvanti() {
		if (Game.getInstance().isOnGround) {
			jump=false;
			Game.getInstance().getPersonaggio().setStato(Protagonista.RUN);
			GraficaProtagonista.getInstance().cambiaAnimazioni(Protagonista.RUN);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyReleased(e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			posY = Game.getInstance().getPersonaggio().getY();
			move = true;
			this.inAvanti();
			break;
		case KeyEvent.VK_SPACE:
			jump = true;
			this.inAvanti();
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		}

	}
}
