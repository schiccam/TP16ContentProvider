package com.example.cdsm.tp_16contentprovider;

public class User
{
    String prenom;
    String nom;
    String tel;
    String mail;

    public User(String p, String n, String t, String m)
    {
        prenom = p;
        nom = n;
        tel = t;
        mail = m;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public String getNom()
    {
        return nom;
    }

    public String getTel()
    {
        return tel;
    }

    public String getMail()
    {
        return mail;
    }

}
