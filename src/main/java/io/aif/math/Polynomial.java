package io.aif.math;


public class Polynomial {

    private final double[] x;

    private final double[] y;

    private final int power;

    private Polynomial(final double[] x, final double[] y, final int power) {
        this.x = x;
        this.y = y;
        this.power = power;
    }

    public static Polynomial calculate(final double[] x, final double[] y, final int power) {
        final Polynomial polynomial = new Polynomial(x, y, power);
        return polynomial;
    }

    private void init() {
        final int n = power - 1;

    }

}
