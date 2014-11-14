package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import controller.Controller;
import javax.swing.JEditorPane;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;

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
		gridBagLayout.columnWidths = new int[]{0, 204, 0};
		gridBagLayout.rowHeights = new int[]{53, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblLoadAMap = new JLabel("Load a map");
		lblLoadAMap.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		GridBagConstraints gbc_lblLoadAMap = new GridBagConstraints();
		gbc_lblLoadAMap.gridwidth = 2;
		gbc_lblLoadAMap.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoadAMap.gridx = 0;
		gbc_lblLoadAMap.gridy = 0;
		frame.getContentPane().add(lblLoadAMap, gbc_lblLoadAMap);
		
		JButton btnLoadMap = new JButton("Load");
		GridBagConstraints gbc_btnLoadMap = new GridBagConstraints();
		gbc_btnLoadMap.insets = new Insets(0, 0, 0, 5);
		gbc_btnLoadMap.gridx = 0;
		gbc_btnLoadMap.gridy = 1;
		frame.getContentPane().add(btnLoadMap, gbc_btnLoadMap);
		
		final JEditorPane dtrpnPathToMap = new JEditorPane();
		dtrpnPathToMap.setToolTipText("Path to map ...");
		GridBagConstraints gbc_dtrpnPathToMap = new GridBagConstraints();
		gbc_dtrpnPathToMap.fill = GridBagConstraints.BOTH;
		gbc_dtrpnPathToMap.gridx = 1;
		gbc_dtrpnPathToMap.gridy = 1;
		frame.getContentPane().add(dtrpnPathToMap, gbc_dtrpnPathToMap);
		
		btnLoadMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!dtrpnPathToMap.getText().isEmpty())
					con.loadMapOnPath(dtrpnPathToMap.getText());
			}
		});
	}

}
