package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Created by Caio on 09/11/2016.
 */
public class Heap {

    private static final int d = 2;
    private int heapSize;
    private float[] heap;

    public int[] heapIdentifier;
    public int[] vertexIdentifier;


    public Heap(int capacity) {
        heapSize = 0;
        heap = new float[capacity + 1];
        heapIdentifier = new int[capacity + 1];
        vertexIdentifier = new int[capacity + 1];
        Arrays.fill(heap, -1);
        Arrays.fill(vertexIdentifier, -1);
        Arrays.fill(heapIdentifier, -1);
    }


    public boolean isEmpty() {
        return heapSize == 0;
    }

    public boolean isFull() {
        return heapSize == heap.length;
    }

    private int parent(int i) {
        return (i - 1) / d;
    }

    public void insert(int v, float dist) {
        if (isFull())
            throw new NoSuchElementException("Overflow Exception");
        /** Percolate up **/

        if (vertexIdentifier[v] == -1) {
            vertexIdentifier[v] = heapSize;
            heapIdentifier[heapSize] = v;
            heap[heapSize] = dist;
            heapSize++;
            normalizar(heapSize -1);

        } else {
            update(v, dist);
        }


    }

    public void update(int v, float dist) {
        int ind = vertexIdentifier[v];
        heap[ind] = dist;
        normalizar(ind);
    }

    public float deleteMin() {
        float keyItem = heap[0];
        delete(0);
        return keyItem;
    }

    public float delete(int ind) {
        if (isEmpty())
            throw new NoSuchElementException("Underflow Exception");
        float keyItem = heap[ind];
        int verticeDeletado = heapIdentifier[ind];


        heap[ind] = heap[heapSize - 1];
        heapIdentifier[ind] = heapIdentifier[heapSize - 1];
        vertexIdentifier[heapIdentifier[heapSize - 1]] = ind;


        heap[heapSize - 1]= -1;
        heapIdentifier[heapSize - 1] = -1;
        vertexIdentifier[verticeDeletado] = -1;

        heapSize--;

        if(heapSize > 0) {
            normalizarDelete(ind);
        }


        return keyItem;
    }

    private void normalizar(int iFilho) {

        float tmp = heap[iFilho];
        int vTemp = heapIdentifier[iFilho];
        while (iFilho > 0 && tmp < heap[parent(iFilho)]) {
            heap[iFilho] = heap[parent(iFilho)];
            heapIdentifier[iFilho] = heapIdentifier[parent(iFilho)];
            vertexIdentifier[heapIdentifier[parent(iFilho)]] = iFilho;
            iFilho = parent(iFilho);

        }
        heap[iFilho] = tmp;
        heapIdentifier[iFilho] = vTemp;
        vertexIdentifier[vTemp] = iFilho;


    }

    private void normalizarDelete(int ind) {
        int child;
        float tmp = heap[ind];
        int vtmp = heapIdentifier[ind];
        while (ind * 2 + 1 < heapSize) {
            child = menorFilho(ind);

            if (heap[child] < tmp) {

                heap[ind]=heap[child];
                heapIdentifier[ind] = heapIdentifier[child];
                vertexIdentifier[heapIdentifier[child]] = ind;

                ind = child;
            } else {
                break;
            }
        }
        heap[ind] = tmp;
        heapIdentifier[ind] = vtmp;
        vertexIdentifier[vtmp] = ind;


    }

    private int menorFilho(int ind) {
        int bestChild = ind * 2 + 1;
        int pos = ind * 2 + 2;
        if (pos < heapSize && heap[pos] < heap[bestChild]) {
            bestChild = pos;
        }
        return bestChild;
    }


    public void printHeap() {
        System.out.print("\nHeap = ");
        for (int i = 0; i < heapSize; i++)
            System.out.print(i + ": " + heap[i] + "(" + heapIdentifier[i] + ")" + " ");
        System.out.println();
    }

}
