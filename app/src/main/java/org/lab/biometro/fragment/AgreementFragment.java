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
import org.lab.biometro.activity.PrivacyActivity;
import org.lab.biometro.activity.RegisterActivity;
import org.lab.biometro.util.AppUtil;

public class AgreementFragment extends Fragment {

    private final RegisterActivity activity;

    private TextView lbl_agreement;
    private Button btn_start;


    public AgreementFragment(RegisterActivity activity) {
        this.activity = activity;
    }

    private void initEvent() {
        lbl_agreement.setOnClickListener(view -> AppUtil.showOtherActivity(activity, PrivacyActivity.class, 0));
        btn_start.setOnClickListener(view -> activity.loadFragmentByIndex(1));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_agreement, container, false);
        initView(fragment);
        initEvent();
        return fragment;
    }

    private void initView(View fragment) {
        lbl_agreement = fragment.findViewById(R.id.lbl_agreement);
        btn_start = fragment.findViewById(R.id.btn_start);
    }

}
