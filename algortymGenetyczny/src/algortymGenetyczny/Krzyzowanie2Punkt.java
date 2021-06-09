package algortymGenetyczny;

public class Krzyzowanie2Punkt {
	int chromosomeLength;
	private int punktKrzyz1,punktKrzyz2; //punkt wydzielenia 2 czesci
	
	public Krzyzowanie2Punkt(int _chromosomeLength){
		chromosomeLength = _chromosomeLength;	
	}
	
	public void wylosujPunktyKrzyzowania() {
		punktKrzyz1 = getRandomNumber(0,chromosomeLength);
		punktKrzyz2 =  getRandomNumber(0,chromosomeLength);
		sprawdzPunktyKrzyzowania();
	}
	protected char[] dajDziecko(char[] parentX, char[] parentY) {
		char [] dzieckoChromosome = new char[chromosomeLength];
//		System.out.println("===== RODZIC1 ======");
//		System.out.println(parentX);
//		System.out.println("===== RODZIC2 ======");
//		System.out.println(parentY);
//		System.out.println(punktKrzyz1+" "+punktKrzyz2);
		//pierwsza czesc z rodzica X
		for(int i = 0; i < punktKrzyz1;i++) {
			dzieckoChromosome[i] = parentX[i];
		}
		//srodek z rodzica Y
		for(int i = punktKrzyz1; i < punktKrzyz2; i++) {
			dzieckoChromosome[i] = parentY[i];		
		}
		//koniec z rodzica X
		for(int i = punktKrzyz2; i < chromosomeLength; i++) {
			dzieckoChromosome[i] = parentX[i];		
		}
		
		
		return dzieckoChromosome;
	}
	
	//sprawdzam czy punkt 1 krzyzowania jest < niz punkt 2 krzyzowania ; jesli nie to zamieniam je 
	private void sprawdzPunktyKrzyzowania() {
		if(punktKrzyz1 > punktKrzyz2) {
			int point = punktKrzyz1;
			punktKrzyz1 = punktKrzyz2;
			punktKrzyz2 = point;
		}
		
		if(punktKrzyz1 == punktKrzyz2) {
			punktKrzyz1 = getRandomNumber(0,chromosomeLength);
			punktKrzyz2 =  getRandomNumber(0,chromosomeLength);
		}
	}
	
	protected int getRandomNumber(int min, int max) {
	    return (int)((Math.random() * (max - min)) + min);
	}
	
}