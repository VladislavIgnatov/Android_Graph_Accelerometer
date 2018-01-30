package com.example.hussienalrubaye.sensot;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.GraphicalView;


public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;

    private GraphicalView mChart;

    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

    private XYSeries mCurrentSeries;
    private XYSeries yCurrentSeries;
    private XYSeries zCurrentSeries;

    private XYSeriesRenderer xCurrentRenderer;
    private XYSeriesRenderer yCurrentRenderer;
    private XYSeriesRenderer zCurrentRenderer;

    private void initChart() {
        mCurrentSeries = new XYSeries("X-axis");
        yCurrentSeries = new XYSeries("Y-axis");
        zCurrentSeries = new XYSeries("Z-axis");
        mDataset.addSeries(mCurrentSeries);
        mDataset.addSeries(yCurrentSeries);
        mDataset.addSeries(zCurrentSeries);

        xCurrentRenderer = new XYSeriesRenderer();
        yCurrentRenderer = new XYSeriesRenderer();
        zCurrentRenderer = new XYSeriesRenderer();

        // X-axis
        xCurrentRenderer.setLineWidth(2);
        xCurrentRenderer.setColor(Color.GREEN);
        xCurrentRenderer.setDisplayBoundingPoints(true);
        xCurrentRenderer.setPointStyle(PointStyle.CIRCLE);
        xCurrentRenderer.setPointStrokeWidth(6);

        // Y-axis
        yCurrentRenderer.setLineWidth(2);
        yCurrentRenderer.setColor(Color.RED);
        yCurrentRenderer.setDisplayBoundingPoints(true);
        yCurrentRenderer.setPointStyle(PointStyle.CIRCLE);
        yCurrentRenderer.setPointStrokeWidth(6);

        // Z-axis
        zCurrentRenderer.setLineWidth(2);
        zCurrentRenderer.setColor(Color.BLUE);
        zCurrentRenderer.setDisplayBoundingPoints(true);
        zCurrentRenderer.setPointStyle(PointStyle.CIRCLE);
        zCurrentRenderer.setPointStrokeWidth(6);

        mRenderer.addSeriesRenderer(xCurrentRenderer);
        mRenderer.addSeriesRenderer(yCurrentRenderer);
        mRenderer.addSeriesRenderer(zCurrentRenderer);

        // Chart styling
        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setShowGrid(true);
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setLabelsTextSize(20);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create sensor
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // accelerometer sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // register sensor listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);


        initChart();

        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        if (mChart == null) {

            mCurrentSeries.add(0, event.values[0]);
            yCurrentSeries.add(0, event.values[1]);
            zCurrentSeries.add(0, event.values[2]);
            mChart = ChartFactory.getCubeLineChartView(this, mDataset, mRenderer, 0);
            layout.addView(mChart);
        } else {
            mChart.repaint();
        }
        mCurrentSeries.add(0, event.values[0]);
        yCurrentSeries.add(0, event.values[1]);
        zCurrentSeries.add(0, event.values[2]);

        xText.setText("X: " + event.values[0]);
        yText.setText("Y: " + event.values[1]);
        zText.setText("Z: " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // no use
    }
}
