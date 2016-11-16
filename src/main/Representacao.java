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
interface Representacao {

//    public ArrayList<Integer> getFilhos(int vertice) {
//        return new ArrayList<>();
//    }

    public ArrayList<Vizinho> getVizinhos(int vertice);


}
