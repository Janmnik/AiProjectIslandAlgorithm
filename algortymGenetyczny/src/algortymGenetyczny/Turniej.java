package algortymGenetyczny;

public class Turniej {
	
	
	
	
	Populacja bazaPopulacja;
	
	public Turniej(Populacja bazaPopulacja) {
		
		this.bazaPopulacja=bazaPopulacja;
	}
	private Populacja wylosowanie() {
		
	
		int length = bazaPopulacja.n;
		/*  wylosowanie dwóch osobników  */
		int los1=(int) (Math.random()*(length - 0) + 0);
		
		//tablica osobników
		
		Czlonek tablicaOsob[]= new Czlonek[length];
		 
		for(int i=0;i<length;i++) {
			
			tablicaOsob[i]=bazaPopulacja.Populacja[los1];
			los1=(int)(Math.random()*(length));
		}
			Populacja nowaPopulacja = bazaPopulacja.setPopulacja(tablicaOsob);

				
		return nowaPopulacja;
	}
	
	
	private Czlonek wyborZwyciezcy(){
		//wybór osobnika z ni¿sz¹ wartoœci¹ funkcji	
		
		Populacja nowaPopulacja=wylosowanie();
		
		return nowaPopulacja.Adaptation.PopulacjaMinCzlonek();
		
	}
	
	public Populacja PopulacjaNajlepszaWygrana() {
		
		int length = bazaPopulacja.n;
		Czlonek nowaTabCzlonkow[] = new Czlonek[length];
		for(int i=0;i<length;i++) {
			nowaTabCzlonkow[i]=wyborZwyciezcy();
		}
		return bazaPopulacja.setPopulacja(nowaTabCzlonkow);
	}
	
	
	
	
		
		//dodanie osobnika do nowej populacji
		

}
