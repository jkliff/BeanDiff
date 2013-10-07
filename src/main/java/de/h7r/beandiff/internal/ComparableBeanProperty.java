package de.h7r.beandiff.internal;

import java.beans.PropertyDescriptor;

public class ComparableBeanProperty {

    private String             path;
    private boolean            matching = true;
    private PropertyDescriptor property;
    private Class<?>           containingClass;
    private Object             left;
    private Object             right;
    private Integer            index;

    @Override
    public String toString () {

        return String.format ("%s -> %s: %s", path, property != null ? property.getName () : null, matching);
    }

    public String getPath () {
        return path;
    }

    public void setPath (String path) {
        this.path = path;
    }

    public boolean isMatching () {
        return matching;
    }

    public void setMatching (boolean state) {
        this.matching = state;
    }

    public PropertyDescriptor getProperty () {
        return property;
    }

    public void setProperty (PropertyDescriptor property) {
        this.property = property;
    }

    public Class getContainingClass () {
        return containingClass;
    }

    public void setContainingClass (Class containingClass) {
        this.containingClass = containingClass;
    }

    public Object getLeft () {
        return left;
    }

    public void setLeft (Object left) {
        this.left = left;
    }

    public Object getRight () {
        return right;
    }

    public void setRight (Object right) {
        this.right = right;
    }

    public Integer getIndex () {
        return index;
    }

    public void setIndex (Integer index) {
        this.index = index;
    }
}