package tsvizzevolution;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;

public class GraphOneVersion extends javax.swing.JFrame {
    private JButton btnChooseFileSearch;
    private JButton btnVisualize;
    private JButton btnUpload;
    private JButton btnSearchMethod;
    
    private JComboBox<String> cbLevel;
    private JComboBox<String> cbClass;
    private JComboBox<String> cbTestSmells;
    private JComboBox<String> cbAuthor;
    private JLabel lblSelectCsv;
    private JLabel lblLevel;
    private JLabel lblSelectClass;
    private JLabel lblSelectTestSmells;
    private JLabel lblAuthor;
    private JLabel lblSelectTheCsvMethod;

    private JPanel pnlClass;
    private JPanel pnlTestSmells;
    private JPanel pnlAuthor;
    private JPanel pnlGraph;
	private JPanel pnlUpload;
	private JPanel pnlMethod;
	private JPanel pnlbutton;
	public JPanel contentPane;

	
    private JTextField txtFilePathDefault;
    private JTextField txtFilePathMethod; 

    
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
        initComponents();
        pnlClass.setVisible(false);
        pnlTestSmells.setVisible(false);
        pnlAuthor.setVisible(false);   
        pnlUpload.setVisible(true);
        pnlMethod.setVisible(false); 
        pnlbutton.setVisible(true);
		
        cbLevel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getItem().equals("A Specific Test Smells")) {
                    pnlTestSmells.setVisible(true);
                    pnlClass.setVisible(false);
                    pnlAuthor.setVisible(false);
                    pnlMethod.setVisible(false);
                } else if (event.getItem().equals("A Specific Test Class")) {
                    pnlClass.setVisible(true);
                    pnlTestSmells.setVisible(false);
                    pnlAuthor.setVisible(false);
                    pnlMethod.setVisible(false);
                } else if (event.getItem().equals("Author")) {
                    pnlClass.setVisible(false);
                    pnlTestSmells.setVisible(true);
                    pnlAuthor.setVisible(true);
                    pnlMethod.setVisible(false);
                } else if (event.getItem().equals("Methods")) {
                    pnlClass.setVisible(true);
                    pnlTestSmells.setVisible(true);
                    pnlAuthor.setVisible(false);
                    pnlMethod.setVisible(true);
                } else {
                    pnlClass.setVisible(false);
                    pnlTestSmells.setVisible(false);
                    pnlAuthor.setVisible(false);
                    pnlMethod.setVisible(false);
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

    private void  btnSearchMethodActionPerformed(java.awt.event.ActionEvent evt) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(GraphOneVersion.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtFilePathDefault.setText(file.getPath());
            nomeDoArquivo = file.getName();
        }
    }

    
    private void btnGerarGrafoActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
            Graph graph1 = new MultiGraph("TSVizzEvolution");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePathDefault.getText())));
            String linha = null;

            List listaClassesInt = new ArrayList();
            List listaClasses = new ArrayList();
            List listaMetodos = new ArrayList();
            
            String cabecalho;

            cabecalho = reader.readLine();

            String[] listaTestSmells = cabecalho.split(VIRGULA);
            for (int i = 10; i < listaTestSmells.length; i++) {
                graph1.addNode(listaTestSmells[i]);
                Node n = graph1.getNode(listaTestSmells[i]);
                n.setAttribute("ui.label", listaTestSmells[i]);
                n.addAttribute("ui.class", "quadradoTS");
                n.addAttribute("ui.", "quadradoTS");

                double x = (Math.random() * ((1000000) + 1));
                double y = (Math.random() * ((1000000) + 1));
                n.setAttribute("x", x);
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
            List<ClassMethod> listaMetodosClasse = new ArrayList<>();
            if (selecionado.equals("Methods")){
                reader  = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePathMethod.getText())));
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(VIRGULA);
                    listaMetodos.add(dados);
                }

                for (int i = 0; i < listaMetodos.size(); i++){
                    boolean tem = false;
                    String[] dado_linha = (String[]) listaMetodos.get(i);
                    for (ClassMethod obj: listaMetodosClasse) {
                        if (dado_linha[1].equals(obj.classe)){
                            tem = true;
                        }
                    }
                    if (tem == false){
                        listaMetodosClasse.add(new ClassMethod(dado_linha[1]));
                    }
                }
                for (ClassMethod obj: listaMetodosClasse){
                    for (int i = 0; i < listaMetodos.size(); i++){
                        String[] dado_linha = (String[]) listaMetodos.get(i);
                        if (obj.classe.equals(dado_linha[1])){
                            obj.addMethods(dado_linha[8]);
                        }
                    }
                }
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
                	}else if (selecionado.equals("Methods")){
                        String testSmell = (String) cbTestSmells.getSelectedItem();
                        String classe = (String) cbClass.getSelectedItem();
                	    CriaGrafoMetodos(listaClassesInt, listaClasses, listaTestSmells, graph1, testSmell, classe, coluna, txtFilePathDefault.getText(), listaMetodosClasse);
                    }
                	else{
                		filtro = (String) cbTestSmells.getSelectedItem();
                        CriaGrafoParcial(listaClassesInt, listaClasses, listaTestSmells, graph1, filtro, coluna, txtFilePathDefault.getText());
                	}
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
           // String path = System.getProperty("user.dir").replace('\\', '/');
            //graph1.addAttribute("ui.stylesheet", "url('" + path + "/src/tsvizzevolution/Config.css')");
            graph1.addAttribute("ui.stylesheet", "url('./src/tsvizzevolution/Config.css')");

           
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
        List<Data> l = retornaDados(file, filtro);
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
            double x = (Math.random() * ((1000000) + 1) - 1000000);
            double y = (Math.random() * ((1000000) + 1) - 1000000);
            n1.setAttribute("x", x);
            n1.setAttribute("y", y);
            //n1.setAttribute("layout.weight", 10);
            n1.setAttribute("edges","layout.weight:4");
            for (int j = 10; j < linhaInt.length; j++) {
                if (linhaInt[j] != 0) {
                    try {
                        graph1.addEdge(cabecalho[j] + " " + linha[coluna], cabecalho[j], linha[coluna]);
                        Edge e = graph1.getEdge(cabecalho[j] + " " + linha[coluna]);
                        int valor = retornaDadosDoisNos(cabecalho[j], linha[coluna], file, filtro, l);
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
        List<Data> l = retornaDados(file, "All Test Classes");
        for (int i = 0; i < listaClassesInt.size(); i++) {
            int[] linhaInt = (int[]) listaClassesInt.get(i);
            String[] linha = (String[]) listaClasses.get(i);
            try {
                graph1.addNode(linha[coluna]);
            } catch (Exception e) {
            }
            Node n1 = graph1.getNode(linha[coluna]);
            n1.setAttribute("ui.label", linha[coluna]);
            double x = (Math.random() * ((1000000) + 1));
            double y = (Math.random() * ((1000000) + 1));
            n1.setAttribute("x", x);
            n1.setAttribute("y", y);
           // n1.setAttribute("layout.weight", 10);
            n1.setAttribute("edges","layout.weight:4");

            for (int j = 10; j < linhaInt.length; j++) {
                if (linhaInt[j] != 0) {
                    if (nome.equals(linha[coluna]) || nome.equals(cabecalho[j])) {
                        try{
                            graph1.addEdge(cabecalho[j] + " " + linha[coluna], cabecalho[j], linha[coluna]);
                            Edge e = graph1.getEdge(cabecalho[j] + " " + linha[coluna]);
                            int valor = retornaDadosDoisNos(cabecalho[j], linha[coluna], file, "All Test Classes", l);
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

    private static void CriaGrafoMetodos(List listaClassesInt, List listaClasses, String[] cabecalho, Graph graph1, String nome, String classe, int coluna, String file, List<ClassMethod> listaMetodosClasse) throws IOException {
        List<Data> l = retornaDados(file, "All Test Classes");
        for (int i = 0; i < listaClassesInt.size(); i++) {
            int[] linhaInt = (int[]) listaClassesInt.get(i);
            String[] linha = (String[]) listaClasses.get(i);
            try {
                graph1.addNode(linha[coluna]);
            } catch (Exception e) {
            }
            Node n1 = graph1.getNode(linha[coluna]);
            n1.setAttribute("ui.label", linha[coluna]);
            double x = (Math.random() * ((1000000) + 1));
            double y = (Math.random() * ((1000000) + 1));
            n1.setAttribute("x", x);
            n1.setAttribute("y", y);
            // n1.setAttribute("layout.weight", 10);
            n1.setAttribute("edges","layout.weight:4");

            for (int j = 10; j < linhaInt.length; j++) {
                if (linhaInt[j] != 0) {
                    if (classe.equals(linha[coluna]) && nome.equals(cabecalho[j])) {
                        try{
                            graph1.addEdge(cabecalho[j] + " " + linha[coluna], cabecalho[j], linha[coluna]);
                            Edge e = graph1.getEdge(cabecalho[j] + " " + linha[coluna]);
                            int valor = retornaDadosDoisNos(cabecalho[j], linha[coluna], file, "All Test Classes", l);
                            e.setAttribute("ui.label", valor);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        if (graph1.getEdgeCount() > 0) {
            for (ClassMethod obj : listaMetodosClasse) {
                for (String metodo : obj.metodos) {
                    try {
                        graph1.addNode(metodo);
                        Node n1 = graph1.getNode(metodo);
                        n1.setAttribute("ui.label", metodo);
                        n1.addAttribute("ui.class", "metodo");
                        double x = (Math.random() * ((1000000) + 1) + 1000000);
                        double y = (Math.random() * ((1000000) + 1) + 1000000);
                        n1.setAttribute("x", x);
                        n1.setAttribute("y", y);
                        if (obj.classe.equals(classe))
                            graph1.addEdge(metodo, obj.classe, metodo);
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

    private static void CriaGrafoParcialAutor(List listaClassesInt, List listaClasses, String[] cabecalho, Graph graph1, String nome, String nomeAutor, int coluna, String file) throws IOException {
        int colunaAutor = 1;
        List<Data> l = retornaDados(file, "All Test Classes");
        List<Data> l2 = retornaDadosAutores(file);
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
                double x1 = (Math.random() * ((1000000) + 1));
                double y = (Math.random() * ((1000000) + 1));
                autor.setAttribute("ui.label", nomeAutor);
                autor.addAttribute("ui.class", "boneco");
                autor.setAttribute("x", x1);
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

                    x1 = (Math.random() * ((1000000) + 1));
                    y = (Math.random() * ((1000000) + 1));
                    n1.setAttribute("x", x1);
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
                                        int valor = retornaDadosAutorMetodo(linha[colunaAutor], cabecalho[j], file, l2);
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
                double x1 = (Math.random() * ((1000000) + 1));
                double y = (Math.random() * ((1000000) + 1));
                n1.setAttribute("x", x1);
                n1.setAttribute("y", y);
                for (int j = 10; j < linhaInt.length; j++) {
                    if (linhaInt[j] != 0) {
                        if (nome.equals(linha[coluna]) || nome.equals(cabecalho[j]) && linha[colunaAutor].equals(nomeAutor)) {
                            try {
                                graph1.addEdge(cabecalho[j] + " " + linha[coluna], cabecalho[j], linha[coluna]);
                                Edge e = graph1.getEdge(cabecalho[j] + " " + linha[coluna]);
                                int valor = retornaDadosDoisNos(cabecalho[j], linha[coluna], file, "All Test Classes", l);
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
                                    int valor = retornaDadosAutorMetodo(linha[colunaAutor], cabecalho[j], file, l2);
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

    public static int retornaDadosAutorMetodo(String autor, String metodo, String file, List<Data> l){
        for (int i = 0; i < l.size(); i++) {
            Data d = l.get(i);
            if (d.autor.equals(autor) && d.nome.equals(metodo)) {
                return d.valor;
            }
        }
        return 0;
    }

    public static List<Data> retornaDadosAutores(String file){
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
            List<Data> resultado = new ArrayList<>();
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
                    resultado.add(new Data(cabecalhoLista[j], soma, autor));
                    coluna += 1;
                }
            }
            return resultado;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static int retornaDadosDoisNos(String a, String b, String file, String filtro, List<Data> l){
        if (filtro.equals("Project")) {
            for (int i = 0; i < l.size(); i++) {
                Data d = l.get(i);
                if (d.nome.equals(a) && d.projeto.equals(b)) {
                    return d.valor;
                }
            }
            return 0;
        }else{
            for (int i = 0; i < l.size(); i++) {
                Data d = l.get(i);
                if (d.nome.equals(a) && d.classe.equals(b)) {
                    return d.valor;
                }
            }
            return 0;
        }
    }

    public static List<Data> retornaDados(String file, String filtro){
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
            List<Data> resultado_final = new ArrayList<>();
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
                    resultado_final.add(new Data(listaTestSmells[i], soma, "", nome_projeto));
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
                        resultado_final.add(new Data(listaTestSmells[j], soma, classe_analisada, nome_projeto));
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
        Collections.sort(resposta);
        String[] resposta_final = new String[resposta.size() - 1];
        for(int i = 0; i < resposta.size() - 1; i++){
        	resposta_final[i] = (String) resposta.get(i+1);
        }
		return resposta_final;
    }
    
    public static String[] carrega_lista_cabecalho(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String cabecalho_combo;
        cabecalho_combo = reader.readLine();
        String[] cabecalhoTest = cabecalho_combo.split(VIRGULA);
        String[] resultado = new String[cabecalhoTest.length - 10];
        for (int i = 10; i < cabecalhoTest.length; i++){
        	resultado[i - 10] = cabecalhoTest[i];
        }
        List<String> aux = new ArrayList<>();
        for (int i = 0; i < resultado.length; i++){
            aux.add(resultado[i]);
        }
        Collections.sort(aux);
        for (int i = 0; i < resultado.length; i++){
            resultado[i] = aux.get(i);
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
        Collections.sort(resposta);
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
        btnUpload = new JButton();
 		btnSearchMethod = new JButton();

        cbLevel = new JComboBox<>();
        cbClass = new JComboBox<>();
        cbTestSmells = new JComboBox<>();
        cbAuthor = new JComboBox<>();
        lblSelectCsv = new JLabel();
        lblLevel = new JLabel();
        lblSelectClass = new JLabel();
        lblSelectTestSmells = new JLabel();
        lblAuthor = new JLabel();
        pnlClass = new JPanel();
        pnlTestSmells = new JPanel();
        pnlAuthor = new JPanel();
        pnlGraph = new JPanel();
        pnlUpload = new JPanel();
	    pnlMethod = new JPanel();
        pnlbutton = new JPanel();

        pnlbutton.setVisible(true);
	    JLabel lblSelectTheCsvMethod = new JLabel();
 		lblSelectTheCsvMethod.setText("Select the .csv File (By Test Smells JNose) :");
 		
 		 		txtFilePathMethod = new JTextField();
 	//	txtFilePathMethod.setText("C:\\Users\\Adriana\\Desktop\\mestrado\\software\\commons-io_testsmesll_2_6.csv");
 		
        txtFilePathDefault = new JTextField();

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
        lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtFilePathDefault.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnUpload.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtFilePathMethod.setFont(new Font("Tahoma", Font.PLAIN, 14));
 		 lblSelectTheCsvMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));
 		 btnSearchMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));
 		 
        pnlUpload.setVisible(true);
        pnlMethod.setVisible(false);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
       
        lblSelectCsv.setText("Select the .csv File :");
        
        //txtFilePathDefault.setText("C:\\Users\\Adriana\\Desktop\\mestrado\\software\\commons-io_testsmesll_2_1.csv");

        btnChooseFileSearch.setText("Search ...");
        btnChooseFileSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileSearchActionPerformed(evt);
            }
        });
        
////////
 		btnSearchMethod.setText("Search ...");
 		btnSearchMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchMethodActionPerformed(evt);
            }
        });
        cbLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Project",  "All Test Classes", "A Specific Test Class", "A Specific Test Smells", "Author", "Methods" }));
        cbLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLevelActionPerformed(evt);
            }
        });

        lblLevel.setText("Select the level of granularity:");
        lblSelectClass.setText("Select a Test Class:");
        lblSelectTestSmells.setText("Select a Test Smells:");
        lblAuthor.setText("Select A Specific Author or All:");
        
        btnVisualize.setText("Generate Graph View");
        btnVisualize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
            	final JProgressBar pr = new JProgressBar();
                pr.setStringPainted(true);
                pr.setValue(0);
                pr.setSize(new Dimension(100, 23));
                pnlbutton.add(pr);
                
            	btnGerarGrafoActionPerformed(evt);
            	
            	int i=10; 
                pr.setValue(i);
                pr.setString( "%");
                try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
        });

		btnUpload.setText("Upload Data Files");
		btnUpload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnGerarUploadActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


			     private void btnGerarUploadActionPerformed(ActionEvent evt) throws IOException {
			            String[] a = null;
			            String[] b = null;
			            String[] c = null;

//			            txtFilePathDefault.setText("C:\\Users\\T-GAMER\\IdeaProjects\\teste\\src\\tsvizzevolution\\commons-io_testsmesll_2_1.csv");
//                      txtFilePathMethod.setText("C:\\Users\\T-GAMER\\IdeaProjects\\teste\\src\\tsvizzevolution\\all_report_by_testsmells.csv");
			            a = carrega_lista_linhas(txtFilePathDefault.getText());
			            b = carrega_lista_cabecalho(txtFilePathDefault.getText());
			            c = carrega_lista_autor(txtFilePathDefault.getText());

			            cbAuthor.setModel(new DefaultComboBoxModel<>(c));
			            cbClass.setModel(new DefaultComboBoxModel<>(a));
			            cbTestSmells.setModel(new DefaultComboBoxModel<>(b));
			    
			}
   });
        
		
       /* String[] a = null;
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
        */

        GroupLayout gl_pnlMethod = new GroupLayout(pnlMethod);
        gl_pnlMethod.setHorizontalGroup(
        	gl_pnlMethod.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlMethod.createSequentialGroup()
        			.addGroup(gl_pnlMethod.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblSelectTheCsvMethod)
        				.addGroup(gl_pnlMethod.createSequentialGroup()
        					.addComponent(txtFilePathMethod, GroupLayout.PREFERRED_SIZE, 534, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(btnSearchMethod, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(21, Short.MAX_VALUE))
        );
        gl_pnlMethod.setVerticalGroup(
        	gl_pnlMethod.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlMethod.createSequentialGroup()
        			.addGap(9)
        			.addComponent(lblSelectTheCsvMethod)
        			.addGap(18)
        			.addGroup(gl_pnlMethod.createParallelGroup(Alignment.BASELINE)
        				.addComponent(txtFilePathMethod, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        				.addComponent(btnSearchMethod, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(14, Short.MAX_VALUE))
        );
        pnlMethod.setLayout(gl_pnlMethod);
        
        pnlbutton = new JPanel();
        
       

        javax.swing.GroupLayout pnlGraphLayout = new javax.swing.GroupLayout(pnlGraph);
        pnlGraphLayout.setHorizontalGroup(
        	pnlGraphLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(pnlGraphLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(pnlGraphLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(pnlGraphLayout.createSequentialGroup()
        					.addGroup(pnlGraphLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(pnlGraphLayout.createSequentialGroup()
        							.addGroup(pnlGraphLayout.createParallelGroup(Alignment.LEADING)
        								.addComponent(lblSelectCsv)
        								.addComponent(txtFilePathDefault, GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE))
        							.addGap(18)
        							.addComponent(btnChooseFileSearch, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
        							.addGap(16))
        						.addGroup(pnlGraphLayout.createSequentialGroup()
        							.addComponent(lblLevel)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addGroup(pnlGraphLayout.createParallelGroup(Alignment.LEADING)
        								.addComponent(btnUpload, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE)
        								.addComponent(cbLevel, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
        							.addGap(215)))
        					.addContainerGap())
        				.addGroup(pnlGraphLayout.createSequentialGroup()
        					.addComponent(pnlMethod, GroupLayout.PREFERRED_SIZE, 664, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap(366, Short.MAX_VALUE))
        				.addGroup(pnlGraphLayout.createSequentialGroup()
        					.addComponent(pnlTestSmells, GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
        					.addGap(277))
        				.addGroup(pnlGraphLayout.createSequentialGroup()
        					.addComponent(pnlClass, GroupLayout.PREFERRED_SIZE, 541, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap(489, Short.MAX_VALUE))
        				.addGroup(pnlGraphLayout.createSequentialGroup()
        					.addComponent(pnlAuthor, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap(516, Short.MAX_VALUE))
        				.addGroup(pnlGraphLayout.createSequentialGroup()
        					.addComponent(pnlbutton, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap(61, Short.MAX_VALUE))))
        );
        pnlGraphLayout.setVerticalGroup(
        	pnlGraphLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(pnlGraphLayout.createSequentialGroup()
        			.addGap(18)
        			.addComponent(lblSelectCsv)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(pnlGraphLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(txtFilePathDefault, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(btnChooseFileSearch, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addComponent(btnUpload, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        			.addGap(22)
        			.addGroup(pnlGraphLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(cbLevel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblLevel))
        			.addGap(18)
        			.addComponent(pnlMethod, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(pnlClass, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
        			.addGap(4)
        			.addComponent(pnlTestSmells, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pnlAuthor, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(pnlbutton, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        			.addContainerGap())
        );

        GroupLayout gl_pnlbutton = new GroupLayout(pnlbutton);
        gl_pnlbutton.setHorizontalGroup(
        	gl_pnlbutton.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlbutton.createSequentialGroup()
        			.addGap(240)
        			.addComponent(btnVisualize, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(115, Short.MAX_VALUE))
        );
        gl_pnlbutton.setVerticalGroup(
        	gl_pnlbutton.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlbutton.createSequentialGroup()
        			.addGap(5)
        			.addComponent(btnVisualize)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlbutton.setLayout(gl_pnlbutton);
        
        GroupLayout gl_pnlAuthor = new GroupLayout(pnlAuthor);
        gl_pnlAuthor.setHorizontalGroup(
        	gl_pnlAuthor.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlAuthor.createSequentialGroup()
        			.addComponent(lblAuthor, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(cbAuthor, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(111, Short.MAX_VALUE))
        );
        gl_pnlAuthor.setVerticalGroup(
        	gl_pnlAuthor.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlAuthor.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnlAuthor.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblAuthor, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        				.addComponent(cbAuthor, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(51, Short.MAX_VALUE))
        );
        pnlAuthor.setLayout(gl_pnlAuthor);
        
        GroupLayout gl_pnlTestSmells = new GroupLayout(pnlTestSmells);
        gl_pnlTestSmells.setHorizontalGroup(
        	gl_pnlTestSmells.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlTestSmells.createSequentialGroup()
        			.addComponent(lblSelectTestSmells, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(cbTestSmells, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(191, Short.MAX_VALUE))
        );
        gl_pnlTestSmells.setVerticalGroup(
        	gl_pnlTestSmells.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlTestSmells.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnlTestSmells.createParallelGroup(Alignment.TRAILING)
        				.addComponent(cbTestSmells, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblSelectTestSmells, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(37, Short.MAX_VALUE))
        );
        pnlTestSmells.setLayout(gl_pnlTestSmells);
        
        GroupLayout gl_pnlClass = new GroupLayout(pnlClass);
        gl_pnlClass.setHorizontalGroup(
        	gl_pnlClass.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlClass.createSequentialGroup()
        			.addComponent(lblSelectClass, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbClass, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(128, Short.MAX_VALUE))
        );
        gl_pnlClass.setVerticalGroup(
        	gl_pnlClass.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlClass.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pnlClass.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblSelectClass, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        				.addComponent(cbClass, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(37, Short.MAX_VALUE))
        );
        pnlClass.setLayout(gl_pnlClass);
        pnlGraph.setLayout(pnlGraphLayout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(contentPane);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(pnlGraph, GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(pnlGraph, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addContainerGap())
        );
        contentPane.setLayout(layout);

        pack();

    }
}


