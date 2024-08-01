package DFS;

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
            RandomAccessFile arqEntrada = new RandomAccessFile(new File("src/n3e2.csv"), "r");

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
    // Busca em Profundidade
    //------------------------------------------------------------------------------

    public boolean buscaProfundidade(int vertice) {

        boolean[] marcado = new boolean[getNumVertices()]; // A variável numVertices é da classe
        int[] antecessor = new int[getNumVertices()]; // A variável numVertices é da classe

        marcado[vertice] = true;

        for (int coluna = 0; coluna <= marcado.length - 1; coluna++) {

            if (vertice == coluna && !marcado[coluna]) {
                antecessor[coluna] = vertice;
                DFS(coluna);
            }
        }
        return marcado[vertice];
    }

    public void DFS(int vertice) {

        boolean[] marcado = new boolean[0];
        int[] antecessor = new int[marcado.length];
        Stack<Object> pilha = new Stack<>(); // instanciar a pilha

        marcado[vertice] = true;
        pilha.push(vertice);// inserir na pilha

        if (!pilha.isEmpty()) { // ver ser ta vazia

            int coluna = (int) pilha.pop();
            for (int z = 0; z <= marcado.length; z++) {
                if (vertice == coluna && !marcado[coluna]) {
                    antecessor[z] = coluna;
                    marcado[z] = true;
                    pilha.push(z);
                }
            }
        }

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
