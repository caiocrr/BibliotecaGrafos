/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Caio
 */
public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
//
//
//        String file1 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\grafos\\grafo_5.txt";
        String fileTest1 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\grafos\\grafoTestGerado.txt";
        String fileTest = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\grafos\\grafoTest.txt";
        String fileGerado1 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\arquivos_enviar\\PRIM_1.txt";
        String fileGerado2 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\arquivos_enviar\\PRIM_2.txt";
        String fileGerado3 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\arquivos_enviar\\PRIM_3.txt";
        String fileGerado4 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\arquivos_enviar\\PRIM_4.txt";
        String fileGerado5 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\arquivos_enviar\\PRIM_5.txt";
        String fileGerado6 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\arquivos_enviar\\PRIM_rede_colaboracao.txt";
//
        String grafo1 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\grafos\\grafo_1.txt";
        String grafo2 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\grafos\\grafo_2.txt";
        String grafo3 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\grafos\\grafo_3.txt";
        String grafo4 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\grafos\\grafo_4.txt";
        String grafo5 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\grafos\\grafo_5.txt";
        String grafoRede = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\grafos\\rede_colaboracao.txt";
        String grafoRedeNomes = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 2\\grafos\\rede_colaboracao_vertices.txt";



        Grafo grafo = manager.carregarArquivo(grafoRede);
        grafo.representacaoGrafo(0);







//        String [] nomes = manager.conversao(grafo,grafoRedeNomes);

//        manager.prim(grafo,1,fileGerado6);
//
//        Grafo mst_rede = manager.carregarArquivo(fileGerado6);
//        mst_rede.representacaoGrafo(0);
//
//        Heap heap = new Heap(mst_rede.vertices);
//
//        for(int i = 0 ; i < mst_rede.vertices ; i++){
//            heap.insert(i+1,mst_rede.grauVertice[i]);
//        }
//
//        ArrayList<Integer> tempV = new ArrayList<>();
//        ArrayList<Float> temp = new ArrayList<>();
//        while(!heap.isEmpty()){
//            int tmpV = heap.heapIdentifier[0];
//            float tmp = heap.deleteMin();
//            temp.add(tmp);
//            tempV.add(tmpV);
//        }
//
//        //Tres maiores graus:
//        int vertice1 = tempV.get(tempV.size()-1);
//        float peso1 = temp.get(temp.size() -1);
//
//        int vertice2 = tempV.get(tempV.size()-2);
//        float peso2 = temp.get(temp.size() -2);
//
//        int vertice3 = tempV.get(tempV.size()-3);
//        float peso3 = temp.get(temp.size() -3);
//
//
//        String name1 = nomes[vertice1-1];
//        String name2 = nomes[vertice2-1];
//        String name3 = nomes[vertice3-1];
//
//        System.out.println("Maior grau: " + mst_rede.grauVertice[vertice1-1] + ":"+peso1 + " - " + name1);
//        System.out.println("Segundo maior grau: " + mst_rede.grauVertice[vertice2-1] + ":"+peso2+ " - " + name2);
//        System.out.println("Terceiro maior grau: " + mst_rede.grauVertice[vertice3-1]+ ":"+peso3 + " - " + name3);
//
//        //Vizinhos
//        int dijkstra = Arrays.asList(nomes).indexOf("Edsger W. Dijkstra") + 1;
//        int daniel = Arrays.asList(nomes).indexOf("Daniel R. Figueiredo") + 1;
//
//
//        System.out.println("Vizinhos Dijkstra============");
//        ArrayList<Vizinho> vizinhosDijkstra = mst_rede.getVizinhos(dijkstra);
//        for(int i = 0 ; i < vizinhosDijkstra.size() ; i ++){
//            System.out.println(nomes[vizinhosDijkstra.get(i).vizinho - 1] + " - Peso: "+vizinhosDijkstra.get(i).peso);
//        }
//
//        System.out.println("Vizinhos Daniel============");
//        ArrayList<Vizinho> vizinhosDaniel = mst_rede.getVizinhos(daniel);
//        for(int i = 0 ; i < vizinhosDaniel.size() ; i ++){
//            System.out.println(nomes[vizinhosDaniel.get(i).vizinho - 1] + " - Peso: "+vizinhosDaniel.get(i).peso);
//        }
//
//
//










//
//        int obj = Arrays.asList(nomes).indexOf("Edsger W. Dijkstra") + 1;
//        manager.dijkstra(grafo,obj);
//
//        //Alan M. Turing - Arrays.asList(nomes).indexOf("Alan M. Turing") + 1
//        //J. B. Kruskal - Arrays.asList(nomes).indexOf("J. B. Kruskal") + 1
//        //Jon M. Kleinberg - Arrays.asList(nomes).indexOf("Jon M. Kleinberg") + 1
//        //Éva Tardos - Arrays.asList(nomes).indexOf("Eva Tardos") + 1
//        //Daniel R. Figueiredo - Arrays.asList(nomes).indexOf("Daniel R. Figueiredo") + 1
//
//        String name = "Daniel R. Figueiredo";
//        ArrayList<Integer> temp = new ArrayList<>();
//        int init = Arrays.asList(nomes).indexOf(name) + 1;
//        int tmp = init;
//        while(tmp!= obj ){
//            temp.add(tmp);
//            tmp = grafo.pai.get(tmp-1);
//        }
//        Collections.reverse(temp);
//        ArrayList<String> response = new ArrayList<>();
//        for( int i = 0 ; i <  temp.size() ; i ++){
//            response.add(nomes[temp.get(i)-1]);
//        }
//
//        System.out.println("Distancia: " + grafo.dist[init - 1] + " - " + response);




//===================================OUTRAS QUESTOES===========================================
//
        long tantes, tdepois, result;
        int vez;
        int obj = 1;
        int vtemp = obj;
        ArrayList<Integer> temp;
//
//
//
        vtemp = obj;
        vez = 10;
        tantes      = System.currentTimeMillis();
        manager.dijkstra(grafo,vez);
        tdepois = System.currentTimeMillis();
        result = tdepois-tantes;
        temp = new ArrayList<>();
        temp.add(1);
        while(true){
            vtemp = grafo.pai.get(vtemp-1);
            if (vtemp == vez){
                break;
            }
            temp.add(vtemp);
        }
        Collections.reverse(temp);
        System.out.println( grafo.dist[0] + " - " + temp + " - " + result + "ms");


        vtemp = obj;
        vez = 20;
        tantes      = System.currentTimeMillis();
        manager.dijkstra(grafo,vez);
        tdepois = System.currentTimeMillis();
        result = tdepois-tantes;
        temp = new ArrayList<>();
        temp.add(1);
        while(true){
            vtemp = grafo.pai.get(vtemp-1);
            if (vtemp == vez){
                break;
            }
            temp.add(vtemp);
        }
        Collections.reverse(temp);
        System.out.println( grafo.dist[0] + " - " + temp + " - " + result + "ms");
//
//
//        vtemp = obj;
//        vez = 30;
//        tantes      = System.currentTimeMillis();
//        manager.dijkstra(grafo,vez);
//        tdepois = System.currentTimeMillis();
//        result = tdepois-tantes;
//        temp = new ArrayList<>();
//        temp.add(1);
//        while(true){
//            vtemp = grafo.pai.get(vtemp-1);
//            if (vtemp == vez){
//                break;
//            }
//            temp.add(vtemp);
//        }
//        Collections.reverse(temp);
//        System.out.println( grafo.dist[0] + " - " + temp + " - " + result + "ms");
//
//
//        vtemp = obj;
//        vez = 40;
//        tantes      = System.currentTimeMillis();
//        manager.dijkstra(grafo,vez);
//        tdepois = System.currentTimeMillis();
//        result = tdepois-tantes;
//        temp = new ArrayList<>();
//        temp.add(1);
//        while(true){
//            vtemp = grafo.pai.get(vtemp-1);
//            if (vtemp == vez){
//                break;
//            }
//            temp.add(vtemp);
//        }
//        Collections.reverse(temp);
//        System.out.println( grafo.dist[0] + " - " + temp + " - " + result + "ms");
//
//
//        vtemp = obj;
//        vez = 50;
//        tantes      = System.currentTimeMillis();
//        manager.dijkstra(grafo,vez);
//        tdepois = System.currentTimeMillis();
//        result = tdepois-tantes;
//        temp = new ArrayList<>();
//        temp.add(1);
//        while(true){
//            vtemp = grafo.pai.get(vtemp-1);
//            if (vtemp == vez){
//                break;
//            }
//            temp.add(vtemp);
//        }
//        Collections.reverse(temp);
//        System.out.println( grafo.dist[0] + " - " + temp + " - " + result + "ms");



    }
}
