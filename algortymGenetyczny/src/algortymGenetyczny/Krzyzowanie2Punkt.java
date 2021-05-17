package algortymGenetyczny;

public class Krzyzowanie2Punkt {
	int chromosomeLength;
	private int punktKrzyz1,punktKrzyz2; //punkt wydzielenia 2 czesci
	
	public Krzyzowanie2Punkt(int _chromosomeLength){
		chromosomeLength = _chromosomeLength;
		punktKrzyz1 = getRandomNumber(0,chromosomeLength);
		punktKrzyz2 =  getRandomNumber(0,chromosomeLength);
		checkpunktKrzyzs();
		
	}
	
	protected Czlonek getdziecko(Czlonek parentX, Czlonek parentY) {
		char [] dzieckoChromosome = new char[chromosomeLength];
		//pierwsza czesc z rodzica X
		for(int i = 0; i < punktKrzyz1;i++) {
			dzieckoChromosome[i] = parentX.chromosome[i];
		}
		//srodek z rodzica Y
		for(int i = punktKrzyz1; i < punktKrzyz2; i++) {
			dzieckoChromosome[i] = parentY.chromosome[i];		
		}
		//koniec z rodzica X
		for(int i = punktKrzyz2; i < chromosomeLength; i++) {
			dzieckoChromosome[i] = parentX.chromosome[i];		
		}
		
		Czlonek dziecko = new Czlonek(parentX.getStart(),parentX.getEnd(),parentX.n,parentX.precyzja);
		dziecko.chromosome = dzieckoChromosome;
		return dziecko;
	}
	
	//sprawdzam czy punkt 1 krzyzowania jest < niz punkt 2 krzyzowania ; jesli nie to zamieniam je 
	private void checkpunktKrzyzs() {
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