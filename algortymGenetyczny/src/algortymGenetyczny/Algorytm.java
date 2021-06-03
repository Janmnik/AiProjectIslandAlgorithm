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
	
	public Populacja populacja;
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
		
		try {
			
				//krok 1 & 2
				populacja.adaptPopulacja(goal);	
				while(populacja.Adaptation.adaptationNr < env) {
					
					if(Wyspa.najlepszaWartoscGlobalna > this.populacja.GLOBALMIN) {
						Wyspa.najlepszaWartoscGlobalna = this.populacja.GLOBALMIN;
						Wyspa.najlepszaWyspaGlobalnaNumer = this.nrWyspy;
						Wyspa.wielkoscPopulacjiWyspGlobalnaNajlepsza = this.populacja.n;
					}
					
					if(populacja.Adaptation.adaptationNr % 1000 == 0) {
						String pomiarNajlepszejGlobalnej = "Nr: "+ Wyspa.najlepszaWyspaGlobalnaNumer+";Populacja:"+Wyspa.wielkoscPopulacjiWyspGlobalnaNajlepsza+";+;Wartosc:"+Wyspa.najlepszaWartoscGlobalna;
						String pomiarNajlepszejLokalnej = "Nr:"+nrWyspy+";Populacja:"+populacja.n+";Wartosc:"+populacja.Adaptation.MIN;
						zapiszNajlepszeGlobalneRozwiazania(pomiarNajlepszejGlobalnej);
						zapiszNajlepszeLokalneRozwiazania(pomiarNajlepszejLokalnej,populacja.n);
					}
					
					for(int i = 0; i < populacja.n;i++) {
						String pomiar = ""+populacja.Adaptation.adaptation[i]; 
						zapiszRozwiazania(pomiar,populacja.n);
					}
					
					//krok 3

					populacja = new Turniej(populacja).PopulacjaNajlepszaWygrana();
					najlepszeRozwiazanie = populacja.GLOBALMIN;
					
					//krok 4 & 5
					populacja = krzyzowanieChromosomow(populacja);
					populacja = mutujPopulacje(populacja);
					
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
		
		if(krzyzowaniePopulacja.Populacja[0].n > 20) {
			for(int i = 0;i<length;i++) {
				prawdopodobienstwoWylosowane = Math.random();
				if(prawdopodobienstwoWylosowane<=krzyzowaniePrawdopodobienstwo) {
					losowyChromosom = (int)(Math.random()*length);
					Krzyzowanie8Punkt krzyzowanie8Punkt = new Krzyzowanie8Punkt(dlugoscChromosomu);
					
					rodzicX = (Czlonek) krzyzowaniePopulacja.Populacja[i].clone();
					rodzicY = (Czlonek) krzyzowaniePopulacja.Populacja[losowyChromosom].clone();
					krzyzowaniePopulacja.Populacja[i] = krzyzowanie8Punkt.dajDziecko(rodzicX, rodzicY);
					krzyzowaniePopulacja.Populacja[losowyChromosom] = krzyzowanie8Punkt.dajDziecko(rodzicY, rodzicX);
				}
			}
		}
		else {
		
		for(int i = 0;i<length;i++) {
			prawdopodobienstwoWylosowane = Math.random();
			if(prawdopodobienstwoWylosowane<=krzyzowaniePrawdopodobienstwo) {
				losowyChromosom = (int)(Math.random()*length);
				Krzyzowanie2Punkt krzyzowanie2Punkt = new Krzyzowanie2Punkt(dlugoscChromosomu);
				rodzicX = (Czlonek) krzyzowaniePopulacja.Populacja[i].clone();
				rodzicY = (Czlonek) krzyzowaniePopulacja.Populacja[losowyChromosom].clone();
				krzyzowaniePopulacja.Populacja[i] = krzyzowanie2Punkt.dajDziecko(rodzicX, rodzicY);
				krzyzowaniePopulacja.Populacja[losowyChromosom] = krzyzowanie2Punkt.dajDziecko(rodzicY, rodzicX);
			}
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
	
	private static void zapiszRozwiazania(String pomiar,int populacja) {
		  
		  Zapisywacz zapisywaczNajlepszychLokalnie = new Zapisywacz("wszystkieRozwiazania"+populacja+"."+".txt");
		  zapisywaczNajlepszychLokalnie.WriteToFile(pomiar);
		 
		  zapisywaczNajlepszychLokalnie.zamknij();
	}

}