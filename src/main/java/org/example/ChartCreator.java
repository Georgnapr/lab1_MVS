package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class ChartCreator {

    public static ChartPanel createPositionTimeChart(
            List<TVector> trajectory,
            List<Double> time
    ) {
        XYSeries seriesX = new XYSeries("X");
        XYSeries seriesY = new XYSeries("Y");
        XYSeries seriesZ = new XYSeries("Z");

        for (int i = 0; i < trajectory.size() && i < time.size(); i++) {
            TVector v = trajectory.get(i);
            double t = time.get(i);
            seriesX.add(t, v.x);
            seriesY.add(t, v.y);
            seriesZ.add(t, v.z);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesX);
        dataset.addSeries(seriesY);
        dataset.addSeries(seriesZ);

        JFreeChart chart = ChartFactory.createXYLineChart(
                null,
                "t",
                "X, Y, Z",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        styleMultiSeriesPlot(chart.getXYPlot(),
                new Color[]{Color.RED, Color.BLUE, Color.GREEN});

        return new ChartPanel(chart);
    }

    public static ChartPanel createVelocityTimeChart(
            List<TVector> trajectory,
            List<Double> time
    ) {
        XYSeries seriesVx = new XYSeries("Vx");
        XYSeries seriesVy = new XYSeries("Vy");
        XYSeries seriesVz = new XYSeries("Vz");

        for (int i = 0; i < trajectory.size() && i < time.size(); i++) {
            TVector v = trajectory.get(i);
            double t = time.get(i);
            seriesVx.add(t, v.vx);
            seriesVy.add(t, v.vy);
            seriesVz.add(t, v.vz);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesVx);
        dataset.addSeries(seriesVy);
        dataset.addSeries(seriesVz);

        JFreeChart chart = ChartFactory.createXYLineChart(
                null,
                "t",
                "Vx, Vy, Vz",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        styleMultiSeriesPlot(chart.getXYPlot(),
                new Color[]{Color.RED, Color.BLUE, Color.GREEN});

        return new ChartPanel(chart);
    }

    public static ChartPanel createPhaseChart(
            String xLabel,
            String yLabel,
            List<TVector> trajectory,
            Function<TVector, Double> xFunc,
            Function<TVector, Double> yFunc
    ) {
        XYSeries series = new XYSeries("", false);

        for (TVector v : trajectory) {
            series.add(xFunc.apply(v), yFunc.apply(v));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                null,
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesShapesVisible(0, false);
        plot.setRenderer(renderer);

        return new ChartPanel(chart);
    }

    private static void styleMultiSeriesPlot(XYPlot plot, Color[] colors) {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i = 0; i < colors.length; i++) {
            renderer.setSeriesPaint(i, colors[i]);
            renderer.setSeriesShapesVisible(i, false);
        }
        plot.setRenderer(renderer);
    }
}

