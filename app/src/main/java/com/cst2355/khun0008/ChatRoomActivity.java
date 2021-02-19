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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    private Message message ;
    private ArrayList<Message> elements = new ArrayList<>();
    private MyListAdapter myAdapter;
    private EditText textValue;
    //private boolean sendOrReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        textValue = findViewById(R.id.messageTypeArea);


        Button sendButton = findViewById(R.id.messageSendButton);
        Button receiveButton = findViewById(R.id.messageReceiveButton);
        sendButton.setOnClickListener((clk) ->
        {
            message= new Message(textValue.getText().toString(),false);
            elements.add(message);
            myAdapter.notifyDataSetChanged();
            textValue.getText().clear();
        });
        receiveButton.setOnClickListener((clk) ->
        {
            message= new Message(textValue.getText().toString(),true);
            elements.add(message);
            myAdapter.notifyDataSetChanged();
            textValue.getText().clear();
        });


        ListView myList = findViewById(R.id.messages_list);
        myList.setAdapter(myAdapter = new MyListAdapter());

        myList.setOnItemLongClickListener((p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message:
                    .setMessage("The selected row is: " + pos + "\nThe database id is: " + id)

                    //what the Yes button does:
                    .setPositiveButton("Positive", (click, arg) -> {
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    //What the No button does:
                    .setNegativeButton("Negative", (click, arg) -> {
                    })

                    //An optional third button:
                    //.setNeutralButton("Maybe", (click, arg) -> {  })

                    //You can add extra layout elements:
                    //.setView(getLayoutInflater().inflate(R.layout.row_layout, null) )

                    //Show the dialog
                    .create().show();
            return true;
        });
    }


    private class MyListAdapter extends BaseAdapter {
        public int getCount() {
            return elements.size();
        }

        public Object getItem(int position) {
            return elements.get(position).getMessageDetail();
        }

        public long getItemId(int p) {
            return (long) p;
        }

        public View getView(int position, View old, ViewGroup parent) {
            //String message= elements.get(position).getMessageDetail();
            Boolean sor = elements.get(position).isSendOrReceive();

            //Message m = new Message(message,sor);

            LayoutInflater inflater = getLayoutInflater();
            View v;
            TextView t;

            if (sor == false) {
                v = inflater.inflate(R.layout.row_send, parent, false);
                t = v.findViewById(R.id.textViewSend);
            }
            else {
                v = inflater.inflate(R.layout.row_receive, parent, false);
                t = v.findViewById(R.id.textViewReceive);
            }
            t.setText(getItem(position).toString());
            return v;
        }
    }
}