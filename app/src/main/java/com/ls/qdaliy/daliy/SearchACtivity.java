package com.ls.qdaliy.daliy;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ls.qdaliy.R;
import com.ls.qdaliy.ui.ResultFragment;
import com.ls.qdaliy.ui.SearchFragment;
import com.ls.qdaliy.util.SearchRecordUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/7/20.
 * Describe:
 */
public class SearchACtivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.search_input)
    EditText mSearchInput;
    @BindView(R.id.search)
    TextView mSearch;
    SearchFragment mSearchFragment;
    ResultFragment mResultFragment;
    @BindView(R.id.close)
    ImageView mClose;

    private boolean mIsInput = false;
    private SearchRecordUtil mRecordUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mSearchFragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.search_fragment);
        mResultFragment = (ResultFragment) getFragmentManager().findFragmentById(R.id.result_fragment);

        mRecordUtil = new SearchRecordUtil();
        mClose.setOnClickListener(this);

        hideResult();
        mSearch.setOnClickListener(this);

        mSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                if (sequence.length() > 0) {
                    mIsInput = true;
                    mSearch.setText(getResources().getString(R.string.search));
                    mClose.setVisibility(View.VISIBLE);
                    /*mResultFragment.sendSearch(sequence.toString());
                    showResult();*/
                } else {
                    mIsInput = false;
                    mSearch.setText(getResources().getString(R.string.search_cancel));
                    mClose.setVisibility(View.GONE);
                    hideResult();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSearchFragment.setItemClickListener(new SearchFragment.OnItemClickListener() {
            @Override
            public void onItemClick(String record) {
                mSearchInput.setText(record);
                search(record);
            }
        });
    }

    private void showResult() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(mSearchFragment);
        transaction.show(mResultFragment);
        transaction.commit();
    }

    private void hideResult() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.show(mSearchFragment);
        transaction.hide(mResultFragment);
        transaction.commit();
    }

    public void search(String searchKey) {
        mResultFragment.sendSearch(searchKey);
        showResult();
        mRecordUtil.addRecord(searchKey);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                if (mIsInput) {
                    search(mSearchInput.getText().toString());
                } else {
                    finish();
                }
                break;
            case R.id.close:
                mSearchInput.getText().clear();
                break;
        }
    }
}