package com.example.clms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.Search_Book_View> {

    String Person, p;
    private List<MyBook> myBooks=new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");

    public Search_Adapter(List<MyBook> myBooks, String person, String p) {

        this.myBooks=myBooks;
        Person = person;
        this.p = p;
    }

    @NonNull
    @Override
    public Search_Adapter.Search_Book_View onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        return new Search_Adapter.Search_Book_View(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull Search_Adapter.Search_Book_View holder, int position) {
        holder.bookAuthor2.setText(myBooks.get(position).getAuthor());
        holder.bookName2.setText(myBooks.get(position).getTitle());
        int x = (int) myBooks.get(position).getCopies();
        if(x>0)
        holder.copies.setText(String.valueOf(x) + " copies available");
        else
            holder.copies.setText("Out of stock.");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Person.equals("User"))
                {
                    Intent i = new Intent(view.getContext(), User_Book_View.class);
                    i.putExtra("Title", myBooks.get(position).getTitle());
                    i.putExtra("Author", myBooks.get(position).getAuthor());
                    i.putExtra("Publisher", myBooks.get(position).getPublisher());
                    i.putExtra("ISBN", myBooks.get(position).getISBN());
                    i.putExtra("Pages", String.valueOf(myBooks.get(position).getPages()));
                    i.putExtra("Rack", myBooks.get(position).getRack());
                    i.putExtra("Subject", myBooks.get(position).getSubject());
                    i.putExtra("Copies", String.valueOf(myBooks.get(position).getCopies()));
                    i.putExtra("user",p);
                    view.getContext().startActivity(i);
                }
                else if(Person.equals("LT"))
                {
                    Intent i = new Intent(view.getContext(), LT_Book_View.class);
                    i.putExtra("Title", myBooks.get(position).getTitle());
                    i.putExtra("Author", myBooks.get(position).getAuthor());
                    i.putExtra("Publisher", myBooks.get(position).getPublisher());
                    i.putExtra("ISBN", myBooks.get(position).getISBN());
                    i.putExtra("Pages", String.valueOf(myBooks.get(position).getPages()));
                    i.putExtra("Rack", myBooks.get(position).getRack());
                    i.putExtra("Subject", myBooks.get(position).getSubject());
                    i.putExtra("Copies", String.valueOf(myBooks.get(position).getCopies()));
                    view.getContext().startActivity(i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return myBooks.size();
    }

    public class Search_Book_View extends RecyclerView.ViewHolder {
        TextView bookName2,bookAuthor2,copies;
        public Search_Book_View(@NonNull View itemView) {
            super(itemView);
            bookAuthor2=(TextView) itemView.findViewById(R.id.bookAuthor2);
            copies=itemView.findViewById(R.id.bookStatus);
            bookName2=itemView.findViewById(R.id.bookName2);
        }
    }
}
