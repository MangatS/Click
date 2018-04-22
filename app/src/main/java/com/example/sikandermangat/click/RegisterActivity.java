package com.example.sikandermangat.click;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikandermangat.click.Model.UserClass;
import com.example.sikandermangat.click.Services.FirebaseDataBaseReferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName;

    private EditText createEmail;

    private EditText newPass;

    private Button createbtn;

    private TextView login;

    private FirebaseAuth createAuth;

    private ProgressBar progressBar;

    private FirebaseDataBaseReferences firebaseObject;

    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName=findViewById(R.id.input_name);

        createEmail=findViewById(R.id.input_email);

        newPass=findViewById(R.id.input_password);

        login=findViewById(R.id.link_login);

        createbtn=(Button)findViewById(R.id.btn_signup);

        createAuth=FirebaseAuth.getInstance();





        progressBar=findViewById(R.id.register_progressbar);

        progressBar.setVisibility(View.GONE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        createbtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                createbtn.setVisibility(View.GONE);

                login.setVisibility(View.GONE);

                progressBar.setVisibility(View.VISIBLE);

                String email=createEmail.getText().toString();

                String pass=newPass.getText().toString();


                if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(pass)){



                    Toast.makeText(getApplicationContext(), "Feilds are empty", Toast.LENGTH_SHORT).show();

                    createbtn.setVisibility(View.VISIBLE);

                    login.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.GONE);

                }

                else {

                    createAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {




                                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(RegisterActivity.this);

                                    builder.setTitle("Message");


                                    builder.setMessage("May be email is already exits or invalid").setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int id) {


                                            builder.setCancelable(false);

                                            createbtn.setVisibility(View.VISIBLE);

                                            login.setVisibility(View.VISIBLE);

                                            progressBar.setVisibility(View.GONE);

                                        }
                                    });

                                    builder.show();

                                } else {

                                    firebaseObject=new FirebaseDataBaseReferences();

                                    ref=firebaseObject.getMyRef();

                                    UserClass userClass=new UserClass();
                                    userClass.setUserName(userName.getText().toString());
                                    userClass.setEmail(createEmail.getText().toString());
                                    userClass.setUserId(firebaseObject.getAuth().getCurrentUser().getUid());

                                    ref.child("Users").child(firebaseObject.getFirebaseUserid()).setValue(userClass);
                                    Toast.makeText(getApplicationContext(), "Account is Created", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                                    createbtn.setVisibility(View.VISIBLE);

                                    login.setVisibility(View.VISIBLE);

                                    progressBar.setVisibility(View.GONE);


                                }

                            }

                        });

                }


            }

        });




    }
}
