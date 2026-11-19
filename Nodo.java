public class Nodo {
    String caracter;
    Nodo derecho;
    Nodo izquierdo;

    public Nodo (String caracter){
        this.caracter = caracter;
        this.derecho = null;
        this.izquierdo = null;
    }

    public Nodo (String caracter, Nodo derecho, Nodo izquierdo){
        this.caracter = caracter;
        this.derecho = derecho;
        this.izquierdo = izquierdo;
    }

    public Nodo getDerecho (){
        return this.derecho;
    }

    public Nodo getIzquierdo (){
        return this.izquierdo;
    }

    public String getCaracter (){
        return this.caracter;
    }

    public void setDerecho (Nodo derecho){
        this.derecho = derecho;
    }

    public void setIzquierdo (Nodo izquierdo){
        this.izquierdo = izquierdo;
    }

    public void setCaracter (String caracter){
        this.caracter = caracter;
    }

    public boolean esHoja (){
        if (derecho == null && izquierdo == null)
            return true;
        else
            return false;
    }

    public void imprimirNodo (){

        String caracter = this.caracter;
        System.out.println (caracter);
        System.out.println();

    }
}