package algortymGenetyczny;

public class Algorytm {

    public static double Ai = -5.12;
    public static double Bi = 5.12;
    static double precyzja = 1000;
    public int liczbaGenow = 10;
    public double mutacjaPrawdopodobienstwo = 0.04;
    public double krzyzowaniePrawdopodobienstwo = 0.6;
    
    public int wielkoscPopulacji;
    public double precision;

    public int generacja;

    public double najlepszeRozwiazanie;
    public int wskaznikEwaulacji = 0;
    
    Czlonek[] populacja;
    
    Czlonek najlepszy = null;
    
    int nrWyspy;

    int ewaulacje;

    public Algorytm(int _nrWyspy, int _n, int _PopulacjaLength, double _MutacjaPropability, double _krzyzowaniePropability) {
        nrWyspy = _nrWyspy;
        mutacjaPrawdopodobienstwo = _MutacjaPropability;
        krzyzowaniePrawdopodobienstwo = _krzyzowaniePropability;
        wielkoscPopulacji = _PopulacjaLength;
        liczbaGenow = _n;
        //krok 1 tworzymy populacje bazowa
        populacja = stworzPopulacje();
        System.out.println("konstruktor");
    }

    
    private Czlonek[] stworzPopulacje(){
    	
    	Czlonek [] _populacja = new Czlonek[wielkoscPopulacji];
    	for(int i = 0; i < wielkoscPopulacji; i++) {
    		_populacja[i] = new Czlonek(Ai,Bi,liczbaGenow,precyzja);
    		
    	}
    	return _populacja;
    }
    
    
    private Czlonek znajdzNajlepszegoObecnie(Czlonek [] populacja) {
   
    	Czlonek najlepszy = populacja[0];
    	for(int i = 1; i < wielkoscPopulacji; i++) {
    		if(najlepszy.compareTo(populacja[i]) == 1) {
    			najlepszy = populacja[i];
    		}
    	}
    	return najlepszy;
    }
    
    private void zmienPopulacje(Czlonek[] nowa) {
    	for(int i = 1; i < wielkoscPopulacji; i++) {
    		populacja[i] = nowa[i];
    	}
    }
    
    public Czlonek[] run(double env) {
        wskaznikEwaulacji = 0;
        while (wskaznikEwaulacji <= env) {
        	for(int i = 0; i < wielkoscPopulacji; i++) {
        		populacja[i].wartoscDlaFunkcji = populacja[i].obliczWartoscFunkcji();
        		wskaznikEwaulacji++;
        	}
        
        	//selekcja najlepszych rozwiazan do nowej populacji
        	Turniej turniej = new Turniej(populacja);
        	najlepszy = znajdzNajlepszegoObecnie(populacja);
        	najlepszeRozwiazanie = najlepszy.wartoscDlaFunkcji;
        	populacja = turniej.calyTurniej();
        	populacja[0] = najlepszy;
        	
        	//dokonanie mutacji:
        	populacja = mutujPopulacje(populacja);
//        	
        	//dokonanie krzyzowania:
        	
        	if(liczbaGenow <= 20) {
        		populacja = krzyzuj2Punktowo(populacja);
        	}
        	else {
        		populacja = krzyzuj8Punktowo(populacja);
        		
        	}
        }
        return populacja;
    }
    
    private Czlonek[] krzyzuj2Punktowo(Czlonek[] populacja) {
    	Krzyzowanie2Punkt krzyzowanie2Punkt = new Krzyzowanie2Punkt(liczbaGenow*13);
    	for(int i = 0; i < populacja.length;i++) {
    		
    		if(Math.random() < krzyzowaniePrawdopodobienstwo) {
    			int partner = losujPartnera(wielkoscPopulacji,0);
    			char [] genyRodzicaX = populacja[i].dajLiniowo();
    			char [] genyRodzicaY = populacja[partner].dajLiniowo();
    			populacja[i].chromosome = Czlonek.zamienNaGeny(krzyzowanie2Punkt.dajDziecko(genyRodzicaX, genyRodzicaY),liczbaGenow,13);
    		}
    	}
    	return populacja;
    }
    private Czlonek[] krzyzuj8Punktowo(Czlonek[] populacja) {
    	Krzyzowanie8Punkt krzyzowanie8Punkt = new Krzyzowanie8Punkt(liczbaGenow*13);
    	for(int i = 0; i < populacja.length;i++) {
    		
    		if(Math.random() < krzyzowaniePrawdopodobienstwo) {
    			int partner = losujPartnera(wielkoscPopulacji,0);
    			char [] genyRodzicaX = populacja[i].dajLiniowo();
    			char [] genyRodzicaY = populacja[partner].dajLiniowo();
    			populacja[i].chromosome = Czlonek.zamienNaGeny(krzyzowanie8Punkt.dajDziecko(genyRodzicaX, genyRodzicaY),liczbaGenow,13);
    		}
    	}
    	return populacja;
    }
    
    private Czlonek[] mutujPopulacje(Czlonek[] populacja) {
    	char [] zmutowanyChromosom = new char[liczbaGenow*13];
    	Mutacja mutacja = new Mutacja(mutacjaPrawdopodobienstwo);
    	for(int i = 0; i < wielkoscPopulacji; i++) {
    		
    		//wybor czy dany gen ma zostac zmutowany
    		//if(Math.random() < mutacjaPrawdopodobienstwo) {
    			zmutowanyChromosom =  mutacja.czlonekMutacja(populacja[i].dajLiniowo());
    			populacja[i].chromosome = Czlonek.zamienNaGeny(zmutowanyChromosom, liczbaGenow, 13);
    		//}
    	}
    	return populacja;
    }
    
    private static void zapiszNajlepszeLokalneRozwiazania(String pomiar, int populacja) {

        Zapisywacz zapisywaczNajlepszychLokalnie = new Zapisywacz("najlepszeLokalneWyspy" + populacja + "." + ".txt");
        zapisywaczNajlepszychLokalnie.WriteToFile(pomiar);
    }

    private static void zapiszRozwiazania(String pomiar, int populacja) {

        Zapisywacz zapisywaczNajlepszychLokalnie = new Zapisywacz("wszystkieRozwiazania" + populacja + "." + ".txt");
        zapisywaczNajlepszychLokalnie.WriteToFile(pomiar);
    }
    private static int losujPartnera(int max, int min) {
		return (int)((Math.random() * (max - min)) + min);
	}

}