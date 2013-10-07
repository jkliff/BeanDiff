package de.h7r.beandiff.internal;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.h7r.beandiff.BeanDiffResult;
import de.h7r.beandiff.BeanDiffRuntimeException;

/**
 *
 */
public abstract class BeanDiffer <T> {

    private Set<Integer> visitedObjectAddresses = Sets.newHashSet ();

    public final BeanDiffResult of (final T left,
                                    final T right) {
        try {
            Preconditions.checkNotNull (left);
            Preconditions.checkNotNull (right);

            final BeanFieldComparator bfc = getComparationStrategy ();

            final List<ComparableBeanProperty> properties = getProperties (bfc, "", Introspector.getBeanInfo (left.getClass ()), left,
                    right);

            final Set<ComparableBeanProperty> mismatches = ImmutableSet.copyOf (Iterables.filter (properties,
                    new Predicate<ComparableBeanProperty> () {
                        public boolean apply (ComparableBeanProperty cbp) {
                            return !cbp.isMatching ();
                        }
                    }));

            final BeanDiffResult result = new BeanDiffResult ();
            result.setMismatchingFields (Collections.unmodifiableSet (Sets.newHashSet (mismatches)));
            return result;

        } catch (IntrospectionException e) {
            throw new BeanDiffRuntimeException (e);
        } catch (InvocationTargetException e) {
            throw new BeanDiffRuntimeException (e);
        } catch (IllegalAccessException e) {
            throw new BeanDiffRuntimeException (e);
        }
    }

    private List<ComparableBeanProperty> getProperties (final BeanFieldComparator comparator,
                                                        final String path,
                                                        final BeanInfo beanInfo,
                                                        final Object left,
                                                        final Object right)
            throws IntrospectionException,
                InvocationTargetException,
                IllegalAccessException {

        final List<ComparableBeanProperty> properties = Lists.newArrayList ();

        properties.addAll (getPropertiesInternal (comparator, path, beanInfo, left, right));

        return properties;

    }

    /**
     * Compares objects based on their properties.
     * 
     * @param comparator
     * @param path
     * @param beanInfo
     * @param left
     * @param right
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IntrospectionException
     */
    private List<ComparableBeanProperty> getPropertiesInternal (final BeanFieldComparator comparator,
                                                                final String path,
                                                                final BeanInfo beanInfo,
                                                                final Object left,
                                                                final Object right)
            throws IllegalAccessException,
                InvocationTargetException,
                IntrospectionException {
        final List<ComparableBeanProperty> properties = Lists.newArrayList ();
        final Class<? extends Object> class1 = left.getClass ();
        final Class<? extends Object> class2 = right.getClass ();

        if (!class1.equals (class2)) {
            System.out.println ("not same class");
            final ComparableBeanProperty cbp = new ComparableBeanProperty ();
            cbp.setPath (path);

            cbp.setProperty (null);
            cbp.setMatching (false);
            cbp.setContainingClass (left.getClass ());
            cbp.setLeft (left);
            cbp.setRight (right);
            properties.add (cbp);

            return properties;
        }
        if (left instanceof Iterable) {

            System.out.println ("iterables");
            Iterable<?> leftIterable = (Iterable<?>) left;
            Iterable<?> rightIterable = (Iterable<?>) right;

            Iterator<?> leftIterator = leftIterable.iterator ();
            Iterator<?> rightIterator = rightIterable.iterator ();
            int idx = -1;
            while (leftIterator.hasNext ()) {
                idx++;
                if (!rightIterator.hasNext ()) {

                }
                Object next = leftIterator.next ();
                Object next2 = rightIterator.next ();

                if (next == null && next2 == null) {
                    continue;
                } else if (next != null && next2 != null && next.equals (next2)) {
                    continue;
                }

                System.out.println ("nulls");
                final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                cbp.setPath (String.format ("%s[%s]", path, idx));
                cbp.setIndex (idx);

                cbp.setMatching (Iterables.elementsEqual (leftIterable, rightIterable));
                cbp.setContainingClass (left.getClass ());
                cbp.setLeft (left);
                cbp.setRight (right);
                properties.add (cbp);
                return properties;

            }

        } else if (left instanceof Collection) {

            System.out.println ("iterables");
            Collection<?> leftCollection = (Collection<?>) left;
            Collection<?> rightCollection = (Collection<?>) right;

            System.out.println ("nulls");
            final ComparableBeanProperty cbp = new ComparableBeanProperty ();
            cbp.setPath (path);
            cbp.setProperty (null);
            cbp.setMatching (Iterables.elementsEqual (leftCollection, rightCollection));
            cbp.setContainingClass (left.getClass ());
            cbp.setLeft (left);
            cbp.setRight (right);
            properties.add (cbp);

            return properties;

        }

        final PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors ();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName ().equals ("class")) {
                continue;
            }

            try {

                if (!pd.getPropertyType ().isPrimitive () && visitedObjectAddresses.contains (System.identityHashCode (pd))) {
                    System.out.println ("WARN: already visited object. would cause circular dependency. skipping.");
                    continue;
                }

                visitedObjectAddresses.add (System.identityHashCode (pd));

                Method readMethod = pd.getReadMethod ();

                if (readMethod == null) {
                    System.out.println (String.format ("can't evaluate property %s: no read method", pd));
                    continue;
                }

                if (pd.getPropertyType ().isPrimitive ()) {

                    Object leftInvoke = null;
                    if (left != null) {
                        leftInvoke = readMethod.invoke (left);
                    }

                    Object rightInvoke = null;
                    if (right != null) {
                        rightInvoke = readMethod.invoke (right);
                    }

                    // System.out.println ("primitive comparisson " + pd);
                    // System.out.println (leftInvoke == null ? "null obj" :
                    // leftInvoke);
                    // System.out.println (rightInvoke == null ? "null obj" :
                    // rightInvoke);

                    final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                    cbp.setPath (path);
                    cbp.setProperty (pd);

                    boolean state = false;
                    if (leftInvoke != null) {
                        cbp.setMatching (leftInvoke.equals (rightInvoke));
                    } else {
                        cbp.setMatching (leftInvoke == null && rightInvoke == null);
                    }

                    cbp.setContainingClass (left.getClass ());
                    cbp.setLeft (left);
                    cbp.setRight (right);
                    properties.add (cbp);
                } else if (pd.getPropertyType ().getPackage ().getName ().equals ("java.lang")) {
                    // System.out.println ("java lang comparisson of " + pd +
                    // ", " + pd.getPropertyType ().getPackage ().getName ());

                    final Object newLeft = readMethod.invoke (left);
                    final Object newRight = readMethod.invoke (right);

                    final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                    cbp.setPath (path);
                    cbp.setProperty (pd);
                    cbp.setMatching (comparator.compare (newLeft, newRight));
                    cbp.setContainingClass (left.getClass ());
                    cbp.setLeft (left);
                    cbp.setRight (right);
                    properties.add (cbp);

                } else {

                    // full comparison
                    if (left == null && right == null) {
                        final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                        cbp.setPath (path);
                        cbp.setProperty (pd);
                        cbp.setMatching (true);
                        properties.add (cbp);
                        cbp.setContainingClass (left.getClass ());
                        cbp.setLeft (left);
                        cbp.setRight (right);
                        continue;
                    } else if (left == null && right != null) {
                        final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                        cbp.setPath (path);
                        cbp.setProperty (pd);
                        cbp.setMatching (false);
                        properties.add (cbp);
                        cbp.setContainingClass (right.getClass ());
                        cbp.setLeft (left);
                        cbp.setRight (right);
                        continue;
                    } else if (left != null && right == null) {
                        final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                        cbp.setPath (path);

                        cbp.setProperty (pd);
                        cbp.setMatching (false);
                        properties.add (cbp);
                        cbp.setContainingClass (left.getClass ());
                        cbp.setLeft (left);
                        cbp.setRight (right);
                        continue;
                    }

                    final Object newLeft = readMethod.invoke (left);
                    final Object newRight = readMethod.invoke (right);

                    if (newLeft == null || newRight == null) {
                        final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                        cbp.setPath (path);

                        cbp.setProperty (pd);
                        cbp.setMatching (newLeft == newRight);
                        cbp.setContainingClass (left.getClass ());
                        cbp.setLeft (newLeft);
                        cbp.setRight (newRight);
                        properties.add (cbp);
                        continue;
                    }

                    properties.addAll (getProperties (comparator, path + "." + pd.getName (),
                            Introspector.getBeanInfo (pd.getPropertyType ()), newLeft, newRight));
                }

            } catch (NullPointerException e) {
                System.out.println ("processing " + pd);
                e.printStackTrace ();
                throw e;
            }
        }
        return properties;
    }

    public abstract BeanFieldComparator getComparationStrategy ();
}
