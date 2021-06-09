package algortymGenetyczny;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WyspowyAlgorytm {

    static int powtorzenia = 20;
   
    static double najlepszaWyspaRozwiazanie = 9999;
    static int najlepszaWyspaPopulacja;
    /*
     * kryterium stopu = jedno dla wszystkich
     * uruchomienie z 50 zmiennymi, jeden przebieg; ile ewaulacji potrzebuje aby wartosc f. 0.5% od najlepszego od 0
     * wartosc min f. R => 0 
     * 
     * */

    private static Wyspa dajWyspeNiepowodzenia(ArrayList < Wyspa > wyspy) {
        //dla maksimum szukamy min
        //dla minimum szukamy max
//        Wyspa najwieksza = dajNajwiekszaWyspe(wyspy);
//        Wyspa niepowodzenie = wyspy.get(0);
//        for (Wyspa wyspa: wyspy) {
//            if (najwieksza.najlepszeRozwiazanie < wyspa.najlepszeRozwiazanie) {
//                niepowodzenie = wyspa;
//                break;
//            }
//        }
//        return niepowodzenie;
       return wyspy.stream().max(Comparator.comparing(wyspa -> wyspa)).get();
    }

    private static Wyspa dajNajwiekszaWyspe(ArrayList < Wyspa > wyspy) {
        Comparator < Wyspa > porownywaczPopulacji = new Comparator < Wyspa > () {
            public int compare(Wyspa w1, Wyspa w2) {
                if (w1.podpopulacja < w2.podpopulacja) return 1;
                else if (w1.podpopulacja > w2.podpopulacja) return -1;
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
                else if (w1.podpopulacja > w2.podpopulacja) return -1;
                else return 0;
            }
        };
        Collections.sort(wyspy, porownywaczPopulacji);
        return wyspy.get(0).podpopulacja;
    }
    
//    public static void main(String[] args) {
//    	Wyspa wyspa = new Wyspa();
//    	wyspa.run(20);
//    }

 public static void main(String[] args) {

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
    	Zapisywacz zapisywaczGlobalnych = new Zapisywacz("GlobalneWartosciWyspy" + "." +Wyspa.liczbaGenow+ ".txt");
    	zapisywaczGlobalnych.WriteToFile("Populacja;ewaulacja;wartosc");
    	
        int env = 2000;
        int maxNiepowodzen = 3;
        
        int maxEwaulacji = 100000;

        //tworzenie wysp
        ArrayList < Wyspa > wyspy = new ArrayList < Wyspa > ();

        for (int i = 1; i <= 12; i++) {
            wyspy.add(new Wyspa());
        }
        
       
        while (Wyspa.wskaznikEwaulacji  < maxEwaulacji) {
            try {
            	
                if (wyspy.size() == 1) break;
                //zmien kryterium na 10000 ewaulacji

                    for (int i = 0; i < wyspy.size(); i++) {
                        System.out.println("========== WYSPA " + wyspy.get(i).numerWyspy + " populacja:" + wyspy.get(i).podpopulacja + "==============");
                        wyspy.get(i).run(env);
                        //System.out.println(wyspy.get(i).najlepszeRozwiazanie);
                    }
                    
                    for(int i = 0; i < wyspy.size();i++) {
                    	if(najlepszaWyspaRozwiazanie >= wyspy.get(i).najlepszeRozwiazanie){
                    		najlepszaWyspaRozwiazanie = wyspy.get(i).najlepszeRozwiazanie;
                    		najlepszaWyspaPopulacja = wyspy.get(i).podpopulacja;
                    	}
                    }
                    
                    //probkowanie najlepszego globalnego co 2k
                    	System.out.println("zapisz");
                    	String pomiar = najlepszaWyspaPopulacja+";"+Wyspa.wskaznikEwaulacji+";"+najlepszaWyspaRozwiazanie+";";
                    	zapisywaczGlobalnych.WriteToFile(pomiar);
                    
                    Wyspa wyspaNiepowodzenia = dajWyspeNiepowodzenia(wyspy);

                    int licznik = wyspaNiepowodzenia.licznikNiepowodzen;

                    if (licznik >= maxNiepowodzen) {
                        wyspy.remove(wyspaNiepowodzenia);
                        System.out.println("usunąłem wyspę");
                    } else {
                    	wyspaNiepowodzenia.licznikNiepowodzen += 1;
                    }
                    
                    System.out.println("Obecnie najgorsza wyspa "+wyspaNiepowodzenia.podpopulacja+" ilość niepowodzeń: "+wyspaNiepowodzenia.licznikNiepowodzen+" najlepsza wartosc:"+wyspaNiepowodzenia.najlepszeRozwiazanie);

                //pomiar najlepszych globalnych rozwiazan

            } catch (java.util.NoSuchElementException brakWyspException) {
                System.out.println("Brak wysp");
                return;
            }
        }
        
        for (Wyspa wyspa: wyspy)
            System.out.println("wyspa " + wyspa.numerWyspy + " " + wyspa.najlepszeRozwiazanie);
        
        System.out.println(Wyspa.wskaznikEwaulacji);
        System.out.println("Wyspa:"+najlepszaWyspaPopulacja+":"+najlepszaWyspaRozwiazanie);
        

    }
 

    public static void zapiszWszysktieWartosciFunkcji(Wyspa wyspa, int powtorzenie) {
        Zapisywacz zapisywaczWszystkich = new Zapisywacz("wszystkieWartosciWyspy" + "." + powtorzenie + "." + wyspa.numerWyspy + "." + wyspa.podpopulacja + ".txt");
    }
    
}