package com.ls.qdaliy.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ls.qdaliy.R;
import com.ls.qdaliy.util.SearchRecordUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by asus on 2018/7/21.
 * Describe: 搜索界面
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.record_clear)
    ImageView mRecordClear;
    @BindView(R.id.record_container)
    ListView mRecordContainer;
    Unbinder unbinder;
    @BindView(R.id.search_record)
    LinearLayout mSearchRecord;

    private SearchRecordUtil mRecordUtil;
    private OnItemClickListener mItemClickListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecordUtil = new SearchRecordUtil();
        showRecord();
        return view;
    }

    private void showRecord() {
        final List<String> records = mRecordUtil.getRecord();
        if (records.size() > 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_search_record, records);
            mRecordContainer.setAdapter(arrayAdapter);
            mRecordContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
                    mItemClickListener.onItemClick(records.get(i));
                }
            });
        } else {
            mSearchRecord.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            showRecord();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecordClear.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        mRecordUtil.clearRecord();
        showRecord();
        Log.d("fragment", "clear click!");
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String record);
    }
}
