package com.tistory.ppiazi.howmuchdidyouspend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ppiazi on 2015-01-18.
 */
public class CardSmsSqlHelper extends SQLiteOpenHelper
{
    final static protected String dbName = "cardsms";
    final static protected String dbFileName = "cardsms.db";
    final static protected int dbVersion = 2;

    public CardSmsSqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table " + dbName + " (" +
                "_id long primary key, " +
                "msgId long, " +
                "threadId long, " +
                "address text, " +
                "person text, " +
                "contactId long, " +
                "contactStr text, " +
                "timeStamp long, " +
                "vendorId text, " +
                "cardCost long, " +
                "placeInUse text, " +
                "body text );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String sql = "drop table if exists cardsms";
        db.execSQL(sql);

        onCreate(db);
    }
}
