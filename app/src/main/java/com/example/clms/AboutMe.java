package com.example.clms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AboutMe extends Fragment {

    TextView welcome, name, dob, email, phone, type, gender;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_me, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        welcome = (TextView) getActivity().findViewById(R.id.textView_show_welcome);
        name = (TextView) getActivity().findViewById(R.id.textView_show_full_name);
        email = (TextView) getActivity().findViewById(R.id.textView_show_email);
        phone = (TextView) getActivity().findViewById(R.id.textView_show_mobile);
        type = (TextView) getActivity().findViewById(R.id.textView_user_type);
        gender = (TextView) getActivity().findViewById(R.id.textView_show_gender);

        Intent i = getActivity().getIntent();
        ref = ref.child(i.getStringExtra("user"));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Toast.makeText(getActivity(), ref.getKey(), Toast.LENGTH_SHORT).show();
                String nam = snapshot.child("Name").getValue(String.class);
                String[] split = nam.split(" ");

                welcome.setText("Welcome, " + split[0]+"!");
                name.setText(nam);
                email.setText(snapshot.child("Email").getValue(String.class));
                phone.setText(snapshot.child("Phone").getValue(String.class));
                type.setText(snapshot.child("Type").getValue(String.class));
                gender.setText(snapshot.child("Gender").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}