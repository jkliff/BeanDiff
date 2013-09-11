package de.h7r.beandiff;

import java.util.Set;

import de.h7r.beandiff.internal.ComparableBeanProperty;

/**
 *
 */
public class BeanDiffResult {

    private Set<ComparableBeanProperty> mismatchingFields;

    public Set<ComparableBeanProperty> getMismatchingFields () {
        return mismatchingFields;
    }

    public void setMismatchingFields (final Set<ComparableBeanProperty> filter) {
        this.mismatchingFields = filter;
    }

}
