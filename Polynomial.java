public class Polynomial {
    double [] coefficients;

    public Polynomial(){
        double [] arr = {0.0};
        this.coefficients = arr;
    }

    public Polynomial(double [] coefficients){
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial polynomial){
        double [] resultCoefficents;

        if (coefficients.length >= polynomial.coefficients.length){
            double [] arr = new double[coefficients.length];

            for(int i = 0; i < polynomial.coefficients.length; i++){
                arr[i] = coefficients[i] + polynomial.coefficients[i];
            }

            for (int i = polynomial.coefficients.length; i < coefficients.length; i++){
                arr[i] = coefficients[i];
            }

            resultCoefficents = arr;

        }else{
            double [] arr = new double[polynomial.coefficients.length];

            for(int i = 0; i < coefficients.length; i++){
                arr[i] = coefficients[i] + polynomial.coefficients[i];
            }

            for (int i = coefficients.length; i < polynomial.coefficients.length; i++){
                arr[i] = polynomial.coefficients[i];
            }


            resultCoefficents = arr;
        }

        return new Polynomial(resultCoefficents);


    }

    public double evaluate(double x){
        double result = 0;

        for(int i = 0; i < coefficients.length; i++){
            double multiplier = 1;
            for(int j = 0; j <= i; j++){
                multiplier = multiplier * x;
            }
            result += coefficients[i] * multiplier  ;
        }

        return result;
    }

    public boolean hasRoot(double x){
        if(evaluate(x) == 0){
            return true;
        }
        return false;
    }


}