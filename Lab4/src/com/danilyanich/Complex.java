package com.danilyanich;

class Complex {

    enum Form {
        POLAR, RECTANGULAR, EXPONENTIAL
    }

    private double real;
    private double imag;

    private static double getAngleBySinCos(double sin, double cos) {
        double acos = Math.acos(cos);
        return sin < 0 ? acos + Math.PI : acos;
    }

    private final static double twoPI = Math.PI * 2;

    public static Complex polar(double radius, double angle) {
        return new Complex(radius, angle, Form.POLAR);
    }

    public static Complex rectangular(double real, double imaginary) {
        return new Complex(real, imaginary, Form.RECTANGULAR);
    }

    public static Complex exponencial(double radius, double angle) {
        return new Complex(radius, angle, Form.EXPONENTIAL);
    }

    private Complex(double rad_real, double ang_imag, Form form) {
        if (form == Form.RECTANGULAR) {
            this.real = rad_real;
            this.imag = ang_imag;
        } else if (form == Form.POLAR || form == Form.EXPONENTIAL) {
            this.real = rad_real * Math.cos(ang_imag);
            this.imag = rad_real * Math.sin(ang_imag);
        }
    }

    public Complex copy() {
        return Complex.rectangular(real, imag);
    }

    public void assign(final Complex c) {
        this.real = c.real;
        this.imag = c.imag;
    }

    public Complex conjugate() {
        this.imag += -1;
        return this;
    }

    public Complex pow(double degree) {
        assert (degree >= 1d);
        double rad = Math.pow(Math.sqrt(Math.pow(real, 2) + Math.pow(imag, 2)), degree);
        double angle = degree * getAngleBySinCos(imag / rad, real / rad);
        this.real = rad * Math.cos(angle);
        this.imag = rad * Math.sin(angle);
        return this;
    }

    public Complex add(final Complex c) {
        this.imag += c.imag;
        this.real += c.real;
        return this;
    }

    static Complex add(final Complex c1, final Complex c2) {
        return new Complex(c1.real + c2.real,
                c1.imag + c2.imag, Form.RECTANGULAR);
    }

    public Complex sub(final Complex c) {
        this.real -= c.real;
        this.imag -= c.imag;
        return this;
    }

    static Complex sub(final Complex c1, final Complex c2) {
        return new Complex(c1.real - c2.real,
                c1.imag - c2.imag, Form.RECTANGULAR);
    }

    Complex mul(final Complex c) {
        this.real = this.real * c.real - this.imag * c.imag;
        this.imag = this.real * c.imag + this.imag * c.real;
        return this;
    }

    public static Complex mul(final Complex c1, final Complex c2) {
        return new Complex(c1.real * c2.real - c1.imag * c2.imag,
                c1.real * c2.imag + c1.imag * c2.real, Form.RECTANGULAR);
    }

    Complex div(final Complex c) {
        double rad = Math.pow(c.imag, 2) + Math.pow(c.real, 2);
        this.real = (this.real * c.real + this.imag * c.imag) / rad;
        this.imag = (c.real * this.imag - c.imag * this.real) / rad;
        return this;
    }

    public static Complex div(final Complex c1, final Complex c2) {
        double rad = Math.pow(c2.imag, 2) + Math.pow(c2.real, 2);
        return new Complex((c1.real * c2.real + c1.imag * c2.imag) / rad,
                (c2.real * c1.imag - c2.imag * c1.real) / rad, Form.RECTANGULAR);
    }

    String toString(Form form) {
        if (form == Form.RECTANGULAR) {
            return String.format("%.2f %s %.2fi", real, (imag < 0) ? "-" : "+", (imag < 0) ? imag * -1 : imag);
        } else {
            double rad = Math.sqrt(Math.pow(real, 2) + Math.pow(imag, 2));
            double angle = getAngleBySinCos(imag / rad, real / rad);
            if (form == Form.POLAR) {
                return String.format("%.2f * (cos(%.2fpi) + i*sin(%.2fpi))", rad, angle / Math.PI, angle / Math.PI);
            }
            if (form == Form.EXPONENTIAL) {
                return String.format("%.2f * exp(%.2fpi * i)", rad, angle / Math.PI);
            }
        }
        return "";
    }
}


