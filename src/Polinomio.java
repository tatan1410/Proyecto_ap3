import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polinomio {
    private Forma1 f1;
    private Forma2 f2;
    private Forma3 f3;

    // =========== Constructor ===========
    public Polinomio(String polinomio) {
        int[] ceVec = convertToCeVec(polinomio);
        f1 = new Forma1(ceVec, getMaxExp(ceVec));
        f2 = new Forma2(ceVec);
        f3 = new Forma3(ceVec);
    }
 
    // =========== Math ===========
    public void additionPolinom(Polinomio x) {
        f1.addition(x.getF1());
        f2.addition(x.getF2());
        f3.addition(x.getF3());
    }

    public void substractPolinom(Polinomio x) {
        f1.substract(x.getF1());
        f2.substract(x.getF2());
        f3.substract(x.getF3());
    }

    public void multiplyPolinom(Polinomio x) {
        f1.multiply(x.getF1());
        f2.multiply(x.getF2());
        f3.multiply(x.getF3());
    }

    // public void convertPoli(Polinomio x) {
    //     f1.convert(x.getF1());
    // }

    public boolean addTerm(int c, int e) {

        boolean s = f1.addTerms(c, e);
        f2.addTerms(c, e);
        f3.addTerms(c, e);

        return s;
    }

    public boolean deleteTerm(int e) {
        boolean s = f1.deleteTerms(e);
        f2.deleteTerms(e);
        f3.deleteTerms(e);

        return s;
    }

    // =========== Convert Methods ===========
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

    // =========== Utility ===========
    public static int getMaxExp(int[] vec) {
        
        int max = 0;
        for (int i = 1; i < vec.length; i+=2) {
            if (vec[i] > max) max = vec[i];     
        }   

        return max;
    }

    public String showNormal() {

        String s = "";
        String coe, exp, sig;

        for (int i = 2; i < f2.getVec().length; i+=2) {
            coe = ""; exp = ""; sig = "";
            if (f2.getVecPos(i-1) == 1) {
                coe = "";
            } else {
                coe = f2.getVecPos(i-1)+"";
            }

            if (f2.getVecPos(i) > 1) {
                exp = "x^"+f2.getVecPos(i);
            } else {
                exp = f2.getVecPos(i) == 1 ? "x" : "";
            }

            if (f2.getVecPos(i-1) > 0 && i != 2) {
                sig = "+";
            }

            s += sig + coe + exp;
        }

        return s;
    }
    
    // =========== Getters and Setters ===========
    public Forma1 getF1() {
        return f1;
    }
    public void setF1(Forma1 f1) {
        this.f1 = f1;
    }
    public Forma2 getF2() {
        return f2;
    }
    public void setF2(Forma2 f2) {
        this.f2 = f2;
    }
    public Forma3 getF3() {
        return f3;
    }
    public void setF3(Forma3 f3) {
        this.f3 = f3;
    }

}
