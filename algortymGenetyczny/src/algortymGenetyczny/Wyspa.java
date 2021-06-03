package algortymGenetyczny;

import java.util.ArrayList;

public class Wyspa implements Comparable<Wyspa>{
	
	static int numerWysp = 1;
	
	int numerWyspy;
	int podpopulacja;
	static int wskaznikEwaulacji= 0;
	double najlepszeRozwiazanie;

	Algorytm algorytmObecny;
	
	static double najlepszaWartoscGlobalna = 1000000;
	static int najlepszaWyspaGlobalnaNumer;
	static int wielkoscPopulacjiWyspGlobalnaNajlepsza;
	
	int licznikNiepowodzen = 0;
	
	
	private static int index = 0;
	static int populacje [] = new int [] {10,20,50,100,200,300,400,500,600,1000,1500,2000};
	
	public Wyspa(Algorytm _baza){
		podpopulacja =  populacje[index];
		numerWyspy = numerWysp;
		algorytmObecny = new Algorytm(_baza.Ai,_baza.Bi,_baza.precision, _baza.n,  podpopulacja,_baza.mutacjaPrawdopodobienstwo,_baza.krzyzowaniePrawdopodobienstwo);
		numerWysp++;
		index++;
	}
	
	
	
	public static void wyzeruj() {
		Wyspa.index = 0;
		Wyspa.numerWysp = 1;
		Wyspa.wskaznikEwaulacji = 0;
	}
	
	public void run(double env) {
		
		algorytmObecny.run(env);
			
		najlepszeRozwiazanie = algorytmObecny.najlepszeRozwiazanie;
		Wyspa.wskaznikEwaulacji += algorytmObecny.wskaznikEwaulacji;
		
	}
	
	public static ArrayList<Wyspa> generujWyspy(){
		Algorytm algorytm = new Algorytm( -5.12, 5.12, 1000d, 30, Wyspa.populacje[Wyspa.numerWysp-1], 0.08, 0.6);
		ArrayList<Wyspa> wyspy = new ArrayList<Wyspa>();
		for(int i = 0 ; i < Wyspa.populacje.length;i++) {
			wyspy.add(new Wyspa(algorytm));
		}
		return wyspy;
	}

	@Override
	public int compareTo(Wyspa w) {
		if(this.najlepszeRozwiazanie > w.najlepszeRozwiazanie)
			return 1;
		else if (this.najlepszeRozwiazanie < w.najlepszeRozwiazanie)
			return -1;
		else
		return 0;
	}
	
}
