package tsvizzevolution;

import java.awt.*;

public class Dados implements Comparable<Dados>{

    public Dados(String nome, int valor, String classe, String projeto) {
        this.nome = nome;
        this.valor = valor;
        this.classe = classe;
        this.projeto = projeto;
    }
    public Dados(String nome, int valor, String classe, String projeto, Color cor) {
        this.nome = nome;
        this.valor = valor;
        this.classe = classe;
        this.cor = cor;
        this.projeto = projeto;
    }
    String classe;
    String nome;
    int valor;
    Color cor;
    String projeto;

    @Override
    public int compareTo(Dados o) {
        int c = this.nome.compareTo(o.nome);
        if(c != 0){
            return c;
        }else{
            c = this.classe.compareTo(o.classe);
            return c;
        }
    }
}
