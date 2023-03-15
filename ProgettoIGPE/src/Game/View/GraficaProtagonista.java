package Game.View;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import Game.Controller.ControllerProtagonista;
import Game.Model.Game;
import Game.Model.Protagonista;
import Game.Setting.Data;


public class GraficaProtagonista {
	//aggiornare la classe inserendo l'indice qui
	private static final String path = "/Game/View/resources/";
	ArrayList <Image> sprites;  //corrente avrï¿½ al suo interno le animazioni che dovrï¿½ mostrare in quel momento 
	Image corrente;
	private HashMap <Integer,ArrayList<Image>> animazioni;
	private int indice;	
	private static GraficaProtagonista instance = null;
	
	public Image getAnimazioneCorrente () {
		return corrente;
	}
	
	public void onGround () {
		if (!Game.getInstance().isOnGround && Game.getInstance().getPersonaggio().getStato()!=Protagonista.DEATH)
			this.cambiaAnimazioni(Protagonista.JUMP);
		else if (Game.getInstance().isOnGround && !ControllerProtagonista.move && Game.getInstance().getPersonaggio().getStato()!=Protagonista.DEATH)
			this.cambiaAnimazioni(Protagonista.STOP);
	}
	
	public void cambiaAnimazioni(int tipo) {
		if (!Game.getInstance().gravity)
			sprites=animazioni.get(tipo+4);
		else
			sprites=animazioni.get(tipo);
		corrente=sprites.get(0);
	}

	public void caricaSprites() {
		String carica="";
		for (int i = 0; i<8;i++) { //Ad ogni i corrisponde una chiave del protagonista
			if (i==Protagonista.DEATH)
				carica="Death";	
			else if (i==Protagonista.JUMP)
				carica="Jump";
			else if (i==Protagonista.RUN)
				carica="Run";
			else if (i==Protagonista.STOP)
				carica="Fermo";
			else if (i==4)
				carica="Capovolte/Death";
			else if (i==5)
				carica="Capovolte/Jump";
			else if (i==6)
				carica="Capovolte/Run";
			else if (i==7)
				carica="Capovolte/Fermo";
			sprites=getResources(carica); //In base al tipo di chiave, ottengo l'arrayList con le relativa animazioni
			animazioni.put(i,sprites);
		}
	}
	
	private ArrayList<Image> getResources(String name) {
		ArrayList<Image> images = new ArrayList<Image>();
		try {
			String percorso = path + name + "/";
			File f = new File(getClass().getResource(percorso).getPath());
			ArrayList<File> listaAnimazioni = new ArrayList<File>();
			for (File r : f.listFiles()) //listFiles è una funzione che ritorna un a con tutti i ifle relativi a quella cartella
				listaAnimazioni.add(r);
			for (File img : listaAnimazioni) {
				Image daAggiungere = ImageIO.read(getClass().getResourceAsStream(percorso + img.getName()));
				daAggiungere=daAggiungere.getScaledInstance(Data.size, Data.size, Image.SCALE_SMOOTH);
				images.add(daAggiungere);
			}
		} catch (Exception e) {	
			System.out.println("Immagine non trovata, getresources error");
		}
		return images;
	}
	

	
	private GraficaProtagonista () {
		animazioni = new HashMap<Integer,ArrayList<Image>>();
		this.caricaSprites();
		int chiave=Game.getInstance().getPersonaggio().getStato();
		sprites=animazioni.get(chiave);
		corrente=sprites.get(0);	
	}
	
	public static GraficaProtagonista getInstance () {
		if (instance==null)
			instance = new GraficaProtagonista();
		return instance;
	}
	
	
	public void update() {
		this.onGround();
		indice++;
		if (indice>=sprites.size())
				indice=0;
		corrente=sprites.get(indice);
	}

}
