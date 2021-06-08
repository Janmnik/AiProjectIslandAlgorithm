package algortymGenetyczny;

import java.util.ArrayList;
public class Turniej {
	Czlonek [] zawodnicy;
	
	public Turniej(Czlonek[] zawodnicy) {
		this.zawodnicy = zawodnicy;
	}
	
	static ArrayList<Double> wylosowani = new ArrayList<Double>();
	
	public Czlonek[] calyTurniej() {
		int wielkoscPopulacji = zawodnicy.length;
		Czlonek [] zwyciezcy = new Czlonek[wielkoscPopulacji];
		
		//zostawienie jednego pustego miejsca na wpisanie obecnie najlepszego aby nie zgubic tego rozwiazania
		for(int i = 0; i < wielkoscPopulacji; i++) {
			zwyciezcy[i] = przeprowadzMalyTurniej();
			wylosowani.add(zwyciezcy[i].wartoscDlaFunkcji);
		}
		
		wylosowani.clear();
		return zwyciezcy;
	}
	
	private Czlonek przeprowadzMalyTurniej() {
		int wylosowany1 = getRandomNumber(0,zawodnicy.length);
		int wylosowany2 = getRandomNumber(0,zawodnicy.length);
			
		Czlonek zawodnik1 = zawodnicy[wylosowany1];
		Czlonek zawodnik2 = zawodnicy[wylosowany2];
		
		if(zawodnik1.wartoscDlaFunkcji <= zawodnik2.wartoscDlaFunkcji) {
			return zawodnik1;
		}
		return zawodnik2;
		
	}
	
	protected int getRandomNumber(int min, int max) {
	    return (int)((Math.random() * (max - min)) + min);
	}
}
