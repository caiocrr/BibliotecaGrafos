/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Arrays;
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
//        String filePoints1 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-5.txt";
//        String filePoints2 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-10.txt";
//        String filePoints3 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-20.txt";
//        String filePoints4 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-50.txt";
//        String filePoints5 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-100.txt";
//        String filePoints6 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-200.txt";
//        String filePoints7 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-500.txt";
//        String filePoints8 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-1000.txt";
//        String filePoints9 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-2000.txt";
//        String filePoints10 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-5000.txt";
//        String filePoints11 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-7500.txt";
//        String filePoints12 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-10000.txt";
//
//        String fileTestPrint1 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-5.txt";
//        String fileTestPrint2 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-10.txt";
//        String fileTestPrint3 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-20.txt";
//        String fileTestPrint4 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-50.txt";
//        String fileTestPrint5 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-100.txt";
//        String fileTestPrint6 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-200.txt";
//        String fileTestPrint7 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-500.txt";
//        String fileTestPrint8 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-1000.txt";
//        String fileTestPrint9 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-2000.txt";
//        String fileTestPrint10 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-5000.txt";
//        String fileTestPrint11 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-7500.txt";
//        String fileTestPrint12 = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-10000.txt";


//        String fileGrafo = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\grafos\\points-5.txt";
          String fileGrafo = "E:\\materiais\\16.2\\teoria_dos_grafos\\trabalho 3\\posicoes\\points-1000.txt";



//        Grafo grafo = manager.carregarArquivo(fileGrafo);
        long antes = System.currentTimeMillis();
        Grafo grafo = manager.carregarPontosXY(fileGrafo);
        grafo.representacaoGrafo(1);
        
        System.out.println(Arrays.toString(manager.TSP(grafo)));
        
        long depois = System.currentTimeMillis();
        long result = depois - antes;
        System.out.println(result);
        
        
//        



    }
}
