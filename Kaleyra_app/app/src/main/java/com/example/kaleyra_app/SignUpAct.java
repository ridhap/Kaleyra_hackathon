package com.example.kaleyra_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUpAct extends AppCompatActivity {
    EditText name, RegPwd, RegEmail;
    TextView userLogin;
    String nameValue,usernameValue,passwordValue;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private ProgressDialog loader;
    Button RegBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = (EditText)findViewById(R.id.etUserName);
        RegPwd = (EditText)findViewById(R.id.etUserPassword);
        RegEmail = (EditText)findViewById(R.id.etUserEmail);
        userLogin = (TextView)findViewById(R.id.tvUserLogin);
        RegBtn=findViewById(R.id.btnRegister);

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpAct.this, Login.class));
                finish();
            }
        });

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = RegEmail.getText().toString().trim();
                String password = RegPwd.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    RegEmail.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    RegPwd.setError("Password required");
                    return;
                }else {
//                    loader.setMessage("Registration in progress");
//                    loader.setCanceledOnTouchOutside(false);
//                    loader.show();
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Intent intent = new Intent(SignUpAct.this, Home.class);
                                startActivity(intent);
                                finish();
//                                loader.dismiss();
                            }else {
                                String error = task.getException().toString();
                                Toast.makeText(SignUpAct.this, "Registration failed" + error, Toast.LENGTH_SHORT).show();
//                                loader.dismiss();
                            }

                        }
                    });
                }



            }
        });
    }
//    public void OnRegisterClicked(View view) {
//        nameValue = name.getText().toString();
//        usernameValue = username.getText().toString();
//        passwordValue=password.getText().toString();
//
//
//
//        if (usernameValue.equals("")) {
//            username.setError("Enter Your Name");
//            username.requestFocus();
//            return;
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(usernameValue).matches()) {
//            username.setError("Enter a valid Email Id");
//            username.requestFocus();
//            return;
//        }
//
//        if (password.getText().toString().length() < 6) {
//            password.setError("Set A Password Length greater than 6");
//            password.requestFocus();
//            return;
//        }
//        loader.setMessage("Registration in progress");
//        loader.setCanceledOnTouchOutside(false);
//        loader.show();
//        firebaseAuth.createUserWithEmailAndPassword(usernameValue,passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                if (task.isSuccessful()){
//                    Intent intent = new Intent(SignUpAct.this, Home.class);
//                    startActivity(intent);
//                    finish();
//                    loader.dismiss();
//                }else {
//                    String error = task.getException().toString();
//                    Toast.makeText(SignUpAct.this, "Registration failed" + error, Toast.LENGTH_SHORT).show();
//                    loader.dismiss();
//                }
//
//            }
//        });
//    }
}