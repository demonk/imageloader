package cn.demonk.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import cn.demonk.baseutils.handler.UIHandler;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        mGridView = (GridView) findViewById(R.id.im_gridview);

        setAdapter();
    }

    private void setAdapter() {
        if (mGridView != null) {
            ImgAdapter adapter = new ImgAdapter(this, 0, Images.imageThumbUrls);
//            ImgAdapter adapter = new ImgAdapter(this, 0, new String[]{"http://www.demonk.cn/tmp/1.jpg"});
            mGridView.setAdapter(adapter);
        }
    }
}
