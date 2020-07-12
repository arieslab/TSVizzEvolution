package tsvizzevolution;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;

public class GraphTwoVersions extends JFrame {
    private JButton btnChooseFileSearch1;
    private JButton btnChooseFileSearch2;
    private JButton btnVisualizeGraph;
    private JButton btnVisualizeTimeline;
    private JComboBox<String> cbLevel;
    private JComboBox<String> cbClass;
    private JComboBox<String> cbTestSmells;
    private JComboBox<String> cbAuthor;  
    private JComboBox<String> cbVisualization;
    private JComboBox<String> cbTimeline;
    private JLabel lblTimeline;
    private JLabel lblCsv1;
    private JLabel lblCsv2;
    private JLabel lblLevel;
    private JLabel lblClass;
    private JLabel lblTestSmells;
    private JLabel lblAuthor;
    private JLabel lblVisualization;
    private JPanel pnlClass;
    private JPanel pnlTestSmells;
    private JPanel pnlAuthor;
    private JPanel pnlGraph;
    private JPanel pnlLevel;
    private JPanel pnlTimeline;
	private JPanel pnlVisualization ;

//    public JPanel pnlGenerateTimeline;
//    public JPanel pnlGenerateGraph;
    private JTextField txtFilePathDefault1; 
    private JTextField txtFilePathDefault2;
    private static final String VIRGULA = ",";
    private static String nomeDoArquivo;
	public JFrame frame;
	public JPanel classe;
	public JPanel metodo;
	public JPanel contentPane;
	
	

    public static int converteInteiro(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public GraphTwoVersions() throws IOException {

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
       // pnlLevel.setVisible(false);
      //  pnlTimeline.setVisible(false);
      //  btnVisualizeTimeline.setVisible(false);
      //  btnVisualizeGraph.setVisible(false);
        pnlVisualization.setVisible(true);
        
        pnlLevel.setVisible(true);
        pnlTimeline.setVisible(false);
        btnVisualizeGraph.setVisible(true);
        btnVisualizeTimeline.setVisible(false);
        

//        pnlGenerateTimeline.setVisible(false);
//        pnlGenerateGraph.setVisible(false);

       
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

    

    private void btnChooseFileSearch1ActionPerformed(ActionEvent evt) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(GraphTwoVersions.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtFilePathDefault1.setText(file.getPath());
            nomeDoArquivo = file.getName();
        }
    }
   
    private void btnChooseFileSearch2ActionPerformed(ActionEvent evt) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(GraphTwoVersions.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtFilePathDefault2.setText(file.getPath());
            nomeDoArquivo = file.getName();
        }
    }


    private void btnGerarTimelineActionPerformed(ActionEvent evt) {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension( 900 + Configurations.adicionalBorda, Configurations.alturaPainel ));
        frame.setMaximumSize(frame.getPreferredSize());
        frame.setMinimumSize(frame.getPreferredSize());
		frame.setTitle("TSVizzEvolution");

		//int larguraPainel = largura_pacote + Configurations.adicionalBorda;		
		
		
        JPanel painel = new JPanel();
        painel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painel.setBackground(Configurations.corPainel); //seta a cor de fundo
        painel.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaPainel, Configurations.larguraBorda)); // seta a borda
        painel.setPreferredSize(new Dimension( 900, Configurations.alturaPainel ));
        painel.setMaximumSize(painel.getPreferredSize());
        painel.setMinimumSize(painel.getPreferredSize());
        frame.add(painel);
        try {
            String selecionado = (String) cbTimeline.getSelectedItem();
            criaRetangulos(painel, selecionado, txtFilePathDefault1.getText(), txtFilePathDefault2.getText());

        } catch (Exception e){
            e.printStackTrace();
        }

	JScrollPane jScrollPane = new JScrollPane(painel);
	jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	jScrollPane.setVerticalScrollBarPolicy(jScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	frame.add(jScrollPane);
	
//	JScrollPane scrollPaneTabela = new JScrollPane(painel);
//	scrollPaneTabela = new JScrollPane(painel);
//	scrollPaneTabela.setViewportBorder(null);
//	scrollPaneTabela.setPreferredSize(new Dimension(140, 50));
//	scrollPaneTabela.setBounds(10, 21, 394, 183);
//	frame.add(scrollPaneTabela);
	}

	private void criaRetangulos(JPanel painel, String filtro, String fileName1, String fileName2){
        JPanel pacote = new JPanel();
        pacote.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pacote.setBackground(Configurations.corPacote); //seta a cor de fundo
        pacote.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaPacote, Configurations.larguraBorda)); // seta a borda
        ToolTipManager.sharedInstance().setInitialDelay(500);//aparecerá logo que passe 0,5 segundos
        painel.add(pacote);

        JPanel pacote2 = new JPanel();

        pacote2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pacote2.setBackground(Configurations.corPacote); //seta a cor de fundo
        pacote2.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaPacote, Configurations.larguraBorda)); // seta a borda
        ToolTipManager.sharedInstance().setInitialDelay(500);//aparecerá logo que passe 0,5 segundos
        
        JPanel espaco = new JPanel();
        espaco.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        espaco.setBackground(Color.WHITE); //seta a cor de fundo
        espaco.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaPainel, Configurations.larguraBorda)); // seta a borda
        ToolTipManager.sharedInstance().setInitialDelay(500);//aparecerá logo que passe 0,5 segundos
        espaco.setPreferredSize(new Dimension(900, 10 ));

        painel.add(espaco);
        
        painel.add(pacote2);

        List<Dados> dados1 = retornaDados(fileName1, filtro);
        List<Dados> dados2 = retornaDados(fileName2, filtro);
        arrumaDados(dados1, dados2);
        constroiBlocos(dados1, filtro, pacote);
        constroiBlocos(dados2, filtro, pacote2);

    }

    private void constroiBlocos(List<Dados> dados, String filtro, JPanel pacote){
        if (filtro.equals("Project")){
            List<String> analisados = new ArrayList<>();
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
            List<String> analisados = new ArrayList<>();
            for (int i = 0; i < dados.size(); i++){
                String classe_analisada = dados.get(i).classe;
                if (!foiAnalisado(analisados, classe_analisada)){
                    classe = new JPanel();
                    classe.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    classe.setBackground(Configurations.corClasse); //seta a cor de fundo
                    classe.setBorder(BorderFactory.createLineBorder((Color) Configurations.bordaClasse, Configurations.larguraBorda)); // seta a borda
                    classe.setPreferredSize(new Dimension(204, Configurations.alturaClasse));
                    String novo_nome_segundo_vertice = classe_analisada.substring(0, classe_analisada.length()-2);
                 //   String html_classe = "<html><p><font color=\"#000000\" " + "size=\"4\" face=\"Arial\"><b> Test Class: <body></b>" + novo_nome_segundo_vertice +"</font></p></html>";
                 //   classe.setToolTipText(html_classe);
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
                           // String html_metodo = "<html><p><font color=\"#000000\" " + "size=\"4\" face=\"Arial\"><b> Test Smells: <body></b>" + novo_nome_primeiro_vertice + " <br>  "+ "<HtMl><b>Occurrence: <body></b>" + dados.get(j).valor + "</font></p></html>";
                            String html_metodo = "<html><p><font color=\"#000000\" " + "size=\"4\" face=\"Arial\"><b> Test Smells: <body></b>" + novo_nome_primeiro_vertice + " <br>  "+ "<HtMl><b>Occurrence: <body></b>" + dados.get(j).valor +" <br>  "+  "<HtMl><b>Test Class: <body></b>" + novo_nome_segundo_vertice + "</font></p></html>";
                            metodo.setToolTipText(html_metodo);
                            classe.add(metodo);
                        }
                    }
                    analisados.add(classe_analisada);
                }
            }
        }

    }

    private boolean foiAnalisado(List<String> analisados, String classe){
        for (String analisado : analisados) {
            if (analisado.equals(classe)) {
                return true;
            }
        }
        return false;
    }


    private void btnGerarGrafoActionPerformed(ActionEvent evt) {
        try {
            System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
            Graph graph1 = new MultiGraph("TSVizzEvolution");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePathDefault1.getText())));
            String linha = null;

            List listaDeLinhasInt = new ArrayList();
            List listaDeLinhas = new ArrayList();
            String cabecalho;

            cabecalho = reader.readLine();

            String[] cabecalhoLista = cabecalho.split(VIRGULA);
            int y = 0;
            for (int i = 10; i < cabecalhoLista.length; i++) {
                graph1.addNode(cabecalhoLista[i] + "_1");
                Node n = graph1.getNode(cabecalhoLista[i] + "_1");
                n.setAttribute("ui.label", cabecalhoLista[i] + "_1");
                n.addAttribute("ui.class", "quadradoTS");

                y = y + 500;
                n.setAttribute("x", 2);
                n.setAttribute("y", y);

            }
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

            BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePathDefault2.getText())));
            String linha2 = null;

            List listaDeLinhasInt2 = new ArrayList();
            List listaDeLinhas2 = new ArrayList();
            String cabecalho2;

            cabecalho2 = reader2.readLine();

            String[] cabecalhoLista2 = cabecalho2.split(VIRGULA);
            for (int i = 10; i < cabecalhoLista2.length; i++) {
                graph1.addNode(cabecalhoLista2[i] + "_2");
                Node n = graph1.getNode(cabecalhoLista2[i] + "_2");
                n.setAttribute("ui.label", cabecalhoLista2[i] + "_2");
                n.addAttribute("ui.class", "quadradoTS");

                y = y + 500;
                n.setAttribute("x", 2);
                n.setAttribute("y", y);

            }
            if (cabecalho2 != null) {
                while ((linha2 = reader2.readLine()) != null) {
                    String[] dados = linha2.split(VIRGULA);
                    listaDeLinhas2.add(dados);
                    int[] valorInteiros = new int[dados.length];

                    for (int i = 0; i < dados.length; i++) {
                        valorInteiros[i] = converteInteiro(String.valueOf(dados[i]));
                    }
                    listaDeLinhasInt2.add(valorInteiros);
                }

            }

            //----------------------------------------------------------------------------------------------------------
            String selecionado = (String) cbLevel.getSelectedItem();
            int coluna = 0;
            if (selecionado.equals("Project")) {
                coluna = 5;
            }else {
                coluna = 6;
            }

            try {
            	 if (selecionado.equals("Project") || selecionado.equals("All Test Classes")) {
                    CriaGrafoCompleto(listaDeLinhasInt, listaDeLinhas, cabecalhoLista, graph1, coluna, 1);
                    CriaGrafoCompleto(listaDeLinhasInt2, listaDeLinhas2, cabecalhoLista2, graph1, coluna, 2);
                } else {
                	String filtro = "";
                	if (selecionado.equals("A Specific Test Class")) {
                		filtro = (String) cbClass.getSelectedItem();
                        CriaGrafoParcial(listaDeLinhasInt, listaDeLinhas, cabecalhoLista, graph1, filtro, coluna, 1);
                        CriaGrafoParcial(listaDeLinhasInt2, listaDeLinhas2, cabecalhoLista2, graph1, filtro, coluna, 2);
                        graph1 = removeVertices(graph1);
                	}else if (selecionado.equals("Author")){
                	    filtro = (String) cbTestSmells.getSelectedItem();
                	    String filtroAutor = (String) cbAuthor.getSelectedItem();
                        CriaGrafoParcialAutor(listaDeLinhasInt, listaDeLinhas, cabecalhoLista, graph1, filtro, filtroAutor, coluna, 1);
                        CriaGrafoParcialAutor(listaDeLinhasInt2, listaDeLinhas2, cabecalhoLista2, graph1, filtro, filtroAutor, coluna, 2);
                        graph1 = removeVertices(graph1);
                	}else{
                		filtro = (String) cbTestSmells.getSelectedItem();
                        CriaGrafoParcial(listaDeLinhasInt, listaDeLinhas, cabecalhoLista, graph1, filtro, coluna, 1);
                        CriaGrafoParcial(listaDeLinhasInt2, listaDeLinhas2, cabecalhoLista2, graph1, filtro, coluna, 1);
                        graph1 = removeVertices(graph1);
                	}
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            String path = System.getProperty("user.dir").replace('\\', '/');
            graph1.addAttribute("ui.stylesheet", "url('" + path + "/src/tsvizzevolution/Config.css')");
            if (graph1.getNodeCount() == 0){
                String msg = "<html>The combination Test Smells x Author does not exist!";

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
            Logger.getLogger(GraphTwoVersions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private static void CriaGrafoCompleto(List listaDeLinhasInt, List listaDeLinhas, String[] cabecalho, Graph graph1, int coluna, int flag) throws IOException {
        String complemento = "";
        if (flag == 1){
            complemento = "_1";
        }else{
            complemento = "_2";
        }
        ArrayList textFileNameAnterior = new ArrayList();
        int y = 0;
        for (int i = 0; i < listaDeLinhasInt.size(); i++) {
            int[] linhaInt = (int[]) listaDeLinhasInt.get(i);
            String[] linha = (String[]) listaDeLinhas.get(i);
            try {
                graph1.addNode(linha[coluna] + complemento);
            } catch (Exception e) {
            }
            String busca = linha[coluna] + complemento;
            Node n1 = graph1.getNode(linha[coluna] + complemento);
            n1.setAttribute("ui.label", linha[coluna] + complemento);
            n1.addAttribute("ui.class", "projeto");

            y = y + 500;
            n1.setAttribute("x", 1000);
            n1.setAttribute("y", y);
            for (int j = 10; j < linhaInt.length; j++) {
                if (linhaInt[j] != 0) {
                    try {
                        graph1.addEdge(cabecalho[j] + complemento + " " + linha[coluna] + complemento, cabecalho[j] + complemento, linha[coluna] + complemento);
                    } catch (Exception e) {
                    }
                }
            }
        }
        if (complemento.equals("_2")) {
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

    private static void CriaGrafoParcial(List listaDeLinhasInt, List listaDeLinhas, String[] cabecalho, Graph graph1, String nome, int coluna, int flag) throws IOException {
        String complemento = "";
        if (flag == 1){
            complemento = "_1";
        }else{
            complemento = "_2";
        }
        int y = 0;
        for (int i = 0; i < listaDeLinhasInt.size(); i++) {
            int[] linhaInt = (int[]) listaDeLinhasInt.get(i);
            String[] linha = (String[]) listaDeLinhas.get(i);
            try {
                graph1.addNode(linha[coluna] + complemento);
            } catch (Exception e) {
            }
            Node n1 = graph1.getNode(linha[coluna] + complemento);
            n1.setAttribute("ui.label", linha[coluna] + complemento);
            y = y + 500;
            n1.setAttribute("x", 1000);
            n1.setAttribute("y", y);
            for (int j = 10; j < linhaInt.length; j++) {
                if (linhaInt[j] != 0) {
                    if (nome.equals(linha[coluna]) || nome.equals(cabecalho[j])) {
                        try {
                            graph1.addEdge(cabecalho[j] + complemento + " " + linha[coluna] + complemento, cabecalho[j] + complemento, linha[coluna] + complemento);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        if (complemento.equals("_2")) {
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

    private static void CriaGrafoParcialAutor(List listaDeLinhasInt, List listaDeLinhas, String[] cabecalho, Graph graph1, String nome, String nomeAutor, int coluna, int flag) throws IOException {
        String complemento = "";
        if (flag == 1){
            complemento = "_1";
        }else{
            complemento = "_2";
        }
        String filtro_autor = nomeAutor;
        int y = 0;
        int colunaAutor = 1;
        if (nomeAutor.equals("All")){
            ArrayList<String> nomeAutores = new ArrayList<>();
            for (int i = 0; i < listaDeLinhas.size(); i++){
                String[] linha = (String[]) listaDeLinhas.get(i);
                if (!nomeAutores.contains(linha[1])){
                    nomeAutores.add(linha[1]);
                }
            }
            for (int x = 0; x < nomeAutores.size(); x++){
                nomeAutor = nomeAutores.get(x);
                graph1.addNode(nomeAutor + complemento);
                Node autor = graph1.getNode(nomeAutor + complemento);
                autor.setAttribute("ui.label", nomeAutor + complemento);
                autor.addAttribute("ui.class", "boneco");
                autor.setAttribute("x", -1000);
                autor.setAttribute("y", y);

                for (int i = 0; i < listaDeLinhasInt.size(); i++) {
                    int[] linhaInt = (int[]) listaDeLinhasInt.get(i);
                    String[] linha = (String[]) listaDeLinhas.get(i);
                    try {
                        graph1.addNode(linha[coluna] + complemento);
                    } catch (Exception e) {
                    }
                    Node n1 = graph1.getNode(linha[coluna] + complemento);
                    n1.setAttribute("ui.label", linha[coluna] + complemento);

                    y = y + 500;
                    n1.setAttribute("x", 1000);
                    n1.setAttribute("y", y);
                    for (int j = 10; j < linhaInt.length; j++) {
                        if (linhaInt[j] != 0) {
                            if (nomeAutor.equals(linha[colunaAutor]) || nome.equals(cabecalho[j])) {
                                if (nome.equals(cabecalho[j])) {
                                    try {
                                        graph1.addEdge(cabecalho[j] + complemento + " " + linha[colunaAutor] + complemento, cabecalho[j] + complemento, linha[colunaAutor] + complemento);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    }

                }
                if (complemento.equals("_2")) {
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
        }else {
            graph1.addNode(nomeAutor + complemento);
            Node autor = graph1.getNode(nomeAutor + complemento);
            autor.setAttribute("ui.label", nomeAutor + complemento);
            autor.addAttribute("ui.class", "boneco");
            autor.setAttribute("x", -1000);
            autor.setAttribute("y", 0);

            for (int i = 0; i < listaDeLinhasInt.size(); i++) {
                int[] linhaInt = (int[]) listaDeLinhasInt.get(i);
                String[] linha = (String[]) listaDeLinhas.get(i);
                try {
                    graph1.addNode(linha[coluna] + complemento);
                } catch (Exception e) {
                }
                Node n1 = graph1.getNode(linha[coluna] + complemento);
                n1.setAttribute("ui.label", linha[coluna] + complemento);

                y = y + 500;
                n1.setAttribute("x", 1000);
                n1.setAttribute("y", y);
                for (int j = 10; j < linhaInt.length; j++) {
                    if (linhaInt[j] != 0) {
                        if (nome.equals(linha[coluna]) || nome.equals(cabecalho[j]) && linha[colunaAutor].equals(nomeAutor)) {
                            try {
                                graph1.addEdge(cabecalho[j] + complemento + " " + linha[coluna] + complemento, cabecalho[j] + complemento, linha[coluna] + complemento);
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
                                    graph1.addEdge(cabecalho[j] + complemento + " " + linha[colunaAutor] + complemento, cabecalho[j] + complemento, linha[colunaAutor] + complemento);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }

            }
            if (complemento.equals("_2")) {
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
    }

    private void cbLevelActionPerformed(ActionEvent evt) {
    }
    private void cbVisualizationActionPerformed(ActionEvent evt) {
    }

    private void cbTimelineActionPerformed(ActionEvent evt) {
    }

    public static Graph removeVertices(Graph graph1){
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
        return graph1;
    }

    public static void arrumaDados(List<Dados> dados1, List<Dados> dados2){
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
                dados2.add(new Dados(dados1.get(i).nome, 0, dados1.get(i).classe, dados1.get(i).projeto, Color.GRAY));
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
                dados1.add(new Dados(dados2.get(i).nome, 0, dados2.get(i).classe, dados1.get(i).projeto, Color.GRAY));
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

    public static List<Dados> retornaDados(String file, String filtro){
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
            List<Dados> resultado_final = new ArrayList<>();
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
                    resultado_final.add(new Dados(cabecalhoLista[i], soma, "", nome_projeto));
                    coluna += 1;

                }
            }
            if (filtro.equals("All Test Classes")){
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
                        resultado_final.add(new Dados(cabecalhoLista[j], soma, classe_analisada, nome_projeto));
                        coluna += 1;
                    }
                }
            }
            return resultado_final;
        }catch (Exception e){
            return null;
        }
    }

    public static void main(String args[]) throws IOException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GraphTwoVersions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GraphTwoVersions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GraphTwoVersions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GraphTwoVersions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
					new GraphTwoVersions().setVisible(true);
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
        cabecalho_combo = reader.readLine();
        String[] cabecalhoTest = cabecalho_combo.split(VIRGULA);
        String[] resultado = new String[cabecalhoTest.length - 10];
        for (int i = 10; i < cabecalhoTest.length; i++){
        	resultado[i - 10] = cabecalhoTest[i];
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

    private String[] concatena(String[] lista1, String[] lista2){
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < lista1.length; i++){
            lista.add(lista1[i]);
        }
        for (int i = 0; i < lista2.length; i++){
            boolean achou = false;
            for (int j = 0; j < lista.size(); j++){
                if (lista.get(j).equals(lista2[i])){
                    achou = true;
                }
            }
            if (!achou){
                lista.add(lista2[i]);
            }
        }
        Collections.sort(lista);
        String[] resultado = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++){
            resultado[i] = lista.get(i);
        }
        return resultado;
    }
    private void initComponents() throws IOException {
        pnlGraph = new JPanel();
		pnlLevel = new JPanel();
		pnlTimeline = new JPanel();
      
		//pnlGenerateTimeline = new JPanel();
		//pnlGenerateGraph = new JPanel();

     //   setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        String[] a = null;
    	String[] b = null;
        String[] c = null;
        String[] a2 = null;
        String[] b2 = null;
        String[] c2 = null;

		btnVisualizeGraph = new JButton();
		btnVisualizeGraph.setText("Generate Graph View");
		btnVisualizeGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnGerarGrafoActionPerformed(evt);
            }
		});

        btnVisualizeTimeline = new JButton();
        btnVisualizeTimeline.setText("Generate Timeline View");
        btnVisualizeTimeline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnGerarTimelineActionPerformed(evt);
            }


        });
        cbVisualization = new JComboBox<>();
        cbVisualization.setModel(new DefaultComboBoxModel<>(new String[] {"Graph View",  "Timeline View" }));
        cbVisualization.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		cbVisualizationActionPerformed(evt);
        }
       });
        pnlLevel.setVisible(true);
        pnlTimeline.setVisible(false);
        btnVisualizeGraph.setVisible(true);
        btnVisualizeTimeline.setVisible(false);
        
        String selecionado2 = (String) cbVisualization.getSelectedItem();
        if (selecionado2.equals("Graph View")) {
        	 cbVisualization.addItemListener(new ItemListener() {
                 public void itemStateChanged(ItemEvent event) {
                     if (event.getItem().equals("Graph View")) {
                         pnlLevel.setVisible(true);
                         pnlTimeline.setVisible(false);
                         btnVisualizeGraph.setVisible(true);
                         btnVisualizeTimeline.setVisible(false);
//                         pnlGenerateTimeline.setVisible(false);
//                         pnlGenerateGraph.setVisible(true);
                     } else if (event.getItem().equals("Timeline View")) {
                     	 pnlClass.setVisible(false);
                          pnlTestSmells.setVisible(false);
                          pnlAuthor.setVisible(false); 
                     	pnlTimeline.setVisible(true);
                         pnlLevel.setVisible(false);
                         btnVisualizeTimeline.setVisible(true);
                         btnVisualizeGraph.setVisible(false);
                        
//                         pnlGenerateTimeline.setVisible(true);
//                         pnlGenerateGraph.setVisible(false);
                     }
                 }
             });

             
        
        }            
        cbTimeline = new JComboBox<>();
        cbTimeline.setModel(new DefaultComboBoxModel<>(new String[] { "Project",  "All Test Classes" }));
        cbTimeline.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		cbTimelineActionPerformed(evt);
            }
        });
		                
		                cbLevel = new JComboBox<>();
		                cbLevel.setModel(new DefaultComboBoxModel<>(new String[] { "Project",  "All Test Classes", "A Specific Test Class", "A Specific Test Smells", "Author" }));
		                cbLevel.addActionListener(new ActionListener() {
		                    public void actionPerformed(ActionEvent evt) {
		                        cbLevelActionPerformed(evt);
		                    }
		                });


		                btnChooseFileSearch2 = new JButton();
		                
		                        btnChooseFileSearch2.setText("Search ...");
		                        btnChooseFileSearch2.addActionListener(new ActionListener() {
		                            public void actionPerformed(ActionEvent evt) {
		                                btnChooseFileSearch2ActionPerformed(evt);
		                            }
		                        });
		                        btnChooseFileSearch1 = new JButton();
		                        
		                                btnChooseFileSearch1.setText("Search ...");
		                                btnChooseFileSearch1.addActionListener(new ActionListener() {
		                                    public void actionPerformed(ActionEvent evt) {
		                                        btnChooseFileSearch1ActionPerformed(evt);
		                                    }
		                                });
		                                		txtFilePathDefault1 = new JTextField();
                                                txtFilePathDefault1.setText("C:\\Users\\Adriana\\Desktop\\mestrado\\software\\commons-io_testsmesll_2.1.csv");
		                                		a2 = carrega_lista_linhas(txtFilePathDefault1.getText());
		                                        b2 = carrega_lista_cabecalho(txtFilePathDefault1.getText());
		                                        c2 = carrega_lista_autor(txtFilePathDefault1.getText());

		                                        txtFilePathDefault2 = new JTextField();
                                                txtFilePathDefault2.setText("C:\\Users\\Adriana\\Desktop\\mestrado\\software\\commons-io_testsmesll_2_6.csv");
		                                        a = carrega_lista_linhas(txtFilePathDefault2.getText());
		                                        b = carrega_lista_cabecalho(txtFilePathDefault2.getText());
		                                        c = carrega_lista_autor(txtFilePathDefault2.getText());

		                                        a = concatena(a, a2);
		                                        b = concatena(b, b2);
		                                        c = concatena(c, c2);
		                               		    lblTimeline = new JLabel();
		                               		     lblTimeline.setText("Select the level of granularity:");
		                               		 
		                                        lblVisualization = new JLabel();
		                                        lblVisualization.setText("Select a view type:");
		                                        
		                                        lblLevel = new JLabel();
		                                        lblLevel.setText("Select the level of granularity:");
		                                        
		                		                pnlAuthor = new JPanel();

		                		                lblAuthor = new JLabel();
		                                        lblAuthor.setText("Select A Specific Author or All:");

		                                        cbAuthor = new JComboBox<>();
		                                        cbAuthor.setModel(new DefaultComboBoxModel<>(c));
		                        

		                                        lblClass = new JLabel();
		        				                lblClass.setText("Select a Test Class:");

		                                        pnlClass = new JPanel();
				                
				                        cbClass = new JComboBox<>();
				                        cbClass.setModel(new DefaultComboBoxModel<>(a));
				                        
						                lblCsv2 = new JLabel();
						                lblCsv2.setText("Select the second .csv File :");
						                lblCsv1 = new JLabel();
						
						                lblCsv1.setText("Select the first .csv File :");
						                lblCsv1.setFont(new Font("Tahoma", Font.PLAIN, 16));

						                lblTestSmells = new JLabel();
						                pnlTestSmells = new JPanel();

						 
						         lblTestSmells.setText("Select a Test Smells:");
						                 cbTestSmells = new JComboBox<>();
						                 cbTestSmells.setModel(new DefaultComboBoxModel<>(b));
							         		pnlVisualization = new JPanel();

						                 btnChooseFileSearch1.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                  btnChooseFileSearch2.setFont(new Font("Tahoma", Font.PLAIN, 16));
						         		 btnVisualizeGraph.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 btnVisualizeTimeline.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 cbLevel.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 cbClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 cbTestSmells.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 cbAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));  
						                 cbVisualization.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 cbTimeline.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 lblTimeline.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 lblCsv1.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 lblCsv2.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 lblClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 lblTestSmells.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 lblVisualization.setFont(new Font("Tahoma", Font.PLAIN, 16));
						                 txtFilePathDefault1.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
						                 txtFilePathDefault2.setFont(new Font("Tahoma", Font.PLAIN, 14)); 

						                 GroupLayout gl_contentPane = new GroupLayout(contentPane);
							         		gl_contentPane.setHorizontalGroup(
							         			gl_contentPane.createParallelGroup(Alignment.LEADING)
							         				.addComponent(pnlGraph, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							         		);
							         		gl_contentPane.setVerticalGroup(
							         			gl_contentPane.createParallelGroup(Alignment.LEADING)
							         				.addComponent(pnlGraph, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
							         		);
							         		GroupLayout gl_pnlClass = new GroupLayout(pnlClass);
							         		gl_pnlClass.setHorizontalGroup(
							         			gl_pnlClass.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlClass.createSequentialGroup()
							         					.addComponent(lblClass)
							         					.addPreferredGap(ComponentPlacement.UNRELATED)
							         					.addComponent(cbClass, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
							         					.addContainerGap(50, Short.MAX_VALUE))
							         		);
							         		gl_pnlClass.setVerticalGroup(
							         			gl_pnlClass.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlClass.createSequentialGroup()
							         					.addGroup(gl_pnlClass.createParallelGroup(Alignment.BASELINE)
							         						.addComponent(lblClass)
							         						.addComponent(cbClass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         					.addContainerGap(67, Short.MAX_VALUE))
							         		);
							         		pnlClass.setLayout(gl_pnlClass);
							         		
							         		GroupLayout gl_pnlLevel = new GroupLayout(pnlLevel);
							         		gl_pnlLevel.setHorizontalGroup(
							         			gl_pnlLevel.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlLevel.createSequentialGroup()
							         					.addComponent(lblLevel)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(cbLevel, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
							         					.addContainerGap(59, Short.MAX_VALUE))
							         		);
							         		gl_pnlLevel.setVerticalGroup(
							         			gl_pnlLevel.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlLevel.createSequentialGroup()
							         					.addContainerGap()
							         					.addGroup(gl_pnlLevel.createParallelGroup(Alignment.BASELINE)
							         						.addComponent(lblLevel)
							         						.addComponent(cbLevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         					.addContainerGap(48, Short.MAX_VALUE))
							         		);
							         		pnlLevel.setLayout(gl_pnlLevel);		
							         		
							         		GroupLayout gl_pnlTimeline = new GroupLayout(pnlTimeline);
							         		gl_pnlTimeline.setHorizontalGroup(
							         			gl_pnlTimeline.createParallelGroup(Alignment.LEADING)
							         				.addGroup(Alignment.TRAILING, gl_pnlTimeline.createSequentialGroup()
							         					.addComponent(lblTimeline, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(cbTimeline, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
							         					.addGap(32))
							         		);
							         		gl_pnlTimeline.setVerticalGroup(
							         			gl_pnlTimeline.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlTimeline.createSequentialGroup()
							         					.addGroup(gl_pnlTimeline.createParallelGroup(Alignment.BASELINE)
							         						.addComponent(lblTimeline)
							         						.addComponent(cbTimeline, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         					.addContainerGap(23, Short.MAX_VALUE))
							         		);
							       		 pnlTimeline.setLayout(gl_pnlTimeline);
							         		
//							         		GroupLayout gl_pnlTimeline = new GroupLayout(pnlTimeline);
//							         		gl_pnlTimeline.setHorizontalGroup(
//							         			gl_pnlTimeline.createParallelGroup(Alignment.LEADING)
//							         				.addGroup(gl_pnlTimeline.createSequentialGroup()
//							         					.addComponent(lblTimeline)
//							         					.addPreferredGap(ComponentPlacement.RELATED)
//							         					.addComponent(cbTimeline, 0, 200, Short.MAX_VALUE)
//							         					.addContainerGap())
//							         		);
//							         		gl_pnlTimeline.setVerticalGroup(
//							         			gl_pnlTimeline.createParallelGroup(Alignment.LEADING)
//							         				.addGroup(gl_pnlTimeline.createSequentialGroup()
//							         					.addContainerGap(20, Short.MAX_VALUE)
//							         					.addGroup(gl_pnlTimeline.createParallelGroup(Alignment.LEADING)
//							         						.addComponent(lblTimeline, Alignment.TRAILING)
//							         						.addComponent(cbTimeline, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
//							         		);
//							         		pnlTimeline.setLayout(gl_pnlTimeline);
							         		
							         		GroupLayout gl_pnlAuthor = new GroupLayout(pnlAuthor);
							         		gl_pnlAuthor.setHorizontalGroup(
							         			gl_pnlAuthor.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlAuthor.createSequentialGroup()
							         					.addComponent(lblAuthor)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(cbAuthor, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
							         					.addContainerGap(19, Short.MAX_VALUE))
							         		);
							         		gl_pnlAuthor.setVerticalGroup(
							         			gl_pnlAuthor.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlAuthor.createSequentialGroup()
							         					.addContainerGap()
							         					.addGroup(gl_pnlAuthor.createParallelGroup(Alignment.BASELINE)
							         						.addComponent(lblAuthor)
							         						.addComponent(cbAuthor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         					.addContainerGap(17, Short.MAX_VALUE))
							         		);
							         		pnlAuthor.setLayout(gl_pnlAuthor);
							         		
							         		
							         		GroupLayout gl_pnlTestSmells = new GroupLayout(pnlTestSmells);
							         		gl_pnlTestSmells.setHorizontalGroup(
							         			gl_pnlTestSmells.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlTestSmells.createSequentialGroup()
							         					.addComponent(lblTestSmells)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(cbTestSmells, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
							         					.addContainerGap(66, Short.MAX_VALUE))
							         		);
							         		gl_pnlTestSmells.setVerticalGroup(
							         			gl_pnlTestSmells.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlTestSmells.createSequentialGroup()
							         					.addContainerGap()
							         					.addGroup(gl_pnlTestSmells.createParallelGroup(Alignment.BASELINE)
							         						.addComponent(lblTestSmells)
							         						.addComponent(cbTestSmells, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         					.addContainerGap(16, Short.MAX_VALUE))
							         		);
							         		pnlTestSmells.setLayout(gl_pnlTestSmells);
							         		GroupLayout gl_pnlGraph = new GroupLayout(pnlGraph);
							         		gl_pnlGraph.setHorizontalGroup(
							         			gl_pnlGraph.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlGraph.createSequentialGroup()
							         					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addGap(10)
							         							.addComponent(lblCsv1))
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addGap(10)
							         							.addComponent(txtFilePathDefault1, GroupLayout.PREFERRED_SIZE, 526, GroupLayout.PREFERRED_SIZE)
							         							.addGap(10)
							         							.addComponent(btnChooseFileSearch1))
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addGap(10)
							         							.addComponent(lblCsv2))
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addGap(10)
							         							.addComponent(txtFilePathDefault2, GroupLayout.PREFERRED_SIZE, 526, GroupLayout.PREFERRED_SIZE)
							         							.addGap(10)
							         							.addComponent(btnChooseFileSearch2))
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addGap(10)
							         							.addComponent(pnlVisualization, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE))
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addContainerGap()
							         							.addComponent(pnlLevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addContainerGap()
							         							.addComponent(pnlTestSmells, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addContainerGap()
							         							.addComponent(pnlAuthor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addGap(218)
							         							.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
							         								.addComponent(btnVisualizeGraph, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
							         								.addComponent(btnVisualizeTimeline, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)))
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addContainerGap()
							         							.addGroup(gl_pnlGraph.createParallelGroup(Alignment.TRAILING, false)
							         								.addComponent(pnlTimeline, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							         								.addComponent(pnlClass, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
							         					.addContainerGap(220, Short.MAX_VALUE))
							         		);
							         		gl_pnlGraph.setVerticalGroup(
							         			gl_pnlGraph.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlGraph.createSequentialGroup()
							         					.addGap(12)
							         					.addComponent(lblCsv1)
							         					.addGap(11)
							         					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addGap(1)
							         							.addComponent(txtFilePathDefault1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         						.addComponent(btnChooseFileSearch1))
							         					.addGap(18)
							         					.addComponent(lblCsv2)
							         					.addGap(6)
							         					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
							         						.addGroup(gl_pnlGraph.createSequentialGroup()
							         							.addGap(1)
							         							.addComponent(txtFilePathDefault2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         						.addComponent(btnChooseFileSearch2))
							         					.addGap(6)
							         					.addComponent(pnlVisualization, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(pnlLevel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(pnlTimeline, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(pnlClass, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(pnlTestSmells, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(pnlAuthor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(btnVisualizeTimeline, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(btnVisualizeGraph, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							         					.addGap(61))
							         		);
							         		
							         		GroupLayout gl_pnlVisualization = new GroupLayout(pnlVisualization);
							         		gl_pnlVisualization.setHorizontalGroup(
							         			gl_pnlVisualization.createParallelGroup(Alignment.LEADING)
							         				.addGroup(gl_pnlVisualization.createSequentialGroup()
							         					.addComponent(lblVisualization)
							         					.addPreferredGap(ComponentPlacement.RELATED)
							         					.addComponent(cbVisualization, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
							         					.addContainerGap(70, Short.MAX_VALUE))
							         		);
							         		gl_pnlVisualization.setVerticalGroup(
							         			gl_pnlVisualization.createParallelGroup(Alignment.LEADING)
							         				.addGroup(Alignment.TRAILING, gl_pnlVisualization.createSequentialGroup()
							         					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							         					.addGroup(gl_pnlVisualization.createParallelGroup(Alignment.BASELINE)
							         						.addComponent(lblVisualization)
							         						.addComponent(cbVisualization, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							         					.addContainerGap())
							         		);
							         		pnlVisualization.setLayout(gl_pnlVisualization);
							         		pnlGraph.setLayout(gl_pnlGraph);
							         		contentPane.setLayout(gl_contentPane);

							         						         
	}
}
