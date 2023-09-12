public class Forma2 {
    int du, vec[];

    // Deben estar ordenados los coeficientes de mayor a menor.
    // du = Nterminos * 2 => vec[0]*2
    //[nTerminos,coeficiente,exponente...]

    public Forma2(int[] ceVec) {
        vec = new int[(ceVec.length+1)];
        vec[0] = ceVec.length/2;

        du = vec[0]*2; // [0] = nterminos -> [+0] co, ex

        for (int i = 0; i < ceVec.length; i++) {
            vec[i+1] = ceVec[i];
        }
    }

    //[==========( Methods )==========]

    // resize
    
    // adjust
    // coe = 0;
    public void adjust() {
        if (vec[1] == 0) {
            int i = 1;
            int j = 1;

            while ((vec[i] == 0) && (i != du)){
                i++;
                i++;
            }

            int[] tempVec = new int[du - i + 2];
            tempVec[0] = (du - i + 1)/2;

            while (i <= du) {
                tempVec[j] = vec[i];
                j++; i++;
            }

            vec = tempVec;
            du = vec[0] + 1;
        }

        
        
    }

    // addition
    public void addition(int[] vecf2) {

        int[] A; // mayor
        int[] B; // menor

        A = ((vecf2[3] > this.vec[3]) ? vecf2  : this.vec) ;
        B = ((vecf2[3] > this.vec[3]) ? this.vec : vecf2);

        int[] C = new int[A.length];
        // C[0] = A[0];

        int i = 1, j = 1, k = 1, MayorExp, MenorExp, sum = 0;
        
        while (i <= A.length-1) {
            while (j <= B.length-1) {
                if (A[i+1] == B[j+1]) {
                    C[k] = A[i] + B[j];
                    C[k+1] = i+1;
                    k++;
                    i++; i++;
                    j++; j++;
                } else if (A[i+1] >= B[j+1]) {
                    C[k] = A[i];
                    C[k+1] = A[i+1];
                    k++;
                    i++; i++;
                } else if (A[i+1] <= B[j+1]) {
                    C[k] = B[j];
                    C[k+1] = B[j+1];
                    k++;
                    j++; j++;
                }
            }
        }
        C[0] = (C.length-1)/2;
        vec = C;
        // resize();
        // adjust();
    }

    public void addition(Forma1 vecf1){
        addition(vecf1.getVec());
    }
    // substract

    // multiply

    // evaluate


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

     public void setVec(int i, int d) {
        this.vec[i] = d;
    }

}
