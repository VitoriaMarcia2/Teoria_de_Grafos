package dijkstra;

public class Main {
    public static void main(String[] args) {


        System.out.println("---------------------------------------------------------------");
        System.out.println("--------------------Algoritmo de Dijkstra-----------------------");
        TGrafos g1 = new TGrafos("Dijkstra.csv"); // grafo g1 instanciado para o algoritimo de dijkstra
        System.out.println("Numero de arestas: "+g1.getNumArestas());
        System.out.println("Numero de vertices: "+g1.getNumVertices());
        System.out.println("Matriz do grafo:\n"+g1.exibirMatrix(g1.getMatrizAdjacencias()));
        System.out.println("Algoritmo de Dijkstra: "+g1.dijkstra(0));

        System.out.println("---------------------------------------------------------------");
}
}
