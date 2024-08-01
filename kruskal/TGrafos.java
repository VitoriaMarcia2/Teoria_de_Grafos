package kruskal;

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
            RandomAccessFile arqEntrada = new RandomAccessFile(new File("src/kruskal.csv"), "r");

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
    // Algoritmo de Kruskal
    //------------------------------------------------------------------------------

    public TGrafos kruskal(int verticeInicial){

        TGrafos arvore = new TGrafos(); // arvore geradora minima a ser criada
        LinkedList<Integer> re = new LinkedList<>();
        List<List<Integer>> arestas =  new ArrayList<>(); // lista de arestas identificadas pelos pares de vértices(int[])
        int[] pa = new int[getNumVertices()]; // Vetor de parents, de onde eu vim
        Arrays.fill(pa, -1); // n posições inicializadas com -1
        int anterior;

        pa[verticeInicial] = -2; // Escolha um vértice Vi e marque-o com 0 na posição i em P
        re.add(verticeInicial);
        anterior = re.getLast();

        adjacentes(anterior, arestas, pa);

        List<Integer> vertices;
        while (contemMenos1(pa)) {// Faça enquanto p == -1
            if (anterior != re.getLast() ) {
                adjacentes(re.getLast(), arestas, pa); // obtem as arestas candidatas a próxima escolha
                anterior = re.getLast();
            }

            vertices = menorArest(arestas); // obtenha a aresta de menor peso
            if (re.size() == 1){ // se é a primeira iteração não há arestas para formar circuitos
                pa[vertices.get(1)] = vertices.get(0); // marque a posição j de p com o valor de i
                re.add(vertices.get(1));
            }else {
                if (!fecharCircuito(vertices.get(0), vertices.get(1), pa)){
                    pa[vertices.get(1)] = vertices.get(0); // marque a posição j de p com o valor de i
                    re.add(vertices.get(1));
                }
            }
            arestas.remove(vertices);
        }

        int[][] matrixPeso = new int[getNumVertices()][getNumVertices()];
        int[][] matrixAdj = new int[getNumVertices()][getNumVertices()];
        for (int j = 0; j < pa.length; j++) {
            if (pa[j] == -2){
                continue;
            }
            matrixPeso[pa[j]][j] = this.matrizPesos[pa[j]][j];
            matrixPeso[j][pa[j]] = this.matrizPesos[j][pa[j]];
            matrixPeso[pa[j]][j] = 1;
            matrixPeso[j][pa[j]] = 1;
        }

        arvore.setNumVertices(getNumVertices());
        arvore.setNumArestas(getNumArestas());
        arvore.setVertices(this.vertices);
        arvore.setMatrizAdjacencias(matrixAdj);
        arvore.setMatrizPesos(matrixPeso);

        return arvore;
    }

    private void adjacentes(int v, List<List<Integer>> arestas, int[] p) {
        for (int j = 0; j < getNumVertices(); j++) {
            if (matrizPesos[v][j] != 0){
                if (!estaAdicionado(v, j, p) || !containsAresta(v, j, arestas)){
                    arestas.add(List.of(v, j));
                }
            }
        }
    }

    public List<Integer> menorArest(List<List<Integer>> arest){

        // variaveis auxiliares para indentificação das arestas.
        int v1 = arest.get(0).get(0), v2 = arest.get(0).get(1);

        int vert = 0;
        int menor = matrizPesos[v1][v2]; // menor peso de aresta encontrado

        for (int i = 1; i < arest.size(); i++) {
            v1 = arest.get(i).get(0);
            v2 = arest.get(i).get(1);

            if (matrizPesos[v1][v2] < menor){
                menor = matrizPesos[v1][v2];
                vert = i;
            }
        }
        return arest.get(vert);
    }

    public boolean fecharCircuito(int vertice, int buscado, int[] p) {

        int n = p.length;
        boolean[] marcado = new boolean[n];
        int[] antecessor = new int[n];
        marcado[vertice] = true;

        for (int w = 0; w < n; w++) {
            if (eAdjacente(vertice, w, p) && !marcado[w]){
                antecessor[w] = vertice;
                DFS(w, marcado, antecessor, n, p);
            }
        }
        return marcado[buscado];
    }

    // Parte de busca e profundidade modificado para verificar se fecha circuito
    public void DFS(int vertice, boolean[] marcado, int[] antecessor, int n, int[] p) {

        Stack<Integer> pilha = new Stack<>(); // instanciar a pilha

        marcado[vertice] = true;
        pilha.push(vertice);// inserir na pilha

        while(!pilha.isEmpty()) { // ver ser ta vazia
            int w = pilha.pop();
            for (int z = 0; z < n; z++) {
                if (eAdjacente(w, z, p) && !marcado[z]) {
                    antecessor[z] = w;
                    marcado[z] = true;
                    pilha.push(z);
                }
            }
        }

    }

    // Verifica se dois vertices são adjacentes na arvore (no vetor p)
    private boolean eAdjacente(int v, int w, int[] vetor) {
        return vetor[v] == w || vetor[w] == v;
    }

    // Verifica se já está adicionado na arvore (no vetor p)
    private boolean estaAdicionado(int x, int y, int[] p) {
        return p[x] == y || p[y] == x;
    }

    private boolean containsAresta(int vertice1, int vertice2, List<List<Integer>> arestas) {
        return arestas.contains(List.of(vertice1, vertice2));
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