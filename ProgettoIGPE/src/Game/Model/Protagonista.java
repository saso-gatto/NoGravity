package Game.Model;

public class Protagonista {

	int stato;
	int x;
	int y;
	int vita;
	
	public static final int DEATH = 0;
	public static final int JUMP = 1;
	public static final int RUN = 2;
	public static final int STOP = 3;
	

		
	public Protagonista(int posInizialeX, int posInzialeY) {
		x=posInizialeX;
		y=posInzialeY;
		stato=3;
		vita=3;
	}
	
	public int getStato() {
		return stato;
	}


	public void setStato(int s) {
		this.stato = s;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x=x;
	}
	
	public void setY(int y) {
		this.y=y;
	}

	public int getVita() {
		return vita;
	}

	public void setVita(int vita) {
		this.vita = vita;
	}
	
	
}
