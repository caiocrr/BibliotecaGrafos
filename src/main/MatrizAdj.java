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
public class MatrizAdj implements  Representacao {

    private float[][] matrizAdj;

//    @Override
//    public ArrayList<Integer> getFilhos(int verticeTemp) {
//        ArrayList<Integer> filhos = new ArrayList<>();
//        for (int i = 0; i < matrizAdj[verticeTemp - 1].length; i++) {
//            if (matrizAdj[verticeTemp - 1][i] > 0) {
//                filhos.add(i+1);
//            }
//        }
//        return filhos;
//    }

    @Override
    public ArrayList<Vizinho> getVizinhos(int verticeTemp) {
        ArrayList<Vizinho> filhos = new ArrayList<>();
        for (int i = 0; i < matrizAdj[verticeTemp - 1].length; i++) {
            if (matrizAdj[verticeTemp - 1][i] > 0) {
                Vizinho tmp = new Vizinho(i+1, matrizAdj[verticeTemp - 1][i]);
                filhos.add(tmp);
            }
        }
        return filhos;
    }
    
    @Override
     public float[][] getMatrix(){
         return matrizAdj;
     }


    public void representar(int vertices, ArrayList<Aresta> arestas) {

        matrizAdj = new float[vertices][vertices];

        //comparar tempo com operação normal x funcional
        arestas.stream()
                .map((trio) -> {
                    return trio;
                })
                .forEach((trio) -> {
                    matrizAdj[trio.vertice1 - 1][trio.vertice2 - 1] = trio.peso;
                    matrizAdj[trio.vertice2 - 1][trio.vertice1 - 1] = trio.peso;
                });
    }

}
