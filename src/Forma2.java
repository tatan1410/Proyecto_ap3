import java.util.Arrays;

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
    public void addition(int[] vecf2) {

        int[] A = getVec();
        int[] B = vecf2; 
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
                } else if (A[i+1] >= B[j+1]) {
                    if ((A[i] + B[j]) != 0) {
                        C[k] = A[i];
                        C[k+1] = A[i+1];
                        k += 2; i += 2;
                    } else {
                        i += 2;
                    }
                } else if (A[i+1] <= B[j+1]) {
                    if ((A[i] + B[j]) != 0) {
                        C[k] = B[j];
                        C[k+1] = B[j+1];
                        k += 2; j += 2;
                    } else {
                        j += 2;
                    }
                }
            }
        }
        C[0] = (k-1)/2; // n term
        du = (C[0]*2);
        vec = C;
        resize();
    }
    public void addition(Forma1 vecf2) {
        addition(vecf2.getVec());
    }
    
    // substract
    public void substract(int[] vecf2) {
        int[] tmp = new int[vecf2.length];
        tmp[0] = vecf2[0];
        for (int i = 1; i < vecf2.length; i++) {
            if (i % 2 == 1) tmp[i] = vecf2[i]*(-1);
            else tmp[i] = vecf2[i];
        }
        addition(tmp);
    }
    public void substract(Forma2 f2) {
        substract(f2.getVec());
    }

    // multiply
    public void multiply(int[] vecf2) {
                // x^2 * x^3 == x^5 
                int[] C = new int[(vecf2[2] + this.vec[2])*2];
                
                
                int i=2,j,k=2; 
                while (i < du/2+1) {
                    j=2;
                    while(j<vecf2.length) {
                        
                        C[k] = vecf2[j-1]+vec[i-1];
                        C[k-1] += vecf2[j]*vec[i];

                        j+=2;
                        k+=2;
                    }
                    i+=2;
                }
                
                C[0] = vecf2[2] + this.vec[2];
                int duC = C[0] + 1;
                vec = C;
                du = duC;
    }
    public void multiply(Forma2 f2) {
        multiply(f2.getVec());
    }


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
