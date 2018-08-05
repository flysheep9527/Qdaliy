package com.ls.qdaliy.daliy;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.ls.qdaliy.R;
import com.ls.qdaliy.api.HttpApi;
import com.ls.qdaliy.api.HttpCallBack;
import com.ls.qdaliy.bean.Comment;
import com.ls.qdaliy.bean.Post;
import com.ls.qdaliy.ui.BarrageView;
import com.ls.qdaliy.ui.Adapter.BaseAdapter;
import com.ls.qdaliy.ui.box.CommentBox;
import com.ls.qdaliy.util.JsonParseUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/8.
 * Describe: 我说对应的界面
 */
public class PaperCommentActivity extends AppCompatActivity {

    @BindView(R.id.barrage_bg)
    ImageView mBarrageBG;
    @BindView(R.id.barrage_view)
    BarrageView mBarrageView;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.comment_container)
    RecyclerView mCommentContainer;

    private static final String TAG = "PaperCommentActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_one);
        ButterKnife.bind(this);
        mBarrageView.setBarrageSize(5);

        Intent intent = getIntent();
        long id = intent.getLongExtra(LaunchActivity.PAPER_ID, 0);
        init(id);
    }

    private void init(long id) {
        HttpApi.getPaperOne(id, new PaperOneCallBack());
    }

    private void updateUI(final JsonParseUtil json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<Comment> comments =
                        json.getList("options", new TypeToken<ArrayList<Comment>>() {
                        }.getType());
                mBarrageView.setBarrages(comments);
                mBarrageView.start();

                Glide.with(PaperCommentActivity.this)
                        .load(json.getData("image", String.class))
                        .into(mBarrageBG);
                Post post = json.getData("post", Post.class);
                mTitle.setText(post.getTitle());
                mDescription.setText(post.getDescription());

                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mCommentContainer.setLayoutManager(layoutManager);
                mCommentContainer.addItemDecoration(new SpacesItemDecoration(8));
                BaseAdapter<Comment> adapter = new BaseAdapter<>(PaperCommentActivity.this, comments, CommentBox.class);
                mCommentContainer.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBarrageView.stop();
    }

    private class PaperOneCallBack implements HttpCallBack {

        @Override
        public void onFailure(int code) {
            Log.d(TAG, "fail to get paper one");
        }

        @Override
        public void onResponse(InputStream inputStream) {
            JsonParseUtil jsonParseUtil = new JsonParseUtil(inputStream);
            updateUI(jsonParseUtil);
        }
    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration{
        private int space;

        SpacesItemDecoration(int space){
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            outRect.top=space;
        }
    }
}
