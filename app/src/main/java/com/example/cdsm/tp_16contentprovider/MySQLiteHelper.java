package com.example.cdsm.tp_16contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper
{

    private static final String DB_NAME = "test.db";
    private static final int DB_VERSION = 3;

    private String query = "CREATE TABLE IF NOT EXISTS Contact("
            +" _id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" CtPrenom TEXT,"
            +" CtNom TEXT,"
            +" CtTel TEXT,"
            +" CtMail Text);";

    public MySQLiteHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bdd)
    {
        bdd.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bdd, int oldVersion, int newVersion)
    {
        if(oldVersion < newVersion)
        {
            bdd.execSQL("DROP TABLE Contact;");
            bdd.execSQL(query);
        }
    }
}
