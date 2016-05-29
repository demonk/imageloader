package cn.demonk.imageloader.net;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ligs on 5/29/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(android.util.Log.class)
public class GetTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetHeader() throws Exception {
        Get.getHeader("http://www.demonk.cn/tmp/hello.txt");
    }
}