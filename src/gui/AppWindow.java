package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

//import com.sun.media.jai.opimage.AddCollectionCRIF;
import controller.Controller;

public class AppWindow implements ActionListener {

	public JFrame frame;
	private Controller con;

	JLabel initMap;
	JLabel currentMap;

	final JFileChooser fc = new JFileChooser();

	// Buttons
	JButton btnStep;
	JButton btnLoadMap;
	JButton btnLoadRule;
	JButton btnGo;
	JButton btnAdd;
	private JLabel lblCurrentTimestep;
	private JLabel lblAmountOfAgents;
	private JProgressBar progressBar;
	private JPanel buttonPanel;
	private JPanel settingsPanel;
	private JPanel progressPanel;
	private JPanel agentsPanel;
	private JPanel timePanel;
	private JLabel lblConstant1;
	private JLabel lblStartAmountOfAgents;
	private JLabel lblConstant2;
	private JLabel lblMaxTimestep;
	private JPanel agentTypePanel;
	private JLabel lblConstant3;
	private JPanel initMapPanel;
	private JPanel currentMapPanel;
	private JButton btnSettings;


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
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

		btnLoadMap = new JButton("Load map");
		buttonPanel.add(btnLoadMap);

		btnLoadRule = new JButton("Load new rule");
		buttonPanel.add(btnLoadRule);
		
		btnSettings = new JButton("Settings");
		buttonPanel.add(btnSettings);

		btnAdd = new JButton("Add agent");
		buttonPanel.add(btnAdd);

		btnStep = new JButton("Step");
		buttonPanel.add(btnStep);

		btnGo = new JButton("Finish");
		buttonPanel.add(btnGo);
		buttonPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnLoadMap, btnLoadRule, btnAdd, btnStep, btnGo}));
		btnGo.addActionListener(this);
		btnStep.addActionListener(this);
		btnAdd.addActionListener(this);
		btnLoadRule.addActionListener(this);
		btnLoadMap.addActionListener(this);

		settingsPanel = new JPanel();
		frame.getContentPane().add(settingsPanel, BorderLayout.WEST);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

		lblConstant1 = new JLabel("Agents");
		lblConstant1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		settingsPanel.add(lblConstant1);

		agentsPanel = new JPanel();
		agentsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		settingsPanel.add(agentsPanel);
		agentsPanel.setLayout(new BoxLayout(agentsPanel, BoxLayout.Y_AXIS));

		lblStartAmountOfAgents = new JLabel("Start count of agents: 0");
		agentsPanel.add(lblStartAmountOfAgents);

		lblAmountOfAgents = new JLabel("Current count of agents: 0");
		agentsPanel.add(lblAmountOfAgents);

		lblConstant2 = new JLabel("Time");
		lblConstant2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblConstant2.setHorizontalAlignment(SwingConstants.LEFT);
		settingsPanel.add(lblConstant2);

		timePanel = new JPanel();
		timePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		settingsPanel.add(timePanel);
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));

		lblCurrentTimestep = new JLabel("Current timestep: 0");
		timePanel.add(lblCurrentTimestep);

		lblMaxTimestep = new JLabel("Max timestep: 0");
		timePanel.add(lblMaxTimestep);

		lblConstant3 = new JLabel("Agent Types Loaded:");
		settingsPanel.add(lblConstant3);
		lblConstant3.setHorizontalAlignment(SwingConstants.LEFT);
		lblConstant3.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		agentTypePanel = new JPanel();
		agentTypePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		settingsPanel.add(agentTypePanel);
		agentTypePanel.setLayout(new BoxLayout(agentTypePanel, BoxLayout.X_AXIS));

		

		

		progressPanel = new JPanel();
		frame.getContentPane().add(progressPanel, BorderLayout.SOUTH);

		progressBar = new JProgressBar();
		progressPanel.add(progressBar);
		progressBar.setMinimum(0);
		progressBar.setMaximum(con.getTotalTimeStep());

		initMapPanel = new JPanel();
		initMapPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(initMapPanel, BorderLayout.CENTER);

		initMap = new JLabel("");
		initMapPanel.add(initMap);

		currentMapPanel = new JPanel();
		currentMapPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(currentMapPanel, BorderLayout.EAST);
		
		currentMap = new JLabel("");
		currentMapPanel.add(currentMap);
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
			int returnVal = fc.showOpenDialog(frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				con.loadMapOnPath(file.getPath());
			}
		} else if(e.getSource() == btnLoadRule) {
			int returnVal = fc.showOpenDialog(frame);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				con.loadRuleOnPath(file.getPath());
			}
		} else if(e.getSource() == btnGo) {
			con.doRestOfTimeSteps();
		} else if(e.getSource() == btnSettings) {
			// Create new frame with forms for setting
			// Max timestep
			// Happy Time rate
			// Happy Time frequency
			// Death Rate for agents
			
		} else if(e.getSource() == btnStep) {
			con.doOneTimeStep();
		} else if(e.getSource() == btnAdd) {
			con.addNewAgent();
		}
	}

}
