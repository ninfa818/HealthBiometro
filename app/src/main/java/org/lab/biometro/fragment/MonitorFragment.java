package org.lab.biometro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.lab.biometro.R;
import org.lab.biometro.activity.MainActivity;
import org.lab.biometro.util.AppUtil;

public class MonitorFragment extends Fragment {

    private final MainActivity activity;

    private TabLayout tabLayout;
    private ViewPager viewPager;


    public MonitorFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_monitor, container, false);
        initView(fragment);
        return fragment;
    }

    private void initView(View fragment) {
        tabLayout = fragment.findViewById(R.id.tab_manager);
        viewPager = fragment.findViewById(R.id.vpr_manager);
        viewPager.setAdapter(new ManagerAdapter(getChildFragmentManager()));
        tabLayout.post(() -> tabLayout.setupWithViewPager(viewPager));
        viewPager.setCurrentItem(AppUtil.pageIndex);
    }

    class ManagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"맥박", "산소포화도", "체온"};

        public ManagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HeartFragment(activity);
                case 1:
                    return new OxygenFragment(activity);
                case 2:
                    return new TempFragment(activity);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }

}
