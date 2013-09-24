package de.h7r.beandiff.test.contrib;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

import com.google.common.collect.ImmutableList;

import de.h7r.beandiff.BeanDiff;
import de.h7r.beandiff.BeanDiffResult;
import de.h7r.beandiff.internal.BeanDiffer;
import de.h7r.beandiff.internal.ComparableBeanProperty;

public class TestWithList {
    @Test
    public void testRecursiveWithList ()
            throws NoSuchFieldException,
                InvocationTargetException,
                IllegalAccessException,
                IntrospectionException {

        final TestClass t1 = new TestClass ("t1");
        final TestClassInternal t1a = new TestClassInternal ("t1a");
        final TestClassInternal t1b = new TestClassInternal ("t1b");
        t1.setTests (ImmutableList.of (t1a, t1b));
        t1.setTest (new TestClassInternal ("t11"));

        final TestClass t2 = new TestClass ("t2");
        final TestClassInternal t2a = new TestClassInternal ("t2a");
        final TestClassInternal t2b = new TestClassInternal ("t2b");
        t2.setTests (ImmutableList.of (t2a, t2b));
        t2.setTest (new TestClassInternal ("t21"));

        final BeanDiffer<TestClass> diff = BeanDiff.ofValues ();
        final BeanDiffResult r = diff.of (t1, t2);
        System.out.println ("########################");
        for (final ComparableBeanProperty comparableBeanProperty : r.getMismatchingFields ()) {
            System.out.println (comparableBeanProperty);
        }
        Assert.assertEquals (3, r.getMismatchingFields ().size ());

    }

    @Test
    public void testRecursiveWithListSameElements ()
            throws NoSuchFieldException,
                InvocationTargetException,
                IllegalAccessException,
                IntrospectionException {

        final TestClass t1 = new TestClass ("t1");
        final TestClassInternal t1a = new TestClassInternal ("t1a");
        final TestClassInternal t1b = new TestClassInternal ("t1b");
        t1.setTests (ImmutableList.of (t1a, t1b));
        t1.setTest (new TestClassInternal ("t11"));

        final TestClass t2 = new TestClass ("t2");
        t2.setTests (ImmutableList.of (t1a, t1b));
        t2.setTest (new TestClassInternal ("t21"));

        final BeanDiffer<TestClass> diff = BeanDiff.ofValues ();
        final BeanDiffResult r = diff.of (t1, t2);
        System.out.println ("########################");
        for (final ComparableBeanProperty comparableBeanProperty : r.getMismatchingFields ()) {
            System.out.println (comparableBeanProperty);
        }
        Assert.assertEquals (2, r.getMismatchingFields ().size ());

    }

    @Test
    public void testRecursiveWithListFirstCommonElement ()
            throws NoSuchFieldException,
                InvocationTargetException,
                IllegalAccessException,
                IntrospectionException {

        final TestClass t1 = new TestClass ("t1");
        final TestClassInternal t1a = new TestClassInternal ("t1a");
        final TestClassInternal t1b = new TestClassInternal ("t1b");
        t1.setTests (ImmutableList.of (t1a, t1b));
        t1.setTest (new TestClassInternal ("t11"));

        final TestClass t2 = new TestClass ("t2");
        final TestClassInternal t2a = new TestClassInternal ("t2a");
        t2.setTests (ImmutableList.of (t1a, t2a));
        t2.setTest (new TestClassInternal ("t21"));

        final BeanDiffer<TestClass> diff = BeanDiff.ofValues ();
        final BeanDiffResult r = diff.of (t1, t2);
        System.out.println ("########################");
        for (final ComparableBeanProperty comparableBeanProperty : r.getMismatchingFields ()) {
            System.out.println (comparableBeanProperty);
        }
        Assert.assertEquals (3, r.getMismatchingFields ().size ());

    }

    public static class TestClass {

        private List<TestClassInternal> tests;
        private TestClassInternal       test;
        private String                  name;

        public TestClass(final String name) {
            this.name = name;
        }

        public List<TestClassInternal> getTests () {
            return tests;
        }

        public void setTests (final List<TestClassInternal> tests) {
            this.tests = tests;
        }

        public String getName () {
            return name;
        }

        public void setName (final String name) {
            this.name = name;
        }

        public TestClassInternal getTest () {
            return test;
        }

        public void setTest (final TestClassInternal test) {
            this.test = test;
        }

        @Override
        public boolean equals (final Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass () != o.getClass ()) {
                return false;
            }

            final TestClass testClass = (TestClass) o;

            if (name != null ? !name.equals (testClass.name) : testClass.name != null) {
                return false;
            }

            if (test != null ? !test.equals (testClass.test) : testClass.test != null) {
                return false;
            }

            if (tests != null ? !tests.equals (testClass.tests) : testClass.tests != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode () {
            int result = tests != null ? tests.hashCode () : 0;
            result = 31 * result + (test != null ? test.hashCode () : 0);
            result = 31 * result + (name != null ? name.hashCode () : 0);
            return result;
        }

        @Override
        public String toString () {
            final StringBuilder sb = new StringBuilder ("TestClass{");
            sb.append ("tests=").append (tests);
            sb.append (", test=").append (test);
            sb.append (", name='").append (name).append ('\'');
            sb.append ('}');
            return sb.toString ();
        }

    }

    public static class TestClassInternal {

        private List<TestClassInternal> tests;
        private TestClassInternal       test;
        private String                  name;

        public TestClassInternal(final String name) {
            this.name = name;
        }

        public List<TestClassInternal> getTests () {
            return tests;
        }

        public void setTests (final List<TestClassInternal> tests) {
            this.tests = tests;
        }

        public String getName () {
            return name;
        }

        public void setName (final String name) {
            this.name = name;
        }

        public TestClassInternal getTest () {
            return test;
        }

        public void setTest (final TestClassInternal test) {
            this.test = test;
        }

        @Override
        public boolean equals (final Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass () != o.getClass ()) {
                return false;
            }

            final TestClassInternal testClassInternal = (TestClassInternal) o;

            if (name != null ? !name.equals (testClassInternal.name) : testClassInternal.name != null) {
                return false;
            }

            if (test != null ? !test.equals (testClassInternal.test) : testClassInternal.test != null) {
                return false;
            }

            if (tests != null ? !tests.equals (testClassInternal.tests) : testClassInternal.tests != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode () {
            int result = tests != null ? tests.hashCode () : 0;
            result = 31 * result + (test != null ? test.hashCode () : 0);
            result = 31 * result + (name != null ? name.hashCode () : 0);
            return result;
        }

        @Override
        public String toString () {
            final StringBuilder sb = new StringBuilder ("TestClass2{");
            sb.append ("tests=").append (tests);
            sb.append (", test=").append (test);
            sb.append (", name='").append (name).append ('\'');
            sb.append ('}');
            return sb.toString ();
        }

    }

}
