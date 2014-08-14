package com.attendance;
import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
public class BarGraph {
    
    public Intent getIntent(Context context){
        
       //dynamic values
    	//int dailyPresentiesCount[] = {25,10,15,20};
        
        CategorySeries series = new CategorySeries("Attendance");
        for(int i=0; i < GraphActivity.percentage.size(); i++){
            series.add("Bar"+(i+1),GraphActivity.percentage.get(i));
        }
        
        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();  // collection of series under one object.,there could any
        dataSet.addSeries(series.toXYSeries());                            // number of series
        
        //customization of the chart
    
        XYSeriesRenderer renderer = new XYSeriesRenderer();     // one renderer for one series
        renderer.setColor(Color.CYAN);
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesSpacing((float) 5.5d);
        renderer.setLineWidth((float) 10.5d);
            
        
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();   // collection multiple values for one renderer or series
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setChartTitle("********CMR*********");
//        mRenderer.setXTitle("xValues");
        mRenderer.setYTitle("Percentage");
        mRenderer.setZoomButtonsVisible(true);    mRenderer.setShowLegend(true);
        mRenderer.setShowGridX(true);      // this will show the grid in  graph
        mRenderer.setShowGridY(true);              
//        mRenderer.setAntialiasing(true);
        mRenderer.setBarSpacing(.5);   // adding spacing between the line or stacks
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.BLACK);
        mRenderer.setXAxisMin(0);
       mRenderer.setYAxisMin(0);
        mRenderer.setXAxisMax(5);
        mRenderer.setYAxisMax(100);
//    
        mRenderer.setXLabels(0);
        
      //-------------------------------------dynamic values
        for (int i = 0; i < GraphActivity.dateList.size(); i++) {
        	 mRenderer.addXTextLabel(i+1,GraphActivity.dateList.get(i));
		}
       
       /* mRenderer.addXTextLabel(2,"Feb");
        mRenderer.addXTextLabel(3,"Mar");
        mRenderer.addXTextLabel(4,"Apr");*/
        mRenderer.setPanEnabled(true, true);    // will fix the chart position
     Intent intent = ChartFactory.getBarChartIntent(context, dataSet, mRenderer,Type.DEFAULT);
        
                return intent;
    }
}
