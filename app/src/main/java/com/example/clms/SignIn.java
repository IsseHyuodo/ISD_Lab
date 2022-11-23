package com.example.clms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText userid;
    private EditText password;
    private TextView head;
    private Button signin;
    String type;
    boolean done = false;
    DatabaseReference ref, ref1, ref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        head = (TextView) findViewById(R.id.textView);
        userid = (EditText) findViewById(R.id.userid);
        password = (EditText) findViewById(R.id.password_login);
        signin = (Button) findViewById(R.id.signin);
        ref = FirebaseDatabase.getInstance().getReference("users");
        ref1 = FirebaseDatabase.getInstance().getReference("AL");
        ref2 = FirebaseDatabase.getInstance().getReference("LT");
        Intent i = getIntent();
        type = i.getStringExtra("Type");

        head.setText(type + " Log In");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useriddata =userid.getText().toString();
                String passworddata = password.getText().toString();

                if(TextUtils.isEmpty(useriddata) || TextUtils.isEmpty(passworddata)){
                    Toast.makeText(SignIn.this, "Enter all the details.", Toast.LENGTH_SHORT).show();
                }else{
                    login(useriddata , passworddata);

                }
            }
        });
    }

    private void login(String useriddata , String passworddata){
        /*auth.signInWithEmailAndPassword(useriddata , passworddata).addOnSuccessListener(SignIn.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignIn.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignIn.this , UserHome.class);

            }
        });*/

        //Toast.makeText(SignIn.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
        Intent intent;
        if(type.equalsIgnoreCase("Student/Faculty"))
        {
            Query q = ref.orderByChild("Email").equalTo(useriddata);

            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Toast.makeText(SignIn.this, snapshot.getKey() , Toast.LENGTH_SHORT).show();
                    for(DataSnapshot ds: snapshot.getChildren())
                    {
                        //Toast.makeText(SignIn.this, ds.getKey(), Toast.LENGTH_SHORT).show();
                        if(ds.child("Password").getValue(String.class).equals(passworddata)){
                            Intent intent = new Intent(SignIn.this , UserHome.class);
                            intent.putExtra("user", ds.getKey());
                            //Toast.makeText(SignIn.this, snapshot.getKey(), Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            done=true;
                        }
                    }

                    if(done==false)
                    {
                        Toast.makeText(SignIn.this, "Invalid UserId or Password", Toast.LENGTH_SHORT).show();
                        done=true;
                    }
                    /*if(snapshot.child("Password").getValue(String.class).equals(passworddata)){
                       //Intent intent = new Intent(SignIn.this , UserHome.class);
                        Toast.makeText(SignIn.this, snapshot.getKey(), Toast.LENGTH_SHORT).show();
                        //startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(SignIn.this, "Signed In failed.", Toast.LENGTH_SHORT).show();
                    }*/
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if(type.equalsIgnoreCase("Assistant Librarian"))
        {
            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot ds1: snapshot.getChildren())
                    {
                            if(ds1.getValue(String.class).equals(passworddata) && ds1.getKey().equals(useriddata))
                            {
                                Intent intent = new Intent(SignIn.this , AL_Home.class);
                                startActivity(intent);
                                Toast.makeText(SignIn.this, "Signed In Successfully!", Toast.LENGTH_SHORT).show();
                                done=true;
                            }


                    }
                    if(done==false)
                    {
                        Toast.makeText(SignIn.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        done=true;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if(type.equalsIgnoreCase("Library Trainee"))
        {
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot ds1: snapshot.getChildren())
                    {
                        if(ds1.getValue(String.class).equals(passworddata) && ds1.getKey().equals(useriddata))
                        {
                            Intent intent = new Intent(SignIn.this , LT_Home.class);
                            startActivity(intent);
                            Toast.makeText(SignIn.this, "Signed In Successfully!", Toast.LENGTH_SHORT).show();
                            done=true;
                        }


                    }
                    if(done==false)
                    {
                        Toast.makeText(SignIn.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        done=true;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }



}