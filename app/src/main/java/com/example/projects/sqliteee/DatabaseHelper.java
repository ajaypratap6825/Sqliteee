package com.example.projects.sqliteee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = " Data.db ";
    private static final String TABLE_NAME = " data_table";
    public static final String COL_1 = " ID ";
    public static final String COL_2 = " TITLE ";
    public static final String COL_3 = " DESCRIPTION ";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table "+ TABLE_NAME +"(" + COL_1
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT NOT NULL, " + COL_3 + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long addNote(Data data){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COL_2,data.getTitle());
        c.put(COL_3,data.getDescription());
        long ID = database.insert(TABLE_NAME,null,c);
        return ID;
    }
    public Data getData(long id){
        SQLiteDatabase database = this.getWritableDatabase();
        String[] query = new String[] {COL_1,COL_2,COL_3};
        Cursor cursor=  database.query(TABLE_NAME,query,COL_1+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        return new Data(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));
    }
    public List<Data> getAllNotes(){
        List<Data> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+COL_1+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Data note = new Data();
                note.setId(Long.parseLong(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setDescription(cursor.getString(2));
                allNotes.add(note);
            }while (cursor.moveToNext());
        }
        return allNotes;
    }
    public int editNote(Data note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ note.getTitle() + "\n ID -> "+note.getId());
        c.put(COL_2,note.getTitle());
        c.put(COL_3,note.getDescription());
        return db.update(TABLE_NAME,c,COL_1+"=?",new String[]{String.valueOf(note.getId())});
    }
    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COL_1+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
}
