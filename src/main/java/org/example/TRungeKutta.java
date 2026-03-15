package org.example;

public class TRungeKutta extends TAbstractIntegrator {

    @Override
    public TVector OneStep() {

        TVector k1 = rightParts.getRightParts(t, state);

        TVector k2 = rightParts.getRightParts(
                t + h/2,
                state.add(k1.multiply(h/2))
        );

        TVector k3 = rightParts.getRightParts(
                t + h/2,
                state.add(k2.multiply(h/2))
        );

        TVector k4 = rightParts.getRightParts(
                t + h,
                state.add(k3.multiply(h))
        );

        TVector sum = k1.add(k2.multiply(2)).add(k3.multiply(2)).add(k4);

        return state.add(sum.multiply(h/6));
    }
}
