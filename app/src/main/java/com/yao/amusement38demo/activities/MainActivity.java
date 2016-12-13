package com.yao.amusement38demo.activities;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.yao.amusement38demo.R;
import com.yao.amusement38demo.adapter.ItemSisterAdapter;
import com.yao.amusement38demo.bean.SisterContentList;
import com.yao.amusement38demo.content.Constant;
import com.yao.amusement38demo.inter.ILoadInfoAProgressView;
import com.yao.amusement38demo.util.MediaManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ILoadInfoAProgressView<SisterContentList>, AbsListView.OnScrollListener {

    private ListView entertainmentList;
    private SisterContentList enterList = null;
    private ItemSisterAdapter adapter;
    private int mCurrentPage = 1;
    private String title = "";
    private String type = "";
    MainActivityPresenter presenter;
    LoadPreviewImageService myService;
    protected int ITEM_LEFT_TO_LOAD_MORE = 20;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;
    private boolean isLoadingMore = false;
    private int visibleLastIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        entertainmentList = (ListView) findViewById(R.id.entertainment_list);
        adapter = new ItemSisterAdapter(this);
        entertainmentList.setAdapter(adapter);
        entertainmentList.setOnScrollListener(this);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                isLoadingMore = false;
            }
        });


        presenter = new MainActivityPresenter(this);
        presenter.loadInfo(type, title, mCurrentPage);
        //presenter.bindService(this, connection);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LoadPreviewImageService.MyServiceBinder myServiceBind = (LoadPreviewImageService.MyServiceBinder) service;
            myService = (LoadPreviewImageService) myServiceBind.getService();
            adapter.setService(myService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all) {
            // Handle the camera action
            type = Constant.SISTER_DATA_TYPE_ALL;
        } else if (id == R.id.nav_gallery) {
            type = Constant.SISTER_DATA_TYPE_IMAGE;
        } else if (id == R.id.nav_video) {
            type = Constant.SISTER_DATA_TYPE_VIDEO;
        } else if (id == R.id.nav_text) {
            type = Constant.SISTER_DATA_TYPE_TEXT;
        } else if (id == R.id.nav_vioce) {
            type = Constant.SISTER_DATA_TYPE_VOICE;
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        mCurrentPage = 1;
        presenter.setShowProgress(true);
        presenter.loadInfo(title, type, mCurrentPage);//从新加载数据

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void setData(SisterContentList data) {
        if (data != null && data.showapi_res_body.pagebean.contentlist.size() > 0) {
            if (mCurrentPage == 1) {
                adapter.refresh(data);
            } else if (mCurrentPage > 1) {
                adapter.addData(data);
            }

        }
    }

    @Override
    public void showError(int erroCode, String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBlueProgress() {
        findViewById(R.id.progress_wheel).setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissBlueProgress() {
        findViewById(R.id.progress_wheel).setVisibility(View.GONE);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int lastIndex = adapter.getCount() - 1;//数据集最后一项的索引
        Log.i(this.getClass().getName(), "lastIndex:" + lastIndex);
        Log.i(this.getClass().getName(), "visibleLastIndex:" + visibleLastIndex);
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && visibleLastIndex == lastIndex && !isLoadingMore) {
            // 如果是自动加载,可以在这里放置异步加载数据的代码
            isLoadingMore = true;
            Log.w(this.getClass().getName(), "next page");
            presenter.setShowProgress(false);
            presenter.loadInfo(title, type, ++mCurrentPage);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
        //Log.i(this.getClass().getName(), "firstVisibleItem:" + firstVisibleItem + " visibleItemCount:" + visibleItemCount + "totalItemCount:" + totalItemCount);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }
}
