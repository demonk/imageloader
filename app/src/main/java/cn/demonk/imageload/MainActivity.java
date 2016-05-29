package cn.demonk.imageload;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

/**
 * Created by ligs on 5/22/16.
 */
public class MainActivity extends Activity {

    private GridView gridView;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        this.setContentView(R.layout.gridview);

        gridView = (GridView) this.findViewById(R.id.id_gridview);

        setAdatper();
    }

    //set adapter for gridView
    private void setAdatper() {
        if (gridView != null) {
            ListImgAdapter imgAdapter = new ListImgAdapter(this, 0, Images.imageThumbUrls);
            gridView.setAdapter(imgAdapter);
        }
    }
}
