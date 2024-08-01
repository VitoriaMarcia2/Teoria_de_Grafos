package fila;

public class FilaDinamica {

    private Celula inicio;
    private Celula fim;
    private int contador;

    public FilaDinamica() {
        inicio = null;
        fim = null;
        contador = 0;
    }

    public boolean vazia() {
        return (contador == 0);
    }

    public int tamanho() {
        return (contador);
    }

    public void enfileirar(Object novoItem) {
        Celula novaCelula = new Celula(novoItem);
        if (inicio == null) {
            inicio = novaCelula;
        } else {
            fim.anterior = novaCelula;
        }
        fim = novaCelula;
        contador++;
    }

    public Object desenfileirar() {
        Object x = null;
        if (vazia()) {
            System.out.println("Fila vazia!");
        } else {
            x = inicio.item;
            inicio = inicio.anterior;
            if (inicio == null) {
                fim = null;
            }
            contador--;
        }
        return (x);
    }

    public Object consultarInicio() {
        Object x = null;
        if (vazia()) {
            System.out.println("Fila vazia!");
        } else {
            x = inicio.item;
        }
        return (x);
    }

    public Object consultarFim() {
        Object x = null;
        if (vazia()) {
            System.out.println("Fila vazia!");
        } else {
            x = fim.item;
        }
        return (x);
    }

    public String toString() {
        String filaCompleta = "";
        if (vazia()) {
            filaCompleta = null;
        } else {
            for (int i = 0; i < contador; i++) {
                Object tempObject = desenfileirar();
                filaCompleta = filaCompleta + "\n" + tempObject;
                enfileirar(tempObject);
            }
        }
        return (filaCompleta);
    }
    public Object buscarSeEstaNaFila(Object posicaoItem) {
        Object resultado = null;
        String itemArrayStr = toString(); // isso passa o array para string atraves do metodo toString()
        if (vazia()) {
            resultado = " Erro: Fila vazia!";
        } else {
            resultado = itemArrayStr.contains(posicaoItem.toString()) ? " está na fila!!" : " não está na fila!!";
            // O posicaoItem tem .toString() pq o java não compara Object e String
            // OBS: o .toString que está ma posicaoItem e um metodo do java e não da filaEstatica
            // .contains :verifica se tem o objeto(posicaoItem) na String(itemArrayStr)
        }
        return resultado;
    }
    public Celula buscarObjeto(Object alvo) {
        if(vazia()) {
            System.out.println("Erro: Fila vazia!");
        }else {
            Celula tempCelula = inicio;
            while(tempCelula != null) {
                if(tempCelula.item.equals(alvo)) {
                    return tempCelula;
                }
                tempCelula = tempCelula.anterior;
            }
        }

        return null;
    }
    public void enfileiraComPrioridadeDinamica( Object novoItem) {
        Celula novaCelula = new Celula (novoItem);
        if (inicio != null) {
            novaCelula.anterior = inicio;
        }
        inicio = novaCelula;
        contador++;
    }

    public void enfileirarComPenalidade(Object valorItem) {
        if (vazia()) {
            System.out.println("Erro: fila vazia!");
        }else {
            FilaDinamica filaAux = new FilaDinamica();
            while(!vazia()) {
                Object itemAtual = desenfileirar();
                if (itemAtual != valorItem)
                    filaAux.enfileirar(itemAtual);;
            }
            while (!filaAux.vazia())
                enfileirar(filaAux.desenfileirar());
            enfileirar(valorItem);
        }

    }
}
