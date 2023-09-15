import java.util.regex.*;
import java.util.Arrays;
//import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        // String polinomio = "2x^6-32x^5+4+2x^4-4x^3+18x^2+x+x-43-2-23";  
        // String polinomio2 = "x+2";
        String polinomio = "18x^3+2x-68";  
        String polinomio2 = "x^2+4x+4";
        //String polinomio2 = "5x^7+3x^4-18x^2+8";

        int[] ceVec2 = convertToCeVec(polinomio2);
        int[] ceVec = convertToCeVec(polinomio);
 
        // ========== Forma1 ==========
        Forma1 f1 = new Forma1(ceVec, getMaxExp(ceVec));
        Forma1 f12 = new Forma1(ceVec2, getMaxExp(ceVec2));

        System.out.println("F1 1 -> \t"+ f1.showVec());
        System.out.println("F1 1 -> \t"+ f12.showVec());

        f1.multiply(f12);
        System.out.println("\nResult -> \t"+ f1.showVec());


        // ========== Forma2 ==========
        Forma2 f2 = new Forma2(ceVec);
        Forma2 f22 = new Forma2(ceVec2);

        System.out.println("\nF2 1     -> \t"+ f2.showVec());
        System.out.println("F2 2     -> \t"+ f22.showVec());

        f2.multiply(f22);
        System.out.println("\nResult    = \t"+ f2.showVec());

        // System.out.println("\nevaluate    = \t"+ f2.evaluate(1));

        // ========== Forma3 ==========
        Forma3 f3 = new Forma3(ceVec);
        Forma3 f32 = new Forma3(ceVec2);

        System.out.println("\nF3 1      -> \t"+f3.showList());
        System.out.println("F3 2      -> \t"+f32.showList());

        f3.multiply(f32);
        System.out.println("\nResult    = \t"+ f3.showList());




    } 

    public static int[] convertToCeVec(String polinomio) {
        String[] StringVec = ingreso(polinomio);
        StringVec = fixVec(StringVec);

        // convert to int
        int[] integerCeVec = new int[StringVec.length];
        for (int i = 0; i < StringVec.length; i++) {
            integerCeVec[i] = Integer.parseInt(StringVec[i]);
        }

        return integerCeVec;
    }

    public static int getMaxExp(int[] vec) {
        // get max exponet
        int max = 0;
        for (int i = 1; i < vec.length; i+=2) {
            if (vec[i] > max) max = vec[i];     
        }   

        return max;
    }

    public static String[] fixVec(String[] vec) {
        int index=0, sum=0;

        String[] newVec = new String[vec.length];
        Arrays.fill(newVec, null);

        String blackList = "";

        for (int i = 1; i <= vec.length; i+=2) {    
            // continue case
            if (blackList.contains(vec[i]+",")) {
                vec[i-1] = "0";
                vec[i] = "0";
                continue;
            }  
            
            // System.out.println("vec " + Arrays.asList(vec));

            index = i;
            sum = Integer.parseInt(vec[i-1]);

            // suma
            for (int j = 1; j <= vec.length; j+=2) {
                if (j != i && vec[i].equals(vec[j])) {   
                    sum += Integer.parseInt(vec[j-1]);
                }
            }
            
            // save
            newVec[index-1] = Integer.toString( sum );
            newVec[index] = vec[index];
            blackList += vec[index]+",";

        }

        //turn null
        for (int i = 0; i < newVec.length; i+=2) {
            if (newVec[i] != null && newVec[i].equals("0")) {
                newVec[i] = null; 
                newVec[i+1] = null;
            }
        }

        // remove the nulls
        int usableData = 0;
        for (String e : newVec) {
            if (e != null) usableData++;
        }

        String[] tempVec = new String[usableData];
        int i = 0;
        for (String e : newVec) {
            if (e != null) {tempVec[i] = e; i++;}
        }
        newVec = tempVec;

        // bubble sort
        String tmpCoe,tmpExp;
        for (int j = 1; j < newVec.length; j+=2) {

            for (int k = 1; k < newVec.length-2; k+=2) {
                if(Integer.parseInt(newVec[k]) < Integer.parseInt(newVec[k+2])) {

                    tmpExp = newVec[k+2]; //siguiente
                    tmpCoe = newVec[k+1];

                    newVec[k+2] = newVec[k]; // siguiente -> actual
                    newVec[k+1] = newVec[k-1];

                    newVec[k] = tmpExp; // actual -> siguiente
                    newVec[k-1] = tmpCoe;
                }
            }
        }

        return newVec;
    }

    public static String[] ingreso(String polinomio) {

        //System.out.println("\n[====================( MYCODE )====================]\n");

        int termCount;

        // menos al inicio
        if( polinomio.charAt(0) == '-') {
            termCount = polinomio.split("[+|-]").length-1;
        } else termCount = polinomio.split("[+|-]").length;

        String[] terminos = new String[termCount];

        Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
        Matcher matcher = pattern.matcher(polinomio);

        int a=0;
        while (matcher.find()) {
            terminos[a]=matcher.group(1);
            a++;
        }

        // System.out.println(Arrays.asList(terminos));

        String coeficiente = "", exponente = "";
        String[] aux;
        String[][] auxMatriz = new String[terminos.length][2];
        String[] finalVec = new String[terminos.length*2];

        for (int i = 0; i < terminos.length; i++) {

            if (terminos[i] == null) continue;

            if (terminos[i].contains("x") ) { // si el termino tiene variable
                if (terminos[i].contains("x^")) { // si esta elevada
                    aux = terminos[i].split("x\\^");

                    if (aux[0] == "+" || aux[0] == "-" ) {
                        coeficiente = aux[0] == "-" ? "1":"-1";
                    } else {
                        coeficiente = aux[0].equals("") ? "1" : aux[0];
                    }
                    
                    exponente = aux[1];
                } else { // no esta elevada
                    aux = terminos[i].split("x");
                    if (aux.length != 0) { // tiene coeficiente
                        
                        if (aux[0].equals("-") || aux[0].equals("+")) {
                            coeficiente = aux[0].equals("+") ? "1": "-1";
                        }
                        else coeficiente = aux[0]; // el coeficiente es un digito
                    } else coeficiente = "1"; // no tiene coeficiente
                    exponente = "1";
                }
            } else {
                coeficiente  = terminos[i];
                exponente = "0";
            }
            

            coeficiente = coeficiente.replace("+", "");
            auxMatriz[i][0] = coeficiente;
            auxMatriz[i][1] = exponente;
        }

        for (int i = 0; i < finalVec.length; i+=2) {
            finalVec[i] = auxMatriz[i/2][0];
            finalVec[i+1] = auxMatriz[i/2][1];

        }

        return finalVec;
    }

    public static String menu() {
        return "";
        // return """
        //         \n ------> Mega menu!
        //         1.
        //         2.
        //         3.
        //         4.
        //         Opt:  """;
    }
}

/*
 // Clase
        System.out.println("\n[====================( CODIGO DE CLASE )====================]\n");
        char Vc[] = polinomio.toCharArray();
        String Vs[] = new String[Vc.length];
        int j = 0;
        boolean isConstant = false;
        Arrays.fill(Vs, "");

        char skipChar[] = {'+','-','x',};
        for (int i = 0; i < Vc.length; i++) {
             if (i>0 && Vc[i-1] == 'x' && Vc[i] != '^' ) {
            }

            // skip characters
            for (char c : skipChar) { 
                if (i != 0 && c == Vc[i]) {
                    j++;
                    
                } 
            }

            // add digit
            if (Character.isDigit(Vc[i]) || Vc[i] == '-') {
                Vs[j] += Character.toString(Vc[i]);
            }

        }

        for (String string : Vs) {
            System.out.print("["+string+"]");
        }
        System.out.println("\n"); 
  
 
 */
