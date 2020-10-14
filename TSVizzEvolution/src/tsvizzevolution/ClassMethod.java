package tsvizzevolution;

import java.util.ArrayList;
import java.util.List;

public class ClassMethod {

    public String classe;
    public List<String> metodos;
    public String testSmell;

    public ClassMethod(String classe) {
        this.classe = classe;
        this.metodos = new ArrayList();
    }

    public ClassMethod(String classe, String testSmell) {
        this.classe = classe;
        this.testSmell = testSmell;
        this.metodos = new ArrayList();
    }

    public void addMethods(String metodo){
        for(int i = 0; i < metodos.size(); i++){
            if (metodo.equals(metodos.get(i))){
                return;
            }
        }
        metodos.add(metodo);
    }

    @Override
    public String toString() {
        return "classe='" + classe + '\'' +
                ", metodos=" + metodos;
    }
}
