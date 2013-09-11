package de.h7r.beandiff;

import de.h7r.beandiff.internal.BeanDiffer;
import de.h7r.beandiff.internal.InstanceBeanDiffer;
import de.h7r.beandiff.internal.ValuesBeanDiffer;

/**
 *
 */
public class BeanDiff {

    public static <T> BeanDiffer<T> ofValues () {

        return new ValuesBeanDiffer<T> ();

    }

    public static <T> BeanDiffer<T> ofInstances () {

        return new InstanceBeanDiffer<T> ();

    }
}
