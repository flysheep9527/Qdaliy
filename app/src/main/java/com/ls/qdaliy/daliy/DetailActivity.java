package com.ls.qdaliy.daliy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ls.qdaliy.R;
import com.ls.qdaliy.api.HttpApi;
import com.ls.qdaliy.api.HttpCallBack;
import com.ls.qdaliy.bean.ArticleDetail;
import com.ls.qdaliy.util.JsonParseUtil;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/5/31.
 * Describe:
 */
public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.web_view)
    WebView mWebView;

//    private static final String TAG = "DetailActivity";
    public static final String ARTICLE_ID = "article_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        long id = intent.getLongExtra(ARTICLE_ID, -1);
        initWebView(id);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        long id = intent.getLongExtra(ARTICLE_ID, -1);
        initWebView(id);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(long id) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setBlockNetworkImage(false);

        if (id != -1) {
            HttpApi.getDetail(id, new HttpCallBack() {
                @Override
                public void onFailure(int code) {

                }

                @Override
                public void onResponse(InputStream inputStream) {
                    JsonParseUtil jsonParseUtil = new JsonParseUtil(inputStream);
                    final ArticleDetail article =
                            jsonParseUtil.getData("article", ArticleDetail.class);

                    DetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.loadDataWithBaseURL("http://app3.qdaily.com", article.getBody(), "text/html; charset=UTF-8", null, "http://app3.qdaily.com");
                        }
                    });
                }
            });
        }
    }
}
