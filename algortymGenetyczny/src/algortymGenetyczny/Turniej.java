package algortymGenetyczny;

import java.util.ArrayList;
public class Turniej {
	Czlonek [] zawodnicy;
	
	ArrayList<Integer> pula = new ArrayList<Integer>();
	public Turniej(Czlonek[] zawodnicy) {
		this.zawodnicy = zawodnicy;
		
		uzupelnijTurniej();
	}
	
	private void uzupelnijTurniej() {
		for(int i  = 0; i < zawodnicy.length;i++) {
			pula.add(i);
		}
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
		int wylosowany1 = getRandomNumber(0,pula.size());
		int wylosowany2 = getRandomNumber(0,pula.size());
			
		Czlonek zawodnik1 = zawodnicy[wylosowany1];
		Czlonek zawodnik2 = zawodnicy[wylosowany2];
		
		if(zawodnik1.wartoscDlaFunkcji <= zawodnik2.wartoscDlaFunkcji) {
			pula.remove(wylosowany1);
			return zawodnik1;
		}
		pula.remove(wylosowany2);
		return zawodnik2;
		
	}
	
	protected int getRandomNumber(int min, int max) {
	    return (int)((Math.random() * (max - min)) + min);
	}
}
