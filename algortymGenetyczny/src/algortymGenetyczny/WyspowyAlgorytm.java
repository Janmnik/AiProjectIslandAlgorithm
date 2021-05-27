package algortymGenetyczny;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class WyspowyAlgorytm {
	
	
//	static FunkcjaCeluZad1 calc1 = new FunkcjaCeluZad1() {
//
//		@Override
//		public double func(double x1,double x2) {
//			return Math.round((-1*( Math.pow(x1, 2) ) - Math.pow(x2, 2) +2.0)*100000d)/100000d; 
//		}
//	};
	
	
	
	private static ArrayList<Double> ewaulujWartosci(ArrayList<Wyspa> wyspy){
		int index = 0;
		ArrayList<Double> najlepszeRozwiazaniaWysp = new ArrayList<Double>();
		for(Wyspa wyspa : wyspy) {
			System.out.println("========== WYSPA "+wyspa.numerWyspy+"==============");
			wyspa.run();
			najlepszeRozwiazaniaWysp.add(index, wyspa.najlepszeRozwiazanie); 
			index++;
		}
		return najlepszeRozwiazaniaWysp;
	}
	
	private static int dajWyspeNiepowodzenia(ArrayList<Double> wynikiWysp){
		//dla maksimum szukamy min
		//dla minimum szukamy max
		return wynikiWysp.indexOf(Collections.max(wynikiWysp));
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		
		
		//Krok 1: stworz wyspy
		//Krok 2: wykonaj adaptajce populacji na wyspach
		//Krok 3:sprawdz czy ktoras z wysp ma gorsze wyniki niz inne
		//jesli tak:
		//Krok 4: dodaj wskaznik
		//jesli nie idz dalej
		//Krok5:sprawdz ile razy wyspy mialy gorsze rozwiazania
		//jesli wiecej niz x razy
		//usun wyspe
		
		int ewaulacje = 1000;
		
		int bierzaceEwaulacje = 20;
		//krok 1
		ArrayList<Wyspa> wyspy = Wyspa.generujWyspy(new Algorytm(-2,2,100000d,2,20,20,0.02,0.6,50));
		
		ArrayList<Double> najlepszeRozwiazaniaWysp = new ArrayList<Double>();
		ArrayList<Integer>ileRazyNiePoprawiono = new ArrayList<Integer>();
		int maxNiepowodzen = 5;
		int index = 0;
		
		try {
		while(wyspy.size() > 1) {
		
			najlepszeRozwiazaniaWysp = ewaulujWartosci(wyspy);
		
			int numerWyspyNiepowodzenia = dajWyspeNiepowodzenia(najlepszeRozwiazaniaWysp);
			
			int licznik = ileRazyNiePoprawiono.get(numerWyspyNiepowodzenia);
			
		
			if(licznik >= maxNiepowodzen) {
				wyspy.remove(numerWyspyNiepowodzenia);
				ileRazyNiePoprawiono.remove(numerWyspyNiepowodzenia);
			}
			else {
				ileRazyNiePoprawiono.add(numerWyspyNiepowodzenia, licznik+1);
			}
		
		}
		}catch(IndexOutOfBoundsException e) {
			
		}
		

		System.out.println("wyspa "+wyspy.get(0).numerWyspy+" "+wyspy.get(0).najlepszeRozwiazanie);

	}
}