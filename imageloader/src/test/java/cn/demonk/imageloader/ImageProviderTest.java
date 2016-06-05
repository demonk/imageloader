package cn.demonk.imageloader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;

import cn.demonk.imageloader.task.getimage.ImageProvider;

/**
 * Created by ligs on 6/5/16.
 */
public class ImageProviderTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetUri() throws Exception {
        File file = new File("file:///media/Data/GameSdk/ftgamesdk/main/base/src/main/java/cn/flysdk/base/remote/RemoteObject.java");

        ImageProvider provider = new ImageProvider(file.getAbsolutePath());
        URI uri = new URI("file:///media/Data/GameSdk/ftgamesdk/main/base/src/main/java/cn/flysdk/base/remote/RemoteObject.java");
        uri=new URI("http://www.demonk.cn/welcome.txt");
        System.out.println(uri.getScheme());
        System.out.println(uri.getPath());
    }
}