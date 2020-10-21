package tsvizzevolution;

import java.awt.*;

public class Data implements Comparable<Data>{

    public Data(String nome, int valor, String classe, String projeto) {
        this.nome = nome;
        this.valor = valor;
        this.classe = classe;
        this.projeto = projeto;
    }
    public Data(String nome, int valor, String classe, String projeto, Color cor) {
        this.nome = nome;
        this.valor = valor;
        this.classe = classe;
        this.cor = cor;
        this.projeto = projeto;
    }
    public Data(String nome, int valor, String autor) {
        this.nome = nome;
        this.valor = valor;
        this.autor = autor;
    }
    String classe;
    String nome;
    int valor;
    Color cor;
    String projeto;
    String autor;

    @Override
    public int compareTo(Data o) {
        int c = this.nome.compareTo(o.nome);
        if(c != 0){
            return c;
        }else{
            c = this.classe.compareTo(o.classe);
            return c;
        }
    }

    @Override
    public String toString() {
        return "Data{" +
                "classe='" + classe + '\'' +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                '}';
    }
}
