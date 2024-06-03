import java.util.Arrays;
import java.io.*;
import java.util.*;

public class Driver {
    public static void main(String [] args) throws IOException {
        double [] c1 = {1,2,1};
        double [] c2 = {3,2};

        int [] e1 = {0,1,2};
        int [] e2 = {0,1};

        Polynomial p1 = new Polynomial(c1, e1);
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial p3 = p1.multiply(p2);
        System.out.println(Arrays.toString(p3.coefficients));
        System.out.println(Arrays.toString(p3.exponents));


        File file = new File("input.txt");

        Polynomial p4 = new Polynomial(file);
        System.out.println(Arrays.toString(p4.coefficients));
        System.out.println(Arrays.toString(p4.exponents));

        p1.saveToFile("input.txt");

        System.out.println(p1.hasRoot(-2));


    }
}