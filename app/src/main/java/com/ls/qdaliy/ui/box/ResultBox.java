package com.ls.qdaliy.ui.box;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.SearchResult;
import com.ls.qdaliy.daliy.DetailActivity;
import com.ls.qdaliy.daliy.LaunchActivity;
import com.ls.qdaliy.daliy.PaperChoiceActivity;
import com.ls.qdaliy.daliy.PaperCommentActivity;
import com.ls.qdaliy.daliy.PaperVoteActivity;
import com.ls.qdaliy.daliy.TotsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ls.qdaliy.ui.box.ArticleBox3.GENRE;

/**
 * Created by asus on 2018/7/21.
 * Describe: 显示搜索结果的 item
 */
public class ResultBox extends LinearLayout implements BaseBox, View.OnClickListener {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.category)
    TextView mCategory;
    @BindView(R.id.author)
    TextView mAuthor;

    private int mGenre;
    private Context mContext;
    private long mId;

    public ResultBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_result_box, this);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
        this.setOrientation(LinearLayout.VERTICAL);

        mContext =context;
        ButterKnife.bind(this);
        this.setOnClickListener(this);
    }

    @Override
    public <T> void onBind(T t, int position) {
        SearchResult result = (SearchResult) t;
        mGenre = result.getPost().getGenre();
        mId = result.getPost().getId();
        mTitle.setText(Html.fromHtml(result.getPost().getTitle()));
        mDescription.setText(Html.fromHtml(result.getPost().getDescription()));
        mCategory.setText(result.getPost().getCategory().getTitle());
        mAuthor.setText(result.getAuthor().getName());
    }

    @Override
    public void onBoxClick(View view) {
        switch (mGenre) {
            case 1000:
                Intent intent = new Intent(mContext, PaperVoteActivity.class);
                intent.putExtra(LaunchActivity.PAPER_ID, mId);
                mContext.startActivity(intent);
                break;
            case 1001:
                Intent intent1 = new Intent(mContext, PaperCommentActivity.class);
                intent1.putExtra(LaunchActivity.PAPER_ID, mId);
                mContext.startActivity(intent1);
                break;
            case 1002:
                Intent intent2 = new Intent(mContext, TotsActivity.class);
                intent2.putExtra(LaunchActivity.PAPER_ID, mId);
                mContext.startActivity(intent2);
                break;
            case 1003:
                Intent intent3 = new Intent(mContext, PaperChoiceActivity.class);
                intent3.putExtra(LaunchActivity.PAPER_ID, mId);
                intent3.putExtra(GENRE, "1003");
                mContext.startActivity(intent3);
                break;
            case 1004:
                Intent intent4 = new Intent(mContext, PaperChoiceActivity.class);
                intent4.putExtra(LaunchActivity.PAPER_ID, mId);
                intent4.putExtra(GENRE, "1004");
                mContext.startActivity(intent4);
                break;
            default:
                Intent intent5 = new Intent(mContext, DetailActivity.class);
                intent5.putExtra(DetailActivity.ARTICLE_ID, mId);
                mContext.startActivity(intent5);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        this.onBoxClick(view);
    }
}
