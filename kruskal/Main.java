package kruskal;

public class Main {
    public static void main(String[] args) {
    System.out.println("---------------------------------------------------------------");
        System.out.println("--------------------Algoritmo de Kruskal-----------------------");
        TGrafos g3 = new TGrafos("kruskal.csv");
        System.out.println( "Numero de vertices: " + g3.getNumVertices() );
        System.out.println( "Numero de arestas: " + g3.getNumArestas() );
        System.out.println( "Matriz de Adjacencias:\n" + g3.exibirMatrix(g3.getMatrizAdjacencias()) );

    TGrafos arvoreG = g3.kruskal(0);

        System.out.println("Matriz de Adjacencias da Arvore Geradora Minima:\n" +
                arvoreG.exibirMatrix(arvoreG.getMatrizAdjacencias()));
        System.out.println("-----------------------------------------------------------------");
}/**/
}
