package algortymGenetyczny;
import java.util.ArrayList;
public class Algorytm{
	
	public double Ai;
	public double Bi;
	public double mutacjaPrawdopodobienstwo;
	public double krzyzowaniePrawdopodobienstwo;
	
	public int n, PopulacjaLength;
	public double precision;
	
	public int generacja;
	
	public double najlepszeRozwiazanie;
	public ArrayList <Double> najlepszeRozwiazaniaLokalne = new ArrayList <Double>();//najlepsze rozwiazania lokanlne probkowanie co 2k
	public ArrayList <Double> wszystkieRozwiazania = new ArrayList <Double>();
	
	public Populacja populacja;
	int adaptationNR = 0;
	public int wskaznikEwaulacji = 0;
	int nrWyspy;
	
	public Algorytm(int _nrWyspy,double _Ai, double _Bi,double _precision,int _n,int _PopulacjaLength,double _MutacjaPropability, double _krzyzowaniePropability) {
		nrWyspy = _nrWyspy;
		Ai = _Ai;
		Bi = _Bi;
		mutacjaPrawdopodobienstwo = _MutacjaPropability;
		krzyzowaniePrawdopodobienstwo = _krzyzowaniePropability;
		precision = _precision;
		PopulacjaLength = _PopulacjaLength;
		n = _n;
		Czlonek baseChromosome = new Czlonek(Ai,Bi,n,precision);
		populacja = new Populacja(baseChromosome,PopulacjaLength);
	}
	
	public Algorytm(double _Ai, double _Bi,double _precision,int _n,int _PopulacjaLength,double _MutacjaPropability, double _krzyzowaniePropability) {
		Ai = _Ai;
		Bi = _Bi;
		mutacjaPrawdopodobienstwo = _MutacjaPropability;
		krzyzowaniePrawdopodobienstwo = _krzyzowaniePropability;
		precision = _precision;
		PopulacjaLength = _PopulacjaLength;
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
			
			//pomiary:
			// - najlepsze globalne rozwiazania
			// - najlepsze lokalne rozwiazania
			// - wartosci przystosowania dla kazdej wartosci ewaulacji
			// - srednie wartosci f przystosowania dla kazdej wartosci 	
			
			//ArrayList <Double> wszystkieRozwiazania = new ArrayList <Double>();
			
				//krok 1 & 2
				populacja.adaptPopulacja(goal);	
				while(populacja.Adaptation.adaptationNr < env) {

					//krok 3
					populacja = new Turniej(populacja).PopulacjaNajlepszaWygrana();
					najlepszeRozwiazanie = populacja.Adaptation.MIN;
					if(adaptationNR % 100 == 0) {
						String pomiarNajlepszejGlobalnej = "Nr: "+ Wyspa.najlepszaWyspaGlobalnaNumer+";Populacja:"+Wyspa.wielkoscPopulacjiWyspGlobalnaNajlepsza+";+;Wartosc:"+Wyspa.najlepszaWartoscGlobalna;
						String pomiarNajlepszejLokalnej = "Nr:"+nrWyspy+";Populacja:"+populacja.n+";Wartosc:"+populacja.Adaptation.MIN;
						zapiszNajlepszeGlobalneRozwiazania(pomiarNajlepszejGlobalnej);
						zapiszNajlepszeLokalneRozwiazania(pomiarNajlepszejLokalnej,populacja.n);
					}
					System.out.println(populacja.Adaptation.MIN);
					najlepszeRozwiazaniaLokalne.add(populacja.Adaptation.MIN);
					//krok 4 & 5
					populacja = krzyzowanieChromosomow(populacja);
					populacja = mutujPopulacje(populacja);
					
					
					//System.out.println("GENERACJA "+generacja);
					
					generacja++;
					
			
				}
			
			System.out.println("THE BEST SOLUTION "+populacja.GLOBALMIN);
			generacja  = 0;
			wskaznikEwaulacji = populacja.Adaptation.adaptationNr;
			
		}
		catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
	}
	
	private Populacja mutujPopulacje(Populacja populacja) {
		
		Mutacja mutacja = new Mutacja(mutacjaPrawdopodobienstwo);
		int length = populacja.Populacja.length;
		for(int i = 0; i < length;i++) {
			
			if(Math.random() < mutacjaPrawdopodobienstwo) {
				populacja.Populacja[i] = mutacja.czlonekMutacja(populacja.Populacja[i]);
			}
		}
		
		return populacja;
	}
	
	private Populacja krzyzowanieChromosomow(Populacja krzyzowaniePopulacja) throws CloneNotSupportedException {
		
		int losowyChromosom;
		int dlugoscChromosomu = krzyzowaniePopulacja.Populacja[0].dajDlugoscChromosomu();
		int length = krzyzowaniePopulacja.Populacja.length;
		double prawdopodobienstwoWylosowane = 0.0;
		
		Czlonek rodzicX, rodzicY;
		for(int i = 0;i<length;i++) {
			prawdopodobienstwoWylosowane = Math.random();
			if(prawdopodobienstwoWylosowane<=krzyzowaniePrawdopodobienstwo) {
				losowyChromosom = (int)(Math.random()*length);
				Krzyzowanie2Punkt krzyzowanie2Punkt = new Krzyzowanie2Punkt(dlugoscChromosomu);
				rodzicX = (Czlonek) krzyzowaniePopulacja.Populacja[i].clone();
				rodzicY = (Czlonek) krzyzowaniePopulacja.Adaptation. Populacja[losowyChromosom].clone();
				krzyzowaniePopulacja.Populacja[i] = krzyzowanie2Punkt.dajDziecko(rodzicX, rodzicY);
				krzyzowaniePopulacja.Populacja[losowyChromosom] = krzyzowanie2Punkt.dajDziecko(rodzicY, rodzicX);
			}
		}
		
		return krzyzowaniePopulacja;
	}
	
	private static void zapiszNajlepszeGlobalneRozwiazania(String pomiar) {
		 Zapisywacz  zapisywaczNajlepszychGlobalnie = new Zapisywacz("najlepszeGlobalnieWyspy"+".txt"); 
			
		 zapisywaczNajlepszychGlobalnie.WriteToFile(pomiar);
		 zapisywaczNajlepszychGlobalnie.zamknij();
	}
	private static void zapiszNajlepszeLokalneRozwiazania(String pomiar,int populacja) {
		  
		  Zapisywacz zapisywaczNajlepszychLokalnie = new Zapisywacz("najlepszeLokalneWyspy"+populacja+"."+".txt");
		  zapisywaczNajlepszychLokalnie.WriteToFile(pomiar);
		 
		  zapisywaczNajlepszychLokalnie.zamknij();
	  }

}