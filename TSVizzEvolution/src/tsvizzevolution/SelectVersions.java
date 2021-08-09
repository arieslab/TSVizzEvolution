package tsvizzevolution;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.print.DocFlavor.URL;
import javax.swing.BorderFactory;

import java.awt.Color;

public class SelectVersions extends JFrame {

	private JPanel contentPane;
    private JComboBox<String> cbVersions;
    private JLabel lblVersions;

    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the frame.
	 */
	public SelectVersions() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 733, 420);
		//setTitle("TSVizzEvolution");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		
		// Para abrir no centro da Tela
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
				
//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
//		this.setUndecorated(true);
//		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
//		
		JLabel lblVersions = new JLabel();
		lblVersions.setText("Select the number of versions to view: ");
		lblVersions.setFont(new Font("Tahoma", Font.PLAIN, 16));

		cbVersions = new JComboBox();
		cbVersions.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbVersions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"", "1",  "2" }));

		cbVersions.addActionListener(new java.awt.event.ActionListener() {
	         public void actionPerformed(java.awt.event.ActionEvent evt) { 
	             	try {
	             		cbVersionsActionPerformed(evt);
					} catch (IOException e) {
						e.printStackTrace();
					} 
	             }

	 		private void cbVersionsActionPerformed(ActionEvent evt) throws IOException {
	 			if(cbVersions.getSelectedItem().equals("1")){
	 	            OneVersion g = new OneVersion();
					OneVersion.main(null);	 
					dispose();
	 	        }else if(cbVersions.getSelectedItem().equals("2")) {
	 	            TwoVersions gt = new TwoVersions();
	 	           TwoVersions.main(null);	
					dispose();
	 	        }	 			
	 		}
	         });
		
		JLabel lblTsvizzevolution = new JLabel("<html><body><b>TSVizzEvolution</body></html>");
		lblTsvizzevolution.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JLabel lblTextApresentation = new JLabel();
		lblTextApresentation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTextApresentation.setText("<html><body>      TSVizzEvolution displays the occurrences and evolution of test smells in java software through software visualization technique.<br><br> First, you must select whether to analyze one or two versions. <br><br>For one version we have two views available, Graph View and Treemap View. <br><br>For two version we have one views available, Timeline View.</body></html>");		lblTextApresentation.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblImage = new JLabel();
		lblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblImage.setBounds(225, 25, 225, 225);
		//lblImage.setIcon(new ImageIcon("logo_tsvizz.png"));
		
		java.net.URL url = SelectVersions.class.getResource("logo_tsvizz.png");
		lblImage.setIcon(new ImageIcon(url));
		
		//JButton btnOk = new JButton("OK");
////		btnOk.addActionListener(new ActionListener() { 
////			public void actionPerformed(ActionEvent arg0) { 
////				cbVersions.addItemListener(new ItemListener() {
////				    public void itemStateChanged(ItemEvent event) {
////				        if (event.equals(1)) {
////						    try {
////								GraphOneVersion g = new GraphOneVersion();
////								GraphOneVersion.main(null);	                	
////							} catch (IOException e) {
////								// TODO Auto-generated catch block
////								e.printStackTrace();
////							}
////				        } else {
////							try {
////								GraphTwoVersions g2 = new GraphTwoVersions();
////								GraphTwoVersions.main(null);	
////							} catch (IOException e) {
////								// TODO Auto-generated catch block
////								e.printStackTrace();
////							}
////				        }
////				    }
////  });					/// colocar condição se 1
////				
////			}
////		});
//		
//		JButton btnCancel = new JButton("Cancel");
//		btnCancel.addActionListener(new ActionListener() { 
//			public void actionPerformed(ActionEvent arg0) { 
//				System.exit(0);
//			}
//		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTextApresentation, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblVersions)
								.addComponent(cbVersions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(199)
							.addComponent(lblTsvizzevolution, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTsvizzevolution, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblTextApresentation)
							.addGap(34)
							.addComponent(lblVersions)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbVersions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(81, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
