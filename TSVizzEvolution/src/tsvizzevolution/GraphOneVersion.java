package tsvizzevolution;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;

import static tsvizzevolution.GraphTwoVersions.OrdenaPeloNumeroOcorrencias;

public class GraphOneVersion extends javax.swing.JFrame {
	private JButton btnChooseFileSearch;
	private JButton btnVisualizeGraph;
	private JButton btnSearchMethod;
	private JButton btnVisualizeTreemap;

	private JComboBox<String> cbLevel;
	private JComboBox<String> cbClass;
	private JComboBox<String> cbTestSmells;
	private JComboBox<String> cbAuthor;
	private JComboBox<String> cbVisualization;
	private JComboBox<String> cbSelectMethod;
	
	private JLabel lblSelectCsv;
	private JLabel lblLevel;
	private JLabel lblSelectClass;
	private JLabel lblSelectTestSmells;
	private JLabel lblAuthor;
	private JLabel lblSelectTheCsvMethod;
	private JLabel lblVisualization;
	private JLabel lblLoad;
	private JLabel lblVisualizeGraph;
	private JLabel lblVisualizeTreemap;
	private JLabel lblSelectMethod;
	private JLabel lblSelect;

	private JPanel pnlClass;
	private JPanel pnlTestSmells;
	private JPanel pnlAuthor;
	private JPanel pnlGraph;
	private JPanel pnlUpload;
	private JPanel pnlMethod;
	private JPanel pnlbutton;
	private JPanel pnlVisualization;
	private JPanel pnlProgress;
	private JPanel pnlSelectMethod;

	public JFrame frame;
	public JPanel classe;
	public JPanel contentPane;
	public JProgressBar progress;

	private JTextField txtFilePathDefault1;
	private JTextField txtFilePathMethod;

	private static final String VIRGULA = ";";
	private static String nomeDoArquivo;
	private JPanel pnlLevel;
	

	public Thread progressoT = new Thread() {
		
		@Override
		public void run(){

			progress.setValue(0);			
			
//			System.out.println("Entrou na Thread");
	
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

	public void onchange_combo(){

	}

	public GraphOneVersion() throws IOException {
		setTitle("TSVizzEvolution");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 733, 655);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);

		// Para abrir no centro da Tela
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
				
		initComponents();
		pnlClass.setVisible(false);
		pnlTestSmells.setVisible(false);
		pnlAuthor.setVisible(false);
		pnlUpload.setVisible(true);
		pnlMethod.setVisible(false);
		pnlVisualization.setVisible(true);
		btnVisualizeTreemap.setVisible(false);
		btnVisualizeGraph.setVisible(true);
		lblVisualizeGraph.setVisible(true);
		lblVisualizeTreemap.setVisible(false);
		pnlSelectMethod.setVisible(false);

		cbLevel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				String[] a = null;
				if (event.getItem().equals("A Specific Test Smells")) {
					pnlTestSmells.setVisible(true);
					pnlClass.setVisible(false);
					pnlAuthor.setVisible(false);
					pnlMethod.setVisible(false);
					pnlSelectMethod.setVisible(false);
					try {
						a = carrega_lista_linhas(txtFilePathDefault1.getText());
						for(String item: a){
							cbClass.addItem(item);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (event.getItem().equals("A Specific Test Class")) {
					pnlClass.setVisible(true);
					pnlTestSmells.setVisible(false);
					pnlAuthor.setVisible(false);
					pnlMethod.setVisible(false);
					pnlSelectMethod.setVisible(false);
					try {
						a = carrega_lista_linhas(txtFilePathDefault1.getText());
						for(String item: a){
							cbClass.addItem(item);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (event.getItem().equals("Author")) {
					pnlClass.setVisible(false);
					pnlTestSmells.setVisible(true);
					pnlAuthor.setVisible(true);
					pnlMethod.setVisible(false);
					pnlSelectMethod.setVisible(false);
					try {
						a = carrega_lista_linhas(txtFilePathDefault1.getText());
						for(String item: a){
							cbClass.addItem(item);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (event.getItem().equals("Methods")) {
					pnlClass.setVisible(true);
					pnlTestSmells.setVisible(false);
					pnlAuthor.setVisible(false);
					pnlMethod.setVisible(true);
					pnlSelectMethod.setVisible(true);
					cbClass.removeAllItems();
					txtFilePathMethod.setText("");
				} else {
					pnlClass.setVisible(false);
					pnlTestSmells.setVisible(false);
					pnlAuthor.setVisible(false);
					pnlMethod.setVisible(false);
					pnlSelectMethod.setVisible(false);
					try {
						a = carrega_lista_linhas(txtFilePathDefault1.getText());
						for(String item: a){
							cbClass.addItem(item);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	private void btnChooseFileSearchActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(GraphOneVersion.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			txtFilePathDefault1.setText(file.getPath());
			//txtFilePathDefault1.setText("C:\\Users\\T-GAMER\\IdeaProjects\\teste\\src\\tsvizzevolution\\resultado_evolution1.csv");
			nomeDoArquivo = file.getName();
			btnGerarUploadActionPerformed(evt);

		}
	}

	private void btnSearchMethodActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(GraphOneVersion.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			txtFilePathMethod.setText(file.getPath());
			//txtFilePathMethod.setText("C:\\Users\\T-GAMER\\IdeaProjects\\teste\\src\\tsvizzevolution\\commons-io_result_byclasstest_testsmells.csv");
			//nomeDoArquivo = file.getName();
			List<ClassMethod> l = retorna_lista_classe_metodo();
			List<String> list = new ArrayList<>();
			for(ClassMethod obj: l){
				if(obj.metodos.size() != 0){
					list.add(obj.classe);
				}
			}
			Collections.sort(list);
			for(String s: list){
				cbClass.addItem(s);
			}
		}
	}

	private void btnGerarTreemapActionPerformed() {
		CriaXMLTreeMapView(txtFilePathDefault1.getText(), "Project");
		/*frame = new JFrame();
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(1200, 900));
		frame.setMaximumSize(frame.getPreferredSize());
		frame.setMinimumSize(frame.getPreferredSize());
		frame.setTitle("TSVizzEvolution");
		frame.setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JPanel painel = new JPanel();
		painel.setLayout(new BorderLayout());
		painel.setBackground(Configurations.corPainel); // seta a cor de fundo
		painel.setBorder(
				BorderFactory.createLineBorder((Color) Configurations.bordaPainel, Configurations.larguraBorda)); // seta
																													// a
																													// borda
		painel.setPreferredSize(new Dimension(1200, 900));
		painel.setMaximumSize(painel.getPreferredSize());
		painel.setMinimumSize(painel.getPreferredSize());
		frame.getContentPane().add(painel);
		try {
			CriaXMLTreeMapView(txtFilePathDefault1.getText(), "Project");
			//CriaTreeMapView(txtFilePathDefault1.getText(), "Project", painel);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JScrollPane jScrollPane = new JScrollPane(painel);
		jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setVerticalScrollBarPolicy(jScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(jScrollPane);*/
	}

	private void CriaXMLTreeMapView(String fileName1, String filtro){
		List<Data> dados1 = retornaDados(fileName1, filtro);
		dados1 = OrdenaPeloNumeroOcorrencias(dados1);
		int somaTotal = 0;
		for (Data d: dados1) {
			somaTotal += d.valor;
		}

		try {
			FileWriter arq = new FileWriter("Delivery_TreeMap.xml");
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.println("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
			gravarArq.println("<states>");
			gravarArq.println("\t<state name=\"Tree Map\" offices=\"35\" rating=\"47.8\">");
			gravarArq.println("\t\t<cities>");
			for (Data d: dados1) {
				double coverage = (d.valor+ 0.0)/(somaTotal + 0.0);
				String nome = d.nome;
				int valor = d.valor;
				if(valor > 0)
					gravarArq.println("\t\t\t<city test_smells=\"" + nome + "\" occurences=\"" + valor + "\" coverage=\"" + coverage + "\" />");
			}
			gravarArq.println("\t\t</cities>");
			gravarArq.println("\t</state>");
			gravarArq.println("</states>");
			arq.close();
			pnlProgress.setVisible(true);
			progress.setValue(0);
			pnlProgress.add(progress);
			new Thread() {

				@Override
				public void run(){
					progress.setValue(0);
					progressoT.run();
					SwingUtilities.invokeLater(() -> {
						TreemapView window = new TreemapView();
						window.setVisible(true);
						progress.setValue(100);
					});
				}
			}.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void CriaTreeMapView(String fileName1, String filtro, JPanel painel) {

		JPanel pacote = new JPanel();
		pacote.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		pacote.setBackground(Configurations.corPacote); // seta a cor de fundo
		pacote.setBorder(
				BorderFactory.createLineBorder((Color) Configurations.bordaPacote, Configurations.larguraBorda)); // seta
																													// a
																													// borda
		pacote.setPreferredSize(new Dimension(1000, 500));
		ToolTipManager.sharedInstance().setInitialDelay(500);// aparecer� logo que passe 0,5 segundos
		painel.add(pacote);

		List<Data> dados1 = retornaDados(fileName1, filtro);
		dados1 = OrdenaPeloNumeroOcorrencias(dados1);
		int maior_valor = dados1.get(0).valor;

		Random rand = new Random();
		for (Data d : dados1) {
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			classe = new JPanel();
			classe.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			Color c = new Color(r, g, b);
			classe.setBackground(c);
			classe.setBorder(BorderFactory.createLineBorder(Configurations.bordaPacote, 1)); // seta a borda
			classe.setPreferredSize(new Dimension((d.valor * 500) / maior_valor, (d.valor * 500) / maior_valor));
			
			String html_classe = "<html><p><font color=\"#000000\" " + "size=\"4\"face=\"Arial\"><b> "+ d.nome+": <body></b>" + d.valor +"</font></p></html>";
            classe.setToolTipText(html_classe);
			pacote.add(classe);
		}

	}

	public static List<Data> OrdenaPeloNumeroOcorrencias(List<Data> l) {
		Data[] v = new Data[l.size()];
		for (int i = 0; i < v.length; i++) {
			v[i] = l.get(i);
		}
		for (int i = 0; i < v.length - 1; i++) {
			for (int j = 0; j < v.length - 1 - i; j++) {
				if (v[j].valor < v[j + 1].valor) {
					Data aux = v[j];
					v[j] = v[j + 1];
					v[j + 1] = aux;
				}
			}
		}
		l = new ArrayList<Data>();
		for (int i = 0; i < v.length; i++) {
			l.add(v[i]);
		}
		return l;
	}

	private void btnGerarGrafoActionPerformed(java.awt.event.ActionEvent evt) {
		pnlProgress.setVisible(true);
		iniciaProcessamento();
	}

	private void iniciaProcessamento() {
		progress.setValue(0);
		pnlProgress.add(progress);
		
		new Thread() {
			
			@Override
			public void run(){
				progress.setValue(0);
				progressoT.run();
				try {
					System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
					System.setProperty("org.graphstream.ui", "swing");
					Graph graph1 = new DefaultGraph("TSVizzEvolution");
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePathDefault1.getText())));
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
						n.setAttribute("edges", "layout.weight:4");

					}
					progress.setValue(55);
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
					} else {
						coluna = 6;
					}
					List<ClassMethod> listaMetodosClasse = new ArrayList<>();
					if (selecionado.equals("Methods")) {
						reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePathMethod.getText())));
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
						progress.setValue(75);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
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
						progress.setValue(78);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					try {
						if (selecionado.equals("Project") || selecionado.equals("All Test Classes")) {
							CriaGrafoCompleto(listaClassesInt, listaClasses, listaTestSmells, graph1, coluna, 1,
									txtFilePathDefault1.getText(), selecionado);
						} else {
							String filtro = "";
							if (selecionado.equals("A Specific Test Class")) {
								filtro = (String) cbClass.getSelectedItem();
								CriaGrafoParcial(listaClassesInt, listaClasses, listaTestSmells, graph1, filtro, coluna,
										txtFilePathDefault1.getText());
							} else if (selecionado.equals("Author")) {
								filtro = (String) cbTestSmells.getSelectedItem();
								String filtroAutor = (String) cbAuthor.getSelectedItem();
								CriaGrafoParcialAutor(listaClassesInt, listaClasses, listaTestSmells, graph1, filtro,
										filtroAutor, coluna, txtFilePathDefault1.getText());
							} else if (selecionado.equals("Methods")) {
								String testSmell = (String) cbTestSmells.getSelectedItem();
								String classe = (String) cbClass.getSelectedItem();
								CriaGrafoMetodos(listaClassesInt, listaClasses, listaTestSmells, graph1, testSmell, classe,
										coluna, txtFilePathDefault1.getText(), listaMetodosClasse, (String) cbSelectMethod.getSelectedItem());
							} else {
								filtro = (String) cbTestSmells.getSelectedItem();
								CriaGrafoParcial(listaClassesInt, listaClasses, listaTestSmells, graph1, filtro, coluna,
										txtFilePathDefault1.getText());
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					// String path = System.getProperty("user.dir").replace('\\', '/');
					// graph1.addAttribute("ui.stylesheet", "url('" + path +
					// "/src/tsvizzevolution/Config.css')");
					CriaLegenda(graph1);
					graph1.addAttribute("ui.stylesheet", "url('tsvizzevolution/Config.css')");
					progress.setValue(99);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					progress.setValue(100);
					progressoT.interrupt();
					if (graph1.getNodeCount() == 0) {
						String msg = "";
						if (selecionado.equals("Author")) {
							msg = "<html>The combination Test Smell x Author does not exist!";
						}
						if (selecionado.equals("A Specific Test Smells")) {
							msg = "<html>The selected Test Smell has no occurrences in the selected csv file!";
						}
						if (selecionado.equals("Methods")) {
							msg = "<html>The combination Test Class x Test Smell does not exist!";
						}
						JOptionPane optionPane = new JOptionPane();
						optionPane.setMessage(msg);
						optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
						JDialog dialog = optionPane.createDialog(null, "Warning");
						dialog.setVisible(true);
						setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

					} else {
//						Viewer v = graph1.display();
//						v.disableAutoLayout();
//						v.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
					}
				} catch (IOException ex) {
					Logger.getLogger(GraphOneVersion.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}.start();
	}
	private static void CriaGrafoCompleto(List listaClassesInt, List listaClasses, String[] cabecalho, Graph graph1,
			int coluna, int flag, String file, String filtro) throws IOException {
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
			// n1.addAttribute("ui.style", "shape:circle;");
			if (filtro.equals("Project"))
				n1.addAttribute("ui.class", "projeto");
			double x = (Math.random() * ((1000000) + 1) - 1000000);
			double y = (Math.random() * ((1000000) + 1) - 1000000);
			n1.setAttribute("x", x);
			n1.setAttribute("y", y);
			// n1.setAttribute("layout.weight", 10);
			n1.setAttribute("edges", "layout.weight:4");
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

	private static void CriaGrafoParcial(List listaClassesInt, List listaClasses, String[] cabecalho, Graph graph1,
										 String nome, int coluna, String file) throws IOException {
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
			n1.setAttribute("edges", "layout.weight:4");

			for (int j = 10; j < linhaInt.length; j++) {
				if (linhaInt[j] != 0) {
					if (nome.equals(linha[coluna]) || nome.equals(cabecalho[j])) {
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

	private static void CriaGrafoMetodos(List listaClassesInt, List listaClasses, String[] cabecalho, Graph graph1,
			String nome, String classe, int coluna, String file, List<ClassMethod> listaMetodosClasse, String metodoFiltro) throws IOException {
		System.out.println(metodoFiltro);
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
			n1.setAttribute("edges", "layout.weight:4");

			for (int j = 9; j < linhaInt.length; j++) {
				if (linhaInt[j] != 0) {
					if (classe.equals(linha[coluna]) && nome.equals(cabecalho[j])) {
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
		}
		if (graph1.getEdgeCount() > 0) {
			for (ClassMethod obj : listaMetodosClasse) {
				for (MethodData metodo : obj.metodos) {
					if (obj.classe.equals(classe)) {
						try {
							graph1.addNode(metodo.metodo);
							Node n1 = graph1.getNode(metodo.metodo);
							n1.setAttribute("ui.label", metodo.metodo + "," + metodo.begin + "-" + metodo.end);
							n1.addAttribute("ui.class", "metodo");
							double x = (Math.random() * ((1000000) + 1) + 1000000);
							double y = (Math.random() * ((1000000) + 1) + 1000000);
							n1.setAttribute("x", x);
							n1.setAttribute("y", y);
							if (metodo.metodo.equals(metodoFiltro))
								graph1.addEdge(metodo.metodo, obj.classe, metodo.metodo);
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

	private static void CriaGrafoParcialAutor(List listaClassesInt, List listaClasses, String[] cabecalho, Graph graph1,
			String nome, String nomeAutor, int coluna, String file) throws IOException {
		int colunaAutor = 1;
		List<Data> l = retornaDados(file, "All Test Classes");
		List<Data> l2 = retornaDadosAutores(file);
		if (nomeAutor.equals("All")) {
			ArrayList<String> nomeAutores = new ArrayList<>();
			for (int i = 0; i < listaClasses.size(); i++) {
				String[] linha = (String[]) listaClasses.get(i);
				if (!nomeAutores.contains(linha[1])) {
					nomeAutores.add(linha[1]);
				}
			}
			for (int x = 0; x < nomeAutores.size(); x++) {
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
					n1.setAttribute("edges", "layout.weight:4");

					for (int j = 10; j < linhaInt.length; j++) {
						if (linhaInt[j] != 0) {
							if (nomeAutor.equals(linha[colunaAutor]) || nome.equals(cabecalho[j])) {
								if (nome.equals(cabecalho[j])) {
									try {
										graph1.addEdge(cabecalho[j] + " " + linha[colunaAutor], cabecalho[j],
												linha[colunaAutor]);
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
		} else {
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
						if (nome.equals(linha[coluna])
								|| nome.equals(cabecalho[j]) && linha[colunaAutor].equals(nomeAutor)) {
							try {
								graph1.addEdge(cabecalho[j] + " " + linha[coluna], cabecalho[j], linha[coluna]);
								Edge e = graph1.getEdge(cabecalho[j] + " " + linha[coluna]);
								int valor = retornaDadosDoisNos(cabecalho[j], linha[coluna], file, "All Test Classes",
										l);
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
									graph1.addEdge(cabecalho[j] + " " + linha[colunaAutor], cabecalho[j],
											linha[colunaAutor]);
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

	public static int retornaDadosAutorMetodo(String autor, String metodo, String file, List<Data> l) {
		for (int i = 0; i < l.size(); i++) {
			Data d = l.get(i);
			if (d.autor.equals(autor) && d.nome.equals(metodo)) {
				return d.valor;
			}
		}
		return 0;
	}

	public static List<Data> retornaDadosAutores(String file) {
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
			for (int i = 0; i < listaDeLinhas.size(); i++) {
				String[] linha_analisada = (String[]) listaDeLinhas.get(i);
				String autor = linha_analisada[coluna];
				boolean flag = false;
				for (int j = 0; j < autores.size(); j++) {
					if (autores.get(j).equals(autor)) {
						flag = true;
					}
				}
				if (!flag) {
					autores.add(autor);
				}
			}
			for (int i = 0; i < autores.size(); i++) {
				String autor = autores.get(i);
				coluna = 10;
				for (int j = 10; j < cabecalhoLista.length; j++) {
					int soma = 0;
					for (int k = 0; k < listaDeLinhas.size(); k++) {
						String[] linha_analisada = (String[]) listaDeLinhas.get(k);
						int[] linha_int = (int[]) listaDeLinhasInt.get(k);
						if (linha_analisada[1].equals(autor)) {
							soma += linha_int[coluna];
						}
					}
					resultado.add(new Data(cabecalhoLista[j], soma, autor));
					coluna += 1;
				}
			}
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int retornaDadosDoisNos(String a, String b, String file, String filtro, List<Data> l) {
		if (filtro.equals("Project")) {
			for (int i = 0; i < l.size(); i++) {
				Data d = l.get(i);
				if (d.nome.equals(a) && d.projeto.equals(b)) {
					return d.valor;
				}
			}
			return 0;
		} else {
			for (int i = 0; i < l.size(); i++) {
				Data d = l.get(i);
				if (d.nome.equals(a) && d.classe.equals(b)) {
					return d.valor;
				}
			}
			return 0;
		}
	}

	public static List<Data> retornaDados(String file, String filtro) {
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
			if (filtro.equals("All Test Classes")) {
				List<String> classes = new ArrayList<>();
				int coluna = 6;
				for (int i = 0; i < listaClasses.size(); i++) {
					String[] linha_analisada = (String[]) listaClasses.get(i);
					String classe = linha_analisada[coluna];
					boolean flag = false;
					for (int j = 0; j < classes.size(); j++) {
						if (classes.get(j).equals(classe)) {
							flag = true;
						}
					}
					if (!flag) {
						classes.add(classe);
					}
				}
				for (int i = 0; i < classes.size(); i++) {
					String classe_analisada = classes.get(i);
					coluna = 10;
					for (int j = 10; j < listaTestSmells.length; j++) {
						int soma = 0;
						String nome_projeto = "";
						for (int k = 0; k < listaClasses.size(); k++) {
							String[] linha_analisada = (String[]) listaClasses.get(k);
							int[] linha_int = (int[]) listaClassesInt.get(k);
							if (linha_analisada[6].equals(classe_analisada)) {
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
		} catch (Exception e) {
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
			java.util.logging.Logger.getLogger(GraphOneVersion.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(GraphOneVersion.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GraphOneVersion.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GraphOneVersion.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
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

	public static String[] carrega_lista_linhas(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		String linha = null;
		List resposta = new ArrayList();
		while ((linha = reader.readLine()) != null) {
			String[] dados = linha.split(VIRGULA);
			String classe = dados[6];
			boolean flag = false;
			for (int i = 0; i < resposta.size(); i++) {
				if (resposta.get(i).equals(classe)) {
					flag = true;
				}
			}
			if (flag == false) {
				resposta.add(classe);
			}
		}
		Collections.sort(resposta);
		String[] resposta_final = new String[resposta.size() - 1];
		for (int i = 0; i < resposta.size() - 1; i++) {
			resposta_final[i] = (String) resposta.get(i + 1);
		}
		return resposta_final;
	}

	private static Graph CriaLegenda(Graph graph1) {
		SpriteManager sm = new SpriteManager(graph1);
		Sprite legenda = sm.addSprite("legenda");
		legenda.setPosition(StyleConstants.Units.PX, 720, 500, 0);
		graph1.addAttribute("ui.stylesheet" , "" +
				"sprite#legenda  {" +
				"   size: 5px; " +
				"   fill-color: white;  " +
				"   icon-mode: at-left; " +
				"   icon: url('tsvizzevolution/legenda.png');" +
				"}"
		);
		Viewer v = graph1.display();
		v.disableAutoLayout();
		v.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
		return graph1;
	}

	public static String[] carrega_lista_cabecalho(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		String cabecalho_combo;
		cabecalho_combo = reader.readLine();
		String[] cabecalhoTest = cabecalho_combo.split(VIRGULA);
		String[] resultado = new String[cabecalhoTest.length - 10];
		for (int i = 10; i < cabecalhoTest.length; i++) {
			resultado[i - 10] = cabecalhoTest[i];
		}
		List<String> aux = new ArrayList<>();
		for (int i = 0; i < resultado.length; i++) {
			aux.add(resultado[i]);
		}
		Collections.sort(aux);
		for (int i = 0; i < resultado.length; i++) {
			resultado[i] = aux.get(i);
		}
		return resultado;

	}

	public static String[] ordenaVetorString(String[] vetor) {
		String[] resultado = new String[vetor.length];
		List<String> l = new ArrayList<>();
		for (int i = 0; i < vetor.length; i++) {
			l.add(vetor[i]);
		}
		Collections.sort(l);
		for (int i = 0; i < vetor.length; i++) {
			resultado[i] = l.get(i);
		}
		return resultado;
	}

	public static String[] carrega_lista_autor(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		String linha = null;
		List resposta = new ArrayList();
		while ((linha = reader.readLine()) != null) {
			String[] dados = linha.split(VIRGULA);
			String classe = dados[1];
			boolean flag = false;
			for (int i = 0; i < resposta.size(); i++) {
				if (resposta.get(i).equals(classe)) {
					flag = true;
				}
			}
			if (flag == false) {
				resposta.add(classe);
			}
		}
		resposta.remove(0);
		Collections.sort(resposta);
		resposta.add(0, "All");
		String[] resposta_final = new String[resposta.size()];
		for (int i = 0; i < resposta.size(); i++) {
			resposta_final[i] = (String) resposta.get(i);
		}
		return resposta_final;
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

	private void cbVisualizationActionPerformed(ActionEvent evt) {
	}

	private void btnGerarUploadActionPerformed(ActionEvent evt) throws IOException {
		String[] a = null;
		String[] b = null;
		String[] c = null;
		String[] metodos = null;

		a = carrega_lista_linhas(txtFilePathDefault1.getText());
		b = carrega_lista_cabecalho(txtFilePathDefault1.getText());
		c = carrega_lista_autor(txtFilePathDefault1.getText());

		cbAuthor.setModel(new DefaultComboBoxModel<>(c));
		cbClass.setModel(new DefaultComboBoxModel<>(a));
		cbTestSmells.setModel(new DefaultComboBoxModel<>(b));
		
		btnVisualizeGraph.setEnabled(true);
		btnVisualizeTreemap.setEnabled(true);


	}

	private void initComponents() throws IOException {
		btnChooseFileSearch = new JButton();
		btnSearchMethod = new JButton();

		progress = new JProgressBar(0, 100);

		lblSelectCsv = new JLabel();
		lblSelectClass = new JLabel();
		lblSelectTestSmells = new JLabel();
		lblAuthor = new JLabel();
		lblSelectTheCsvMethod = new JLabel();

		pnlClass = new JPanel();
		pnlTestSmells = new JPanel();
		pnlAuthor = new JPanel();
		pnlGraph = new JPanel();
		pnlUpload = new JPanel();
		pnlMethod = new JPanel();
		pnlbutton = new JPanel();
		pnlVisualization = new JPanel();
		pnlLevel = new JPanel();
		pnlProgress = new JPanel();
		pnlSelectMethod = new JPanel();

		cbTestSmells = new JComboBox<>();
		cbClass = new JComboBox<>();
		cbAuthor = new JComboBox<>();

		pnlSelectMethod.setVisible(false);
		pnlProgress.setVisible(false);

		progress.setStringPainted(true);
		progress.setValue(0);
		progress.setSize(new Dimension(100, 23));

		pnlbutton.setVisible(false);

		lblSelectTheCsvMethod.setText("Select the .csv File (''project''_result_byclasstest_testsmells.csv) :");

		txtFilePathMethod = new JTextField();
//		txtFilePathMethod.setText("C:\\Users\\T-GAMER\\IdeaProjects\\teste\\src\\tsvizzevolution\\commons-io_testsmesll_2_6.csv");
		txtFilePathDefault1 = new JTextField();

		pnlUpload.setVisible(true);
		pnlMethod.setVisible(false);
		pnlSelectMethod.setVisible(false);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		lblSelectCsv.setText("Select the .csv File (resultado_evolution1.csv):");

		btnChooseFileSearch.setText("Search ...");
		btnChooseFileSearch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					btnChooseFileSearchActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		cbAuthor.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (!String.valueOf(cbAuthor.getSelectedItem()).equals("All")) {
					try {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							String[] result = carrega_lista_autor_test(txtFilePathDefault1.getText(), String.valueOf(cbAuthor.getSelectedItem()));
							cbTestSmells.setModel(new DefaultComboBoxModel<>(result));
							cbTestSmells.removeAllItems();
							for (String test : result) {
								cbTestSmells.addItem(test);
							}
						} else {
						}
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}else{
					try {
						String[] result = carrega_lista_cabecalho(txtFilePathDefault1.getText());
						Arrays.sort(result);
						cbTestSmells.removeAllItems();
						for (String test : result) {
							cbTestSmells.addItem(test);
						}
					} catch (IOException ioException) {
						ioException.printStackTrace();
					}

				}
			}
		});

		cbSelectMethod = new JComboBox<String>();

		cbClass.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				try {
					List<ClassMethod> l = retorna_lista_classe_metodo();
					cbSelectMethod.removeAllItems();
					for(ClassMethod obj: l){
						if(obj.classe.equals(String.valueOf(cbClass.getSelectedItem())))
							if(obj.metodos.size() > 0) {
								for (MethodData Metodos : obj.metodos) {
									cbSelectMethod.addItem(Metodos.metodo);
								}
							}
					}
				} catch (IOException ioException) {
				}
			}
		});


		btnSearchMethod.setText("Search ...");
		btnSearchMethod.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					btnSearchMethodActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		lblSelectClass.setText("Select a Test Class:");
		lblSelectTestSmells.setText("Select a Test Smells:");
		lblAuthor.setText("Select A Specific Author or All:");

		cbVisualization = new JComboBox<>();
		cbVisualization.setModel(new DefaultComboBoxModel<>(new String[] { "Graph View", "Treemap View" }));
		cbVisualization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cbVisualizationActionPerformed(evt);
			}
		});

		String selecionado2 = (String) cbVisualization.getSelectedItem();
		if (selecionado2.equals("Graph View")) {
			cbVisualization.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (event.getItem().equals("Graph View")) {
						pnlUpload.setVisible(true);
						pnlMethod.setVisible(false);
						pnlSelectMethod.setVisible(false);
						pnlLevel.setVisible(true);
						btnVisualizeGraph.setVisible(true);
						lblVisualizeGraph.setVisible(true);
						btnVisualizeTreemap.setVisible(false);
						lblVisualizeTreemap.setVisible(false);
					} else if (event.getItem().equals("Treemap View")) {
						pnlClass.setVisible(false);
						pnlTestSmells.setVisible(false);
						pnlAuthor.setVisible(false);
						pnlLevel.setVisible(false);
						pnlUpload.setVisible(true);
						pnlMethod.setVisible(false);
						pnlSelectMethod.setVisible(false);
						btnVisualizeGraph.setVisible(false);
						lblVisualizeGraph.setVisible(false);
						btnVisualizeTreemap.setVisible(true);
						lblVisualizeTreemap.setVisible(true);
					}

				}
			});

		}
		pnlSelectMethod.setVisible(false);

		btnChooseFileSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbTestSmells.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSelectCsv.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSelectClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSelectTestSmells.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtFilePathDefault1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtFilePathMethod.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectTheCsvMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSearchMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbVisualization.setFont(new Font("Tahoma", Font.PLAIN, 16));

		// });

		lblSelect = new JLabel("Select a view type:");
		lblSelect.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblLevel = new JLabel();
		lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblLevel.setText("Select the level of granularity:");
		cbLevel = new JComboBox<>();
		cbLevel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Project", "All Test Classes",
				"A Specific Test Class", "A Specific Test Smells", "Author", "Methods" }));
		cbLevel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cbLevelActionPerformed(evt);
			}
		});
		btnVisualizeGraph = new JButton();
		
				btnVisualizeGraph.setText("Generate Graph View");
				btnVisualizeGraph.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnGerarGrafoActionPerformed(evt);
					}
				});
		btnVisualizeGraph.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnVisualizeTreemap = new JButton("Generate Treemap View");
			btnVisualizeTreemap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnGerarTreemapActionPerformed();
			}
		});
				
		btnVisualizeTreemap.setVisible(false);
		
		lblVisualizeGraph = new JLabel("Click here to generate the visualization:");
		
		lblVisualizeTreemap= new JLabel("Click here to generate the visualization:");

		btnVisualizeGraph.setEnabled(false);
		btnVisualizeTreemap.setEnabled(false);

				
		lblSelectMethod = new JLabel();
		lblSelectMethod.setText("Select a Method:");
		
		btnVisualizeTreemap.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVisualizeGraph.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVisualizeTreemap.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSelectMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbSelectMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));



		GroupLayout gl_pnlMethod = new GroupLayout(pnlMethod);
		gl_pnlMethod.setHorizontalGroup(
			gl_pnlMethod.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlMethod.createSequentialGroup()
					.addGroup(gl_pnlMethod.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSelectTheCsvMethod)
						.addGroup(gl_pnlMethod.createSequentialGroup()
							.addGap(2)
							.addComponent(txtFilePathMethod, GroupLayout.PREFERRED_SIZE, 534, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(btnSearchMethod, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		gl_pnlMethod.setVerticalGroup(
			gl_pnlMethod.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlMethod.createSequentialGroup()
					.addGap(9)
					.addComponent(lblSelectTheCsvMethod)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlMethod.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtFilePathMethod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSearchMethod, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		pnlMethod.setLayout(gl_pnlMethod);

		GroupLayout gl_pnlVisualization = new GroupLayout(pnlVisualization);
		gl_pnlVisualization
				.setHorizontalGroup(
						gl_pnlVisualization.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlVisualization.createSequentialGroup().addGap(2).addComponent(lblSelect)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(cbVisualization,
												GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
										.addGap(176)));
		gl_pnlVisualization.setVerticalGroup(gl_pnlVisualization.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlVisualization.createSequentialGroup().addGap(5)
						.addGroup(gl_pnlVisualization
								.createParallelGroup(Alignment.BASELINE).addComponent(cbVisualization,
										GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSelect))));
		pnlVisualization.setLayout(gl_pnlVisualization);

		GroupLayout gl_pnlLevel = new GroupLayout(pnlLevel);
		gl_pnlLevel.setHorizontalGroup(
			gl_pnlLevel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLevel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblLevel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cbLevel, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(584, Short.MAX_VALUE))
		);
		gl_pnlLevel.setVerticalGroup(
			gl_pnlLevel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLevel.createSequentialGroup()
					.addGroup(gl_pnlLevel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbLevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLevel))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlLevel.setLayout(gl_pnlLevel);

		GroupLayout gl_pnlAuthor = new GroupLayout(pnlAuthor);
		gl_pnlAuthor.setHorizontalGroup(gl_pnlAuthor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlAuthor.createSequentialGroup()
						.addComponent(lblAuthor, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbAuthor, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(111, Short.MAX_VALUE)));
		gl_pnlAuthor.setVerticalGroup(gl_pnlAuthor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlAuthor.createSequentialGroup().addContainerGap()
						.addGroup(gl_pnlAuthor.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAuthor, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbAuthor, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(51, Short.MAX_VALUE)));
		pnlAuthor.setLayout(gl_pnlAuthor);

		GroupLayout gl_pnlTestSmells = new GroupLayout(pnlTestSmells);
		gl_pnlTestSmells.setHorizontalGroup(gl_pnlTestSmells.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTestSmells.createSequentialGroup()
						.addComponent(lblSelectTestSmells, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbTestSmells, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(191, Short.MAX_VALUE)));
		gl_pnlTestSmells
				.setVerticalGroup(gl_pnlTestSmells.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlTestSmells.createSequentialGroup().addContainerGap()
								.addGroup(gl_pnlTestSmells.createParallelGroup(Alignment.TRAILING)
										.addComponent(cbTestSmells, GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblSelectTestSmells, GroupLayout.PREFERRED_SIZE, 20,
												GroupLayout.PREFERRED_SIZE))
								.addContainerGap(37, Short.MAX_VALUE)));
		pnlTestSmells.setLayout(gl_pnlTestSmells);

		GroupLayout gl_pnlClass = new GroupLayout(pnlClass);
		gl_pnlClass.setHorizontalGroup(gl_pnlClass.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlClass.createSequentialGroup()
						.addComponent(lblSelectClass, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(cbClass, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(128, Short.MAX_VALUE)));
		gl_pnlClass.setVerticalGroup(gl_pnlClass.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlClass
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_pnlClass.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSelectClass, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbClass, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(37, Short.MAX_VALUE)));
		pnlClass.setLayout(gl_pnlClass);

		GroupLayout gl_pnlGraph = new GroupLayout(pnlGraph);
		gl_pnlGraph.setHorizontalGroup(
			gl_pnlGraph.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlGraph.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlClass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSelectCsv)
						.addGroup(gl_pnlGraph.createSequentialGroup()
							.addComponent(txtFilePathDefault1, GroupLayout.PREFERRED_SIZE, 535, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnChooseFileSearch))
						.addComponent(pnlVisualization, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlLevel, GroupLayout.PREFERRED_SIZE, 999, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlMethod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlGraph.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblVisualizeGraph, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnVisualizeGraph, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlGraph.createSequentialGroup()
							.addComponent(lblVisualizeTreemap, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(btnVisualizeTreemap, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE))
						.addComponent(pnlProgress, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_pnlGraph.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlAuthor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_pnlGraph.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlTestSmells, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_pnlGraph.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlSelectMethod, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
					.addGap(639))
		);
		gl_pnlGraph.setVerticalGroup(
			gl_pnlGraph.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlGraph.createSequentialGroup()
					.addGap(6)
					.addComponent(lblSelectCsv)
					.addGap(6)
					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtFilePathDefault1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChooseFileSearch))
					.addGap(12)
					.addComponent(pnlVisualization, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(pnlLevel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(pnlMethod, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlClass, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlAuthor, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlTestSmells, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlSelectMethod, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnVisualizeGraph)
						.addComponent(lblVisualizeGraph))
					.addGap(6)
					.addGroup(gl_pnlGraph.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlGraph.createSequentialGroup()
							.addGap(4)
							.addComponent(lblVisualizeTreemap))
						.addComponent(btnVisualizeTreemap))
					.addGap(1)
					.addComponent(pnlProgress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		
		GroupLayout gl_pnlSelectMethod = new GroupLayout(pnlSelectMethod);
		gl_pnlSelectMethod.setHorizontalGroup(
			gl_pnlSelectMethod.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSelectMethod.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelectMethod)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbSelectMethod, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(39, Short.MAX_VALUE))
		);
		gl_pnlSelectMethod.setVerticalGroup(
			gl_pnlSelectMethod.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSelectMethod.createSequentialGroup()
					.addGroup(gl_pnlSelectMethod.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbSelectMethod, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSelectMethod))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlSelectMethod.setLayout(gl_pnlSelectMethod);
		pnlGraph.setLayout(gl_pnlGraph);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(pnlGraph, GroupLayout.PREFERRED_SIZE, 689, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(11)
					.addComponent(pnlGraph, GroupLayout.PREFERRED_SIZE, 474, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);

		pack();

	}
}
