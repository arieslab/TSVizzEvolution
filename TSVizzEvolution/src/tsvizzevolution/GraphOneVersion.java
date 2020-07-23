package tsvizzevolution;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;

public class GraphOneVersion extends javax.swing.JFrame {
    private JButton btnChooseFileSearch;
    private JButton btnVisualize;
    private JComboBox<String> cbLevel;
    private JComboBox<String> cbClass;
    private JComboBox<String> cbTestSmells;
    private JComboBox<String> cbAuthor;
    private JLabel lblSelectCsv;
    private JLabel lblLevel;
    private JLabel lblSelectClass;
    private JLabel lblSelectTestSmells;
    private JLabel lblSelectAuthor;
    private JPanel pnlClass;
    private JPanel pnlTestSmells;
    private JPanel pnlAuthor;
    private JPanel pnlGraph;
    private JTextField txtFilePathDefault;
    private static final String VIRGULA = ",";
    private static String nomeDoArquivo;

    public static int converteInteiro(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public GraphOneVersion() throws IOException {
		setTitle("TSVizzEvolution");
		setBounds(100, 100, 710, 510);
        initComponents();
        pnlClass.setVisible(false);
        pnlTestSmells.setVisible(false);
        pnlAuthor.setVisible(false);   

		
        cbLevel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getItem().equals("A Specific Test Smells")) {
                    pnlTestSmells.setVisible(true);
                    pnlClass.setVisible(false);
                    pnlAuthor.setVisible(false);
                } else if (event.getItem().equals("A Specific Test Class")) {
                    pnlClass.setVisible(true);
                    pnlTestSmells.setVisible(false);
                    pnlAuthor.setVisible(false);
                } else if (event.getItem().equals("Author")) {
                    pnlClass.setVisible(false);
                    pnlTestSmells.setVisible(true);
                    pnlAuthor.setVisible(true);
                } else {
                    pnlClass.setVisible(false);
                    pnlTestSmells.setVisible(false);
                    pnlAuthor.setVisible(false);

                }
            }
        });

    }


    private void btnChooseFileSearchActionPerformed(java.awt.event.ActionEvent evt) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(GraphOneVersion.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtFilePathDefault.setText(file.getPath());
            nomeDoArquivo = file.getName();
        }
    }
    
    
    private void txtFilePathDefaultActionPerformed(java.awt.event.ActionEvent evt){
    	String[] b = null;
        String[] a = null;
        String[] c = null;

		try {
			a = carrega_lista_linhas(txtFilePathDefault.getText());
			b = carrega_lista_cabecalho(txtFilePathDefault.getText());
			c = carrega_lista_autor(txtFilePathDefault.getText());

		} catch (IOException e) {

		}
		cbClass.setModel(new javax.swing.DefaultComboBoxModel<>(a));
        cbTestSmells.setModel(new javax.swing.DefaultComboBoxModel<>(b));
        cbAuthor.setModel(new javax.swing.DefaultComboBoxModel<>(c));

    }
    
    private void btnGerarGrafoActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
            Graph graph1 = new MultiGraph("TSVizzEvolution");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePathDefault.getText())));
            String linha = null;
            String linha2 = null;

            List listaClassesInt = new ArrayList();
            List listaClasses = new ArrayList();
            
            List listaDeAutorInt = new ArrayList();
            List listaDeAutor = new ArrayList();
            
            String cabecalho;

            cabecalho = reader.readLine();

            String[] listaTestSmells = cabecalho.split(VIRGULA);
            int y = 0;
            for (int i = 10; i < listaTestSmells.length; i++) {
                graph1.addNode(listaTestSmells[i]);
                Node n = graph1.getNode(listaTestSmells[i]);
                n.setAttribute("ui.label", listaTestSmells[i]);
                n.addAttribute("ui.class", "quadradoTS");

                y = y + 500;
                n.setAttribute("x", 200);
                n.setAttribute("y", y);
               // n.setAttribute("layout.weight", 10);
                n.setAttribute("edges","layout.weight:4");

            }
            if (cabecalho != null) {
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(VIRGULA);
                    listaClasses.add(dados);
                    int[] valorInteiros = new int[dados.length];

                    for (int i = 0; i < dados.length; i++) {
                        valorInteiros[i] = converteInteiro(String.valueOf(dados[i]));
                    }
                    listaClassesInt.add(valorInteiros);
                }
                
            }
            String selecionado = (String) cbLevel.getSelectedItem();
            int coluna = 0;
            if (selecionado.equals("Project")) {
                coluna = 5;
            }else {
                coluna = 6;
            }
            try {
            	 if (selecionado.equals("Project") || selecionado.equals("All Test Classes")) {
                     CriaGrafoCompleto(listaClassesInt, listaClasses, listaTestSmells, graph1, coluna, 1, txtFilePathDefault.getText(), selecionado);
                } else {
                	String filtro = "";
                	if (selecionado.equals("A Specific Test Class")) {
                		filtro = (String) cbClass.getSelectedItem();
                        CriaGrafoParcial(listaClassesInt, listaClasses, listaTestSmells, graph1, filtro, coluna, txtFilePathDefault.getText());
                	}else if (selecionado.equals("Author")){
                	    filtro = (String) cbTestSmells.getSelectedItem();
                	    String filtroAutor = (String) cbAuthor.getSelectedItem();
                        CriaGrafoParcialAutor(listaClassesInt, listaClasses, listaTestSmells, graph1, filtro, filtroAutor, coluna, txtFilePathDefault.getText());
                	}else{
                		filtro = (String) cbTestSmells.getSelectedItem();
                        CriaGrafoParcial(listaClassesInt, listaClasses, listaTestSmells, graph1, filtro, coluna, txtFilePathDefault.getText());
                	}
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            String path = System.getProperty("user.dir").replace('\\', '/');
            graph1.addAttribute("ui.stylesheet", "url('" + path + "/src/tsvizzevolution/Config.css')");
            if (graph1.getNodeCount() == 0){
                String msg = "";
            	if (selecionado.equals("Author")) {
                    msg = "<html>The combination Test Smells x Author does not exist!";
                }
                if (selecionado.equals("A Specific Test Smells")) {
                    msg = "<html>The selected Test Smells has no occurrences in the selected csv file!";
                }
                JOptionPane optionPane = new JOptionPane();
                optionPane.setMessage(msg);
                optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                JDialog dialog = optionPane.createDialog(null, "Warning");
                dialog.setVisible(true);
                
            }else {
            	Viewer v = graph1.display();
            	v.disableAutoLayout();
            }
        } catch (IOException ex) {
            Logger.getLogger(GraphOneVersion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void CriaGrafoCompleto(List listaClassesInt, List listaClasses, String[] cabecalho, Graph graph1, int coluna, int flag, String file, String filtro) throws IOException {
        int y = 0;
        for (int i = 0; i < listaClassesInt.size(); i++) {
            int[] linhaInt = (int[]) listaClassesInt.get(i);
            String[] linha = (String[]) listaClasses.get(i);
            try {
                graph1.addNode(linha[coluna]);
            } catch (Exception e) {
            }
            Node n1 = graph1.getNode(linha[coluna]);
            n1.setAttribute("ui.label", linha[coluna]);
            //n1.addAttribute("ui.style", "shape:circle;");
            if (filtro.equals("Project"))
                n1.addAttribute("ui.class", "projeto");

            y = y + 500;
            n1.setAttribute("x", 1000);
            n1.setAttribute("y", y);
            //n1.setAttribute("layout.weight", 10);
            n1.setAttribute("edges","layout.weight:4");

            for (int j = 10; j < linhaInt.length; j++) {
                if (linhaInt[j] != 0) {
                    try {
                        graph1.addEdge(cabecalho[j] + " " + linha[coluna], cabecalho[j], linha[coluna]);
                        Edge e = graph1.getEdge(cabecalho[j] + " " + linha[coluna]);
                        int valor = retornaDadosDoisNos(cabecalho[j], linha[coluna], file, filtro);
                        e.setAttribute("ui.label", valor);
                    } catch (Exception e) {
                    }
                }
            }
        }
        boolean stop = false;
        while (!stop) {
            boolean Flag = false;
            for (int i = 0; i < graph1.getNodeCount(); i++) {
                Node n1 = graph1.getNode(i);
                if (n1.getDegree() == 0) {
                    Flag = true;
                    graph1.removeNode(n1);
                    break;
                }
            }
            if (!Flag) {
                stop = true;
            }
        }
    }

    private static void CriaGrafoParcial(List listaClassesInt, List listaClasses, String[] cabecalho, Graph graph1, String nome, int coluna, String file) throws IOException {
        int y = 0;
        for (int i = 0; i < listaClassesInt.size(); i++) {
            int[] linhaInt = (int[]) listaClassesInt.get(i);
            String[] linha = (String[]) listaClasses.get(i);
            try {
                graph1.addNode(linha[coluna]);
            } catch (Exception e) {
            }
            Node n1 = graph1.getNode(linha[coluna]);
            n1.setAttribute("ui.label", linha[coluna]);
            y = y + 500;
            n1.setAttribute("x", 1000);
            n1.setAttribute("y", y);
           // n1.setAttribute("layout.weight", 10);
            n1.setAttribute("edges","layout.weight:4");

            for (int j = 10; j < linhaInt.length; j++) {
                if (linhaInt[j] != 0) {
                    if (nome.equals(linha[coluna]) || nome.equals(cabecalho[j])) {
                        try {
                            graph1.addEdge(cabecalho[j] + " " + linha[coluna], cabecalho[j], linha[coluna]);
                            Edge e = graph1.getEdge(cabecalho[j] + " " + linha[coluna]);
                            int valor = retornaDadosDoisNos(cabecalho[j], linha[coluna], file, "All Test Classes");
                            e.setAttribute("ui.label", valor);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        boolean stop = false;
        while (!stop) {
            boolean Flag = false;
            for (int i = 0; i < graph1.getNodeCount(); i++) {
                Node n1 = graph1.getNode(i);
                if (n1.getDegree() == 0) {
                    Flag = true;
                    graph1.removeNode(n1);
                    break;
                }
            }
            if (!Flag) {
                stop = true;
            }
        }
    }

    private static void CriaGrafoParcialAutor(List listaClassesInt, List listaClasses, String[] cabecalho, Graph graph1, String nome, String nomeAutor, int coluna, String file) throws IOException {
        int y = 0;
        int colunaAutor = 1;
        if (nomeAutor.equals("All")){
            ArrayList<String> nomeAutores = new ArrayList<>();
            for (int i = 0; i < listaClasses.size(); i++){
                String[] linha = (String[]) listaClasses.get(i);
                if (!nomeAutores.contains(linha[1])){
                    nomeAutores.add(linha[1]);
                }
            }
            for (int x = 0; x < nomeAutores.size(); x++){
                nomeAutor = nomeAutores.get(x);
                graph1.addNode(nomeAutor);
                Node autor = graph1.getNode(nomeAutor);
                autor.setAttribute("ui.label", nomeAutor);
                autor.addAttribute("ui.class", "boneco");
                autor.setAttribute("x", -1000);
                autor.setAttribute("y", y);

                for (int i = 0; i < listaClassesInt.size(); i++) {
                    int[] linhaInt = (int[]) listaClassesInt.get(i);
                    String[] linha = (String[]) listaClasses.get(i);
                    try {
                        graph1.addNode(linha[coluna]);
                    } catch (Exception e) {
                    }
                    Node n1 = graph1.getNode(linha[coluna]);
                    n1.setAttribute("ui.label", linha[coluna]);

                    y = y + 500;
                    n1.setAttribute("x", 1000);
                    n1.setAttribute("y", y);
                    n1.setAttribute("layout.weight", 10);
                    n1.setAttribute("edges","layout.weight:4");

                    for (int j = 10; j < linhaInt.length; j++) {
                        if (linhaInt[j] != 0) {
                            if (nomeAutor.equals(linha[colunaAutor]) || nome.equals(cabecalho[j])) {
                                if (nome.equals(cabecalho[j])) {
                                    try {
                                        graph1.addEdge(cabecalho[j] + " " + linha[colunaAutor], cabecalho[j], linha[colunaAutor]);
                                        Edge e = graph1.getEdge(cabecalho[j] + " " + linha[colunaAutor]);
                                        int valor = retornaDadosAutorMetodo(linha[colunaAutor], cabecalho[j], file);
                                        e.setAttribute("ui.label", valor);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    }

                }

                boolean stop = false;
                while (!stop) {
                    boolean Flag = false;
                    for (int i = 0; i < graph1.getNodeCount(); i++) {
                        Node n1 = graph1.getNode(i);
                        if (n1.getDegree() == 0) {
                            Flag = true;
                            graph1.removeNode(n1);
                            break;
                        }
                    }
                    if (!Flag) {
                        stop = true;
                    }
                }
            }
        }else {
            graph1.addNode(nomeAutor);
            Node autor = graph1.getNode(nomeAutor);
            autor.setAttribute("ui.label", nomeAutor);
            autor.addAttribute("ui.class", "boneco");
            autor.setAttribute("x", -1000);
            autor.setAttribute("y", 0);

            for (int i = 0; i < listaClassesInt.size(); i++) {
                int[] linhaInt = (int[]) listaClassesInt.get(i);
                String[] linha = (String[]) listaClasses.get(i);
                try {
                    graph1.addNode(linha[coluna]);
                } catch (Exception e) {
                }
                Node n1 = graph1.getNode(linha[coluna]);
                n1.setAttribute("ui.label", linha[coluna]);

                y = y + 500;
                n1.setAttribute("x", 1000);
                n1.setAttribute("y", y);
                for (int j = 10; j < linhaInt.length; j++) {
                    if (linhaInt[j] != 0) {
                        if (nome.equals(linha[coluna]) || nome.equals(cabecalho[j]) && linha[colunaAutor].equals(nomeAutor)) {
                            try {
                                graph1.addEdge(cabecalho[j] + " " + linha[coluna], cabecalho[j], linha[coluna]);
                                Edge e = graph1.getEdge(cabecalho[j] + " " + linha[coluna]);
                                int valor = retornaDadosDoisNos(cabecalho[j], linha[coluna], file, "All Test Classes");
                                e.setAttribute("ui.label", valor);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                for (int j = 10; j < linhaInt.length; j++) {
                    if (linhaInt[j] != 0) {
                        if (nomeAutor.equals(linha[colunaAutor]) || nome.equals(cabecalho[j])) {
                            if (nome.equals(cabecalho[j])) {
                                try {
                                    graph1.addEdge(cabecalho[j] + " " + linha[colunaAutor], cabecalho[j], linha[colunaAutor]);
                                    Edge e = graph1.getEdge(cabecalho[j] + " " + linha[colunaAutor]);
                                    int valor = retornaDadosAutorMetodo(linha[colunaAutor], cabecalho[j], file);
                                    e.setAttribute("ui.label", valor);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }

            }

            boolean stop = false;
            while (!stop) {
                boolean Flag = false;
                for (int i = 0; i < graph1.getNodeCount(); i++) {
                    Node n1 = graph1.getNode(i);
                    if (n1.getDegree() == 0) {
                        Flag = true;
                        graph1.removeNode(n1);
                        break;
                    }
                }
                if (!Flag) {
                    stop = true;
                }
            }
        }
    }

    public static int retornaDadosAutorMetodo(String autor, String metodo, String file){
        List<Dados> l = retornaDadosAutores(file);
        for (int i = 0; i < l.size(); i++) {
            Dados d = l.get(i);
            if (d.autor.equals(autor) && d.nome.equals(metodo)) {
                return d.valor;
            }
        }
        return 0;
    }

    public static List<Dados> retornaDadosAutores(String file){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String linha = null;

            List listaDeLinhasInt = new ArrayList();
            List listaDeLinhas = new ArrayList();
            String cabecalho;

            cabecalho = reader.readLine();
            String[] cabecalhoLista = cabecalho.split(VIRGULA);
            if (cabecalho != null) {
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(VIRGULA);
                    listaDeLinhas.add(dados);
                    int[] valorInteiros = new int[dados.length];

                    for (int i = 0; i < dados.length; i++) {
                        valorInteiros[i] = converteInteiro(String.valueOf(dados[i]));
                    }
                    listaDeLinhasInt.add(valorInteiros);
                }

            }
            List<Dados> resultado = new ArrayList<>();
            List<String> autores = new ArrayList<>();
            int coluna = 1;
            for (int i = 0; i < listaDeLinhas.size(); i++){
                String[] linha_analisada = (String[]) listaDeLinhas.get(i);
                String autor = linha_analisada[coluna];
                boolean flag = false;
                for (int j = 0; j < autores.size(); j++){
                    if (autores.get(j).equals(autor)){
                        flag = true;
                    }
                }
                if (!flag){
                    autores.add(autor);
                }
            }
            for (int i = 0; i < autores.size(); i ++){
                String autor = autores.get(i);
                coluna = 10;
                for (int j = 10; j < cabecalhoLista.length; j++) {
                    int soma = 0;
                    for (int k = 0; k < listaDeLinhas.size(); k++) {
                        String[] linha_analisada = (String[]) listaDeLinhas.get(k);
                        int[] linha_int = (int[]) listaDeLinhasInt.get(k);
                        if (linha_analisada[1].equals(autor)){
                            soma += linha_int[coluna];
                        }
                    }
                    resultado.add(new Dados(cabecalhoLista[j], soma, autor));
                    coluna += 1;
                }
            }
            return resultado;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static int retornaDadosDoisNos(String a, String b, String file, String filtro){
        if (filtro.equals("Project")) {
            List<Dados> l = retornaDados(file, filtro);
            for (int i = 0; i < l.size(); i++) {
                Dados d = l.get(i);
                if (d.nome.equals(a) && d.projeto.equals(b)) {
                    return d.valor;
                }
            }
            return 0;
        }else{
            List<Dados> l = retornaDados(file, filtro);
            for (int i = 0; i < l.size(); i++) {
                Dados d = l.get(i);
                if (d.nome.equals(a) && d.classe.equals(b)) {
                    return d.valor;
                }
            }
            return 0;
        }
    }

    public static List<Dados> retornaDados(String file, String filtro){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String linha = null;

            List listaClassesInt = new ArrayList();
            List listaClasses = new ArrayList();
            String cabecalho;

            cabecalho = reader.readLine();
            String[] listaTestSmells = cabecalho.split(VIRGULA);
            if (cabecalho != null) {
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(VIRGULA);
                    listaClasses.add(dados);
                    int[] valorInteiros = new int[dados.length];

                    for (int i = 0; i < dados.length; i++) {
                        valorInteiros[i] = converteInteiro(String.valueOf(dados[i]));
                    }
                    listaClassesInt.add(valorInteiros);
                }

            }
            List<Dados> resultado_final = new ArrayList<>();
            if (filtro.equals("Project")) {

                int coluna = 10;

                for (int i = 10; i < listaTestSmells.length; i++) {
                    int soma = 0;
                    String nome_projeto = "";
                    for (int j = 0; j < listaClasses.size(); j++) {
                        int[] linha_int = (int[]) listaClassesInt.get(j);
                        String[] linha_analisada = (String[]) listaClasses.get(j);
                        soma += linha_int[coluna];
                        nome_projeto = linha_analisada[5];
                    }
                    resultado_final.add(new Dados(listaTestSmells[i], soma, "", nome_projeto));
                    coluna += 1;

                }
            }
            if (filtro.equals("All Test Classes")){
                List<String> classes = new ArrayList<>();
                int coluna = 6;
                for (int i = 0; i < listaClasses.size(); i++){
                    String[] linha_analisada = (String[]) listaClasses.get(i);
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
                    for (int j = 10; j < listaTestSmells.length; j++) {
                        int soma = 0;
                        String nome_projeto = "";
                        for (int k = 0; k < listaClasses.size(); k++) {
                            String[] linha_analisada = (String[]) listaClasses.get(k);
                            int[] linha_int = (int[]) listaClassesInt.get(k);
                            if (linha_analisada[6].equals(classe_analisada)){
                                soma += linha_int[coluna];
                                nome_projeto = linha_analisada[5];
                            }
                        }
                        resultado_final.add(new Dados(listaTestSmells[j], soma, classe_analisada, nome_projeto));
                        coluna += 1;
                    }
                }
            }
            return resultado_final;
        }catch (Exception e){
            return null;
        }
    }
   
    private void cbLevelActionPerformed(java.awt.event.ActionEvent evt) {
    }
    
    public static void main(String args[]) throws IOException {
        try {
        	
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GraphOneVersion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GraphOneVersion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GraphOneVersion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GraphOneVersion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
					new GraphOneVersion().setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
    
    public static String[] carrega_lista_linhas(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String linha = null;
        List resposta = new ArrayList();
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(VIRGULA);
            String classe = dados[6];
            boolean flag = false;
            for(int i = 0; i < resposta.size(); i++){
            	if(resposta.get(i).equals(classe)){
            		flag = true;
            	}
            }
            if (flag == false){
            	resposta.add(classe);
            }
        }
        String[] resposta_final = new String[resposta.size() - 1];
        for(int i = 0; i < resposta.size() - 1; i++){
        	resposta_final[i] = (String) resposta.get(i+1);
        }
		return resposta_final;
    }
    
    public static String[] carrega_lista_cabecalho(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String cabecalho_combo;
        List respos = new ArrayList();
        cabecalho_combo = reader.readLine();
        String[] cabecalhoTest = cabecalho_combo.split(VIRGULA);
        String[] resultado = new String[cabecalhoTest.length - 10];
        for (int i = 10; i < cabecalhoTest.length; i++){
        	resultado[i - 10] = cabecalhoTest[i];
        }
		return resultado;
		
    }

    public static String[] ordenaVetorString(String[] vetor){
        String[] resultado = new String[vetor.length];
        List<String> l = new ArrayList<>();
        for (int i = 0; i < vetor.length; i++){
            l.add(vetor[i]);
        }
        Collections.sort(l);
        for (int i = 0; i < vetor.length; i++){
            resultado[i] = l.get(i);
        }
        return resultado;
    }
   
    public static String[] carrega_lista_autor(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String linha = null;
        List resposta = new ArrayList();
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(VIRGULA);
            String classe = dados[1];
            boolean flag = false;
            for(int i = 0; i < resposta.size(); i++){
            	if(resposta.get(i).equals(classe)){
            		flag = true;
            	}
            }
            if (flag == false){
            	resposta.add(classe);
            }
        }
        String[] resposta_final = new String[resposta.size()];
        for(int i = 0; i < resposta.size() - 1; i++){
        	resposta_final[i] = (String) resposta.get(i+1);
        }
        resposta_final[resposta.size() - 1] = "All";
		return resposta_final;
    }
    @SuppressWarnings("unchecked")
    
    private void initComponents() throws IOException {
        btnChooseFileSearch = new JButton();
        btnVisualize = new JButton();
        cbLevel = new JComboBox<>();
        cbClass = new JComboBox<>();
        cbTestSmells = new JComboBox<>();
        cbAuthor = new JComboBox<>();
        lblSelectCsv = new JLabel();
        lblLevel = new JLabel();
        lblSelectClass = new JLabel();
        lblSelectTestSmells = new JLabel();
        lblSelectAuthor = new JLabel();
        pnlClass = new JPanel();
        pnlTestSmells = new JPanel();
        pnlAuthor = new JPanel();
        pnlGraph = new JPanel();
        txtFilePathDefault = new javax.swing.JTextField();

        btnChooseFileSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnVisualize.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbLevel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbTestSmells.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSelectCsv.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSelectClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSelectTestSmells.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSelectAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtFilePathDefault.setFont(new Font("Tahoma", Font.PLAIN, 14));

        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
       
        lblSelectCsv.setText("Select a .csv File :");
        
        txtFilePathDefault.setText("C:\\Users\\Adriana\\Desktop\\mestrado\\software\\commons-io_testsmesll_2_1.csv");

        btnChooseFileSearch.setText("Search ...");
        btnChooseFileSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileSearchActionPerformed(evt);
            }
        });

        cbLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Project",  "All Test Classes", "A Specific Test Class", "A Specific Test Smells", "Author" }));
        cbLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLevelActionPerformed(evt);
            }
        });

        lblLevel.setText("Select the level of granularity:");
        lblSelectClass.setText("Select a Test Class:");
        lblSelectTestSmells.setText("Select a Test Smells:");
        lblSelectAuthor.setText("Select A Specific Author or All:");
        
        btnVisualize.setText("Graph View");
        btnVisualize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarGrafoActionPerformed(evt);
            }
        });

        String[] a = null;
    	String[] b = null;
        String[] c = null;

		try {
			a = carrega_lista_linhas(txtFilePathDefault.getText());
			b = carrega_lista_cabecalho(txtFilePathDefault.getText());
			c = carrega_lista_autor(txtFilePathDefault.getText());

            a = ordenaVetorString(a);
            b = ordenaVetorString(b);
            c = ordenaVetorString(c);

		} catch (IOException e) {

		}
		cbClass.setModel(new javax.swing.DefaultComboBoxModel<>(a));
        cbTestSmells.setModel(new javax.swing.DefaultComboBoxModel<>(b));
        cbAuthor.setModel(new javax.swing.DefaultComboBoxModel<>(c));
        
        javax.swing.GroupLayout pnlClassLayout = new javax.swing.GroupLayout(pnlClass);
        pnlClass.setLayout(pnlClassLayout);
        pnlClassLayout.setHorizontalGroup(
            pnlClassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClassLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlClassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlClassLayout.createSequentialGroup()
                        .addComponent(lblSelectClass)
                        .addGap(0, 0, Short.MAX_VALUE))
                  .addComponent(cbClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)//)
        // .addComponent(cbClass, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
)
                .addContainerGap())
        );
        pnlClassLayout.setVerticalGroup(
            pnlClassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClassLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSelectClass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );


        javax.swing.GroupLayout pnlTestSmellsLayout = new javax.swing.GroupLayout(pnlTestSmells);
        pnlTestSmells.setLayout(pnlTestSmellsLayout);
        pnlTestSmellsLayout.setHorizontalGroup(
            pnlTestSmellsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestSmellsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTestSmellsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTestSmellsLayout.createSequentialGroup()
                        .addComponent(lblSelectTestSmells)
                        .addGap(0, 0, Short.MAX_VALUE))
                   .addComponent(cbTestSmells, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               // .addComponent(cbTestSmells, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))

                .addContainerGap())
        );
        pnlTestSmellsLayout.setVerticalGroup(
            pnlTestSmellsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestSmellsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSelectTestSmells)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbTestSmells, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlAuthorLayout = new javax.swing.GroupLayout(pnlAuthor);
        pnlAuthor.setLayout(pnlAuthorLayout);
        pnlAuthorLayout.setHorizontalGroup(
        		pnlAuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAuthorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAuthorLayout.createSequentialGroup()
                        .addComponent(lblSelectAuthor)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbAuthor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                //.addComponent(cbAuthor, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))

                .addContainerGap())
        );
        pnlAuthorLayout.setVerticalGroup(
        		pnlAuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAuthorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSelectAuthor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlGraphLayout = new javax.swing.GroupLayout(pnlGraph);
        pnlGraph.setLayout(pnlGraphLayout);
        pnlGraphLayout.setHorizontalGroup(
            pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGraphLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbLevel, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
     				//	.addComponent(cbLevel, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)

                		.addGroup(pnlGraphLayout.createSequentialGroup()
                        .addComponent(txtFilePathDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChooseFileSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                    .addComponent(pnlClass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAuthor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlGraphLayout.createSequentialGroup()
                        .addGroup(pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSelectCsv)
                            .addComponent(lblLevel)
                            )
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addComponent(pnlTestSmells, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(pnlGraphLayout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(btnVisualize, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGraphLayout.setVerticalGroup(
            pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGraphLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblSelectCsv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFilePathDefault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChooseFileSearch))
                .addGap(18, 18, 18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addGap(18, 18, 18)
                    )
                .addGap(17, 17, 17)
                .addComponent(lblLevel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(pnlClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlTestSmells, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100,100,100)
                .addComponent(btnVisualize)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }
}


