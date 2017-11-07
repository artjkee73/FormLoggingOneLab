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

import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmResults;

public class ChangePasswordActivity extends AppCompatActivity {
    public final static String LOG = "MyLog";
    EditText etOldPassword, etNewPassword, etRepeatPassword;
    Button btnOk;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        realm = Realm.getDefaultInstance();

        etOldPassword = (EditText) findViewById(R.id.et_old_pass_cp_activity);
        etNewPassword = (EditText) findViewById(R.id.et_new_pass_cp_activity);
        etRepeatPassword = (EditText) findViewById(R.id.et_repeat_pass_cp_activity);


        btnOk = (Button) findViewById(R.id.btn_cp_activity);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final User user = realm.where(User.class).
                        equalTo("mLogin", getLoginIntent())
                        .findFirst();

                if (Utils.encryptStr(Utils.toMD4(etOldPassword.getText().toString())).equals(getPasswordUser(getLoginIntent()))) {
                    if (etNewPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
                        if (isPasLimitationUser(etNewPassword.getText().toString(), user)) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    user.setPassword(Utils.encryptStr(Utils.toMD4(etNewPassword.getText().toString())));
                                    realm.copyToRealmOrUpdate(user);
                                }
                            });
                            Toast.makeText(ChangePasswordActivity.this, "Пароль успешно изменён на : " + etNewPassword.getText().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Ваш пароль должен содержать только знаки препинания и буквы", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Некорретно введён повторно новый пароль", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Некорретно введён текущий пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private String getLoginIntent() {
        Bundle data = getIntent().getExtras();
        String login = data.getString(MainActivity.LOGIN_USER);
        return login;

    }

    private String getPasswordUser(String login) {
        RealmResults<User> results = realm.where(User.class)
                .equalTo("mLogin", login)
                .findAll();
        User user = results.where().findFirst();
        return user.getPassword();
    }

    private boolean isPasLimitationUser(String password, User user) {
        if (!user.isPasLimitation()) {
            return true;
        } else if ((Pattern.matches("^[a-zA-Zа-яА-Я.,;:\'\"?!]+$", password))) {
            return true;
        } else return false;
    }
}

