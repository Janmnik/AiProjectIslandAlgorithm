package algortymGenetyczny;
import java.util.ArrayList;
public class Algorytm{
	
	public double Ai;
	public double Bi;
	public double MutacjaPropability;
	public double krzyzowaniePropability;
	public static double env;
	
	public int n, PopulacjaLength;
	public double precision;
	
	public int generacja;
	
	public double najlepszeRozwiazanie;
	public ArrayList <Double> najlepszeRozwiazaniaLokalne = new ArrayList <Double>();//najlepsze rozwiazania lokanlne probkowanie co 2k
	public ArrayList <Double> wszystkieRozwiazania = new ArrayList <Double>();
	
	public Populacja populacja;
	int adaptationNR = 0;
	int nrWyspy;
	
	public Algorytm(int _nrWyspy,double _Ai, double _Bi,double _precision,int _n,int _PopulacjaLength,double _env,double _MutacjaPropability, double _krzyzowaniePropability) {
		nrWyspy = _nrWyspy;
		Ai = _Ai;
		Bi = _Bi;
		MutacjaPropability = _MutacjaPropability;
		krzyzowaniePropability = _krzyzowaniePropability;
		precision = _precision;
		PopulacjaLength = _PopulacjaLength;
		env = _env;
		n = _n;
		Czlonek baseChromosome = new Czlonek(Ai,Bi,n,precision);
		populacja = new Populacja(baseChromosome,PopulacjaLength);
	}
	
	public Algorytm(double _Ai, double _Bi,double _precision,int _n,int _PopulacjaLength,double _env,double _MutacjaPropability, double _krzyzowaniePropability) {
		Ai = _Ai;
		Bi = _Bi;
		MutacjaPropability = _MutacjaPropability;
		krzyzowaniePropability = _krzyzowaniePropability;
		precision = _precision;
		PopulacjaLength = _PopulacjaLength;
		env = _env;
		n = _n;
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
			
			//ArrayList <Double> wszystkieRozwiazania = new ArrayList <Double>();
			
				//krok 1 & 2
				populacja.adaptPopulacja(goal);	
				
				while(adaptationNR < env) {

					//krok 3
					
					populacja = new Turniej(populacja).PopulacjaNajlepszaWygrana();
					
					najlepszeRozwiazanie = populacja.GLOBALMIN;
					
					najlepszeRozwiazaniaLokalne.add(populacja.Adaptation.MIN);
					//krok 4 & 5
					populacja = krzyzowanieChromosomes(populacja);
					populacja = mutatePopulacja(populacja);
					
					//System.out.println("GENERACJA "+generacja);
					generacja++;
					
					adaptationNR++;	
					
					for(int k=0;k<populacja.n;k++) {
						//wszystkieRozwiazania.get(i).add(populacja.Adaptation.adaptation[k]);
						
					}
				
				}
			
			System.out.println("THE BEST SOLUTION "+populacja.GLOBALMIN);
			generacja  = 0;
			adaptationNR = 0;
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

}