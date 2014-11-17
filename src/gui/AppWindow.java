package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sun.media.jai.opimage.AddCollectionCRIF;

import controller.Controller;

public class AppWindow {

	private JFrame frame;
	private Controller con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow window = new AppWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppWindow() {
		initialize();
		con = new Controller();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 325, 79, 0};
		gridBagLayout.rowHeights = new int[]{53, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblLoadAMap = new JLabel("Load a map");
		lblLoadAMap.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		GridBagConstraints gbc_lblLoadAMap = new GridBagConstraints();
		gbc_lblLoadAMap.gridwidth = 2;
		gbc_lblLoadAMap.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoadAMap.gridx = 0;
		gbc_lblLoadAMap.gridy = 0;
		frame.getContentPane().add(lblLoadAMap, gbc_lblLoadAMap);
		
		final JLabel initMap = new JLabel("");
		GridBagConstraints gbc_initMap = new GridBagConstraints();
		gbc_initMap.gridheight = 2;
		gbc_initMap.insets = new Insets(0, 0, 5, 0);
		gbc_initMap.gridx = 2;
		gbc_initMap.gridy = 0;
		frame.getContentPane().add(initMap, gbc_initMap);
		
		JButton btnLoadMap = new JButton("Load");
		GridBagConstraints gbc_btnLoadMap = new GridBagConstraints();
		gbc_btnLoadMap.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadMap.gridx = 0;
		gbc_btnLoadMap.gridy = 1;
		frame.getContentPane().add(btnLoadMap, gbc_btnLoadMap);
		
		final JEditorPane dtrpnPathToMap = new JEditorPane();
		dtrpnPathToMap.setToolTipText("Path to map ...");
		GridBagConstraints gbc_dtrpnPathToMap = new GridBagConstraints();
		gbc_dtrpnPathToMap.insets = new Insets(0, 0, 5, 5);
		gbc_dtrpnPathToMap.fill = GridBagConstraints.BOTH;
		gbc_dtrpnPathToMap.gridx = 1;
		gbc_dtrpnPathToMap.gridy = 1;
		frame.getContentPane().add(dtrpnPathToMap, gbc_dtrpnPathToMap);
		
		JLabel lblLoadARule = new JLabel("Load a rule");
		lblLoadARule.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		GridBagConstraints gbc_lblLoadARule = new GridBagConstraints();
		gbc_lblLoadARule.gridwidth = 2;
		gbc_lblLoadARule.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoadARule.gridx = 0;
		gbc_lblLoadARule.gridy = 2;
		frame.getContentPane().add(lblLoadARule, gbc_lblLoadARule);
		
		JButton btnLoadRule = new JButton("Load");
		GridBagConstraints gbc_btnLoadRule = new GridBagConstraints();
		gbc_btnLoadRule.insets = new Insets(0, 0, 0, 5);
		gbc_btnLoadRule.gridx = 0;
		gbc_btnLoadRule.gridy = 3;
		frame.getContentPane().add(btnLoadRule, gbc_btnLoadRule);
		
		final JEditorPane dtrpnPathToRule = new JEditorPane();
		GridBagConstraints gbc_dtrpnPathToRule = new GridBagConstraints();
		gbc_dtrpnPathToRule.fill = GridBagConstraints.BOTH;
		gbc_dtrpnPathToRule.insets = new Insets(0, 0, 0, 5);
		gbc_dtrpnPathToRule.gridx = 1;
		gbc_dtrpnPathToRule.gridy = 3;
		frame.getContentPane().add(dtrpnPathToRule, gbc_dtrpnPathToRule);
		
		btnLoadMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!dtrpnPathToMap.getText().isEmpty()) {
					con.loadMapOnPath(dtrpnPathToMap.getText());
					BufferedImage map;
					try {
						map = ImageIO.read(new File(dtrpnPathToMap.getText()));
						
						initMap.setIcon(new ImageIcon(map.getScaledInstance(400, 400, Image.SCALE_SMOOTH)));
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
		});
		
		btnLoadRule.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				con.loadRuleOnPath(dtrpnPathToRule.getText());
			}
		});
	}

}
