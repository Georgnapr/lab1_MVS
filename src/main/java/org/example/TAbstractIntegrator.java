package org.example;

public abstract class TAbstractIntegrator {
    public double t0;
    public double tk;
    protected double t;
    public double h;
    protected TDynamicModel rightParts;
    protected TVector state;


    public void setInitialState(TVector state){
        this.state = state;
        this.t = this.t0;
    }

    public void SetRightParts(TDynamicModel DynamicModel){
        this.rightParts = DynamicModel;
    }

    public void MoveTo(){
        while(t < this.tk){
            state = OneStep();
            t += h;
        }
    }

    public abstract TVector OneStep();
}
