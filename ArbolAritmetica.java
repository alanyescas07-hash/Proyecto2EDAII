import java.util.Scanner;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class ArbolAritmetica {

    Nodo raiz = null;
    Scanner escaner = new Scanner (System.in);
    String expresion;
    ArrayList <String> tokens = new ArrayList<>();
    Deque <String> pilaOperadores = new ArrayDeque<>();
    Deque <Nodo> pilaNodos = new ArrayDeque<>();
    ArrayList <String> postordenLista = new ArrayList<>();
    double resultado;

    public ArbolAritmetica (){
        this.raiz = null;
    }

    public void ingresaExpresion(){
        System.out.println ("Ingresa tu expresión");
        expresion = escaner.nextLine();

        tokens = tokenizar(expresion);
        construirArbol();
    }

    public void resultado(){

        if (raiz == null){
            System.out.println("El árbol se encuentra vacío");
        } else {
            resultado = calculadora();

            System.out.println ("Resultado: " + resultado);
        }
    }

    public void postorden(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        postorden(nodo.izquierdo);
        postorden(nodo.derecho);
        System.out.print(nodo.getCaracter() + " ");
        postordenLista.add(nodo.getCaracter());
    }
    
    public ArrayList<String> tokenizar (String expresion){

        ArrayList <String> tokensAux = new ArrayList<>();
        String aux = "";

        for (int i = 0 ; i<expresion.length(); i++ ){

            char caracter = expresion.charAt(i);
            String ultimo;

            if (Character.isDigit(caracter) || caracter == '.'){
                aux = aux + caracter;
            }
            else {

                if (!aux.isEmpty()){
                    tokensAux.add(aux);
                    aux = "";
                }

                if (caracter == ' ')
                    continue;

                if (caracter == '-'){

                    if (tokensAux.isEmpty()){
                        aux = "-";

                    }else if (!tokensAux.isEmpty()){
                        ultimo = tokensAux.get(tokensAux.size()-1);

                        if ( ultimo.equals("-") || ultimo.equals("^") || ultimo.equals("*") || ultimo.equals("+") ||  ultimo.equals("/") ||  ultimo.equals("(") ||  ultimo.equals("[")){
                            aux = "-";

                        }else{
                            tokensAux.add("-");
                        }
                    }
                }  else if (caracter == '+' || caracter == '*' || caracter == '/' || caracter == '^' || caracter == '(' || caracter == ')' || caracter == '[' || caracter == ']') {
                    
                    tokensAux.add(String.valueOf(caracter));
                }              
            }
        }

        if (!aux.isEmpty()){
            tokensAux.add(aux);
            aux = "";
        }

        return tokensAux;
    }

    public void imprimirTokens (ArrayList<String> tokens){

        for (int i = 0; i<tokens.size(); i++){
            System.out.println (tokens.get(i));
        }

    }

    public int jerarquia (String caracter){

        if (caracter.equals("-") || caracter.equals("+")){
            return 1;

        } else if (caracter.equals("*") || caracter.equals("/")){
            return 2;

        } else if (caracter.equals("^")){
            return 3;

        } else if (caracter.equals("(") || caracter.equals(")") || caracter.equals("[") || caracter.equals("]")){
            return 0;
        }

        return 0;
    }

    public void crearSubarbol (){
        Nodo derecho;
        Nodo izquierdo;
        String caracter;

        caracter = pilaOperadores.pop();
        derecho = pilaNodos.pop();
        izquierdo = pilaNodos.pop();

        Nodo nuevo = new Nodo (caracter, derecho, izquierdo);

        pilaNodos.push(nuevo);
    }

    public void construirArbol (){

        pilaNodos.clear();
        pilaOperadores.clear();

        for (int i = 0; i < tokens.size(); i++){

            String caracter = tokens.get(i);

            if (caracter.equals("(") || caracter.equals("[")){
                pilaOperadores.push(caracter);
            } else if (caracter.equals (")")){

                while (!pilaOperadores.isEmpty() && !pilaOperadores.peek().equals(("("))){
                    crearSubarbol();
                }

                pilaOperadores.pop();

            } else if (caracter.equals ("]")){

                while (!pilaOperadores.isEmpty() && !pilaOperadores.peek().equals(("["))){
                    crearSubarbol();
                }

                pilaOperadores.pop();

            }else if(caracter.equals("+") || caracter.equals("-") || caracter.equals("*") || caracter.equals("/") || caracter.equals("^")){

                while (!pilaOperadores.isEmpty() && jerarquia(pilaOperadores.peek()) >= jerarquia(caracter)){
                    crearSubarbol();
                }

                pilaOperadores.push(caracter);

            }else {

                pilaNodos.push(new Nodo (caracter));

            }
        }

        while (!pilaOperadores.isEmpty()){
            crearSubarbol();
        }

        if (!pilaNodos.isEmpty()){
            raiz = pilaNodos.pop();
        }
    }

    public void mostrarArbol(){

        if(raiz==null){
            System.out.println("Tu árbol no ha sido creado");
            return;
        }

        Queue<Nodo> nodos = new LinkedList<>();

        nodos.add(raiz);

        System.out.println ("Raíz del árbol");
        raiz.imprimirNodo();

        while( !nodos.isEmpty() ){
            
            Nodo aux = nodos.poll();
            Nodo izquierdo = aux.getIzquierdo();
            Nodo derecho = aux.getDerecho();

            if (aux == raiz || !aux.esHoja()){

                System.out.print("\nNodo Padre: ");
                System.out.println(aux.getCaracter());
            }

            if(izquierdo != null){
                System.out.print("\nNodo Izquierdo: ");
                izquierdo.imprimirNodo();
                nodos.add(izquierdo);
            }

            if (derecho != null){
                System.out.print("\nNodo Derecho: ");
                derecho.imprimirNodo();
                nodos.add(derecho);
            }
            System.out.print("\n\t\t");

        }
        System.out.println("\n");
    }

    public double calculadora() {

        if (raiz == null){
            System.out.println ("No has creado un arbol aún. :(");
            return 0;
        }

        postordenLista.clear();
        postorden(raiz);
        System.out.println();

        Deque <Double> pilaResultados = new ArrayDeque<>();
        double derecho;
        double izquierdo;

        for (String elemento : postordenLista){
            if (elemento.equals ("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/") || elemento.equals("^")){
                derecho = pilaResultados.pop();
                izquierdo = pilaResultados.pop();
                
                if (elemento.equals ("+")){
                    pilaResultados.push( izquierdo + derecho);
                }

                if (elemento.equals ("-")){
                    pilaResultados.push( izquierdo - derecho);
                }

                if (elemento.equals ("*")){
                    pilaResultados.push( izquierdo * derecho);
                }

                if (elemento.equals ("/")){

                    if (derecho == 0){
                        System.out.println ("No se puede efectuar una división entre 0");
                        return 0;
                    } else {
                        pilaResultados.push( izquierdo / derecho);
                    }
                }

                if (elemento.equals ("^")){
                    pilaResultados.push(Math.pow(izquierdo, derecho));
                }

            } else {

                pilaResultados.push (Double.parseDouble(elemento));
            
            }
        }

        return pilaResultados.pop();

    }

}