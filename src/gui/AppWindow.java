package gui;

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
//import com.sun.media.jai.opimage.AddCollectionCRIF;
import controller.Controller;
import javax.swing.JProgressBar;

public class AppWindow implements ActionListener {

	public JFrame frame;
	private Controller con;

	JLabel initMap;
	JLabel currentMap;
	
	// Buttons
	JButton btnStep;
	JButton btnLoadMap;
	JButton btnLoadRule;
	JButton btnGo;
	private JButton btnAdd;
	
	// EditTexts
	private JEditorPane dtrpnPathToMap;
	private JEditorPane dtrpnPathToRule;
	private JLabel lblCurrentTimestep;
	private JLabel lblAmountOfAgents;
	private JProgressBar progressBar;

	
	/**
	 * Create the application.
	 */
	public AppWindow(Controller con) {
		this.con = con;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 307, 79, 0};
		gridBagLayout.rowHeights = new int[]{53, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblLoadAMap = new JLabel("Load a map");
		lblLoadAMap.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		GridBagConstraints gbc_lblLoadAMap = new GridBagConstraints();
		gbc_lblLoadAMap.gridwidth = 2;
		gbc_lblLoadAMap.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoadAMap.gridx = 0;
		gbc_lblLoadAMap.gridy = 0;
		frame.getContentPane().add(lblLoadAMap, gbc_lblLoadAMap);

		initMap = new JLabel("");
		GridBagConstraints gbc_initMap = new GridBagConstraints();
		gbc_initMap.gridheight = 3;
		gbc_initMap.insets = new Insets(0, 0, 5, 0);
		gbc_initMap.gridx = 2;
		gbc_initMap.gridy = 1;
		frame.getContentPane().add(initMap, gbc_initMap);

		currentMap = new JLabel("");
		GridBagConstraints gbc_currentMap = new GridBagConstraints();
		gbc_currentMap.insets = new Insets(0, 0, 5, 0);
		gbc_currentMap.gridheight = 3;
		gbc_currentMap.gridx = 2;
		gbc_currentMap.gridy = 4;
		frame.getContentPane().add(currentMap, gbc_currentMap);

		btnLoadMap = new JButton("Load");
		GridBagConstraints gbc_btnLoadMap = new GridBagConstraints();
		gbc_btnLoadMap.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadMap.gridx = 0;
		gbc_btnLoadMap.gridy = 1;
		frame.getContentPane().add(btnLoadMap, gbc_btnLoadMap);
		btnLoadMap.addActionListener(this);

		dtrpnPathToMap = new JEditorPane();
		dtrpnPathToMap.setToolTipText("Path to map ...");
		dtrpnPathToMap.setText("/Users/bjarkehou/Desktop/map.png");
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

		btnLoadRule = new JButton("Load");
		GridBagConstraints gbc_btnLoadRule = new GridBagConstraints();
		gbc_btnLoadRule.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadRule.gridx = 0;
		gbc_btnLoadRule.gridy = 3;
		frame.getContentPane().add(btnLoadRule, gbc_btnLoadRule);
		btnLoadRule.addActionListener(this);

		dtrpnPathToRule = new JEditorPane();
		GridBagConstraints gbc_dtrpnPathToRule = new GridBagConstraints();
		gbc_dtrpnPathToRule.fill = GridBagConstraints.BOTH;
		gbc_dtrpnPathToRule.insets = new Insets(0, 0, 5, 5);
		gbc_dtrpnPathToRule.gridx = 1;
		gbc_dtrpnPathToRule.gridy = 3;
		frame.getContentPane().add(dtrpnPathToRule, gbc_dtrpnPathToRule);
		
		btnAdd = new JButton("Add agent");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 5;
		frame.getContentPane().add(btnAdd, gbc_btnAdd);
		btnAdd.addActionListener(this);
		
		lblAmountOfAgents = new JLabel("Amount of agents: 0");
		GridBagConstraints gbc_lblAmountOfAgents = new GridBagConstraints();
		gbc_lblAmountOfAgents.insets = new Insets(0, 0, 5, 5);
		gbc_lblAmountOfAgents.gridx = 1;
		gbc_lblAmountOfAgents.gridy = 5;
		frame.getContentPane().add(lblAmountOfAgents, gbc_lblAmountOfAgents);
		
		lblCurrentTimestep = new JLabel("Current timestep: 0");
		GridBagConstraints gbc_lblCurrentTimestep = new GridBagConstraints();
		gbc_lblCurrentTimestep.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentTimestep.gridx = 1;
		gbc_lblCurrentTimestep.gridy = 6;
		frame.getContentPane().add(lblCurrentTimestep, gbc_lblCurrentTimestep);
		
		btnStep = new JButton("Step");
		GridBagConstraints gbc_btnStep = new GridBagConstraints();
		gbc_btnStep.insets = new Insets(0, 0, 5, 5);
		gbc_btnStep.gridx = 0;
		gbc_btnStep.gridy = 6;
		frame.getContentPane().add(btnStep, gbc_btnStep);
		btnStep.addActionListener(this);
		
		btnGo = new JButton("Finish");
		GridBagConstraints gbc_btnGo = new GridBagConstraints();
		gbc_btnGo.insets = new Insets(0, 0, 0, 5);
		gbc_btnGo.gridx = 0;
		gbc_btnGo.gridy = 7;
		frame.getContentPane().add(btnGo, gbc_btnGo);
		btnGo.addActionListener(this);
		
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(con.getTotalTimeStep());
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridwidth = 2;
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 7;
		frame.getContentPane().add(progressBar, gbc_progressBar);
	}

	public void setInitialMap(BufferedImage map) {
		initMap.setIcon(new ImageIcon(map.getScaledInstance(250, 250, Image.SCALE_SMOOTH)));
		initMap.revalidate();
	}

	public void setCurrentMap(BufferedImage map) {
		currentMap.setIcon(new ImageIcon(map.getScaledInstance(250, 250, Image.SCALE_SMOOTH)));
		currentMap.revalidate();
	}
	
	public void setCurrentTimeStep(int timeStep) {
		lblCurrentTimestep.setText("Current timestep: "+timeStep);
		lblCurrentTimestep.revalidate();
	}
	
	public void setAmountOfAgentsLbl(int agents) {
		lblAmountOfAgents.setText("Amount of agents: "+agents);
		lblAmountOfAgents.revalidate();
	}
	
	public void updateProgressBar(int progress) {
		progressBar.setValue(progress);
		progressBar.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLoadMap) {
			con.loadMapOnPath(dtrpnPathToMap.getText());
		} else if(e.getSource() == btnLoadRule) {
			con.loadRuleOnPath(dtrpnPathToRule.getText());
		} else if(e.getSource() == btnGo) {
			con.doRestOfTimeSteps();
		} else if(e.getSource() == btnStep) {
			con.doOneTimeStep();
		} else if(e.getSource() == btnAdd) {
			con.addNewAgent();
		}
	}

}
