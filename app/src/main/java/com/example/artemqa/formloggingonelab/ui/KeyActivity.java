package com.example.artemqa.formloggingonelab.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.artemqa.formloggingonelab.R;
import com.example.artemqa.formloggingonelab.util.Utils;

import io.realm.Realm;

public class KeyActivity extends AppCompatActivity {

    Realm realm;
    EditText etKeyActivity;
    Button btnKeyActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);
        Realm.init(this);
        etKeyActivity = (EditText) findViewById(R.id.et_key_activity);
        btnKeyActivity = (Button) findViewById(R.id.btn_key_activity);
        btnKeyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(etKeyActivity.getText().toString()) == Utils.ACCESS_APPLICATION_KEY ){
                    Intent intent = new Intent(KeyActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
