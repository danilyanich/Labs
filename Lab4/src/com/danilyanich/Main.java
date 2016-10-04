package com.danilyanich;


public class Main {

    public static void main(String[] args) {
        Complex complex1 = Complex.rectangular(1,1);
        Complex complex2 = Complex.polar(1, Math.PI/2);
        Complex complex3 = Complex.add(complex1, complex2);
        Complex complex4 = Complex.sub(complex3, complex1.conjugate());
        complex1 = Complex.div(complex3.div(complex2), complex1);
        Complex complex5 = complex2.copy().mul(complex4).add(complex4);
        Complex m_one = Complex.rectangular(0, 1).pow(2);

        System.out.println(m_one.toString(Complex.Form.RECTANGULAR));
        System.out.println(complex1.toString(Complex.Form.RECTANGULAR));
        System.out.println(complex2.toString(Complex.Form.EXPONENTIAL));
        System.out.println(complex3.toString(Complex.Form.POLAR));
        System.out.println(complex4.toString(Complex.Form.EXPONENTIAL));
        System.out.println(complex5.toString(Complex.Form.RECTANGULAR));
    }
}
