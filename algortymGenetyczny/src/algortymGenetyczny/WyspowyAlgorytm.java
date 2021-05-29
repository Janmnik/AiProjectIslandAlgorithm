package algortymGenetyczny;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WyspowyAlgorytm {
	
	private static Wyspa dajWyspeNiepowodzenia(ArrayList<Wyspa> wyspy){
		//dla maksimum szukamy min
		//dla minimum szukamy max
		
//		Wyspa wyspaNajlepsza = wyspy.stream().min(Comparator.comparing(wyspa -> wyspa)).get();
//		Wyspa wyspaNajgorsza = null;
//		for(Wyspa wyspa : wyspy)
//			if(wyspaNajlepsza.compareTo(wyspa) <= -1)
//				wyspaNajgorsza =  wyspa;
//		
//		return wyspaNajgorsza;
		return wyspy.stream().max(Comparator.comparing(wyspa -> wyspa)).get();
	}
	
	private static int dajMaxPopulacja(ArrayList<Wyspa> wyspy) {
		Comparator<Wyspa> porownywaczPopulacji = new Comparator<Wyspa>() {
			public int compare(Wyspa w1, Wyspa w2) {
				if(w1.podpopulacja < w2.podpopulacja)
					return 1;
				else if(w1.podpopulacja > w2.podpopulacja)
					return -1;
				else return 0;
			}
		};
		Collections.sort(wyspy,porownywaczPopulacji);
		return wyspy.get(0).podpopulacja; 
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
		
		int ewaulacje = 2000;	
		
		ArrayList<Wyspa> wyspy = Wyspa.generujWyspy(new Algorytm(-2,2,100000d,10,20,ewaulacje,0.02,0.6,50));
	
		int maxNiepowodzen = 7;
		
		try {
		
		int najwiekszaPopulacja = dajMaxPopulacja(wyspy);
		int envKrok = ewaulacje / najwiekszaPopulacja / wyspy.size();
		int env = envKrok;
		System.out.println("Najwieksza populacja "+najwiekszaPopulacja);
		Wyspa rozwiazanie;
		while(wyspy.size() > 1) {
			
			while(env < ewaulacje) {
	
				for(Wyspa wyspa : wyspy) {
						System.out.println("========== WYSPA "+wyspa.numerWyspy+" populacja:"+wyspa.podpopulacja+"==============");
						wyspa.run(env);
						//wyspa.algorytm.najlepszeRozwiazanie  = 0;
						System.out.println("Liczb niepowodzen"+wyspa.licznikNiepowodzen);
					}			
					
					if(wyspy.size() == 1)
						break;
					
					Wyspa wyspa = dajWyspeNiepowodzenia(wyspy);
					int licznik = wyspa.licznikNiepowodzen;
					
					if(licznik >= maxNiepowodzen) {
						wyspy.remove(wyspa);
						System.out.println("usunąłem wyspę");
					}
					else {
						wyspa.licznikNiepowodzen += 1;
					}
				
				
				System.out.println("env "+env);
				env+=envKrok;
			}
		}
		}catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		catch(java.util.NoSuchElementException brakWyspException) {
			System.out.println("Brak wysp");
			return;
		}
		for(Wyspa wyspa : wyspy)
			System.out.println("wyspa "+wyspa.numerWyspy+" "+wyspa.najlepszeRozwiazanie);
		return;
	}
}