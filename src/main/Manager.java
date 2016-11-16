/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Caio
 */
public class Manager {


    private ArrayList<Integer> verticesArvore;
    private LinkedList stack;

    public Grafo carregarArquivo(String path) {
        Grafo grafo = new Grafo();

        Scanner in;
        try {
            in = new Scanner(new FileReader(path));
            Boolean firstline = false;
            grafo.arestas = new ArrayList();

            while (in.hasNextLine()) {
                if (!firstline) {
                    grafo.vertices = Integer.parseInt(in.nextLine().split(" ")[0]);
                    firstline = true;

                    grafo.grauVertice = new int[grafo.vertices];
                } else {
                    String[] temp = in.nextLine().split(" ");

                    Aresta aresta = new Aresta();

                    aresta.vertice1 = Integer.parseInt(temp[0]);
                    aresta.vertice2 = Integer.parseInt(temp[1]);
                    if (temp.length == 2) {
                        aresta.peso = 1f;
                    } else {
                        aresta.peso = Float.parseFloat(temp[2]);
                        if (aresta.peso < 0) {
                            grafo.pesosPositivos = false;
                        }
                        grafo.comPeso = true;
                    }


                    grafo.grauVertice[aresta.vertice1 - 1] += 1;
                    grafo.grauVertice[aresta.vertice2 - 1] += 1;

                    if (grafo.grauVertice[aresta.vertice1 - 1] > grafo.grauMax) {
                        grafo.grauMax = grafo.grauVertice[aresta.vertice1 - 1];
                    }

                    if (grafo.grauVertice[aresta.vertice2 - 1] > grafo.grauMax) {
                        grafo.grauMax = grafo.grauVertice[aresta.vertice2 - 1];
                    }

                    grafo.arestas.add(aresta);
                }

                grafo.grau = new int[grafo.grauMax + 1];

            }
            grafo.startArrays();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return grafo;

    }

    public void printBFS(Grafo grafo, int vertice, String path) {
        grafo.startArrays();

        BFS(grafo, vertice);
        imprimirArvore(grafo, path);
    }

    public void printDFS(Grafo grafo, int vertice, String path) {
        grafo.startArrays();

        DFS(grafo, vertice);
        imprimirArvore(grafo, path);

    }

    public ArrayList<Integer> BFS(Grafo grafo, int vertice) {

        stack = new LinkedList();
        stack.add(vertice);
        grafo.nivel.set(vertice - 1, 0);
        grafo.pai.set(vertice - 1, vertice);
        grafo.marcacao.set(vertice - 1, true);
        verticesArvore = new ArrayList<>();

        while (stack.size() > 0) {
            int verticeTemp = (int) stack.removeFirst();
            verticesArvore.add(verticeTemp);
            grafo.getFilhos(verticeTemp).stream().forEach((verticeFilho) -> {
                stack.add(verticeFilho);
            });
        }

        return verticesArvore;


    }

    public ArrayList<Integer> DFS(Grafo grafo, int vertice) {

        stack = new LinkedList();
        stack.add(vertice);
        grafo.nivel.set(vertice - 1, 0);
        grafo.pai.set(vertice - 1, vertice);
        grafo.marcacao.set(vertice - 1, true);
        verticesArvore = new ArrayList<>();

        while (stack.size() > 0) {
            int verticeTemp = (int) stack.removeLast();
            verticesArvore.add(verticeTemp);
            grafo.getFilhos(verticeTemp).stream().forEach((verticeFilho) -> {
                stack.add(verticeFilho);
            });
        }

        return verticesArvore;

    }

    public ArrayList<Integer> clearBFS(Grafo grafo, int vertice) {
        grafo.startArrays();
        return BFS(grafo, vertice);
    }

    public ArrayList<Integer> clearDFS(Grafo grafo, int vertice) {
        grafo.startArrays();
        return DFS(grafo, vertice);
    }

    private void imprimirArvore(Grafo grafo, String path) {
        try {
            FileWriter arq = new FileWriter(path);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf("vertice nivel pai" + "\r\n");
            for (int i = 0; i < grafo.vertices; i++) {
                int verticeTemp = i + 1;
                gravarArq.printf(verticeTemp + "       " + grafo.nivel.get(i) + "     " + grafo.pai.get(i) + "\r\n");
            }

            arq.close();

        } catch (IOException ex) {
            Logger.getLogger(Manager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dijkstra(Grafo grafo, int v1) {
        if (!grafo.pesosPositivos) {
            System.out.println("Esse grafo possui aresta(s) com peso não positivo. Portanto, não é possível rodar Dijkstra.");
        } else {

            grafo.dist = new float[grafo.vertices];
            Heap heap = new Heap(grafo.vertices);
            grafo.startArrays(); //zera arrays
            Arrays.fill(grafo.dist, Float.MAX_VALUE);


            grafo.marcacao.set(v1 - 1, true);
            grafo.pai.set(v1 - 1, v1);
            grafo.dist[v1 - 1] = 0;

            heap.insert(v1, 0);


            while (!heap.isEmpty()) {
                int vtemp = heap.heapIdentifier[0];
                float temp = heap.deleteMin();
//                System.out.println("Removido do HEAP: " + vtemp + " - " + temp);
//                heap.printHeap();

                grafo.marcacao.set(vtemp - 1, true);

                for (Vizinho vvizinho :
                        grafo.getVizinhos(vtemp)) {

                    if (!grafo.marcacao.get(vvizinho.vizinho - 1)) {
                        if (grafo.dist[vvizinho.vizinho - 1] > grafo.dist[vtemp - 1] + vvizinho.peso) {
                            grafo.dist[vvizinho.vizinho - 1] = grafo.dist[vtemp - 1] + vvizinho.peso;
                            grafo.pai.set(vvizinho.vizinho - 1, vtemp);
                            //update heap


                            heap.insert(vvizinho.vizinho, grafo.dist[vtemp - 1] + vvizinho.peso);
                            float result = grafo.dist[vtemp - 1] + vvizinho.peso;
//                                System.out.println("Adicionado ao HEAP: " + vvizinho.vizinho + " - " + result);
//                                heap.printHeap();
                        }
                    }

                }


            }
        }


    }

    public void prim(Grafo grafo, int v1, String path) {
        grafo.dist = new float[grafo.vertices];
        Heap heap = new Heap(grafo.vertices);
        grafo.startArrays(); //zera arrays
        Arrays.fill(grafo.dist, Float.MAX_VALUE);
        ArrayList<Aresta> arestasAGeradora = new ArrayList<>();
        float custo = 0;

        grafo.marcacao.set(v1 - 1, true);
        grafo.pai.set(v1 - 1, v1);
        grafo.dist[v1 - 1] = 0;

        heap.insert(v1, 0);

        while (!heap.isEmpty()) {
            int vtemp = heap.heapIdentifier[0];
            float temp = heap.deleteMin();
            //add to spanning tree / custo
            custo += temp;
            arestasAGeradora.add(new Aresta(grafo.pai.get(vtemp - 1), vtemp, temp));

            //remover do Heap
//            System.out.println("Removido do HEAP: " + vtemp + " - " + temp);
//            heap.printHeap();

            grafo.marcacao.set(vtemp - 1, true);

            for (Vizinho vvizinho :
                    grafo.getVizinhos(vtemp)) {

                if (!grafo.marcacao.get(vvizinho.vizinho - 1)) {
                    if (grafo.dist[vvizinho.vizinho - 1] > +vvizinho.peso) {
                        grafo.dist[vvizinho.vizinho - 1] = vvizinho.peso;
                        grafo.pai.set(vvizinho.vizinho - 1, vtemp);

                        //update heap
                        heap.insert(vvizinho.vizinho, vvizinho.peso);
//                        System.out.println("Adicionado ao HEAP: " + vvizinho.vizinho + " - " + vvizinho.peso);
//                        heap.printHeap();
//
                    }
                }

            }

        }

        //Imprimir a arvore

        try {
            FileWriter arq = new FileWriter(path);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(grafo.vertices + " " + custo + "\r\n");
            for (int i = 1; i < arestasAGeradora.size(); i++) {
                gravarArq.printf(arestasAGeradora.get(i).vertice1 + " " + arestasAGeradora.get(i).vertice2 + " " + arestasAGeradora.get(i).peso + "\r\n");
            }
            arq.close();

        } catch (IOException ex) {
            Logger.getLogger(Manager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public float distanciaMedia(Grafo grafo) {

        float combinacao2 = grafo.vertices * (grafo.vertices - 1) / 2;
        float sumDist = 0;
        for (int i = 0; i < grafo.vertices; i++) {
            dijkstra(grafo, i + 1);
            for (int j = i; j < grafo.vertices; j++) {
                sumDist += grafo.dist[j];
            }

        }

        return sumDist / combinacao2;

    }

    public void diametroGrafo(Grafo grafo) {

        int diametro = 0;
        ArrayList<ArrayList<Integer>> verticesComponentesConexos = componentesConexos(grafo);

        //itera sobre a maior componente conexa
        for(int i = 0 ; i < verticesComponentesConexos.get(verticesComponentesConexos.size() - 1).size() ; i ++ ){
            ArrayList<Integer> verticesArvore = clearBFS(grafo, verticesComponentesConexos.get(verticesComponentesConexos.size() - 1).get(i));
            int ultimo = verticesArvore.get(verticesArvore.size() - 1);
            if (grafo.nivel.get(ultimo-1)>diametro){
                diametro = grafo.nivel.get(ultimo-1);
            }
        }
        System.out.println("Diametro: " + diametro);

    }

    public ArrayList<Integer> caminhoMinimo(Grafo grafo, int v1, int v2){
        if(grafo.comPeso){
            dijkstra(grafo, v1);

        } else {
            BFS(grafo, v1 );
        }

        //subir vetor de pai a partir de v2 até chegar v1
        ArrayList<Integer> tmp = new ArrayList<>();
        int temp = v2;
        while( temp != v1){
            tmp.add(temp);
            temp = grafo.pai.get(temp);
        }

        return tmp;
    }

    public void printComponentesConexos(Grafo grafo,String path){
        ArrayList<ArrayList<Integer>> verticesComponentesConexos = componentesConexos(grafo);

        try {
            FileWriter arq = new FileWriter(path);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(verticesComponentesConexos.size() + "\r\n");
            for (int i = 0; i < verticesComponentesConexos.size(); i++) {
                gravarArq.printf(verticesComponentesConexos.get(i).size() + " " + verticesComponentesConexos.get(i).toString() + "\r\n");
            }

            arq.close();

        } catch (IOException ex) {
            Logger.getLogger(Grafo.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ArrayList<Integer>> componentesConexos(Grafo grafo) {
        ArrayList<ArrayList<Integer>> verticesComponentesConexos = new ArrayList<>();

        grafo.startArrays();
        Manager manager = new Manager();
        for (int i = 0; i < grafo.vertices; i++) {
            if (!grafo.marcacao.get(i)) {
                ArrayList<Integer> verticesArvore = manager.DFS(grafo, i + 1);
                verticesComponentesConexos.add(verticesArvore);
            }

        }
        Collections.sort(verticesComponentesConexos, new Manager.MyComparator());
        return verticesComponentesConexos;
    }

    public String[] conversao(Grafo grafo, String path) {
        String[] nomes = new String[grafo.vertices];
        Scanner in;
        try {
            in = new Scanner(new FileReader(path));

            while (in.hasNextLine()) {
                String[] temp = in.nextLine().split(",");
                nomes[Integer.parseInt(temp[0]) - 1 ] = temp[1];
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nomes;
    }

    public class MyComparator implements java.util.Comparator<ArrayList<Integer>> {

        public MyComparator() {

        }

        public int compare(ArrayList<Integer> s1, ArrayList<Integer> s2) {
            int dist1 = s1.size();
            int dist2 = s2.size();

            return dist1 - dist2;
        }
    }
}
