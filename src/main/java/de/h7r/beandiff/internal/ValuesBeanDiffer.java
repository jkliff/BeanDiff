package de.h7r.beandiff.internal;


/**
 *
 */
public class ValuesBeanDiffer <T>
        extends BeanDiffer<T> {

    @Override
    public BeanFieldComparator getComparationStrategy () {

        return new BeanFieldComparator () {

            public boolean compare (final Object left,
                                    final Object right) {
                System.out.println (String.format ("%s == %s ?", left, right));
                if (left == null) {
                    return right == null;
                } else if (right == null) {
                    return false;
                }

                return left.equals (right);
            }
        };
    }
}
