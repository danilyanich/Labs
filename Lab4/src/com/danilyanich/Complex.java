package com.danilyanich;

import java.util.DoubleSummaryStatistics;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Complex {

    public static Pattern PATTERN = Pattern.compile("((-?\\d*\\.?\\d+)( *)(\\+|-)( *)(\\d*\\.?\\d+)( *\\*? *)i)|((-?\\d*\\.?\\d+)( *\\*? *)\\(cos\\((-?\\d*\\.?\\d+)\\)( *\\+ *)i( *\\*? *)sin\\((-?\\d*\\.?\\d+)\\)\\))|((-?\\d*\\.?\\d+)?( *\\*? *)exp\\((-?\\d*\\.?\\d+)( *\\*? *)i\\))");
    public static Pattern POLAR = Pattern.compile("((-? *\\d*\\.?\\d+)( *\\*? *)\\(( *)cos\\((-? *\\d*\\.?\\d+)\\)( *\\+ *)i( *\\*? *)sin\\((-? *\\d*\\.?\\d+)\\)\\))");
    public static Pattern RECTANGULAR = Pattern.compile("((-? *\\d*\\.?\\d+)( *)(\\+|-)( *)(\\d*\\.?\\d+)( *\\*? *)i)");
    public static Pattern EXPONENTIAL = Pattern.compile("((-? *\\d*\\.?\\d+)?( *\\*? *)exp\\((-? *\\d*\\.?\\d+)( *\\*? *)i\\))");

    enum Form {
        POLAR, RECTANGULAR, EXPONENTIAL
    }

    private double real;
    private double imag;

    private static double getAngleBySinCos(double sin, double cos) {
        double acos = Math.acos(cos);
        return sin < 0 ? acos + Math.PI : acos;
    }

    public static Complex polar(double radius, double angle) {
        return new Complex(radius, angle, Form.POLAR);
    }

    public static Complex rectangular(double real, double imaginary) {
        return new Complex(real, imaginary, Form.RECTANGULAR);
    }

    public static Complex exponencial(double radius, double angle) {
        return new Complex(radius, angle, Form.EXPONENTIAL);
    }

    public static Complex parseComplex(String string) throws Exception {
        if (!string.matches(PATTERN.pattern()))
            throw new Exception("no match!");
        Pattern _value = Pattern.compile("(-? *\\d*\\.?\\d+)");
        Matcher matcher = _value.matcher(string);
        double first, second;
        if (string.matches(RECTANGULAR.pattern())) {
            if (matcher.find()) {
                first = Double.parseDouble(matcher.group().replaceAll(" +", ""));
                if (matcher.find()) {
                    second = Double.parseDouble(matcher.group().replaceAll(" +", ""));
                    return Complex.rectangular(first, second);
                }
            }
            throw new Exception("kek, wrong string");
        } else if (string.matches(EXPONENTIAL.pattern())) {
            if (matcher.find()) {
                first = Double.parseDouble(matcher.group().replaceAll(" +", ""));
                if (matcher.find()) {
                    second = Double.parseDouble(matcher.group().replaceAll(" +", ""));
                    return Complex.exponencial(first, second);
                }
            }
            throw new Exception("kek, wrong string");
        } else if (string.matches(POLAR.pattern())) {
            if (matcher.find()) {
                first = Double.parseDouble(matcher.group().replaceAll(" +", ""));
                if (matcher.find()) {
                    second = Double.parseDouble(matcher.group().replaceAll(" +", ""));
                    if (matcher.find()){
                        double third = Double.parseDouble(matcher.group().replaceAll(" +", ""));
                        if(second == third){
                            return Complex.polar(first,second);
                        }
                    }
                } else throw new Exception("kek, wrong string");
            }
        }
        throw new Exception("kek, no match");
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

    public Complex() {
        this.real = 0;
        this.imag = 0;
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

    @Override
    public String toString() {
        return toString(Form.RECTANGULAR);
    }
}


