package com.example.cdsm.tp_16contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

public class monContentProvider extends ContentProvider
{

    public static final String AUTHORITY = "com.example.cdsm.tp_16contentprovider";
    public static final String PATH = "Contact";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+PATH);
    private static final int TOUS_LES_CONTACTS = 1;
    private static final int CONTACT_PAR_ID = 2;
    private static UriMatcher URI_MATCHER;
    static
    {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY,PATH,TOUS_LES_CONTACTS);
        URI_MATCHER.addURI(AUTHORITY,PATH+"#",CONTACT_PAR_ID);
    }
    private MySQLiteHelper database = null;

    @Override
    public boolean onCreate() {
        database = new MySQLiteHelper(getContext());
        return ((database == null) ? false : true);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PATH);
        if(URI_MATCHER.match(uri) == CONTACT_PAR_ID)
        {
            queryBuilder.appendWhere("_id = " + uri.getPathSegments().get(1));
        }
        String orderBy;
        if(TextUtils.isEmpty(s1))
        {
            orderBy="_id";
        }
        else
        {
            orderBy = s1;
        }

        Cursor cursor = queryBuilder.query(database.getReadableDatabase(),strings,s,strings1,null,null,s1);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        getContext().getContentResolver().notifyChange(uri,null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String MYMIME = "vnd.com.example.cdsm.tp_16contentprovider/Contact";
        switch (URI_MATCHER.match(uri))
        {
            case TOUS_LES_CONTACTS:
                return "vnd.android.cursor.dir/"+MYMIME;


            case CONTACT_PAR_ID:
                return "vnd.andorid.cursor.item/"+MYMIME+"byID";
        }
        return "";

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (URI_MATCHER.match(uri) != TOUS_LES_CONTACTS)
        {
            throw new IllegalArgumentException("Erreur de URI: "+uri);
        }

        long id = database.getWritableDatabase().insert("Contact", null, contentValues);
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse(CONTENT_URI+"/"+id);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.e("SQL", "delete");
        int count = database.getWritableDatabase().delete(PATH,s,strings);

        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
