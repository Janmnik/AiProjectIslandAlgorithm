package algortymGenetyczny;

import java.util.ArrayList;

public class Wyspa implements Comparable<Wyspa>{
	
	static int calosc = 20;
	static int numerWysp = 1;
	
	int numerWyspy;
	int podpopulacja;
	
	static double globalNajlepszeRozwiazanie = 0;
	static int NumerNajlepszejWyspy = 0;
	
	double najlepszeRozwiazanie;
	Algorytm algorytm;
	
	public Wyspa(Algorytm _baza, double _env){
		podpopulacja =  generujPopulacjeWysp(_baza.PopulacjaLength);
		algorytm = new Algorytm(_baza.Ai,_baza.Bi,_baza.precision, _baza.n, podpopulacja,_env,_baza.MutacjaPropability,_baza.krzyzowaniePropability,_baza.times);
		numerWyspy = numerWysp;
		numerWysp++;
	}
	
	
	public void run() {
		this.algorytm.run();
		najlepszeRozwiazanie = this.algorytm.najlepszeRozwiazanie;
	}
	
	public void ustawEnv(double _env) {
		this.algorytm.env = _env;
	}
	
	public static void ustawPopulacje(int populacja) {
		calosc = populacja;
	}
	
	
	public static ArrayList<Wyspa> generujWyspy(Algorytm algorytm){
		
		ArrayList<Wyspa> wyspy = new ArrayList<Wyspa>();
		while(calosc > 0) {
			wyspy.add(new Wyspa(algorytm,20));
		}
		
		return wyspy;
	}
	
	private static int generujPopulacjeWysp(int populacjaLength){
		
		int wylosowanaPodpopulacja = getRandomNumber(1,calosc);
			
		calosc -= wylosowanaPodpopulacja;
	
		return wylosowanaPodpopulacja;
	}
	
	private static int getRandomNumber(int min, int max) {
	    return (int)((Math.random() * (max - min)) + min);
	}


	@Override
	public int compareTo(Wyspa w) {
		if(this.podpopulacja > w.podpopulacja)
			return 1;
		else if (this.podpopulacja < w.podpopulacja)
			return -1;
		else
		return 0;
	}
	
}
