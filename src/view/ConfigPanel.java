package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


/**
 * A configuration panel; exposes GUI controls for a series of properties for a given model object.
 * 
 * The panel supports non-cyclic dependencies and nested properties (that is, setting certain values
 * can trigger the display of additional configuration panels, or make certain values valid or invalid). 
 * 
 * @author mfreire
 *
 * @param <T> the model object to be configured
 */

public class ConfigPanel<T> extends JPanel {

	private static final long serialVersionUID = -4809789335274054757L;
	private static final int MAX_CASCADES = 4;
	
	public static Logger log = Logger.getLogger("CP");
	
	/** options at this level */
	private ArrayList<Option<T> > options = new ArrayList<Option<T> >();
		
	/** invalid options at this level */
	private HashSet<Option<?> > invalidOptions = new HashSet<Option<?> >();
	
	/** true if panel was previously valid (as of last sent event) */
	private boolean wasValid = true;

	/** listeners awaiting configChanged events */
	private ArrayList<ConfigListener> listeners = new ArrayList<ConfigListener>();
	
	/** a listener for configuration changes */
	public interface ConfigListener {
		/** signals a configuration change */
		public void configChanged(boolean isConfigValid);
	}
	
	/** the current inner-panel that will receive any addInner requests */
	private ConfigPanel<?> currentInner;
	
	/** the root config panel - which will receive all updates */
	private ConfigPanel<?> rootPanel;
	
	/** the current target, as set via setTarget */
	private T target;
	
	/** avoids recursive updates */
	private boolean updating;
	
	//------- public API --------
	
	/**
	 * Dumps all state
	 */
	public void dump(Level level, String text) {
		if (log.isLoggable(level)) {
			log.log(level, dump(text, "  ."));
		}
	}
	
	/**
	 * Adds a new configuration option.
	 * Internally, all options are rendered as rows inside a GridBagConstraints
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ConfigPanel<T> addOption(Option option) {
		Option<T> o = (Option<T>)option;
		o.addToConfigPanel(this);
		o.setConfigPanel(this);
		revalidate();
		options.add(o);
		invalidOptions.add(option);
		return this;
	}
	
	/**
	 * Starts an inner option; should end with closeInner
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })	
	public ConfigPanel<?> beginInner(ComplexOption option) {
		ComplexOption<T, ?> o = (ComplexOption<T, ?>)option; 
		if (currentInner != null) {
			currentInner.beginInner(o);
		} else {
			addOption(o);
			o.inner.rootPanel = rootPanel;
			currentInner = o.inner;
		}
		return this;
	}

	/**
	 * Adds an inner option (must have called beginInner)
	 * @param o
	 * @return
	 */
	public ConfigPanel<T> addInner(Option<?> o) {		 
		if (currentInner.currentInner != null) {
			currentInner.addInner(o);
		} else {
			currentInner.addOption((Option<?>) o);
		}
		return this;
	}	
	
	/**
	 * Ends last-created inner option
	 * @return
	 */
	public ConfigPanel<T> endInner() {
		if (currentInner.currentInner != null) {
			currentInner.endInner();
		} else {
			currentInner = null;
		}
		return this;
	}
	
	/**
	 * Adds a new empty element that takes up all the remaining vertical space.
	 * No more elements should be added after this one.
	 */
	public void endOptions() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.weighty = 1;
		add(new JPanel(), gbc);	
	}
	
	/**
	 * Initializes all controls to their current model-object values
	 * 
	 * @return
	 */
	public void initialize() {

		log.fine("Initializing " + hashCode());
		
		if (target == null) {
			throw new IllegalArgumentException("Set a target before initializing");
		}
		
		// prevent updating during initialization
		updating = true;
		for (Option<T> o : options) {
			o.initialize();
		}
		updating = false;
		update();
	}	
	
	/**
	 * Writes a copy of all current control values into their model-objects
	 */
	public void copyUpdate() {
		updating = true;
		for (Option<T> o : options) {
			o.copyUpdate(); 
		}
		updating = false;
	}
	
	/**
	 * Updates all model-object values to their current control values;
	 * may trigger cascaded updates 
	 * 
	 * @return
	 */
	public void update() {
		
		if (updating) {
			// avoid recursion
			return;
		}
		updating = true;
		
		log.fine("Updating " + hashCode());
		
		int iterations = 0;
		boolean somethingChanged = true;
		while (somethingChanged) {
			somethingChanged = false;
			log.fine("Options that have changed: ");
			for (Option<T> o : options) {
				boolean optionChanged = o.update(); 
				if (optionChanged) { 
					log.fine("\t" + o.getLabel());
				}
				somethingChanged |= optionChanged;
			}
		
			boolean allOk = invalidOptions.isEmpty();
	
			if (log.isLoggable(Level.FINE)) {
				log.fine(this.hashCode() + ": " + allOk + " invalids are: ");
				if ( ! allOk) for (Option<?> o : invalidOptions) {
					log.fine("\t" + o.getLabel());
				}
			}
			
			if (++iterations > MAX_CASCADES) {
				log.warning("Cascaded " + iterations + " times and could keep going " +
						"-- there is probably a dependency cycle in your ConfigPanel");
				return;
			}
		}		
		
		// important: only fire if validity changed
		if (invalidOptions.isEmpty() != wasValid) fireConfigChanged();		
		updating = false;
	}
	
	/**
	 * Enables or disables all settings at once
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		for (Option<T> o : options) {
			o.setEnabled(enabled);
		}
	}
	
	/**
	 * Sets the model object under edit; does not actually transfer any
	 * values between model object and controls -- use initialize() to transfer
	 * from model to controls, or update() to transfer from controls to model
	 */
	public void setTarget(T modelObject) {
		if (modelObject == target) {
			// no need to do anything - already up to date
			return;
		}
		
		log.fine(hashCode() + " [new target: " + modelObject + " - old was " + target + " ]");		
		
		target = modelObject;
		
		// avoid recursive updates
		updating = true;
		for (Option<T> o : options) {
			o.setTarget(modelObject);
		}
		updating = false;
	}	
	
	/**
	 * Returns true if all controls have valid values
	 */
	public boolean isAllValid() {
		return invalidOptions.isEmpty();
	}
	
	/**
	 * Empty constructor
	 */
	public ConfigPanel() {
		setLayout(new GridBagLayout());
		rootPanel = this;
	}	
	
	/** Add a configListener */
	public void addConfigListener(ConfigListener cl) { listeners.add(cl); }
	/** Remove a configListener */
	public void removeConfigListener(ConfigListener cl) { listeners.remove(cl); }
	
	//------- internal methods  --------	

	protected String dump(String text, String indent) {
		StringBuilder sb = new StringBuilder(indent + text + " " 
				+ hashCode() + " " + ((target != null) ? target.hashCode() : "???") + "\n");
		for (Option<T> o : options) {
			o.dump(indent + indent, sb);
		}
		return sb.toString();
	}
	
	/**
	 * Fires a configChanged event
	 */
	private void fireConfigChanged() {
		boolean allOk = invalidOptions.isEmpty();
		wasValid = allOk;
		for (ConfigListener cl : listeners) {
			cl.configChanged(allOk);
		}
	}
	
	/**
	 * Utility method to find a property descriptor for a single property
	 * @param c
	 * @param fieldName
	 * @return
	 */
	private static PropertyDescriptor getPropertyDescriptor(Class<?> c, String fieldName) {
		try {
			for (PropertyDescriptor pd : 
				Introspector.getBeanInfo(c).getPropertyDescriptors()) {
				if (pd.getName().equals(fieldName)) {
					return pd;
				}
			}
		} catch (IntrospectionException e) {
			throw new IllegalArgumentException("Could not find getters or setters for field " 
					+ fieldName + " in class " + c.getCanonicalName());
		}
		return null;
	}
	
	/**
	 * A generic form-field, intended for use inside a ConfigPanel 
	 * 
	 * @author mfreire
	 */
	public static abstract class Option<T> {
		
		// ------------- properties
		
		/** label string */		
		protected String label;
		/** control tooltip / help */		
		protected String tooltip;	
		/** GUI control to use to display / set value */
		protected JComponent control;
		/** the configuration panel that contains related options */
		protected ConfigPanel<T> cp;
		
		/** constructor */
		public Option(String label, String tooltip) { 
			this.label = label; 
			this.tooltip = tooltip; 
		}

		// ------------- accessors
		
		/** get the label */
		public String getLabel() { return label; }
		/** get the tooltip */
		public String getTooltip() { return tooltip; }
		/** set the target model-object to operate on  */
		public void setTarget(T target) { /* by default, do nothing */ }
		
		// ------------- to be implemented by the actual option

		/** initialize from model object */
		public abstract void initialize();
		/** 
		 * cascaded update into model object; returns 'true' 
		 * if anything changed (eg.: validity) 
		 */
		public abstract boolean update();
		/** performs a copy-update */
		public abstract void copyUpdate();
		
		// ------------- to be implemented by the option-type (either composite or single) 
		
		/** adds this option to a config panel's gridbaglayout */
		protected abstract void addToConfigPanel(ConfigPanel<T> cp);
		/** enables or disables all components within this option */
		protected abstract void setEnabled(boolean enabled);		
		
		// ------------- accessors & logic
		
		protected abstract void dump(String indent, StringBuilder sb);
				
		/** sets the config panel to use in cascaded updates */
		public void setConfigPanel(ConfigPanel<T> cp) {
			this.cp = cp;
		}			
	}
	
	/**
	 * A single-row form-field, suitable for simple controls
	 */
	public abstract static class SimpleOption<T> extends Option<T> {
		
		/** original, non-error border */
		protected Border goodBorder;
		/** fieldName, for use with introspection */
		protected String fieldName;
		/** property descriptor for the corresponding property */
		protected PropertyDescriptor pd;
		
		public SimpleOption(String label, String tooltip, String fieldName) {
			super(label, tooltip);
			this.fieldName = fieldName;
		}
		public SimpleOption(String label, String tooltip) {
			this(label, tooltip, null);
		}
				
		/** reads the control value */
		protected abstract Object readControl();
		/** writes the control value */
		protected abstract void writeControl(Object v);
		/** returns true if this object is valid and could be written */
		protected abstract boolean isValid(Object v);	
		/** returns the GUI control used to display / set value  */
		protected abstract JComponent getControlComponent();
			
		/** adds this option to a config panel's gridbaglayout */
		@Override
		protected void addToConfigPanel(ConfigPanel<T> cp) {
			JLabel jl = new JLabel(label);
			jl.setToolTipText(tooltip);
			GridBagConstraints gbc = new GridBagConstraints();

			gbc.gridheight = 1;
			gbc.gridwidth = 1;
			gbc.insets = new Insets(2, 4, 2, 2);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = GridBagConstraints.RELATIVE;
			cp.add(jl, gbc);

			getControl();
			gbc.insets = new Insets(2, 2, 2, 4);
			gbc.gridx = 1;
			gbc.gridy = GridBagConstraints.RELATIVE;
			gbc.weightx = 1f;
			cp.add(control, gbc);
		}
		
		/** performs a copy-update */
		public void copyUpdate() {
			Object o = readControl();
			if (o != null && o instanceof Cloneable) {
				try { o = o.getClass().getMethod("clone").invoke(o); } catch (Exception e) {
					throw new IllegalArgumentException("Could not clone " + o, e);
				}
				// must write the control, to keep both in sync
				writeControl(o);
			}
			write(o);
		}
		
		/** debugging support */
		public void dump(String indent, StringBuilder sb) {
			Object c = readControl();
			Object v = read();
			sb.append(indent + label 
					+ " o: " + v + " (" + ((v != null) ? v.hashCode() : "null") + ")" 
					+ " c: " + c + " (" + ((c != null) ? c.hashCode() : "null") + ")\n");
		}		
		
		/** initialize from model object */
		public void initialize() {
			writeControl(read());
		}			
		
		/** true if control and model-object option value are the same */ 
		protected boolean isUpToDate() {
			Object c = readControl();
			Object v = read();
			return c == v || (c != null && v != null && c.equals(v));
		}						
		
		/** update from control into model object */
		public boolean update() {
			boolean ok = isValid(readControl());
			boolean written = false;
			boolean wasValid = ! cp.invalidOptions.contains(this);

			control.setBorder(ok ?  goodBorder :
				BorderFactory.createLineBorder(Color.red, 2));
			if ( ! isUpToDate() && ok) {
				write(readControl());
				written = true;
			}
			log.finest("\t s> " + label 
					+ " ok? " + ok 
					+ " written? " + written 
					+ " was-ok? " + wasValid);
			if (ok) {
				cp.invalidOptions.remove(this);
			} else {
				cp.invalidOptions.add(this);
			}
			return ok != wasValid;
		}
						
		/** returns the component to use for this field */
		public JComponent getControl() {
			control = getControlComponent();
			control.setToolTipText(tooltip);
			goodBorder = control.getBorder();
			return control;
		}			
		
		/** enables or disables all components within this option */
		@Override
		protected void setEnabled(boolean enabled) {
			control.setEnabled(enabled);
		}
		
		/** reads the value of the model object that is being configured */ 
		protected Object read() {
			try {
				if (pd == null) pd = getPropertyDescriptor(cp.target.getClass(), fieldName);
				return pd.getReadMethod().invoke(cp.target);				
			} catch (Exception e) {
				throw new RuntimeException("Error reading field " + fieldName, e);
			}
		}
		/** writes a value into the value of the model object that is being configured */ 		
		protected void write(Object v) {
			log.fine("\t\twriting object " + fieldName + " with " + v 
					+ " (" + ((v != null) ? v.hashCode() : "null") + ")");
			try {
				if (pd == null) pd = getPropertyDescriptor(cp.target.getClass(), fieldName);
				pd.getWriteMethod().invoke(cp.target, v);
			} catch (Exception e) {
				throw new RuntimeException("Error writing field " + fieldName, e);				
			}
		}		
	}
	
	/**
	 * A complex form-field, accommodates a nested ConfigPanel
	 */
	public abstract static class ComplexOption<T, I> extends Option<T> {

		protected ConfigPanel<I> inner = new ConfigPanel<I>();
		
		protected boolean active = false;
		
		protected boolean updating = false;
		
		public ComplexOption(String label, String tooltip) {
			super(label, tooltip);
		}
		
		/** set the target model-object to operate on  */		
		@Override
		public void setTarget(T target) {
			checkInner(false);			
		}
		
		/** initialize from model object */
		@Override
		public void initialize() {
			log.fine("Initializing inner for " + label);
			if (checkInner(true)) {
				log.fine("... proceeding for " + label);
				inner.initialize();
			}
		}
		
		/** cascaded update into model object */
		@Override
		public boolean update() {
			boolean wasValid = ! cp.invalidOptions.contains(this);
			if (checkInner(true)) {
				inner.update();
				if (inner.isAllValid()) {
					cp.invalidOptions.remove(this);
				} else {
					cp.invalidOptions.add(this);
				}				
			}
			log.fine("\t c> " + label 
					+ " active? " + active
					+ " is-ok? " + inner.isAllValid() 
					+ " was-ok? " + wasValid);
			return active && (wasValid != inner.isAllValid());
		}
				
		protected void dump(String indent, StringBuilder sb) {
			checkInner(false);
			if (active) {
				sb.append(inner.dump(indent + "[" + label + "]", indent));
			}
		}		
		
		/** performs a copy-update */
		public void copyUpdate() {
			if (checkInner(false)) {
				log.fine("... copy-updating for " + label);
				inner.copyUpdate();
			}
		}		
		
		/** 
		 * use before any initializations or updates: 
		 * only do things if inner is valid 
		 */
		public boolean checkInner(boolean initIfActivating) {
			I in = inner(cp.target);	
			if (in != null) {
				inner.setTarget(in);
				control.setVisible(true);
				if ( ! active) {
					active = true;
					if (initIfActivating) {
						log.fine("\t\tcheck-inner (" + label + "): initializing from model object ---");
						inner.initialize();
					}
				}
			} else {
				control.setVisible(false);
				active = false;
				cp.invalidOptions.remove(this);
			}
			log.finer("\t\t" + label + ": " + (active ? "active" : "inactive") + " in=" + 
					(in != null ? in.hashCode() : "null"));			
			return active;
		}
		
		// ------ get a reference to the inner data 
		
		/** 
		 * returns the interesting part of this target
		 * @param target
		 * @return the interesting part of this target, or null if none interesting
		 */		
		public abstract I inner(T target);		
		
		// ------FINER -- 		Torneo Det.: active in=1184537445 744919514


		/** adds this option to a parent config panel's gridbaglayout */
		@Override
		protected void addToConfigPanel(ConfigPanel<T> cp) {
			control = inner;			
			inner.setToolTipText(tooltip);
			inner.setBorder(BorderFactory.createTitledBorder(label));
			GridBagConstraints gbc = new GridBagConstraints();

			gbc.gridheight = 2;
			gbc.gridwidth = 2;
			gbc.insets = new Insets(2, 4, 2, 2);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = GridBagConstraints.RELATIVE;
			cp.add(inner, gbc);
		}

		@Override
		protected void setEnabled(boolean enabled) {
			inner.setEnabled(enabled);
		}		
	}	
	
	/**
	 * A concrete implementation of a complex option
	 * @author mfreire
	 *
	 * @param <T> External target type (object which contains the object to configure)
	 * @param <I> Internal target type (actual inner object we want to work with)
	 */
	public static class InnerOption<T, I> extends ComplexOption<T, I> {
		private Class<I> innerClass;
		private String fieldName;
		private PropertyDescriptor pd;
		@SuppressWarnings("unchecked")
		public InnerOption(String label, String tooltip, String fieldName, Class<?> innerClass) {
			super(label, tooltip);
			this.fieldName = fieldName;			
			this.innerClass = (Class<I>)innerClass;
		}
		public InnerOption(String label, String tooltip) {
			this(label, tooltip, null, null);
		}
				
		@Override
		public I inner(T target) {
			try {
				if (pd == null) pd = getPropertyDescriptor(target.getClass(), fieldName);
				Object o = pd.getReadMethod().invoke(target);
				return (o == null || ! innerClass.isAssignableFrom(o.getClass())) ?
						null : (I)innerClass.cast(o); 
			} catch (Exception e) {
				log.warning("Error accessing inner: " + e);
				e.printStackTrace();
				return null;
			}				
		}
	}
			
	/**
	 * A simple text option
	 */
	public static class TextOption<T> extends SimpleOption<T> {
		public TextOption(String nombre, String tooltip, String fieldName) {
			super(nombre, tooltip, fieldName);
		}		
		public TextOption(String nombre, String tooltip) {
			this(nombre, tooltip, null);
		}		
		@Override
		public JComponent getControlComponent() {
			JTextField jtf = new JTextField();
			jtf.addCaretListener(new CaretListener() {
				@Override
				public void caretUpdate(CaretEvent evt) { cp.rootPanel.update(); }
			});
			return jtf;
		}
		@Override
		protected void writeControl(Object o) {
			((JTextField)control).setText("" + o);
		}
		@Override
		protected Object readControl() {
			return ((JTextField)control).getText();
		}
		@Override
		protected boolean isValid(Object v) {
			return true;
		}
	}
	
	/**
	 * A simple integer option
	 * @author mfreire
	 */
	public static class IntegerOption<T> extends TextOption<T> {
		private int min, max;
		public IntegerOption(String label, String tooltip, String fieldName, int min, int max) {
			super(label, tooltip + " ("
					+ (min == Integer.MIN_VALUE ? "" : " >= " + min)
					+ (max == Integer.MAX_VALUE ? "" : " <= " + max) + ")", fieldName);
			this.min = min; 
			this.max = max;
		}
		public IntegerOption(String label, String tooltip, int min, int max) {
			this(label, tooltip, null, min, max);
		}		
		@Override
		protected Integer readControl() {
			try {
				return Integer.parseInt((String)((JTextField)control).getText());
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
		@Override
		protected boolean isValid(Object v) {
			log.finest("\t\t" + v + " in " + min + " " + max);
			if (v == null) return false;
			int iv = (Integer)v;
			return iv >= min && iv <= max;
		}		
	}
		
	/**
	 * A simple double option
	 * @author mfreire
	 */
	public static class DoubleOption<T> extends TextOption<T> {
		private double min, max, mul, imul;
		public DoubleOption(String label, String tooltip, String fieldName, double min, double max, double mul) {
			super(label, tooltip + " ("
					+ (min == Double.NEGATIVE_INFINITY ? "" : " > " + min)
					+ (max == Double.POSITIVE_INFINITY ? "" : " <= " + max) + ")", fieldName);
			this.min = min; 
			this.max = max;
			this.mul = mul;
			this.imul = 1.0 / mul;
		}
		public DoubleOption(String label, String tooltip, String fieldName, double min, double max) {
			this(label, tooltip, fieldName, min, max, 1.0);
		}
		public DoubleOption(String label, String tooltip, double min, double max, double mul) {
			this(label, tooltip, null, min, max, 1.0);
		}
		@Override
		protected Double readControl() {
			try {
				return Double.parseDouble((String)((JTextField)control).getText());
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
		@Override
		protected boolean isValid(Object v) {
			log.finest("\t\t" + v + " in " + min + " " + max);
			if (v == null) return false;
			double dv = (Double)v;
			return dv >= min && dv <= max;
		}		
		@Override
		protected Object read() {
			return mul * (Double)super.read();
		}
		@Override
		protected void write(Object v) {
			super.write(imul * (Double)v);			
		}
	}		
	
	/**
	 * An option that allows for multiple choices. 
	 * @author mfreire
	 */	
	public static class ChoiceOption<T> extends SimpleOption<T> {
		private Object[] choices;
		private List<Object> choiceList;
		public ChoiceOption(String label, String tooltip, String fieldName, Object[] choices) {
			super(label, tooltip, fieldName);
			this.choices = choices;
			this.choiceList = Arrays.asList(choices);
		}
		public ChoiceOption(String label, String tooltip, Object[] choices) {
			this(label, tooltip, null, choices);
		}
		@Override
		public JComponent getControlComponent() {
			JComboBox jcb = new JComboBox(choices);
			jcb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) { cp.rootPanel.update(); }
			});
			return jcb;
		}
		@Override
		protected Object readControl() {
			return ((JComboBox)control).getSelectedItem();
		}
		@Override
		protected boolean isValid(Object v) {
			return choiceList.contains(v);
		}			
		@Override 
		protected void writeControl(Object v) {			
			((JComboBox)control).setSelectedItem(v);
		}
	}
	
	/**
	 * An option that allows for multiple choices among mutable objects;
	 * similar to choiceOption, but considers the current choice valid as long 
	 * as it is of the same class as an available choice.
	 * @author mfreire
	 */	
	public static class StrategyOption<T> extends SimpleOption<T> {
		private HashSet<Class<?>> choiceClasses = new HashSet<Class<?>>();
		private ArrayList<Object> initialChoices = new ArrayList<Object>();
		public StrategyOption(String label, String tooltip, String fieldName, Cloneable[] choices) {
			super(label, tooltip, fieldName);
			for (Object o : choices) {
				choiceClasses.add(o.getClass());
				initialChoices.add(o);
			}
		}
		public StrategyOption(String label, String tooltip, Cloneable[] choices) {
			this(label, tooltip, null, choices);
		}
		
		@Override
		public JComponent getControlComponent() {
			JComboBox jcb = new JComboBox(new Vector<Object>(initialChoices));
			jcb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					readControl();
					cp.rootPanel.update(); 
				}
			});
			return jcb;
		}
		
		@Override
		protected Object readControl() {
			Object v = ((JComboBox)control).getSelectedItem();
			log.fine("\t\treading control " + label + ": " + v + " (" + (v != null ? v.hashCode() : "") + ")");
			return v;
		}

		@Override
		protected boolean isValid(Object v) {
			return choiceClasses.contains(v == null ? null : v.getClass());
		}			

		@Override 
		/** when written, accept equivalent values as choices */
		protected void writeControl(Object v) {
			log.fine("\t\twriting control " + label + ": " + v + " (" + (v != null ? v.hashCode() : "") + ")");
			if ( ! initialChoices.contains(v) && v != null) {
				DefaultComboBoxModel m = (DefaultComboBoxModel)((JComboBox)control).getModel();				
				Object old = null;
				for (Object o : initialChoices) {
					log.fine("\t\t\tcomparing "  + o.getClass() + " to " + v.getClass());
					if (o.getClass().equals(v.getClass())) {
						old = o;
						break;
					}
				}
				if (old == null) {
					throw new IllegalArgumentException("tried to writeControl with non-choice " + v);
				}				
				initialChoices.remove(old);
				initialChoices.add(v);
				m.removeElement(old);
				m.addElement(v);				
			}
			((JComboBox)control).setSelectedItem(v);
		}
		
		
		/** avoid overwriting with equivalent values during normal updates */
		public boolean isUpToDate() {
			Object v = read();
			Object c = readControl();
			return c == v || (c != null && v != null && c.getClass().equals(v.getClass()));
		}	
	}	
}
