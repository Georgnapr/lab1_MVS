package org.example;

public class TSpaceCraft extends TDynamicModel {
    private static final double MU = 3.98603e14;

    @Override
    public TVector getRightParts(double t, TVector s) {

        double r = Math.sqrt(s.x * s.x + s.y * s.y + s.z * s.z);

        double ax = -MU * s.x / (r * r * r);
        double ay = -MU * s.y / (r * r * r);
        double az = -MU * s.z / (r * r * r);

        return new TVector(
                s.vx,
                s.vy,
                s.vz,
                ax,
                ay,
                az
        );
    }
}
