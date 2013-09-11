package de.h7r.beandiff.test;

import de.h7r.beandiff.BeanDiff;
import de.h7r.beandiff.BeanDiffResult;
import de.h7r.beandiff.internal.BeanDiffer;
import de.h7r.beandiff.test.beans.MyRecursiveTestBean;
import de.h7r.beandiff.test.beans.MyTestBean;
import org.junit.Assert;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public class DiffTest {

    @Test(expected = NullPointerException.class)
    public void testSimpleNPE1 ()
            throws NoSuchFieldException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        MyTestBean obj = new MyTestBean ();

        BeanDiffer<MyTestBean> diff = BeanDiff.ofValues ();
        diff.of (null, obj);
    }

    @Test(expected = NullPointerException.class)
    public void testSimpleNPE2 ()
            throws NoSuchFieldException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        MyTestBean obj = new MyTestBean ();

        BeanDiffer<MyTestBean> diff = BeanDiff.ofValues ();
        diff.of (obj, null);
    }

    @Test
    public void testSimpleAPI ()
            throws NoSuchFieldException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        MyTestBean left = new MyTestBean ();
        MyTestBean right = new MyTestBean ();

        BeanDiffer<MyTestBean> diff = BeanDiff.ofValues ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);
    }

    @Test
    public void testSimpleAPIRecursive ()
            throws NoSuchFieldException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        MyRecursiveTestBean left = new MyRecursiveTestBean ();
        MyRecursiveTestBean right = new MyRecursiveTestBean ();

        BeanDiffer<MyRecursiveTestBean> diff = BeanDiff.ofValues ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);
    }

    @Test
    public void testSimpleAPICircular ()
            throws NoSuchFieldException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        MyCircularTestBeanA left = new MyCircularTestBeanA ();
        MyCircularTestBeanA right = new MyCircularTestBeanA ();

        BeanDiffer<MyCircularTestBeanA> diff = BeanDiff.ofValues ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);
    }

    @Test
    public void testSimpleAPI2 () throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, IntrospectionException {

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
    public void testSimpleAPI3 () {

        BeanDiffer<MyTestBean> diff = BeanDiff.ofInstances ();

    }

    @Test
    public void testSimpleAPI4 ()
            throws NoSuchFieldException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        MyTestBean left = null;
        MyTestBean right = null;

        BeanDiffer<MyTestBean> diff = BeanDiff.<MyTestBean>ofInstances ();
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);
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

}
