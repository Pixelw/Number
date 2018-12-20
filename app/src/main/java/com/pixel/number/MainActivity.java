package com.pixel.number;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<Fragment> fgLists = new ArrayList<>(3);
    private Fragment f1 = new Conv();
    private Fragment f2 = new Calc();
    private Fragment f3 = new Something();
    private ActionBar bar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_conv:
                    mViewPager.setCurrentItem(0);
                    bar.setTitle(R.string.title_conv);
                    return true;
                case R.id.navigation_calc:
                    mViewPager.setCurrentItem(1);
                    bar.setTitle(R.string.title_calc);
                    return true;
                case R.id.navigation_new:
                    mViewPager.setCurrentItem(2);
                    bar.setTitle(R.string.title_new);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fgLists.add(f1);
        fgLists.add(f2);
        fgLists.add(f3);
        bar = getSupportActionBar();
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPager = findViewById(R.id.pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                switch (i) {
                    case 0:
                        bar.setTitle(R.string.title_conv);
                        break;
                    case 1:
                        bar.setTitle(R.string.title_calc);
                        break;
                    case 2:
                        bar.setTitle(R.string.title_new);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageSelected(int i) {
                navigation.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fgLists.get(i);
            }

            @Override
            public int getCount() {
                return fgLists.size();
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
    }

}
