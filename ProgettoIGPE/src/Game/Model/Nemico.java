package Game.Model;

//StartX e StartY sono state aggiunte per poter resettare in determinati casi la posizione di un nemico--> es. nemico4
public class Nemico {

	int x;
	int y;
	int startX;
	int startY;
	char tipo;
	int dir;
	int salto;
	boolean segui;
	
	//Segui viene utilizzata nel nemico1 per gestire il salto contro il player.
	//Se il nemico1 ha già saltato per provare a prendere il player, prima di poter effettuare un altro salto
	//Deve finire di muoversi all'interno della piattaforma su cui si trova
	//Viene attivata nel nemico4 solo quando il player si trova distante da esso-->caso gestito nella classe Game
		
	public Nemico(int posInizialeX, int posInizialeY, char tipo) {
		x=posInizialeX;
		y=posInizialeY;
		startX=posInizialeX;
		startY=posInizialeY;
		this.tipo=tipo;
		dir=1;
		salto=2;
		if (tipo!='z')
			segui=true;
		else
			segui=false;
	}
	
	public void resetPosizione() {
		this.x=this.startX;
		this.y=this.startY;
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
	
	public int getTipo() {
		return this.tipo;
	}
	
	public void setTipo(char tipo) {
		this.tipo=tipo;
	}
	
	
	
}

