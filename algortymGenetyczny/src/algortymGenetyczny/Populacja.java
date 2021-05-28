package algortymGenetyczny;

import java.util.Arrays;

public class Populacja {
	//klasa wewnetrzna odpowiedzialna za wylicznie przystosowania populacji
	class Adaptation{
		
		Czlonek Populacja [];
		double adaptation [];
		Rastrigin goal;
		double AVG,MIN;
		
		Adaptation(Rastrigin goal, Czlonek[] Populacja){
			this.goal = goal;
			this.Populacja = Populacja;
			this.adaptation = adaptPopulacja(goal);
			this.AVG = calcAVGAdaptation();
			this.MIN = getMin(adaptation);
		}
		
		
			
		private double [] adaptPopulacja(Rastrigin goal) {
			
			int n = Populacja.length;
			double adaptation [] = new double[n];
			
			Double wartoscChromosom [][] = new Double[n][Populacja[0].n];
			for(int i=0;i<n;i++) {
				for(int k=0;k<Populacja[i].n;k++) {
					wartoscChromosom[i][k]=Populacja[i].decoding(k+1);
				}
			}
			
			for(int i = 0; i<n;i++) {
				
				adaptation[i] = goal.apply(wartoscChromosom[i]);
			}
			
			this.MIN = getMin(adaptation);
			
			if(GLOBALMIN > MIN)
				GLOBALMIN = MIN;
			
			return adaptation;
		}
		public Czlonek PopulacjaMinCzlonek() {
			int MinIndex=0;
			int n=Populacja.length;
			for(int i=0;i<n;i++) {
				if(adaptation[i]==this.MIN) {
					MinIndex=i;
					break;
				}
				
			}
			return Populacja[MinIndex];
		}
		
		private double getMin(double adaptation[]) {
			return Arrays.stream(adaptation).min().getAsDouble();
		}
		
		public void setAdaptation(double _adaptation[]) 
		{
			this.adaptation = _adaptation;
			calcAVGAdaptation();
			this.MIN = getMin(adaptation);
		}
		
		
		public void showAdaptation() {
			int n = adaptation.length;
			for(int i = 0 ; i < n; i++) {
				System.out.println("ADAP:"+adaptation[i]+"| X1:"+Populacja[i].decoding(1)+"; X2:"+Populacja[i].decoding(2));
			}
			System.out.println("MIN:"+MIN);
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
	static double GLOBALMIN = 0.0;

	
	public Populacja(Czlonek base,int n) {
		this.base = base;
		this.n = n;
		Populacja = generujPopulacja();
	}
	
	public void adaptPopulacja(Rastrigin goal) {
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
	}
	
	
}