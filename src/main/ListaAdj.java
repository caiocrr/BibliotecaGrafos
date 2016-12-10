/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author Caio
 */
public class ListaAdj implements  Representacao {

    public ArrayList<ArrayList<Vizinho>> listaAdj;

//    @Override
//    public ArrayList<Integer> getFilhos(int verticeTemp) {
//        ArrayList<Integer> filhos = new ArrayList<>();
//        for (int i = 0; i < listaAdj.get(verticeTemp - 1).size(); i++) {
//            filhos.add(listaAdj.get(verticeTemp - 1).get(i).vizinho);
//        }
//        return filhos;
//    }

    @Override
    public ArrayList<Vizinho> getVizinhos(int verticeTemp) {
        ArrayList<Vizinho> filhos = new ArrayList<>();
        for (int i = 0; i < listaAdj.get(verticeTemp - 1).size(); i++) {
            filhos.add(listaAdj.get(verticeTemp - 1).get(i));
        }
        return filhos;
    }
    
    @Override
     public float[][] getMatrix(){
         return new float[1][1];
     }



    public void representar(int vertices, ArrayList<Aresta> arestas) {
        listaAdj = new ArrayList();

        for (int i = 0; i < vertices; i++) {
            listaAdj.add(new ArrayList<>());
        }

        for (int m = 0; m < arestas.size(); m++) {

            listaAdj.get(arestas.get(m).vertice1 - 1).add(new Vizinho(arestas.get(m).vertice2, arestas.get(m).peso));
            listaAdj.get(arestas.get(m).vertice2 - 1).add(new Vizinho(arestas.get(m).vertice1, arestas.get(m).peso));

        }

    }




}
