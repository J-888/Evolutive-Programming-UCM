package view;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import geneticos.Individuo;
import geneticos.IndividuoStd;
import geneticos.TipoCromosoma;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import operadores.cruce.Aritmetico;
import operadores.cruce.CX;
import operadores.cruce.ERX;
import operadores.cruce.FuncionCruce;
import operadores.cruce.Monopunto;
import operadores.cruce.Multipunto;
import operadores.cruce.OPX;
import operadores.cruce.OX;
import operadores.cruce.OXOrdenPrio;
import operadores.cruce.OXPosPrio;
import operadores.cruce.PMX;
import operadores.cruce.Permutacion;
import operadores.cruce.SX;
import operadores.cruce.Uniforme;
import operadores.fitness.BloatControl.AntibloatingMethod;
import operadores.mutacion.FuncionMutacion;
import operadores.mutacion.Heuristica;
import operadores.mutacion.Insercion;
import operadores.mutacion.Intercambio;
import operadores.mutacion.IntercambioAgresivo;
import operadores.mutacion.BaseABase;
import operadores.mutacion.Funcion;
import operadores.mutacion.Inversion;
import operadores.mutacion.Multiple;
import operadores.mutacion.OPM;
import operadores.mutacion.Subarbol;
import operadores.mutacion.Terminal;
import operadores.seleccion.EstocasticoUniversal;
import operadores.seleccion.FuncionSeleccion;
import operadores.seleccion.Ruleta;
import operadores.seleccion.TorneoDeterminista;
import problemas.*;
import util.pg.TipoNodo;
import view.ConfigPanel.ChoiceOption;
import view.ConfigPanel.DoubleOption;
import view.ConfigPanel.InnerOption;
import view.ConfigPanel.IntegerOption;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

import javax.swing.JSeparator;

import java.awt.Dimension;

import javax.swing.Box;

import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI extends JFrame{

	private JFrame gui = this;
	private String[] problemOptions = {"Pr1.1", "Pr1.2", "Pr1.3", "Pr1.4", "Pr1.4Xtra", "Pr1.5", "Pr2.Ajuste", "Pr2.Datos12", "Pr2.Datos15", "Pr2.Datos30", "Pr2.tai100a", "Pr2.tai256c", "Pr3.MuxN"}; 
	private FuncionSeleccion[] selectionOptions = {new Ruleta(), new TorneoDeterminista(2), new TorneoDeterminista(3), new EstocasticoUniversal()}; 
	private FuncionCruce[] crossoverOptionsBin = {new Monopunto(), new Multipunto(2), new Multipunto(3), new Uniforme()}; 
	private FuncionCruce[] crossoverOptionsReal = {new Monopunto(), new Multipunto(2), new Multipunto(3), new Uniforme(), new Aritmetico()};
	private FuncionCruce[] crossoverOptionsPermInt = {new PMX(), new OX(), new OXPosPrio(), new OXOrdenPrio(), new CX(), new ERX(), new Monopunto(), new Multipunto(2), new Multipunto(3), new Uniforme(), new SX(), new OPX()};
	private FuncionCruce[] crossoverOptionsPG = {new Permutacion()};
	private FuncionMutacion[] mutationOptionsBin = {new BaseABase()}; 
	private FuncionMutacion[] mutationOptionsReal = {new BaseABase()}; 
	private FuncionMutacion[] mutationOptionsPermInt = {new Inversion(), new Intercambio(), new IntercambioAgresivo(0.2), new Insercion(), new Heuristica(5)}; 
	private FuncionMutacion[] mutationOptionsPG = {new Terminal(), new Funcion(), new Subarbol(), new Multiple(), new OPM()}; 
	private String[] gateNames = {"AND", "OR", "NOT", "IF", "XOR"};
	private int[] gateSelected = {0, 1, 2};
	private JComboBox<String> problemCombobox;
	private JComboBox<String> contractividadCombobox; 
	private JPanel bloatingPanel;
	private JComboBox<String> bloatingCombobox; 
	private GraficaPanel chartPanel;
	private final ConfigPanel<Settings> settingsPanel;
	private JLabel nLab;
	private JLabel maxEntLab;
	private JLabel maxProfLab;
	private JPanel tricksPanel;
	private JTextField ntf;
	private JTextField maxProftf;
	private JTextField maxEntradastf;
	private JComboBox<String> initPopPG;
	private JButton changeGatesBtn; 
	private JCheckBox invEspecialCheckBox;
	private JCheckBox escaladoCheckBox;
	private JCheckBox irradiateCheckBox;
	private JTextArea genesTextArea;
	private Problema pf;
	private Double optimo;
	private TipoCromosoma tipoCromosoma = TipoCromosoma.BIN; 
	private JCheckBox visuals;
	private JButton runButton;
	private JButton stopButton;
	
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
		
		JPanel chartYGenes = new JPanel();
		
		chartYGenes.setLayout(new BorderLayout());
		chartYGenes.add(chartPanel, BorderLayout.CENTER);
		
		getContentPane().add(chartYGenes, BorderLayout.CENTER);
		
		JLabel labelComponent;
		
		JPanel problemPanel = new JPanel();		
		problemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		labelComponent = new JLabel("Problem");
		labelComponent.setHorizontalAlignment(SwingConstants.LEFT);
		problemPanel.add(labelComponent);
		
		problemCombobox = new JComboBox(problemOptions);
		
		labelComponent.setHorizontalAlignment(SwingConstants.RIGHT);
		problemPanel.add(problemCombobox);
					
		nLab = new JLabel("N:");
		problemPanel.add(nLab);
		
		ntf = new JTextField("4");
		problemPanel.add(ntf);

		maxEntLab = new JLabel("Max Ents/puerta:");
		problemPanel.add(maxEntLab);
		maxEntradastf = new JTextField("2");
		problemPanel.add(maxEntradastf);

		maxProfLab = new JLabel("Max Profundidad:");
		problemPanel.add(maxProfLab);
		maxProftf = new JTextField("4");
		problemPanel.add(maxProftf);
		
		initPopPG = new JComboBox<String>(new String[]{"Creciente", "Completa", "Ramped&Half"});
		problemPanel.add(initPopPG);
		
		GateSelector gateSelector = new GateSelector(gateNames, gateSelected);

		changeGatesBtn = new JButton("Puertas Logicas");
		changeGatesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, gateSelector, "Gate selector", JOptionPane.PLAIN_MESSAGE);
				gateSelected = gateSelector.getSelectedIndices();
			}
		});
		problemPanel.add(changeGatesBtn);
		

		bloatingPanel = new JPanel();
		JLabel bloatingLabel = new JLabel("Antibloating");
		bloatingPanel.add(bloatingLabel);
		bloatingCombobox = new JComboBox(AntibloatingMethod.values());
		bloatingPanel.add(bloatingCombobox);
		problemPanel.add(bloatingPanel);				
		
		visuals = new JCheckBox("Enable popups", true);
		problemPanel.add(visuals);
		
		getContentPane().add(problemPanel, BorderLayout.NORTH);
		
		JPanel leftPanel = new JPanel();
		getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout());
		
		JPanel upperLeftPannel = new JPanel();
		upperLeftPannel.setLayout(new BoxLayout(upperLeftPannel, BoxLayout.Y_AXIS));
		leftPanel.add(upperLeftPannel, BorderLayout.NORTH);
		
		final Settings settings = new Settings();
		settingsPanel = createSettingsPanelNested();
		settingsPanel.setTarget(settings);
		settingsPanel.initialize();		
		
		upperLeftPannel.add(settingsPanel, BorderLayout.NORTH);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
					
		tricksPanel = new JPanel();
		tricksPanel.setLayout(new BoxLayout(tricksPanel, BoxLayout.Y_AXIS));
		tricksPanel.setBorder(BorderFactory.createTitledBorder("Trickery"));
		
		JPanel contractividadPanel = new JPanel();
		JLabel contractividadLabel = new JLabel("Contractividad");
		contractividadPanel.add(contractividadLabel);
		contractividadCombobox = new JComboBox(new String[]{"No", "Actualizando población", "Sin actualizar población"});
		contractividadPanel.add(contractividadCombobox);
		tricksPanel.add(contractividadPanel);
				
		escaladoCheckBox = new JCheckBox("Escalado lineal");
		escaladoCheckBox.setMaximumSize(new Dimension(111, 23));
		escaladoCheckBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		tricksPanel.add(escaladoCheckBox);
		
		invEspecialCheckBox = new JCheckBox("Inversión especial");
		invEspecialCheckBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		tricksPanel.add(invEspecialCheckBox);
		
		irradiateCheckBox = new JCheckBox("Irradiar");
		irradiateCheckBox.setMaximumSize(new Dimension(111, 23));
		irradiateCheckBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		tricksPanel.add(irradiateCheckBox);

		upperLeftPannel.add(tricksPanel, BorderLayout.SOUTH);
		
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
		leftPanel.add(controlsPanel, BorderLayout.SOUTH);
		
		showAcordingOperators();
		
		problemCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(problemCombobox.getSelectedItem() == "Pr1.4Xtra")	//cromosoma real
					tipoCromosoma = TipoCromosoma.REAL;
				else if(((String)problemCombobox.getSelectedItem()).substring(0, 4).equals( "Pr2."))
					tipoCromosoma = TipoCromosoma.PERMINT;
				else if(((String)problemCombobox.getSelectedItem()).substring(0, 4).equals( "Pr3."))
					tipoCromosoma = TipoCromosoma.CROMPG;
				else
					tipoCromosoma = TipoCromosoma.BIN;

				showAcordingOperators();	//update config panel
			}
		});
					
		runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(settingsPanel.isAllValid()){
					runButton.setEnabled(false);
					contractividadCombobox.setEnabled(false); 
					genesTextArea.setText("");
					String opt = (String) problemCombobox.getSelectedItem();
					
					FuncionCruce fcross;
					FuncionMutacion fmut;
					if(tipoCromosoma == TipoCromosoma.REAL){
						fcross = settings.getCrossoverOptionReal();
						fmut = settings.getMutationOptionReal();
					}
					else if (tipoCromosoma == TipoCromosoma.PERMINT){
						fcross = settings.getCrossoverOptionPermInt();
						fmut = settings.getMutationOptionPermInt();
					}
					else if (tipoCromosoma == TipoCromosoma.CROMPG){
						fcross = settings.getCrossoverOptionPG();
						fmut = settings.getMutationOptionPG();
					}
					else{
						fcross = settings.getCrossoverOptionBin();
						fmut = settings.getMutationOptionBin();
					}
					
					FuncionSeleccion fselec = settings.getSelectionOption();
					double elite = settings.getEliteIndex()/100.0;
					int genNum = settings.getGenerationNum();
					int popSize = settings.getPopulationSize();
					
					fcross.setProb(settings.getCrossoverIndex()/100.0);
					fmut.setProb(settings.getMutationIndex()/100.0);
					
					chartPanel.reset();
					
					if(popSize*elite < 1 && elite != 0)
						JOptionPane.showMessageDialog(null, "Población / elite < 1 individuo!","Ojo!!!",JOptionPane.WARNING_MESSAGE);
					
					if(elite != 0 && opt.equals("Pr3.MuxN") && bloatingCombobox.getSelectedItem() != AntibloatingMethod.NONE)
						JOptionPane.showMessageDialog(null, "No se garantiza la preservacion de la elite con antibloating","Ojo!!!",JOptionPane.WARNING_MESSAGE);
					
					int npass;
					
					switch (opt) {
						case "Pr1.1":
							pf = new Pr1Func1(fcross, fmut, fselec, elite, genNum, popSize, gui);
							break;
						case "Pr1.2":
							pf = new Pr1Func2(fcross, fmut, fselec, elite, genNum, popSize, gui);				
							break;
						case "Pr1.3":
							pf = new Pr1Func3(fcross, fmut, fselec, elite, genNum, popSize, gui);
							break;
						case "Pr1.4":
							npass = Integer.parseInt(ntf.getText());
							pf = new Pr1Func4(fcross, fmut, fselec, elite, genNum, popSize, gui, npass);
							break;
						case "Pr1.4Xtra":
							npass = Integer.parseInt(ntf.getText());
							pf = new Pr1Func4Xtra(fcross, fmut, fselec, elite, genNum, popSize, gui, npass);
							break;
						case "Pr1.5":
							pf = new Pr1Func5(fcross, fmut, fselec, elite, genNum, popSize, gui);
							break;
						case "Pr2.Ajuste":
							pf = new Pr2Ajuste(fcross, fmut, fselec, elite, genNum, popSize, gui);
							break;
						case "Pr2.Datos12":
							pf = new Pr2Datos12(fcross, fmut, fselec, elite, genNum, popSize, gui);
							break;
						case "Pr2.Datos15":
							pf = new Pr2Datos15(fcross, fmut, fselec, elite, genNum, popSize, gui);
							break;
						case "Pr2.Datos30":
							pf = new Pr2Datos30(fcross, fmut, fselec, elite, genNum, popSize, gui);
							break;
						case "Pr2.tai100a":
							pf = new Pr2tai100a(fcross, fmut, fselec, elite, genNum, popSize, gui);
							break;
						case "Pr2.tai256c":
							pf = new Pr2tai256c(fcross, fmut, fselec, elite, genNum, popSize, gui);
							break;
						case "Pr3.MuxN":
							pf = new Pr3MuxN(fcross, fmut, fselec, elite, genNum, popSize, (GUI)gui);
							break;
						default:
							break;
						}
					
					optimo = pf.getOptimo();
					pf.execute();
					stopButton.setEnabled(true);
					
				}
				else{
					JOptionPane.showMessageDialog(settingsPanel, "fob", "PARAMETROS INCORRECTOS", JOptionPane.ERROR_MESSAGE);}
				}
			});
			
			runButton.setActionCommand("Run");
			controlsPanel.add(runButton);
			
			stopButton = new JButton("Stop");
			stopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopButton.setEnabled(false);
					pf.stopProblemExecution();
				}
			});
			
			stopButton.setActionCommand("Stop");
			stopButton.setEnabled(false);
			controlsPanel.add(stopButton);
						
			int linesToDisplay = 4;
			genesTextArea = new JTextArea("", linesToDisplay,1);
			genesTextArea.setBackground(SystemColor.menu);
			genesTextArea.setEditable(false);
			genesTextArea.setLineWrap(true);
			genesTextArea.setWrapStyleWord(true);
			genesTextArea.setTabSize(7);
			
			JScrollPane rutasUpScroll = new JScrollPane(genesTextArea);
			
			JLabel labelGenes = new JLabel("Genes del mejor individuo");
			labelGenes.setHorizontalAlignment(SwingConstants.CENTER);
			
			JPanel aux = new JPanel();
			aux.setLayout(new BorderLayout());
			aux.add(labelGenes, BorderLayout.NORTH);
			aux.add(rutasUpScroll, BorderLayout.SOUTH);

			chartYGenes.add(aux, BorderLayout.SOUTH);
						
			useLongScrolls();
			this.pack();
		
	}
	
	public void onExecutionDone(Individuo mejorAbsoluto) {
		String messIntro = "";
		String mess = "";
		String messGenes = "";
		if(optimo != null)
			messIntro += ("Fitness óptimo del problema: " + optimo + "\n");
		messIntro += ("Mejor fitness encontrado: " + mejorAbsoluto.getFitness() + "\n");
		messIntro += ("Con los siguientes genes: \n");
		for(int i = 0; i < mejorAbsoluto.getFenotipo().size(); i++){
			mess += ("Gen #" + i + ": " + mejorAbsoluto.getFenotipo().get(i) + "\n");
			messGenes += ("Gen#" + i + ":" + mejorAbsoluto.getFenotipo().get(i));
			if(i != mejorAbsoluto.getFenotipo().size()-1)
				messGenes += ",\t";
		}
		System.out.println(messIntro + mess);
		
		genesTextArea.setText(messGenes);
		
		JPanel popUp = new JPanel();
		popUp.setLayout(new BoxLayout(popUp, BoxLayout.Y_AXIS));
		if(optimo != null){
			JLabel popUpLabel0 = new JLabel("Fitness óptimo del problema: " + optimo);
			popUp.add(popUpLabel0);
		}
		JLabel popUpLabel1 = new JLabel("Mejor fitness encontrado: " + mejorAbsoluto.getFitness());
		JLabel popUpLabel2 = new JLabel( "Con los siguientes genes:");
		popUp.add(popUpLabel1);
		popUp.add(popUpLabel2);
		int linesToDisplay = Math.min(4, mejorAbsoluto.getFenotipo().size()) + 1;
		JTextArea popUpTextArea = new JTextArea(mess.substring(0, mess.length()-1), linesToDisplay, 2);
		popUpTextArea.setEditable(false);
		JScrollPane popUpScroll = new JScrollPane(popUpTextArea);
		popUp.add(popUpScroll);
		if(visuals.isSelected())
			JOptionPane.showMessageDialog(null, popUp,"Información de la ejecución",JOptionPane.INFORMATION_MESSAGE);
		

		stopButton.setEnabled(false);
		runButton.setEnabled(true);
		contractividadCombobox.setEnabled(true); 
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
				.addInner(new ChoiceOption<Settings>("CrossoverPI", "", "crossoverOptionPermInt", crossoverOptionsPermInt))
				.addInner(new ChoiceOption<Settings>("CrossoverPG", "", "crossoverOptionPG", crossoverOptionsPG))
				.addInner(new DoubleOption<Settings>("Crossover %", "", "crossoverIndex", 0, 100))
			.endInner()
			.beginInner(new InnerOption<Settings,Settings>("Mutation", "", "settings", Settings.class))
				.addInner(new ChoiceOption<Settings>("MutationB", "", "mutationOptionBin", mutationOptionsBin))
				.addInner(new ChoiceOption<Settings>("MutationR", "", "mutationOptionReal", mutationOptionsReal))
				.addInner(new ChoiceOption<Settings>("MutationPI", "", "mutationOptionPermInt", mutationOptionsPermInt))
				.addInner(new ChoiceOption<Settings>("MutationPG", "", "mutationOptionPG", mutationOptionsPG))
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
		private FuncionCruce crossoverOptionPermInt = new PMX();
		private FuncionCruce crossoverOptionPG = new Permutacion();
		private FuncionMutacion mutationOptionBin = new BaseABase();
		private FuncionMutacion mutationOptionReal = new BaseABase();
		private FuncionMutacion mutationOptionPermInt = new Inversion();
		private FuncionMutacion mutationOptionPG = new Terminal();
		
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
		public FuncionCruce getCrossoverOptionPermInt() { return crossoverOptionPermInt; }
		public void setCrossoverOptionPermInt(FuncionCruce crossoverOptionPermInt) { this.crossoverOptionPermInt = crossoverOptionPermInt; }
		public FuncionCruce getCrossoverOptionPG() { return crossoverOptionPG; }
		public void setCrossoverOptionPG(FuncionCruce crossoverOptionPG) { this.crossoverOptionPG = crossoverOptionPG; }

		public FuncionMutacion getMutationOptionBin() { return mutationOptionBin; }
		public void setMutationOptionBin(FuncionMutacion mutationOptionBin) { this.mutationOptionBin = mutationOptionBin; }
		public FuncionMutacion getMutationOptionReal() { return mutationOptionReal; }
		public void setMutationOptionReal(FuncionMutacion mutationOptionReal) { this.mutationOptionReal = mutationOptionReal; }
		public FuncionMutacion getMutationOptionPermInt() { return mutationOptionPermInt; }
		public void setMutationOptionPermInt(FuncionMutacion mutationOptionPermInt) { this.mutationOptionPermInt = mutationOptionPermInt; }
		public FuncionMutacion getMutationOptionPG() { return mutationOptionPG; }
		public void setMutationOptionPG(FuncionMutacion mutationOptionPG) { this.mutationOptionPG = mutationOptionPG; }
		
		public Settings getSettings() { return this; }
		public void setSettings(Settings settings) { }
		
	}	
	
	private void showAcordingOperators() {

		ConfigPanel<?> crossoverPanel = (ConfigPanel<?>) settingsPanel.getComponent(6);
		ConfigPanel<?> mutationPanel = (ConfigPanel<?>) settingsPanel.getComponent(7);
		
		String opt = (String) problemCombobox.getSelectedItem();
		
		if(opt == "Pr1.4" || opt == "Pr1.4Xtra"){
			nLab.setVisible(true);
			ntf.setVisible(true);
			maxEntLab.setVisible(false);
			maxEntradastf.setVisible(false);
			maxProftf.setVisible(false);
			maxProfLab.setVisible(false);
			initPopPG.setVisible(false);
			changeGatesBtn.setVisible(false);
			bloatingPanel.setVisible(false);
		}
		else if(opt == "Pr3.MuxN"){
			nLab.setVisible(true);
			ntf.setVisible(true);
			maxEntLab.setVisible(true);
			maxEntradastf.setVisible(true);
			maxProftf.setVisible(true);
			maxProfLab.setVisible(true);
			initPopPG.setVisible(true);
			changeGatesBtn.setVisible(true);
			bloatingPanel.setVisible(true);
		}
		else{
			nLab.setVisible(false);
			ntf.setVisible(false);
			maxEntLab.setVisible(false);
			maxEntradastf.setVisible(false);
			maxProftf.setVisible(false);
			maxProfLab.setVisible(false);
			initPopPG.setVisible(false);
			changeGatesBtn.setVisible(false);
			bloatingPanel.setVisible(false);
		}

		if(tipoCromosoma == TipoCromosoma.REAL) {	//cromosoma real
			crossoverPanel.getComponent(0).setVisible(false);	//oculta label crossover bin
			crossoverPanel.getComponent(1).setVisible(false);	//oculta combobox crossover bin
			crossoverPanel.getComponent(2).setVisible(true);	//muestra label crossover real
			crossoverPanel.getComponent(3).setVisible(true);	//muestra combobox crossover real
			crossoverPanel.getComponent(4).setVisible(false);	//oculta label crossover permint
			crossoverPanel.getComponent(5).setVisible(false);	//oculta combobox crossover permint
			crossoverPanel.getComponent(6).setVisible(false);	//oculta label crossover pg
			crossoverPanel.getComponent(7).setVisible(false);	//oculta combobox crossover pg

			mutationPanel.getComponent(0).setVisible(false);	//muestra label mutacion bin
			mutationPanel.getComponent(1).setVisible(false);	//muestra combobox mutacion bin
			mutationPanel.getComponent(2).setVisible(true);		//oculta label mutacion real
			mutationPanel.getComponent(3).setVisible(true);		//oculta combobox mutacion real
			mutationPanel.getComponent(4).setVisible(false);	//oculta label mutacion permint
			mutationPanel.getComponent(5).setVisible(false);	//oculta combobox mutacion permint
			mutationPanel.getComponent(6).setVisible(false);	//oculta label mutacion pg
			mutationPanel.getComponent(7).setVisible(false);	//oculta combobox mutacion pg
			
			invEspecialCheckBox.setVisible(false);
			invEspecialCheckBox.setSelected(false);
		}		
		else if(tipoCromosoma == TipoCromosoma.PERMINT) {	//cromosoma permint
			crossoverPanel.getComponent(0).setVisible(false);	//oculta label crossover bin
			crossoverPanel.getComponent(1).setVisible(false);	//oculta combobox crossover bin
			crossoverPanel.getComponent(2).setVisible(false);	//oculta label crossover real
			crossoverPanel.getComponent(3).setVisible(false);	//oculta combobox crossover real
			crossoverPanel.getComponent(4).setVisible(true);	//muestra label crossover permint
			crossoverPanel.getComponent(5).setVisible(true);	//muestra combobox crossover permint
			crossoverPanel.getComponent(6).setVisible(false);	//oculta label crossover pg
			crossoverPanel.getComponent(7).setVisible(false);	//oculta combobox crossover pg

			mutationPanel.getComponent(0).setVisible(false);	//oculta label mutacion bin
			mutationPanel.getComponent(1).setVisible(false);	//oculta combobox mutacion bin
			mutationPanel.getComponent(2).setVisible(false);	//oculta label mutacion real
			mutationPanel.getComponent(3).setVisible(false);	//oculta combobox mutacion real
			mutationPanel.getComponent(4).setVisible(true);		//muestra label mutacion permint
			mutationPanel.getComponent(5).setVisible(true);		//muestra combobox mutacion permint
			mutationPanel.getComponent(6).setVisible(false);	//oculta label mutacion pg
			mutationPanel.getComponent(7).setVisible(false);	//oculta combobox mutacion pg
			
			invEspecialCheckBox.setVisible(true);
			invEspecialCheckBox.setSelected(true);
		}		
		else if(tipoCromosoma == TipoCromosoma.CROMPG) {	//cromosoma pg
			crossoverPanel.getComponent(0).setVisible(false);	//oculta label crossover bin
			crossoverPanel.getComponent(1).setVisible(false);	//oculta combobox crossover bin
			crossoverPanel.getComponent(2).setVisible(false);	//oculta label crossover real
			crossoverPanel.getComponent(3).setVisible(false);	//oculta combobox crossover real
			crossoverPanel.getComponent(4).setVisible(false);	//oculta label crossover permint
			crossoverPanel.getComponent(5).setVisible(false);	//oculta combobox crossover permint
			crossoverPanel.getComponent(6).setVisible(true);	//muestra label crossover pg
			crossoverPanel.getComponent(7).setVisible(true);	//muestra combobox crossover pg

			mutationPanel.getComponent(0).setVisible(false);	//oculta label mutacion bin
			mutationPanel.getComponent(1).setVisible(false);	//oculta combobox mutacion bin
			mutationPanel.getComponent(2).setVisible(false);	//oculta label mutacion real
			mutationPanel.getComponent(3).setVisible(false);	//oculta combobox mutacion real
			mutationPanel.getComponent(4).setVisible(false);	//oculta label mutacion permint
			mutationPanel.getComponent(5).setVisible(false);	//oculta combobox mutacion permint
			mutationPanel.getComponent(6).setVisible(true);		//muestra label mutacion pg
			mutationPanel.getComponent(7).setVisible(true);		//muestra combobox mutacion pg
			
			invEspecialCheckBox.setVisible(false);
			invEspecialCheckBox.setSelected(false);
		}
		else {	//cromosoma binario
			crossoverPanel.getComponent(0).setVisible(true);	//muestra label crossover bin
			crossoverPanel.getComponent(1).setVisible(true);	//muestra combobox crossover bin
			crossoverPanel.getComponent(2).setVisible(false);	//oculta label crossover real
			crossoverPanel.getComponent(3).setVisible(false);	//oculta combobox crossover real
			crossoverPanel.getComponent(4).setVisible(false);	//oculta label crossover permint
			crossoverPanel.getComponent(5).setVisible(false);	//oculta combobox crossover permint
			crossoverPanel.getComponent(6).setVisible(false);	//oculta label crossover pg
			crossoverPanel.getComponent(7).setVisible(false);	//oculta combobox crossover pg

			mutationPanel.getComponent(0).setVisible(true);		//muestra label mutacion bin
			mutationPanel.getComponent(1).setVisible(true);		//muestra combobox mutacion bin
			mutationPanel.getComponent(2).setVisible(false);	//oculta label mutacion real
			mutationPanel.getComponent(3).setVisible(false);	//oculta combobox mutacion real
			mutationPanel.getComponent(4).setVisible(false);	//oculta label mutacion permint
			mutationPanel.getComponent(5).setVisible(false);	//oculta combobox mutacion permint
			mutationPanel.getComponent(6).setVisible(false);	//oculta label mutacion pg
			mutationPanel.getComponent(7).setVisible(false);	//oculta combobox mutacion pg
			
			invEspecialCheckBox.setVisible(false);
			invEspecialCheckBox.setSelected(false);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void useLongScrolls() {
		
		int listLength = 20;
		

		ConfigPanel<?> crossoverPanel = (ConfigPanel<?>) settingsPanel.getComponent(6);
		ConfigPanel<?> mutationPanel = (ConfigPanel<?>) settingsPanel.getComponent(7);
		
		problemCombobox.setMaximumRowCount(listLength);

		((JComboBox<String>) crossoverPanel.getComponent(1)).setMaximumRowCount(listLength);	//combobox crossover bin
		((JComboBox<String>) crossoverPanel.getComponent(3)).setMaximumRowCount(listLength);	//combobox crossover real
		((JComboBox<String>) crossoverPanel.getComponent(5)).setMaximumRowCount(listLength);	//combobox crossover permint

		((JComboBox<String>) mutationPanel.getComponent(1)).setMaximumRowCount(listLength);	//combobox mutacion bin
		((JComboBox<String>) mutationPanel.getComponent(3)).setMaximumRowCount(listLength);	//combobox mutacion real
		((JComboBox<String>) mutationPanel.getComponent(5)).setMaximumRowCount(listLength);	//combobox mutacion permint
	}

	public GraficaPanel getChartPanel() {
		return chartPanel;
	}
	
	public String getContractividad() {
		return (String) this.contractividadCombobox.getSelectedItem();
	}
	
	public boolean getEscalado(){
		return escaladoCheckBox.isSelected();
	}

	public boolean getInvEspecial() {
		return invEspecialCheckBox.isSelected();
	}
	
	public boolean getIrradiate() {
		return irradiateCheckBox.isSelected();
	}
	
	public int getProfundidadMax() {
		int ents = Integer.parseInt(maxProftf.getText());
		if(ents < 1){
			JOptionPane.showMessageDialog(null, "La profundidad minima es 1! Cambiada a 1","Ojo!!!",JOptionPane.WARNING_MESSAGE);
			ents = 1;
		}
		return ents;
	}

	public String getPobIniGenMethod() {
		return (String) initPopPG.getSelectedItem();
	}

	public List<TipoNodo> getNTDisponibles() {
		List<TipoNodo> ret = new ArrayList<TipoNodo>();
		ArrayList<Integer> aux = new ArrayList<Integer>(gateSelected.length);
		for (int i = 0; i < gateSelected.length; i++)
			aux.add(gateSelected[i]);
		
		if(aux.contains(0))
			ret.add(TipoNodo.AND);
		if(aux.contains(1))
			ret.add(TipoNodo.OR);
		if(aux.contains(2))
			ret.add(TipoNodo.NOT);
		if(aux.contains(3))
			ret.add(TipoNodo.IF);
		if(aux.contains(4))
			ret.add(TipoNodo.XOR);		
		return ret;
	}

	public int getMaxentsPorNodo() {
		int ents = Integer.parseInt(maxEntradastf.getText());
		if(ents <= 1){
			JOptionPane.showMessageDialog(null, "El numero minimo de entradas de las puertas es 2! Cambiada a 2","Ojo!!!",JOptionPane.WARNING_MESSAGE);
			ents = 2;
		}
		return ents;
	}

	public int getTamMux() {
		int ents = Integer.parseInt(ntf.getText());
		
		if(ents <= 1){
			JOptionPane.showMessageDialog(null, "El numero de entradas del Mux es menor que 1! Redondeada a 2","Ojo!!!",JOptionPane.WARNING_MESSAGE);
			ents = 2;
		}
		else if((ents & (ents - 1)) != 0){
			boolean oK = false;
		
			while(!oK){
				ents++;
				if((ents & (ents - 1)) == 0)
					oK = true;
			}
				
			JOptionPane.showMessageDialog(null, "El numero de entradas del Mux ha de ser potencia de 2! Redondeada a: " + ents,"Ojo!!!",JOptionPane.WARNING_MESSAGE);
		}
			
		return ents;
	}

	public AntibloatingMethod getDesiredBloating() {
		return (AntibloatingMethod) this.bloatingCombobox.getSelectedItem();
	}
	
}
