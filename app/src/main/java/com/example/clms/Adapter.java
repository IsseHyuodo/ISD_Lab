package com.example.clms;

import static com.example.clms.R.id.bookAuthor1;
import static com.example.clms.R.id.my_books;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.My_Book_View> {

    private List<MyBook> myBooks=new ArrayList<>();
    ArrayList<String> IDate = new ArrayList<>();
    ArrayList<String> acc = new ArrayList<>();
    ArrayList<String> DDate = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");

    public Adapter(List<MyBook> myBooks, ArrayList<String> IDate, ArrayList<String> DDate, ArrayList<String> acc) {

        this.myBooks=myBooks;
        this.DDate = DDate;
        this.IDate = IDate;
        this.acc = acc;
        Log.v("adapt", "In adapter");
    }
    @NonNull
    @Override
    public My_Book_View onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        return new My_Book_View(view);
    }

    @Override
    public void onBindViewHolder(@NonNull My_Book_View holder, @SuppressLint("RecyclerView") int position) {
        holder.bookAuthor1.setText(myBooks.get(position).getAuthor());
        holder.bookName1.setText(myBooks.get(position).getTitle());
        holder.bookIdate.setText("Issued on: \n" + IDate.get(position));
        holder.bookDdate.setText("Due on: \n" + DDate.get(position));

        Log.v("adapt", myBooks.get(position).getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Issued_Book_View.class);
                i.putExtra("ISBN", myBooks.get(position).getISBN());
                i.putExtra("Acc", acc.get(position));
                i.putExtra("IDate", IDate.get(position));
                i.putExtra("DDate", DDate.get(position));
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myBooks.size();
    }

    public class My_Book_View extends RecyclerView.ViewHolder {

        TextView bookName1,bookAuthor1,bookIdate,bookDdate;

        public My_Book_View(@NonNull View itemView) {
            super(itemView);

            bookAuthor1=(TextView) itemView.findViewById(R.id.bookAuthor1);
            bookIdate=itemView.findViewById(R.id.bookIdate);
            bookName1=itemView.findViewById(R.id.bookName1);
            bookDdate=itemView.findViewById(R.id.bookDdate);
        }
    }


}