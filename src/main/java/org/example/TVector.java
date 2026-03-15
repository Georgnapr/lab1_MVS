package org.example;

public class TVector {
    public double x;
    public double y;
    public double z;

    public double vx;
    public double vy;
    public double vz;

    public TVector() {}

    public TVector(double x, double y, double z,
                   double vx, double vy, double vz) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    public TVector add(TVector v) {
        return new TVector(
                x + v.x,
                y + v.y,
                z + v.z,
                vx + v.vx,
                vy + v.vy,
                vz + v.vz
        );
    }

    public TVector multiply(double k) {
        return new TVector(
                x * k,
                y * k,
                z * k,
                vx * k,
                vy * k,
                vz * k
        );
    }
}
