package de.h7r.beandiff.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import de.h7r.beandiff.BeanDiff;
import de.h7r.beandiff.BeanDiffResult;
import de.h7r.beandiff.internal.BeanDiffer;
import de.h7r.beandiff.internal.ComparableBeanProperty;
import de.h7r.beandiff.test.beans.MyCircularTestBeanA;
import de.h7r.beandiff.test.beans.MyRecursiveTestBean;
import de.h7r.beandiff.test.beans.MyTestBean;
import de.h7r.beandiff.test.beans.MyTestBean2;

/**
 *
 */
public class DiffTest {

    @Test(expected = NullPointerException.class)
    public void testSimpleNPE1 () {

        MyTestBean obj = new MyTestBean ();

        BeanDiffer<MyTestBean> diff = BeanDiff.ofValues ();
        diff.of (null, obj);
    }

    @Test(expected = NullPointerException.class)
    public void testSimpleNPE2 () {

        MyTestBean obj = new MyTestBean ();

        BeanDiffer<MyTestBean> diff = BeanDiff.ofValues ();
        diff.of (obj, null);
    }

    @Test
    public void testSimpleAPI () {

        MyTestBean left = new MyTestBean ();
        MyTestBean right = new MyTestBean ();

        BeanDiffer<MyTestBean> diff = BeanDiff.ofValues ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);
    }

    @Test
    public void testSimpleAPIRecursive () {

        MyRecursiveTestBean left = new MyRecursiveTestBean ();
        MyRecursiveTestBean right = new MyRecursiveTestBean ();

        BeanDiffer<MyRecursiveTestBean> diff = BeanDiff.ofValues ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);
    }

    @Test
    public void testSimpleAPICircular () {

        MyCircularTestBeanA left = new MyCircularTestBeanA ();
        MyCircularTestBeanA right = new MyCircularTestBeanA ();

        BeanDiffer<MyCircularTestBeanA> diff = BeanDiff.ofValues ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);
    }

    @Test
    public void testSimpleAPIValues2 () {

        MyTestBean left = new MyTestBean ();
        left.setBar (1);
        left.setFoo ("asdf");
        MyTestBean right = new MyTestBean ();
        right.setBar (1);

        BeanDiffer<MyTestBean> diff = BeanDiff.ofValues ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);

        Assert.assertEquals (1, result.getMismatchingFields ().size ());

    }

    @Test
    public void testSimpleAPIValues3 () {

        MyTestBean left = new MyTestBean ();
        left.setBar (1);
        left.setFoo ("asdf");
        MyTestBean right = new MyTestBean ();
        right.setBar (1);
        right.setFoo ("asdf");

        BeanDiffer<MyTestBean> diff = BeanDiff.ofValues ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);

        Assert.assertEquals (0, result.getMismatchingFields ().size ());
    }

    @Test
    public void testSimpleAPIValues4 () {

        MyTestBean2 left = new MyTestBean2 ();
        left.setFoo (new MyTestBean ());

        MyTestBean2 right = new MyTestBean2 ();
        right.setFoo (new MyTestBean ());

        BeanDiffer<MyTestBean2> diff = BeanDiff.ofInstances ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);

        Assert.assertEquals (0, result.getMismatchingFields ().size ());
    }

    @Test
    public void testSimpleAPIInstances1 () {

        MyTestBean left = new MyTestBean ();
        left.setBar (1);
        left.setFoo ("asdf");

        BeanDiffer<MyTestBean> diff = BeanDiff.ofInstances ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, left);
        Assert.assertNotNull (result);

        Assert.assertEquals (0, result.getMismatchingFields ().size ());
    }

    @Test
    public void testSimpleAPIInstances2 () {

        MyTestBean left = new MyTestBean ();
        left.setBar (1);
        left.setFoo ("asdf");
        MyTestBean right = new MyTestBean ();
        right.setBar (1);
        // java caches this string, so is ok.
        right.setFoo ("asdf");

        BeanDiffer<MyTestBean> diff = BeanDiff.ofInstances ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);

        Assert.assertEquals (0, result.getMismatchingFields ().size ());
    }

    @Test
    public void testSimpleAPIInstnaces3 () {

        MyTestBean left = new MyTestBean ();
        left.setBar (1);
        String foo = "asdf";
        left.setFoo (foo);
        MyTestBean right = new MyTestBean ();
        right.setBar (1);
        right.setFoo (foo);

        BeanDiffer<MyTestBean> diff = BeanDiff.<MyTestBean>ofInstances ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);
        Assert.assertEquals (0, result.getMismatchingFields ().size ());
    }

    @Test
    @Ignore
    // FIXME find a proper use case. do i need to check by instance at all?
    public void testSimpleAPIInstances4 () {

        MyTestBean2 left = new MyTestBean2 ();

        left.setFoo (new MyTestBean ());
        MyTestBean2 right = new MyTestBean2 ();

        right.setFoo (new MyTestBean ());

        BeanDiffer<MyTestBean2> diff = BeanDiff.ofInstances ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);

        Assert.assertEquals (1, result.getMismatchingFields ().size ());
    }

    @Test
    public void testSimpleAPIInstances5 () {

        MyTestBean2 left = new MyTestBean2 ();

        MyTestBean myTestBean = new MyTestBean ();
        left.setFoo (myTestBean);
        MyTestBean2 right = new MyTestBean2 ();

        // java caches this string, so is ok.
        right.setFoo (myTestBean);

        BeanDiffer<MyTestBean2> diff = BeanDiff.ofInstances ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);

        Assert.assertEquals (0, result.getMismatchingFields ().size ());
    }

    @Test
    public void testNoDiff1 () {
        // Set<Field> fields = BeanDiff.newInstance ().

        // Assert.assertTrue (0, fields.size ());
    }

    @Test
    public void testNoDiffRecursive () {

    }

    @Test
    public void testSimpleDiff () {

    }

    @Test
    public void testMultipleDiffs () {

    }

    @Test
    public void testMultipleDiffsRecursive () {

    }

    @Test
    public void testBeansDifferentClasses () {
        BeanDiffer<Object> diff = BeanDiff.ofInstances ();
        // non cached Integer
        final Integer right = 4000;
        final String left = "foo";
        BeanDiffResult of = diff.of (left, right);
        Assert.assertNotNull (of);
        Assert.assertEquals (1, of.getMismatchingFields ().size ());
        ComparableBeanProperty mismatchingFields = of.getMismatchingFields ().iterator ().next ();
        Assert.assertTrue (mismatchingFields.getLeft () == left);
        Assert.assertTrue (mismatchingFields.getRight () == right);

    }
}
