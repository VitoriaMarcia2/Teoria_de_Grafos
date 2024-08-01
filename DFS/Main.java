package DFS;

public class Main {
    public static void main(String[] args) {
        System.out.println("---------------------------------------------------------------");
        System.out.println("--------------------Busca em Profundidade-----------------------");
        TGrafos g2 = new TGrafos("n4e5.csv");
        System.out.println("Numero de arestas: "+g2.getNumArestas());
        System.out.println("Numero de vertices: "+g2.getNumVertices());
        System.out.println("Matriz do grafo:\n "+g2.exibirMatrix(g2.getMatrizAdjacencias()));

        System.out.println("Grafo em profundidade: "+g2.buscaProfundidade(1));
        System.out.println("---------------------------------------------------------------");
    }
}
