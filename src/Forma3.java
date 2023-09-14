public class Forma3 {
    // Deben estar ordenados los coheficientes de mayor a menor.
    // Lista simplemente ligada.
    // [Coeficiente|Exponente|Liga] -> [c|e|l]

    private Node Punta;

    public Forma3 (int[] ceVec) {
        for (int i = 0; i < ceVec.length; i+=2) {
            appendToEnd(ceVec[i], ceVec[i+1]);
        }
    }

    public String appendToStart(int c, int e) { // append to the Punta of the list
        Node newNode = new Node(c,e);
        if (isEmpty()) {
            Punta = newNode;
        } else {
            newNode.setLiga(Punta);
            Punta = newNode;
        }
        return "the number "+c+e+" was added at the Punta";
    }


    public void appendSort(int c, int e) {
        Node p = Punta, y = null;
        Node x = new Node(c,e);

        while (p != null && p.getExp() < x.getExp()) {
            y = p;
            p = p.getLiga();
        }

        y.setLiga(x);
        x.setLiga(p);

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
        while (p != Punta) {
            p = p.getLiga();
            counter++;
        }
        return counter;
    }

    public String showList() { // returns a string with the list
        Node p = Punta;
        String output = isEmpty() ? "Empty list" : "", next;

        while (p != null) {
            // output += " | " + p.getLigaData() + p.getLigaLiga() == null ? " / " : " | -> ";
            next = p.getLiga() == null ? " / " : " | -> ";
            output += " | " + p.getCoe() + ", " + p.getExp() + next;
            p = p.getLiga();
        }
        return output;
    }


}
