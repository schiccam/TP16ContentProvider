package com.example.cdsm.tp_16contentprovider;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCustomAdapter extends CursorAdapter
{

    public MyCustomAdapter(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if(cursor.getPosition()%2==1)
        {
            view.setBackgroundColor(Color.CYAN);
        }
        else
        {
            view.setBackgroundColor(Color.GRAY);
        }

        TextView prenom = view.findViewById(R.id.tvPrenom);
        TextView nom = view.findViewById(R.id.tvNom);
        TextView tel = view.findViewById(R.id.tvTelephone);
        TextView mail = view.findViewById(R.id.tvMail);

        prenom.setText(cursor.getString(1));
        nom.setText(cursor.getString(2));
        tel.setText(cursor.getString(3));
        mail.setText(cursor.getString(4));

    }
}
