package com.ls.qdaliy.daliy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ls.qdaliy.R;
import com.ls.qdaliy.api.HttpApi;
import com.ls.qdaliy.api.HttpCallBack;
import com.ls.qdaliy.bean.ChoicePostOption;
import com.ls.qdaliy.bean.GenderQuestion;
import com.ls.qdaliy.bean.Paper;
import com.ls.qdaliy.bean.Post;
import com.ls.qdaliy.bean.SlideQuestion;
import com.ls.qdaliy.bean.TotsQuestion;
import com.ls.qdaliy.ui.RadioGroup;
import com.ls.qdaliy.ui.ResultTots;
import com.ls.qdaliy.ui.Slide;
import com.ls.qdaliy.ui.TotsChoice;
import com.ls.qdaliy.util.JsonParseUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/26.
 * Describe:
 */
public class TotsActivity extends AppCompatActivity {

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.slide_bar)
    Slide mSlideBar;
    @BindView(R.id.container)
    ScrollView mContainer;
    @BindView(R.id.next)
    TextView mNext;
    @BindView(R.id.head_image)
    ImageView mHeadImage;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description)
    TextView mDescription;

    private long mId;
    private int mQuestionNum = 0;
    private ChoicePostOption mGenreOption;
    private ChoicePostOption mSlideOption;
    private List<TotsQuestion> mTotsQuestions;
    private List<ChoicePostOption> mPostOptions;
    private String mImageLink;
    private static final String TAG = "TotsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tots);
        ButterKnife.bind(this);

        mGenreOption = new ChoicePostOption(0, 0, -1);
        mSlideOption = new ChoicePostOption(-1, 0, 0);
        mPostOptions = new ArrayList<>();

        mRadioGroup.setOnRadioCheckListener(new RadioGroup.OnRadioCheckListener() {
            @Override
            public void onRadioCheck(View view, long id, long questionId) {
                mGenreOption.setId(id);
                mGenreOption.setQuestionId(questionId);
            }
        });

        mSlideBar.setSlideListener(new Slide.OnSeeKBarSlideListener() {
            @Override
            public void onSeekBarSlide(long questionId, int progress) {
                mSlideOption.setQuestionId(questionId);
                mSlideOption.setScore(progress/10);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPostOptions.add(mGenreOption);
                mPostOptions.add(mSlideOption);
                showTots();
            }
        });

        Intent intent = getIntent();
        long id = intent.getLongExtra(LaunchActivity.PAPER_ID, 0);
        start(id);
    }

    private void start(long id) {
        HttpApi.getTots(id, new HttpCallBack() {
            @Override
            public void onFailure(int code) {
                Log.d(TAG, "fail to get tots!");
            }

            @Override
            public void onResponse(InputStream inputStream) {
                JsonParseUtil json = new JsonParseUtil(inputStream);
                totsPrepare(json);

                TotsActivity.this.mId = json.getData("paper", Post.class).getId();
            }
        });
    }

    private void totsPrepare(final JsonParseUtil json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Paper paper = json.getData("paper", Paper.class);
                Glide.with(TotsActivity.this).load(paper.getImageLink()).into(mHeadImage);
                mTitle.setText(paper.getTitle());
                mDescription.setText(paper.getDescription());
                mImageLink = paper.getImageLink();

                GenderQuestion genderQuestion = json.getData("gender_question", GenderQuestion.class);
                mRadioGroup.setRadio(genderQuestion);

//                初始化 mGenreOption 的默认值
                mGenreOption.setId(genderQuestion.getId());
                mGenreOption.setQuestionId(genderQuestion.getOptions().get(0).getId());

                SlideQuestion slideQuestion = json.getData("slide_question", SlideQuestion.class);
                mSlideBar.setSlideQuestion(slideQuestion);

//                初始化 mSlideOption 的默认值
                mSlideOption.setQuestionId(slideQuestion.getId());
                mSlideOption.setScore(5);

                mTotsQuestions = json.getList("normal_questions", new TypeToken<ArrayList<TotsQuestion>>() {
                }.getType());
            }
        });
    }

    private void showTots() {
        mContainer.removeAllViews();
        final TotsChoice totsChoice = new TotsChoice(this, null);
        mContainer.addView(totsChoice);
        totsChoice.showHeader(mImageLink);
        totsChoice.showTots(mTotsQuestions.get(mQuestionNum));

        totsChoice.setChooseListener(new TotsChoice.OnOptionChooseListener() {
            @Override
            public void onOptionChoose(long id, long questionId) {
                ChoicePostOption option = new ChoicePostOption(id, questionId, -1);
                mPostOptions.add(option);

                ++mQuestionNum;
                if (mQuestionNum == mTotsQuestions.size()) {
                    getResult();
                    totsChoice.removeChooseListener();
                } else {
                    totsChoice.showTots(mTotsQuestions.get(mQuestionNum));
                }
            }
        });
    }

    private void getResult() {
        Map<String, String> map = new HashMap<>();
        map.put("options", new Gson().toJson(mPostOptions));
        map.put("mId", String.valueOf(this.mId));

        HttpApi.postTots(map, new HttpCallBack() {
            @Override
            public void onFailure(int code) {
                Log.d(TAG, "fail to post tots");
            }

            @Override
            public void onResponse(InputStream inputStream) {
                JsonParseUtil json = new JsonParseUtil(inputStream);
                showResult(json);
                Log.d(TAG, "post tots!");
            }
        });
    }

    private void showResult(final JsonParseUtil json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ResultTots resultTots = new ResultTots(TotsActivity.this, null);
                mContainer.removeAllViews();
                mContainer.addView(resultTots);
                resultTots.setContent(json);
            }
        });
    }
}
