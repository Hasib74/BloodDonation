package com.securesoftbd.cco.bloodbonorlist;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PhoneAuthActivity extends AppCompatActivity {

    private EditText textInputEditText;
    private Button singUpBTN;
    private TextView gotoSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        textInputEditText = findViewById(R.id.numberLoginETId);
        singUpBTN = findViewById(R.id.loginBtnId);
        gotoSignUp = findViewById(R.id.gotoSingupActivityId);

        gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhoneAuthActivity.this,RegistationActivity.class));
            }
        });

        singUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

       String  id =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("id", "onStart: "+id);
        if(id != null)
        {
            startActivity(new Intent(PhoneAuthActivity.this,MainActivity.class));
            finish();
        }

    }
}
