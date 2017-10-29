package com.example.artemqa.formloggingonelab.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.artemqa.formloggingonelab.R;
import com.example.artemqa.formloggingonelab.model.User;
import com.example.artemqa.formloggingonelab.util.Utils;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {
    public final static String LOG = "MyLog";
    public final static String LOGIN_USER = "loginUser_LoginActivity";
    EditText etLoginLoginActivity, etPasswordLoginActivity;
    Button btnLoginActivity;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        realm = Realm.getDefaultInstance();
        addAdminAccount();
        init();

    }

    private void init() {


        etLoginLoginActivity = (EditText) findViewById(R.id.et_login_login_activity);
        etPasswordLoginActivity = (EditText) findViewById(R.id.et_password_login_activity);

        btnLoginActivity = (Button) findViewById(R.id.btn_login_activity);
        btnLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = realm.where(User.class)
                        .equalTo("mLogin", etLoginLoginActivity.getText().toString())
                        .equalTo("mPassword", etPasswordLoginActivity.getText().toString())
                        .findFirst();

                if (user != null) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(LOGIN_USER, user.getLogin());
                    startActivity(intent);


                } else {

                    Toast.makeText(LoginActivity.this, R.string.no_user_e_login_activity, Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private void addAdminAccount() {

        RealmResults<User> results = realm.where(User.class)
                .equalTo("mLogin", Utils.ADMIN_LOGIN)
                .findAll();

        if (results.size() == 0) {
            final User admin = new User(Utils.ADMIN_LOGIN, Utils.ADMIN_PASSWORD, Utils.ADMIN_IS_BLOCKED, Utils.ADMIN_IS_LIMITATION);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(admin);
                }
            });
        }

    }
}
