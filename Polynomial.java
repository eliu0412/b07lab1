import java.util.Arrays;
import java.io.*;
import java.util.*;


public class Polynomial {
    double [] coefficients;
    int [] exponents;

    public Polynomial(){
        double [] arr = {0.0};
        int [] exp = {};
        this.coefficients = arr;
        this.exponents = exp;

    }

    public Polynomial(double [] coefficients, int [] exponents){
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws IOException{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String polynomialString = reader.readLine();
            reader.close();

            String [] value = polynomialString.split("[+-]");

            String [] values;

            if(value[0].isEmpty()){
                values = new String[value.length - 1];
                for(int i = 1; i < value.length; i++){
                    values[i - 1] = value[i];
                }
            }else{
                values = value;
            }

            int [] signs = new int[values.length];


            int sign_index = 0;

            for(int i = 0; i < polynomialString.length(); i++){

                String cur = Character.toString(polynomialString.charAt(i));

                if(i == 0){
                    if(cur.equals("-")){
                        signs[sign_index] = 0;
                    }else{
                        signs[sign_index] = 1;
                    }
                    sign_index += 1;
                }
                else if(cur.equals("+")){
                    signs[sign_index] = 1;
                    sign_index += 1;
                }
                else if(cur.equals("-")){
                    sign_index += 1;
                }
        }

        double [] coeff = new double[values.length];
        int [] exp = new int[values.length];

        for(int i = 0; i < values.length; i++){
            String term = values[i];
            if(!term.contains("x")){
                coeff[i] = Double.parseDouble(term);
                exp[i] = 0;
            }else{
                String [] splitted = term.split("x");
                if(splitted.length == 0){
                    exp[i] = 1;
                    coeff[i] = 1;
                }
                else if(splitted.length == 1){
                    coeff[i] = Double.parseDouble(splitted[0]);
                    exp[i] = 1;
                }
                else if(splitted[0].isEmpty() || splitted[1].isEmpty()){
                    String first = Character.toString(term.charAt(0));
                    if(first.equals("x")){
                        coeff[i] = 1;
                        exp[i] = Integer.parseInt(splitted[1]);
                    }else{
                        coeff[i] = Double.parseDouble(splitted[0]);
                        exp[i] = 1;
                    }
                }else{
                    exp[i] = Integer.parseInt(splitted[1]);
                    coeff[i] = Double.parseDouble(splitted[0]);

                }
            }

            if(signs[i] == 0){
                coeff[i] *= -1;
            }
        }

        this.coefficients = coeff;
        this.exponents = exp;
    }



    public Polynomial add(Polynomial other){
        double [] other_coefficient = other.coefficients;
        int [] other_exponent = other.exponents;

        if(other_exponent.length == 0){
            return new Polynomial(coefficients, exponents);
        }

        if(exponents.length == 0){
            return new Polynomial(other_coefficient, other_exponent);
        }

        int max_len = 1 + Math.max(exponents[exponents.length - 1], other_exponent[other_exponent.length - 1]);

        double [] res_coeff = new double[max_len];
        int [] res_exp = new int[max_len];

        int index1 = 0;
        int index2 = 0;
        int res_index = 0;

        while (res_index < max_len && (index1 < exponents.length || index2 < other_exponent.length)){
            while(index1 < exponents.length && (index2 >= other_exponent.length || exponents[index1] <= other_exponent[index2])){
                res_exp[res_index] = exponents[index1];
                res_coeff[res_index] = coefficients[index1];

                if(index2 < other_exponent.length && exponents[index1] == other_exponent[index2]){
                    res_coeff[res_index] += other_coefficient[index2];
                    index2 += 1;
                }

                if(res_coeff[res_index] != 0) {
                    res_index += 1;
                }

                index1 += 1;
            }

            while(index2 < other_exponent.length && (index1 >= exponents.length || exponents[index1] > other_exponent[index2])){
                res_exp[res_index] = other_exponent[index2];
                res_coeff[res_index] = other_coefficient[index2];

                index2 += 1;
                res_index += 1;
            }
        }

        int new_index = 0;
        while (new_index < res_coeff.length){
            if(res_coeff[new_index] == 0){
                break;
            }
            new_index += 1;
        }

        double [] new_coeff = new double[new_index];
        int [] new_exp = new int[new_index];

        for(int i = 0; i < new_index; i++){
            new_coeff[i] = res_coeff[i];
            new_exp[i] = res_exp[i];
        }

        return new Polynomial(new_coeff, new_exp);
    }

    public double evaluate(double x){
        double sum = 0;
        for(int i = 0; i < exponents.length; i++){
            sum += coefficients[i] * Math.pow(x, exponents[i]);
        }

        return sum;
    }

    public Polynomial multiply(Polynomial other){
        double [] other_coeff = other.coefficients;
        int [] other_exp = other.exponents;

        if(exponents.length == 0 || other_exp.length == 0){
            return new Polynomial();
        }

        int max_len = 1 + (exponents[exponents.length - 1] + other_exp[other_exp.length - 1]);

        double [] res_coeff = new double[max_len];
        int [] res_exp = new int[max_len];

        for(int i = 0; i < exponents.length; i++){
            for(int j = 0; j < other_exp.length; j++){

                int mult_exp = exponents[i] + other_exp[j];
                double mult_coeff = coefficients[i] * other_coeff[j];

                res_exp[mult_exp] = mult_exp;
                res_coeff[mult_exp] += mult_coeff;
            }
        }

        int new_len = 0;

        for(int i = 0; i < res_coeff.length; i++){
            if (res_coeff[i] != 0){
                new_len += 1;
            }
        }

        double [] final_coeff = new double[new_len];
        int [] final_exp = new int[new_len];

        int new_index = 0;

        for(int i = 0; i < res_coeff.length; i++){
            if(res_coeff[i] != 0){
                final_coeff[new_index] = res_coeff[i];
                final_exp[new_index] = res_exp[i];
                new_index += 1;
            }
        }

        return new Polynomial(final_coeff, final_exp);

    }

    public boolean hasRoot(double x){
        if(evaluate(x) == 0){
            return true;
        }
        return false;
    }

    public void saveToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        String result = "";
        if(exponents.length == 0){
            writer.write("0.0");
        }else{
            for(int i = 0; i < coefficients.length; i++) {
                if(i != 0 && coefficients[i] > 0) {
                    result += "+";
                }
                result += Double.toString(coefficients[i]);
                if(exponents[i] != 0){
                    result += "x";
                    if(exponents[i] != 1){
                        result += Integer.toString(exponents[i]);
                    }
                }
            }

            writer.write(result);
        }

        writer.close();
    }

}