package algortymGenetyczny;

import java.util.ArrayList;
import java.util.Collections;


//sprawdzasz czy obecny najlepszy jest lepszy niz inny

public class Turniej {
	Czlonek [] zawodnicy;
	
	public Turniej(Czlonek[] zawodnicy) {
		this.zawodnicy = zawodnicy;
		
	}
	
	
	
	public Czlonek[] calyTurniej() {
		int wielkoscPopulacji = zawodnicy.length;
		Czlonek [] zwyciezcy = new Czlonek[wielkoscPopulacji];
		
		//zostawienie jednego pustego miejsca na wpisanie obecnie najlepszego aby nie zgubic tego rozwiazania
		for(int i = 0; i < wielkoscPopulacji; i++) {
			zwyciezcy[i] = przeprowadzMalyTurniej();
		}
		return zwyciezcy;
	}
	
	private Czlonek przeprowadzMalyTurniej() {
		int najlepszyLos = getRandomNumber(0,zawodnicy.length);
//		int wylosowany1 = getRandomNumber(0,pula.size());
//		int wylosowany2 = getRandomNumber(0,pula.size());
		ArrayList<Integer> wylosowani = new ArrayList<Integer>();
		//wylosowani.add(najlepszyLos);
		ArrayList<Czlonek> kokurenci = new ArrayList<Czlonek>();
		
		Czlonek najlepszy = zawodnicy[najlepszyLos];
	
		while(wylosowani.size() < 3){
			int indeks = getRandomNumber(0,zawodnicy.length);
			if(!wylosowani.contains(indeks) && indeks != najlepszyLos) {
				wylosowani.add(indeks);
				kokurenci.add(zawodnicy[indeks]); // ogarnij kopie; bo referencje
			}
		
		}
		
		
		for(int i = 0; i < wylosowani.size();i++) {
			if(najlepszy.wartoscDlaFunkcji > kokurenci.get(i).wartoscDlaFunkcji) {
				najlepszy = kokurenci.get(i);
			}
		}
		
		
		return najlepszy;
		
	}
	
	protected int getRandomNumber(int min, int max) {
	    return (int)((Math.random() * (max - min)) + min);
	}
}
