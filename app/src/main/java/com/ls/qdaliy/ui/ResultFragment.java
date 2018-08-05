package com.ls.qdaliy.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ls.qdaliy.R;
import com.ls.qdaliy.api.HttpApi;
import com.ls.qdaliy.api.HttpCallBack;
import com.ls.qdaliy.bean.SearchResult;
import com.ls.qdaliy.ui.Adapter.BaseAdapter;
import com.ls.qdaliy.ui.box.ResultBox;
import com.ls.qdaliy.util.JsonParseUtil;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by asus on 2018/7/21.
 * Describe: 显示搜索结果
 */
public class ResultFragment extends Fragment {

    @BindView(R.id.rv_result)
    RecyclerView mRvResult;
    Unbinder unbinder;
    @BindView(R.id.result_num)
    TextView mResultNum;

    private BaseAdapter<SearchResult> mResultAdapter;
    private List<SearchResult> mSearchResults;
    private String mLastKey;
    private boolean mHasMore;
    private String mSearchInput;
    private String mResults;

    private boolean mIsFirstPage;

    private static final String TAG = "ResultFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        unbinder = ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        mSearchResults = new LinkedList<>();
        mResultAdapter = new BaseAdapter<>(getActivity(), mSearchResults, ResultBox.class);
        mRvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvResult.setAdapter(mResultAdapter);
        mRvResult.addOnScrollListener(getMoreListListener());
        mRvResult.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
    }

    public void sendSearch(String searchInput) {
        this.mSearchInput = searchInput;
        getSearChResult(mSearchInput, "0");
        mIsFirstPage = true;
    }

    private void getSearChResult(String input, final String lastKey) {
        HttpApi.getSearchResult(input, lastKey, new HttpCallBack() {
            @Override
            public void onFailure(int code) {
                Log.e(TAG, "fail to get mSearch result!");
            }

            @Override
            public void onResponse(InputStream inputStream) {
                JsonParseUtil json = new JsonParseUtil(inputStream);
                mLastKey = json.getData("last_key", String.class);
                mHasMore = json.getData("has_more", Boolean.class);
                mResults = json.getData("total_number", String.class);
                mSearchResults.addAll(json.<SearchResult>getList("searches", new TypeToken<LinkedList<SearchResult>>() {
                }.getType()));

                if(!lastKey.equals("0")){
                    mIsFirstPage = false;
                }
                updateResult();
            }
        });
    }

    private void updateResult() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mResultAdapter.notifyDataSetChanged();
                if(mIsFirstPage){
                    mResultNum.setText("搜索到 ".concat(mResults).concat(" 条结果!"));
                }
//                Log.d(TAG, "update result");
            }
        });
    }

    private RecyclerView.OnScrollListener getMoreListListener() {
        return new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 5 >= recyclerView.getAdapter().getItemCount()) {
                    if (mHasMore) {
                        getSearChResult(mSearchInput, mLastKey);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
