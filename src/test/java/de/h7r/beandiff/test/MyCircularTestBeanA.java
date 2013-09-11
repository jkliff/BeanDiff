package de.h7r.beandiff.test;

public class MyCircularTestBeanA {
    private MyCircularTestBeanB b;

    public MyCircularTestBeanB getB () {
        return b;
    }

    public void setB (MyCircularTestBeanB b) {
        this.b = b;
    }
}

class MyCircularTestBeanB {
    private MyCircularTestBeanA a;

    public MyCircularTestBeanA getA () {
        return a;
    }

    public void setA (MyCircularTestBeanA a) {
        this.a = a;
    }
}