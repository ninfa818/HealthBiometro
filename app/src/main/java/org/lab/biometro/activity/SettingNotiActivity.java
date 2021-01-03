package org.lab.biometro.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.lab.biometro.R;
import org.lab.biometro.adapter.NotificationAdapter;
import org.lab.biometro.model.NotificationModel;
import org.lab.biometro.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class SettingNotiActivity extends AppCompatActivity {

    private ImageView img_back;
    private RelativeLayout rlt_toolbar_noti;

    private NotificationAdapter adapter;
    private final List<NotificationModel> notificationModels = new ArrayList<>();

    private void initEvent() {
        img_back.setOnClickListener(view -> onBackPressed());
        rlt_toolbar_noti.setOnClickListener(view -> {

        });
        adapter.setOnNotificationCellListener(model -> AppUtil.showOtherActivity(SettingNotiActivity.this, NotiDetailActivity.class, 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_noti);

        initView();
        initEvent();
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
        rlt_toolbar_noti = findViewById(R.id.rlt_toolbar_noti);

        ListView lst_notification = findViewById(R.id.lst_notification);
        adapter = new NotificationAdapter(this, notificationModels);
        lst_notification.setAdapter(adapter);

        initData();
    }

    private void initData() {
        NotificationModel model = new NotificationModel();

        notificationModels.add(model);
        notificationModels.add(model);
        notificationModels.add(model);

        adapter.notifyDataSetChanged();
    }


}
