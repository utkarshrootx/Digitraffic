package com.example.digitrafficinspectorv0;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    public EditText registerEmailId, passwd, cnfrmpasswd, mobile, name;
    Button btnRegister;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    LoadingDialogue loadingDialogue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        registerEmailId = findViewById(R.id.registerEmail);
        passwd = findViewById(R.id.paswd);
        mobile = findViewById(R.id.mobile);
        name = findViewById(R.id.name);
        passwd = findViewById(R.id.paswd);
        cnfrmpasswd = findViewById(R.id.cnfrmpaswd);
        btnRegister = findViewById(R.id.btnRegister);
        db = FirebaseFirestore.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = registerEmailId.getText().toString();
                String userPaswd = passwd.getText().toString();
                String cnfrmPaswd = cnfrmpasswd.getText().toString();
                final String userName = name.getText().toString();
                final String userMobile = mobile.getText().toString();
                loadingDialogue = new LoadingDialogue( RegistrationActivity.this);

                if (userEmail.isEmpty()) {
                    registerEmailId.setError("Provide your Email first!");
                    registerEmailId.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    passwd.setError("Enter Password!");
                    passwd.requestFocus();
                } else if (cnfrmPaswd.isEmpty()) {
                    cnfrmpasswd.setError("Confirm Password!");
                    cnfrmpasswd.requestFocus();
               }
//                else if ((userEmail.isEmpty() && userPaswd.isEmpty()) || (userEmail.isEmpty() && cnfrmPaswd.isEmpty()) || (userPaswd.isEmpty()  && cnfrmPaswd.isEmpty()) ) {
                else if (userEmail.isEmpty() || userEmail.isEmpty() || cnfrmPaswd.isEmpty() || userName.isEmpty() || userMobile.isEmpty() ) {
                        Toast.makeText(RegistrationActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(userEmail.isEmpty() || userPaswd.isEmpty() || cnfrmPaswd.isEmpty() || userName.isEmpty() || userMobile.isEmpty() )) {
                    if (userPaswd.equals(cnfrmPaswd)) {
                        loadingDialogue.startLoadingDialogue();
                        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                                } else {
                                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    Map<String, Object> newCase = new HashMap<>();
                                    newCase.put("USERID", currentuser);
                                    newCase.put("USER_TYPE", "Public");
                                    newCase.put("NAME", userName);
                                    newCase.put("MOBILE", userMobile);
                                    newCase.put("TOTAL_SCORE", "0");
                                    db.collection("USERDB").document(currentuser).set(newCase)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(RegistrationActivity.this, "Registration Successful",
                                                            Toast.LENGTH_SHORT).show();
                                                    loadingDialogue.dismissDialogue();
                                                    startActivity(new Intent(RegistrationActivity.this, ProfileActivity2.class));
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(RegistrationActivity.this, "ERROR" + e.toString(),
                                                            Toast.LENGTH_SHORT).show();
                                                    loadingDialogue.dismissDialogue();
                                                    Log.d("TAG", e.toString());
                                                }
                                            });
                                }


                                }

                        });
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();

                    }
                }
                    else {
                    Toast.makeText(RegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}