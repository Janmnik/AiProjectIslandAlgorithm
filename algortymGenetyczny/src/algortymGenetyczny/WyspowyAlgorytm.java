package algortymGenetyczny;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WyspowyAlgorytm {

  static int calaPopulacja = 10;
  static int powtorzenia = 10;
  
  private static Wyspa dajWyspeNiepowodzenia(ArrayList < Wyspa > wyspy) {
    //dla maksimum szukamy min
    //dla minimum szukamy max
    return wyspy.stream().max(Comparator.comparing(wyspa -> wyspa)).get();
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

    int ewaulacje = 2000;
    int envKrok = 100;
    int env = 2000;
    int maxNiepowodzen = 5;
    
    //przechowywanieNajlepszychRozwiazanGlobalnych
    ArrayList<Wyspa> najlepszeWyspyGlobalnie = new ArrayList<Wyspa>();
    ArrayList<ArrayList<Wyspa>> historiaWysp = new ArrayList<ArrayList<Wyspa>>();
    
    for(int i = 0; i < powtorzenia; i++) {
    	historiaWysp.add(new ArrayList<Wyspa>());
    }
    
    for (int i = 1; i <= powtorzenia; i++) {
    	WyspowyAlgorytm.calaPopulacja = 10;
        Wyspa.numerWysp = 1;
        Wyspa.calosc = 10;
        env = 2000;
    	try { 
    	  
        ArrayList < Wyspa > wyspy = Wyspa.generujWyspy(new Algorytm( - 2, 2, 100000d, 10, 20, ewaulacje, 0.02, 0.6));
      
        while (wyspy.size() > 1) {
         	
          for (Wyspa wyspa: wyspy) {
            System.out.println("========== WYSPA " + wyspa.numerWyspy + " populacja:" + wyspa.podpopulacja + "==============");
            wyspa.run(env);
            System.out.println("Liczb niepowodzen" + wyspa.licznikNiepowodzen);
            
            historiaWysp.get(i-1).add(wyspa);
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

          System.out.println("env " + env);
          env += envKrok;
        }
        
        for (Wyspa wyspa: wyspy)
        System.out.println("wyspa " + wyspa.numerWyspy + " " + wyspa.najlepszeRozwiazanie);
        
        //pomiar najlepszych globalnych rozwiazan
        najlepszeWyspyGlobalnie.add(wyspy.get(0));
        zapiszNajlepszeGlobalneRozwiazania(najlepszeWyspyGlobalnie);
         
      } catch(IndexOutOfBoundsException e) {
        e.printStackTrace();
      }
      catch(java.util.NoSuchElementException brakWyspException) {
        System.out.println("Brak wysp");
        return;
      }
    }
  //pomiar najlepszych loklanych rozwiazan
    zapiszNajlepszeLokalneRozwiazania(historiaWysp);
  }
  
  static void zapiszNajlepszeGlobalneRozwiazania(ArrayList<Wyspa> najlepsze) {
	  Zapisywacz zapisywaczNajlepszychGlobalnie = new Zapisywacz("najlepszeGlobalnieWyspy"+calaPopulacja+".txt");
	  zapisywaczNajlepszychGlobalnie.WriteToFile("wyspa;populacja;wartosc");
	  for(Wyspa wyspa : najlepsze) {
		  String daneOWyspie = ""+wyspa.numerWyspy+";"+wyspa.podpopulacja+";"+wyspa.najlepszeRozwiazanie;
		  zapisywaczNajlepszychGlobalnie.WriteToFile(daneOWyspie);
	  }
	  zapisywaczNajlepszychGlobalnie.zamknij();
  }
  
  private static void zapiszNajlepszeLokalneRozwiazania(ArrayList<ArrayList<Wyspa>> wyspy) {
	  
	  Zapisywacz zapisywaczNajlepszychLokalnie = new Zapisywacz("najlepszeLokalneWyspy"+calaPopulacja+".txt");
	  zapisywaczNajlepszychLokalnie.WriteToFile("wyspa;populacja;wartosc");
	  System.out.println("WYMIARY");
	  for(int k = 0; k < wyspy.size(); k++) {
		  
		  for(int i = 0; i < wyspy.get(k).size();i++) {
			  String daneOWyspie = ""+wyspy.get(i).get(k).numerWyspy+";"+wyspy.get(i).get(k).podpopulacja+";"+calculateAVG(wyspy.get(i).get(k).najlepszeRozwiazaniaLokalne);
			  System.out.println(daneOWyspie);
			  zapisywaczNajlepszychLokalnie.WriteToFile(daneOWyspie);
//			  System.out.println(wyspy.size());
//			  System.out.print(wyspy.get(i).size()+",");
//			  System.out.println("");
		  }
	  }
	  zapisywaczNajlepszychLokalnie.zamknij();
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

	