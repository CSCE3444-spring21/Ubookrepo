//update book class
package com.example.profilehomepage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestore;


public class UpdateBook extends AppCompatActivity {

    //declaring the variables
    private EditText bookNameEdt, bookAuthorEdt, bookISBNEdt, bookPriceEdt, bookYearEdt;
    private String bookname, author, isbn, price, year;

    private FirebaseFirestore db;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //creating the instance of the Bookinfo class and initializing it
        BookInfo bookInfo = (BookInfo) getIntent().getSerializableExtra("book");

        //initializing the variables
        db = FirebaseFirestore.getInstance();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        bookNameEdt = findViewById(R.id.idEdtBookName);
        bookAuthorEdt = findViewById(R.id.idEdtBookAuthor);
        bookISBNEdt = findViewById(R.id.idEdtBookISBN);
        bookPriceEdt = findViewById(R.id.idEdtBookPrice);
        bookYearEdt = findViewById(R.id.idEdtBookYear);

        Button updateBookButton = findViewById(R.id.idBtnUpdateBook);
        Button deleteBookButton = findViewById(R.id.idBtnDeleteBook);

        bookNameEdt.setText(bookInfo.getBookname());
        bookAuthorEdt.setText(bookInfo.getAuthor());
        bookISBNEdt.setText(bookInfo.getIsbn());
        bookPriceEdt.setText(bookInfo.getPrice());
        bookYearEdt.setText(bookInfo.getYear());

        //onclick listener for the delete button
        deleteBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook(bookInfo);
            }
        });

        //onclick listener for the update book button
        updateBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookname = bookNameEdt.getText().toString();
                isbn = bookISBNEdt.getText().toString();
                author = bookAuthorEdt.getText().toString();
                price = bookPriceEdt.getText().toString();
                year = bookYearEdt.getText().toString();

                //if else function to check if the user entered the info or not
                if (TextUtils.isEmpty(bookname)) {
                    bookNameEdt.setError("Please enter Book Name");
                } else if (TextUtils.isEmpty(author)) {
                    bookAuthorEdt.setError("Please enter Author Name");
                } else if (TextUtils.isEmpty(isbn)) {
                    bookISBNEdt.setError("Please enter Course Duration");
                } else if (TextUtils.isEmpty(price)) {
                    bookPriceEdt.setError("Please enter Price of book in dollars");
                }else if (TextUtils.isEmpty(year)) {
                    bookYearEdt.setError("Please enter Book Year");
                } else {
                    updateBooks(bookInfo, bookname, author, isbn, price, year, userID);
                }
            }
        });
    }

    //delete book function
    //when pressed deletes the book and takes the user to the view book page
    private void deleteBook(BookInfo bookInfo) {
        db.collection("users").document(userID).collection("Books").
                document(bookInfo.getId()).
                delete().
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(UpdateBook.this, "Book has been deleted from Database.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateBook.this, ViewBooks.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(UpdateBook.this, "Failed to delete the book. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //update book function
    //ask user for the book info and updates it when the user click update button
    //after updating, this will take the user to the view book page
    private void updateBooks(BookInfo bookInfo, String bookName, String bookAuthor, String bookIsbn, String bookPrice, String bookYear, String userID) {
        BookInfo updatedBook = new BookInfo(bookName, bookAuthor, bookIsbn, bookPrice, bookYear, userID);
        db.collection("users").document(userID).collection("Books").
                document(bookInfo.getId()).set(updatedBook).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateBook.this, "Book has been updated..", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UpdateBook.this, ViewBooks.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateBook.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
                            }
                });
    }
}