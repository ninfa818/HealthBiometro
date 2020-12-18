package org.lab.biometro.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;
import org.lab.biometro.R;
import org.lab.biometro.activity.ConfirmActivity;
import org.lab.biometro.activity.RegisterActivity;
import org.lab.biometro.listener.OnHttpListener;
import org.lab.biometro.model.UserModel;
import org.lab.biometro.util.AppUtil;

import java.util.Objects;

public class InformationFragment extends Fragment implements View.OnClickListener {

    private final RegisterActivity activity;
    private EditText txt_name, txt_phone, txt_email, txt_password, txt_repass, txt_pro_name, txt_pro_phone;
    private View content;
    private ProgressDialog dialog;


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
        content = fragment.findViewById(R.id.content);
        dialog = ProgressDialog.show(activity, "", getString(R.string.alt_connect_server));
        dialog.dismiss();

        TextView lbl_verify = fragment.findViewById(R.id.lbl_verify);
        lbl_verify.setOnClickListener(this);
        Button btn_enter = fragment.findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);

        txt_name = fragment.findViewById(R.id.txt_name);
        txt_phone = fragment.findViewById(R.id.txt_phone);
        txt_email = fragment.findViewById(R.id.txt_email);
        txt_password = fragment.findViewById(R.id.txt_password);
        txt_repass = fragment.findViewById(R.id.txt_repass);
        txt_pro_name = fragment.findViewById(R.id.txt_pro_name);
        txt_pro_phone = fragment.findViewById(R.id.txt_pro_phone);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lbl_verify:
                AppUtil.showOtherActivity(activity, ConfirmActivity.class, 0);
                break;
            case R.id.btn_enter:
                String name = txt_name.getText().toString();
                if (name.isEmpty()) {
                    Snackbar.make(content, R.string.no_name, BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                String phone = txt_phone.getText().toString();
                if (phone.isEmpty()) {
                    Snackbar.make(content, R.string.no_phone, BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                String email = txt_email.getText().toString();
                if (email.isEmpty()) {
                    Snackbar.make(content, R.string.no_email, BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                String pass = txt_password.getText().toString();
                if (pass.isEmpty()) {
                    Snackbar.make(content, R.string.no_pass, BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                String repass = txt_repass.getText().toString();
                if (!pass.equals(repass)) {
                    Snackbar.make(content, R.string.no_match, BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                String safename = txt_pro_name.getText().toString();
                if (safename.isEmpty()) {
                    Snackbar.make(content, R.string.no_pro_name, BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                String safephone = txt_pro_phone.getText().toString();
                if (safephone.isEmpty()) {
                    Snackbar.make(content, R.string.no_pro_phone, BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }

                UserModel user = new UserModel();
                user.account = email;
                user.memberName = name;
                user.mobile = phone;
                user.safePerson = safename;
                user.safeMobile = safephone;

                dialog.show();
                user.register(pass, "bgold1118@gmail.com", "123456", new OnHttpListener() {
                    @Override
                    public void onEventCallBack(JSONObject obj, int ret) {
                        dialog.dismiss();
                        if (ret == 1) {
                            activity.loadFragmentByIndex(3);
                            activity.registedName = user.memberName;
                            activity.registedEmail = user.account;
                            Snackbar.make(content, R.string.success_register, BaseTransientBottomBar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(content, R.string.failed_register, BaseTransientBottomBar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onEventInternetError(Exception e) {
                        dialog.dismiss();
                        Snackbar.make(content, Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEventServerError(Exception e) {
                        dialog.dismiss();
                        Snackbar.make(content, Objects.requireNonNull(e.getMessage()), BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
