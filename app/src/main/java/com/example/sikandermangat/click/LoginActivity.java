package com.example.sikandermangat.click;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikandermangat.click.Services.FirebaseDataBaseReferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private TextView createAcc;

    private Button login;

    private EditText email;

    private EditText pass;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    private FirebaseDataBaseReferences firebaseObject;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();


        login = (Button) findViewById(R.id.btn_login);

        createAcc = (TextView) findViewById(R.id.link_register);

        email = (EditText) findViewById(R.id.login_email);

        pass = (EditText) findViewById(R.id.login_password);

        progressBar=findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);


        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {





                if (firebaseAuth.getCurrentUser() != null) {


                    startActivity(new Intent(LoginActivity.this,MainActivity.class));


                }


            }

        };

        createAcc.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Intent screen = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(screen);

            }

        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {



                SignIn();

              progressBar.setVisibility(View.VISIBLE);

              login.setVisibility(View.GONE);

              createAcc.setVisibility(View.VISIBLE);

            }

        });




    }

    @Override

    protected void onStart() {

        super.onStart();



        auth.addAuthStateListener(authStateListener);

    }

    private void SignIn() {



        String emailStr = email.getText().toString();

        String passStr = pass.getText().toString();



        if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passStr)) {



            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();



        } else {



            auth.signInWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override

                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(LoginActivity.this, "Sign in Problem", Toast.LENGTH_SHORT).show();

                    }

                    progressBar.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    createAcc.setVisibility(View.VISIBLE);

                }
            });



        }





    }
}
