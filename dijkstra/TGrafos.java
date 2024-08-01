package dijkstra;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class TGrafos {

    private int numVertices;                // qtde de vertices
    private int numArestas;                 // qtde de arestas
    private String[] vertices;            //array que armazena o nome de cada vertice
    private String[] arestas;            //array que armazena o nome de cada aresta
    private int[][] matrizAdjacencias;    //matriz de adjacencias numvertices X numvertices
    private int[][] matrizPesos;        //matriz de pesos

    private int[] grausVertices;            //Grau de todos os vertices


    //------------------------------------------------------------------------------

    public TGrafos() {
    }

    public TGrafos(String nomearquivo) {
        this.lerArquivo(nomearquivo);
    }

    //----------------------------------------

    private void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int getNumVertices() {
        return (this.numVertices);
    }

    private void setNumArestas(int numArestas) {
        this.numArestas = numArestas;
    }

    public int getNumArestas() {
        return (this.numArestas);
    }

    private void setVertices(String[] vertices) {
        this.vertices = vertices;
    }

    public String[] getVertices() {
        return (this.vertices);
    }

    private void setArestas(String[] arestas) {
        this.arestas = arestas;
    }

    public String[] getArestas() {
        return (this.arestas);
    }

    public int[][] getMatrizAdjacencias() {
        return (this.matrizAdjacencias);
    }

    public int[][] getMatrizPesos() {
        return this.matrizPesos;
    }

    public int getGrauVertice(int vertice) {
        int grau = 0;
        for (int i = 0; i < this.vertices.length; i++) {
            if (this.matrizAdjacencias[vertice][i] == 1) {
                grau++;
            }
        }
        return (grau);
    }

    public void setMatrizAdjacencias(int[][] matrizAdjacencias) {
        this.matrizAdjacencias = matrizAdjacencias;
    }

    public void setMatrizPesos(int[][] matrizPesos) {
        this.matrizPesos = matrizPesos;
    }

    //Grau de todos os vertices - OK!
    public int[] getGrausVertices() {
        this.grausVertices = new int[this.getNumVertices()];
        if (this.getNumVertices() > 0) {
            for (int i = 0; i < this.getNumVertices(); i++) {
                this.grausVertices[i] = this.getGrauVertice(i);
            }
        } else {
            System.out.println("ERRO: Nao existem vertices no grafo!");
        }
        return (this.grausVertices);
    }

    //------------------------------------------------------------------------------

    public void lerArquivo(String nomearquivo) {
        try {
            RandomAccessFile arqEntrada = new RandomAccessFile(new File("src/Dijkstra.csv"), "r");

            // Rotulo dos vertices
            String linha = arqEntrada.readLine();
            String[] vertices = linha.split(";");
            this.setVertices(vertices);

            this.setNumVertices(vertices.length); // Qtde de vertices

            this.matrizAdjacencias = new int[this.numVertices][this.numVertices];
            this.matrizPesos = new int[this.numVertices][this.numVertices];

            linha = arqEntrada.readLine();
            int nLin = 0; // Primeia linha da matriz
            while (linha != null) {
                String[] lin = linha.split(";");
                for (int j = 0; j < lin.length; j++) {
                    if (lin[j].equals("0")) {
                        this.matrizPesos[nLin][j] = 0;
                        this.matrizAdjacencias[nLin][j] = 0;
                    } else {
                        this.matrizPesos[nLin][j] = Integer.parseInt(lin[j]);
                        this.matrizAdjacencias[nLin][j] = 1;
                        this.numArestas++;
                    }
                }
                nLin++;
                linha = arqEntrada.readLine();
            }
            this.setNumArestas(this.numArestas); // Qtde de arestas
            this.grausVertices = this.getGrausVertices(); // Grau de todos os vertices

            arqEntrada.close();

        } catch (IOException e) {
            System.out.println("ERRO: Leitura de arquivo invalida!");
        } catch (NumberFormatException e) {
            System.out.println("ERRO: Formato de numero invalido!");
        }
    }


    //------------------------------------------------------------------------------
    // Algoritmo de Dikstra
    //------------------------------------------------------------------------------

    public HashMap<String, Integer> dijkstra(int v0){

        List<Integer> borda = new ArrayList<>(); // proximos vértices candidatos a próxima escolha
        borda.add(v0);

        List<Integer> incluidos = new ArrayList<>(); // menores caminhos a partir do inicial

        int[] custo = new int[getNumVertices()]; // Custo do caminho até x
        Arrays.fill(custo, Integer.MAX_VALUE); // custo[x] = infinito d
        custo[v0] = 0;

        int v = 0;
        while (!borda.isEmpty()){

            v = menorCusto(borda, custo); // Vértice da borda com o menor custo
            incluidos.add(v); // insira v em incluidos
            borda.remove((Integer) v); // retire v da borda

            // todos vertices de G não pertencentes em incluidos
            for (int x = 0; x < getNumVertices(); x++){
                if (!incluidos.contains(x)){
                    if ( ( eAdjacente(v,x, matrizAdjacencias) ) && ( custo[x] > custo[v] + pesoXparaY(v,x) ) ){
                        custo[x] = custo[v] + pesoXparaY(v,x);
                        borda.add(x); // insira x na borda
                    }
                }
            }
        }

        HashMap<String, Integer> solucao = new HashMap<>();
        for (int i = 0; i < custo.length; i++) {
            solucao.put(vertices[i], custo[i]);
        }

        return solucao; // Retorna os vertices e os custos para chegar em nele a partir do inicial
    }

    // Vértice da borda com o menor custo
    private int menorCusto(List<Integer> borda, int[] custo) {

        int menorPeso = Integer.MAX_VALUE;
        int vertice = 0;

        for (int i : borda) {
            if (custo[i] < menorPeso){
                menorPeso = custo[i];
                vertice = i;
            }
        }

        return vertice;
    }

    // W(x, y): peso da aresta de x para y
    private int pesoXparaY(int v, int x){
        return matrizPesos[v][x];
    }

    // E(x, y): aresta de x para y
    private boolean eAdjacente(int v, int w, int[][] vetor) {
        return vetor[v][w] != 0;
    }

    //------------------------------------------------------------------------------
    // METODOS AUXILIARES
    //------------------------------------------------------------------------------

    public String exibirVetor(int[] vet) {
        String res = "";
        for (int i = 0; i < vet.length; i++) {
            res += ((i < (vet.length - 1)) ? vet[i] + "-" : vet[i]);
        }
        return (res);
    }

    public String exibirVetor(String[] vet) {
        String res = "";
        for (int i = 0; i < vet.length; i++) {
            res += ((i < (vet.length - 1)) ? vet[i] + "-" : vet[i]);
        }
        return (res);
    }

    public String exibirMatrix(int[][] mat) {
        String res = "";
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                res += ((j < (mat[0].length - 1)) ? mat[i][j] + "  " : mat[i][j]);
            }
            res += ((i < (mat.length - 1)) ? "\n" : "");
        }
        return (res);
    }

    private boolean contemMenos1(int[] vetor) {
        for (int x : vetor) {
            if (x == -1) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return (">>> Classe src.Grafos (G) <<<" + "\n\n" +
                "Matriz de adjacencias  :\n" + this.exibirMatrix(this.getMatrizAdjacencias()) + "\n" +
                "Numero de vertices (n) : " + this.getNumVertices() + "\n" +
                "Numero de arestas (e)  : " + this.getNumArestas() + "\n" +
                "Graus dos vertices de G: " + this.exibirVetor(this.getGrausVertices()) + "\n"
        );
    }
}
