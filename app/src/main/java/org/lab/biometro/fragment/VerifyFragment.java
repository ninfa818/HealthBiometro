package org.lab.biometro.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.lab.biometro.R;
import org.lab.biometro.activity.RegisterActivity;

public class VerifyFragment extends Fragment {

    private final RegisterActivity activity;

    public VerifyFragment(RegisterActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_verify, container, false);
        initView();
        return fragment;
    }

    private void initView() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> activity.loadFragmentByIndex(2), 1500);
    }

}
