package algortymGenetyczny;
import java.util.Arrays;
import java.util.Stack;

public class Krzyzowanie8Punkt {
	
	int dlugoscChromosomu;
	int [] punktyKrzyzowania = new int[8];
	
	public Krzyzowanie8Punkt(int _dlugoscChromosomu) {
		dlugoscChromosomu = _dlugoscChromosomu;
		punktyKrzyzowania = Krzyzowanie8Punkt.losujPunkty(_dlugoscChromosomu);
		Arrays.sort(punktyKrzyzowania);
	}
	
	private static  int [] losujPunkty(int dlugosc) {
		Stack<Integer> wylosowaneJuzPunkty = new Stack<Integer>();
		int [] wylosowanePunkty = new int[8];
		int wylosowanyPunkt = 0;
		for(int i = 0; i < 8; i++) {
			wylosowanyPunkt = Krzyzowanie8Punkt.losujPunkt(0,dlugosc);
			while(wylosowaneJuzPunkty.contains(wylosowanyPunkt)) {
				wylosowanyPunkt = Krzyzowanie8Punkt.losujPunkt(0,dlugosc);
			}
			wylosowanePunkty[i] = wylosowanyPunkt;
		}
		return wylosowanePunkty;
	}
	
//	public Czlonek dajDziecko(Czlonek rodzicX, Czlonek rodzicY) {
//		
//		char [] dzieckoChromosome = new char[dlugoscChromosomu];
//		for(int i = 1; i < punktyKrzyzowania.length;i++) {
//			int punkt1 = punktyKrzyzowania[i-1];
//			int punkt2 = punktyKrzyzowania[i];
//			
//			for(int j = punkt1; j < punkt2; j++) {
//				dzieckoChromosome[j] = rodzicX.chromosome[j];
//			}
//		}
//	}
	
	
	private static int losujPunkt(int max, int min) {
		return (int)((Math.random() * (max - min)) + min);
	}
}
