package view;

import java.awt.Color;
import java.awt.Paint;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory;

public class GraficaPanel extends JPanel{
	private JFreeChart chart;
	private XYSeriesCollection dataset;
	private XYSeries bestSeries;
	private XYSeries worstSeries;
	private XYSeries averageSeries;
	private XYSeries bestAbsSeries;
	private ChartPanel chartPanel;
	
	public GraficaPanel() {
		init();
		this.add(chartPanel);
	}
	
	private void init(){
		dataset = new XYSeriesCollection();
		
		bestSeries = new XYSeries("Gen's best");
		worstSeries = new XYSeries("Gen's worse");
		averageSeries = new XYSeries("Gen's average");
		bestAbsSeries = new XYSeries("Absolute best");
		
		dataset.addSeries(bestSeries);
		dataset.addSeries(averageSeries);
		dataset.addSeries(worstSeries);
		dataset.addSeries(bestAbsSeries);
		
		chart = ChartFactory.createXYLineChart(
			"Chart",					//chart title
			"generations",				//x axis label
			"fitness",					//y axis label
			dataset,					//data
			PlotOrientation.VERTICAL,
			true,						//include legend
			true,						//tooltips
			false						//urls
		);
		
		Color bgColor = new Color(255, 255, 255, 0);	//transparent
		Color gridColor = new Color(80, 80, 80);
		
		XYPlot plot = chart.getXYPlot();
		
		plot.setBackgroundPaint(bgColor);
		plot.setDomainGridlinePaint(gridColor);
		plot.setRangeGridlinePaint(gridColor);
		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.RED); 
		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(1, Color.GREEN); 
		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(2, Color.YELLOW); 
		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(3, Color.BLUE); 
		
		chartPanel = new ChartPanel(chart);
	}
	
	public void update(int currentGen, double bestFitness, double worstFitness, double avgFitness, double bestAbsFitness){
		bestSeries.add(currentGen, bestFitness);
		averageSeries.add(currentGen, avgFitness);
		worstSeries.add(currentGen, worstFitness);
		bestAbsSeries.add(currentGen, bestAbsFitness);
	}

	public void reset() {
		dataset.removeAllSeries();
		
		bestSeries = new XYSeries("best");
		averageSeries = new XYSeries("average");
		worstSeries = new XYSeries("worse");
		bestAbsSeries = new XYSeries("Absolute best");
		
		dataset.addSeries(bestSeries);
		dataset.addSeries(averageSeries);
		dataset.addSeries(worstSeries);
		dataset.addSeries(bestAbsSeries);
	}

}
