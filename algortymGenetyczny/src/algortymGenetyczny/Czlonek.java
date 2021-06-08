package algortymGenetyczny;

public class Czlonek implements Comparable<Czlonek> {


    double Ai, Bi;
    double precyzja = 1000d;
    int MI;
    int n = 1;
    char[][] chromosome;
    double[] wartosciGenow;
    double wartoscDlaFunkcji = 9999.9;
    
    Rastrigin funkcja;

    public Czlonek(double Ai, double Bi, int n, double precyzja) {
        this.Ai = Ai; // wartosc poczatku przedzialu wartosci
        this.Bi = Bi; // wartosc konica przedzialu wartosci
        this.n = n; // liczba genow
        this.precyzja = precyzja;
        MI = Mi();
        chromosome = stworz();
        wartosciGenow = new double[n];
        funkcja = new Rastrigin(10,n);
    }

    @Override
    public int compareTo(Czlonek tenDrugi) {
    	if(this.wartoscDlaFunkcji < tenDrugi.wartoscDlaFunkcji) {
    		return -1;
    	}
    	else if(this.wartoscDlaFunkcji > tenDrugi.wartoscDlaFunkcji) {
    		return 1;
    	}
    	else {
    		return 0;
    	}
    }
   
    public void ustawChromosom(char[][] values) {
        chromosome = values;
    }

    //wyliczenie ilu bit�w potrzebne jest do zakodowania 1 genu.
    public int Mi() {
        return (int) Math.round(Log2((Bi - Ai) * precyzja));
    }

    //c : generowanie losowo chromosomu w postaci binarnej
    private double decoding(char[] gen) {
        double rezultat = Ai + Integer.parseInt(new String(gen), 2) * (Bi - Ai) / (Math.pow(2, MI) - 1);
        return Math.round(rezultat * precyzja) / precyzja;
    }
    private char[] stworzGen() {
        char[] gen = new char[MI];
        for (int i = 0; i < MI; i++) {
            gen[i] = 0 + (int)(Math.random() * 2) == 0 ? '0' : '1';
        }
        return gen;
    }
    private char[][] stworz() {
        char[][] _chromosome = new char[n][MI];
        double wartosc = 0.0;
        char[] gen = new char[MI];
        for (int i = 0; i < n; i++) {
            gen = stworzGen();
            wartosc = decoding(gen);
            while (!(wartosc >= -5.12 && wartosc <= 5.12)) {
                gen = stworzGen();
                wartosc = decoding(gen);
            }
            _chromosome[i] = gen;
        }
        return _chromosome;
    }

    public double obliczWartoscFunkcji() {
    	return funkcja.apply(dajWartosciGenow(chromosome));
    }
    
    //dostanie dowolnego genu po przez podanie jego numeru
    public String getXn(int gen) {
        return new String(chromosome[gen]);
    }
    
    //dekodowanie zakodowanego binarnie genu na jego wartosc decymalna
    public double decoding(int gen) {
        double result = Ai + Integer.parseInt(getXn(gen), 2) * (Bi - Ai) / (Math.pow(2, MI) - 1);
        return Math.round(result * precyzja) / precyzja;
    }

  
    public char[] dajLiniowo() {
        char[] linia = new char[MI * n];
        int wskaznik = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < MI; j++) {
                linia[wskaznik] = chromosome[i][j];
                wskaznik++;
            }
        }
        return linia;
    }

    public String toString() {
        String chromosome = "";
        for (int i = 0; i < n; i++)
            chromosome += getXn(i);
        return chromosome;
    }

    private double Log2(double x) {
        return (Math.log(x) / Math.log(2));
    }
    
    public static char[][] zamienNaGeny(char[] linia, int n, int MI) {
        char[][] _geny = new char[n][MI];
        int wskaznik = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < MI; j++) {
                _geny[i][j] = linia[wskaznik];
                wskaznik++;
            }
        }
        return _geny;
    }
    
    private double[] dajWartosciGenow(char[][] chromosome) {
    	double [] wartosci = new double[n];
    	
    	for(int i = 0; i < n; i++) {
    		wartosci[i] = decoding(chromosome[i]);
    	}
    	return wartosci;
    }
   
}