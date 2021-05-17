package algortymGenetyczny;

import java.util.ArrayList;
import java.util.Arrays;

public class WyspowyAlgorytm {
	
	
	static FunkcjaCeluZad1 calc1 = new FunkcjaCeluZad1() {

		@Override
		public double func(double x1,double x2) {
			return Math.round((-1*( Math.pow(x1, 2) ) - Math.pow(x2, 2) +2.0)*100000d)/100000d; 
		}
	};
	
	public static void main(String[] args) {
		
		ArrayList<Algorytm> wyspy = generujWyspy(new Algorytm(-2,2,100000d,2,20,20,0.02,0.6,50));
		
		try {
			for(Algorytm wyspa : wyspy) {
				wyspa.run(calc1);
			}
			
		}catch(CloneNotSupportedException cloneEx) {
			cloneEx.printStackTrace();
		}
	}
	
	
	//generowanie wysp wraz z jej podpopulacjami
	private static ArrayList<Algorytm> generujWyspy(Algorytm algorytm){
		
		ArrayList<Integer> wielkosciPodpopulacji = new ArrayList<Integer>();
		
		int calosc = algorytm.PopulacjaLength;
		
		while(calosc > 0) {
			int wylosowanaPodpopulacja = getRandomNumber(2,calosc);
			System.out.println(wylosowanaPodpopulacja);
			wielkosciPodpopulacji.add(wylosowanaPodpopulacja);
			calosc -= wylosowanaPodpopulacja;
		}
		
		ArrayList<Algorytm> wyspy = new ArrayList<Algorytm>();
		
		for(Integer wielkosc : wielkosciPodpopulacji) {
			wyspy.add(new Algorytm(algorytm.Ai,algorytm.Bi,algorytm.precision,algorytm.n,wielkosc,algorytm.env,algorytm.MutacjaPropability,algorytm.krzyzowaniePropability,20));
		}
		
		return wyspy;
	}
	
	private static int getRandomNumber(int min, int max) {
	    return (int)((Math.random() * (max - min)) + min);
	}

}