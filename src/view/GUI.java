package view;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.BoxLayout;

import operadores.cruce.Aritmetico;
import operadores.cruce.FuncionCruce;
import operadores.cruce.Monopunto;
import operadores.mutacion.FuncionMutacion;
import operadores.mutacion.MutaBaseABase;
import operadores.seleccion.EstocasticoUniversal;
import operadores.seleccion.FuncionSeleccion;
import operadores.seleccion.Ruleta;
import operadores.seleccion.TorneoDeterminista;
import problemas.Problema1;
import problemas.Problema2;
import problemas.Problema3;
import problemas.Problema4;
import problemas.Problema4Xtra;
import problemas.Problema5;
import problemas.ProblemaFuncion;
import view.ConfigPanel.ChoiceOption;
import view.ConfigPanel.DoubleOption;
import view.ConfigPanel.InnerOption;
import view.ConfigPanel.IntegerOption;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame{

	private String[] problemOptions = {"1", "2", "3", "4", "4Xtra", "5"}; 
	private FuncionSeleccion[] selectionOptions = {new Ruleta(), new TorneoDeterminista(2), new TorneoDeterminista(3), new EstocasticoUniversal()}; 
	private FuncionCruce[] crossoverOptions = {new Monopunto(), new Aritmetico()}; 
	private FuncionMutacion[] mutationOptions = {new MutaBaseABase()}; 
	private JComboBox<String> problemCombobox;
	
	private GraficaPanel chartPanel;
	
	private ProblemaFuncion pf;
	
	public GUI() {
		
		super("PE");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
		catch (UnsupportedLookAndFeelException e) { }
	    catch (ClassNotFoundException e) { }
	    catch (InstantiationException e) { }
	    catch (IllegalAccessException e) { }
				
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		/*CHART*/
		chartPanel = new GraficaPanel();
			
		getContentPane().add(chartPanel, BorderLayout.CENTER);
		
		JLabel labelComponent;
		
		JPanel problemPanel = new JPanel();		
		problemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		labelComponent = new JLabel("Problem");
		labelComponent.setHorizontalAlignment(SwingConstants.LEFT);
		problemPanel.add(labelComponent);
		
		problemCombobox = new JComboBox<String>(problemOptions);
		
		labelComponent.setHorizontalAlignment(SwingConstants.RIGHT);
		problemPanel.add(problemCombobox);
				
		getContentPane().add(problemPanel, BorderLayout.NORTH);
		
		JPanel leftPanel = new JPanel();
		getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		
		final Settings settings = new Settings();
		final ConfigPanel<Settings> settingsPanel = createSettingsPanelNested();
		settingsPanel.setTarget(settings);
		settingsPanel.initialize();		
		
		leftPanel.add(settingsPanel, BorderLayout.NORTH);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
					
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
		leftPanel.add(controlsPanel, BorderLayout.SOUTH);
		
			JButton resetButton = new JButton("Reset fields");
			controlsPanel.add(resetButton);
			
			JButton runButton = new JButton("Run");
			runButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(settingsPanel.isAllValid()){
						String opt = (String) problemCombobox.getSelectedItem();
						
						FuncionCruce fcross = settings.getCrossoverOption();
						FuncionMutacion fmut = settings.getMutationOption();
						FuncionSeleccion fselec = settings.getSelectionOption();
						double elite = settings.getEliteIndex()/100.0;
						int genNum = settings.getGenerationNum();
						int popSize = settings.getPopulationSize();
						
						fcross.setProb(settings.getCrossoverIndex()/100.0);
						fmut.setProb(settings.getMutationIndex()/100.0);
						
						chartPanel.reset();
						
						switch (opt) {
							case "1":
								pf = new Problema1(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							case "2":
								pf = new Problema2(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);				
								break;
							case "3":
								pf = new Problema3(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							case "4":
								pf = new Problema4(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							case "4Xtra":
								pf = new Problema4Xtra(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							case "5":
								pf = new Problema5(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							default:
								break;
							}
						
						pf.execute();
					}
					else{
						JOptionPane.showMessageDialog(settingsPanel, "fob", "PARAMETROS INCORRECTOS", JOptionPane.ERROR_MESSAGE);}
					}
				});
			
			runButton.setActionCommand("Run");
			controlsPanel.add(runButton);
			
		
	}
	
	public ConfigPanel<Settings> createSettingsPanel() {
		
		ConfigPanel<Settings> config = new ConfigPanel<Settings>();

		config
			.addOption(new IntegerOption<Settings>("Population size", "", "populationSize", 1, Integer.MAX_VALUE))
			.addOption(new IntegerOption<Settings>("Generation number", "",	 "generationNum", 1,  Integer.MAX_VALUE))
			.addOption(new DoubleOption<Settings>("Crossover %", "", "crossoverIndex", 1, 100))
			.addOption(new DoubleOption<Settings>("Mutation %", "", "mutationIndex", 1, 100))
			.addOption(new ChoiceOption<Settings>("Selection", "", "selectionOption", selectionOptions))
			.addOption(new ChoiceOption<Settings>("Crossover", "", "crossoverOption", crossoverOptions))
			.addOption(new ChoiceOption<Settings>("Mutation", "", "mutationOption", mutationOptions))
		.endOptions();
		
		return config;
	}
	
	public ConfigPanel<Settings> createSettingsPanelNested() {
		
		ConfigPanel<Settings> config = new ConfigPanel<Settings>();
				
		config
			.addOption(new IntegerOption<Settings>("Population size", "", "populationSize", 1, Integer.MAX_VALUE))
			.addOption(new IntegerOption<Settings>("Generation number", "",	 "generationNum", 1,  Integer.MAX_VALUE))
			.addOption(new ChoiceOption<Settings>("Selection", "", "selectionOption", selectionOptions))
			.beginInner(new InnerOption<Settings,Settings>("Crossover", "", "settings", Settings.class))
				.addInner(new ChoiceOption<Settings>("Crossover", "", "crossoverOption", crossoverOptions))
				.addInner(new DoubleOption<Settings>("Crossover %", "", "crossoverIndex", 0, 100))
			.endInner()
			.beginInner(new InnerOption<Settings,Settings>("Mutation", "", "settings", Settings.class))
				.addInner(new ChoiceOption<Settings>("Mutation", "", "mutationOption", mutationOptions))
				.addInner(new DoubleOption<Settings>("Mutation %", "", "mutationIndex", 0, 100))
			.endInner()
			.beginInner(new InnerOption<Settings,Settings>("Elite", "", "settings", Settings.class))
				.addInner(new DoubleOption<Settings>("Elite %", "", "eliteIndex", 0, 100))
			.endInner()
		.endOptions();
		
		return config;
	}
	
	public class Settings {
		
		private int populationSize = 100;
		private int generationNum = 50;
		private double crossoverIndex = 40;
		private double mutationIndex = 2;
		private double eliteIndex = 5;

		private FuncionSeleccion selectionOption = new Ruleta();
		private FuncionCruce crossoverOption = new Monopunto();
		private FuncionMutacion mutationOption = new MutaBaseABase();
		
		public int getPopulationSize() { return populationSize; }
		public void setPopulationSize(int populationSize) { this.populationSize = populationSize; }
		public int getGenerationNum() { return generationNum; }
		public void setGenerationNum(int generationNum){ this.generationNum = generationNum; }
		public double getCrossoverIndex() { return crossoverIndex; }
		public void setCrossoverIndex(double crossoverIndex) { this.crossoverIndex = crossoverIndex; }
		public double getMutationIndex() { return mutationIndex; }
		public void setMutationIndex(double mutationIndex) { this.mutationIndex = mutationIndex; }
		public double getEliteIndex() { return eliteIndex; }
		public void setEliteIndex(double eliteIndex) { this.eliteIndex = eliteIndex; }
		
		public FuncionSeleccion getSelectionOption() { return selectionOption; }
		public void setSelectionOption(FuncionSeleccion selectionOption) { this.selectionOption = selectionOption; }
		public FuncionCruce getCrossoverOption() { return crossoverOption; }
		public void setCrossoverOption(FuncionCruce crossoverOption) { this.crossoverOption = crossoverOption; }
		public FuncionMutacion getMutationOption() { return mutationOption; }
		public void setMutationOption(FuncionMutacion mutationOption) { this.mutationOption = mutationOption; }
		
		public Settings getSettings() { return this; }
		public void setSettings(Settings settings) { }
		
	}	
	
}
