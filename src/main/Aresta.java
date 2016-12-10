package main;

/**
 * Created by Caio on 08/11/2016.
 */
public class Aresta implements Comparable {
    public int vertice1;
    public int vertice2;
    public float peso;
    public Aresta(int v1, int v2, float p){
        this.vertice1 = v1;
        this.vertice2 = v2;
        this.peso = p;
    }
    public Aresta(){

    }
    public int compareTo(Object _o) {
				Aresta e = (Aresta)_o;
				if(this.peso == e.peso) return 0;
				else if(this.peso > e.peso) return 1;
				else return -1;
    }
    
    public String toString(){
        return vertice1 + " " + vertice2 + " " + peso;
    }
}
