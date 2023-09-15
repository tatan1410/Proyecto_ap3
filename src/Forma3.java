public class Forma3 {
    private Node Punta = null;

    // contructor for coeficient-exponent Vectors
    public Forma3 (int[] ceVec) {
        for (int i = 0; i < ceVec.length; i+=2) {
            appendToEnd(ceVec[i], ceVec[i+1]);
        }
    }

    // constructor for tmpF3's
    public Forma3 () {
        Punta = null;
    }

    // ==================== Methods ==================

    // addTerms
    public boolean addTerms(int coe, int exp) {

        Node p = Punta;
        while (p != null) {
            if (p.getExp() == exp) return false;
            p = p.getLiga();
        }
        if (exp > Punta.getExp()) {
            appendToStart(coe, exp);
        } else appendSort(coe, exp);
        return true;
    }

    // deleteTerms

    public boolean deleteTerms(int exp) {

        Node p = Punta, y = null;
        while (p != null) {

            if (p.getExp() == exp) {
                if (p == Punta) {
                    Punta = p.getLiga();
                    p.setLiga(null);

                } else {
                    y.setLiga(p.getLiga());
                    p.setLiga(null);
                }

                return true;
            }

            y = p;
            p = p.getLiga();
        }

        return false;
    }

    // addition
    public void addition(Forma3 list) {

        Node A, B;
        Forma3 C = new Forma3();
        if (this.size() > list.size()) {
            A = Punta;
            B = list.getPunta();
        } else {
            A = list.getPunta();
            B = Punta;
        }

        while (A != null) {         
            if (B == null ){
                C.appendToEnd(A.getCoe(), A.getExp());
                A = A.getLiga();

            } else if (A.getExp() == B.getExp()) {
                if (A.getCoe() + B.getCoe() != 0) {
                    C.appendToEnd(A.getCoe() + B.getCoe(), A.getExp());
                }
                A = A.getLiga();
                B = B.getLiga();

            } else if (A.getExp() > B.getExp()) {
                C.appendToEnd(A.getCoe(), A.getExp());
                A = A.getLiga();

            } else if (A.getExp() < B.getExp()) {
                C.appendToEnd(B.getCoe(), B.getExp());
                B = B.getLiga();
            }
        }

        Punta = C.getPunta();
    }

    // substract
    public void substract(Forma3 list) {
        Forma3 tmp = new Forma3();
        Node p = list.getPunta();
        while (p != null) {
            tmp.appendToEnd(p.getCoe()*(-1), p.getExp());
            p = p.getLiga();
        }
        addition(tmp);
    }

    // multiply
    public void multiply(Forma3 list) {
        Node a = Punta, b, aux;
        Forma3 c = new Forma3();

        while (a != null) {
            
            b = list.getPunta();
            while ( b != null) {

                aux = c.getPunta();
                while (aux != null) {
                    if (aux.getExp() == a.getExp()+b.getExp()) {
                        aux.setCoe(aux.getCoe() + a.getCoe()*b.getCoe());
                        break;
                    }
                    aux = aux.getLiga();
                }
                if (aux == null) {
                    c.appendToEnd(a.getCoe()*b.getCoe(), a.getExp()+b.getExp());
                }
                
                b = b.getLiga();
            }

            a = a.getLiga();
        }
        
        Punta = c.getPunta();
        sortByLigament(false);
    }


    // =========== utility ===============
    public String appendToStart(int c, int e) { // append to the Punta of the list
        Node newNode = new Node(c,e);
        if (isEmpty()) {
            Punta = newNode;
        } else {
            newNode.setLiga(Punta);
            Punta = newNode;
        }
        return "the number "+c+e+" was added at the start";
    }

    public void appendSort(int c, int e) {
        Node p = Punta, y = p;
        Node x = new Node(c,e);

        while (p != null && p.getExp() > x.getExp()) {
            y = p;
            p = p.getLiga();
        }

        y.setLiga(p);
        x.setLiga(y);

    }

    public String appendToEnd(int c, int e) { // append to the end of the list
        Node newNode = new Node(c, e), p = Punta;

        if (isEmpty()) {
            Punta = newNode;
        } else {
            while (p.getLiga() != null) {
                p = p.getLiga();
            }
            p.setLiga(newNode);
        }
        return "the number "+c+e+" was added at the end";
    }

    public boolean isEmpty() { // return true if the list is empty
        return Punta == null ? true : false;
    }

    public int size() { //return the number of nodes of the list
        Node p = Punta;
        int counter = 0;
        while (p != null) {
            p = p.getLiga();
            counter++;
        }
        return counter;
    }

    public String showList() { // returns a string with the list
        Node p = Punta;
        String output = isEmpty() ? "Empty list" : "", next;

        while (p != null) {
            next = p.getLiga() == null ? " / " : " ] -> ";
            output += "[ " + p.getCoe() + ", " + p.getExp() + next;
            p = p.getLiga();
        }
        return output;
    }

    public String sortByLigament(boolean sortAsc) { // sort the list
        // if the list cant be sorted, exit from the method
        if (isEmpty() || size() == 1) {
            return "The list can't be sorted."; 
        }

        Node p = Punta, q, qNext=null, qPrevious = null;
        while (p != null) { // 
            // Initial values ​​for the run of q
            q = Punta;
            qPrevious = null;
            qNext = null;
            while (q != null) {
                qNext = q.getLiga();
                if (qNext != null && // if the next exits
                ((sortAsc && q.getExp() > qNext.getExp()) || // sort ascendant
                (!sortAsc && q.getExp() < qNext.getExp()) ) ) { // or sort decreasing
                    q.setLiga(qNext.getLiga()); // connect q to the node that follows the next
                    qNext.setLiga(q); // connet the next with q
                    // previous conection
                    if( qPrevious != null ) qPrevious.setLiga(qNext);
                    // Start Change
                    if (q == Punta) Punta = qNext;
                }
                
                qPrevious = q; // set qPrevios before q advances
                q = q.getLiga(); // going to next nodo
                
            }
            p = p.getLiga(); // going to next nodo
        }
        return "Sorted!";
    }
    // 
    public Node getPunta() {
        return Punta;
    }

    public void setPunta(Node punta) {
        Punta = punta;
    }

}
