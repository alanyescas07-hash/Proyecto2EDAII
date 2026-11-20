/**
 * Representa un nodo dentro de un árbol binario de expresiones aritméticas.
 * Cada nodo almacena un valor (un operando numérico o un operador) y
 * referencias a sus hijos izquierdo y derecho.
 *
 * @author Equipo 5
 * @version 1.0
 */
public class Nodo {

    /** Valor almacenado en el nodo (operador o número en formato String). */
    String caracter;

    /** Referencia al nodo hijo derecho. */
    Nodo derecho;

    /** Referencia al nodo hijo izquierdo. */
    Nodo izquierdo;

    /**
     * Construye un nodo hoja sin hijos con el valor indicado.
     *
     * @param caracter el operando o símbolo a almacenar en el nodo
     */
    public Nodo (String caracter){
        this.caracter = caracter;
        this.derecho = null;
        this.izquierdo = null;
    }

    /**
     * Construye un nodo con un valor y sus referencias a los hijos derecho e izquierdo.
     *
     * @param caracter  el operador o símbolo a almacenar en el nodo
     * @param derecho   el nodo que se asignará como hijo derecho
     * @param izquierdo el nodo que se asignará como hijo izquierdo
     */
    public Nodo (String caracter, Nodo derecho, Nodo izquierdo){
        this.caracter = caracter;
        this.derecho = derecho;
        this.izquierdo = izquierdo;
    }

    /**
     * Obtiene el hijo derecho del nodo actual.
     *
     * @return el nodo hijo derecho, o {@code null} si no tiene
     */
    public Nodo getDerecho (){
        return this.derecho;
    }

    /**
     * Obtiene el hijo izquierdo del nodo actual.
     *
     * @return el nodo hijo izquierdo, o {@code null} si no tiene
     */
    public Nodo getIzquierdo (){
        return this.izquierdo;
    }

    /**
     * Obtiene el valor o símbolo almacenado en el nodo.
     *
     * @return el {@code String} almacenado en el nodo
     */
    public String getCaracter (){
        return this.caracter;
    }

    /**
     * Asigna o actualiza el hijo derecho del nodo.
     *
     * @param derecho el nuevo nodo hijo derecho
     */
    public void setDerecho (Nodo derecho){
        this.derecho = derecho;
    }

    /**
     * Asigna o actualiza el hijo izquierdo del nodo.
     *
     * @param izquierdo el nuevo nodo hijo izquierdo
     */
    public void setIzquierdo (Nodo izquierdo){
        this.izquierdo = izquierdo;
    }

    /**
     * Asigna o actualiza el valor almacenado en el nodo.
     *
     * @param caracter el nuevo valor o símbolo para el nodo
     */
    public void setCaracter (String caracter){
        this.caracter = caracter;
    }

    /**
     * Verifica si el nodo actual es una hoja (no tiene hijos).
     *
     * @return {@code true} si tanto el hijo derecho como el izquierdo son {@code null};
     *         {@code false} en caso contrario
     */
    public boolean esHoja (){
        if (derecho == null && izquierdo == null)
            return true;
        else
            return false;
    }

    /**
     * Imprime en consola el contenido del nodo seguido de un salto de línea.
     */
    public void imprimirNodo (){
        String caracter = this.caracter;
        System.out.println (caracter);
        System.out.println();
    }
}