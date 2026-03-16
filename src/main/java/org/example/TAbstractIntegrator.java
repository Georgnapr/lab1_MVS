package org.example;

import java.util.ArrayList;
import java.util.List;

public abstract class TAbstractIntegrator {
    public double t0;
    public double tk;
    protected double t;
    public double h;

    protected TDynamicModel rightParts;
    protected TVector state;

    public List<TVector> trajectory = new ArrayList<>();
    public List<Double> time = new ArrayList<>();

    public void setInitialState(TVector state){
        this.state = state;
        this.t = this.t0;
    }

    public void SetRightParts(TDynamicModel DynamicModel){
        this.rightParts = DynamicModel;
    }

    public void MoveTo() {
        trajectory.clear();
        time.clear();

        trajectory.add(new TVector(state.x, state.y, state.z, state.vx, state.vy, state.vz));
        time.add(t);

        while (t < this.tk) {
            state = OneStep();
            t += h;

            trajectory.add(new TVector(state.x, state.y, state.z, state.vx, state.vy, state.vz));
            time.add(t);
        }
    }

    public abstract TVector OneStep();
}
