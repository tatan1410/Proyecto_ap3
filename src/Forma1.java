public class Forma1 {
    private int du, vec[];

    // constructor
    public Forma1(int du) {
        this.vec = new int[du+1];
    }

    public Forma1(int[] ceVec, int max) {
        du = max + 1;
        this.vec = new int[max+2];
        vec[0] = max;

        int i = 1;
        int pos = 0;
        while (i < ceVec.length) {
            pos = du - ceVec[i];
            vec[pos] = ceVec[i-1];
            i+=2;
        }
    }

    //[==========( Methods )==========]

    // REDIMENSIONAR
    public void resize() {
        
        if (vec.length == du) return;

        du = vec[0] + 1;
        int[] tmpVec = new int[du + 1];
        tmpVec[0] = vec[0];

        int i = 1;
        while (i <= du) {
            tmpVec[i] = vec[i];
            i++;
        }

        vec = tmpVec;
        du = tmpVec[0]+1;

    }

    // AJUSTAR
    public void adjust() {
        if (vec[1] == 0) {
            int i = 2; 

            while (vec[i] == 0 && i != vec.length-1){
                i++; 
            }

            int[] tempVec = new int[du - i + 2 ];
            tempVec[0] = du - i;

            int j = 1;
            while (i <= du) {
                tempVec[j] = vec[i];
                j++; i++;
            }

            vec = tempVec;
            du = vec[0] + 1;
        }
    }

    // SUMA
    public void addition(Forma1 f1) {


        int[] A; // mayor
        int[] B; // menor
        int[] vecf1 = f1.getVec();

        A = ((vecf1[0] > this.vec[0]) ? vecf1  : this.vec) ;
        B = ((vecf1[0] > this.vec[0]) ? this.vec : vecf1);

        int[] C = new int[A.length];
        C[0] = A[0];

        int i = 1, j = 1, Me, me, sum = 0;
        
        while (i <= A.length-1) {
            sum = 0;

            Me = (A.length-1) - i; // du - pos = exp
            me = (B.length-1) - j;

            if (me == Me) {
                sum += A[i] + B[j];
                j++;
            } else {
                sum = A[i];
            }

            C[i] = sum;

            i++;
        }
        
        vec = C;
        resize();
        adjust();
    }

    // RESTA
    public void substract(Forma1 f1) {
        int[] vecf1 = f1.getVec();
        Forma1 tmp = new Forma1(vecf1.length-1);
        tmp.setVecPost(0, du = vecf1[0]);
        for (int i = 1; i < vecf1.length; i++) {
            tmp.setVecPost(i,vecf1[i]*(-1));
        }
        
        addition(tmp);
    }

    // MULTIPLICACION
    public void multiply(Forma1 f1) {

        int[] vecf1 = f1.getVec();

        // x^2 * x^3 == x^5 
        int[] C = new int[vecf1[0] + this.vec[0] + 2];
        C[0] = vecf1[0] + this.vec[0];
        
        int duC = C[0] + 1;
        int pos;

        int i=1,j=1;
        while (i <= vec[0]+1) {
            j=1;
            while(j<=vecf1[0]+1) {
                pos = duC - ((getDu() - i) + (vecf1[0]+1-j));
                C[pos] += getVecPos(i)*vecf1[j];
                j++;
            }
            i++;
        }

        vec = C;
        du = duC;
    }

    // evaluar
    public int evaluate(int x) {
        int res = 0;

        for (int i = 1; i < vec.length; i++) {
            res += vec[i]*Math.pow(x, du-i);
        }
        
       return res;
    }

    // Agregar terminos
    public void addTerms(int coe, int exp) {

        if (exp > vec[0]) {
            int[] vectorNuevo = new int[exp+2];
            int duNuevo = exp+1;

            vectorNuevo[0] = exp;
            vectorNuevo[1] = coe;

            int auxCoe, pos;
            for (int i = 1; i < vec.length; i++) {
                auxCoe = vec[i];
                pos = duNuevo - (du-i);

                vectorNuevo[pos] = auxCoe;
            }

            vec = vectorNuevo;
            du = duNuevo;

        } else {

            if (vec[du-exp] == 0) {
                vec[du-exp] += coe;
            }
        }
    }

    // Eliminar terminos

    public void deleteTerms(int exp) {
        // EXP = DU - POS
        if (exp <= vec[0]) {
        
            for (int i = 1; i < vec.length; i++) {
                if (exp == (du - i)) {
                    vec[i] = 0;
                }
            }
            adjust();
        
        } else {
            System.out.println("El término no existe en el polinomio");
        }
    }


    //[==========( Utility )==========]

    public String showVec() {
        String s = "[ "+vec[0]+" ] [ ";
        for (int i = 1; i < vec.length; i++ ) {
            s += vec[i] + (i != vec.length-1 ? ", ":"");
        }
        s += " ]";
        return s;
    }

    //[==========(getters and setters)==========]

    public int getDu() {
        return du;
    }

    public void setDu(int du) {
        this.du = du;
    }

    public int[] getVec() {
        return vec;
    }

    public int getVecPos(int i) {
        return vec[i];
    }

    public void setVec(int[] vec) {
        this.vec = vec;
    }

    public void setVecPost(int i, int d) {
        vec[i] = d;
    }
}
