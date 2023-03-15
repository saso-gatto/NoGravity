package Game.View;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import Game.Setting.Data;

public class InterfacciaLivelli extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image sfondo;
	public static PannelloGrafico livelli;

	public ImageIcon getIcona(String nameImg) {
		Image scalata = null;
		ImageIcon icona = null;
		try {
			BufferedImage legge = ImageIO.read(getClass().getResourceAsStream("/Game/View/resources/" + nameImg));
			scalata = legge.getScaledInstance(440, 250, Image.SCALE_SMOOTH);
			icona = new ImageIcon(scalata);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return icona;
	}

	public void addAction(JButton pic, String nomeLivello) {
		pic.addActionListener(new ActionListener() { // Implementiamo il funzionamento di entrata in un livello
			@Override
			public void actionPerformed(ActionEvent e) {
				if (PannelloGrafico.getInstance().getLivelloCorrente().equals("0"))
					PannelloGrafico.getInstance().creaMappa(nomeLivello);
				else
					PannelloGrafico.getInstance().aggiornaMappa(nomeLivello);
				Contenitore.getInstance().impostaLivelli(PannelloGrafico.getInstance());
			}
		});
	}

	// Per ogni livello i-esimo creiamo la sua icona ed impostiamo il bottone
	// relativo al suo caricamento
	private void aggiungiLivelli() {
		for (int i = 1; i < 5; i++) {
			ImageIcon icona = getIcona("liv" + i + ".png");
			JButton livello = new JButton(icona);
			String liv = String.valueOf(i);
			this.add(livello); // Aggiungo il livello al pannello di Interfaccia Livelli. Inter.livelli estende JPanel
			livello.setBorder(null);
			livello.setContentAreaFilled(false);
			addAction(livello, liv);
		}
	};

	public InterfacciaLivelli() {
		this.setLayout(new GridLayout(2, 2, Data.size, Data.size));
		try {
			sfondo = ImageIO.read(getClass().getResourceAsStream("/Game/View/resources/SfondoLivelli.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aggiungiLivelli();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (sfondo != null) {
			sfondo = sfondo.getScaledInstance(getSize().width, getSize().height, Image.SCALE_FAST);
			g.drawImage(sfondo, 0, 0, null);
		}
	}

}
