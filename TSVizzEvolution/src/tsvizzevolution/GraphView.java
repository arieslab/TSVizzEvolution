package tsvizzevolution;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphView {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JProgressBar progress;
    
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
    
    public void iniciaProcessamento(String nameFile, JProgressBar progressP, JPanel pnlProgress, JComboBox<String> cbLevel, 
                                    JComboBox<String> cbClass, JComboBox<String> cbTestSmells, 
                                    JComboBox<String> cbAuthor, JComboBox<String> cbSelectMethod) {
        this.progress = progressP;
        progress.setValue(0);
        pnlProgress.add(progress);
        new Thread() {
            @Override
            public void run(){
                progress.setValue(0);
                progressoT.run();
                try {                 
                    // dispose();
                    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
                    System.setProperty("org.graphstream.ui", "swing");
                    Graph graph1 = new DefaultGraph("TSVizzEvolution");
                                     
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile)));
                    String linha = null;

                    List listaClassesInt = new ArrayList();
                    List listaClasses = new ArrayList();
                    List listaMetodos = new ArrayList();

                    String cabecalho;

                    cabecalho = reader.readLine();

                    String[] listaTestSmells = cabecalho.split(";");
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
                        n.setAttribute("edges", "layout.weight:4");
                    }
                    progress.setValue(55);
                    if (cabecalho != null) {
                        while ((linha = reader.readLine()) != null) {
                            String[] dados = linha.split(";");
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
                        reader = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile)));
                        while ((linha = reader.readLine()) != null) {
                            String[] dados = linha.split(";");
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
                                   // String end;
                                    try {
                                        begin = dado_linha[9];
                                    }catch (Exception e){
                                        begin = "0";
                                    }
                                    /*try {
                                        end = dado_linha[10];
                                    }catch (Exception e){
                                        end = "0";
                                    }*/
                                    obj.addMethods(new MethodData(dado_linha[8], begin/*, end*/));
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
                                    nameFile, selecionado);
                        } else {
                            String filtro = "";
                            if (selecionado.equals("A Specific Test Class")) {
                                filtro = (String) cbClass.getSelectedItem();
                                CriaGrafoParcial(listaClassesInt, listaClasses, listaTestSmells, graph1, filtro, coluna,
                                        nameFile);
                            } else if (selecionado.equals("Author")) {
                                filtro = (String) cbTestSmells.getSelectedItem();
                                String filtroAutor = (String) cbAuthor.getSelectedItem();
                                CriaGrafoParcialAutor(listaClassesInt, listaClasses, listaTestSmells, graph1, filtro,
                                        filtroAutor, coluna, nameFile);
                            } else if (selecionado.equals("Methods")) {
                                String testSmell = (String) cbTestSmells.getSelectedItem();
                                String classe = (String) cbClass.getSelectedItem();
                                CriaGrafoMetodos(listaClassesInt, listaClasses, listaTestSmells, graph1, testSmell, classe,
                                        coluna, nameFile, listaMetodosClasse, (String) cbSelectMethod.getSelectedItem());
                            } else {
                                filtro = (String) cbTestSmells.getSelectedItem();
                                CriaGrafoParcial(listaClassesInt, listaClasses, listaTestSmells, graph1, filtro, coluna,
                                        nameFile);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                        //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    } else {
                        CriaLegenda(graph1);
                        graph1.addAttribute("ui.stylesheet", "url('tsvizzevolution/Config.css')");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(OneVersion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    public static int converteInteiro(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
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
            if (filtro.equals("Project"))
                n1.addAttribute("ui.class", "projeto");
            double x = (Math.random() * ((1000000) + 1) - 1000000);
            double y = (Math.random() * ((1000000) + 1) - 1000000);
            n1.setAttribute("x", x);
            n1.setAttribute("y", y);
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
       // System.out.println(metodoFiltro);
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
                            n1.setAttribute("ui.label", metodo.metodo + ";" + metodo.begin + "-" /*+ metodo.end*/);
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
							if (!nome.equals("All")) {
								if (nomeAutor.equals(linha[colunaAutor]) || nome.equals(cabecalho[j])) {
									if (nome.equals(cabecalho[j])) {
										try {
											graph1.addEdge(cabecalho[j] + " " + linha[colunaAutor], cabecalho[j],
													linha[colunaAutor]);
											Edge e = graph1.getEdge(cabecalho[j] + " " + linha[colunaAutor]);
											int valor = retornaDadosAutorMetodo(linha[colunaAutor], cabecalho[j], file,
													l2);
											e.setAttribute("ui.label", valor);
										} catch (Exception e) {
										}
									}
								}
							} else {
								if (nomeAutor.equals(linha[colunaAutor])) {
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
                        if (!nome.equals("All")) {
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
                        }else{
                            if (linha[colunaAutor].equals(nomeAutor)) {
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
            String[] listaTestSmells = cabecalho.split(";");
            if (cabecalho != null) {
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(";");
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

    private static Graph CriaLegenda(Graph graph1) {
        SpriteManager sm = new SpriteManager(graph1);
        Sprite legenda = sm.addSprite("legenda");
        legenda.setPosition(StyleConstants.Units.PX, 700, 475, 0);
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

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
