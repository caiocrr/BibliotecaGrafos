/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Caio
 */
public class Two_OPT {

    float[][] matrizAdj;
    int[] path;
    double totalDist;

    public void reverse(int start, int end, int n) {
        while (end - start > 0) {
            int temp = path[start % n];
            path[start % n] = path[end % n];
            path[end % n] = temp;
            start++;
            end--;
        }
    }

    public boolean is_path_shorter(int v1, int v2, int v3, int v4) {
        if ((matrizAdj[v1 - 1][v3 - 1] + matrizAdj[v2 - 1][v4 - 1]) < (matrizAdj[v1 - 1][v2 - 1] + matrizAdj[v3 - 1][v4 - 1])) {
            totalDist -= (matrizAdj[v1 - 1][v2 - 1] + matrizAdj[v3 - 1][v4 - 1] - matrizAdj[v1 - 1][v3 - 1] - matrizAdj[v2 - 1][v4 - 1]);
            return true;
        }
        return false;
    }

    double twoOpt(float[][] matrizAdj, int[] path, double pathLength, int n) {
        this.path = path;
        this.matrizAdj = matrizAdj;
        this.totalDist = pathLength;
 
        int v1, v2, u1, u2;

        for (int i = 0; i < n; i++) {
            u1 = i;
            v1 = (i + 1) % n; // esse jeito possibilita voltar do ultimo vertice para o primeiro

            for (int j = i + 2; (j + 1) % n != i; j++) {
                u2 = j % n;
                v2 = (j + 1) % n;

                if (is_path_shorter(path[u1], path[v1], path[u2],
                        path[v2])) {
                    reverse(i + 1, j, n); // v1, u2
                }
            }
        }
        return totalDist;
    }

    int[] get_path() {
        return path;
    }
}
