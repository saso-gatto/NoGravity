package Game.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GestioneMappa {

	private ArrayList <String> righe;
	private int colonna; //width
	private int riga; //height
	private static final String path = "/Game/View/";
	public static int posInizialeX;
	public static int posInzialeY;
	public static int posPortaX;
	public static int posPortaY;
	
	public int getRiga() {
		return riga;
	}
	
	public int getColonna() {
		return colonna;
	}
	
	public Blocco[][] creaMatrice() {
		Blocco [][]matrice = new Blocco [riga][colonna];
		for (int i = 0; i<riga; i++)
		{
			String rigaCorrente =righe.get(i); //In riga corrente salviamo la riga che stiamo analizzando
			for (int j=0; j<colonna; j++) //Per ogni riga, analizziamo i caratteri presenti al suo interno
			{	//Ogni carattere rappresenta uno stato del blocco
				char chiave =rigaCorrente.charAt(j);
				
				matrice[i][j]=new Blocco(i,j,chiave);
				if (matrice[i][j].getChiave()==Blocco.PROTAGONISTA) //Salviamo la posiniziale del personaggio in questo livello
				{
					posInizialeX=i;
					posInzialeY=j;
				}
				if (matrice[i][j].getChiave()==Blocco.PORTA)
				{
					posPortaX=i;
					posPortaY=j;
				}

			}
		}
		return matrice;
	}
	
	public void caricaMappa (String filename)
	{
		righe= new ArrayList <String>(); //il primo arrayList sono le righe, il secondo le colonne
		BufferedReader br = null; 
		try {
				br = new BufferedReader(new FileReader(getClass().getResource( path + "Map"+ filename + "/map"+filename+".txt").getPath()));
				String currentLine;
				while (br.ready()) //finche le righe del file di testo presentano caratteri
				{
					currentLine=br.readLine();			
					righe.add(currentLine);
					colonna = Math.max(colonna, currentLine.length());
					//Calcoliamo di volta in volta la lunghezza di ogni riga. 
					//In colonna verrà salvata la lugnhezza max della riga presente nel file di testo della mappa. 
				}
			br.close();
			} 
		catch (IOException e)
			{
				System.out.println("Siamo nella funzione carica mappa, errore");	
			}
		
		riga=righe.size();
	}

	
	public GestioneMappa() {
	}
	
}

