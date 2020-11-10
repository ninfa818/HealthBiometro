package org.lab.biometro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.lab.biometro.R;
import org.lab.biometro.activity.ConfirmActivity;
import org.lab.biometro.activity.RegisterActivity;
import org.lab.biometro.util.AppUtil;

public class InformationFragment extends Fragment {

    private final RegisterActivity activity;


    public InformationFragment(RegisterActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_information, container, false);
        initView(fragment);
        return fragment;
    }

    private void initView(View fragment) {
        TextView lbl_verify = fragment.findViewById(R.id.lbl_verify);
        lbl_verify.setOnClickListener(view -> AppUtil.showOtherActivity(activity, ConfirmActivity.class, 0));
        Button btn_enter = fragment.findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(view -> activity.loadFragmentByIndex(3));
    }

}
