package view;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import geneticos.Individuo;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import problemas.*;
import view.ConfigPanel.ChoiceOption;
import view.ConfigPanel.DoubleOption;
import view.ConfigPanel.InnerOption;
import view.ConfigPanel.IntegerOption;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame{

	private String[] problemOptions = {"Pr1.1", "Pr1.2", "Pr1.3", "Pr1.4", "Pr1.4Xtra", "Pr1.5", "Pr2.Ajuste", "Pr2.Datos12", "Pr2.Datos15", "Pr2.Datos30"}; 
	private FuncionSeleccion[] selectionOptions = {new Ruleta(), new TorneoDeterminista(2), new TorneoDeterminista(3), new EstocasticoUniversal()}; 
	private FuncionCruce[] crossoverOptionsBin = {new Monopunto()}; 
	private FuncionCruce[] crossoverOptionsReal = {new Monopunto(), new Aritmetico()}; 
	private FuncionMutacion[] mutationOptionsBin = {new MutaBaseABase()}; 
	private FuncionMutacion[] mutationOptionsReal = {new MutaBaseABase()}; 
	private JComboBox<String> problemCombobox;
	private GraficaPanel chartPanel;
	private final ConfigPanel<Settings> settingsPanel;
	private JLabel n;
	private JTextField ntf;
	private ProblemaFuncion pf;
	private boolean isCromosomaBin = true; //true para cromosoma bin, false para cromosoma real 
	
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
					
		n = new JLabel("N:");
		problemPanel.add(n);
		
		ntf = new JTextField("3");
		problemPanel.add(ntf);
		
		JCheckBox visuals = new JCheckBox("Enable popups", true);
		problemPanel.add(visuals);

		getContentPane().add(problemPanel, BorderLayout.NORTH);
		
		JPanel leftPanel = new JPanel();
		getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		
		final Settings settings = new Settings();
		settingsPanel = createSettingsPanelNested();
		settingsPanel.setTarget(settings);
		settingsPanel.initialize();		
		showAcordingOperators();
		
		leftPanel.add(settingsPanel, BorderLayout.NORTH);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
					
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
		leftPanel.add(controlsPanel, BorderLayout.SOUTH);
		
		problemCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(problemCombobox.getSelectedItem() == "Pr1.4Xtra")	//cromosoma real
					isCromosomaBin = false;
				else												//cromosoma bin
					isCromosomaBin = true;

				showAcordingOperators();	//update config panel
			}
		});
					
			JButton runButton = new JButton("Run");
			runButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(settingsPanel.isAllValid()){
						String opt = (String) problemCombobox.getSelectedItem();
						
						FuncionCruce fcross;
						if(isCromosomaBin)
							fcross = settings.getCrossoverOptionBin();
						else
							fcross = settings.getCrossoverOptionReal();
						
						FuncionMutacion fmut;
						if(isCromosomaBin)
							fmut = settings.getMutationOptionBin();
						else
							fmut = settings.getMutationOptionReal();
						
						FuncionSeleccion fselec = settings.getSelectionOption();
						double elite = settings.getEliteIndex()/100.0;
						int genNum = settings.getGenerationNum();
						int popSize = settings.getPopulationSize();
						
						fcross.setProb(settings.getCrossoverIndex()/100.0);
						fmut.setProb(settings.getMutationIndex()/100.0);
						
						chartPanel.reset();
						
						if(popSize*elite < 1 && elite != 0)
							JOptionPane.showMessageDialog(null, "Población / elite < 1 individuo!","Ojo!!!",JOptionPane.WARNING_MESSAGE);
						
						int npass;
						
						switch (opt) {
							case "Pr1.1":
								pf = new Pr1Func1(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							case "Pr1.2":
								pf = new Pr1Func2(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);				
								break;
							case "Pr1.3":
								pf = new Pr1Func3(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							case "Pr1.4":
								npass = Integer.parseInt(ntf.getText());
								pf = new Pr1Func4(fcross, fmut, fselec, elite, genNum, popSize, chartPanel, npass);
								break;
							case "Pr1.4Xtra":
								npass = Integer.parseInt(ntf.getText());
								pf = new Pr1Func4Xtra(fcross, fmut, fselec, elite, genNum, popSize, chartPanel, npass);
								break;
							case "Pr1.5":
								pf = new Pr1Func5(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							case "Pr2.Ajuste":
								pf = new Pr2Ajuste(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							/*case "Pr2.Datos12":
								pf = new Pr2Datos12(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							case "Pr2.Datos15":
								pf = new Pr2Datos15(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;
							case "Pr2.Datos30":
								pf = new Pr2Datos30(fcross, fmut, fselec, elite, genNum, popSize, chartPanel);
								break;*/
							default:
								break;
							}
						
						Individuo bestFound = pf.execute();
						String mess = "";
						mess += ("Mejor fitness encontrado: " + bestFound.getFitness() + "\n");
						mess += ("Con los siguientes genes: \n");
						for(int i = 0; i < bestFound.getFenotipo().size(); i++){
							mess += ("Gen #" + i + ": " + bestFound.getFenotipo().get(i) + "\n");
						}
						System.out.println(mess);
						if(visuals.isSelected())
							JOptionPane.showMessageDialog(null, mess,"Información de la ejecución",JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(settingsPanel, "fob", "PARAMETROS INCORRECTOS", JOptionPane.ERROR_MESSAGE);}
					}
				});
			
			runButton.setActionCommand("Run");
			controlsPanel.add(runButton);
			
		
	}
	
	public ConfigPanel<Settings> createSettingsPanelNested() {
		
		ConfigPanel<Settings> config = new ConfigPanel<Settings>();
				
		config
			.addOption(new IntegerOption<Settings>("Population size", "", "populationSize", 1, Integer.MAX_VALUE))
			.addOption(new IntegerOption<Settings>("Generation number", "",	 "generationNum", 1,  Integer.MAX_VALUE))
			.addOption(new ChoiceOption<Settings>("Selection", "", "selectionOption", selectionOptions))
			.beginInner(new InnerOption<Settings,Settings>("Crossover", "", "settings", Settings.class))
				.addInner(new ChoiceOption<Settings>("CrossoverB", "", "crossoverOptionBin", crossoverOptionsBin))
				.addInner(new ChoiceOption<Settings>("CrossoverR", "", "crossoverOptionReal", crossoverOptionsReal))
				.addInner(new DoubleOption<Settings>("Crossover %", "", "crossoverIndex", 0, 100))
			.endInner()
			.beginInner(new InnerOption<Settings,Settings>("Mutation", "", "settings", Settings.class))
				.addInner(new ChoiceOption<Settings>("MutationB", "", "mutationOptionBin", mutationOptionsBin))
				.addInner(new ChoiceOption<Settings>("MutationR", "", "mutationOptionReal", mutationOptionsReal))
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
		private FuncionCruce crossoverOptionBin = new Monopunto();
		private FuncionCruce crossoverOptionReal = new Monopunto();
		private FuncionMutacion mutationOptionBin = new MutaBaseABase();
		private FuncionMutacion mutationOptionReal = new MutaBaseABase();
		
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
		public FuncionCruce getCrossoverOptionBin() { return crossoverOptionBin; }
		public void setCrossoverOptionBin(FuncionCruce crossoverOptionBin) { this.crossoverOptionBin = crossoverOptionBin; }
		public FuncionCruce getCrossoverOptionReal() { return crossoverOptionReal; }
		public void setCrossoverOptionReal(FuncionCruce crossoverOptionReal) { this.crossoverOptionReal = crossoverOptionReal; }
		public FuncionMutacion getMutationOptionBin() { return mutationOptionBin; }
		public void setMutationOptionBin(FuncionMutacion mutationOptionBin) { this.mutationOptionBin = mutationOptionBin; }
		public FuncionMutacion getMutationOptionReal() { return mutationOptionReal; }
		public void setMutationOptionReal(FuncionMutacion mutationOptionReal) { this.mutationOptionReal = mutationOptionReal; }
		
		public Settings getSettings() { return this; }
		public void setSettings(Settings settings) { }
		
	}	
	
	private void showAcordingOperators() {

		ConfigPanel<?> crossoverPanel = (ConfigPanel<?>) settingsPanel.getComponent(6);
		ConfigPanel<?> mutationPanel = (ConfigPanel<?>) settingsPanel.getComponent(7);
		
		String opt = (String) problemCombobox.getSelectedItem();
		
		if(opt == "Pr1.4" || opt == "Pr1.4Xtra"){
			n.setVisible(true);
			ntf.setVisible(true);
		}
		else{
			n.setVisible(false);
			ntf.setVisible(false);
		}

		
		if(isCromosomaBin) {	//cromosoma binario
			crossoverPanel.getComponent(2).setVisible(false);	//oculta label crossover real
			crossoverPanel.getComponent(3).setVisible(false);	//oculta combobox crossover real
			crossoverPanel.getComponent(0).setVisible(true);	//muestra label crossover bin
			crossoverPanel.getComponent(1).setVisible(true);	//muestra combobox crossover bin

			mutationPanel.getComponent(2).setVisible(false);	//oculta label mutacion real
			mutationPanel.getComponent(3).setVisible(false);	//oculta combobox mutacion real
			mutationPanel.getComponent(0).setVisible(true);	//muestra label mutacion bin
			mutationPanel.getComponent(1).setVisible(true);	//muestra combobox mutacion bin
		}
		else {					//cromosoma real
			crossoverPanel.getComponent(0).setVisible(false);	//oculta label crossover bin
			crossoverPanel.getComponent(1).setVisible(false);	//oculta combobox crossover bin
			crossoverPanel.getComponent(2).setVisible(true);	//muestra label crossover real
			crossoverPanel.getComponent(3).setVisible(true);	//muestra combobox crossover real
			
			mutationPanel.getComponent(2).setVisible(false);	//oculta label mutacion real
			mutationPanel.getComponent(3).setVisible(false);	//oculta combobox mutacion real
			mutationPanel.getComponent(0).setVisible(true);	//muestra label mutacion bin
			mutationPanel.getComponent(1).setVisible(true);	//muestra combobox mutacion bin
		}
	}
	
}
