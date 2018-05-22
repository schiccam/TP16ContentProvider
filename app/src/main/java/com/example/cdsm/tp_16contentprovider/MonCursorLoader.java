package com.example.cdsm.tp_16contentprovider;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MonCursorLoader extends CursorLoader {

    private SQLiteDatabase bdd;

    public MonCursorLoader(Context context, SQLiteDatabase bdd)
    {
        super(context);
        this.bdd = bdd;
    }

    @Override
    protected Cursor onLoadInBackground() {
        return bdd.rawQuery("SELECT * FROM Contact", null);
    }
}
