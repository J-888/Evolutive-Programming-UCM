package view;

import javax.swing.JPanel;
import javax.swing.JList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JScrollPane;

public class GateSelector extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JList<String> listCurrentObjs;
	
	//To show this Panel as a popup, use:
	//JOptionPane.showMessageDialog(null, new ObjetiveSelectorPanel(options, indices));
	
	public GateSelector(String[] options, int[] indices) {

		listCurrentObjs = new JList<String>(options);
		listCurrentObjs.setVisibleRowCount(4);
		listCurrentObjs.setSelectionModel(new DefaultListSelectionModel() {
			private static final long serialVersionUID = 1L;

			@Override
		    public void setSelectionInterval(int index0, int index1) {
		        if(super.isSelectedIndex(index0)) {
		            super.removeSelectionInterval(index0, index1);
		        }
		        else {
		            super.addSelectionInterval(index0, index1);
		        }
		    }
		});
		listCurrentObjs.setSelectedIndices(indices);
		
		JScrollPane scrollPane = new JScrollPane(listCurrentObjs);
		add(scrollPane);
	}
	
	public void setOptions(String[] options) {
		listCurrentObjs.setListData(options);
	}
	
	public int[] getSelectedIndices() {
		return listCurrentObjs.getSelectedIndices();
	}
	
	public void setSelectedIndices(int[] indices) {
		listCurrentObjs.setSelectedIndices(indices);
	}
	
	public String[] getSelectedValues() {
		return this.listCurrentObjs.getSelectedValuesList().toArray(new String[this.listCurrentObjs.getModel().getSize()]);
	}
	
	public void setSelectedValues(String[] values) {
		this.listCurrentObjs.clearSelection();
		
		for (String val : values) {
			this.listCurrentObjs.setSelectedValue(val, false);
		}
	}

}