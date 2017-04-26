package com.example.administrator.zhihu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.administrator.zhihu.Model.NewsModel;
import com.example.administrator.zhihu.R;
import com.example.administrator.zhihu.bean.CommentBean;
import com.example.administrator.zhihu.http.HttpMethods;
import com.example.administrator.zhihu.http.NewsListener;
import com.example.administrator.zhihu.http.NewsModelImpl;
import com.example.administrator.zhihu.http.ProgressSubscriber;
import com.example.administrator.zhihu.http.SubscriberOnNextListener;
import com.example.administrator.zhihu.utils;
import com.loopj.android.image.SmartImageView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, NewsListener {

    @BindView(R.id.sv_content)
    SmartImageView svContent;
    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.tb_content)
    Toolbar tbContent;

    String url1 = "http://news-at.zhihu.com/api/4/news/";
    JSONObject json = null;
    private NewsModel weatherModel;
    private SubscriberOnNextListener<CommentBean> getTopMovieOnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        initView();
        initDate();
    }

    public void initDate() {
        Intent intent = getIntent();
        int ID = intent.getIntExtra("ID", 0);
        url1 += ID;
        weatherModel.getWeather(ContentActivity.this, this, url1);
        HttpMethods.getInstance().getComment(
                new ProgressSubscriber<>(getTopMovieOnNext, this), ID + "");
    }

    public void initView() {
        tbContent.setTitle("");
        weatherModel = new NewsModelImpl();
        setSupportActionBar(tbContent);
        tbContent.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        tbContent.setNavigationOnClickListener(v -> finish());
        tbContent.setOnMenuItemClickListener(this);
        getTopMovieOnNext = commentBean -> {
        };
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                utils.showShare(ContentActivity.this);
                break;
            case R.id.action_star:
                break;
            case R.id.action_comment:
                break;
            case R.id.action_good:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public void onSuccess(String data) {
        try {
            json = new JSONObject(data);

            wvContent.getSettings().setJavaScriptEnabled(true);
//                    wvContent.loadDataWithBaseURL("about:blank",json.getString("body")+json.getJSONArray("css").get(0), "text/html", "utf-8",null);
            svContent.setImageUrl(json.optString("image"));

            String css = "<link rel=\"stylesheet\" href=\"" + json.getJSONArray("css").get(0) + "\" type=\"text/css\">";
            String html = "<html><head>" + css + "</head><body>" + json.getString("body") + "</body></html>";
            html = html.replace("<div class=\"img-place-holder\">", "");
            wvContent.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFaile() {

    }
}
