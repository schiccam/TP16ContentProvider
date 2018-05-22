package com.example.cdsm.tp_16contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.LoaderManager;
import android.content.Loader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    MySQLiteHelper helper;
    SQLiteDatabase bdd;
    ListView ListeContact;
    MyCustomAdapter adapter;

    LoaderManager loaderManager = getLoaderManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new MySQLiteHelper(this);
        bdd = helper.getReadableDatabase();

        ListeContact = findViewById(R.id.list);

        loaderManager.initLoader(1, null, this);
        adapter = new MyCustomAdapter(this,null,0);

    }

    public void SaveContact_click(View view)
    {
        AjoutContact();
    }

    public void AjoutContact() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phoneNo ="";
                String mail="";
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    Cursor mCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (mCur.moveToNext()){
                        mail = mCur.getString(mCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }

                    String[] names = name.split(" ");
                    String prenom = names[0];
                    String nom = names[1];

                    User Contact = new User(prenom,nom,phoneNo,mail);
                    InsertContact(Contact);

                    pCur.close();
                }
            }
        }

    }

    public void InsertContact(User Contact)
    {

        String I =
                "INSERT INTO Contact(CtPrenom,CtNom,CtTel,CtMail)"
                +"Values('" + Contact.getPrenom() + "','" + Contact.getNom() + "','" + Contact.getTel() + "','" + Contact.getMail()+"')";
        bdd.execSQL(I);

    }




    public void ShowContact_click(View view)
    {
        /*ArrayList<String> liste = new ArrayList<String>();

        Cursor cr = bdd.rawQuery("SELECT * FROM Contact", null);
        if (cr.moveToFirst()) {
            do {
                //int id = cr.getInt(cr.getColumnIndex("CtId"));
                String nom = cr.getString(cr.getColumnIndex("CtPrenom"));
                String prenom = cr.getString(cr.getColumnIndex("CtNom"));
                String tel = cr.getString(cr.getColumnIndex("CtTel"));
                String mail = cr.getString(cr.getColumnIndex("CtMail"));


                String raw = prenom + " " + nom + " " + tel +" " + mail;
                liste.add(raw);


            }
            while (cr.moveToNext());
            cr.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,liste);
            ListeContact.setAdapter(adapter);*/
        ListeContact.setAdapter(adapter);




    }

    public void DeleteContact_click(View view)
    {
        String Delete =
                "delete  from Contact";

        bdd.execSQL(Delete);
        ListeContact.setAdapter(null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //Loader = new CursorLoader(this,)
        return new MonCursorLoader(this,bdd);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.changeCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }
}

