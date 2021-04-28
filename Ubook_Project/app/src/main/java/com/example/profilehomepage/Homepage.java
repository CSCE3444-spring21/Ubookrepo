//homepage
package com.example.profilehomepage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;


public class Homepage extends AppCompatActivity {
    //declaring the buttons
    Button one;
    Button two;
    Button three;
    Button four;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        addListenerOnButton();
    }

    //onclick listener for buttons
    public void addListenerOnButton() {
        final Context context = this;
        one = (Button) findViewById(R.id.search_page);
        //two = (Button) findViewById(R.id.chat_message);
        three = (Button) findViewById(R.id.my_profile);
        four = (Button) findViewById(R.id.log_out);

        //onclick for search button
        one.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, SearchPage.class);
                startActivity(intent);
            }
        });
        /*
        two.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, Chat.class);
                startActivity(intent);
            }
        });

         */

        //onclick for profile button
        three.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MyProfile.class);
                startActivity(intent);
            }
        });

        //onclick for logout button
        four.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UBookLogin.class);
                startActivity(intent);
            }
        });
    }
}