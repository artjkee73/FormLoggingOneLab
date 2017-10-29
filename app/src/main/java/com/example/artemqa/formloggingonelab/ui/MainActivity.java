package com.example.artemqa.formloggingonelab.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.artemqa.formloggingonelab.R;
import com.example.artemqa.formloggingonelab.model.User;
import com.example.artemqa.formloggingonelab.util.Utils;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String LOG ="MyLog";
    public final static String LOGIN_USER = "loginUser_MainActivity";
    Button btnChangePass, btnAbout, btxExit, btnAdminPanel;
    TextView tvMainActivity;
    Realm realm;
    boolean userIsAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        userIsAdmin = userIsAdmin();

        tvMainActivity = (TextView) findViewById(R.id.tv_main_activity);
        tvMainActivity.setText(setTvText());

        btnChangePass = (Button) findViewById(R.id.btn_change_pass_main_activity);
        btnChangePass.setOnClickListener(this);
        btnAbout = (Button) findViewById(R.id.btn_about_main_activity);
        btnAbout.setOnClickListener(this);
        btxExit = (Button) findViewById(R.id.btn_exit_main_activity);
        btxExit.setOnClickListener(this);
        btnAdminPanel = (Button) findViewById(R.id.btn_admin_panel_main_activity);
        btnAdminPanel.setOnClickListener(this);
        visibleAdminPanel(btnAdminPanel);
    }

    private String getLoginIntent() {
        Bundle data = getIntent().getExtras();
        String login = data.getString(LoginActivity.LOGIN_USER);
        return login;

    }

    private boolean userIsAdmin() {
        User user = realm.where(User.class)
                .equalTo("mLogin", getLoginIntent())
                .findFirst();

        return user.getLogin().equals(Utils.ADMIN_LOGIN);
    }

    private void visibleAdminPanel(Button btnAdminPanel) {
        if (!userIsAdmin) {
            btnAdminPanel.setVisibility(View.GONE);
        }
    }

    private String setTvText() {
        String status = "пользователь";
        if (userIsAdmin)
            status = "администратор";
        return "Вы вошли как : " + status + "\n" + "Ваш логин : " + getLoginIntent();
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_change_pass_main_activity:
                intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                intent.putExtra(LOGIN_USER, getLoginIntent());
                startActivity(intent);

                break;
            case R.id.btn_about_main_activity:
                intent = new Intent(MainActivity.this, AboutProgramActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_exit_main_activity:
                finishAffinity();
                break;
            case R.id.btn_admin_panel_main_activity:
                intent = new Intent(MainActivity.this, AdminPanelActivity.class);
                startActivity(intent);
                break;
        }

    }
}