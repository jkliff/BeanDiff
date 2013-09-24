package de.h7r.beandiff.internal;

import java.beans.PropertyDescriptor;

public class ComparableBeanProperty {

    String             path;
    boolean            state = true;
    PropertyDescriptor property;
    Class              containingClass;
    Object             left;
    Object             right;
    Integer            index;

    @Override
    public String toString () {

        return String.format ("%s -> %s: %s", path, property != null ? property.getName () : null, state);
    }
}