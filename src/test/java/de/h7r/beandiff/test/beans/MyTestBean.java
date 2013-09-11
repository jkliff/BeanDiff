package de.h7r.beandiff.test.beans;

/**
 *
 */
public class MyTestBean {

    private String foo;
    private int    bar;

    public String getFoo () {

        return foo;
    }

    public void setFoo (String foo) {

        this.foo = foo;
    }

    public int getBar () {
        return bar;
    }

    public void setBar (int bar) {
        this.bar = bar;
    }

    @Override
    public String toString () {
        return "MyTestBean [foo=" + foo + ", bar=" + bar + "]";
    }

}
