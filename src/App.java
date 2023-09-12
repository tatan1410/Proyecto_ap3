import java.util.regex.*;
import java.util.Arrays;
//import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        // petaopeta
        String polinomio = "2x^6-32x^5+4+0x^7-4x^3+18x^2-x+x-43-2-23";  
        //String polinomio = "x+2";
        //String polinomio = "5x^7+3x^4+2x^2+8";

        String[] VecS = ingreso(polinomio);
        System.out.println("ingreso -> "+Arrays.asList(VecS));
        
        VecS = fixVec(VecS);
        System.out.println("fixed -> "+Arrays.asList(VecS));

        int[] ceVec = new int[VecS.length];

        // convert to int
        for (int i = 0; i < VecS.length; i++) {
            ceVec[i] = Integer.parseInt(VecS[i]);
        }

        // get max
        int mayor = 0;
        for (int i = 1; i < ceVec.length; i+=2) {
            if (ceVec[i] > mayor) mayor = ceVec[i];     
        }

        Forma2 f2 = new Forma2(ceVec);
        System.out.println("original -> "+ f2.showVec());
        f2.addition(ceVec);

        System.out.println("additioned -> "+ f2.showVec());

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
            
            index = i;
            sum = Integer.parseInt(vec[i-1]);

            // suma
            for (int j = 1; j <= vec.length; j+=2) {
                if (j != i && vec[i].equals(vec[j])) {   
                    sum += Integer.parseInt(vec[j-1]);
                }
            }
            
            // guardar
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

        //System.out.println(Arrays.asList(terminos));

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
                    } else coeficiente = aux[0];
                    
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
        return """
                \n ------> Mega menu!
                1.
                2.
                3.
                4.
                Opt:  """;
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
