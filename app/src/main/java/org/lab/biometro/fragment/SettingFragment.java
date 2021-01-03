package org.lab.biometro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.lab.biometro.R;
import org.lab.biometro.activity.MainActivity;
import org.lab.biometro.activity.ProductActivity;
import org.lab.biometro.activity.ProfileActivity;
import org.lab.biometro.activity.SettingNotiActivity;
import org.lab.biometro.model.UserModel;
import org.lab.biometro.util.AppUtil;

public class SettingFragment extends Fragment {

    private final MainActivity activity;
    private LinearLayout llt_profile, llt_product, llt_notification;
    private TextView lbl_name;

    private final UserModel currentUser;

    public SettingFragment(MainActivity activity, UserModel userModel) {
        this.activity = activity;
        currentUser = userModel;
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

        lbl_name = fragment.findViewById(R.id.lbl_name);
        initData();
    }

    private void initData() {
        lbl_name.setText(currentUser.memberName);
    }

}
