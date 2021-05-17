package algortymGenetyczny;

import java.util.Arrays;

public class Populacja {
	//klasa wewnetrzna odpowiedzialna za wylicznie przystosowania populacji
	class Adaptation{
		
		Czlonek Populacja [];
		double adaptation [];
		FunkcjaCeluZad1 goal;
		double AVG,MIN,MAX;
		
		Adaptation(FunkcjaCeluZad1 goal, Czlonek[] Populacja){
			this.goal = goal;
			this.Populacja = Populacja;
			this.adaptation = adaptPopulacja(goal);
			this.AVG = calcAVGAdaptation();
			this.MIN = getMin(adaptation);
			this.MAX = getMax(adaptation);
		}
		
		public double adaptGenome(Czlonek genome) {
			return goal.func(genome.decoding(1),genome.decoding(2));
		}
			
		private double [] adaptPopulacja(FunkcjaCeluZad1 goal) {
			
			int n = Populacja.length;
			double adaptation [] = new double[n];
			
			for(int i = 0; i<n;i++) {
				adaptation[i] = goal.func(Populacja[i].decoding(1),Populacja[i].decoding(2));
			}
			
			this.MIN = getMin(adaptation);
			this.MAX = getMax(adaptation);
			
			if(GLOBALMAX < MAX)
				GLOBALMAX = MAX;
			
			return adaptation;
		}
		
		private double getMax(double adaptation[]) {
			return Arrays.stream(adaptation).max().getAsDouble();
		}
		
		private double getMin(double adaptation[]) {
			return Arrays.stream(adaptation).min().getAsDouble();
		}
		
		public void setAdaptation(double _adaptation[]) 
		{
			this.adaptation = _adaptation;
			calcAVGAdaptation();
			//this.MIN = getMin(adaptation);
		}
		
		
		public void showAdaptation() {
			int n = adaptation.length;
			for(int i = 0 ; i < n; i++) {
				System.out.println("ADAP:"+adaptation[i]+"| X1:"+Populacja[i].decoding(1)+"; X2:"+Populacja[i].decoding(2));
			}
			System.out.println("MIN:"+getMin(adaptation)+" MAX:"+getMax(adaptation));
		}
		
		public int invidualsBelowAVG() {
			int count = 0;
			for(int i = 0 ; i < n; i++) {
				if(adaptation[i] < AVG)
					count++;
			}
			return count;
		}
		
		public int invidualsAboveOrEqualAVG() {
			int count = 0;
			int n = adaptation.length;
			for(int i = 0 ; i < n; i++) {
				if(adaptation[i] >= AVG)
					count++;
			}
			return count;
		}
		
		private double calcAVGAdaptation() {
			int n = adaptation.length;
			double sum = 0.0;
			for(int i = 0 ; i < n; i++) {
				sum+=adaptation[i];
			}
			return sum / n ;
		}
	}
	
	int n = 1;
	
	Adaptation Adaptation;
	Czlonek Populacja[];
	Czlonek base;
	static double GLOBALMAX = 0.0;

	
	public Populacja(Czlonek base,int n) {
		this.base = base;
		this.n = n;
		Populacja = generujPopulacja();
	}
	
	public void adaptPopulacja(FunkcjaCeluZad1 goal) {
		this.Adaptation = new Adaptation(goal,Populacja);
	}
	
	private Czlonek[] generujPopulacja() {
		Czlonek[] Populacja = new Czlonek[n];
		
		for(int i = 0; i < n; i++) {
			Populacja[i] = new Czlonek(base.getStart(),base.getEnd(),base.n,base.precyzja);
		}
		return Populacja;
	}
	
	public void showPopulacjaAsBinary() {
		for(int i = 0 ; i < n; i++) {
			System.out.println(Populacja[i].chromosome);
		}
	}
	
	public void showPopulacja() {
		for(int i = 0 ; i < n; i++) {
			System.out.println("CHROMOSOME"+(i+1));
			System.out.println(new String(Populacja[i].chromosome)+"|"+Adaptation.adaptation[i]);
			for(int j = 1 ; j <= Populacja[i].n; j++)
			{
				System.out.print(Populacja[i].decoding(j)+",");
			}
			System.out.println();
		}
	}
	
	public boolean isBelowAVG(Czlonek genome) {
		return Adaptation.adaptGenome(genome) < Adaptation.AVG;
	}
	
	public boolean isAboveAVG(Czlonek genome) {
		return Adaptation.adaptGenome(genome) >= Adaptation.AVG;
	}
	
	public Populacja setPopulacja(Czlonek[] _Populacja) {
		Populacja populacja = new Populacja(this.base,this.n);
		populacja.Populacja = _Populacja;
		populacja.adaptPopulacja(Adaptation.goal);
		populacja.Adaptation.AVG = populacja.Adaptation.calcAVGAdaptation();
		return populacja;
	}
	
	public void showDetails() {
//		System.out.println("========Populacja AS BINARY==========");
//		showPopulacjaAsBinary();
//		System.out.println("========Populacja'S ADAPTATION==========");
//		Adaptation.showAdaptation();
		System.out.println("========Populacja'S VIEW==========");
		showPopulacja();
		System.out.println("======== AVG Populacja'S ADAPTATION==========");
		System.out.println(Adaptation.AVG);
		System.out.println("======== COUNT BELOW AVG==========");
		System.out.println(Adaptation.invidualsBelowAVG());
		System.out.println("======== COUNT ABOVE OR EQUAL AVG==========");
		System.out.println(Adaptation.invidualsAboveOrEqualAVG());
	}
	
	
}