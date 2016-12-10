/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Caio
 */
public class Grafo {

    public ArrayList<Integer> pai;
    public ArrayList<Integer> nivel;
    public ArrayList<Boolean> marcacao;

    public float dist[];
    
    public int[] vertexMapping;
    public int[] vertexMappingReverse;

    public int[] grauVertice;
    public int[] grau;
    public int grauMax = 0;
    
    public Representacao representacao;
    
    public Integer vertices;
    public ArrayList<Aresta> arestas;
    public boolean comPeso = false;
    public boolean pesosPositivos = true;

    public ArrayList<ArrayList<Integer>> verticesComponentesConexos;
    

    public Grafo() {
    }
    
    public void setRepresentacao(Representacao representacao){
        this.representacao = representacao;
    }
    
    public ArrayList<Integer> getFilhos(int verticeTemp){
        ArrayList<Integer> arrayTemp = new ArrayList<>();
        for (Vizinho vizinho : representacao.getVizinhos(verticeTemp)) {
                if (!marcacao.get(vizinho.vizinho - 1)) {
                    pai.set(vizinho.vizinho - 1, verticeTemp);
                    nivel.set(vizinho.vizinho - 1, nivel.get(verticeTemp - 1) + 1);
                    marcacao.set(vizinho.vizinho - 1, true);

                    arrayTemp.add(vizinho.vizinho);
                }
        }
        return arrayTemp;
    }

    public ArrayList<Vizinho> getVizinhos(int verticeTemp){
        return representacao.getVizinhos(verticeTemp);
    }


    public void startArrays(){
        pai = new ArrayList();
        nivel = new ArrayList<>();
        marcacao = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            pai.add(0);
            nivel.add(0);
            marcacao.add(false);
        }
    }

    public void gerarArquivoInfo(String path) {
        try {
            FileWriter arq = new FileWriter(path);
            PrintWriter gravarArq = new PrintWriter(arq);

            gravarArq.printf("#n = " + this.vertices + " \r\n");
            gravarArq.printf("#m = " + this.arestas.size() + "\r\n");
            gravarArq.printf("#d_media = " + 2 * (double) this.arestas.size() / (double) this.vertices + "\r\n");

            for (int i = 0; i < this.grauVertice.length; i++) {
                this.grau[this.grauVertice[i]] += 1;
            }

            for (int i = 0; i < this.grau.length; i++) {
                gravarArq.printf(i + " " + String.format(Locale.ROOT, "%.30f", (double) this.grau[i] / (double) this.vertices) + "\r\n");
            }

            arq.close();
        } catch (IOException ex) {
            Logger.getLogger(Grafo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void gerarArquivoGrafo(String path) {
        try {
            FileWriter arq = new FileWriter(path);
            PrintWriter gravarArq = new PrintWriter(arq);

            gravarArq.printf(this.vertices + " \r\n");

            for (int i = 0; i < this.arestas.size(); i++) {
                gravarArq.printf(this.arestas.get(i).vertice1 + " " + this.arestas.get(i).vertice2 + " " + this.arestas.get(i).peso + "\r\n");
            }

            arq.close();
        } catch (IOException ex) {
            Logger.getLogger(Grafo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }




    public void representacaoGrafo(int tipo) {
        //0 = lista
        //1...n = matriz
        switch (tipo) {
            case 0:
                ListaAdj ladj = new ListaAdj();
                ladj.representar(this.vertices, this.arestas);
                this.setRepresentacao(ladj);
                break;
            default:
                MatrizAdj madj = new MatrizAdj();
                madj.representar(this.vertices, this.arestas);
                this.setRepresentacao(madj);
                break;
        }




    }






    

}
