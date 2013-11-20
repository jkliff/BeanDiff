package de.h7r.beandiff.internal;

/**
 *
 */
public class ValuesBeanDiffer <T>
        extends BeanDiffer<T> {

    private static final BeanFieldComparator BEAN_FIELD_COMPARATOR = new BeanFieldComparator () {

                                                                       public boolean compare (final Object left,
                                                                                               final Object right) {
                                                                           getLogger ().log (String.format ("%s == %s ?", left, right));
                                                                           if (left == null) {
                                                                               return right == null;
                                                                           } else if (right == null) {
                                                                               return false;
                                                                           }

                                                                           return left.equals (right);
                                                                       }
                                                                   };

    @Override
    public BeanFieldComparator getComparissonStrategy () {

        return BEAN_FIELD_COMPARATOR;
    }
}
