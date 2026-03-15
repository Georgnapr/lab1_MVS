package org.example;

import java.util.Vector;

public abstract class TAbstractIntegrator {
    public Float t0;
    public Float tk;
    public Float h;
    private TDynamicModel RightParts;

    public void SetRightParts(TDynamicModel model){

    }

    public void MoveTo(Float t0) {
    }

    public Vector OneStep(){
        Vector v = new Vector<>();
        return v;
    }
}
