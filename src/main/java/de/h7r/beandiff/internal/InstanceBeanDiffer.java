package de.h7r.beandiff.internal;


/**
 *
 */
public class InstanceBeanDiffer <T>
        extends BeanDiffer<T> {

    @Override
    public BeanFieldComparator getComparationStrategy () {

        return new BeanFieldComparator () {

            public boolean compare (final Object left,
                                    final Object right) {
                return left == right;
            }
        };
    }
}
