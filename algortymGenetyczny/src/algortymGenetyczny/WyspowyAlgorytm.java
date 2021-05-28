package algortymGenetyczny;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WyspowyAlgorytm {
	
	
	//wyliczenie wartosci podpopulacji 
	private static ArrayList<Double> ewaulujWartosci(ArrayList<Wyspa> wyspy){
		int index = 0;
		ArrayList<Double> rozwiazaniaWysp = new ArrayList<Double>();
		int bierzacaEwaulacja = dajMaxPopulacja(wyspy);
		int ewaulacje = 1000;
		for(Wyspa wyspa : wyspy) {
			System.out.println("========== WYSPA "+wyspa.numerWyspy+" populacja:"+wyspa.podpopulacja+"==============");
			wyspa.run();
			rozwiazaniaWysp.add(index, wyspa.najlepszeRozwiazanie); 
			index++;
			wyspa.algorytm.najlepszeRozwiazanie  = 0;
		}
		return rozwiazaniaWysp;
	}
	
	private static int dajWyspeNiepowodzenia(ArrayList<Double> wynikiWysp){
		//dla maksimum szukamy min
		//dla minimum szukamy max
		return wynikiWysp.indexOf(Collections.max(wynikiWysp));
	}
	
	
	private static int dajMaxPopulacja(ArrayList<Wyspa> wyspy) {
		return wyspy.stream().max(Comparator.comparing(wyspa -> wyspa)).get().podpopulacja;
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
		int bierzacaEwaulacja = 50;
		//krok 1
		
		ArrayList<Wyspa> wyspy = Wyspa.generujWyspy(new Algorytm(-2,2,100000d,10,100,ewaulacje,0.02,0.6,50));
		
		
		
		ArrayList<Double> rozwiazaniaWysp = new ArrayList<Double>();
		ArrayList<Integer>ileRazyNiePoprawiono = new ArrayList<Integer>();
		int maxNiepowodzen = 7;
		int index = 0;
		
		try {
		while(wyspy.size() > 1) {
		
			rozwiazaniaWysp = ewaulujWartosci(wyspy);
		
			int numerWyspyNiepowodzenia = dajWyspeNiepowodzenia(rozwiazaniaWysp);
			
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