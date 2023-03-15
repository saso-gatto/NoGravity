package Game.View;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import Game.Model.Game;
import Game.Setting.Data;

public class PopupFinale extends JPanel {
     
	private static final long serialVersionUID = 1L;
		private Image img;
		private String score;
        private JButton yes; 
        private JButton no;
        
        public ImageIcon getIcona (String nameImg) {
            Image scalata=null;
            ImageIcon icona=null;
            try {
                BufferedImage legge = ImageIO.read(getClass().getResourceAsStream("/Game/View/resources/"+nameImg));
                scalata = legge.getScaledInstance(100,60,Image.SCALE_SMOOTH);
                icona = new ImageIcon (scalata);
            } catch (IOException e) {
                e.printStackTrace();
            }
       
            return icona;
        }
    	
        
        public PopupFinale (Image image)
         {
            this.img = image.getScaledInstance(Data.width,Data.height, Image.SCALE_SMOOTH);
            this.setLayout(null);
            if (PannelloGrafico.getInstance().getLivelloCorrente().equals("5")) {
            	score = Game.getInstance().getPunteggio();
            }
            
            yes=new JButton (getIcona("yes.png"));
            no =new JButton (getIcona("no.png"));
            
            this.add(yes);
            this.add(no);

            yes.setBounds(Data.width/2 - Data.width/8, Data.height - Data.height/4, 100, 60);
            no.setBounds(Data.width/2 + Data.width/8, Data.height - Data.height/4,100,60);

            
            yes.setContentAreaFilled(false);
            yes.setBorder(null);
            no.setContentAreaFilled(false);
            no.setBorder(null);
            
            yes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Contenitore.getInstance().resetInterfaccia();
					}
			});
            no.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
         }
         
         @Override
         public void paintComponent (Graphics g) {
             super.paintComponent (g);
             g.drawImage (img, 0, 0, this);
             if (PannelloGrafico.getInstance().getLivelloCorrente().equals("5")) {
	             g.setFont(new Font("arial",Font.BOLD,40));
	             g.setColor(Color.YELLOW);
	             g.drawString("YOUR SCORE IS: "+score, Data.width/2-160, Data.height/8);
	         }
         }

}

