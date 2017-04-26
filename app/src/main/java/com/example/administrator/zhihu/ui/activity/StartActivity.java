package com.example.administrator.zhihu.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.zhihu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.start_iv)
    ImageView startIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
//        getWindow().setFormat(PixelFormat.RGBA_8888);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
//
//        setContentView(R.layout.activity_start);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

//        //Display the current version number
//        PackageManager pm = getPackageManager();
//        try {
//            PackageInfo pi = pm.getPackageInfo("com.example.administrator.zhihu", 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        ShareSDK.initSDK(this);
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Main WordPress Activity. */
            Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
            StartActivity.this.startActivity(mainIntent);
            StartActivity.this.finish();
        }, 2900); //2900 for release
    }

//    public void getImageInfoByAsyncHttpClientGet() {
//        // 创建异步的客户端对象
//        AsyncHttpClient client = new AsyncHttpClient();
//        // 请求的地址
//        // 创建请求参数的封装的对象
//        RequestParams params = new RequestParams();
//        String param = "http://news-at.zhihu.com/api/4/start-image/1080*1776";
//
//        // 发送get请求的时候 url地址 相应参数,匿名回调对象
//        client.get(param, params, new AsyncHttpResponseHandler() {
//
//            JSONObject json = null;
//
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                // 成功处理的方法
//                String result = new String(bytes);
//                try {
//                    json = new JSONObject(result);
//                    String url = json.getString("img");
//                    Toast.makeText(StartActivity.this, url, Toast.LENGTH_SHORT).show();
//                    startIv.setImageUrl(json.getString("img"));
//
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//                /* Create an Intent that will start the Main WordPress Activity. */
//                            Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
//                            StartActivity.this.startActivity(mainIntent);
//                            StartActivity.this.finish();
//                        }
//                    }, 2900); //2900 for release
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                throwable.printStackTrace();
//            }
//
//        });
//    }
}
