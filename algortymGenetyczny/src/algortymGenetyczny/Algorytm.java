package algortymGenetyczny;

import java.util.Arrays;
public class Algorytm{
	
	public double Ai;
	public double Bi;
	public double MutacjaPropability;
	public double krzyzowaniePropability;
	public double env;
	
	public int n, PopulacjaLength;
	public double precision;
	public int times;
	private boolean firstRun = true;
	
	public int generacja;
	
	public double najlepszeRozwiazanie;
	
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
	}
	
	
	public void ustawEnv(double _env) {
		this.env = _env;
	}
	
	public void run(){
		
		//funkcja celu:
		Rastrigin goal = new Rastrigin(20, n);

		
		
		//1 krok inicjalizacja poczatkowej populacji chromosomow
		//2 krok ocena przystosowania chromosomow w populacji
		//3 krok: selekcja choromosomow: ruletka
		//4 krok: zastpspwanie operatorow genetycznych
		//	mutowanie
		//	krzyzowanie
		//5 krok: stworzenie nowej popualcji
		
		try {
			//Zapisywacz globalMaxes = new Zapisywacz(String.format("globalMAX%d .txt",PopulacjaLength));
			double AVGMAX [][] = new double[times][(int)(1000)];
			double AVGS[][] = new double[times][(int)(1000)];
			double AVG_GLOBALMAX[][] = new double[50][1000];
			
			//for(int i = 0 ; i < times; i++) {
				
				generacja  = 0;
				Czlonek baseChromosome = new Czlonek(Ai,Bi,n,precision);
				//krok 1 & 2
				Populacja populacja = new Populacja(baseChromosome,PopulacjaLength);
				populacja.adaptPopulacja(goal);	
				int j = 0;
				
				int adaptationNR = 0;
				
				while(adaptationNR < env) {
					//krok 3
					
					if(firstRun == true && adaptationNR % 20 == 0) {
						//Zapisywacz adaptationSteps = new Zapisywacz(String.format("adaptationSteps%d .txt", PopulacjaLength));
						//adaptationSteps.WriteToFile(String.format("%d", adaptationNR));
					}
					
					populacja = new Ruletka(populacja,"MAX").nowaPopulacja;
					//AVGMAX[i][j] = populacja.Adaptation.MAX;
					//AVGS[i][j] = populacja.Adaptation.AVG;
					
					najlepszeRozwiazanie = Populacja.GLOBALMAX;
					//AVG_GLOBALMAX[i][j] = Populacja.GLOBALMAX; 
					//krok 4 & 5
					populacja = krzyzowanieChromosomes(populacja);
					populacja = mutatePopulacja(populacja);
					
					System.out.println("GENERACJA "+generacja);
					generacja++;
					populacja.Adaptation.showAdaptation();
					j++;
					adaptationNR++;	
				}
				
				
				firstRun = false;
				
				//globalMaxes.WriteToFile(String.format("%g",Populacja.GLOBALMAX));
			//}
			
//			Zapisywacz localMax = new Zapisywacz(String.format("localMax%d .txt",PopulacjaLength));
//			Zapisywacz localAVG = new Zapisywacz(String.format("localAVG%d .txt",PopulacjaLength));
//			double localMaxes[] =  calculateAVGLocal(AVGMAX);
//			double localAVGS[] = calculateAVGLocal(AVGS);
//			double globalMaxesArr[] = calculateAVGLocal(AVG_GLOBALMAX);
//			
//			for(int i = 0; i < localMaxes.length;i++) {
//				localMax.WriteToFile(String.format("%g", localMaxes[i]));
//				localAVG.WriteToFile(String.format("%g", localAVGS[i]));
//				globalMaxes.WriteToFile(String.format("%g",globalMaxesArr[i]));
//			}
			System.out.println("THE BEST SOLUTION "+Populacja.GLOBALMAX);
			generacja  = 0;
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
	
	
	private double[] calculateAVGLocal(double[][]arr) {
		double AVGLocalMAX [] = new double[times];
		for(int i = 0; i < times;i++) {
			AVGLocalMAX[i] = calculateAVG(arr[i]);
		}
		return AVGLocalMAX;
	}
	
	private double calculateAVG(double[] arr) {
		return Arrays.stream(arr).average().getAsDouble();
	}
	
}