package org.example;

public class TEuler extends TAbstractIntegrator {

    @Override
    public TVector OneStep() {

        TVector f = rightParts.getRightParts(t, state);

        return state.add(f.multiply(h));
    }
}
