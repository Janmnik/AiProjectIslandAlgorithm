package algortymGenetyczny;
import java.util.ArrayList;
import java.util.Random;

public class Krzyzowanie8Punkt {
	
	int dlugoscChromosomu;
	ArrayList<Integer> punktyKrzyzowania = new ArrayList<Integer>();
	
	public Krzyzowanie8Punkt(int _dlugoscChromosomu) {
		dlugoscChromosomu = _dlugoscChromosomu;
		punktyKrzyzowania = losujPunkty(8);
	
	}
	
	private ArrayList<Integer> losujPunkty(int dlugosc) {
		 ArrayList<Integer> numbers = new ArrayList<Integer>();   
		    Random randomGenerator = new Random();
		    while (numbers.size() < dlugosc){
		      int random = losujPunkt(1,dlugoscChromosomu);
		      if (!numbers.contains(random)) {
		        numbers.add(random);
		      }
		    }
		
		numbers.sort(null);    
		return numbers;
	}
	
	public char[] dajDziecko(char [] rodzicX, char[] rodzicY) {
		
		char [] dzieckoChromosom = new char[dlugoscChromosomu];

		int in = 0;
	    int kolej = 0;
	    //powtorz tyle razy ile jest punktow krzyzownia
	    for(int i = 0; i < punktyKrzyzowania.size();i++){
	      //wez punkt krzyzowania
	      int punkt = punktyKrzyzowania.get(i);
	      //dopoki nie dojdziesz do punktu krzyzowania
	      do {
	        //czesc rodzica 1
	        if(kolej % 2 == 0){
	        	dzieckoChromosom[in] = rodzicX[in];
	        }
	        //czesc rodzica 2
	        else{
	        	dzieckoChromosom[in] = rodzicY[in];
	        }

	        in++;
	       
	      }while(in < punkt);
	      kolej++;
	    }
	   
	    //dla reszty: od punktu ostatniego do konca
	    while(in < rodzicX.length){
	      //czesc rodzica 1
	        if(kolej % 2 == 0){
	        	dzieckoChromosom[in] =rodzicX[in];
	        }
	        //czesc rodzica 2
	        else{
	        	dzieckoChromosom[in] = rodzicY[in];
	        }
	        in++;
	    }
	    
		return dzieckoChromosom;
	}
	
	
	private static int losujPunkt(int max, int min) {
		return (int)((Math.random() * (max - min)) + min);
	}
}
