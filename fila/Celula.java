package fila;

class Celula {

    Object item;
    Celula anterior; // referencia para o objeto anterior

    public Celula() {
        item = null;
        anterior = null;
    }

    public Celula(Object valorItem) {
        item = valorItem;
        anterior = null;
    }

    public Celula(Object valorItem, Celula celulaSeguinte) {
        item = valorItem;
        anterior = celulaSeguinte;
    }
}
