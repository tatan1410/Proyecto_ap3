public class Forma2 {
    int du, vec[];

    // Deben estar ordenados los coheficientes de mayor a menor.
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

        vec[1] = 0;

        
        
    }

    // addition

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
