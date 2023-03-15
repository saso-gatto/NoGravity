package Game.Model;

public class Blocco {
	
	public final static char VUOTO = ' ';
	public final static char INIZIO = 'i';
	public final static char CENTRO = 'c';
	public final static char FINE = 'f';
	public final static char SOTTO = 's';
	
	public final static char DANGER = 'd';
	public final static char PORTA = '|';
	
	
	public final static char NEMICO1 = 'x';
	public final static char NEMICO2 = 'y';
	public final static char NEMICO3 = 'w';
	public final static char NEMICO4 = 'z';
	
	public final static char PROTAGONISTA = 'P';

	int x;
	int y;
	private char chiave;

	public Blocco (int x, int y,char chiave) {
		this.x=x;
		this.y=y;
		this.chiave=chiave;
	}

	public char getChiave() {
		return chiave;
	}

	public void setChiave(char chiave) {
		this.chiave = chiave;
	}
}

