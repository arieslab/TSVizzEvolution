package tsvizzevolution;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.*;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;

import static org.graphstream.algorithm.Toolkit.randomNode;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;

public class GraphTwoVersions extends JFrame {
    private JButton btnChooseFileSearch1;
    private JButton btnChooseFileSearch2;
    private JButton btnVisualizeTimeline;
    private JButton btnSearchMethod;
    private JButton btnSearchMethod2;
    private JComboBox<String> cbTimeline;
    private JComboBox<String> cbSelectMethod;

    private JLabel lblTimeline;
    private JLabel lblCsv1;
    private JLabel lblCsv2;
    private JLabel lblVisualization;
    private JLabel lblSelectTheCsvMethod;
    private JLabel lblSelectTheSecond;
	private JLabel lblVisualizeTimeline;
	private JLabel lblSelectMethod;
    private JPanel pnlGraph;
    private JPanel pnlTimeline;
	private JPanel pnlVisualization ;
	private JPanel pnlMethod;
	private JPanel pnlProgress;
	private JPanel pnlSelectMethod;
	public JProgressBar progress;
	public JPanel classe;
	public JPanel metodo;
	public JPanel contentPane;
	
	public JFrame frame;

    private JTextField txtFilePathDefault1; 
    private JTextField txtFilePathDefault2;
    private JTextField txtFilePathMethod; 
    private JTextField txtFilePathMethod2;

    private static final String VIRGULA = ";";
    private static String nomeDoArquivo;



	public Thread progressoT = new Thread() {
		@Override
		public void run(){
			progress.setValue(0);				
			for (int i = 0; i <= 50; i++) {
				progress.setValue(i);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	};

	public static int converteInteiro(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public GraphTwoVersions() throws IOException {

		setTitle("TSVizzEvolution - Two Versions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 715, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);

		// Para abrir no centro da Tela
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
        initComponents();
        pnlVisualization.setVisible(true);
        pnlTimeline.setVisible(true);
        btnVisualizeTimeline.setVisible(true);
        pnlMethod.setVisible(false);
		lblVisualizeTimeline.setVisible(true);
		pnlSelectMethod.setVisible(false);
		btnVisualizeTimeline.setEnabled(false);
        
        cbTimeline.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
            	if (event.getItem().equals("Methods")) {
                    pnlMethod.setVisible(true);
                    pnlSelectMethod.setVisible(false);
                } else {
                    pnlMethod.setVisible(false);
                    pnlSelectMethod.setVisible(false);
                }
            }
        });
        
addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SelectVersions frame = new SelectVersions();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
    }

    private void btnChooseFileSearch1ActionPerformed(ActionEvent evt) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(GraphTwoVersions.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtFilePathDefault1.setText(file.getPath());
            //txtFilePathDefault1.setText("C:\\Users\\T-GAMER\\IdeaProjects\\GraphTwoVersions\\src\\tsvizzevolution\\resultado_evolution1.csv");
            nomeDoArquivo = file.getName();
        }
    }
   
    private void btnChooseFileSearch2ActionPerformed(ActionEvent evt) throws IOException {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(GraphTwoVersions.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtFilePathDefault2.setText(file.getPath());
            //txtFilePathDefault2.setText("C:\\Users\\T-GAMER\\IdeaProjects\\GraphTwoVersions\\src\\tsvizzevolution\\resultado_evolution1.csv");
            nomeDoArquivo = file.getName();
            btnGerarUploadActionPerformed(evt);
        }
    }

    private void btnSearchMethodActionPerformed(ActionEvent evt) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(GraphTwoVersions.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtFilePathMethod.setText(file.getPath());
            //txtFilePathMethod.setText("C:\\Users\\T-GAMER\\IdeaProjects\\GraphTwoVersions\\src\\tsvizzevolution\\commons-io_result_byclasstest_testsmells.csv");
            nomeDoArquivo = file.getName();
        }
    }

    private void btnSearchMethod2ActionPerformed(ActionEvent evt) throws IOException {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(GraphTwoVersions.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            txtFilePathMethod2.setText(file.getPath());
            //txtFilePathMethod2.setText("C:\\Users\\T-GAMER\\IdeaProjects\\GraphTwoVersions\\src\\tsvizzevolution\\commons-io_result_byclasstest_testsmells.csv");
            nomeDoArquivo = file.getName();
            List<ClassMethod> l = retorna_lista_classe_metodo();
            List<String> list = new ArrayList<>();
            for(ClassMethod obj: l){
                if(obj.metodos.size() != 0){
                    list.add(obj.classe);
                }
            }
            Collections.sort(list);
            for(String s: list){
                //cbClass.addItem(s);
            }
        }
    }

    
    
    private void cbTimelineActionPerformed(ActionEvent evt) {
    }

    private void btnGerarUploadActionPerformed(ActionEvent evt) throws IOException {
        String[] a = null;
        String[] b = null;
        String[] c = null;
        String[] a2 = null;
        String[] b2 = null;
        String[] c2 = null;
       // txtFilePathDefault1.setText("C:\\Users\\T-GAMER\\IdeaProjects\\GraphTwoVersions\\src\\tsvizzevolution\\commons-io_testsmesll_2_1.csv");
     //   txtFilePathMethod.setText("C:\\Users\\T-GAMER\\IdeaProjects\\GraphTwoVersions\\src\\tsvizzevolution\\all_report_by_testsmells.csv");
        a2 = carrega_lista_linhas(txtFilePathDefault1.getText());
        b2 = carrega_lista_cabecalho(txtFilePathDefault1.getText());
        c2 = carrega_lista_autor(txtFilePathDefault1.getText());

      //  txtFilePathDefault2.setText("C:\\Users\\T-GAMER\\IdeaProjects\\GraphTwoVersions\\src\\tsvizzevolution\\commons-io_testsmesll_2_6.csv");
       // txtFilePathMethod2.setText("C:\\Users\\T-GAMER\\IdeaProjects\\GraphTwoVersions\\src\\tsvizzevolution\\all_report_by_testsmells.csv");
        a = carrega_lista_linhas(txtFilePathDefault2.getText());
        b = carrega_lista_cabecalho(txtFilePathDefault2.getText());
        c = carrega_lista_autor(txtFilePathDefault2.getText());

        a = concatena(a, a2);
        b = concatena(b, b2);
        c = concatena_autor(c, c2);

        
		btnVisualizeTimeline.setEnabled(true);

	}
    public static int retornaDadosDoisNos(String a, String b, String filtro, List<Data> l){
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

    public static Graph removeVertices(Graph graph1, String filtro){
        boolean stop = false;
        while (!stop) {
            boolean Flag = false;
            for (int i = 0; i < graph1.getNodeCount(); i++) {
                Node n1 = graph1.getNode(i);
                if (n1.getDegree() == 0) {
                    if (!(n1.getId().equals(filtro + "_1") || n1.getId().equals(filtro + "_2"))) {
                        Flag = true;
                        graph1.removeNode(n1);
                        break;
                    }
                }
            }
            if (!Flag) {
                stop = true;
            }
        }
        return graph1;
    }

    public static Graph removeVerticesDoisLados(Graph graph1){
        for (int i = 0; i < graph1.getNodeCount(); i++) {
            Node n1 = graph1.getNode(i);
            String complemento = n1.getId().substring(n1.getId().length()-2, n1.getId().length());
            if (complemento.equals("_1")){
                Node n2 = graph1.getNode(n1.getId().substring(0, n1.getId().length()-2) + "_2");
                if (n2 != null){
                    if(n2.getDegree() == 0 && n1.getDegree() == 0){
                        graph1.removeNode(n2);
                        graph1.removeNode(n1);
                        i --;
                    }
                }
            }else{
                Node n2 = graph1.getNode(n1.getId().substring(0, n1.getId().length()-2) + "_1");
                if (n2 != null){
                    if(n2.getDegree() == 0 && n1.getDegree() == 0){
                        graph1.removeNode(n2);
                        graph1.removeNode(n1);
                        i --;
                    }
                }
            }
        }
        return graph1;
    }


    public static Graph criaNosInexistentes(Graph graph, List listaDeLinhas){
        List<String> todasClasses = retornaTodasClasses(listaDeLinhas);
        for (int i = 0; i < graph.getNodeCount(); i++) {
            Node n1 = graph.getNode(i);
            if (in(n1.getId().substring(0, n1.getId().length()-2), todasClasses)) {
                char complemento = n1.getId().charAt(n1.getId().length() - 1);
                char adicional = ' ';
                if (complemento == '1') {
                    adicional = '2';
                } else {
                    adicional = '1';
                }
                String nomeAlterado = n1.getId();
                nomeAlterado = nomeAlterado.substring(0, nomeAlterado.length() - 1) + adicional;
                boolean achou = false;
                for (int j = 0; j < graph.getNodeCount(); j++) {
                    Node no = graph.getNode(j);
                    if (no.getId().equals(nomeAlterado)) {
                        achou = true;
                    }
                }
                if (!achou) {
                    graph.addNode(nomeAlterado);
                    Node n = graph.getNode(nomeAlterado);
                    n.setAttribute("ui.label", nomeAlterado);
                    n.addAttribute("ui.class", "x");
                    n.setAttribute("x", -1000);
                    n.setAttribute("y", 0);
                }
            }
        }

        return graph;
    }

    public static List<Data> OrdenaPeloNumeroOcorrencias(List<Data> l){
        Data[] v = new Data[l.size()];
        for (int i = 0; i < v.length; i++){
            v[i] = l.get(i);
        }

        for(int i = 0; i < v.length - 1; i++) {
            for(int j = 0; j < v.length - 1 - i; j++) {
                if(v[j].valor < v[j + 1].valor) {
                    Data aux = v[j];
                    v[j] = v[j + 1];
                    v[j + 1] = aux;
                }
            }
        }
        l = new ArrayList<Data>();
        for (int i = 0; i < v.length; i++){
            l.add(v[i]);
        }
        return l;
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

    public static List<Data> retornaDados(String file, String filtro){
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
        resposta.remove(0);
        Collections.sort(resposta);
        resposta.remove(1);
        String[] resposta_final = new String[resposta.size()];
        for (int i = 0; i < resposta.size(); i++) {
             resposta_final[i] = (String) resposta.get(i);
        }
        return resposta_final;
    }
    @SuppressWarnings("unchecked")

    private String[] concatena_autor(String[] lista1, String[] lista2){
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
        lista.add(0, "All");
        String[] resultado = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++){
            resultado[i] = lista.get(i);
        }
        return resultado;
    }

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

    public void carregaComponentes(){
        try {
            String[] a = null;
            String[] b = null;
            String[] c = null;
            String[] a2 = null;
            String[] b2 = null;
            String[] c2 = null;

            a2 = carrega_lista_linhas(txtFilePathDefault1.getText());
            b2 = carrega_lista_cabecalho(txtFilePathDefault1.getText());
            c2 = carrega_lista_autor(txtFilePathDefault1.getText());

            a = carrega_lista_linhas(txtFilePathDefault2.getText());
            b = carrega_lista_cabecalho(txtFilePathDefault2.getText());
            c = carrega_lista_autor(txtFilePathDefault2.getText());

            a = concatena(a, a2);
            b = concatena(b, b2);
            c = concatena_autor(c, c2);
           }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List concatenaLista(List lista1, List lista2){
        List lista = new ArrayList<>();
        for (int i = 0; i < lista1.size(); i++){
            lista.add(lista1.get(i));
        }
        for (int i = 0; i < lista2.size(); i++){
            boolean achou = false;
            for (int j = 0; j < lista.size(); j++){
                if (lista.get(j).equals(lista2.get(i))){
                    achou = true;
                }
            }
            if (!achou){
                lista.add(lista2.get(i));
            }
        }
        return lista;
    }

    public static List<String> retornaTodasClasses(List listaDeLinhas){
        List<String> listaClasses = new ArrayList<>();
        for (int i = 0; i < listaDeLinhas.size(); i++){
            String[] linha = (String[]) listaDeLinhas.get(i);
            Boolean encontrou = false;
            for (int j = 0; j < listaClasses.size(); j++){
                if (listaClasses.get(j).equals(linha[6])){
                    encontrou = true;
                }
            }
            if (!encontrou){
                listaClasses.add(linha[6]);
            }
        }
        return listaClasses;
    }

    public static boolean in(String x, List<String> l){
        for (int i = 0; i < l.size(); i++){
            if (x.equals(l.get(i))){
                return true;
            }
        }
        return false;
    }

    public static String[] carrega_lista_autor_test(String path, String autor) throws IOException {
        List<String> resposta_final_array = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String[] cabecalho = reader.readLine().split(VIRGULA);
        String linha = null;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(VIRGULA);
            if(autor.equals(dados[1])){
                for(int i=10; i<dados.length; i++){
                    if(!dados[i].equals("")){
                        if(!dados[i].equals("0")){
                            if(!resposta_final_array.contains(cabecalho[i]))
                                resposta_final_array.add(cabecalho[i]);
                        }
                    }
                }
            }
        }
        Collections.sort(resposta_final_array);
        String[] resposta_final = new String[resposta_final_array.size()];
        for (int i = 0; i < resposta_final_array.size(); i++) {
            resposta_final[i] = (String) resposta_final_array.get(i);
        }
        return resposta_final;
    }

    private List<ClassMethod> retorna_lista_classe_metodo() throws IOException {
        List<ClassMethod> listaMetodosClasse = new ArrayList<>();
        List listaMetodos = new ArrayList();
        String linha = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePathMethod.getText())));
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(VIRGULA);
            listaMetodos.add(dados);
        }

        for (int i = 0; i < listaMetodos.size(); i++) {
            boolean tem = false;
            String[] dado_linha = (String[]) listaMetodos.get(i);
            for (ClassMethod obj : listaMetodosClasse) {
                if (dado_linha[1].equals(obj.classe)) {
                    tem = true;
                }
            }
            if (tem == false) {
                listaMetodosClasse.add(new ClassMethod(dado_linha[1]));
            }
        }
        for (ClassMethod obj : listaMetodosClasse) {
            for (int i = 0; i < listaMetodos.size(); i++) {
                String[] dado_linha = (String[]) listaMetodos.get(i);
                if (obj.classe.equals(dado_linha[1])) {
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
        return listaMetodosClasse;
    }

    private void initComponents() throws IOException {
        pnlGraph = new JPanel();
		pnlTimeline = new JPanel();
	    pnlMethod = new JPanel();
	    txtFilePathDefault1 = new JTextField();
        txtFilePathDefault2 = new JTextField();
        btnSearchMethod = new JButton();
		progress = new JProgressBar(0, 100);
		pnlSelectMethod = new JPanel();
		
		pnlSelectMethod.setVisible(false);

		progress.setStringPainted(true);
		progress.setValue(0);
		progress.setSize(new Dimension(100, 23));
        pnlTimeline.setVisible(true);
        pnlMethod.setVisible(false);
                     /*if (event.getItem().equals("Graph View")) {
                         pnlMethod.setVisible(false);
                         pnlSelectMethod.setVisible(false);
                         pnlTimeline.setVisible(false);
                         btnVisualizeTimeline.setVisible(false);
 						 lblVisualizeTimeline.setVisible(false);}*/
                        /* pnlTimeline.setVisible(true);
                         btnVisualizeTimeline.setVisible(true);
                         pnlMethod.setVisible(false);
 						 lblVisualizeTimeline.setVisible(true);
                         pnlSelectMethod.setVisible(false);*/
                                         
        cbTimeline = new JComboBox<>();
        cbTimeline.setModel(new DefaultComboBoxModel<>(new String[] { "Project",  "All Test Classes", "Methods" }));
        cbTimeline.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		cbTimelineActionPerformed(evt);
            }
        });

        btnChooseFileSearch2 = new JButton();
        btnChooseFileSearch2.setText("Search ...");
        btnChooseFileSearch2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
					btnChooseFileSearch2ActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        btnChooseFileSearch1 = new JButton();
        btnChooseFileSearch1.setText("Search ...");
        btnChooseFileSearch1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnChooseFileSearch1ActionPerformed(evt);
            }
        });
       
        btnSearchMethod.setText("Search ...");
    		btnSearchMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchMethodActionPerformed(evt);
            }
        });

        lblTimeline = new JLabel();
        lblTimeline.setText("Select the level of granularity:");

        lblVisualization = new JLabel();
        lblVisualization.setText("View type: Timeline View");

        lblCsv2 = new JLabel();
        lblCsv2.setText("Select the second .csv File (resultado_evolution1.csv):");
        lblCsv1 = new JLabel();

        lblCsv1.setText("Select the first .csv File (resultado_evolution1.csv):");
        lblCsv1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        pnlVisualization = new JPanel();

 		lblSelectTheCsvMethod = new JLabel();
 		lblSelectTheCsvMethod.setText("Select the first .csv File (''project''_result_byclasstest_testsmells.csv) :");
 		
 		txtFilePathMethod = new JTextField();
 	// txtFilePathMethod.setText("C:\\Users\\T-GAMER\\IdeaProjects\\GraphTwoVersions\\src\\tsvizzevolution\\commons-io_testsmesll_2_6.csv");
 		
 		lblSelectTheSecond = new JLabel();
 		lblSelectTheSecond.setText("Select the second .csv File (''project''_result_byclasstest_testsmells.csv) :");
 		   		
 		txtFilePathMethod2 = new JTextField();
 				
 		btnSearchMethod2 = new JButton();
 		btnSearchMethod2.setText("Search ...");
 	    btnSearchMethod2.addActionListener(new java.awt.event.ActionListener() {
 	    	public void actionPerformed(java.awt.event.ActionEvent evt) {
 	    		try {
					btnSearchMethod2ActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 	        }
 	    });
  				
		lblSelectMethod = new JLabel();
		lblSelectMethod.setText("Select a Method:");

        cbSelectMethod = new JComboBox<String>();

		pnlSelectMethod.setVisible(false);

  		lblSelectMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbSelectMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));
 		btnSearchMethod2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnChooseFileSearch1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnChooseFileSearch2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbTimeline.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTimeline.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCsv1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCsv2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblVisualization.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtFilePathDefault1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtFilePathDefault2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtFilePathMethod.setFont(new Font("Tahoma", Font.PLAIN, 14));
  		lblSelectTheCsvMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));
  		btnSearchMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));
  		lblSelectTheSecond.setFont(new Font("Tahoma", Font.PLAIN, 16));
 		txtFilePathMethod2.setFont(new Font("Tahoma", Font.PLAIN, 14));
 		
lblVisualizeTimeline = new JLabel();
		
		lblVisualizeTimeline.setVisible(true);
		lblVisualizeTimeline.setText("Click here to generate the visualization :");
		lblVisualizeTimeline.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlProgress = new JPanel();
		pnlProgress.setVisible(false);
		
		        btnVisualizeTimeline = new JButton();
		        btnVisualizeTimeline.setText("Generate Timeline View");
		        btnVisualizeTimeline.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	TimelineView window;
						try {
							window = new TimelineView();
							window.setVisible(true);
							dispose();

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }


		        });
		        btnVisualizeTimeline.setVisible(true);
		        btnVisualizeTimeline.setFont(new Font("Tahoma", Font.PLAIN, 16));
		   		
		   		
        GroupLayout gl_pnlSelectMethod = new GroupLayout(pnlSelectMethod);
		gl_pnlSelectMethod.setHorizontalGroup(
			gl_pnlSelectMethod.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSelectMethod.createSequentialGroup()
					.addComponent(lblSelectMethod, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbSelectMethod, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
					.addGap(71))
		);
		gl_pnlSelectMethod.setVerticalGroup(
			gl_pnlSelectMethod.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSelectMethod.createSequentialGroup()
					.addGap(3)
					.addGroup(gl_pnlSelectMethod.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSelectMethod)
						.addComponent(cbSelectMethod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		pnlSelectMethod.setLayout(gl_pnlSelectMethod);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(pnlGraph, GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(pnlGraph, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		   		
		GroupLayout gl_pnlGraph = new GroupLayout(pnlGraph);
		gl_pnlGraph.setHorizontalGroup(
			gl_pnlGraph.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlGraph.createSequentialGroup()
					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlGraph.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCsv1)
								.addGroup(gl_pnlGraph.createSequentialGroup()
									.addComponent(txtFilePathDefault1, GroupLayout.PREFERRED_SIZE, 536, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(btnChooseFileSearch1))
								.addComponent(lblCsv2)
								.addGroup(gl_pnlGraph.createSequentialGroup()
									.addComponent(txtFilePathDefault2, GroupLayout.PREFERRED_SIZE, 536, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(btnChooseFileSearch2))
								.addComponent(pnlVisualization, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
								.addComponent(pnlSelectMethod, GroupLayout.PREFERRED_SIZE, 468, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlGraph.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
								.addComponent(pnlMethod, 0, 0, Short.MAX_VALUE)
								.addGroup(gl_pnlGraph.createSequentialGroup()
									.addComponent(pnlTimeline, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(23))))
						.addGroup(gl_pnlGraph.createSequentialGroup()
							.addContainerGap()
							.addComponent(pnlProgress, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlGraph.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblVisualizeTimeline, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnVisualizeTimeline, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_pnlGraph.setVerticalGroup(
			gl_pnlGraph.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlGraph.createSequentialGroup()
					.addComponent(lblCsv1)
					.addGap(6)
					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
						.addComponent(txtFilePathDefault1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChooseFileSearch1))
					.addGap(13)
					.addComponent(lblCsv2)
					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlGraph.createSequentialGroup()
							.addGap(6)
							.addComponent(txtFilePathDefault2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnChooseFileSearch2))
					.addGap(6)
					.addComponent(pnlVisualization, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlTimeline, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlMethod, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVisualizeTimeline)
						.addComponent(btnVisualizeTimeline))
					.addGap(18)
					.addComponent(pnlProgress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(199)
					.addComponent(pnlSelectMethod, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(75))
		);
		pnlGraph.setLayout(gl_pnlGraph);
		contentPane.setLayout(gl_contentPane);
   		
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
   		
   		GroupLayout gl_pnlMethod = new GroupLayout(pnlMethod);
   		gl_pnlMethod.setHorizontalGroup(
   			gl_pnlMethod.createParallelGroup(Alignment.LEADING)
   				.addGroup(gl_pnlMethod.createSequentialGroup()
   					.addGroup(gl_pnlMethod.createParallelGroup(Alignment.LEADING)
   						.addComponent(lblSelectTheCsvMethod)
   						.addGroup(gl_pnlMethod.createSequentialGroup()
   							.addGap(2)
   							.addComponent(txtFilePathMethod, GroupLayout.PREFERRED_SIZE, 534, GroupLayout.PREFERRED_SIZE)
   							.addPreferredGap(ComponentPlacement.UNRELATED)
   							.addComponent(btnSearchMethod, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
   						.addComponent(lblSelectTheSecond, GroupLayout.PREFERRED_SIZE, 456, GroupLayout.PREFERRED_SIZE)
   						.addGroup(gl_pnlMethod.createSequentialGroup()
   							.addGap(2)
   							.addComponent(txtFilePathMethod2, GroupLayout.PREFERRED_SIZE, 534, GroupLayout.PREFERRED_SIZE)
   							.addPreferredGap(ComponentPlacement.UNRELATED)
   							.addComponent(btnSearchMethod2, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)))
   					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
   		);
   		gl_pnlMethod.setVerticalGroup(
   			gl_pnlMethod.createParallelGroup(Alignment.LEADING)
   				.addGroup(gl_pnlMethod.createSequentialGroup()
   					.addGap(9)
   					.addComponent(lblSelectTheCsvMethod)
   					.addPreferredGap(ComponentPlacement.RELATED)
   					.addGroup(gl_pnlMethod.createParallelGroup(Alignment.BASELINE)
   						.addComponent(btnSearchMethod, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
   						.addComponent(txtFilePathMethod, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
   					.addPreferredGap(ComponentPlacement.RELATED)
   					.addComponent(lblSelectTheSecond, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
   					.addPreferredGap(ComponentPlacement.RELATED)
   					.addGroup(gl_pnlMethod.createParallelGroup(Alignment.BASELINE)
   						.addComponent(btnSearchMethod2, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
   						.addComponent(txtFilePathMethod2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
   					.addContainerGap(41, Short.MAX_VALUE))
   		);
   		pnlMethod.setLayout(gl_pnlMethod);
   		
   		GroupLayout gl_pnlVisualization = new GroupLayout(pnlVisualization);
   		gl_pnlVisualization.setHorizontalGroup(
   			gl_pnlVisualization.createParallelGroup(Alignment.LEADING)
   				.addGroup(gl_pnlVisualization.createSequentialGroup()
   					.addComponent(lblVisualization)
   					.addContainerGap(280, Short.MAX_VALUE))
   		);
   		gl_pnlVisualization.setVerticalGroup(
   			gl_pnlVisualization.createParallelGroup(Alignment.LEADING)
   				.addGroup(gl_pnlVisualization.createSequentialGroup()
   					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
   					.addComponent(lblVisualization)
   					.addContainerGap())
   		);
   		pnlVisualization.setLayout(gl_pnlVisualization);
	}


}