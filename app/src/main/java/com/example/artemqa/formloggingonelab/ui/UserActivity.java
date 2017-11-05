package com.example.artemqa.formloggingonelab.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.artemqa.formloggingonelab.R;
import com.example.artemqa.formloggingonelab.adapter.RecyclerViewAdapter;
import com.example.artemqa.formloggingonelab.model.User;

import io.realm.Realm;

public class UserActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    public final static String EXTRA_POSITION = "Position_Extra_UserActivity";
    TextView tvUsernameUserActivity;
    CheckBox cbBlockedUserActivity, cbLimitationUserActivity;
    Button btnOkUserActivity;
    private int positionExtra;
    private String loginExtra;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getIntentData();

        tvUsernameUserActivity = (TextView) findViewById(R.id.tv_username_user_activity);
        setTextTV(tvUsernameUserActivity);
        cbBlockedUserActivity = (CheckBox) findViewById(R.id.cb_blocked_user_activity);
        setCheckableBlocked(cbBlockedUserActivity);
        cbBlockedUserActivity.setOnCheckedChangeListener(this);
        cbLimitationUserActivity = (CheckBox) findViewById(R.id.cb_limitation_user_activity);
        cbLimitationUserActivity.setOnCheckedChangeListener(this);
        setCheckableLimitation(cbLimitationUserActivity);
        btnOkUserActivity = (Button) findViewById(R.id.btn_ok_user_activity);
        btnOkUserActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra(EXTRA_POSITION, positionExtra);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    private void getIntentData() {
        Bundle data = getIntent().getExtras();
        positionExtra = data.getInt(RecyclerViewAdapter.CustomViewHolder.EXTRA_POSITION);
        loginExtra = data.getString(RecyclerViewAdapter.CustomViewHolder.EXTRA_LOGIN);
    }

    private void setTextTV(TextView tv) {
        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class)
                .equalTo("mLogin", loginExtra)
                .findFirst();

        tv.setText("Пользователь : " + user.getLogin());
    }

    private void setCheckableBlocked(CheckBox cb) {
        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class)
                .equalTo("mLogin", loginExtra)
                .findFirst();

        cb.setChecked(user.isBlocked());
    }

    private void setCheckableLimitation(CheckBox cb) {
        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class)
                .equalTo("mLogin", loginExtra)
                .findFirst();

        cb.setChecked(user.isPasLimitation());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_blocked_user_activity:
                setUserCB(b, R.id.cb_blocked_user_activity);
                break;
            case R.id.cb_limitation_user_activity:
                setUserCB(b, R.id.cb_limitation_user_activity);
                break;
        }
    }
    private void setUserCB(final boolean b, final int compoundId) {

        final User user = realm.where(User.class)
                .equalTo("mLogin", loginExtra)
                .findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if ( compoundId == R.id.cb_blocked_user_activity)
                    user.setBlocked(b);
                else
                    user.setPasLimitation(b);

                realm.copyToRealmOrUpdate(user);
            }
        });
    }
}