import java.util.ArrayList;
import java.util.Scanner;

public class App {

    static Scanner read = new Scanner(System.in);
    static ArrayList<Polinomio> polinomios = new ArrayList<Polinomio>();
    static int p = 0;
    public static void main(String[] args) throws Exception {

        String[] polinomiosPrueba = {
            "-4x^6-32x^5+4+2x^4-4x^3+18x^2+x+x-43-2-23",
            "5x^7+3x^4-18x^2+8",
            "18x^3+2x-68",
            "x^2+4x+4",
            "x+2",
            "x"
        };

        int opt = 0;
        int mathOpt;
        int auxP;
        int coe = 0, exp = 0;

        for (String pol : polinomiosPrueba) {
            polinomios.add(new Polinomio(pol));
        }

        while (true) {
            
            System.out.println( "\n\n" + menu() );
            opt = read.nextInt();

            switch (opt) {

                case 1: // ingresar polinomios

                    try {
                        String nuevoPolinomio = read.next();
                        Polinomio add = new Polinomio(nuevoPolinomio);
                        polinomios.add(add);
                        System.out.println("Polinomio añadido.");

                    } catch (Exception e) {
                        System.out.println("Polinomio invalido.");
                    }

                    break;
                
                case 2: // Mostrar todas las formas
                    System.out.println(
                        "Polinomio Forma 1: \n\t" + polinomios.get(p).getF1().showVec() +
                        "\nPolinomio Forma 2: \n\t" + polinomios.get(p).getF2().showVec() +
                        "\nPolinomio Forma 3: \n\t" + polinomios.get(p).getF3().showList()
                    );

                    break;

                case 3: // mostrar normal
                    System.out.println("Polinomio en forma normal -> "+polinomios.get(p).showNormal());

                    break;

                case 4:
                    System.out.println( "\n" + operaciones() );
                    mathOpt = read.nextInt();

                    for (int i = 0; i < polinomios.size(); i++) {
                        if ( i != p) {
                            System.out.println((i+1)+". "+polinomios.get(i).showNormal() );
                        }
                    } 

                    if (mathOpt == 1) {
                        System.out.print("Seleccione el polinomio con el cual lo desea sumar: ");
                        auxP = read.nextInt()-1;
                        polinomios.get(p).additionPolinom( polinomios.get(auxP) ); // f1 a f3

                    } else if (mathOpt == 2) {
                        System.out.print("Seleccione el polinomio con el cual lo desea restar: ");
                        auxP = read.nextInt()-1;
                        polinomios.get(p).substractPolinom( polinomios.get(auxP) );

                    } else if (mathOpt == 3) {
                        System.out.print("Seleccione el polinomio con el cual lo desea multiplicar: ");
                        auxP = read.nextInt()-1;
                        polinomios.get(p).multiplyPolinom( polinomios.get(auxP) );

                    }

                    System.out.println("Los polinomios han quedado de la siguiente manera: ");
                    System.out.println(
                        "Polinomio Forma 1: \n\t" + polinomios.get(p).getF1().showVec() +
                        "\nPolinomio Forma 2: \n\t" + polinomios.get(p).getF2().showVec() +
                        "\nPolinomio Forma 3: \n\t" + polinomios.get(p).getF3().showList()
                    );
                    break;

                case 5: // insertar
                    
                    System.out.print("Ingrese el coeficiente del nuevo termino: ");
                    coe = read.nextInt();

                    System.out.print("Ingrese el exponente del nuevo termino: ");
                    exp = read.nextInt();
                    
                    if ( polinomios.get(p).addTerm(coe, exp) ) {
                        System.out.println("El termino se ha añadido correctamente!");
                    } else {
                        System.out.println("No se ha podido agregar el termino.");
                    }

                    break;

                case 6:
                    System.out.print("Ingrese el exponente del termino que se desea eliminar: ");
                    exp = read.nextInt();
                    
                    if ( polinomios.get(p).deleteTerm(exp) ) {
                        System.out.println("El termino se ha eliminado correctamente!");
                    } else {
                        System.out.println("No se ha podido eliminar el termino.");
                    }


                    break;

               case 7:
                    for (int i = 0; i < polinomios.size(); i++) {
                        System.out.println((i+1)+". "+polinomios.get(i).showNormal() );
                    } 
                    System.out.print("Seleccionar vector a usar: ");
                    p = read.nextInt() - 1;


                    break;

                // case 8:
                //     polinomios.get(p).con.additionPolinom( polinomios.get(auxP) ); // f1 a f3


                case 0: // exit
                    read.close();
                    System.exit(0);
                    break;
            
                default:
                    break;
            }

        }

    } 

    public static String menu() {
        return (
            "Menu de polinomios." +
            state() +
            "\n1. Ingresar polinomio." +
            "\n2. Mostrar polinomio en todas sus formas." +
            "\n3. Mostrar polinomio en forma normal." +
            "\n4. Operaciones Matematicas entre polinomios." +
            "\n5. Insertar al polinomio" +
            "\n6. Eliminar del polinomio" +
            "\n7. Seleccionar polinomio." +
            "\n0. Salir." +
            "\nOpcion: " 
        );
    }

    public static String operaciones() {
        return (
            "Operaciones con polinomios" +
            state() +
            "\n1. Sumar con un polinomio." +
            "\n2. Restar un polinomio." +
            "\n3. Multiplicar." +
            "\n0. Salir." +
            "\nOpcion: "
        );
    }

    public static String state() {
        return 
        "\nPolinomio Selecionado: " + (p + 1) +
        "\nNumero de polinomios: " + polinomios.size();
    }
}