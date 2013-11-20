package de.h7r.beandiff;

import de.h7r.beandiff.internal.BeanDiffer;
import de.h7r.beandiff.internal.ValuesBeanDiffer;

/**
 *
 */
public class BeanDiff {

    public static enum GetterOnlyProperty {
        IGNORE, ACCEPT;
    }

    public static <T> BeanDiffer<T> ofValues () {

        return new ValuesBeanDiffer<T> ();

    }

}
