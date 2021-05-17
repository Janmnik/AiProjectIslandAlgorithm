package algortymGenetyczny;

public class Ruletka {

	Populacja bazaPopulacja;
	Populacja nowaPopulacja;
	double[] decisionRouletteSectors;
	private double u;
	double przeskalowane[];
	public Ruletka(Populacja _Populacja, String aim) {
		bazaPopulacja = _Populacja;
		
		przeskalowane = new double[_Populacja.n];
		//u = przesuniecie; gdy wartosci dopasowania sa na "-" nale�y przesun�� warto�ci o min warto�� z populacji
		u = bazaPopulacja.Adaptation.MIN;
		
		if(u <= 0) {
			_Populacja.Adaptation.setAdaptation(scaleAdaptation(_Populacja.Adaptation)); 
		}
		
		decisionRouletteSectors = calculateRouletteSectors(bazaPopulacja);
		
		if (aim.toLowerCase().equals("max")) {
			nowaPopulacja = selectMaxPopulacjaCzlonki(bazaPopulacja);
		} else {
			nowaPopulacja = selectMinPopulacjaCzlonki(bazaPopulacja);
		}
	}
	
	
	private double [] scaleAdaptation(Populacja.Adaptation adaptation) {
		int length = adaptation.adaptation.length;
		double scaled [] = new double[length];
		for(int i = 0; i < length; i++) {
			scaled[i] = adaptation.adaptation[i]+Math.abs(u)+1;
		}
		return scaled;
	}
	
	private double sumAdaptation(Populacja.Adaptation adaptation) {
		double sum = 0;

		for (int i = 0; i < adaptation.adaptation.length; i++) {
			sum += adaptation.adaptation[i];
		}
		
		return sum;
	}

	double[] calculateRouletteSectors(Populacja Populacja) {
		double begin = 0;
		double area = 0.0;
		double[] rouletteAreaSectors = new double[Populacja.Populacja.length];
		double sumAdaptation = sumAdaptation(Populacja.Adaptation);
		
		int n = Populacja.Populacja.length;
		
		for (int i = 0; i < n; i++) {
			area = calculateArea(calculatePropability(Populacja.Adaptation.adaptation[i], sumAdaptation));
			rouletteAreaSectors[i] = begin + area;
			begin += area;
		}
		return rouletteAreaSectors;
	}

	private double calculatePropability(double adaptation, double sumAdaptation) {
		return adaptation / sumAdaptation;
	}

	private double calculateArea(double propability) {
		return propability * 100;
	}

	private Czlonek getChromosomeFromRoulette(int percent) {
		int chromosomeNumber = 1;
		int lenght = decisionRouletteSectors.length;
		for (int i = 1; i < lenght; i++) {
			if (decisionRouletteSectors[i - 1] >= percent && decisionRouletteSectors[i] > percent) {
				break;
			}
			chromosomeNumber++;
		}
		return bazaPopulacja.Populacja[chromosomeNumber - 1];
	}

	public Populacja selectMaxPopulacjaCzlonki(Populacja Populacja) {

		int length = Populacja.n;
		int[] randomDigits = new int[length];

		Czlonek[] Populacja1 = new Czlonek[length];

		// losowanie liczb, ktore maja wskazywac obszar na kole, czyli wybor nowych
		// osobnikow do populacji
		for (int i = 0; i < length; i++) {
			randomDigits[i] = (int) (Math.random() * 100);
		}

		// tworzenie nowej populacji
		for (int i = 0; i < randomDigits.length; i++) {
			Populacja1[i] = getChromosomeFromRoulette(randomDigits[i]);
		}

		Populacja nowaPopulacja = bazaPopulacja.setPopulacja(Populacja1);

		return nowaPopulacja;
	}

	public Populacja selectMinPopulacjaCzlonki(Populacja Populacja) {
		return null;
	}

}