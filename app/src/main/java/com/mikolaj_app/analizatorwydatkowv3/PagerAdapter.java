package com.mikolaj_app.analizatorwydatkowv3;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends AppCompatActivity {

    public static List<Fragment> mFragments = new ArrayList<>();
    public ViewPager mViewPager;
    public boolean mInstallFrag = true;
    private TabLayout mTabLayout;
    private Toolbar toolbar;
    private String tabTitles[] = new String[]{"Wydatki", "Status", "Statystyki"};

    public final String EXTRA_KEY_INSTALL_FRAG = "true or false for install frag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_adapter);

        if(savedInstanceState != null){
            mInstallFrag = savedInstanceState.getBoolean(EXTRA_KEY_INSTALL_FRAG);
        }

        if(mInstallFrag){
            installFragments();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager manager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(manager) {

            @Override
            public Fragment getItem(int position) {
                Log.d("addFragment","dodany fragment"+position);
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }

        });

        mViewPager.setCurrentItem(1);

        mTabLayout.setupWithViewPager(mViewPager);


    }//koniec głównej metody


    public void installFragments(){
        mFragments.add(new ExpensesListFragment());
        mFragments.add(new MainFragment());
        mFragments.add(new StatisticsFragment());
        mInstallFrag = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_KEY_INSTALL_FRAG, mInstallFrag);
    }
}
