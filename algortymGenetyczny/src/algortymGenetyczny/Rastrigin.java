package algortymGenetyczny;



public class Rastrigin {
    double amplitude;
    int numberOfVariables;

    public Rastrigin() {
    }

    public Rastrigin(double amplitude, int numberOfVariables){
        this.amplitude = amplitude;
        this.numberOfVariables = numberOfVariables;
    }

    private double func(double x){
        return Math.pow(x,2) - (amplitude * Math.cos(2 * Math.PI * x));
    }

    public double apply(double[] arr) {
        double sum = 0.0;
        for (int i = 0; i < arr.length; i++) {
            sum += func(arr[i]);
        }
        return (amplitude * numberOfVariables + sum);
    }
}
