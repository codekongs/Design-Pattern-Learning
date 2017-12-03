package cn.codekong.imageloader.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.codekong.imageloader.R;
import cn.codekong.imageloader.util.ImageLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageLoader imageLoader = new ImageLoader();
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: ");
        initView();
    }

    private void initView() {
        mTextView = findViewById(R.id.id_textview);
        final ImageView imageView = findViewById(R.id.id_imageview);
        final String imageUrl = "http://img3.imgtn.bdimg.com/it/u=586993804,1661460647&fm=27&gp=0.jpg";
        final Button button = findViewById(R.id.load_image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 点击了");
                imageLoader.displayImage(imageUrl, imageView);
            }
        });
    }
}
