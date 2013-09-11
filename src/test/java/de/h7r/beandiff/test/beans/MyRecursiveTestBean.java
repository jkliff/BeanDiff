package de.h7r.beandiff.test.beans;

/**
 *
 */
public class MyRecursiveTestBean {

    private String     lala1;
    private String     lala2;
    private MyTestBean bean;
    private MyOtherTestBean bean2;

    public MyTestBean getBean () {

        return bean;
    }

    public void setBean (MyTestBean bean) {

        this.bean = bean;
    }

    public String getLala1 () {
        return lala1;
    }

    public void setLala1 (String lala1) {
        this.lala1 = lala1;
    }

    public String getLala2 () {
        return lala2;
    }

    public void setLala2 (String lala2) {
        this.lala2 = lala2;
    }

    public MyOtherTestBean getBean2 () {
        return bean2;
    }

    public void setBean2 (MyOtherTestBean bean2) {
        this.bean2 = bean2;
    }

}
