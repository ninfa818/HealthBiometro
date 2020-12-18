package org.lab.biometro.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.lab.biometro.R;
import org.lab.biometro.fragment.AgreementFragment;
import org.lab.biometro.fragment.CompleteFragment;
import org.lab.biometro.fragment.InformationFragment;
import org.lab.biometro.fragment.VerifyFragment;

public class RegisterActivity extends AppCompatActivity {

    public String registedEmail = "";
    public String registedName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initToolBar();
        initView();
    }

    private void initToolBar() {
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void initView() {
        loadFragmentByIndex(0);
    }

    public void loadFragmentByIndex(int index) {
        Fragment frg = null;
        switch (index) {
            case 0:
                frg = new AgreementFragment(this);
                break;
            case 1:
                frg = new VerifyFragment(this);
                break;
            case 2:
                frg = new InformationFragment(this);
                break;
            case 3:
                frg = new CompleteFragment(this);
                break;
        }
        onLoadFragment(frg);
    }

    private void onLoadFragment(Fragment frg) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction frgTran = fm.beginTransaction();
        frgTran.replace(R.id.frg_body, frg);
        frgTran.commit();
    }

}