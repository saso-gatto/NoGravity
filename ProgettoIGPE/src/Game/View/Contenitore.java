package Game.View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Game.Model.Game;
import Game.Setting.Data;


public class Contenitore extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JPanel menu; 		//Pannello superiore usato solo nei livelli
	private JPanel centro; 		//Pannello centrale 
	private boolean loopSound;
	private static Contenitore contenitore = null;
	
	private Contenitore () {
		this.setLayout(new BorderLayout()); //Il contenitore di default e' caratterizzato da un BorderLayout
		menu=new JPanel();
		centro=new JPanel();
	}
	
	public static Contenitore getInstance() {
		if (contenitore==null)
			contenitore=new Contenitore();
		return contenitore;
	}
	
	public boolean getSound() {
		return this.loopSound;
	}
	
	public Image caricaImmagine (String filename)
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResourceAsStream(filename));
			img.getScaledInstance(Data.width, Data.height, Image.SCALE_FAST);
		}
		catch (IOException e) {
			System.out.println("impossibile trovare l'immagine");
		}
		return img;
	}
	
	public ImageIcon getIcona (String nameImg, int dim) { //Dim equivale alla larghezza dell'icona da scalare nel menu
        Image scalata=null;
        ImageIcon icona=null;
        try {
            BufferedImage legge = ImageIO.read(getClass().getResourceAsStream("/Game/View/resources/"+nameImg));
            scalata = legge.getScaledInstance(dim,Data.size,Image.SCALE_SMOOTH);
            icona = new ImageIcon (scalata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return icona;
    }
	
	
	//Aggiunta del bottone "Indietro" nella Menu Bar dei livelli
	public void aggiungiBottone (String pic) { 
		ImageIcon icona = getIcona(pic,200);
		JButton b1 = new JButton (icona);
		b1.setBorder(null);
		b1.setContentAreaFilled(false);
		b1.addMouseListener(new MouseAdapter() {
			public void mouseClicked (MouseEvent e) {
				PannelloGrafico.getInstance().stopSound();
				if (!PannelloGrafico.getInstance().getLivelloCorrente().equals("5")) { 
					Game.getInstance().resetPosizione();
					PannelloGrafico.getInstance().setVisible(false); //Quando torno indietro oscuro il pannellografico ed il menu
					menu.setVisible(false);
					impostaInterfaccia();
				}
				else { //Se siamo nella modalita' sopravvivenza, resettiamo l'interfaccia
					resetInterfaccia();
				}
			}
		});
		menu.add(b1);
	}
	
	public void aggiornaVita(int v) { //Scorriamo nei componenti del menu, trovata la prima jlabel, aggiorniamo la vita
		for (Component componente : menu.getComponents()) {
			 if (componente instanceof JLabel) { 
		            ((JLabel) componente).setIcon(getIcona(v+"vita.png",160));
		        }
			 break;
		}
	}
	
	public void impostaMenuStart() {
		if (menu!=null)
			menu.setVisible(false); //Se in passato e' stato creato un MenuBar, viene oscurato
		if (centro!=null)
			this.remove(centro);
		this.loopSound=false;
		this.centro= new MenuStart();
		this.add(centro, BorderLayout.CENTER);
		revalidate();
		centro.requestFocus();
	}
	
	public void impostaInterfaccia() {
		if (centro!=null)
			this.remove(centro);
		this.loopSound=false;
		this.centro=new InterfacciaLivelli();
		this.add(centro, BorderLayout.CENTER);
		revalidate();
		this.centro.requestFocus();
	}
	
	
	public void impostaMenuLivello () {
		menu=new JPanel();
		menu.setLayout(new GridLayout (1,3));
		if (Game.getInstance().getPersonaggio().getVita()>0) {
			JLabel vita = new JLabel(getIcona(Game.getInstance().getPersonaggio().getVita()+"vita.png",160));
			menu.add(vita);
			JLabel esci = new JLabel(getIcona("esc.png",400)); 
			menu.add(esci);
			aggiungiBottone("back.png");	
		}
		menu.setVisible(true);
	}
	
	//ImpostaLivelli riceve come parametro un JPanel. Questa funzione viene richiamata in InterfacciaLivelli
	//In base al livello scelto, aggiorno il PannelloGrafico, caricando la mappa selezionata. 
	//Una volta fatto ci�, passiamo PannelloGrafico al contenitore, che lo imposter� come pannello centrale
	
	public void impostaLivelli(JPanel cambia) {
		impostaMenuLivello();
		this.add(menu,BorderLayout.NORTH);
		this.remove(centro);
		centro=cambia;
		this.add(centro, BorderLayout.CENTER);
		revalidate();
		this.centro.setVisible(true);
		this.centro.requestFocus();
		this.loopSound=true;
	}

	public void impostaPannelloFinale (boolean win) {
		this.loopSound=false;
		PannelloGrafico.getInstance().stopSound();
		PopupFinale pf;
		if(win) {
			Sound s = new Sound("applausi.wav");
			s.start();
			pf = new PopupFinale (caricaImmagine("/Game/View/resources/Win.png"));
		}
		else{
			Sound s = new Sound("lose.wav");
			s.start();
			pf = new PopupFinale (caricaImmagine("/Game/View/resources/GameOver.png"));
		}
		this.remove(centro);
		this.menu.setVisible(false);
		centro=pf;
		this.add(centro, BorderLayout.CENTER);
		revalidate();
	}

	public void resetInterfaccia() {
		this.remove(centro);
		this.remove(menu);
		this.loopSound=false;
		Game.getInstance().resetPlayer(); 
		impostaMenuStart();
	}
}
