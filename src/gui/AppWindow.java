package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.wb.swing.FocusTraversalOnArray;








//import com.sun.media.jai.opimage.AddCollectionCRIF;
import controller.Controller;

public class AppWindow implements ActionListener {

	private int mapScaleSize = 400;
	
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
	private JPanel fileIOPanel;
	private JLabel lblFileOut;
	private JCheckBox fileOut;
	private JLabel lblFileOutRate;
	private JSpinner fileOutRate;
	private JLabel lblFileOutPath;
	private JTextField fileOutPath;
	private JSpinner maxTimeStepsSpinner;
	private JLabel lblShowAgents;
	private JCheckBox showAgents;
	private JLabel lblFertility;
	private JSpinner fertilitySpinner;


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
		frame.setBounds(100, 100, mapScaleSize*2+200, mapScaleSize+120);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

		btnLoadMap = new JButton("Load map");
		buttonPanel.add(btnLoadMap);

		btnLoadRule = new JButton("Load agent type");
		buttonPanel.add(btnLoadRule);


		btnAdd = new JButton("Add start agent");
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

		// Agents
		lblConstant1 = new JLabel("Agents");
		lblConstant1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		settingsPanel.add(lblConstant1);

		agentsPanel = new JPanel();
		agentsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		settingsPanel.add(agentsPanel);
		agentsPanel.setLayout(new BoxLayout(agentsPanel, BoxLayout.Y_AXIS));

		lblShowAgents = new JLabel("Show Agents:");
		agentsPanel.add(lblShowAgents);
		
		showAgents = new JCheckBox();
		showAgents.setSelected(false);
		agentsPanel.add(showAgents);
		
		lblFertility = new JLabel("Fertility rate");
		agentsPanel.add(lblFertility);
		
		fertilitySpinner = new JSpinner();
		fertilitySpinner.setMaximumSize(new Dimension(150, 30));
		fertilitySpinner.setValue((int)10);
		agentsPanel.add(fertilitySpinner);
		
		lblStartAmountOfAgents = new JLabel("Start count of agents: 0");
		agentsPanel.add(lblStartAmountOfAgents);

		lblAmountOfAgents = new JLabel("Current count of agents: 0");
		agentsPanel.add(lblAmountOfAgents);

		// Time
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
		
		lblMaxTimestep = new JLabel("Max timestep:");
		timePanel.add(lblMaxTimestep);
		
		maxTimeStepsSpinner = new JSpinner();
		maxTimeStepsSpinner.setMaximumSize(new Dimension(150, 30));
		maxTimeStepsSpinner.setValue(400);
		timePanel.add(maxTimeStepsSpinner);
		
		// IO
		JLabel lblLort = new JLabel("File IO:");
		lblConstant2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblConstant2.setHorizontalAlignment(SwingConstants.LEFT);
		settingsPanel.add(lblLort);
		
		fileIOPanel = new JPanel();
		fileIOPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		settingsPanel.add(fileIOPanel);
		fileIOPanel.setLayout(new BoxLayout(fileIOPanel, BoxLayout.Y_AXIS));
		
		lblFileOut = new JLabel("Write map to files:");
		fileIOPanel.add(lblFileOut);
		
		fileOut = new JCheckBox();
		fileOut.setSelected(true);
		fileIOPanel.add(fileOut);
		
		lblFileOutRate = new JLabel("Rate of files:");
		fileIOPanel.add(lblFileOutRate);
		
		fileOutRate = new JSpinner();
		fileOutRate.setMaximumSize(new Dimension(150, 30));
		fileOutRate.setValue(10);
		fileIOPanel.add(fileOutRate);
		
		lblFileOutPath = new JLabel("Write files to:");
		fileIOPanel.add(lblFileOutPath);
		
		fileOutPath = new JTextField();
		fileOutPath.setMaximumSize(new Dimension(150,30));
		String defaultPath = "";
		if(System.getProperty("os.name").toLowerCase().equals("windows")) {
			defaultPath = "C:\\Users\\Username\\Desktop\\PCGCity";
		} else if(System.getProperty("os.name").toLowerCase().equals("mac")) {
			defaultPath = "/Users/Username/Desktop/PCGCity";
		}
		fileOutPath.setText(defaultPath);
		fileIOPanel.add(fileOutPath);

		
		// Buttom
		lblConstant3 = new JLabel("Agent Types Loaded:");
		settingsPanel.add(lblConstant3);
		lblConstant3.setHorizontalAlignment(SwingConstants.LEFT);
		lblConstant3.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		agentTypePanel = new JPanel();
		agentTypePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		settingsPanel.add(agentTypePanel);
		agentTypePanel.setLayout(new BoxLayout(agentTypePanel, BoxLayout.Y_AXIS));

		progressPanel = new JPanel();
		frame.getContentPane().add(progressPanel, BorderLayout.SOUTH);

		progressBar = new JProgressBar();
		progressPanel.add(progressBar);
		progressBar.setMinimum(0);
		progressBar.setMaximum((int)maxTimeStepsSpinner.getValue());

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
		initMap.setIcon(new ImageIcon(map.getScaledInstance(mapScaleSize, mapScaleSize, Image.SCALE_SMOOTH)));
		initMap.revalidate();
	}

	public void setCurrentMap(BufferedImage map) {
		currentMap.setIcon(new ImageIcon(map.getScaledInstance(mapScaleSize, mapScaleSize, Image.SCALE_SMOOTH)));
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
	
	public void setAmountOfStartAgentsLbl(int agents) {
		lblStartAmountOfAgents.setText("Start count of agents: "+agents);
		lblStartAmountOfAgents.revalidate();
	}

	public void updateProgressBar(int progress) {
		progressBar.setValue(progress);
		progressBar.revalidate();
	}
	
	public void addAgentType(String name) {
		agentTypePanel.add(new JLabel(name));
		agentTypePanel.revalidate();
	}
	
	public boolean showAgents() {
		return showAgents.isSelected();
	}
	
	public int getFertilityRate() {
		return (int)fertilitySpinner.getValue();
	}
	
	public boolean writeFiles() {
		return fileOut.isSelected();
	}
	
	public String getPathToWriteFiles() {
		String path = fileOutPath.getText();
		if(!path.endsWith("/") && System.getProperty("os.name").toLowerCase().equals("mac"))
			path = path + "/";
		else if(!path.endsWith("\\") && System.getProperty("os.name").toLowerCase().equals("windows"))
			path = path + "\\";
		
		return path;
	}
	
	public int getWriteFileRate() {
		return (int)fileOutRate.getValue();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLoadMap) {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("Map files","png", "PNG"));
			int returnVal = fc.showOpenDialog(frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				con.loadMapOnPath(file.getPath());
			}
		} else if(e.getSource() == btnLoadRule) {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("Agent files", "json", "JSON"));
			int returnVal = fc.showOpenDialog(frame);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				con.loadAgentOnPath(file.getPath());
			}
		} else if(e.getSource() == btnGo) {
			con.doRestOfTimeSteps((int)maxTimeStepsSpinner.getValue());
		} else if(e.getSource() == btnStep) {
			con.doOneTimeStep();
		} else if(e.getSource() == btnAdd) {
			con.addNewAgent();
		}
	}

}
