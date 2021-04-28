//upload book class
package com.example.profilehomepage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class Upload extends AppCompatActivity {

    //declaring the variables
    private EditText bookName, bookAuthor, bookIsbn, bookPrice, bookYear;
    private Button uploadBtn;
    private Button viewCourse;

    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private FirebaseFirestore db;

    BookInfo bookInfo;//creating instance of bookinfo class


    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initializing the variables
        db=FirebaseFirestore.getInstance();
        bookName = findViewById(R.id.editbookName);
        bookAuthor = findViewById(R.id.editauthorName);
        bookIsbn = findViewById(R.id.editisbnNum);
        bookPrice = findViewById(R.id.editpriceBook);
        bookYear = findViewById(R.id.editdateBook);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

        //this will add the book to firebase database
        databaseReference = firebaseDatabase.getReference("Books").push();

        bookInfo = new BookInfo();

        uploadBtn = findViewById(R.id.upload);
        userID = fAuth.getCurrentUser().getUid();


        //onclick listener for upload button
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = bookName.getText().toString();
                String author = bookAuthor.getText().toString();
                String isbn = bookIsbn.getText().toString();
                String price = bookPrice.getText().toString();
                String year = bookYear.getText().toString();

                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(author) && TextUtils.isEmpty(isbn) && TextUtils.isEmpty(price) && TextUtils.isEmpty(year)) {
                    Toast.makeText(Upload.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDataToFirestore(name, author, isbn, price,year, userID);
                    addDataToFirebase(name,author,isbn,price,year, userID);
                }
                startActivity(new Intent(getApplicationContext(), MyProfile.class));
            }
        });
    }

    //function to add the book info in the firebase realtime database
    private void addDataToFirebase(String name, String author, String isbn, String price, String year, String userID) {
        bookInfo.setBookname(name);
        bookInfo.setAuthor(author);
        bookInfo.setIsbn(isbn);
        bookInfo.setPrice(price);
        bookInfo.setYear(year);
        bookInfo.setSellerId(userID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(bookInfo);
                Toast.makeText(Upload.this, "Book added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Upload.this, "Failed to add data" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //adds the book info in firestore
    //this function will create a subcollection under a user who uploaded the book
    //the subcollection will have a book id which is a unique id
    //when clicked on the book id will take the user to the bookinfo
    private void addDataToFirestore(String bookName, String bookAuthor, String bookIsbn, String bookPrice, String bookYear,String userID) {
        CollectionReference dbBookInfo = db.collection("users").document(userID).collection("Books");
        BookInfo bookInfo = new BookInfo(bookName,bookAuthor,bookIsbn,bookPrice,bookYear,userID);

        dbBookInfo.add(bookInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(Upload.this, "Your Book has been added!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Upload.this, "Fail to add book \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
