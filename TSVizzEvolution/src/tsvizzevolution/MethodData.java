package tsvizzevolution;

public class MethodData {
    public String metodo;
    public int begin;
    public int end;

    public MethodData(String metodo, int begin, int end) {
        this.metodo = metodo;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return  metodo + " " + begin + " " + end;
    }
}
