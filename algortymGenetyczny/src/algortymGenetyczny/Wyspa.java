package algortymGenetyczny;

import java.util.ArrayList;

public class Wyspa implements Comparable<Wyspa>{
	
	static int calosc = WyspowyAlgorytm.calaPopulacja;
	static int numerWysp = 1;
	
	int numerWyspy;
	int podpopulacja;
	int powtorzenie;
	
	double najlepszeRozwiazanie;
	
	
	static int env = 2000;
	Algorytm algorytmBazowy;
	
	int licznikNiepowodzen = 0;
	
	ArrayList<Double> najlepszeRozwiazaniaLokalne= new ArrayList<Double>();
	
	public Wyspa(Algorytm _baza){
		podpopulacja =  generujPopulacjeWysp();
		numerWyspy = numerWysp;
		algorytmBazowy = new Algorytm(_baza.Ai,_baza.Bi,_baza.precision, _baza.n,  podpopulacja,env,_baza.MutacjaPropability,_baza.krzyzowaniePropability);
		numerWysp++;
	}
	
	//do wyliczenia sredniej
	private Algorytm stworzAlgorytm(Algorytm _baza) {
		return new Algorytm(numerWyspy,_baza.Ai,_baza.Bi,_baza.precision, _baza.n, podpopulacja,env,_baza.MutacjaPropability,_baza.krzyzowaniePropability);
	}
	
	public void run(double env) {
		Algorytm alg = stworzAlgorytm(algorytmBazowy);
			alg.run(env);
			
		najlepszeRozwiazanie = alg.najlepszeRozwiazanie;
		this.najlepszeRozwiazaniaLokalne = alg.najlepszeRozwiazaniaLokalne;
	}
	
	public static void ustawPopulacje(int populacja) {
		calosc = populacja;
	}
	
	public static ArrayList<Wyspa> generujWyspy(Algorytm algorytm){
		
		ArrayList<Wyspa> wyspy = new ArrayList<Wyspa>();
		while(calosc > 0) {
			wyspy.add(new Wyspa(algorytm));
		}
		
		return wyspy;
	}
	
	private static int generujPopulacjeWysp(){
		
		int wylosowanaPodpopulacja = getRandomNumber(1,calosc);
			
		calosc -= wylosowanaPodpopulacja;
	
		return wylosowanaPodpopulacja;
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
