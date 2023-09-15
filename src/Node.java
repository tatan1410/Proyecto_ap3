public class Node {
    int coe, exp;
    Node liga;

    public Node(int coe, int exp) {
        this.coe = coe;
        this.exp = exp;
        this.liga = null;
    }

    public int getCoe() {
        return coe;
    }
    public void setCoe(int coe) {
        this.coe = coe;
    }
    public int getExp() {
        return exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
    public Node getLiga() {
        return liga;
    }
    public void setLiga(Node liga) {
        this.liga = liga;
    }
}
