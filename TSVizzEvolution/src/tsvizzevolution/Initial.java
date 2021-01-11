package tsvizzevolution;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Initial extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Initial frame = new Initial();
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
	public Initial() {
		setTitle("TSVizzEvolution");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Para abrir no centro da Tela
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
			
		JPanel pnlPrincipal = new JPanel();
	//JLabel lblImage = new JLabel(new ImageIcon(getClass().getResource("url('tsvizzevolution/logo_tsvizz.png')")));
		
		JLabel lblImage = new JLabel();
		lblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblImage.setBounds(275, 30, 225, 225);
		lblImage.setIcon(new ImageIcon("logo_tsvizz.png"));
		pnlPrincipal.add(lblImage);
		
		JLabel lblTsvizzevolution = new JLabel("<html><body><b>TSVizzEvolution</body></html>");
		lblTsvizzevolution.setFont(new Font("Tahoma", Font.PLAIN, 22));
		
		JLabel lblTextApresentation = new JLabel();
		lblTextApresentation.setHorizontalAlignment(SwingConstants.CENTER);
		//"TSVizzEvolution displays the occurrences and evolution of test smells in java software through software visualization techniques." + \n\n First, you must select whether to review one or two versions. For one version we have two views available, Graph View and Treemap View. For two versions, Graph View and Timeline View.");
		lblTextApresentation.setText("<html><body>      TSVizzEvolution displays the occurrences and evolution of test smells in java software through software visualization technique<br> First, you must select whether to review one or two versions. <br>For one version we have two views available, Graph View and Treemap View. <br>For two versions, Graph View and Timeline View.</body></html>");

		JButton btnInitial = new JButton("Click here to start");
		btnInitial.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnInitial.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnInitialActionPerformed(evt);
			}

			private void btnInitialActionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				SelectVersions v = new SelectVersions();
				SelectVersions.main(null);
				dispose();
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlPrincipal, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(pnlPrincipal, GroupLayout.PREFERRED_SIZE, 251, Short.MAX_VALUE)
		);
		GroupLayout gl_pnlPrincipal = new GroupLayout(pnlPrincipal);
		gl_pnlPrincipal.setHorizontalGroup(
			gl_pnlPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlPrincipal.createSequentialGroup()
					.addContainerGap(232, Short.MAX_VALUE)
					.addComponent(lblTsvizzevolution)
					.addGap(253))
				.addGroup(gl_pnlPrincipal.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTextApresentation, GroupLayout.PREFERRED_SIZE, 644, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_pnlPrincipal.createSequentialGroup()
					.addGap(207)
					.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(232, Short.MAX_VALUE))
				.addGroup(gl_pnlPrincipal.createSequentialGroup()
					.addGap(251)
					.addComponent(btnInitial, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(286, Short.MAX_VALUE))
		);
		gl_pnlPrincipal.setVerticalGroup(
			gl_pnlPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlPrincipal.createSequentialGroup()
					.addGap(36)
					.addComponent(lblTsvizzevolution)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTextApresentation, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addGap(36)
					.addComponent(btnInitial)
					.addContainerGap(49, Short.MAX_VALUE))
		);
		pnlPrincipal.setLayout(gl_pnlPrincipal);
		contentPane.setLayout(gl_contentPane);
	}
}