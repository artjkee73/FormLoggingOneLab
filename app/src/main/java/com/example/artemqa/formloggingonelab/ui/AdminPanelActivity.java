package com.example.artemqa.formloggingonelab.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.artemqa.formloggingonelab.R;
import com.example.artemqa.formloggingonelab.adapter.RecyclerViewAdapter;
import com.example.artemqa.formloggingonelab.model.User;
import com.example.artemqa.formloggingonelab.util.Utils;

import io.realm.Realm;
import io.realm.RealmResults;

public class AdminPanelActivity extends AppCompatActivity {
    EditText etLoginUser;
    Button btnAddUser;
    RecyclerView recyclerView;
    Realm realm;
    LinearLayoutManager linearLayoutManager;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        realm = Realm.getDefaultInstance();

        etLoginUser = (EditText) findViewById(R.id.et_ap_activity);
        recyclerView = (RecyclerView) findViewById(R.id.rv_ap_activity);
        btnAddUser = (Button) findViewById(R.id.btn_ap_activity);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(getListUsers(),AdminPanelActivity.this);
        recyclerView.setAdapter(adapter);


        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = realm.where(User.class)
                        .equalTo("mLogin", etLoginUser.getText().toString())
                        .findFirst();
                if (etLoginUser.getText().toString().equals("")) {
                    Toast.makeText(AdminPanelActivity.this, "Нелья добавить пользователя с пустым логином", Toast.LENGTH_SHORT).show();
                } else {
                    if (user == null) {
                        final User newUser = new User(etLoginUser.getText().toString(), Utils.encryptStr(Utils.toMD4(Utils.NEW_USER_PASSWORD)) , Utils.NEW_USER_IS_BLOCKED, Utils.NEW_USER_IS_LIMITATION);
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(newUser);
                            }
                        });
                    } else {
                        Toast.makeText(AdminPanelActivity.this, "Пользователь с таким логином уже существует", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private RealmResults<User> getListUsers() {
        realm = Realm.getDefaultInstance();
        return realm.where(User.class)
                .findAll();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case RecyclerViewAdapter.CustomViewHolder.REQUEST_CODE:
                    int position = data.getIntExtra(UserActivity.EXTRA_POSITION,0);
                    adapter.notifyItemChanged(position);
                    break;
            }
        }
    }
}
