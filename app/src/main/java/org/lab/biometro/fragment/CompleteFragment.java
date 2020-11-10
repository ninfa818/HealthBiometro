package org.lab.biometro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.lab.biometro.R;
import org.lab.biometro.activity.RegisterActivity;

public class CompleteFragment extends Fragment {

    private final RegisterActivity activity;

    private Button btn_login;

    public CompleteFragment(RegisterActivity activity) {
        this.activity = activity;
    }

    private void initEvent() {
        btn_login.setOnClickListener(view -> activity.onBackPressed());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_complete, container, false);
        initView(fragment);
        initEvent();
        return fragment;
    }

    private void initView(View fragment) {
        btn_login = fragment.findViewById(R.id.btn_login);
    }

}
