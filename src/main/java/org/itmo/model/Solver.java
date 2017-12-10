package org.itmo.model;

import com.itmo.model.DataSet;
import com.itmo.model.Interpolator;

public class Solver {

    protected DiffSolver solver;

    public Solver(DiffSolver diffSolver){
        this.solver = diffSolver;
    }

    public DataSet compute(Interpolator.Point startCondition, double lastX, double eMax, double eMin, double h){
        DataSet dataSet = new DataSet();
        Interpolator.Point point = startCondition;
        dataSet.addPoint(startCondition);
        do{
            System.out.println(point(point));
            Interpolator.Point coarsePoint = solver.nextPoint(point, 2*h);
            Interpolator.Point finePoint = solver.nextPoint(solver.nextPoint(point, h), h);
            double mist = Math.abs(finePoint.getY()-coarsePoint.getY())/15;
            if(mist > eMax){
                h = h / 2;
                continue;
            }else{
                dataSet.addPoint(finePoint);
                point = finePoint;
                if(mist < eMin) h = h * 2;
            }
        }while (point.getX() < lastX);
        return dataSet;
    }
    public static String point(Interpolator.Point point){
        return point.getX()+ " " + point.getY();
    }
}
