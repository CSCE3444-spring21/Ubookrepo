//search adapter to implement the search function

package com.example.profilehomepage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class searchadapter extends FirebaseRecyclerAdapter<model,searchadapter.myviewholder>
{
    //creating the recycler view for the book info
    public searchadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    //holder for all setting the required information to the book information variables
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, final int position, @NonNull model m)
    {
        holder.bookname.setText(m.getBookname());
        holder.author.setText(m.getAuthor());
        holder.isbn.setText(m.getIsbn());
        holder.price.setText(m.getPrice());
        holder.year.setText(m.getYear());

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),chatwithseller.class);
                i.putExtra("sellerId",m.getSellerId());
                v.getContext().startActivity(i);
            }
        });
    }

    //inflating the layout of the books
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }


    //Recycler view for storing the books
    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView chat;
        TextView bookname, author, isbn, price, year;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            bookname=(TextView)itemView.findViewById(R.id.namebook);
            author=(TextView)itemView.findViewById(R.id.authorbook);
            isbn=(TextView)itemView.findViewById(R.id.isbnbook);
            price=(TextView)itemView.findViewById(R.id.pricebook);
            year=(TextView)itemView.findViewById(R.id.yearbook);
            chat=(ImageView) itemView.findViewById(R.id.chatbutton);
        }
    }
}

