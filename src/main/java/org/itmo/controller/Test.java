package org.itmo.controller;

import com.itmo.model.BiCubeInterpolator;
import com.itmo.model.DataSet;
import com.itmo.model.Interpolator;
import com.itmo.model.LagrangeInterpolator;
import com.itmo.view.InterpolatorView;
import oracle.jrockit.jfr.JFR;
import org.itmo.model.RungeCuttaAlg;
import org.itmo.model.Solver;
import org.itmo.view.DiffView;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiFunction;

public class Test {

    public static void main(String[] args){
        Solver solver = new Solver(new RungeCuttaAlg(new BiFunction<Double, Double, Double>() {
            @Override
            public Double apply(Double aDouble, Double aDouble2) {
                return 2*aDouble+aDouble2;
            }
        }));

        Solver solver2 = new Solver(new RungeCuttaAlg(new BiFunction<Double, Double, Double>() {
            @Override
            public Double apply(Double aDouble, Double aDouble2) {
                return aDouble*2;
            }
        }));

        DataSet set = solver.compute(new Interpolator.Point(0, 0), 10,
                0.1,0.05, 1);
        DataSet set1 = solver2.compute(new Interpolator.Point(0, 0), 20,
                0.1, 0.05, 1);
        //SolverFrame(set);
       // SolverFrame(set1);

        DiffView view = new DiffView(solver2);
        view.view();
    }

    private static void SolverFrame(DataSet set) {
        Interpolator interpolator = new BiCubeInterpolator(set);
        InterpolatorView view = new InterpolatorView(interpolator, 1000);
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JPanel panel = new XChartPanel<XYChart>(view.getChart());
        JTextField x = new JTextField();
        x.setSize(100, 50);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(x, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
