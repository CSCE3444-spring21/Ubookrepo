//view book class
package com.example.profilehomepage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewBooks extends AppCompatActivity {

    //declaring the variables
    //declaring the recyclerview
    //creating the array for the books
    private RecyclerView bookRV;
    private ArrayList<BookInfo> BookList;
    private BookAdapter bookAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;


    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initializing the variables
        bookRV = findViewById(R.id.idRVCourses);
        loadingPB = findViewById(R.id.idProgressBar);


        //gettig suthorization from firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


        db = FirebaseFirestore.getInstance();


        BookList = new ArrayList<>();
        bookRV.setHasFixedSize(true);
        bookRV.setLayoutManager(new LinearLayoutManager(this));


        bookAdapter = new BookAdapter(BookList, this);


        //this function basically lists out the book info to the user
        //function runs by getting the book info from firebase
        //this will show the info of the book retrieved from firebase to the user in the app
        bookRV.setAdapter(bookAdapter);
        db.collection("users").document(userID).collection("Books").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                BookInfo c = d.toObject(BookInfo.class);
                                c.setId(d.getId());
                                BookList.add(c);
                            }
                            bookAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ViewBooks.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ViewBooks.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
