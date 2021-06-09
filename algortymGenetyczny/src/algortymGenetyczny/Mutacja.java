package algortymGenetyczny;

public class Mutacja {
	private double propability = 0.1;
	Czlonek czlonek;
	
	public Mutacja(Czlonek czlonek,double propability) {
		this.propability = propability; //prawdopodobienstwo
		this.czlonek = czlonek;
	}
	
	public Mutacja(double propability) {
		this.propability = propability; //prawdopodobienstwo
	}
	
	public Czlonek czlonekMutacja() {
		char chromosome [] = czlonek.dajLiniowo();
		for(int i = 0; i < czlonek.chromosome.length;i++) {
			double _propability = Math.random();
			if(_propability < propability) {
				chromosome[i] = chromosome[i] == '0' ? '1' : '0';
			}
		}
		return czlonek;
	}
	
	public char[] czlonekMutacja(char [] chromosome) {
		for(int i = 0; i < chromosome.length;i++) {
			double _propability = Math.random();
			if(_propability < propability) {
				chromosome[i] = chromosome[i] == '0' ? '1' : '0';
			}
		}	
		return chromosome;
	}
	
}

