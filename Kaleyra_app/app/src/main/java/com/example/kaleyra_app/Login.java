package com.example.kaleyra_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Login extends AppCompatActivity {
    private EditText Name, Password;
    private Button Login;
    private TextView Info;
    private int counter = 3;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        Info = (TextView)findViewById(R.id.tvInfo);
        userRegistration = (TextView)findViewById(R.id.tvRegister);
        Info.setText("No of attempt remaining : 3");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {
            finish();
            startActivity(new Intent(Login.this, Home.class));
            finish();

        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUpAct.class));
                finish();
            }
        });
        }
    private void validate(String userName, String userPassword) {
        String usernameValue = Name.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(usernameValue).matches()) {
            Name.setError("Enter a valid Email Id");
            Name.requestFocus();
            return;
        }

        if (Password.getText().toString().length() < 6) {
            Password.setError("Set A Password Length greater than 6");
            Password.requestFocus();
            return;
        }

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d("XXXXXXXXXXXXXXXXXXXXXXXXXXX------>", "ALLOW THEM AT ONCE");
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                    Uid = currentFirebaseUser.getUid();
                    progressDialog.dismiss();

                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.putExtra("parcel", Uid);
                    startActivity(intent);
                    finish();
                    finish();
                } else {
                    Toast.makeText(Login.this, "Login Failed!! Please SignUp if not Signed Up.", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempt remaining : " + counter);
                    progressDialog.dismiss();
                    if (counter == 0) {
                        Login.setEnabled(false);
                    }
                }
            }
        });



    }
}