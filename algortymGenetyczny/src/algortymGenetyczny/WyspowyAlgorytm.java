package algortymGenetyczny;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WyspowyAlgorytm {

  static int powtorzenia = 20;
  /*
   * kryterium stopu = jedno dla wszystkich
   * uruchomienie z 50 zmiennymi, jeden przebieg; ile ewaulacji potrzebuje aby wartosc f. 0.5% od najlepszego od 0
   * wartosc min f. R => 0 
   * 
   * */
 
  private static Wyspa dajWyspeNiepowodzenia(ArrayList < Wyspa > wyspy) {
    //dla maksimum szukamy min
    //dla minimum szukamy max
    Wyspa najwieksza = dajNajwiekszaWyspe(wyspy);
    Wyspa niepowodzenie = wyspy.get(0);
    for(Wyspa wyspa : wyspy) {
    	if(najwieksza.najlepszeRozwiazanie < wyspa.najlepszeRozwiazanie ) {
    		niepowodzenie = wyspa;
    		break;
    	}
    }
    return niepowodzenie;
	  //return wyspy.stream().max(Comparator.comparing(wyspa -> wyspa)).get();
  }
  
  private static Wyspa dajNajwiekszaWyspe(ArrayList < Wyspa > wyspy) {
	  Comparator < Wyspa > porownywaczPopulacji = new Comparator < Wyspa > () {
	      public int compare(Wyspa w1, Wyspa w2) {
	        if (w1.podpopulacja < w2.podpopulacja) return 1;
	        else if (w1.podpopulacja > w2.podpopulacja) return - 1;
	        else return 0;
	      }
	    };
	    Collections.sort(wyspy, porownywaczPopulacji);
	    return wyspy.get(0);
  }
  private static int dajMaxPopulacja(ArrayList < Wyspa > wyspy) {
    Comparator < Wyspa > porownywaczPopulacji = new Comparator < Wyspa > () {
      public int compare(Wyspa w1, Wyspa w2) {
        if (w1.podpopulacja < w2.podpopulacja) return 1;
        else if (w1.podpopulacja > w2.podpopulacja) return - 1;
        else return 0;
      }
    };
    Collections.sort(wyspy, porownywaczPopulacji);
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
    
    /*
     * dla kazdej wyspy wez najlepszy globalny, najlepszy lokalny, srednia wszystkich pomiarowz
     * punkty probkowania co 2k
     * 12 wysp populacje : 10 ... 2000
     * wykres:
     * globalny najlepszy - 12 krzywych
     * bierzacy najlepszy - 12 krzywych
     * sredni dla wartosci - 12 wykresow
     * 
     * 14 wykresow
     * */
    //co 100 ew probkowanie
    int ewaulacje = 0;
    int env = 2000;
    int maxNiepowodzen = 5;

    int maxEwaulacji = 100000;
 
    try { 
    	  
        ArrayList < Wyspa > wyspy = Wyspa.generujWyspy();
        //zmien kryterium na 10000 ewaulacji
        while (Wyspa.wskaznikEwaulacji < maxEwaulacji) {
         	
          for (Wyspa wyspa: wyspy) {
            System.out.println("========== WYSPA " + wyspa.numerWyspy + " populacja:" + wyspa.podpopulacja + "==============");
            wyspa.run(env);
            System.out.println("Liczb niepowodzen" + wyspa.licznikNiepowodzen+"ewaluacje:"+wyspa.algorytmObecny.populacja.Adaptation.adaptationNr);
            
            ewaulacje += wyspa.algorytmObecny.wskaznikEwaulacji;
          }
          
          if (wyspy.size() == 1) break;

          Wyspa wyspa = dajWyspeNiepowodzenia(wyspy);
           
          int licznik = wyspa.licznikNiepowodzen;

          if (licznik >= maxNiepowodzen) {
            wyspy.remove(wyspa);
            System.out.println("usunąłem wyspę");
          }
          else {
            wyspa.licznikNiepowodzen += 1;
          }
      
        }
        
        for (Wyspa wyspa: wyspy)
        System.out.println("wyspa " + wyspa.numerWyspy + " " + wyspa.najlepszeRozwiazanie);
        
        //pomiar najlepszych globalnych rozwiazan
        System.out.println("Globalna wyspa"+Wyspa.wielkoscPopulacjiWyspGlobalnaNajlepsza+", "+Wyspa.najlepszaWartoscGlobalna);
       
        
      } catch(IndexOutOfBoundsException e) {
        e.printStackTrace();
      }
      catch(java.util.NoSuchElementException brakWyspException) {
        System.out.println("Brak wysp");
        return;
      }
    	
    	System.out.println(Wyspa.wskaznikEwaulacji);
    	Wyspa.wyzeruj();
    
  }
    
  public static void zapiszWszysktieWartosciFunkcji(Wyspa wyspa, int powtorzenie) {
	  Zapisywacz zapisywaczWszystkich= new Zapisywacz("wszystkieWartosciWyspy"+"."+powtorzenie+"."+wyspa.numerWyspy+"."+wyspa.podpopulacja+".txt");
		for(int i = 0; i < wyspa.algorytmObecny.populacja.n;i++) {
		
			zapisywaczWszystkich.WriteToFile(""+wyspa.algorytmObecny.populacja.Adaptation.adaptation[i]);
		}	 
	  zapisywaczWszystkich.zamknij(); 
  }
        
  private static Double calculateAVG(ArrayList< Double > rozwiazania) {
		   double avgList = 0;
		   for (int i = 0; i < rozwiazania.size(); i++) {
		     avgList += rozwiazania.get(i);
		   }
		   return
		   avgList / rozwiazania.size();
  }
}

	