//main page to show the details of the user in the homepage

package com.example.profilehomepage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    //declaring variables
    TextView fullName,email,phone, verifyMsg;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button resendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the variables
        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhone);

        //authorizatioon code for firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //getting the user ID of the user who logged in
        userId = fAuth.getCurrentUser().getUid();

        resendCode = findViewById(R.id.ResendCode);
        verifyMsg = findViewById(R.id.VerifyMsg);

        //getting the current user
        //if there's no user it'll redirect to the profile page
        FirebaseUser user = fAuth.getCurrentUser();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MyProfile.class));
            finish();
        }

        /*
        if(!user.isEmailVerified()){
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Verify Your Email", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: Email hasn't been send." + e.getMessage());
                        }
                    });

                }
            });
        }

         */

        //creating the collection users in firebase
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

    /*
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),UBookLogin.class));
        finish();
    }

     */
}