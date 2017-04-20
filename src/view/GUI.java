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
import operadores.cruce.OX;
import operadores.cruce.OXOrdenPrio;
import operadores.cruce.OXPosPrio;
import operadores.cruce.PMX;
import operadores.cruce.Uniforme;
import operadores.mutacion.FuncionMutacion;
import operadores.mutacion.Heuristica;
import operadores.mutacion.Insercion;
import operadores.mutacion.Intercambio;
import operadores.mutacion.IntercambioAgresivo;
import operadores.mutacion.BaseABase;
import operadores.mutacion.Inversion;
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
import java.awt.Component;
import javax.swing.JSeparator;
import java.awt.Dimension;
import javax.swing.Box;

public class GUI extends JFrame{

	private JFrame gui = this;
	private String[] problemOptions = {"Pr1.1", "Pr1.2", "Pr1.3", "Pr1.4", "Pr1.4Xtra", "Pr1.5", "Pr2.Ajuste", "Pr2.Datos12", "Pr2.Datos15", "Pr2.Datos30", "Pr2.tai100a", "Pr2.tai256c"}; 
	private FuncionSeleccion[] selectionOptions = {new Ruleta(), new TorneoDeterminista(2), new TorneoDeterminista(3), new EstocasticoUniversal()}; 
	private FuncionCruce[] crossoverOptionsBin = {new Monopunto(), new Multipunto(2), new Multipunto(3), new Uniforme()}; 
	private FuncionCruce[] crossoverOptionsReal = {new Monopunto(), new Multipunto(2), new Multipunto(3), new Uniforme(), new Aritmetico()};
	private FuncionCruce[] crossoverOptionsPermInt = {new PMX(), new OX(), new OXPosPrio(), new OXOrdenPrio(), new CX(), new ERX(), new Monopunto(), new Multipunto(2), new Multipunto(3), new Uniforme()};
	private FuncionMutacion[] mutationOptionsBin = {new BaseABase()}; 
	private FuncionMutacion[] mutationOptionsReal = {new BaseABase()}; 
	private FuncionMutacion[] mutationOptionsPermInt = {new Inversion(), new Intercambio(), new IntercambioAgresivo(), new Insercion(), new Heuristica(3)}; 
	private JComboBox<String> problemCombobox;
	private JComboBox<String> contractividadCombobox; 
	private GraficaPanel chartPanel;
	private final ConfigPanel<Settings> settingsPanel;
	private JLabel n;
	private JPanel tricksPanel;
	private JTextField ntf;
	private JCheckBox invEspecialCheckBox;
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
		problemCombobox.setMaximumRowCount(20);
		
		labelComponent.setHorizontalAlignment(SwingConstants.RIGHT);
		problemPanel.add(problemCombobox);
					
		n = new JLabel("N:");
		problemPanel.add(n);
		
		ntf = new JTextField("3");
		problemPanel.add(ntf);
		
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
		
		invEspecialCheckBox = new JCheckBox("Inversión especial");
		invEspecialCheckBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		tricksPanel.add(invEspecialCheckBox);

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
		
	}
	
	public void onExecutionDone(Individuo bestFound) {
		String messIntro = "";
		String mess = "";
		String messGenes = "";
		if(optimo != null)
			messIntro += ("Fitness óptimo del problema: " + optimo + "\n");
		messIntro += ("Mejor fitness encontrado: " + bestFound.getFitness() + "\n");
		messIntro += ("Con los siguientes genes: \n");
		for(int i = 0; i < bestFound.getFenotipo().size(); i++){
			mess += ("Gen #" + i + ": " + bestFound.getFenotipo().get(i) + "\n");
			messGenes += ("Gen#" + i + ":" + bestFound.getFenotipo().get(i));
			if(i != bestFound.getFenotipo().size()-1)
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
		JLabel popUpLabel1 = new JLabel("Mejor fitness encontrado: " + bestFound.getFitness());
		JLabel popUpLabel2 = new JLabel( "Con los siguientes genes:");
		popUp.add(popUpLabel1);
		popUp.add(popUpLabel2);
		int linesToDisplay = Math.min(4, bestFound.getFenotipo().size()) + 1;
		JTextArea popUpTextArea = new JTextArea(mess.substring(0, mess.length()-1), linesToDisplay, 2);
		popUpTextArea.setEditable(false);
		JScrollPane popUpScroll = new JScrollPane(popUpTextArea);
		popUp.add(popUpScroll);
		if(visuals.isSelected())
			JOptionPane.showMessageDialog(null, popUp,"Información de la ejecución",JOptionPane.INFORMATION_MESSAGE);
		

		stopButton.setEnabled(false);
		runButton.setEnabled(true);
		contractividadCombobox.setEnabled(true); 
		
		this.pack();
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
				.addInner(new DoubleOption<Settings>("Crossover %", "", "crossoverIndex", 0, 100))
			.endInner()
			.beginInner(new InnerOption<Settings,Settings>("Mutation", "", "settings", Settings.class))
				.addInner(new ChoiceOption<Settings>("MutationB", "", "mutationOptionBin", mutationOptionsBin))
				.addInner(new ChoiceOption<Settings>("MutationR", "", "mutationOptionReal", mutationOptionsReal))
				.addInner(new ChoiceOption<Settings>("MutationPI", "", "mutationOptionPermInt", mutationOptionsPermInt))
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
		private FuncionMutacion mutationOptionBin = new BaseABase();
		private FuncionMutacion mutationOptionReal = new BaseABase();
		private FuncionMutacion mutationOptionPermInt = new Inversion();
		
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

		public FuncionMutacion getMutationOptionBin() { return mutationOptionBin; }
		public void setMutationOptionBin(FuncionMutacion mutationOptionBin) { this.mutationOptionBin = mutationOptionBin; }
		public FuncionMutacion getMutationOptionReal() { return mutationOptionReal; }
		public void setMutationOptionReal(FuncionMutacion mutationOptionReal) { this.mutationOptionReal = mutationOptionReal; }
		public FuncionMutacion getMutationOptionPermInt() { return mutationOptionPermInt; }
		public void setMutationOptionPermInt(FuncionMutacion mutationOptionPermInt) { this.mutationOptionPermInt = mutationOptionPermInt; }
		
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

		if(tipoCromosoma == TipoCromosoma.REAL) {	//cromosoma real
			crossoverPanel.getComponent(0).setVisible(false);	//oculta label crossover bin
			crossoverPanel.getComponent(1).setVisible(false);	//oculta combobox crossover bin
			crossoverPanel.getComponent(2).setVisible(true);	//muestra label crossover real
			crossoverPanel.getComponent(3).setVisible(true);	//muestra combobox crossover real
			crossoverPanel.getComponent(4).setVisible(false);	//oculta label crossover permint
			crossoverPanel.getComponent(5).setVisible(false);	//oculta combobox crossover permint

			mutationPanel.getComponent(0).setVisible(false);		//muestra label mutacion bin
			mutationPanel.getComponent(1).setVisible(false);		//muestra combobox mutacion bin
			mutationPanel.getComponent(2).setVisible(true);	//oculta label mutacion real
			mutationPanel.getComponent(3).setVisible(true);	//oculta combobox mutacion real
			mutationPanel.getComponent(4).setVisible(false);	//oculta label mutacion permint
			mutationPanel.getComponent(5).setVisible(false);	//oculta combobox mutacion permint
			
			//invEspecialCheckBox.setVisible(false);
			invEspecialCheckBox.setSelected(false);
		}
		
		else if(tipoCromosoma == TipoCromosoma.PERMINT) {	//cromosoma permint
			crossoverPanel.getComponent(0).setVisible(false);	//oculta label crossover bin
			crossoverPanel.getComponent(1).setVisible(false);	//oculta combobox crossover bin
			crossoverPanel.getComponent(2).setVisible(false);	//oculta label crossover real
			crossoverPanel.getComponent(3).setVisible(false);	//oculta combobox crossover real
			crossoverPanel.getComponent(4).setVisible(true);	//muestra label crossover permint
			crossoverPanel.getComponent(5).setVisible(true);	//muestra combobox crossover permint

			mutationPanel.getComponent(0).setVisible(false);	//oculta label mutacion bin
			mutationPanel.getComponent(1).setVisible(false);	//oculta combobox mutacion bin
			mutationPanel.getComponent(2).setVisible(false);	//oculta label mutacion real
			mutationPanel.getComponent(3).setVisible(false);	//oculta combobox mutacion real
			mutationPanel.getComponent(4).setVisible(true);		//muestra label mutacion permint
			mutationPanel.getComponent(5).setVisible(true);		//muestra combobox mutacion permint
			
			//invEspecialCheckBox.setVisible(true);
			invEspecialCheckBox.setSelected(true);
		}
		else {	//cromosoma binario
			crossoverPanel.getComponent(0).setVisible(true);	//muestra label crossover bin
			crossoverPanel.getComponent(1).setVisible(true);	//muestra combobox crossover bin
			crossoverPanel.getComponent(2).setVisible(false);	//oculta label crossover real
			crossoverPanel.getComponent(3).setVisible(false);	//oculta combobox crossover real
			crossoverPanel.getComponent(4).setVisible(false);	//oculta label crossover permint
			crossoverPanel.getComponent(5).setVisible(false);	//oculta combobox crossover permint

			mutationPanel.getComponent(0).setVisible(true);		//muestra label mutacion bin
			mutationPanel.getComponent(1).setVisible(true);		//muestra combobox mutacion bin
			mutationPanel.getComponent(2).setVisible(false);	//oculta label mutacion real
			mutationPanel.getComponent(3).setVisible(false);	//oculta combobox mutacion real
			mutationPanel.getComponent(4).setVisible(false);	//oculta label mutacion permint
			mutationPanel.getComponent(5).setVisible(false);	//oculta combobox mutacion permint
			
			//invEspecialCheckBox.setVisible(false);
			invEspecialCheckBox.setSelected(false);
		}
	}

	public GraficaPanel getChartPanel() {
		return chartPanel;
	}
	
	public String getContractividad() {
		return (String) this.contractividadCombobox.getSelectedItem();
	}
	
}
