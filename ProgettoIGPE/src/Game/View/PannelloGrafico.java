package Game.View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import Game.GameThread;
import Game.ThreadGrafico;
import Game.Controller.ControllerProtagonista;
import Game.Model.Blocco;
import Game.Model.Game;
import Game.Setting.Data;

public class PannelloGrafico extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image sfondo;
	public Camera camera;
	private ArrayList<Image> blocchi;
	private static PannelloGrafico instance = null;
	private Sound s;
	private String livelloCorrente;
	public ExecutorService es = Executors.newCachedThreadPool();

	public String getLivelloCorrente() {
		return this.livelloCorrente;
	}

	public void stopSound() {
		s.stop();
	}

	public static PannelloGrafico getInstance() {
		if (instance == null)
			instance = new PannelloGrafico();
		return instance;
	}

	// Impostando 0 come livelloCorrente, capisco che pannelloGrafico debba
	// inizializzare la matrice di blocchi per le mappe
	private PannelloGrafico() {
		this.livelloCorrente = "0";
	}

	public synchronized void creaMappa(String liv) {
		this.livelloCorrente = liv;
		Game.getInstance().impostaMappa(livelloCorrente,false);//False: creo il protagonista, non aggiorno
		camera = new Camera(Game.getInstance().getPersonaggio(), Data.width, Data.height,
				Data.size * Data.NUM_BLOCCHI_X, Data.size * Data.NUM_BLOCCHI_Y); // Creazione della camera
		this.addKeyListener(new ControllerProtagonista());
		this.setFocusable(true);
		es.submit(new ThreadGrafico(this));
		es.submit(new GameThread());
		caricaImmagini(livelloCorrente);
		s = new Sound("1.wav");
		s.start();
	}

	public synchronized void aggiornaMappa(String liv) {
		this.livelloCorrente = liv;
		Game.getInstance().impostaMappa(liv,true); //True: aggiorno solo la pos del protagonista
		caricaImmagini(liv);
		s.start();
	}

	private Image ottieniBlocco(char chiave) {
		if (chiave == Blocco.INIZIO)
			return blocchi.get(0);
		else if (chiave == Blocco.CENTRO)
			return blocchi.get(1);
		else if (chiave == Blocco.FINE)
			return blocchi.get(2);
		else if (chiave == Blocco.SOTTO)
			return blocchi.get(3);
		else if (chiave == Blocco.DANGER)
			return blocchi.get(4);
		else if (chiave == Blocco.PORTA)
			return blocchi.get(5);
		else if (chiave == Blocco.NEMICO1)
			return blocchi.get(6);
		else if (chiave == Blocco.NEMICO2)
			return blocchi.get(7);
		else if (chiave == Blocco.NEMICO3)
			return blocchi.get(8);
		else if (chiave == Blocco.NEMICO4)
			return blocchi.get(9);

		return null;
	}

	private void caricaImmagini(String liv) {
		blocchi = new ArrayList<Image>();
		for (int i = 0; i < 10; i++) {
			BufferedImage blocco = caricaImmagine("/Game/View/Map" + liv + "/" + i + ".png"); 
			blocchi.add(blocco);
		}
		if (livelloCorrente.equals("5")) {
			sfondo = caricaImmagine("/Game/View/resources/SfondoSopravvivenza.jpg");
			Game.getInstance().getPersonaggio().setVita(1);
		} else
			sfondo = caricaImmagine("/Game/View/resources/SfondoCompleto.png");
	}

	public BufferedImage caricaImmagine(String filename) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResourceAsStream(filename));
		} catch (IOException e) {
			System.out.println("impossibile trovare l'immagine");
		}
		return img;
	}

	//Per ruotare i blocchi sfruttiamo la classe AffineTrasform. Questa ci permette di ruotare l'immagine a nostro piacimento
	//getScaleInstance di affineTransform permette di "scalare/ruotare" l'immagine in base ai valori inseriti. -1 -1 equivalgono ad angolo di 180°
	private Image ruotaBlocco(char tipo, int i, int j) {
		BufferedImage daRuotare = (BufferedImage) ottieniBlocco(tipo);
		AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
		tx.translate(-daRuotare.getWidth(null), -daRuotare.getHeight(null));
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		daRuotare = op.filter(daRuotare, null);
		return daRuotare;
	}

	//Utilizziamo Graphics2D in quanto è preferibile visto l'uso di AffineTransfrom
	//NB: Graphics2D è un oggetto che estende Graphics, è incluso nella libreria standard di Java
	private void drawMatrice(Graphics2D g) {
		Blocco[][] daDisegnare = Game.getInstance().getMatrice();
		int righe = Game.getInstance().getRighe();
		int colonne = Game.getInstance().getColonne();
		g.drawImage(sfondo, 0, 0, Data.NUM_BLOCCHI_X * Data.size, Data.size * Data.NUM_BLOCCHI_Y, null);
		for (int i = 0; i < righe; i++) {
			for (int j = 0; j < colonne; j++) {
				if (daDisegnare[i][j].getChiave() != Blocco.PROTAGONISTA && daDisegnare[i][j].getChiave() != ' ') {
					char chiave = daDisegnare[i][j].getChiave();
					Image blocco = ottieniBlocco(chiave);
					if (i > 0 && i < 4) {
						blocco = ruotaBlocco(daDisegnare[i][j].getChiave(), i, j);
						g.drawImage(blocco, j * Data.size, i * Data.size, Data.size, Data.size, null);
					} else
						g.drawImage(blocco, j * Data.size, i * Data.size, Data.size, Data.size, null);
				}
				if (daDisegnare[i][j].getChiave() == Blocco.PROTAGONISTA)
					g.drawImage(GraficaProtagonista.getInstance().getAnimazioneCorrente(), j * Data.size, i * Data.size,null);
			}
		}
	}

	@Override
	protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.translate((int) -camera.y, (int) -camera.x); // il paint avviene da camX e camY FORSE QUI
		drawMatrice(g2);
		g2.translate((int) camera.y, (int) camera.x); 
	}

	public synchronized void update() {
		if (!livelloCorrente.equals("0")) {
			camera.update(Game.getInstance().getPersonaggio()); 
			repaint();
			if (Contenitore.getInstance().getSound()) { 
				s.loop();
			}
		}

	}

}
