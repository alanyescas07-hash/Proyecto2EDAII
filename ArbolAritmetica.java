import java.util.Scanner;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementa un Árbol Binario de Expresión Aritmética capaz de leer una
 * expresión en notación infija (con enteros, decimales, negativos, operadores y
 * paréntesis/corchetes), construir el árbol respetando la jerarquía de
 * operaciones, mostrarlo por niveles, generar su recorrido en postorden
 * (Notación Polaca Inversa) y evaluarlo mediante una pila.
 *
 * @author Equipo 5
 * @version 1.0
 */

public class ArbolAritmetica {

    /** Nodo raíz del árbol de expresión aritmética. */
    Nodo raiz = null;

    /** Objeto Scanner utilizado para leer la expresión desde consola. */
    Scanner escaner = new Scanner (System.in);

    /** Expresión aritmética ingresada por el usuario. */
    String expresion;

    /** Lista de tokens generados a partir de la expresión ingresada. */
    ArrayList <String> tokens = new ArrayList<>();

    /** Pila auxiliar para almacenar operadores y símbolos de agrupación al construir el árbol. */
    Deque <String> pilaOperadores = new ArrayDeque<>();

    /** Pila auxiliar para almacenar nodos hoja y subárboles durante la construcción del árbol. */
    Deque <Nodo> pilaNodos = new ArrayDeque<>();

    /** Lista que almacena los elementos del recorrido en postorden (Notación Polaca Inversa). */
    ArrayList <String> postordenLista = new ArrayList<>();

    /** Resultado numérico obtenido al evaluar la expresión aritmética. */
    double resultado;

    /**
     * Constructor por defecto que inicializa un árbol vacío.
     */
    public ArbolAritmetica (){
        this.raiz = null;
    }

    /**
     * Solicita al usuario una expresión aritmética por consola, la divide en
     * tokens y construye el árbol binario de expresión correspondiente.
     */
    public void ingresaExpresion(){
        System.out.println ("Ingresa tu expresión");
        expresion = escaner.nextLine();

        tokens = tokenizar(expresion);
        construirArbol();
    }

    /**
     * Verifica que el árbol no esté vacío, calcula el resultado de la expresión
     * utilizando el método {@link #calculadora()} y lo imprime en consola.
     */
    public void resultado(){

        if (raiz == null){
            System.out.println("El árbol se encuentra vacío");
        } else {
            resultado = calculadora();

            System.out.println ("Resultado: " + resultado);
        }
    }

    /**
     * Realiza el recorrido recursivo en postorden (izquierdo, derecho, raíz) del árbol
     * para imprimir y almacenar la expresión en Notación Polaca Inversa.
     *
     * @param nodo el nodo actual desde el cual se realiza el recorrido
     */
    public void postorden(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        postorden(nodo.izquierdo);
        postorden(nodo.derecho);
        System.out.print(nodo.getCaracter() + " ");
        postordenLista.add(nodo.getCaracter());
    }
    
    /**
     * Convierte una expresión aritmética en formato {@code String} en una lista
     * de tokens individuales (números enteros, decimales, negativos, operadores y
     * símbolos de agrupación), ignorando espacios en blanco.
     *
     * @param expresion la cadena de texto con la expresión aritmética
     * @return una lista {@code ArrayList<String>} con los tokens separados
     */
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

    /**
     * Imprime en consola todos los tokens contenidos en una lista, uno por línea.
     *
     * @param tokens la lista de tokens a imprimir
     */
    public void imprimirTokens (ArrayList<String> tokens){

        for (int i = 0; i<tokens.size(); i++){
            System.out.println (tokens.get(i));
        }

    }

    /**
     * Devuelve el nivel de prioridad (jerarquía) de un operador o símbolo de agrupación.
     *
     * @param caracter el token del operador o símbolo a evaluar
     * @return {@code 1} para suma y resta; {@code 2} para multiplicación y división;
     *         {@code 3} para potencia; {@code 0} para paréntesis, corchetes u otros símbolos
     */
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

    /**
     * Extrae un operador de {@code pilaOperadores} y los dos nodos superiores de
     * {@code pilaNodos} (primero el hijo derecho y luego el izquierdo) para formar
     * un subárbol e insertarlo nuevamente en {@code pilaNodos}.
     */
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

    /**
     * Construye el árbol binario de expresión a partir de la lista de {@code tokens},
     * utilizando dos pilas para respetar la jerarquía de operadores y los signos de agrupación.
     */
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

    /**
     * Muestra en consola la estructura del árbol mediante un recorrido por niveles
     * usando una cola, indicando la raíz, cada nodo padre y sus hijos izquierdo y derecho.
     */
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

    /**
     * Genera la Notación Polaca Inversa mediante el recorrido en postorden del árbol
     * y evalúa la expresión utilizando una pila de resultados.
     *
     * @return el resultado numérico de la expresión, o {@code 0} si el árbol está vacío
     *         o se intenta realizar una división entre cero
     */
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