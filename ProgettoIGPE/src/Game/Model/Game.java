package Game.Model;

import java.util.ArrayList;

import Game.ThreadGrafico;
import Game.Controller.ControllerProtagonista;
import Game.View.Contenitore;
import Game.View.PannelloGrafico;

public class Game {

	private Blocco[][] matrice;
	private static Game game = null;
	private Protagonista player;
	private int righe; // Inseriamo temporaneamente le dim della matrice
	private int colonne;
	private ArrayList<Nemico> nemici;
	private String livelloCorrente;
	public boolean isOnGround;
	public boolean gravity;

	public String getPunteggio() {
		int punteggio=(player.y*100)-300; //La pos iniziale e' 3, senza -300 il punteggio partirebbe da 300
		String score = String.valueOf(punteggio);
		return score;
	}
	
	public int getRighe() {
		return righe;
	}

	public int getColonne() {
		return colonne;
	}

	//Il personaggio viene creato impostando la gravità del gioco verso il basso
	private Game() {
		gravity = true;
	}

	public Blocco[][] getMatrice() {
		return matrice;
	}

	private void aggiungiNemici() {
		nemici = new ArrayList<Nemico>();
		for (int i = 0; i < righe; i++)
			for (int j = 0; j < colonne; j++) {
				if (matrice[i][j].getChiave() == Blocco.NEMICO1)
					nemici.add(new Nemico(i, j, Blocco.NEMICO1));

				else if (matrice[i][j].getChiave() == Blocco.NEMICO2)
					nemici.add(new Nemico(i, j, Blocco.NEMICO2));

				else if (matrice[i][j].getChiave() == Blocco.NEMICO3)
					nemici.add(new Nemico(i, j, Blocco.NEMICO3));
				
				else if (matrice[i][j].getChiave() == Blocco.NEMICO4)
					nemici.add(new Nemico(i, j, Blocco.NEMICO4));
			}

	}

	public void resetPosizione() {
		player.x = GestioneMappa.posInizialeX;
		player.y = GestioneMappa.posInzialeY;
	}

	public void resetPlayer() {
		player.x = GestioneMappa.posInizialeX;
		player.y = GestioneMappa.posInzialeY;
		player.vita = 3;
		player.stato = Protagonista.STOP;
		this.isOnGround = false;
		gravity = true;
	}

	public void impostaMappa(String livello, boolean aggiorna) { 
		GestioneMappa gestione = new GestioneMappa();
		gestione.caricaMappa(livello);
		livelloCorrente = livello;
		matrice = gestione.creaMatrice();
		this.righe = gestione.getRiga();
		this.colonne = gestione.getColonna();
		if (aggiorna) {
			player.setX(GestioneMappa.posInizialeX);
			player.setY(GestioneMappa.posInzialeY);
		}
		else
			player = new Protagonista(GestioneMappa.posInizialeX, GestioneMappa.posInzialeY);
		aggiungiNemici();
	}


	public static Game getInstance() { // ottiene l'istanza del gioco in modo da bloccare la creazione di nuovi Game
		if (game == null)
			game = new Game();
		return game;
	}

	public Protagonista getPersonaggio() {
		return player;
	}

	public boolean collisioniSupInf(int x, int y) { // Torna true se vi e' una collissione con i blocchi in pos x y
		if (matrice[x][y].getChiave() == Blocco.INIZIO || matrice[x][y].getChiave() == Blocco.CENTRO
				|| matrice[x][y].getChiave() == Blocco.FINE) {
			isOnGround = true;
			return true;
		} else if (matrice[x][y].getChiave() == Blocco.VUOTO)
			isOnGround = false;
		if (matrice[x][y].getChiave() == Blocco.DANGER || matrice[x][y].getChiave() == Blocco.NEMICO1
				|| matrice[x][y].getChiave() == Blocco.NEMICO2 || matrice[x][y].getChiave() == Blocco.NEMICO3) {
			PannelloGrafico.getInstance().setFocusable(false); //Per rendere visibile l'animazione di death togliamo il focus dal pannello
			player.stato = Protagonista.DEATH;
			ThreadGrafico.attendi();
			matrice[player.x][player.y].setChiave(Blocco.VUOTO);
			restart();
		}
		return false;
	}

	
	public void gestisciGravita() { // se true va verso il basso
		if (gravity) {
			if (!collisioniSupInf(player.x + 1, player.y)) // Se non c'e' la collissione, il personaggio va verso il basso
			{
				matrice[player.x][player.y].setChiave(Blocco.VUOTO);
				player.x += 1;
				matrice[player.x][player.y].setChiave(Blocco.PROTAGONISTA);
			}
		} else {
			if (!collisioniSupInf(player.x - 1, player.y)) // Se non c'e' la collissione, il personaggio va verso il basso
			{
				matrice[player.x][player.y].setChiave(Blocco.VUOTO);
				player.x -= 1;
				matrice[player.x][player.y].setChiave(Blocco.PROTAGONISTA);
			}
		}

	}

	public void restart() {
		player.vita--;
		ControllerProtagonista.move=false;
		gravity=true;//Così il protagonista inizia con la gravità sempre verso il basso
		if (player.vita == 0) {
			this.isOnGround=true;
			GameOver();
		} else if (player.vita > 0) {
			Contenitore.getInstance().aggiornaVita(player.vita);
			player.stato = Protagonista.JUMP;
			player.setX(GestioneMappa.posInizialeX);
			player.setY(GestioneMappa.posInzialeY);
		}
		PannelloGrafico.getInstance().setFocusable(true);
		PannelloGrafico.getInstance().requestFocus();//Il pannello riguadagna il focus
	}

	public void GameOver() {
		Contenitore.getInstance().impostaPannelloFinale(false);//False: carica l'immagine GameOver
		player.x = GestioneMappa.posInizialeX; //resettiamo la pos del player per non avere interferenza con ciò che c'era nella mappa
		player.y = GestioneMappa.posInzialeY;
		if (livelloCorrente.equals("5")){	//5: sopravvivenza
			for (Nemico e : nemici)
				if (e.getTipo()==Blocco.NEMICO4) { //Resettiamo la posizone del nemico4 in sopravvivenza
					e.resetPosizione();
					e.segui=false;
				}
		}
	}

	public void move() {
		matrice[player.x][player.y].setChiave(Blocco.VUOTO);
		if (matrice[player.x][player.y + 1].getChiave() != Blocco.SOTTO
				&& matrice[player.x][player.y + 1].getChiave() != Blocco.CENTRO
				&& matrice[player.x][player.y + 1].getChiave() != Blocco.INIZIO
				&& matrice[player.x][player.y + 1].getChiave() != Blocco.FINE) { 
			player.y += 1; // muoviti a destra
		}
		matrice[player.x][player.y].setChiave(Blocco.PROTAGONISTA);

	}
	
	public void cambioGravita() {
		if (gravity)
			gravity = false;
		else
			gravity = true;

	}
	
	
	public float getPosX() {
		return player.x;
	}

	public float getPosY() {
		return player.y;
	}

	private void nemicoIntelligente(Nemico e, int soffitto) { // se soffitto<0
		matrice[e.x][e.y].setChiave(Blocco.VUOTO);
		if (player.y == e.y && e.segui) { //Gestione salto del nemico
			//La variabile salto indica di quanto il nemico si può ancora spostare in verticale
			//In base a dove si trovi il nemico, il salto viene effettuato sottraendo soffitto
			if (e.salto > 0 && ((soffitto<0 && e.x<4) || (soffitto>0 && e.x>=6))) {
				e.x -= soffitto;
				e.salto--;
			} else { //Reset della gestione salto del nemico
				e.x += 2*soffitto;
				e.salto = 2;
				e.segui = false;
			}
			matrice[e.x][e.y].setChiave(Blocco.NEMICO1);
		} else { //Gestione del movimento
			if (e.salto < 2) {
				while (matrice[e.x + soffitto][e.y].getChiave() != Blocco.CENTRO
						&& matrice[e.x + soffitto][e.y].getChiave() != Blocco.INIZIO
						&& matrice[e.x + soffitto][e.y].getChiave() != Blocco.FINE) {
					e.x += soffitto;
				}
				e.salto = 2;
				e.segui = false;
			}

			if (matrice[e.x + soffitto][e.y].getChiave() == Blocco.CENTRO)
				e.y += e.dir;
			else {
				e.dir *= -1;
				e.segui = true;
				e.y += e.dir;
			}
		}
		matrice[e.x][e.y].setChiave(Blocco.NEMICO1);

	}
	
	private void nemicoSopravvivenza(Nemico e) {
		//1. Verificare se il nemico si trovi nelle stesse ascissa verticale (X), se non rispetta tale condizione, aumentare la y fino a quando 
		//non assume la stessa componente X del player
		//2. Verificare se il player si trovi sopra o sotto il nemico e in caso seguirlo
		
		if (player.vita>0)
		{	
				matrice[e.x][e.y].setChiave(Blocco.VUOTO);
				
				if(e.getY()<player.y) {
					e.y+=1;
				}	
				else 
				{
					if (player.x>e.getX()) //il protagonista si trova sotto il nemico
						e.x+=1;
					else if (player.x<e.getX())
						e.x-=1;		
				}
				matrice[e.x][e.y].setChiave(Blocco.NEMICO4);
		}
	}

	private void gestisciNemico() {
		for (Nemico e : nemici) {
			if (e.getTipo() == Blocco.NEMICO1) // orizzontale a terra
			{	if (e.getX()<=6) //Fino alla riga 6 il nemico si comparta come se fosse sul soffitto
					nemicoIntelligente(e,-1); //-1 e' sul soffitto
				else
					nemicoIntelligente (e,1);	
			}
			else if (e.getTipo() == Blocco.NEMICO2) // orizzontale volante
			{
				matrice[e.x][e.y].setChiave(Blocco.VUOTO);

				if (matrice[e.x][e.y + 1].getChiave() != Blocco.CENTRO
						&& matrice[e.x][e.y - 1].getChiave() != Blocco.CENTRO
						&& matrice[e.x][e.y + 1].getChiave() != Blocco.INIZIO
						&& matrice[e.x][e.y - 1].getChiave() != Blocco.INIZIO
						&& matrice[e.x][e.y + 1].getChiave() != Blocco.FINE
						&& matrice[e.x][e.y - 1].getChiave() != Blocco.FINE
						&& matrice[e.x][e.y + 1].getChiave() != Blocco.SOTTO
						&& matrice[e.x][e.y - 1].getChiave() != Blocco.SOTTO)
					e.y += e.dir;
				else {
					e.dir *= -1;
					e.segui = true;
					e.y += e.dir;
				}

				matrice[e.x][e.y].setChiave(Blocco.NEMICO2);
			}

			else if (e.getTipo() == Blocco.NEMICO3) // verticale
			{
				matrice[e.x][e.y].setChiave(Blocco.VUOTO);
				if (matrice[e.x - 1][e.y].getChiave() != Blocco.CENTRO
						&& matrice[e.x + 1][e.y].getChiave() != Blocco.CENTRO)
					e.x += e.dir;
				else {
					e.dir *= -1;
					e.x += e.dir;
				}

				matrice[e.x][e.y].setChiave(Blocco.NEMICO3);
			}
			else if (e.getTipo() == Blocco.NEMICO4) {
				
				if(player.y-e.getY()>=14)
					e.segui=true;
				if (e.segui)
					nemicoSopravvivenza(e);
			}
			
			
			if (e.getX() == player.x && e.getY() == player.y) // collisione con il protagonista
			{
				PannelloGrafico.getInstance().setFocusable(false);
				player.stato = Protagonista.DEATH;
				matrice[e.x][e.y].setChiave(Blocco.PROTAGONISTA);
				ThreadGrafico.attendi(); // mostra animazioni morte del protagonista, in questo frangente il gioco viene fermato
				matrice[player.x][player.y].setChiave(Blocco.VUOTO); // Togliamo lo sprites del protagonista dalla pos in cui si trovava
				restart();
			}

		}

	}

	public void cambiaLivello() {
		if (livelloCorrente.equals("1"))
			PannelloGrafico.getInstance().aggiornaMappa("2");
		else if (livelloCorrente.equals("2"))
			PannelloGrafico.getInstance().aggiornaMappa("3");
		else if (livelloCorrente.equals("3"))
			PannelloGrafico.getInstance().aggiornaMappa("4");
		else if (livelloCorrente.equals("4") || livelloCorrente.equals("5")) {
			Contenitore.getInstance().impostaPannelloFinale(true);
			Game.getInstance().resetPlayer();
			//La pos del player viene resettata affinche quest'ultimo non risulta essere sempre nella posizione della porta
		}
	}

	public boolean collisionePorta() {
		if (player.x == GestioneMappa.posPortaX && player.y == GestioneMappa.posPortaY)
			return true;
		return false;
	}

	public void update() { // Gestione del movimento
		if (ControllerProtagonista.move) //Teniamo premuto vk_right --> move=true
			move();
		gestisciNemico();
		gestisciGravita();
		if (collisionePorta())
			cambiaLivello();
	}

	
}