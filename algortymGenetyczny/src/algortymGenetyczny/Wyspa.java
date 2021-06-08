package algortymGenetyczny;

import java.util.ArrayList;

public class Wyspa implements Comparable<Wyspa>{
	
	static int numerWysp = 1;
	static int liczbaGenow = 50;
	
	int numerWyspy;
	int podpopulacja;
	static int wskaznikEwaulacji= 0;
	double najlepszeRozwiazanie;
	Algorytm algorytmObecny;
	
	static double najlepszaWartoscGlobalna = 1000000;
	static int najlepszaWyspaGlobalnaNumer;
	ArrayList<Wyspa> najlepszeRozwiazaniaLokalne = new ArrayList<Wyspa>();
	
	int licznikNiepowodzen = 0;
	
	
	private static int index = 0;
	static int populacje [] = new int [] {10,20,50,100,200,300,400,500,600,1000,1500,2000};
	
	public Wyspa(){
		podpopulacja =  populacje[index];
		numerWyspy = numerWysp;
		algorytmObecny = new Algorytm(numerWyspy,liczbaGenow,podpopulacja,0.04,0.6);
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
		System.out.println("Wskaznik"+Wyspa.wskaznikEwaulacji);
		
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
