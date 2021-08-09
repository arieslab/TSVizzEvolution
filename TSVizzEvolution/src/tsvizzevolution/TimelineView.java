package tsvizzevolution;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimelineView {

    public JPanel classe;

    public int criaRetangulos(JPanel painel, String filtro, String fileName1, String fileName2, int tam, JTextField txtFilePathMethod, JTextField txtFilePathMethod2, JPanel classe){
        this.classe = classe;
        JLabel versao1 = new JLabel ("V1");
        versao1.setFont(new Font("Tahoma", Font.PLAIN, 22));
        painel.add(versao1);

        JPanel pacote = new JPanel();
        pacote.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pacote.setBackground(Configurations.corPacote); //seta a cor de fundo
        pacote.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaPacote, Configurations.larguraBorda)); // seta a borda
        ToolTipManager.sharedInstance().setInitialDelay(500);//aparecerá logo que passe 0,5 segundos
        painel.add(pacote);

        JLabel versao2 = new JLabel ("V2");
        versao2.setFont(new Font("Tahoma", Font.PLAIN, 22));
        painel.add(versao2);

        JPanel pacote2 = new JPanel();
        pacote2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pacote2.setBackground(Configurations.corPacote); //seta a cor de fundo
        pacote2.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaPacote, Configurations.larguraBorda)); // seta a borda
        ToolTipManager.sharedInstance().setInitialDelay(500);//aparecerá logo que passe 0,5 segundos

        painel.add(pacote2);

        //txtFilePathMethod.setText("C:\\Users\\T-GAMER\\IdeaProjects\\GraphTwoVersions\\src\\tsvizzevolution\\all_report_by_testsmells.csv");
        java.util.List<ClassMethod> l1 = CriaListaDeMetodos(txtFilePathMethod.getText());
        java.util.List<ClassMethod> l2 = CriaListaDeMetodos(txtFilePathMethod2.getText());
        java.util.List<Data> dados1 = retornaDados(fileName1, filtro);
        List<Data> dados2 = retornaDados(fileName2, filtro);
        arrumaDados(dados1, dados2);
        int tamanho = constroiBlocos(dados1, filtro, pacote, l1);
        tamanho = constroiBlocos(dados2, filtro, pacote2, l2);

        LegendTimeline t = new LegendTimeline();
        LegendTimeline.main(null);

        return tamanho;

    }

    private List<ClassMethod> CriaListaDeMetodos(String path){
        List<ClassMethod> listaMetodosClasse1 = new ArrayList<>();
        try {
            List listaMetodos = new ArrayList();
            String linha = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                listaMetodos.add(dados);
            }

            for (int i = 0; i < listaMetodos.size(); i++) {
                boolean tem = false;
                String[] dado_linha = (String[]) listaMetodos.get(i);
                for (ClassMethod obj : listaMetodosClasse1) {
                    if (dado_linha[1].equals(obj.classe) && dado_linha[7].equals(obj.testSmell)) {
                        tem = true;
                    }
                }
                if (tem == false) {
                    listaMetodosClasse1.add(new ClassMethod(dado_linha[1], dado_linha[7]));
                }
            }
            for (ClassMethod obj : listaMetodosClasse1) {
                for (int i = 0; i < listaMetodos.size(); i++) {
                    String[] dado_linha = (String[]) listaMetodos.get(i);
                    if (obj.classe.equals(dado_linha[1]) && obj.testSmell.equals(dado_linha[7])) {
                        String begin;
                        String end;
                        try {
                            begin = dado_linha[9];
                        }catch (Exception e){
                            begin = "0";
                        }
                        try {
                            end = dado_linha[10];
                        }catch (Exception e){
                            end = "0";
                        }
                        obj.addMethods(new MethodData(dado_linha[8], begin, end));
                    }
                }
            }
            return listaMetodosClasse1;
        } catch (Exception e) {

        }
        return listaMetodosClasse1;
    }

    public static List<Data> retornaDados(String file, String filtro){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String linha = null;

            List listaDeLinhasInt = new ArrayList();
            List listaDeLinhas = new ArrayList();
            String cabecalho;

            cabecalho = reader.readLine();
            String[] cabecalhoLista = cabecalho.split(";");
            if (cabecalho != null) {
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(";");
                    listaDeLinhas.add(dados);
                    int[] valorInteiros = new int[dados.length];

                    for (int i = 0; i < dados.length; i++) {
                        valorInteiros[i] = converteInteiro(String.valueOf(dados[i]));
                    }
                    listaDeLinhasInt.add(valorInteiros);
                }

            }
            List<Data> resultado_final = new ArrayList<>();
            if (filtro.equals("Project")) {

                int coluna = 10;

                for (int i = 10; i < cabecalhoLista.length; i++) {
                    int soma = 0;
                    String nome_projeto = "";
                    for (int j = 0; j < listaDeLinhas.size(); j++) {
                        int[] linha_int = (int[]) listaDeLinhasInt.get(j);
                        String[] linha_analisada = (String[]) listaDeLinhas.get(j);
                        soma += linha_int[coluna];
                        nome_projeto = linha_analisada[5];
                    }
                    resultado_final.add(new Data(cabecalhoLista[i], soma, "", nome_projeto));
                    coluna += 1;

                }
            }
            if (filtro.equals("All Test Classes") || filtro.equals("Methods")){
                List<String> classes = new ArrayList<>();
                int coluna = 6;
                for (int i = 0; i < listaDeLinhas.size(); i++){
                    String[] linha_analisada = (String[]) listaDeLinhas.get(i);
                    String classe = linha_analisada[coluna];
                    boolean flag = false;
                    for (int j = 0; j < classes.size(); j++){
                        if (classes.get(j).equals(classe)){
                            flag = true;
                        }
                    }
                    if (!flag){
                        classes.add(classe);
                    }
                }
                for (int i = 0; i < classes.size(); i ++){
                    String classe_analisada = classes.get(i);
                    coluna = 10;
                    for (int j = 10; j < cabecalhoLista.length; j++) {
                        int soma = 0;
                        String nome_projeto = "";
                        for (int k = 0; k < listaDeLinhas.size(); k++) {
                            String[] linha_analisada = (String[]) listaDeLinhas.get(k);
                            int[] linha_int = (int[]) listaDeLinhasInt.get(k);
                            if (linha_analisada[6].equals(classe_analisada)){
                                soma += linha_int[coluna];
                                nome_projeto = linha_analisada[5];
                            }
                        }
                        resultado_final.add(new Data(cabecalhoLista[j], soma, classe_analisada, nome_projeto));
                        coluna += 1;
                    }
                }
            }
            return resultado_final;
        }catch (Exception e){
            return null;
        }
    }

    public static int converteInteiro(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void arrumaDados(List<Data> dados1, List<Data> dados2){
        for (int i = 0; i < dados1.size(); i++){
            if (dados1.get(i).valor == 0){
                boolean achou = false;
                int j_achado = -1;
                for (int j = 0; j < dados2.size(); j++){
                    if (dados2.get(j).nome.equals(dados1.get(i).nome) && dados2.get(j).classe.equals(dados1.get(i).classe)){
                        achou = true;
                        j_achado = j;
                        break;
                    }
                }
                if (achou){
                    if (dados2.get(j_achado).valor == 0){
                        dados2.remove(dados2.get(j_achado));
                        dados1.remove(dados1.get(i));
                        i--;
                    }
                }else{
                    dados1.remove(dados1.get(i));
                    i--;
                }
            }
        }
        for (int i = 0; i < dados2.size(); i++){
            if (dados2.get(i).valor == 0){
                boolean achou = false;
                int j_achado = -1;
                for (int j = 0; j < dados1.size(); j++){
                    if (dados1.get(j).nome.equals(dados2.get(i).nome) && dados1.get(j).classe.equals(dados2.get(i).classe)){
                        achou = true;
                        j_achado = j;
                        break;
                    }
                }
                if (achou){
                    if (dados1.get(j_achado).valor == 0){
                        dados1.remove(dados1.get(j_achado));
                        dados2.remove(dados2.get(i));
                        i--;
                    }
                }else{
                    dados2.remove(dados2.get(i));
                    i--;
                }
            }
        }
        for (int i =0; i < dados1.size(); i++){
            boolean tem = false;
            for (int j = 0; j < dados2.size(); j++){
                if (dados1.get(i).nome.equals(dados2.get(j).nome) && dados1.get(i).classe.equals(dados2.get(j).classe)){
                    tem = true;
                    if (dados1.get(i).valor > 0){
                        dados1.get(i).cor = Color.green;
                    }else{
                        dados1.get(i).cor = Color.gray;
                    }
                    if (dados1.get(i).valor > 0) {
                        dados2.get(j).cor = Color.YELLOW;
                        if (dados1.get(i).valor > dados2.get(j).valor) {
                            if(dados2.get(j).valor > 0){
                                dados2.get(j).cor = Color.BLUE;
                            }else{
                                dados2.get(j).cor = Color.gray;
                            }
                        }
                        if (dados1.get(i).valor < dados2.get(j).valor) {
                            if(dados2.get(j).valor > 0){
                                dados2.get(j).cor = Color.red;
                            }else{
                                dados2.get(j).cor = Color.gray;
                            }
                        }
                    }else{
                        dados2.get(j).cor = Color.GREEN;
                    }
                }
            }
            if (!tem){
                dados2.add(new Data(dados1.get(i).nome, 0, dados1.get(i).classe, dados1.get(i).projeto, Color.GRAY));
                dados1.get(i).cor = Color.green;
            }
        }
        for (int i =0; i < dados2.size(); i++){
            boolean tem = false;
            for (int j = 0; j < dados1.size(); j++){
                if (dados2.get(i).nome.equals(dados1.get(j).nome) && dados2.get(i).classe.equals(dados1.get(j).classe)){
                    tem = true;
                }
            }
            if (!tem){
                dados1.add(new Data(dados2.get(i).nome, 0, dados2.get(i).classe, dados1.get(i).projeto, Color.GRAY));
                dados2.get(i).cor = Color.green;
            }
        }
        for (int i = 0; i < dados1.size(); i++){
            dados1.get(i).nome = dados1.get(i).nome + "_1";
            if (dados1.get(i).classe != null){
                dados1.get(i).classe = dados1.get(i).classe + "_1";
            }
        }
        for (int i = 0; i < dados2.size(); i++){
            dados2.get(i).nome = dados2.get(i).nome + "_2";
            if (dados2.get(i).classe != null) {
                dados2.get(i).classe = dados2.get(i).classe + "_2";
            }
        }
        Collections.sort(dados1);
        Collections.sort(dados2);
    }

    private int constroiBlocos(List<Data> dados, String filtro, JPanel pacote, List<ClassMethod> l){
        List<String> analisados = new ArrayList<>();
        if (filtro.equals("Project")){
            for (int i = 0; i < dados.size(); i++){
                String projeto = dados.get(i).projeto;
                if (!foiAnalisado(analisados, projeto)){
                    classe = new JPanel();
                    classe.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    classe.setBackground(Configurations.corClasse); //seta a cor de fundo
                    classe.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaClasse, Configurations.larguraBorda)); // seta a borda
                    classe.setPreferredSize(new Dimension(204, Configurations.alturaClasse));
                    String novo_nome_segundo_vertice = projeto.substring(0, projeto.length()-2);
                    String html_classe = "<html><p><font color=\"#000000\" " + "size=\"4\" face=\"Arial\"><b> "+ filtro+": <body></b>" + novo_nome_segundo_vertice +"</font></p></html>";
                    classe.setToolTipText(html_classe);
                    pacote.add(classe);
                    JPanel espaco3 = new JPanel();
                    espaco3.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    espaco3.setBackground(Color.WHITE); //seta a cor de fundo
                    espaco3.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaPainel, Configurations.larguraBorda)); // seta a borda
                    ToolTipManager.sharedInstance().setInitialDelay(500);//aparecerá logo que passe 0,5 segundos
                    espaco3.setPreferredSize(new Dimension(800, 10 ));

                    pacote.add(espaco3);
                    for (int j = i; j < dados.size(); j++){
                        if (dados.get(j).projeto.equals(projeto)){
                            JPanel metodo = new JPanel();
                            metodo.setBackground(dados.get(j).cor);
                            metodo.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaMetodo, Configurations.larguraBorda)); // seta a borda
                            metodo.setPreferredSize(new Dimension (Configurations.larguraMetodo, Configurations.alturaMetodo)); // seta o tamanho
                            metodo.setMaximumSize(metodo.getPreferredSize());
                            metodo.setMinimumSize(metodo.getPreferredSize());
                            String novo_nome_primeiro_vertice = dados.get(j).nome.substring(0, dados.get(j).nome.length()-2);
                            String html_metodo = "<html><p><font color=\"#000000\" " + "size=\"4\" face=\"Arial\"><b> Test Smells: <body></b>" + novo_nome_primeiro_vertice + " <br>  "+ "<HtMl><b>Occurrence: <body></b>" + dados.get(j).valor + "</font></p></html>";
                            metodo.setToolTipText(html_metodo);
                            classe.add(metodo);
                        }
                    }
                    analisados.add(projeto);
                }
            }
        }else{
            for (int i = 0; i < dados.size(); i++){
                String classe_analisada = dados.get(i).classe;
                if (!foiAnalisado(analisados, classe_analisada)){
                    classe = new JPanel();
                    classe.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    classe.setBackground(Configurations.corClasse); //seta a cor de fundo
                    classe.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaClasse, Configurations.larguraBorda)); // seta a borda
                    classe.setPreferredSize(new Dimension(204, Configurations.alturaClasse));
                    String novo_nome_segundo_vertice = classe_analisada.substring(0, classe_analisada.length()-2);
                    String html_classe = "<html><p><font color=\"#000000\" " + "size=\"4\" face=\"Arial\"><b> Test Class: <body></b>" + novo_nome_segundo_vertice +"</font></p></html>";
                    classe.setToolTipText(html_classe);
                    pacote.add(classe);
                    for (int j = i; j < dados.size(); j++){
                        if (dados.get(j).classe.equals(classe_analisada)){
                            JPanel metodo = new JPanel();
                            metodo.setBackground(dados.get(j).cor);
                            metodo.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaMetodo, Configurations.larguraBorda)); // seta a borda
                            metodo.setPreferredSize(new Dimension (Configurations.larguraMetodo, Configurations.alturaMetodo)); // seta o tamanho
                            metodo.setMaximumSize(metodo.getPreferredSize());
                            metodo.setMinimumSize(metodo.getPreferredSize());
                            String novo_nome_primeiro_vertice = dados.get(j).nome.substring(0, dados.get(j).nome.length()-2);
                            String html_metodo = "<html><p><font color=\"#000000\" " + "size=\"4\" face=\"Arial\"><b> Test Smells: <body></b>" + novo_nome_primeiro_vertice + " <br>  "+ "<HtMl><b>Occurrence: <body></b>" + dados.get(j).valor +" <br>  "+  "<HtMl><b>Test Class: <body></b>" + novo_nome_segundo_vertice + "</font></p></html>";
                            if(filtro.equals("Methods")){
                                for(ClassMethod obj: l){
                                    if(obj.testSmell.equals(novo_nome_primeiro_vertice) && obj.classe.equals(novo_nome_segundo_vertice)){
                                        html_metodo = "<html><p><font color=\"#000000\" " + "size=\"4\" face=\"Arial\"><b> Test Smells: <body></b>" + novo_nome_primeiro_vertice + " <br>  "+ "<HtMl><b>Occurrence: <body></b>" + dados.get(j).valor +" <br>  " +  "<HtMl><b>Test Class: <body></b>" + novo_nome_segundo_vertice +  "<br> <HtMl><b>Metodos: <body></b>" + obj.metodos +"</font></p></html>";
                                    }
                                }
                            }
                            metodo.setToolTipText(html_metodo);
                            classe.add(metodo);
                        }
                    }
                    analisados.add(classe_analisada);
                }
            }
        }
        return analisados.size();
    }

    private boolean foiAnalisado(List<String> analisados, String classe){
        for (String analisado : analisados) {
            if (analisado.equals(classe)) {
                return true;
            }
        }
        return false;
    }

}
