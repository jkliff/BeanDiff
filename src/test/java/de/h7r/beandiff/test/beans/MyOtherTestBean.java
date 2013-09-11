package de.h7r.beandiff.test.beans;

public class MyOtherTestBean {
    private MyTestBean testBean1;
    private MyTestBean testBean2;

    public MyTestBean getTestBean1 () {
        return testBean1;
    }

    public void setTestBean1 (MyTestBean testBean1) {
        this.testBean1 = testBean1;
    }

    public MyTestBean getTestBean2 () {
        return testBean2;
    }

    public void setTestBean2 (MyTestBean testBean2) {
        this.testBean2 = testBean2;
    }
}
