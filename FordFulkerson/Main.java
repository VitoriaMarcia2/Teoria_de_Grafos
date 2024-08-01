package FordFulkerson;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //Decidimos testar a matriz de forma diferente, sem entrada de arquivo .csv
        //Construimos nós mesmo uma matriz com entrada da dados do usuario, mas colocamos alguns
        //valores fixos, como o nos, fonte e destino
        //para os dados de teste da matriz, pode usar os dados
      /*0 16 13 0 0 0
        0 0  10 12 0 0
        0 4 0 0 14 0
        0 0 9 0 0 20
        0 0 0 7 0 4
        0 0 0 0 0 0*/
        //o resultado esperado é: 14
        //sinta-se a vontade para alterar os dados propostos, afim de testar o algoritmo
        System.out.println("---------------------------------------------------------------");
        System.out.println("-----------------Algoritmo de Ford Fulkerson-------------------");

        Scanner scanner = new Scanner(System.in);

        int[][] matriz;
        int nos = 6, fonte = 1, destino = 5, fluxoMax;

        matriz = new int[nos + 1][nos + 1];
        System.out.println("Insira a matriz");
        for (int sourceVertex = 1; sourceVertex <= nos; sourceVertex++) {
            for (int destinationVertex = 1; destinationVertex <= nos; destinationVertex++) {
                matriz[sourceVertex][destinationVertex] = scanner.nextInt();
            }
        }

        TGrafos g4 = new TGrafos(nos);
        fluxoMax = g4.run(matriz, fonte, destino);
        System.out.println("O fluxo máximo de G é: " + fluxoMax);
    }
}
