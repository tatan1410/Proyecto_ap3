public class Forma2 {
    int du, vec[];

    // Deben estar ordenados los coeficientes de mayor a menor.
    // du = Nterminos * 2 => vec[0]*2
    //[nTerminos,coeficiente,exponente...]

    public Forma2(int[] ceVec) {
        vec = new int[(ceVec.length + 1)];
        vec[0] = ceVec.length/2;

        du = vec[0]*2; // [0] = nterminos -> [+0] co, ex

        for (int i = 0; i < ceVec.length; i++) {
            vec[i + 1] = ceVec[i];
        }
    }

    public Forma2(int length) {
        du = 0;
        vec = new int[length];
    }

    //[==========( Methods )==========]

    // agregar terminos

    public boolean addTerms(int coe, int exp) {
        for (int i = 1; i < vec.length; i+=2) {
            if (exp == vec[i+1]) {
                System.out.println("Este termino ya existe");
                return false;
            } 
        }

        int[] newVec = new int[vec.length + 2];
        newVec[0] = vec[0] + 1;

        newVec[newVec.length-1] = exp;
        newVec[newVec.length-2] = coe;

        for (int i = 1; i < vec.length; i++) {
            newVec[i] = vec[i];
        }

        vec = newVec;
        du+=2;
        sort();;

        return true;
    }

    // eliminar terminos
    public boolean deleteTerms(int exp) {
        for (int i = 1; i < vec.length; i+=2) {
            if (exp == vec[i+1]) {
                du = (vec[0]-1)*2;
                vec[i+1] = -1;

                int[] newVec = new int[vec.length-2];
                newVec[0] = vec[0]-1;

                int j = 2, k = 2;
                while (j <= du) {
                    if (vec[k] != -1) {
                        newVec[j] = vec[k];
                        newVec[j-1] = vec[k-1];
                        j+=2; k+=2;
                    } else {
                        k+=2;
                    }
                }

                vec = newVec;
                return true;
            } 
        }
        return false;
    }

    // resize
    public void resize() {

        du = (vec[0]*2);

        int[] tmpVec = new int[du+1];

        int i = 0;
        while (i <= du) {
            tmpVec[i] = vec[i];
            i++;
        }

        vec = tmpVec;
    }
    
    // adjust coe = 0;
    public void adjust() {
        vec[1] = 0;
        if (vec[1] == 0) { // primer coeficiente
            int i = 1;
            int j = 1;

            vec[3] = 0;

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
        resize();
    }

    // addition
    public void addition(Forma2 vecf2) {

        int[] A = getVec();
        int[] B = vecf2.getVec(); 
        int[] C = new int[A.length + B.length];

        int i = 1, j = 1, k = 1;
        
        while (i <= A.length-1) {
            while (j <= B.length-1) {
                if (A[i+1] == B[j+1]) {
                    if ((A[i] + B[j]) != 0) {
                        C[k] = A[i] + B[j];
                        C[k+1] = A[i+1]; 
                        k += 2; i += 2; j += 2;
                    } else {
                        i += 2; j += 2;
                    }
                } else if (A[i+1] > B[j+1]) {
                    C[k] = A[i];
                    C[k+1] = A[i+1];
                    k += 2; i += 2;

                } else if (A[i+1] < B[j+1]) {
                    C[k] = B[j];
                    C[k+1] = B[j+1];
                    k += 2; j += 2;
                }
            }
            if ( !(j <= B.length-1) ) {
                C[k] = A[i];
                C[k+1] = A[i+1];
                k += 2; i += 2;
            }
        }
        C[0] = (k-1)/2; // n term
        du = (C[0]*2);
        vec = C;
        resize();
    }
    
    // substract
    public void substract(Forma2 vecf2) {
        Forma2 tmp = new Forma2(vecf2.getVec().length);
        tmp.setVecPos(0, vecf2.getVecPos(0));

        for (int i = 1; i < vecf2.getVec().length; i++) {
            if (i % 2 == 1) tmp.setVecPos(i, vecf2.getVecPos(i)*(-1));
            else tmp.setVecPos(i, vecf2.getVecPos(i));
        }
        addition(tmp);
    }

    // multiply
    public void multiply(Forma2 f2) {
        // x^2 * x^3 == x^5
        int[] vecf2 = f2.getVec();
        int[] C = new int[((vecf2[0] * this.vec[0]) * 2) + 1];        
        int i = 1, j = 1, k = 1;

        while (i < this.vec.length) {
            j = 1;
            while (j < vecf2.length) {

                C[k] = this.vec[i] * vecf2[j];
                C[k+1] = vecf2[j+1] + vec[i+1];
                k += 2; j += 2;
            }
            i += 2;
        }
        
        C[0] = vecf2[0] * this.vec[0];

        String blacklist = "";

        int [] auxVec = new int[C.length];
        for (int z = 2; z < C.length; z+=2) {
            if (blacklist.contains(C[z] + ",")) {
                auxVec[z] = -1;
                auxVec[z-1] = 0;
                continue;
            }
            
            auxVec[z-1] += C[z-1];
            auxVec[z] = C[z];

            for (int y = z; y < C.length; y+=2) {
                if ((C[z] == C[y]) && (z < y)) {
                    auxVec[z-1] += C[y-1]; 
                }

            }
            
            blacklist += C[z] + ", ";
        }

        int usableData = 0;
        for (int l = 2; l<auxVec.length; l+=2) {
            if (auxVec[l] != -1) usableData++;
        }

        int[] tempVec = new int[usableData*2+1];
        int c = 2;
        for (int l = 2; l < auxVec.length; l+=2) {
            if (auxVec[l] != -1) {
                tempVec[c] = auxVec[l]; 
                tempVec[c-1] = auxVec[l-1];
                c+=2;
            }
        }

        vec = tempVec;
        vec[0] = (tempVec.length-1)/2;
        du = (tempVec.length-1);

        sort();
    }

    // evaluate
    public int evaluate(int x) {
        int res = 0;      
        int i = 2;
        while (i <= du) {
            res += getVecPos(i-1)*(Math.pow(x, getVecPos(i)));
            i+=2;
        }
        return res;
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

    public void sort() {
        // bubble sort
        int tmpCoe,tmpExp;
        for (int j = 2; j < vec.length; j+=2) {

            for (int k = 2; k < vec.length-2; k+=2) {
                if(vec[k] < vec[k+2]) {

                    tmpExp = vec[k+2]; //siguiente
                    tmpCoe = vec[k+1];

                    vec[k+2] = vec[k]; // siguiente -> actual
                    vec[k+1] = vec[k-1];

                    vec[k] = tmpExp; // actual -> siguiente
                    vec[k-1] = tmpCoe;
                }
            }
        }
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

     public void setVecPos(int i, int d) {
        this.vec[i] = d;
    }

}
