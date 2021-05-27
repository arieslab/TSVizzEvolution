package tsvizzevolution;
import com.mindfusion.diagramming.*;
import com.mindfusion.drawing.GraphicsUnit;
import com.mindfusion.drawing.SolidBrush;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.nio.file.Paths;
import java.util.Random;


public class TreemapView extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TreemapView window = new TreemapView();
            window.setVisible(true);
        });
    }

    public TreemapView() {
      //  setSize(new Dimension(1200, 700));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
        setTitle("Treemap View");
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        scale = new Color[]{
                Color.decode("#20C300"),
                Color.decode("#FFFF00"),
                Color.decode("#FF8000")
        };

        diagram = new Diagram();
        diagram.setDefaultShape(com.mindfusion.diagramming.Shape.fromId("Rectangle"));
        diagram.setBackBrush(new SolidBrush(Color.WHITE));
        diagram.setMeasureUnit(GraphicsUnit.Millimeter);
        diagram.setBounds(new Rectangle2D.Float(0,0,1000,600));

        diagramView = new DiagramView(diagram);
        diagramView.setBounds(100, 100, 1000, 600);
        getContentPane().add(BorderLayout.CENTER, diagramView );
        
        assignData();
        assignWeights();
        assignColors();
        createLayout();

    }

    private void assignColors() {
        double biggest_cover = 0.0;
        for (DiagramNode node : diagram.getNodes()) {
            if (node instanceof ContainerNode) {
                ContainerNode container = (ContainerNode) node;
                for (DiagramNode child : container.getSubordinateGroup().getAttachedNodes()) {
                    if (child.getTag() instanceof Element) {
                        Element element = (Element) child.getTag();
                        int toCover = Integer.parseInt(element.getAttribute("occurences"));
                        double coverage = Double.parseDouble(element.getAttribute("coverage")) * 1000;
                        biggest_cover = Math.max(biggest_cover, toCover * (coverage / 100.0d));
                    }
                }
            }
        }


        for (DiagramNode node : diagram.getNodes()) {
            if (node instanceof ContainerNode || node.getLocked())
                continue;

            if (node instanceof ShapeNode) {
                if (node.getTag() instanceof Element) {
                    Element element = (Element) node.getTag();
                    double toCover = Double.parseDouble(element.getAttribute("occurences"));
                    double coverage = Double.parseDouble(element.getAttribute("coverage")) * 1000;
                    double value = toCover * (coverage / 100.0d);
                    node.setBrush(new SolidBrush(getScaledColor(value, biggest_cover)));
                }
            }
        }
    }

    private Color getScaledColor(double value, double total) {
        Random random = new Random();
        return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
    }


    private void assignData() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        try {
            builder = docFactory.newDocumentBuilder();
            File file = new File("Delivery_TreeMap.xml");
            document = builder.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Element element = document.getDocumentElement();
        stateNodes = element.getElementsByTagName("state");

        Element state = (Element) stateNodes.item(0);
        String name = state.getAttribute("test_smells");
        Element employeesEl = (Element) state.getElementsByTagName("cities").item(0);
        NodeList employeesList = employeesEl.getElementsByTagName("city");

        ContainerNode node = diagram.getFactory().createContainerNode(0, 0, 10, 10);
        node.setCaption(name);

        for (int j = 0; j < employeesList.getLength(); j++) {
            Element employeesData = (Element) employeesList.item(j);
            String city = employeesData.getAttribute("test_smells");
            int employeeCount = Integer.parseInt(employeesData.getAttribute("occurences"));
            double coverage = Double.parseDouble(employeesData.getAttribute("coverage"));

            ShapeNode shapeNode = diagram.getFactory().createShapeNode(0, 0, 10, 10);
            String dados = city + "<br>" + employeeCount;
            shapeNode.setText(dados);
            shapeNode.setTag(employeesData);
            //shapeNode.setToolTip(String.format("Test Smells %s with %d occurences",city, employeeCount, coverage));
            
                 
            String html_classe = "<html><p><font color=\"#000000\" " + "size=\"4\"face=\"Arial\"><b> " +
            "Test Smells: <body></b>" + city + "<b><br> Occurrences: <body></b>" + employeeCount + "</font></p></html>";
            shapeNode.setToolTip(html_classe);
            
            node.add(shapeNode);

        }
    }


    private void assignWeights() {

        for (DiagramNode node : diagram.getNodes()) {
            if (node instanceof ContainerNode || node.getLocked())
                continue;

            if (node instanceof ShapeNode) {
                if (node.getTag() instanceof Element) {
                    Element element = (Element) node.getTag();
                    float cov = Float.parseFloat(element.getAttribute("coverage"));
                    cov = cov * 100;
                    node.setWeight(cov);
                }
            }
        }
    }

    private void createLayout()  {
        TreeMapLayout layout = new TreeMapLayout();
        layout.setPadding(0);
        layout.setContainerPadding(3);//4
        layout.setLayoutArea(new Rectangle2D.Float(0, 0, 700/coef, 400/coef)); //540
        layout.arrange(diagram);
      // diagramView.zoomToFit();

    }
        Diagram diagram;
        DiagramView diagramView;
        private NodeList stateNodes;
        private Color[] scale;
        private final float coef = 3;
        private static final long serialVersionUID = 1;

}
