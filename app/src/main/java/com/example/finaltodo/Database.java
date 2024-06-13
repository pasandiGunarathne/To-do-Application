package com.example.finaltodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry1 = "create table users(username text,email text, password text)";
        sqLiteDatabase.execSQL(qry1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public void register(String username, String email, String password){
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("email",email);
        cv.put("password",password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users",null,cv);
        db.close();
    }
    public int login(String username,String password){
        int result=0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c =db.rawQuery("select * from users where username=? and password=?",str);
        if(c.moveToFirst()){
            result=1;
        }
        return result;
    }


    public String[] getUserDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username, email FROM users WHERE username=?", new String[]{username});
        if (cursor.moveToFirst()) {
            String user = cursor.getString(0);
            String email = cursor.getString(1);
            cursor.close();
            db.close();
            return new String[]{user, email};
        }
        cursor.close();
        db.close();
        return null;
    }

    public void updateUserDetails(String oldUsername, String newUsername, String newEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", newUsername);
        cv.put("email", newEmail);
        db.update("users", cv, "username = ?", new String[]{oldUsername});
        db.close();
    }



}
