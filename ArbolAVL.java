import java.util.LinkedList;
import java.util.Queue;

/*
 * Árbol AVL.
 */
public class ArbolAVL {
    /* Nodo raíz. */
    public Nodo raiz;

    /*
     * Constructor por defecto.
     */
    public ArbolAVL() {
        this.raiz = null;
    }

    /*
     * Inserta un nuevo valor en el árbol y actualiza el balanceo si es necesario.
     *
     * valor es el número entero a insertar.
     */
    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

    /*
     * Método recursivo auxiliar para insertar un valor y rebalancear el camino de regreso.
     *
     * nodo  nodo actual en el recorrido.
     * valor valor a insertar.
     * raíz del subárbol ya balanceado.
     */
    private Nodo insertarRec(Nodo nodo, int valor) {
        // Encontró la posición vacía para el nuevo nodo
        if (nodo == null) {
            return new Nodo(valor);
        }

        // Búsqueda de la posición según la propiedad de árbol binario de búsqueda
        if (valor < nodo.valor) {
            nodo.hIzquierdo = insertarRec(nodo.hIzquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.hDerecho = insertarRec(nodo.hDerecho, valor);
        } else {
            // Se retorna sin insertar ni rebalancear
            return nodo;
        }

        // Al volver de la recursión, se verifica y corrige el balanceo de nodo padre
        return CondicionBalanceo(nodo);
    }

    /*
     * Busca un valor específico dentro del árbol.
     *
     * valor es valor a buscar.
     */
    public Nodo buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    /*
     * Método recursivo auxiliar para buscar un nodo por su valor.
     *
     * nodo  es el nodo actual en la búsqueda.
     * valor valor buscado.
     */
    private Nodo buscarRec(Nodo nodo, int valor) {
        // nodo nulo o un valor encontrada ya en el arbol
        if (nodo == null || nodo.valor == valor) {
            return nodo;
        }

        if (valor < nodo.valor) {
            return buscarRec(nodo.hIzquierdo, valor);
        } else {
            return buscarRec(nodo.hDerecho, valor);
        }
    }

    /*
     * Elimina un valor del árbol y mantiene el equilibrio AVL.
     *
     * valor es el valor a eliminar.
     */
    public void eliminar(int valor) {
        raiz = eliminarRec(raiz, valor);
    }

    /*
     * Método recursivo auxiliar para eliminar un nodo y rebalancear el subárbol resultante.
     *
     * nodo  es el actual en el recorrido.
     * valor es el valor a eliminar.
     */
    private Nodo eliminarRec(Nodo nodo, int valor) {
        // Caso base, el valor no se encuentra en el árbol
        if (nodo == null) {
            return null;
        }

        // Buscar el nodo por valor
        if (valor < nodo.valor) {
            nodo.hIzquierdo = eliminarRec(nodo.hIzquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.hDerecho = eliminarRec(nodo.hDerecho, valor);
        } else {
            // Caso 1 y 2: nodo sin hijos o con un solo hijo
            if (nodo.hIzquierdo == null) {
                return nodo.hDerecho;
            } else if (nodo.hDerecho == null) {
                return nodo.hIzquierdo;
            }

            // Caso 3: nodo con dos hijos
            // Se sustituye por el nodo de menor valor del subárbol derecho
            nodo.valor = valorMinimo(nodo.hDerecho);
            // Se elimina el padre duplicado del subárbol derecho
            nodo.hDerecho = eliminarRec(nodo.hDerecho, nodo.valor);
        }

        // Rebalancear el nodo actual tras la eliminación
        return CondicionBalanceo(nodo);
    }

    /*
     * Encuentra el valor mínimo dentro de un subárbol.
     *
     * nodo raíz del subárbol.
     */
    private int valorMinimo(Nodo nodo) {
        while (nodo.hIzquierdo != null) {
            nodo = nodo.hIzquierdo;
        }
        return nodo.valor;
    }

    /*
     * Calcula la altura de un nodo o rama específica.
     *
     * nodo es el nodo desde el cual se calcula la altura.
     * 
     */
    public int alturaEnRama(Nodo nodo) {
        return calculoAltura(nodo);
    }

    /*
     * Método recursivo auxiliar para obtener la altura de un subárbol.
     *
     * nodo representa el nodo actual.
     */
    private int calculoAltura(Nodo nodo) {
        // En caso de ser un nodo nulo tiene altura -1
        if (nodo == null) {
            return -1;
        }

        // Un nodo hoja altura 0
        if (nodo.hIzquierdo == null && nodo.hDerecho == null) {
            return 0;
        }
        
        // Metodos recursivos para calcular las alturas de los nodos
        int alturaIzq = calculoAltura(nodo.hIzquierdo);
        int alturaDer = calculoAltura(nodo.hDerecho);

        return Math.max(alturaIzq, alturaDer) + 1;
    }

    /*
     * Calcula el factor de equilibrio de un nodo.
     * Valores AVL: -1, 0 y 1.
     *
     * nodo nodo a evaluar.
     */
    private int factorEquilibrio(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return alturaEnRama(nodo.hDerecho) - alturaEnRama(nodo.hIzquierdo);
    }

    /*
     * Verifica si un nodo cumple con la condición de equilibrio AVL y lo balancea si es necesario.
     *
     * nodo nodo a evaluar su balanceo.
     */
    public Nodo CondicionBalanceo(Nodo nodo) {
        if (nodo == null) {
            return null;
        }

        int condicion = factorEquilibrio(nodo);

        // Si el factor de equilibrio no se encuentra entre [-1, 1], se aplican rotaciones
        if (condicion > 1 || condicion < -1) {
            return balanceo(nodo, condicion);
        }

        return nodo;
    }

    /*
     * Determina y ejecuta el tipo de rotación simple o doble necesaria para rebalancear un nodo.
     *
     * nodo      nodo desbalanceado.
     * condicion factor de equilibrio del nodo, para identificar el tipo de rotacion aplicada.
     */
    private Nodo balanceo(Nodo nodo, int condicion) {
        if (condicion > 1) {
            // Desbalanceado hacia la derecha
            // Si el hijo derecho está inclinado a la izquierda caso rotecion doble Derecha-Izquierda
            if (factorEquilibrio(nodo.hDerecho) < 0) {
                nodo.hDerecho = rotacionDerecha(nodo.hDerecho);
            }
            // Caso continuación Derecha-Izquierda o Rotacion simple Izquierda 
            return rotacionIzquierda(nodo);
        } else {
            // Desbalanceado hacia la izquierda
            // Si el hijo izquierdo está inclinado a la derecha caso rotacion doble Izquierda-Derecha
            if (factorEquilibrio(nodo.hIzquierdo) > 0) {
                nodo.hIzquierdo = rotacionIzquierda(nodo.hIzquierdo);
            }
            // Caso continuación Izquierda-Derecha o Rotacion simple Derecha
            return rotacionDerecha(nodo);
        }
    }

    /*
     * Realiza una rotación simple hacia la derecha sobre el subárbol dado.
     *
     * nodo1 Nodo raíz actual del subárbol desbalanceado.
     */
    private Nodo rotacionDerecha(Nodo nodo1) {
        // Guardar los nodos necesarios para la rotacion
        Nodo nodo2 = nodo1.hIzquierdo;
        Nodo B = nodo2.hDerecho;

        // Realizar rotación
        nodo2.hDerecho = nodo1;
        nodo1.hIzquierdo = B;

        return nodo2;
    }

    /*
     * Realiza una rotación simple hacia la izquierda sobre el subárbol dado.
     *
     * nodo1 Nodo raíz actual del subárbol desbalanceado.
     */
    private Nodo rotacionIzquierda(Nodo nodo1) {
        //Guardar los nodos necesarios para la rotacion
        Nodo nodo2 = nodo1.hDerecho;
        Nodo B = nodo2.hIzquierdo; 

        // Realizar rotación
        nodo2.hIzquierdo = nodo1;
        nodo1.hDerecho = B;

        return nodo2;
    }

    /*
     * Imprime el árbol nivel por nivel , mostrando cada nodo junto con sus hijos izquierdo y derecho.
     */
    public void imprimirArbol() {
        if (raiz == null) {
            System.out.println("El arbol esta vacio.");
            return;
        }

        // Cola para el recorrido por niveles
        Queue<Nodo> queue = new LinkedList<>();
        queue.add(raiz);
        int nivel = 0;

        while (!queue.isEmpty()) {
            int nivelTamanio = queue.size();
            System.out.println("Nivel " + nivel + ":");

            // Procesa todos los nodos del nivel actual
            while (nivelTamanio > 0) {
                Nodo actual = queue.poll();
                System.out.print("[" + actual.valor + "] ");

                if (actual.hIzquierdo != null) {
                    queue.add(actual.hIzquierdo);
                    System.out.print("(Izq: " + actual.hIzquierdo.valor + ") ");
                }
                if (actual.hDerecho != null) {
                    queue.add(actual.hDerecho);
                    System.out.print("(Der: " + actual.hDerecho.valor + ") ");
                }

                System.out.print(" | ");
                nivelTamanio--;
            }

            System.out.println("\n");
            nivel++;
        }
    }
}