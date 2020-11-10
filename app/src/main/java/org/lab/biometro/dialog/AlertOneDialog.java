package org.lab.biometro.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.lab.biometro.R;
import org.lab.biometro.listener.OnClickAlertDialogListener;

public class AlertOneDialog extends Dialog {

    private String title, description, str_button;

    private TextView lbl_title, lbl_desc, lbl_button;
    private OnClickAlertDialogListener onClickAlertDialogListener;


    public AlertOneDialog(@NonNull Context context, String title, String desc, String buttonTxt) {
        super(context);

        this.title = title;
        this.description = desc;
        this.str_button = buttonTxt;

        setContentView(R.layout.dialog_alert_one);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        setTitle(null);
        setCanceledOnTouchOutside(true);

        lbl_title = findViewById(R.id.lbl_title);
        lbl_desc = findViewById(R.id.lbl_desc);
        lbl_button = findViewById(R.id.lbl_button);

        initView();
        initEvent();
    }

    private void initView() {
        lbl_title.setText(title);
        lbl_desc.setText(description);
        lbl_button.setText(str_button);
    }

    private void initEvent() {
        lbl_button.setOnClickListener(view -> {
            dismiss();
            if (onClickAlertDialogListener != null) {
                onClickAlertDialogListener.onClickConfirmButton();
            }
        });
    }

    public void setOnClickAlertDialogListener(OnClickAlertDialogListener onClickAlertDialogListener) {
        this.onClickAlertDialogListener = onClickAlertDialogListener;
    }

}
