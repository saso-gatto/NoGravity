package Game.View;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Game.Setting.Data;


public class MenuStart extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image sfondo = null;
	private JButton start;
	private JButton sopravvivenza;
	private JButton regole;
	private JButton exit;
	private JButton easterEgg;
	String path="/Game/View/resources/Menu/";
	
	public ImageIcon getIcona (String nameImg) {
        Image scalata=null;
        ImageIcon icona=null;
        try {
            BufferedImage legge = ImageIO.read(getClass().getResourceAsStream(path+nameImg));
            scalata = legge.getScaledInstance(250,65,Image.SCALE_SMOOTH);
            icona = new ImageIcon (scalata);
        } catch (IOException e) {
            e.printStackTrace();
        }
   
        return icona;
    }
	
	public JButton impostaBottone(String icona) {
		ImageIcon icon = getIcona(icona);
		JButton b = new JButton (icon);
		b.setBorder(null);
		b.setContentAreaFilled(false);
		return b;
	}
	
	public MenuStart () {
		this.setLayout(null);
		try {
			sfondo=ImageIO.read(getClass().getResourceAsStream(path+"MenuStart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		start = impostaBottone("Inizia_button.png");
		sopravvivenza=impostaBottone("sopravvivenza.png");
		regole=impostaBottone("regole_button.png");
		exit=impostaBottone("Esci_button.png");


		easterEgg= new JButton();
		easterEgg.setBorder(null);
		easterEgg.setContentAreaFilled(false);
		
		this.add(start);
		this.add(sopravvivenza);
		this.add(regole);
		this.add(exit);
		this.add(easterEgg);
		
        start.setBounds(Data.width/2-100, Data.height - 3*Data.height/4, 200,65);
        sopravvivenza.setBounds(Data.width/2-sopravvivenza.getIcon().getIconWidth()/2, Data.height - 3*Data.height/4+75, sopravvivenza.getIcon().getIconWidth(),65);
        regole.setBounds(Data.width/2-100, Data.height - 3*Data.height/4+150, 200,65);
        exit.setBounds(Data.width/2-100, Data.height - 3*Data.height/4+225, 200,65);
        easterEgg.setBounds(Data.width/2-150, Data.height -200, 250,200);

        


		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Contenitore.getInstance().impostaInterfaccia();
			}
		});
		
		sopravvivenza.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (PannelloGrafico.getInstance().getLivelloCorrente().equals("0"))
					PannelloGrafico.getInstance().creaMappa("5");
				else
					PannelloGrafico.getInstance().aggiornaMappa("5");
				Contenitore.getInstance().impostaLivelli(PannelloGrafico.getInstance());		
			}
		});
		
		exit.addActionListener(new ActionListener () { //Implementiamo il funzionamento di uscita dal programma
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		
		regole.addActionListener(new ActionListener () { //Implementiamo il funzionamento di uscita dal programma
			@Override
			public void actionPerformed(ActionEvent e) {

				 JFrame f3 = new JFrame();
		         f3.setTitle("Regole - No Gravity");
		         f3.setSize(1000, 620);
		         JLabel label;
				try {
					BufferedImage legge = ImageIO.read(getClass().getResourceAsStream("/Game/View/resources/regole.png")); 
					Image scalata = legge.getScaledInstance(1000,600, Image.SCALE_SMOOTH);
					ImageIcon aggiungi = new ImageIcon (scalata);
					label = new JLabel(aggiungi);
			        f3.add(label);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		         f3.setLocationRelativeTo(null);
		         f3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		         f3.setVisible(true);
			}
			
		});
		
		
		
		easterEgg.addActionListener(new ActionListener () { //Implementiamo il funzionamento di uscita dal programma
			@Override
			public void actionPerformed(ActionEvent e) {
				Sound s=new Sound ("sound.wav");
				s.start();
				 JFrame f2 = new JFrame();
		         f2.setTitle("Easter Egg - No Gravity");
		         f2.setSize(700, 420);
		         JLabel label;
				try {
					BufferedImage legge = ImageIO.read(getClass().getResourceAsStream("/Game/View/resources/EasterEgg.png")); 
					Image scalata = legge.getScaledInstance(700,400, Image.SCALE_SMOOTH);
					ImageIcon aggiungi = new ImageIcon (scalata);
					label = new JLabel(aggiungi);
			        f2.add(label);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		         f2.setLocationRelativeTo(null);
		         f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		         f2.setVisible(true);
			}
			
		});
	}
	
	@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (sfondo!=null)
				{sfondo=sfondo.getScaledInstance(getSize().width,getSize().height, Image.SCALE_SMOOTH);
				g.drawImage(sfondo,0,0,null);
				}
			
		}
}


