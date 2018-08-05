package com.ls.qdaliy.daliy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ls.qdaliy.R;
import com.ls.qdaliy.api.HttpApi;
import com.ls.qdaliy.api.HttpCallBack;
import com.ls.qdaliy.bean.ChoicePostOption;
import com.ls.qdaliy.bean.ChoiceResult;
import com.ls.qdaliy.bean.NormalQuestion;
import com.ls.qdaliy.bean.Post;
import com.ls.qdaliy.ui.box.ArticleBox3;
import com.ls.qdaliy.ui.ChoiceSelect;
import com.ls.qdaliy.ui.ResultChoice;
import com.ls.qdaliy.util.JsonParseUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/17.
 * Describe: 选择界面, 包括 1003 和 1004
 */
public class PaperChoiceActivity extends AppCompatActivity {

    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.option_container)
    ChoiceSelect mOptionContainer;
    @BindView(R.id.paper_container)
    LinearLayout mPaperContainer;

    private List<ChoicePostOption> mPostOptions;
    private List<NormalQuestion> mQuestions;
    private Post mPost;
    private int mQuestionNum;
    private ResultChoice mResult;
    private String mGenre;

    private static final String TAG = "PaperChoiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_three);
        ButterKnife.bind(this);

        mPostOptions = new ArrayList<>();
        mQuestionNum = 0;


        mOptionContainer.setOnOptionClickListener(new ChoiceSelect.OnOptionClickListener() {
            @Override
            public void onOptionClick(View view, long selectId, int score) {
                ChoicePostOption postOption = new ChoicePostOption(mQuestions.get(mQuestionNum).getId(), selectId, score);
                mPostOptions.add(postOption);

                updateUI();

                if (mQuestionNum == mQuestions.size()) {
                    postResult();
                }
            }
        });

        Intent intent = getIntent();
        long id = intent.getLongExtra(LaunchActivity.PAPER_ID, 0);
        mGenre = intent.getStringExtra(ArticleBox3.GENRE);
        mOptionContainer.setIsChoice(mGenre.equals("1003"));

        HttpApi.getChoice(mGenre, id, new GetOptionCallback());
    }

    // FIXME: 2018/6/20 点击返回退出之后, 再进来会报错
    private void updateUI() {
        NormalQuestion question = mQuestions.get(mQuestionNum);
        mContent.setText(question.getContent());
        if(!(question.getImageLink() == null || question.getImageLink().equals(""))){
            mImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(question.getImageLink()).into(mImage);
        }
        mOptionContainer.setOptions(question.getOptions());

        ++mQuestionNum;
    }

    private void postResult() {
        Map<String, String> map = new HashMap<>();
        map.put("options", new Gson().toJson(mPostOptions));
        map.put("id", String.valueOf(mPost.getId()));
        mPaperContainer.removeAllViews();
        mResult = new ResultChoice(this, null);
        mPaperContainer.addView(mResult);

        HttpApi.postChoice(mGenre, new PostCallBack(), map);
    }

    private void showResult(final JsonParseUtil json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mResult.setResult(mPost, json.getData("result", ChoiceResult.class));
            }
        });
    }

    private class GetOptionCallback implements HttpCallBack {

        @Override
        public void onFailure(int code) {
            Log.d(TAG, "fail to get option!");
        }

        @Override
        public void onResponse(InputStream inputStream) {
            JsonParseUtil json = new JsonParseUtil(inputStream);
            start(json);
        }
    }

    private class PostCallBack implements HttpCallBack {

        @Override
        public void onFailure(int code) {
            Log.d(TAG, "fail to mPost result!");
        }

        @Override
        public void onResponse(InputStream inputStream) {
            JsonParseUtil json = new JsonParseUtil(inputStream);
            showResult(json);
        }
    }

    private void start(JsonParseUtil json) {
        mPost = json.getData("paper", Post.class);
        mQuestions = json.getList("normal_questions", new TypeToken<ArrayList<NormalQuestion>>() {
        }.getType());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateUI();
            }
        });
    }
}
