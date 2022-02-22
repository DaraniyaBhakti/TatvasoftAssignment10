package com.tatvasoft.tatvasoftassignment10.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tatvasoft.tatvasoftassignment10.Model.UserModel;

import java.util.ArrayList;

public class UserDatabase extends SQLiteOpenHelper {


    Context context;
    SQLiteDatabase sqLiteDatabase;
    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_NAME = "UserTable";

    private static final String USER_ID = "UserId";
    private static final String USER_NAME = "UserName";
    private static final String CONTACT_NUMBER = "ContactNumber";
    private static final String BIRTH_DATE = "BirthDate";
    private static final String BLOOD_GROUP = "BloodGroup";
    private static final String COUNTRY = "Country";
    private static final String GENDER = "Gender";
    private static final String LANGUAGES = "Languages";


    public UserDatabase(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + DATABASE_TABLE_NAME + "(" +
                USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_NAME + " VARCHAR, " +
                CONTACT_NUMBER +" VARCHAR, " +
                BIRTH_DATE + " VARCHAR, " +
                BLOOD_GROUP + " VARCHAR, " +
                COUNTRY + " VARCHAR," +
                GENDER + " VARCHAR," +
                LANGUAGES + " VARCHAR" +
                " )";
        db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String userName,
                           String contactNumber,
                           String birthDate,
                           String bloodGroup,
                           String country,
                           String gender,
                           String languages)
    {
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME,userName);
        contentValues.put(CONTACT_NUMBER,contactNumber);
        contentValues.put(BIRTH_DATE,birthDate);
        contentValues.put(BLOOD_GROUP,bloodGroup);
        contentValues.put(COUNTRY,country);
        contentValues.put(GENDER,gender);
        contentValues.put(LANGUAGES,languages);

        sqLiteDatabase.insert(DATABASE_TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<UserModel> getDataById(int userId){

        sqLiteDatabase = getReadableDatabase();
        ArrayList<UserModel> arrayList =new ArrayList<>();
        String query =" SELECT * FROM "+ DATABASE_TABLE_NAME +" WHERE "+ USER_ID +"= '"+userId+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do{
                arrayList.add(new UserModel(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return arrayList;
    }

    public ArrayList<UserModel> getAllData()
    {
        sqLiteDatabase = getReadableDatabase();
        ArrayList<UserModel> arrayList =new ArrayList<>();

        String selectById = "SELECT * FROM "+ DATABASE_TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(selectById,null);

        if(cursor.moveToFirst())
        {
            do{
                arrayList.add(new UserModel(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return arrayList;

    }

    public void updateDataById(int userId,
                               String userName,
                               String contactNumber,
                               String birthDate,
                               String bloodGroup,
                               String country,
                               String gender,
                               String languages){

        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME,userName);
        contentValues.put(CONTACT_NUMBER,contactNumber);
        contentValues.put(BIRTH_DATE,birthDate);
        contentValues.put(BLOOD_GROUP,bloodGroup);
        contentValues.put(COUNTRY,country);
        contentValues.put(GENDER,gender);
        contentValues.put(LANGUAGES,languages);

        sqLiteDatabase.update(DATABASE_TABLE_NAME,contentValues,USER_ID + "= ? ",new String[]{String.valueOf(userId)});
        sqLiteDatabase.close();

    }

    public void deleteDataById(int userId){
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DATABASE_TABLE_NAME,USER_ID + "= ?",new String[]{String.valueOf(userId)});
        sqLiteDatabase.close();
    }

}
