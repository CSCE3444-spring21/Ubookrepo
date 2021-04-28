//book adapter for the view book class
package com.example.profilehomepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;

import com.example.profilehomepage.ViewBooks;

import java.io.Serializable;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    //creating the array of the book info
    private ArrayList<BookInfo> BookList;
    private Context context;

    public BookAdapter(ArrayList<BookInfo>BookList,Context context) {
        this.BookList = BookList;
        this.context = context;
    }

    //inflating the layout of the book info
    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.booklist, parent, false));
    }

    //holder for all the book info
    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        BookInfo books = BookList.get(position);
        holder.booknameTV.setText(books.getBookname());
        holder.authorTV.setText(books.getAuthor());
        holder.isbnTV.setText(books.getIsbn());
        holder.priceTV.setText(books.getPrice());
        holder.yearTV.setText(books.getYear());
    }

    @Override
    public int getItemCount() {
        return BookList.size();
    }

    //recycler view to store information about books
    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView booknameTV;
        private final TextView authorTV;
        private final TextView isbnTV;
        private final TextView priceTV;
        private final TextView yearTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            booknameTV = itemView.findViewById(R.id.idTVbookname);
            authorTV = itemView.findViewById(R.id.idTVauthor);
            isbnTV = itemView.findViewById(R.id.idTVisbn);
            priceTV = itemView.findViewById(R.id.idTVprice);
            yearTV = itemView.findViewById(R.id.idTVyear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookInfo bookInfo = BookList.get(getAdapterPosition());
                    Intent i = new Intent(context, UpdateBook.class);
                    i.putExtra("book", bookInfo);
                    context.startActivity(i);
                }
            });
        }
    }
}


