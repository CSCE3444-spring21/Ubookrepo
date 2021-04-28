//my profile class which will show the profile information
package com.example.profilehomepage;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import androidx.appcompat.app.AppCompatActivity;

//import android.os.Bundle;
//import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;

//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toast;

//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MyProfile extends AppCompatActivity {

    //declaring the variables
    TextView fullName,email,phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        addListenerOnButton();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initializing the variables
        fullName = findViewById(R.id.textView4);
        email = findViewById(R.id.textView6);
        phone = findViewById(R.id.textView7);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        //getting the info of the user from firebase
        //shows the info in the profile page
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                phone.setText(documentSnapshot.getString("Phone"));
                fullName.setText(documentSnapshot.getString("Name"));
                email.setText(documentSnapshot.getString("Email"));
            }
        });
    }

    Button a;
    Button b;

    public void addListenerOnButton() {
        final Context context = this;
        a = (Button) findViewById(R.id.upload);
        b = (Button) findViewById(R.id.courseBook);


        //onclick listener for upload button
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, Upload.class);
                startActivity(intent);
            }
        });

        //onclick listener for view book button
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewBooks.class);
                startActivity(intent);
            }
        });
    }
}