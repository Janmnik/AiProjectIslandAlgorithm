package algortymGenetyczny;

import java.util.ArrayList;

public class Wyspa implements Comparable<Wyspa>{
	
	static int numerWysp = 1;
	
	int numerWyspy;
	int podpopulacja;
	
	static int wskaznikEwaulacji= 0;
	double najlepszeRozwiazanie;
	
	Algorytm algorytmBazowy;
	Algorytm algorytmObecny;
	
	static double najlepszaWartoscGlobalna = 1000000;
	static int najlepszaWyspaGlobalnaNumer;
	static int wielkoscPopulacjiWyspGlobalnaNajlepsza;
	
	int licznikNiepowodzen = 0;
	
	ArrayList<Double> najlepszeRozwiazaniaLokalne= new ArrayList<Double>();
	ArrayList<Double> wszystkieRozwiazania = new ArrayList<Double>();
	
	private static int index = 0;
	static int populacje [] = new int [] {10,20,50,100,200,300,400,500,500,600,1000,1500,2000};
	
	public Wyspa(Algorytm _baza){
		podpopulacja =  populacje[index];
		System.out.println(podpopulacja);
		numerWyspy = numerWysp;
		algorytmBazowy = new Algorytm(_baza.Ai,_baza.Bi,_baza.precision, _baza.n,  podpopulacja,_baza.mutacjaPrawdopodobienstwo,_baza.krzyzowaniePrawdopodobienstwo);
		numerWysp++;
		index++;
	}
	
	//do wyliczenia sredniej
	private Algorytm stworzAlgorytm(Algorytm _baza) {
		return new Algorytm(numerWyspy,_baza.Ai,_baza.Bi,_baza.precision, _baza.n, podpopulacja,_baza.mutacjaPrawdopodobienstwo,_baza.krzyzowaniePrawdopodobienstwo);
	}
	
	public static void wyzeruj() {
		Wyspa.index = 0;
		Wyspa.numerWysp = 1;
		Wyspa.wskaznikEwaulacji = 0;
	}
	
	public void run(double env) {
		algorytmObecny = stworzAlgorytm(algorytmBazowy);
		algorytmObecny.run(env);
			
		najlepszeRozwiazanie = algorytmObecny.najlepszeRozwiazanie;
		this.najlepszeRozwiazaniaLokalne = algorytmObecny.najlepszeRozwiazaniaLokalne;
		Wyspa.wskaznikEwaulacji += algorytmObecny.wskaznikEwaulacji;
		
		if(Wyspa.najlepszaWartoscGlobalna > this.algorytmObecny.najlepszeRozwiazanie) {
			Wyspa.najlepszaWartoscGlobalna = this.algorytmObecny.najlepszeRozwiazanie;
			Wyspa.najlepszaWyspaGlobalnaNumer = this.numerWyspy;
			wielkoscPopulacjiWyspGlobalnaNajlepsza = this.podpopulacja;
		}
	}
	
	public static ArrayList<Wyspa> generujWyspy(){
		Algorytm algorytm = new Algorytm( -5.12, 5.12, 1000d, 50, Wyspa.populacje[Wyspa.numerWysp-1], 0.02, 0.6);
		ArrayList<Wyspa> wyspy = new ArrayList<Wyspa>();
		for(int i = 0 ; i < Wyspa.populacje.length;i++) {
			wyspy.add(new Wyspa(algorytm));
		}
		return wyspy;
	}
	
	private static int getRandomNumber(int min, int max) {
	    return (int)((Math.random() * (max - min)) + min);
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
