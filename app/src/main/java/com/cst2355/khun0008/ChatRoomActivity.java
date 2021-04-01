package com.cst2355.khun0008;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    SQLiteDatabase db;
    DetailsFragments dFragment;
    long newId;
    //private boolean sendOrReceiv
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView theList = (ListView) findViewById(R.id.messages_list);
        boolean isTablet = findViewById(R.id.fragmentLocation) != null;

        textValue = findViewById(R.id.messageTypeArea);

        loadDataFromDatabase();

        Button sendButton = findViewById(R.id.messageSendButton);
        Button receiveButton = findViewById(R.id.messageReceiveButton);
        sendButton.setOnClickListener((clk) ->
        {
            String messageValue = textValue.getText().toString();
            int sor = 0;
            ContentValues newRowValues = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:
            //put string name in the NAME column:
            newRowValues.put(MyOpener.COL_MESSAGES, messageValue);
            //put string email in the EMAIL column:
            newRowValues.put(MyOpener.COL_SENDRECEIVE, sor);

            //Now insert in the database:
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            //now you have the newId, you can create the Contact object
            message = new Message(messageValue,sor, newId);
            elements.add(message);
            myAdapter.notifyDataSetChanged();
            textValue.getText().clear();
        });
        receiveButton.setOnClickListener((clk) ->
        {
            String messageValue = textValue.getText().toString();
            int sor = 1;
            ContentValues newRowValues = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:
            //put string name in the NAME column:
            newRowValues.put(MyOpener.COL_MESSAGES, messageValue);
            //put string email in the EMAIL column:
            newRowValues.put(MyOpener.COL_SENDRECEIVE, sor);

            //Now insert in the database:
            newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            //now you have the newId, you can create the Contact object
            message = new Message(messageValue,sor, newId);
            elements.add(message);
            myAdapter.notifyDataSetChanged();
            textValue.getText().clear();
        });


        ListView myList = findViewById(R.id.messages_list);
        myList.setAdapter(myAdapter = new MyListAdapter());


        myList.setOnItemClickListener((list, view, position, id) ->{
            Bundle dataToPass = new Bundle();
            dataToPass.putString("msg", elements.get(position).getMessage() );
            //dataToPass.putInt("msg_pos", position);
            dataToPass.putLong("id",elements.get(position).getId());
            dataToPass.putInt("checkSend",elements.get(position).getIsSendOrRecieve());
            if(isTablet)
            {
                dFragment = new DetailsFragments(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
            }
            else //isPhone
            {
                Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });


        myList.setOnItemLongClickListener((p, b, pos, id) -> {
            Message msg = elements.get(pos);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message:
                    .setMessage("The selected row is: " + pos + "\nThe database id is: " + id)

                    //what the Yes button does:
                    .setPositiveButton("Positive", (click, arg) -> {
                        deleteMessage(msg);
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                        if(isTablet){
                            FragmentManager fm = getSupportFragmentManager();
                            fm.beginTransaction().remove(dFragment).commit();
                        }
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

    private void loadDataFromDatabase()
    {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGES, MyOpener.COL_SENDRECEIVE};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(results, 1);
        //Now the results object has rows of results that match the query.
        //find the column indices:
        int messageColumnIndex = results.getColumnIndex(MyOpener.COL_MESSAGES);
        int sorColIndex = results.getColumnIndex(MyOpener.COL_SENDRECEIVE);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String newM = results.getString(messageColumnIndex);
            int newSor = results.getInt(sorColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            elements.add(new Message(newM, newSor, id));
        }

        //At this point, the contactsList array has loaded every row from the cursor.
    }



    protected void deleteMessage(Message m)
    {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(m.getId())});
    }

    protected void printCursor(Cursor c, int version) {
        Log.i("Database version: ", String.valueOf(db.getVersion()));
        Log.i("Number of Columns", String.valueOf(c.getColumnCount()));
        Log.i("Column names: ", String.valueOf(c.getColumnNames()));
        Log.i("Number of Rows", String.valueOf(c.getCount()));
    }


    private class MyListAdapter extends BaseAdapter {
        public int getCount() {
            return elements.size();
        }

        public Message getItem(int position) {
            return elements.get(position);
        }

        public long getItemId(int p) {
            return getItem(p).getId();
        }

        public View getView(int position, View old, ViewGroup parent) {
            //String message= elements.get(position).getMessageDetail();
            int sor = elements.get(position).getIsSendOrRecieve();

            //Message m = new Message(message,sor);

            LayoutInflater inflater = getLayoutInflater();
            View v = null;
            TextView t = null;

            if (sor == 0) {
                v = inflater.inflate(R.layout.row_send, parent, false);
                t = v.findViewById(R.id.textViewSend);
            }
            else if(sor == 1) {
                v = inflater.inflate(R.layout.row_receive, parent, false);
                t = v.findViewById(R.id.textViewReceive);
            }
            t.setText(getItem(position).getMessage());
            return v;
        }
    }
}