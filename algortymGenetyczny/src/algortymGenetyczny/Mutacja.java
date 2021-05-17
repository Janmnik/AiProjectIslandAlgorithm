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
		for(int i = 0; i < czlonek.chromosome.length;i++) {
			double _propability = Math.random();
			if(_propability < propability) {
				czlonek.chromosome[i] = czlonek.chromosome[i] == '0' ? '1' : '0';
			}
		}
		return czlonek;
	}
	
	public Czlonek czlonekMutacja(Czlonek czlonek) {
		for(int i = 0; i < czlonek.chromosome.length;i++) {
			double _propability = Math.random();
			if(_propability < propability) {
				czlonek.chromosome[i] = czlonek.chromosome[i] == '0' ? '1' : '0';
			}
		}
		return czlonek;
	}
	
}

