package com.example.clms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Add_Book extends AppCompatActivity {

    AutoCompleteTextView textIn;
    AlertDialog.Builder builder;
    Button buttonAdd, Add;
    LinearLayout container;
    Spinner spinner;
    ArrayAdapter<CharSequence> arr_adapter;

    FirebaseDatabase rootNode;
    DatabaseReference ref1,ref2;

    private static final String[] NUMBER = new String[] {
            "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten"
    };
    ArrayAdapter<String> adapter;

    ArrayList<String> acc;
    EditText Title, Author, Publisher, ISBN, Pages, Rack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        rootNode = FirebaseDatabase.getInstance();
        ref1 = rootNode.getReference("books");

        Title = (EditText) findViewById(R.id.editTitle1);
        Author = (EditText) findViewById(R.id.editAuthor1);
        Publisher = (EditText) findViewById(R.id.editpublish1);
        ISBN = (EditText) findViewById(R.id.editISBN1);
        Pages = (EditText) findViewById(R.id.editpages1);
        Rack = (EditText) findViewById(R.id.editRack1);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, NUMBER);
        builder = new AlertDialog.Builder(this);

        textIn = (AutoCompleteTextView)findViewById(R.id.textin);
        textIn.setAdapter(adapter);

        buttonAdd = (Button)findViewById(R.id.add);
        Add = (Button)findViewById(R.id.add1);
        container = (LinearLayout) findViewById(R.id.container);

        spinner = (Spinner) findViewById(R.id.spinner2);

        arr_adapter = ArrayAdapter.createFromResource(this,
                R.array.list1, android.R.layout.simple_spinner_item);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter);


        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                AutoCompleteTextView textOut = (AutoCompleteTextView)addView.findViewById(R.id.textout);
                textOut.setAdapter(adapter);
                String s = textIn.getText().toString();
                textIn.setText("");
                if(!TextUtils.isEmpty(s))
                {
                    textOut.setText(s);
                    Button buttonRemove = (Button)addView.findViewById(R.id.row_remove);

                    final View.OnClickListener thisListener = new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout)addView.getParent()).removeView(addView);

                            listAllAddView();
                        }
                    };

                    buttonRemove.setOnClickListener(thisListener);
                    container.addView(addView);
                    listAllAddView();

                }


            }
        });


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                ref1 = rootNode.getReference("books");


                //Toast.makeText(Add_Book.this, "Book added.", Toast.LENGTH_SHORT).show();
                builder.setMessage("Do you want to add this book ?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ref2 = ref1.child(ISBN.getText().toString());
                                ref2.child("Title").setValue(Title.getText().toString());
                                ref2.child("Author").setValue(Author.getText().toString());
                                ref2.child("Publisher").setValue(Publisher.getText().toString());
                                ref2.child("ISBN").setValue(ISBN.getText().toString());
                                ref2.child("Pages").setValue(Pages.getText().toString());
                                ref2.child("Rack").setValue(Rack.getText().toString());
                                ref2.child("Subject").setValue(spinner.getSelectedItem().toString());
                                ref2.child("Acc").setValue(getAcc());
                                Toast.makeText(getApplicationContext(),"Book added.",
                                        Toast.LENGTH_SHORT).show();finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                Toast.makeText(getApplicationContext(),"Book addition failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Add Book");
                alertDialog.show();
                /*builder.setMessage("Do you want to add this book ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(getApplicationContext(),"Book added.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Book addition failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Add Book");
                alert.show();*/
            }
        });
    }

    private ArrayList<String> getAcc()
    {
        int childCount = container.getChildCount();
        ArrayList<String> arr = new ArrayList<>();
        for(int i=0; i<childCount; i++){
            View thisChild = container.getChildAt(i);
            AutoCompleteTextView childTextView = (AutoCompleteTextView) thisChild.findViewById(R.id.textout);
            String childTextViewValue = childTextView.getText().toString();
            //Toast.makeText(this, childTextViewValue, Toast.LENGTH_SHORT).show();

            arr.add(childTextViewValue);
        }

        return arr;
    }


    private void listAllAddView(){

        int childCount = container.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = container.getChildAt(i);
            AutoCompleteTextView childTextView = (AutoCompleteTextView) thisChild.findViewById(R.id.textout);
            String childTextViewValue = childTextView.getText().toString();
            //Toast.makeText(this, childTextViewValue, Toast.LENGTH_SHORT).show();

        }
        Toast.makeText(this, String.valueOf(childCount), Toast.LENGTH_SHORT).show();
        if(childCount<=0 && spinner.getSelectedItemPosition() == arr_adapter.getPosition("Select Book Category") && TextUtils.isEmpty(Title.getText()) && TextUtils.isEmpty(Author.getText()) && TextUtils.isEmpty(Publisher.getText()) && TextUtils.isEmpty(ISBN.getText()) && TextUtils.isEmpty(Pages.getText()) && TextUtils.isEmpty(Rack.getText())){
            Add.setEnabled(false);
        }
        else
        {
            Add.setEnabled(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.lt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(Add_Book.this, MainActivity.class);
        if(item.getItemId()==R.id.Log_Out)
        {
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}