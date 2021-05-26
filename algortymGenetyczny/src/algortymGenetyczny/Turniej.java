package algortymGenetyczny;

public class Turniej {
	
	
	static FunkcjaCeluZad1 calc1 = new FunkcjaCeluZad1() {

		@Override
		public double func(double x) {
			 return Math.pow(x,2) - (amplitude * Math.cos(2 * Math.PI * x));
		}
	};
	
	Populacja bazaPopulacja;
	Populacja nowaPopulacja;
	
	public Populacja wylosowanie(int n) {
		
		Populacja Populacja = new Populacja(czlonek,n);
		int n = Populacja;
		int length = Populacja.n;
		/*  wylosowanie dwóch osobników  */
		int los1=(int) (Math.random()*(length - 0) + 0);
		int los2=(int) (Math.random()*(length - 0) + 0);
		while(los2==los1)
		{
			los2=(int) (Math.random()*(length - 0) + 0);
		}
		
		return Populacja;
	}
	
	

}
