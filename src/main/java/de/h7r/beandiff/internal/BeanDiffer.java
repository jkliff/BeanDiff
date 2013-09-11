package de.h7r.beandiff.internal;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.h7r.beandiff.BeanDiffResult;

/**
 *
 */
public abstract class BeanDiffer <T> {

    private Set<Integer> visitedObjectAddresses = Sets.newHashSet ();

    public final BeanDiffResult of (final T left,
                                    final T right)
            throws NoSuchFieldException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        Preconditions.checkNotNull (left);
        Preconditions.checkNotNull (right);

        final BeanFieldComparator bfc = getComparationStrategy ();

        final List<ComparableBeanProperty> properties = getProperties (bfc, "", Introspector.getBeanInfo (left.getClass ()), left, right);

        final Iterable<ComparableBeanProperty> mismatches = Iterables.filter (properties, new Predicate<ComparableBeanProperty> () {
            public boolean apply (ComparableBeanProperty cbp) {
                return !cbp.state;
            }
        });

        System.out.println (properties);
        final BeanDiffResult result = new BeanDiffResult ();
        result.setMismatchingFields (Collections.unmodifiableSet (Sets.newHashSet (mismatches)));
        return result;
    }

    private List<ComparableBeanProperty> getProperties (final BeanFieldComparator bfc,
                                                        final String path,
                                                        final BeanInfo beanInfo,
                                                        final Object left,
                                                        final Object right)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        final List<ComparableBeanProperty> properties = Lists.newArrayList ();

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

                    System.out.println ("primitive comparisson " + pd);
                    System.out.println (leftInvoke == null ? "null obj" : leftInvoke);
                    System.out.println (rightInvoke == null ? "null obj" : rightInvoke);

                    final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                    cbp.path = path;
                    cbp.property = pd;

                    boolean state = false;
                    if (leftInvoke != null) {
                        cbp.state = leftInvoke.equals (rightInvoke);
                    } else {
                        cbp.state = leftInvoke == null && rightInvoke == null;
                    }

                    cbp.containingClass = left.getClass ();
                    cbp.left = left;
                    cbp.right = right;
                    properties.add (cbp);
                } else if (pd.getPropertyType ().getPackage ().getName ().equals ("java.lang")) {
                    System.out.println ("java lang comparisson of " + pd + ", " + pd.getPropertyType ().getPackage ().getName ());

                    final Object newLeft = readMethod.invoke (left);
                    final Object newRight = readMethod.invoke (right);

                    final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                    cbp.path = path;
                    cbp.property = pd;
                    cbp.state = bfc.compare (newLeft, newRight);
                    cbp.containingClass = left.getClass ();
                    cbp.left = left;
                    cbp.right = right;
                    properties.add (cbp);

                } else {

                    System.out.println ("full comparisson");

                    if (left == null && right == null) {
                        final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                        cbp.path = path;
                        cbp.property = pd;
                        cbp.state = true;
                        properties.add (cbp);
                        cbp.containingClass = left.getClass ();
                        cbp.left = left;
                        cbp.right = right;
                        continue;
                    } else if (left == null && right != null) {
                        final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                        cbp.path = path;
                        cbp.property = pd;
                        cbp.state = false;
                        properties.add (cbp);
                        cbp.containingClass = left.getClass ();
                        cbp.left = left;
                        cbp.right = right;
                        continue;
                    } else if (left != null && right == null) {
                        final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                        cbp.path = path;
                        cbp.property = pd;
                        cbp.state = false;
                        properties.add (cbp);
                        cbp.containingClass = left.getClass ();
                        cbp.left = left;
                        cbp.right = right;
                        continue;
                    }

                    final Object newLeft = readMethod.invoke (left);
                    final Object newRight = readMethod.invoke (right);

                    if (newLeft == null || newRight == null) {
                        final ComparableBeanProperty cbp = new ComparableBeanProperty ();
                        cbp.path = path;
                        cbp.property = pd;
                        cbp.state = newLeft == newRight;
                        cbp.containingClass = left.getClass ();
                        cbp.left = newLeft;
                        cbp.right = newRight;
                        properties.add (cbp);
                        continue;
                    }

                    properties.addAll (getProperties (bfc, path + "." + pd.getName (), Introspector.getBeanInfo (pd.getPropertyType ()),
                            newLeft, newRight));
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
