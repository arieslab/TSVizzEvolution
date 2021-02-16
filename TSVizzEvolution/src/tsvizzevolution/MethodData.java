package tsvizzevolution;

public class MethodData {
    public String metodo;
    public String begin;
    public String end;

    public MethodData(String metodo, String begin, String end) {
        this.metodo = metodo;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return  metodo + " " + begin + " " + end;
    }
}
