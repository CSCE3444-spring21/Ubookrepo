//search page to implement the search function
package com.example.profilehomepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SearchPage extends AppCompatActivity {

    //creating instance of recyclerview, searchadapter
    RecyclerView RecView;
    searchadapter adapter;
    //declaring the button
    Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initializing the variables
        RecView = (RecyclerView) findViewById(R.id.RecView);
        RecView.setLayoutManager(new LinearLayoutManager(this));

        //getting the information of the books from firebase
        //creating the recyclerview
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Books"), model.class)
                        .build();

        adapter = new searchadapter(options);
        RecView.setAdapter(adapter);
    }

    //main function to implement the search function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });
        return true;
    }


    //function to show the book info if a user type a string
    private void processsearch(String s) {
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Books").orderByChild("bookname").startAt(s).endAt(s+"\uf8ff"), model.class)
                        .build();

        adapter = new searchadapter(options);
        adapter.startListening();
        RecView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

