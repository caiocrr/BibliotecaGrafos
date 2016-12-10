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

    public int[] TSP(Grafo grafo) {
        // 1 - Construir MST 
        Grafo grafoMST = this.prim(grafo, 1); //O(n^2)

        //Pegar subgrafo: vertices com grau impar e criar um determinar o perfect matching entre eles
        ArrayList<Integer> oddDegrees = this.getOddVertex(grafoMST); //O(n)

        float[][] matrizAdj = grafo.representacao.getMatrix();
        //Pegar PerfectMatch com minimum weight
        int[][] match = this.getMaxMatchMinimumCost(oddDegrees, matrizAdj); //O(n^3)
//
//        for (int i = 0; i < match.length; i++) {
//            System.out.println(Arrays.toString(match[i]));
//        }
//        System.out.println(Arrays.toString(match[0]));

        //Multigrafo : MST + match
        Node[] multiGrafo = multiGrafo(match, grafoMST); //O(n)

        int caminho[] = caminhoEuleriano(multiGrafo);

        double pathLength = 0;
        for (int i = 0; i < caminho.length - 1; i++) {
            pathLength += matrizAdj[caminho[i] - 1][caminho[i + 1] - 1];
        }
      
        Two_OPT two_opt1 = new Two_OPT();
        double tmp = two_opt1.twoOpt(matrizAdj, caminho, pathLength, grafo.vertices);
        caminho = two_opt1.get_path();
        
                
        while (tmp < pathLength) {
            pathLength = tmp;
            tmp = two_opt1.twoOpt(matrizAdj, caminho, pathLength, grafo.vertices);
            caminho = two_opt1.get_path();
        }

        return caminho;

    }

    public double[][] generateNewMatrix(ArrayList<Integer> oddDegrees, float[][] matrizAdj, int[] newMatrixToOld) {
        int oddSize = oddDegrees.size();
        double[][] newMatrix = new double[oddSize][oddSize];
        for (int i = 0; i < oddSize; i++) {
            for (int j = 0; j < oddSize; j++) {
                if (newMatrixToOld[i] == newMatrixToOld[j]) {
                    newMatrix[i][j] = Double.MAX_VALUE;
                } else {
                    newMatrix[i][j] = (double) matrizAdj[newMatrixToOld[i] - 1][newMatrixToOld[j] - 1];
                }

            }
        }
        return newMatrix;
    }

    public int[] caminhoEuleriano(Node[] nodes) {
        LinkedList caminhoFinal = new LinkedList();
        ArrayList tmp = new ArrayList();

        int j = 0;
        nodes[0].getNextChild(nodes[0].name, tmp, true);
        caminhoFinal.addAll(0, tmp);

        while (j < caminhoFinal.size()) {
            if (nodes[((Integer) caminhoFinal.get(j)).intValue()].hasMoreChilds()) {

                nodes[((Integer) caminhoFinal.get(j)).intValue()].getNextChild(nodes[((Integer) caminhoFinal.get(j)).intValue()].name,
                        tmp, true);
                if (tmp.size() > 0) {

                    for (int i = 0; i < caminhoFinal.size(); i++) {
                        if (((Integer) caminhoFinal.get(i)).intValue() == ((Integer) tmp.get(0)).intValue()) {
                            caminhoFinal.addAll(i, tmp);
                            break;
                        }
                    }
                    tmp.clear();
                }
                j = 0;
            } else {
                j++;
            }
        }

        boolean inPath[] = new boolean[nodes.length];
        int[] route = new int[nodes.length];
        j = 0;
        for (int i = 0; i < caminhoFinal.size(); i++) {
            if (!inPath[((Integer) caminhoFinal.get(i)).intValue()]) {
                route[j] = ((Integer) caminhoFinal.get(i)).intValue() + 1;
                j++;
                inPath[((Integer) caminhoFinal.get(i)).intValue()] = true;
            }
        }

        return route;
    }

    public Node[] multiGrafo(int[][] match, Grafo mst) {

        Node nodes[] = new Node[mst.vertices];

        for (int i = 0; i < mst.vertices; i++) {
            nodes[i] = new Node(i);
        }

        //Pai como filho filho como pai da MST.. duplica as arestas
        for (int i = 0; i < mst.vertices; i++) {
            nodes[i].addChild(nodes[mst.pai.get(i) - 1]);
            nodes[mst.pai.get(i) - 1].addChild(nodes[i]);
        }

        //Duplicando arestas do match
        for (int i = 0; i < match.length; i++) {
            nodes[match[i][0] - 1].addChild(nodes[match[i][1] - 1]);
            nodes[match[i][1] - 1].addChild(nodes[match[i][0] - 1]);
        }
        return nodes;
    }

    public boolean checkBipartite(Grafo grafo) {
        LinkedList stack = new LinkedList();
        stack.add(1);

        //Definir cor 1 e cor 0
        int[] cor = new int[grafo.vertices];
        Arrays.fill(cor, -1);
        //Definir array de cores, onde o índice é o vertice e o campo é a cor
        cor[1] = 1;
        while (stack.size() > 0) {
            int u = (int) stack.removeFirst();
            ArrayList<Vizinho> vizinhos = grafo.getVizinhos(u);
            for (int i = 0; i < vizinhos.size(); i++) {
                if (cor[vizinhos.get(i).vizinho - 1] == -1) {
                    cor[vizinhos.get(i).vizinho - 1] = 1 - cor[u - 1];
                    stack.add(vizinhos.get(i).vizinho);

                } else {
                    if (cor[vizinhos.get(i).vizinho - 1] == cor[u - 1]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int[][] getMaxMatchMinimumCost(ArrayList<Integer> listOddDegreeVertices, float[][] matrizAdj) {

        int qtdOdd = listOddDegreeVertices.size();
        Aresta arestas[][] = new Aresta[qtdOdd][qtdOdd];

        for (int i = 0; i < qtdOdd; i++) {
            for (int j = 0; j < qtdOdd; j++) {
                if (listOddDegreeVertices.get(i) != listOddDegreeVertices.get(j)) {
                    arestas[i][j] = new Aresta((listOddDegreeVertices.get(i)),
                            listOddDegreeVertices.get(j),
                            matrizAdj[listOddDegreeVertices.get(i) - 1][listOddDegreeVertices.get(j) - 1]);
                } else {
                    arestas[i][j] = new Aresta(listOddDegreeVertices.get(i),
                            listOddDegreeVertices.get(j), Float.MAX_VALUE);
                }
            }
            Arrays.sort(arestas[i]);
        }

        int maxIt = qtdOdd;
        float countGlobal = Float.MAX_VALUE;

        boolean matched[];
        int matchGlobal[][] = new int[(qtdOdd / 2)][2];
        int match[][];
        float count;
        int k;

        for (int m = 0; m < maxIt; m++) {

            //arestas ordenadas pelo peso
            //Primeiro conjunto de arestas é feito contendo sempre a aresta de menor valor...        
            match = new int[(qtdOdd / 2)][2];
            matched = new boolean[matrizAdj.length];
            count = 0;
            k = 0;

            count += arestas[m][0].peso;
            matched[arestas[m][0].vertice1 - 1] = true;
            matched[arestas[m][0].vertice2 - 1] = true;
            match[k][0] = arestas[m][0].vertice1;
            match[k][1] = arestas[m][0].vertice2;
            k++;

            for (int i = 0; i < qtdOdd; i++) {
                for (int j = 0; j < qtdOdd; j++) {
                    if (matched[arestas[i][j].vertice1 - 1] || matched[arestas[i][j].vertice2 - 1]) {
                        continue;
                    } else {

                        count += arestas[i][j].peso;
                        matched[arestas[i][j].vertice1 - 1] = true;
                        matched[arestas[i][j].vertice2 - 1] = true;
                        match[k][0] = arestas[i][j].vertice1;
                        match[k][1] = arestas[i][j].vertice2;
                        k++;
                    }
                }
            }
            if (count < countGlobal) {
                countGlobal = count;
                matchGlobal = match;
            }

        }

//        float minWeight = Float.MAX_VALUE;
//        int idMin = -1;
//        for (int i = 0 ; i < count.length ; i ++){
//            if (count[i]<minWeight){
//                minWeight = count[i];
//                idMin = i;
//            }
//        }
//        System.out.println(Arrays.toString(count));
//        System.out.println(count[idMin]);
        return matchGlobal;
    }

    public ArrayList<Integer> getOddVertex(Grafo grafoMST) {

        //Vetor de n com marcacao para os vertices presentes.
        boolean[] subVertices = new boolean[grafoMST.vertices];
        //Lista de vertices presentes;
        ArrayList<Integer> listOddDegreeVertices = new ArrayList();

        //Como temos um novo grafo com vertices de indices diferentes do original, temos que fazer um mapeamento. Definimos
        //isso dentro do grafo.
        for (int i = 0; i < grafoMST.grauVertice.length; i++) {
            if (grafoMST.grauVertice[i] % 2 == 1) {
                subVertices[i] = true;
                listOddDegreeVertices.add(i + 1);
            }
        }

        return listOddDegreeVertices;

    }

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

    public Grafo carregarPontosXY(String path) {
        Grafo grafo = new Grafo();
        ArrayList<Position> positions = new ArrayList();

        Scanner in;
        try {
            in = new Scanner(new FileReader(path));
            Boolean firstline = false;
            grafo.arestas = new ArrayList();

            while (in.hasNextLine()) {
                if (!firstline) {
                    grafo.vertices = Integer.parseInt(in.nextLine().split(" ")[0]);
                    firstline = true;

                } else {
                    String[] temp = in.nextLine().split(" ");

                    Position position = new Position(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
                    positions.add(position);
                }

            }
            double x1, x2, y1, y2;
            float dist;
            for (int i = 0; i < grafo.vertices; i++) {
                //Criar arestas do grafo completo
                for (int j = i + 1; j < grafo.vertices; j++) {
                    //Calcular dist i to j em positions
                    //e criar arestas (i to j) com peso da distancia
                    x1 = (double) positions.get(i).x;
                    y1 = (double) positions.get(i).y;

                    x2 = (double) positions.get(j).x;
                    y2 = (double) positions.get(j).y;

                    dist = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

                    grafo.arestas.add(new Aresta(i + 1, j + 1, dist));
                }

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

                for (Vizinho vvizinho
                        : grafo.getVizinhos(vtemp)) {

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

    public Grafo prim(Grafo grafo, int v1) {
        Grafo grafoMST = new Grafo();
        grafoMST.vertices = grafo.vertices;
        grafoMST.arestas = new ArrayList();
        grafoMST.grauVertice = new int[grafo.vertices];

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
        boolean first = false;
        while (!heap.isEmpty()) {
            int vtemp = heap.heapIdentifier[0];
            float temp = heap.deleteMin();
            //add to spanning tree / custo
//            custo += temp;
            Aresta aresta = new Aresta(grafo.pai.get(vtemp - 1), vtemp, temp);
            if (first) {
                grafoMST.arestas.add(aresta);
                grafoMST.grauVertice[aresta.vertice1 - 1] += 1;
                grafoMST.grauVertice[aresta.vertice2 - 1] += 1;
            } else {
                first = true;
            }
            arestasAGeradora.add(aresta);
            //remover do Heap
//            System.out.println("Removido do HEAP: " + vtemp + " - " + temp);
//            heap.printHeap();

            grafo.marcacao.set(vtemp - 1, true);

            for (Vizinho vvizinho
                    : grafo.getVizinhos(vtemp)) {

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
        grafoMST.pai = grafo.pai;
        return grafoMST;

    }

    public void printMST(Grafo grafo, int v1, String path) {

        Grafo grafoMST = this.prim(grafo, v1);
        ArrayList<Aresta> arestasAGeradora = grafoMST.arestas;
        try {
            FileWriter arq = new FileWriter(path);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(grafo.vertices + "");
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
        for (int i = 0; i < verticesComponentesConexos.get(verticesComponentesConexos.size() - 1).size(); i++) {
            ArrayList<Integer> verticesArvore = clearBFS(grafo, verticesComponentesConexos.get(verticesComponentesConexos.size() - 1).get(i));
            int ultimo = verticesArvore.get(verticesArvore.size() - 1);
            if (grafo.nivel.get(ultimo - 1) > diametro) {
                diametro = grafo.nivel.get(ultimo - 1);
            }
        }
        System.out.println("Diametro: " + diametro);

    }

    public ArrayList<Integer> caminhoMinimo(Grafo grafo, int v1, int v2) {
        if (grafo.comPeso) {
            dijkstra(grafo, v1);

        } else {
            BFS(grafo, v1);
        }

        //subir vetor de pai a partir de v2 até chegar v1
        ArrayList<Integer> tmp = new ArrayList<>();
        int temp = v2;
        while (temp != v1) {
            tmp.add(temp);
            temp = grafo.pai.get(temp);
        }

        return tmp;
    }

    public void printComponentesConexos(Grafo grafo, String path) {
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
                nomes[Integer.parseInt(temp[0]) - 1] = temp[1];
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
