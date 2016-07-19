package com.gm.jianmerchant;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gm.jianmerchant.base.BaseActivity;
import com.gm.jianmerchant.bean.Subject;
import com.gm.jianmerchant.entity.TabEntity;
import com.gm.jianmerchant.fragment.VPSimpleFragment;
import com.gm.jianmerchant.http.ProgressSubscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private int fragment_count = 3;
    private List<String> type = Arrays.asList("g","a","o");
    private ArrayList<VPSimpleFragment> fragmentList = new ArrayList<>();
    private ProgressSubscriber<List<Subject>> progressSubscriber;
    private ViewPager viewPager;
    private CommonTabLayout tabHost;
    private ArrayList<CustomTabEntity> tabList = new ArrayList<>();
    private String[] titles = new String[]{"我","很","帅"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        for (int i = 0; i < fragment_count; i++) {
            fragmentList.add(VPSimpleFragment.newInstance(type.get(i)));
        }
        tabHost = (CommonTabLayout) findViewById(R.id.tabHost);
        for (int i = 0; i < fragment_count; i++) {
            tabList.add(new TabEntity(titles[i], 0, 0));
        }
        tabHost.setTabData(tabList);
        tabHost.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                //再次点击刷新，相当于
                fragmentList.get(position).onUserVisible();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
                if (position == 2) {
                    tabHost.hideMsg(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chakan:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "查看", Toast.LENGTH_SHORT).show();
                        //Toast持有主线成的handler所以这里构建消息循环体系可以 展示toast
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.bukan:
                Toast.makeText(MainActivity.this, "不看", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
