package com.example.androidtest.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import com.example.androidtest.model.UserModel;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UserDB extends SQLiteOpenHelper {
    // tao bang database
    // dinh nghia bang database bang code java
    public static final String DB_NAME = "user_db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "users";
    //dinh nghia cac cot trong bang database
    public static final String ID_COL = "id"; // khoa chinh (primary key)
    public static final String NAME_COL = "name";
    public static final String PASSWORD_COL = "password";
    public static final String EMAIL_COL = "email";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String DELETED_AT = "deleted_at";


    public UserDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tao bang du lieu - create table
        String query = "CREATE TABLE " + TABLE_NAME + " ( "
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " VARCHAR(60) NOT NULL, "
                + PASSWORD_COL + " VARCHAR(200) NOT NULL, "
                + EMAIL_COL + " VARCHAR(60) NOT NULL, "
                + CREATED_AT + " DATETIME, "
                + UPDATED_AT + " DATETIME, "
                + DELETED_AT + " DATETIME) ";
        db.execSQL(query); // tao bang
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // xoa bang neu co loi
        onCreate(db); // tao lai bang
    }

    // viet lenh insert du lieu cua tai khoan nguoi dung
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long addNewUser(
            String name,
            String password,
            String email
    ) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        ZonedDateTime now = ZonedDateTime.now();
        String dateNow = dtf.format(now);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(PASSWORD_COL, password);
        values.put(EMAIL_COL, email);
        values.put(CREATED_AT, dateNow);
        long add = db.insert(TABLE_NAME, null, values);
        db.close();
        return add;
    }

    @SuppressLint("Range")
    public UserModel getInfoUser(String username, String password) {
        UserModel user = new UserModel();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            // tao mang chua cac cot du lieu can lay thong tin
            String[] columns = {ID_COL, NAME_COL, EMAIL_COL};
            // Select ID_COL, USERNAME_COL, EMAIL_COL, PHONE_COL, ADDRESS_COL from users
            // where USERNAME_COL = "?" and PASSWORD_COL = "?"
            String condition = EMAIL_COL + " =? " + " AND " + PASSWORD_COL + " =? ";
            String[] params = {username, password};
            Cursor cursor = db.query(TABLE_NAME, columns, condition, params, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst(); // lay ra 1 row data dau tien
                // do du lieu vao model
                user.setId(cursor.getInt(cursor.getColumnIndex(ID_COL)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(NAME_COL)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_COL)));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public boolean checkUsernameEmail(String data, int type) {
        boolean checking = false;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {ID_COL, NAME_COL, EMAIL_COL};
            String condition = (type == 1) ? (NAME_COL + " =? ") : (EMAIL_COL + " =? ");
            String[] params = {data};
            Cursor cursor = db.query(TABLE_NAME, columns, condition, params, null, null, null);
            if (cursor.getCount() > 0) {
                checking = true;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return checking;
    }
}
