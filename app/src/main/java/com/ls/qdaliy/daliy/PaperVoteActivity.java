package com.ls.qdaliy.daliy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.ls.qdaliy.R;
import com.ls.qdaliy.api.HttpApi;
import com.ls.qdaliy.api.HttpCallBack;
import com.ls.qdaliy.bean.Option;
import com.ls.qdaliy.bean.Post;
import com.ls.qdaliy.bean.Question;
import com.ls.qdaliy.bean.VoteResult;
import com.ls.qdaliy.ui.OptionItem;
import com.ls.qdaliy.ui.ResultVote;
import com.ls.qdaliy.util.AppUtil;
import com.ls.qdaliy.util.JsonParseUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/14.
 * Describe:
 */
public class PaperVoteActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.paper_container)
    ScrollView mPapercontainer;
    @BindView(R.id.background)
    ImageView mBackground;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.option_container)
    LinearLayout mOptionContainer;
    @BindView(R.id.vote)
    TextView mVote;

    private long mId;
    private ResultVote mResultZero;
    private List<String> mSelectedOptions;
    private SelectChangeListener mListener;

    private static final String TAG = "PaperVoteActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_zero);
        ButterKnife.bind(this);

        init();

        Intent intent = getIntent();
        long id = intent.getLongExtra(LaunchActivity.PAPER_ID, 0);
        HttpApi.getPaperOne(id, new PaperZeroCallback());
    }

    public void init() {
        mSelectedOptions = new LinkedList<>();
        mListener = new SelectChangeListener();
        mVote.setOnClickListener(this);
    }

    private void updateUI(final JsonParseUtil json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(PaperVoteActivity.this)
                        .load(json.getData("image", String.class))
                        .into(mBackground);
                Post post = json.getData("post", Post.class);
                mTitle.setText(post.getTitle());
                mDescription.setText(post.getDescription());

                List<Question> questions = json.getList("questions", new TypeToken<ArrayList<Question>>(){}.getType());
                List<Option> options = questions.get(0).getOptions();
                for (Option op : options) {
                    OptionItem item = new OptionItem(PaperVoteActivity.this, null, op);
                    item.setSelectChangeListener(mListener);
                    mOptionContainer.addView(item);
                }

                mId = post.getId();
            }
        });
    }

    private void updateResult(final JsonParseUtil json){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                VoteResult.Option option = json.getData("option", VoteResult.Option.class);
                mResultZero.setResultImage(option.getImageLink());
                mResultZero.setQuestion(option.getTitle());
                List<VoteResult.Attitude> attitudes = json.getList("everyones_attitude", new TypeToken<ArrayList<VoteResult.Attitude>>(){}.getType());
                mResultZero.setResult(attitudes);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(mSelectedOptions.size() != 0) {
            showResult();
        }
    }

    private void showResult() {
        Map<String, String> map = new HashMap<>();
        map.put("options", AppUtil.mergeString(mSelectedOptions));
        map.put("paper_id", String.valueOf(mId));
        mPapercontainer.removeAllViews();
        mResultZero = new ResultVote(this, null);
        mPapercontainer.addView(mResultZero);

        HttpApi.postPaperZeroResult(new PostCallback(), map);
    }

    private class PaperZeroCallback implements HttpCallBack {
        @Override
        public void onFailure(int code) {
            Log.d(TAG, "fail to get paper zero");
        }

        @Override
        public void onResponse(InputStream inputStream) {
            JsonParseUtil jsonParseUtil = new JsonParseUtil(inputStream);
            updateUI(jsonParseUtil);
        }
    }

    private class PostCallback implements HttpCallBack {

        @Override
        public void onFailure(int code) {
            Log.d(TAG, "fail to get paper zero");
        }

        @Override
        public void onResponse(InputStream inputStream) {
            JsonParseUtil jsonParseUtil = new JsonParseUtil(inputStream);
            updateResult(jsonParseUtil);
        }
    }

    private class SelectChangeListener implements OptionItem.OnSelectChangeListener {

        @Override
        public void onSelectChange(boolean select, long id) {
            if (select) {
                mSelectedOptions.add(String.valueOf(id));
            } else {
                mSelectedOptions.remove(String.valueOf(id));
            }

            if (mSelectedOptions.size() != 0) {
                mVote.setSelected(true);
            } else {
                mVote.setSelected(false);
            }
        }
    }
}
