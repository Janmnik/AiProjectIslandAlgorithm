package algortymGenetyczny;

import java.util.Arrays;
import java.util.LinkedList;
public class Algorytm{
	
	public double Ai;
	public double Bi;
	public double MutacjaPropability;
	public double krzyzowaniePropability;
	public static double env;
	
	public int n, PopulacjaLength;
	public double precision;
	public int times;
	private boolean firstRun = true;
	
	public int generacja;
	
	public double najlepszeRozwiazanie;
	
	public Populacja populacja;
	int adaptationNR = 0;
	
	public Algorytm(double _Ai, double _Bi,double _precision,int _n,int _PopulacjaLength,double _env,double _MutacjaPropability, double _krzyzowaniePropability, int _times) {
		Ai = _Ai;
		Bi = _Bi;
		MutacjaPropability = _MutacjaPropability;
		krzyzowaniePropability = _krzyzowaniePropability;
		precision = _precision;
		PopulacjaLength = _PopulacjaLength;
		env = _env;
		n = _n;
		times = _times;
		Czlonek baseChromosome = new Czlonek(Ai,Bi,n,precision);
		populacja = new Populacja(baseChromosome,PopulacjaLength);
		
	}
	
	public void run(double env){
		
		//funkcja celu:

		Rastrigin goal = new Rastrigin(10, n);

		//1 krok inicjalizacja poczatkowej populacji chromosomow
		//2 krok ocena przystosowania chromosomow w populacji
		//3 krok: selekcja choromosomow: ruletka
		//4 krok: zastpspwanie operatorow genetycznych
		//	mutowanie
		//	krzyzowanie
		//5 krok: stworzenie nowej popualcji
		
		try {
			//Zapisywacz globalMaxes = new Zapisywacz(String.format("globalMAX%d .txt",PopulacjaLength));
			//double AVGMIN [][] = new double[times][(int)(1000)];
			//double AVGS[][] = new double[times][(int)(1000)];
			//double AVG_GLOBALMIN[][] = new double[50][1000];
			
			//pomiary:
			// - najlepsze globalne rozwiazania
			// - najlepsze lokalne rozwiazania
			// - wartosci przystosowania dla kazdej wartosci ewaulacji
			// - srednie wartosci f przystosowania dla kazdej wartosci 
			LinkedList  najlepszeRozwiazaniaGlob [] = new LinkedList[50]; //najlepsze rozwiazania globalne probkowanie co 2k
			LinkedList  najlepszeRozwiazaniaLok [] = new LinkedList[50]; //najlepsze rozwiazania lokanlne probkowanie co 2k
			LinkedList  wszystkieRozwiazania [] = new LinkedList[50];
			
			for(int i = 0 ; i < times; i++) {
				//krok 1 & 2
				populacja.adaptPopulacja(goal);	
				int j = 0;
				
				while(adaptationNR < env) {
					//krok 3
					
					populacja = new Turniej(populacja).PopulacjaNajlepszaWygrana();
					//AVGMIN[i][j] = populacja.Adaptation.MIN;
					//AVGS[i][j] = populacja.Adaptation.AVG;
					
					najlepszeRozwiazanie = populacja.GLOBALMIN;
					//AVG_GLOBALMIN[i][j] = Populacja.GLOBALMIN; 
					//krok 4 & 5
					populacja = krzyzowanieChromosomes(populacja);
					populacja = mutatePopulacja(populacja);
					
					//System.out.println("GENERACJA "+generacja);
					generacja++;
					//populacja.Adaptation.showAdaptation();
					j++;
					adaptationNR++;	
					
					// TODO: indeks to i
					//  wszystkieRozwiazania -> populacja.Adaptation.adaptation
				}
				
				firstRun = false;
			}
			
			System.out.println("THE BEST SOLUTION "+populacja.GLOBALMIN);
			//generacja  = 0;
		}
		catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	private Populacja mutatePopulacja(Populacja populacja) {
		
		Mutacja mutacja = new Mutacja(MutacjaPropability);
		int length = populacja.Populacja.length;
		for(int i = 0; i < length;i++) {
			
			if(Math.random() < MutacjaPropability) {
				populacja.Populacja[i] = mutacja.czlonekMutacja(populacja.Populacja[i]);
			}
		}
		
		return populacja;
	}
	
	private Populacja krzyzowanieChromosomes(Populacja krzyzowaniePopulacja) throws CloneNotSupportedException {
		
		int randomChromosome;
		int chromosomeLength = krzyzowaniePopulacja.Populacja[0].getChromosomeLength();
		int length = krzyzowaniePopulacja.Populacja.length;
		double randomPropability = 0.0;
		
		Czlonek parentX,parentY;
		for(int i = 0;i<length;i++) {
			randomPropability = Math.random();
			if(randomPropability<=krzyzowaniePropability) {
				randomChromosome = (int)(Math.random()*length);
				Krzyzowanie2Punkt krzyzowanie2Punkt = new Krzyzowanie2Punkt(chromosomeLength);
				parentX = (Czlonek) krzyzowaniePopulacja.Populacja[i].clone();
				parentY = (Czlonek) krzyzowaniePopulacja.Populacja[randomChromosome].clone();
				krzyzowaniePopulacja.Populacja[i] = krzyzowanie2Punkt.getdziecko(parentX, parentY);
				krzyzowaniePopulacja.Populacja[randomChromosome] = krzyzowanie2Punkt.getdziecko(parentY, parentX);
			}
		}
		
		return krzyzowaniePopulacja;
	}
	
	///przerobic aby dzialalo na listach wiazanych
	
	//! w liscie wiazanej nie ma indeksowania; dostep po kolei
	private double[] calculateAVGLocal(double[][]arr) {
		double AVGLocalMIN [] = new double[times];
		for(int i = 0; i < times;i++) {
			AVGLocalMIN[i] = calculateAVG(arr[i]);
		}
		return AVGLocalMIN;
	}
	
	private double calculateAVG(double[] arr) {
		return Arrays.stream(arr).average().getAsDouble();
	}
	
}