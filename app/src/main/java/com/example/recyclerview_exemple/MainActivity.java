package com.example.recyclerview_exemple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Contact> contacts;
    ContactsAdapter adapter;
    String TAG="rv";
    ConstraintLayout coordinatorLayout;
    RecyclerView rvContacts;
    Button btn_addContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the recyclerview in activity layout
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        btn_addContact=(Button) findViewById(R.id.btn_addContact);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        // Initialize contacts
        contacts = Contact.createContactsList(20);
        // Create adapter passing in the sample user data
        adapter = new ContactsAdapter(contacts,this);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

        btn_addContact.setOnClickListener(this);

        enableSwipeToDeleteAndUndo();
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Contact item = adapter.getData().get(position);

                adapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.restoreItem(item, position);
                        rvContacts.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvContacts);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        // Lấy id từng button
        switch(v.getId()){
            case R.id.btn_addContact:
                // Hiện thị thông báo trong vòng vài giây
                //Toast.makeText(getApplication(), "Hiệp Sĩ IT: Bạn đang Click vào Button Login", Toast.LENGTH_LONG).show();
//// Add a new contact
//                contacts.add(0, new Contact("Barney", true));
//// Notify the adapter that an item was inserted at position 0
//                adapter.notifyItemInserted(0);

                //remove
                contacts.remove(0);
                adapter.notifyItemMoved(0,0);
                adapter.notifyDataSetChanged();
                break;

        }
    }
}