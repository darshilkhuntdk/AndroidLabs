package com.cst2355.khun0008;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {

        protected final static String DATABASE_NAME = "ContactsDB";
        protected final static int VERSION_NUM = 1;
        public final static String TABLE_NAME = "MESSAGEDB";
        public final static String COL_MESSAGES = "MESSAGES";
        public final static String COL_SENDRECEIVE = "SENDORRECEIVE";
        public final static String COL_ID = "_id";

        public MyOpener(Context ctx)
        {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }


        //This function gets called if no database file exists.
        //Look on your device in the /data/data/package-name/database directory.
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_MESSAGES + " text,"
                    + COL_SENDRECEIVE  + " integer);");  // add or remove columns
        }


        //this function gets called if the database version on your device is lower than VERSION_NUM
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {   //Drop the old table:
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

            //Create the new table:
            onCreate(db);
        }

        //this function gets called if the database version on your device is higher than VERSION_NUM
        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {   //Drop the old table:
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

            //Create the new table:
            onCreate(db);
        }


}
