package com.securesoftbd.cco.bloodbonorlist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class RegistationActivity extends AppCompatActivity {
    private TextInputEditText user,number,pass,confirm;
    private Button button;
    private String userName,numberAuth,password,confrmPassword;
    private FirebaseAuth firebaseAuth;
    private String mVerificationId;
    private String code= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
         init();
        onClick();
    }

    private void onClick()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName  = user.getText().toString();
                numberAuth = number.getText().toString();
                password = pass.getText().toString();
                confrmPassword = confirm.getText().toString();

               // String code = user.getText().toString().trim();

                if(code == null)
                {

                    if(userName.isEmpty() || numberAuth.isEmpty() || password.isEmpty() || confrmPassword.isEmpty())
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistationActivity.this);
                        builder.setMessage("Empty fill");
                        builder.show();

                    }
                    else
                    {
                        sendVerificationCode(numberAuth);

                        if(code != null)
                        {
                            confirmClear();

                        }

                    }
                }
               else if (code != null && code.length() < 6) {
                    user.setError("Enter valid code");
                    user.requestFocus();
                    return;
                } else
                {
                    verifyVerificationCode(code);
                }







            }
        });
    }

    private void confirmClear() {
        button.setText("Confirm");
        CardView cardView1= findViewById(R.id.cardView1),cardView3= findViewById(R.id.cardView3),cardView4= findViewById(R.id.cardView4);
        cardView1.setVisibility(View.GONE);
        cardView3.setVisibility(View.GONE);
        cardView4.setVisibility(View.GONE);
        number.setText(null);
    }


    private void init()
    {
        user = findViewById(R.id.userdSingUpETId);
        number = findViewById(R.id.phoneNumberSingUpETId);
        pass = findViewById(R.id.passSingUpETId);
        confirm = findViewById(R.id.confirmSingUpETId);
        button = findViewById(R.id.signupBtnId);
        firebaseAuth = FirebaseAuth.getInstance();


    }



    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+88" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
        confirmClear();

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null)
            {
                number.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(RegistationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }

    };


    private void verifyVerificationCode(String code)
    {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(RegistationActivity.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                           // Common.phone_number=number;
                           // User user =new User(number,getIntent().getStringExtra("name"),getIntent().getStringExtra("password"));
                            FirebaseDatabase.getInstance().getReference("User").setValue(user.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegistationActivity.this, "Added to the Firebase database", Toast.LENGTH_SHORT).show();
                                }
                            });


                            Intent intent=new Intent(RegistationActivity.this,MainActivity.class);
                            intent.putExtra("number",getIntent().getStringExtra("number"));
                            startActivity(intent);

                        } else {
                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(RegistationActivity.this,""+message,Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }



}
