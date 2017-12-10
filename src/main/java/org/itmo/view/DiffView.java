package org.itmo.view;

import com.itmo.model.BiCubeInterpolator;
import com.itmo.model.DataSet;
import com.itmo.model.Interpolator;
import com.itmo.view.InterpolatorView;
import javafx.scene.chart.XYChart;
import org.itmo.model.DiffSolver;
import org.itmo.model.Solver;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleFunction;

public class DiffView {

    private Solver solver;
    private Interpolator.Point start = new Interpolator.Point(0, 0);
    private double lastX = 10;
    private double eMin = 0.05;
    private double eMax = 0.1;

    public double geteMin() {
        return eMin;
    }

    public void seteMin(double eMin) {
        this.eMin = eMin;
    }

    public double geteMax() {
        return eMax;
    }

    public void seteMax(double eMax) {
        this.eMax = eMax;
    }

    public Interpolator.Point getStart() {
        return start;
    }

    public void setStart(Interpolator.Point start) {
        this.start = start;
    }

    public double getLastX() {
        return lastX;
    }

    public void setLastX(double lastX) {
        this.lastX = lastX;
    }

    public DiffView(Solver solver){
        this.solver = solver;
        setChart();
    }

    JPanel chartP = new JPanel();

    public void setChart() {
        frame.remove(chartP);
        DataSet set = solver.compute(this.start, this.lastX, this.eMax, this.eMin, 0.1);
        Interpolator interpolator = new BiCubeInterpolator(set);
        InterpolatorView view = new InterpolatorView(interpolator, 10000);
        chartP = new XChartPanel<>(view.getChart());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.add(chartP);
                frame.repaint();
            }
        });
    }

    JTextField xStartF = new JTextField("x");
    JTextField yStartF = new JTextField("y");
    JTextField xLastF = new JTextField("xLast");
    JTextField eMinF = new JTextField("eMin");
    JTextField eMaxF = new JTextField("eMax");
    JButton compute = new JButton("compute");
    JFrame frame = new JFrame();

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public  void view(){
        DiffView view = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = view.frame;
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1080, 720);
                frame.setLayout(new BorderLayout());
                JPanel panel = new JPanel();
                panel.add(xStartF);
                panel.add(yStartF);
                panel.add(xLastF);
                panel.add(eMaxF);
                panel.add(eMinF);
                panel.add(compute);
                frame.add(panel, BorderLayout.SOUTH);
                frame.add(view.chartP, BorderLayout.CENTER);
                frame.setVisible(true);
                compute.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Double xStart = getFromText(view.xStartF);
                        Double yStart = getFromText(view.yStartF);
                        Double xLast = getFromText(view.xLastF);
                        Double eMin = getFromText(view.eMinF);
                        Double eMax = getFromText(view.eMaxF);
                        view.setStart(new Interpolator.Point(xStart, yStart));
                        view.setLastX(xLast);
                        view.seteMin(eMin);
                        view.seteMax(eMax);
                        view.setChart();
                    }
                });
            }
        });
    }

    public double getFromText(JTextField e){
        String text = e.getText();
        return ("".equals(text)) ? 0.0 : Double.parseDouble(text);
    }

}
