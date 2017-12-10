package org.itmo.model;

import com.itmo.model.Interpolator;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;

public abstract class DiffSolver {

    protected BiFunction<Double, Double, Double> equation;

    public DiffSolver(BiFunction<Double, Double, Double> equation){
        this.equation = equation;
    }

    public void setEquation(BiFunction<Double, Double, Double> equation){
        this.equation = equation;
    }

    public abstract Interpolator.Point nextPoint(Interpolator.Point point, double h);

}
