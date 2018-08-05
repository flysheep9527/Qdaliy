package com.ls.qdaliy.daliy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.Feed;
import com.ls.qdaliy.bean.NewsCategory;
import com.ls.qdaliy.ui.Adapter.AdapterWrapper;
import com.ls.qdaliy.ui.Adapter.BaseAdapter;
import com.ls.qdaliy.ui.Adapter.HomeAdapter;
import com.ls.qdaliy.ui.LaunchAdapter;
import com.ls.qdaliy.ui.SideMenu;
import com.ls.qdaliy.ui.box.ArticleBox3;
import com.ls.qdaliy.ui.box.ErrorPage;
import com.ls.qdaliy.ui.box.FooterBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends AppCompatActivity implements LaunchContract.View {

    @BindView(R.id.tab_nav)
    TabLayout mTabLayout;
    @BindView(R.id.vp_launch)
    ViewPager mViewPager;
    RecyclerView mHomeRV;
    SwipeRefreshLayout mHomeFreshLayout;
    RecyclerView mLabRV;
    SwipeRefreshLayout mLabFreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    /*@BindView(R.id.banner)
    BannerView mBannerView;*/

    private AdapterWrapper homeAdapter;
    private AdapterWrapper labAdapter;
    private LaunchContract.Presenter mPresenter;
    private SideMenu sideMenu;
    private List<Object> mFooter;

    public static final int HOME = 1001;
    public static final int LAB = 1002;
    public static final String PAPER_ID = "paper_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        init();
        mPresenter = new LaunchPresenter(this);
        mPresenter.start();
    }

    @SuppressLint("InflateParams")
    private void init() {
        mFooter = new ArrayList<>(1);
        mFooter.add(getResources().getString(R.string.loading));

        LayoutInflater inflater = LayoutInflater.from(this);
        ArrayList<View> viewsList = new ArrayList<>(3);

        View home = inflater.inflate(R.layout.layout_home, null);
//        mBannerView = home_select.findViewById(R.id.banner);
        mHomeRV = home.findViewById(R.id.rv_home);
        mHomeFreshLayout = home.findViewById(R.id.rv_flush);
        viewsList.add(home);

        View lab = inflater.inflate(R.layout.layout_news, null);
        mLabRV = lab.findViewById(R.id.rv_news);
        mLabFreshLayout = lab.findViewById(R.id.rv_flush);
        viewsList.add(lab);

        LaunchAdapter adapter = new LaunchAdapter(viewsList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        ImageView image1 = new ImageView(this);
        image1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image1.setImageResource(R.drawable.home);
        Objects.requireNonNull(mTabLayout.getTabAt(0)).setCustomView(image1);

        ImageView image2 = new ImageView(this);
        image2.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image2.setImageResource(R.drawable.lab);
        Objects.requireNonNull(mTabLayout.getTabAt(1)).setCustomView(image2);

        sideMenu = new SideMenu(this);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideMenu.show();
            }
        });
    }

    @Override
    public void initHomeList(List<Feed> feeds, List<Feed> banners) {
        HomeAdapter<Feed> adapter = new HomeAdapter<>(this, feeds);
        ErrorPage errorPage = new ErrorPage(this, null);
        errorPage.setTryAgainClickListener(new ErrorPage.OnTryAgainClickListener() {
            @Override
            public void onTryAgainClick(View view) {
                mPresenter.getHomeList("0");
            }
        });

        homeAdapter = new AdapterWrapper(adapter);
        homeAdapter.addFootView(FooterBox.class);
        homeAdapter.setFooterData(mFooter);
        homeAdapter.setErrorPage(errorPage);

        mHomeRV.setLayoutManager(new LinearLayoutManager(this));
        mHomeRV.setAdapter(homeAdapter);
        mHomeRV.addOnScrollListener(mPresenter.getMoreListListener(3, HOME));

        mHomeFreshLayout.setProgressViewOffset(true, 50, 200);
        mHomeFreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mHomeFreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mHomeFreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHomeList("0");
            }
        });
    }

    @Override
    public void initLabList(List<Feed> labs) {
//        labAdapter = new HomeAdapter<>(this, labs);
        BaseAdapter<Feed> adapter = new BaseAdapter<>(this, labs, ArticleBox3.class);
        ErrorPage errorPage = new ErrorPage(this, null);
        errorPage.setTryAgainClickListener(new ErrorPage.OnTryAgainClickListener() {
            @Override
            public void onTryAgainClick(View view) {
                mPresenter.getLabList("0");
            }
        });

        labAdapter = new AdapterWrapper(adapter);
        labAdapter.addFootView(FooterBox.class);
        labAdapter.setFooterData(mFooter);
        labAdapter.setErrorPage(errorPage);

        mLabRV.setLayoutManager(new LinearLayoutManager(this));
        mLabRV.setAdapter(labAdapter);
        mLabRV.addOnScrollListener(mPresenter.getMoreListListener(5, LAB));

        mLabFreshLayout.setProgressViewOffset(true, 50, 200);
        mLabFreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mLabFreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mLabFreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getLabList("0");
            }
        });
    }

    @Override
    public void updateHomeList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeAdapter.notifyDataSetChanged();
                mHomeFreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void updateLabList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                labAdapter.notifyDataSetChanged();
                mLabFreshLayout.setRefreshing(false);
            }
        });
    }

    public void showHomeError(final boolean isError) {
        homeAdapter.showErrorPage(isError);
    }

    public void showLabError(final boolean isError) {
        labAdapter.showErrorPage(isError);
    }

    public void initSideMenu(final List<NewsCategory> newsCategories) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sideMenu.setNewsCategoryList(newsCategories);
            }
        });
    }

    public void showFAB() {
        mFab.show();
    }

    public void hideFAB() {
        mFab.hide();
    }
}
