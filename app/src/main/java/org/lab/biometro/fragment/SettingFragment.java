package org.lab.biometro.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.lab.biometro.R;
import org.lab.biometro.activity.MainActivity;
import org.lab.biometro.activity.ProductActivity;
import org.lab.biometro.activity.ProfileActivity;
import org.lab.biometro.activity.RegisterActivity;
import org.lab.biometro.activity.SettingNotiActivity;
import org.lab.biometro.util.AppUtil;

public class SettingFragment extends Fragment {

    private final MainActivity activity;
    private LinearLayout llt_profile, llt_product, llt_notification;


    public SettingFragment(MainActivity activity) {
        this.activity = activity;
    }

    private void initEvent() {
        llt_profile.setOnClickListener(view -> AppUtil.showOtherActivity(activity, ProfileActivity.class, 0));
        llt_product.setOnClickListener(view -> AppUtil.showOtherActivity(activity, ProductActivity.class, 0));
        llt_notification.setOnClickListener(view -> AppUtil.showOtherActivity(activity, SettingNotiActivity.class, 0));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(fragment);
        initEvent();
        return fragment;
    }

    private void initView(View fragment) {
        llt_profile = fragment.findViewById(R.id.llt_profile);
        llt_product = fragment.findViewById(R.id.llt_product);
        llt_notification = fragment.findViewById(R.id.llt_notification);
    }

}
