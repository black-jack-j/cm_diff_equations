package org.itmo.model;

import com.itmo.model.Interpolator;

import java.util.function.BiFunction;

public class RungeCuttaAlg extends DiffSolver{

    public RungeCuttaAlg(BiFunction<Double, Double, Double> equation) {
        super(equation);
    }

    @Override
    public Interpolator.Point nextPoint(Interpolator.Point point, double h) {
        double currentX = point.getX();
        double currentY = point.getY();
        double k1 = equation.apply(currentX, currentY);
        double k2 = equation.apply(currentX+h/2, currentY + h*k1/2);
        double k3 = equation.apply(currentX+h/2, currentY + h*k2/2);
        double k4 = equation.apply(currentX+h, currentY + h*k3);
        currentY+= h*((k1+2*k2+2*k3+k4)/6);
        currentX += h;
        return new Interpolator.Point(currentX, currentY);
    }
}
