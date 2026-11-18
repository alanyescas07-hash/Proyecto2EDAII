import java.util.LinkedList;
import java.util.Queue;

public class ArbolAVL {
    public Nodo raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
        CondicionBalanceo(raiz);
    }

    private Nodo insertarRec(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }

        if (valor < nodo.valor) {
            nodo.hIzquierdo = insertarRec(nodo.hIzquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.hDerecho = insertarRec(nodo.hDerecho, valor);
        } 

        return nodo;
    }

    public Nodo buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    private Nodo buscarRec(Nodo nodo, int valor) {
        if (nodo == null || nodo.valor == valor) {
            return nodo;
        }

        if (valor < nodo.valor) {
            return buscarRec(nodo.hIzquierdo, valor);
        } else {
            return buscarRec(nodo.hDerecho, valor);
        }
    }

    public void eliminar(int valor) {
        raiz = eliminarRec(raiz, valor);
        CondicionBalanceo(raiz);
    }

    private Nodo eliminarRec(Nodo nodo, int valor) {
        if (nodo == null) {
            return null;
        }

        if (valor < nodo.valor) {
            nodo.hIzquierdo = eliminarRec(nodo.hIzquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.hDerecho = eliminarRec(nodo.hDerecho, valor);
        } else {
            if (nodo.hIzquierdo == null) {
                return nodo.hDerecho;
            } else if (nodo.hDerecho == null) {
                return nodo.hIzquierdo;
            }

            nodo.valor = valorMinimo(nodo.hDerecho);
            nodo.hDerecho = eliminarRec(nodo.hDerecho, nodo.valor);
        }
        return nodo;
    }

    private int valorMinimo(Nodo nodo) {
        while (nodo.hIzquierdo != null) {
            nodo = nodo.hIzquierdo;
        }
        return nodo.valor;
    }

    public int alturaEnRama(Nodo nodo){
        int altura = calculoAltura(nodo) ;
        return altura;
    }

    private int calculoAltura (Nodo nodo){

        if (nodo == null) {
            return -1;
        }

        if (nodo.hIzquierdo == null && nodo.hDerecho == null) {
            return 0;
        }

        int alturaIzq = calculoAltura(nodo.hIzquierdo);
        int alturaDer = calculoAltura(nodo.hDerecho);

        return Math.max(alturaIzq, alturaDer) + 1;

    }

    public Nodo CondicionBalanceo(Nodo nodo){
        int condicion;

        condicion = alturaEnRama(nodo.hDerecho) - alturaEnRama(nodo.hIzquierdo);

        if(condicion == 0 || condicion == 1 || condicion == -1){
            System.out.println("El arbol ya se encuentra balanceado.\n");
        }else{
            balanceo(nodo, condicion);
        }

        return nodo;
    }

    private void balanceo(Nodo raiz, int condicion){
        if(condicion > 0){

        }else{
            
        }
    }

    public void imprimirArbol() {
        if (raiz == null) {
            System.out.println("El arbol esta vacio.");
            return;
        }

        Queue<Nodo> queue = new LinkedList<>();
        queue.add(raiz);
        int nivel = 0;

        while (!queue.isEmpty()) {
            int nivelTamanio = queue.size();
            System.out.println("Nivel " + nivel + ":");

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
