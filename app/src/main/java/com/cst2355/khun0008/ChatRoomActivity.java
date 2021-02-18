package com.cst2355.khun0008;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {
    private Message message = new Message();
    private ArrayList<String> elements = new ArrayList<>(Arrays.asList());
    private MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Button sendButton = findViewById(R.id.messageSendButton);
        Button receiveButton = findViewById(R.id.messageReceiveButton);
        sendButton.setOnClickListener((clk)->
        {
            elements.add(message.getMessage());
            myAdapter.notifyDataSetChanged();
        });


        ListView myList = findViewById(R.id.messages_list);
        myList.setAdapter(myAdapter = new MyListAdapter());

        myList.setOnItemLongClickListener( (p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message:
                    .setMessage("The selected row is: " +pos +"/nThe database id is:" +id)

                    //what the Yes button does:
                    .setPositiveButton("Positive", (click, arg) -> {
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    //What the No button does:
                    .setNegativeButton("Negative", (click, arg) -> { })

                    //An optional third button:
                    //.setNeutralButton("Maybe", (click, arg) -> {  })

                    //You can add extra layout elements:
                    //.setView(getLayoutInflater().inflate(R.layout.row_layout, null) )

                    //Show the dialog
                    .create().show();
            return true;
        });
    }

    private class Message {
        EditText textValue = findViewById(R.id.messageTypeArea);
        public String getMessage(){
            return textValue.getText().toString();
        }
    }

    private class MyListAdapter extends BaseAdapter {
        public int getCount() {
            return elements.size();
        }

        public Object getItem(int position) {
            return message.getMessage();
        }

        public long getItemId(int p) {
            return (long)p;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newRow = inflater.inflate(R.layout.row_send, parent, false);
            TextView textViewOfReceive = newRow.findViewById(R.id.textViewSend);
            textViewOfReceive.setText((String)getItem(position));
            return newRow;
        }
    }
}