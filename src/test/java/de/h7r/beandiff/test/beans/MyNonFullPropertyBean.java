package de.h7r.beandiff.test.beans;

public class MyNonFullPropertyBean {

    private String propertyWithOnlyGetter = "fixed value";
    private String propertyWithGetterAndSetter;

    public String getPropertyWithGetterAndSetter () {
        return propertyWithGetterAndSetter;
    }

    public void setPropertyWithGetterAndSetter (String propertyWithGetterAndSetter) {
        this.propertyWithGetterAndSetter = propertyWithGetterAndSetter;
    }

    public String getPropertyWithOnlyGetter () {
        return propertyWithOnlyGetter;
    }

}
