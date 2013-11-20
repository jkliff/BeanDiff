package de.h7r.beandiff.test;

import org.junit.Assert;
import org.junit.Test;

import de.h7r.beandiff.BeanDiff;
import de.h7r.beandiff.BeanDiffResult;
import de.h7r.beandiff.internal.BeanDiffer;
import de.h7r.beandiff.logger.DiffLogger;
import de.h7r.beandiff.test.beans.MyTestBean;

public class VerbosityTest {

    @Test
    public void testLogOutput () {

        MyTestBean left = new MyTestBean ();
        MyTestBean right = new MyTestBean ();

        final DiffLogger testLogger = new DiffLogger () {

            private StringBuffer sb = new StringBuffer ();

            public void log (String s) {
                sb.append (s);

            }

            public String getContent () {
                return sb.toString ();
            }
        };
        BeanDiffer<MyTestBean> diff = BeanDiff.<MyTestBean>ofValues ().withLogger (testLogger);
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);

        Assert.assertFalse (testLogger.toString ().isEmpty ());

    }

    @Test
    public void testLogOutputDefaultLogger () {

        MyTestBean left = new MyTestBean ();
        MyTestBean right = new MyTestBean ();

        BeanDiffer<MyTestBean> diff = BeanDiff.<MyTestBean>ofValues ().withLogger (DiffLogger.STDOUT_LOGGER);
        Assert.assertNotNull (diff);

        BeanDiffResult result = diff.of (left, right);
        Assert.assertNotNull (result);

    }

}
