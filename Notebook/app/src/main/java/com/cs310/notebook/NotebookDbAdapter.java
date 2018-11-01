package com.cs310.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by erdembocugoz on 14/05/16.
 */
public class NotebookDbAdapter {
    private static final String DATABASE_NAME = "notebook.db";
    private static final int DATABASE_VERSION=3;

    public static final String NOTE_TABLE = "note";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_MESSAGE="message";
    public static final String COLUMN_LOCATION="location";
    public static final String COLUMN_IMAGE="image";

    //photo
    //location

    private String[] allCollumn ={COLUMN_ID,COLUMN_TITLE,COLUMN_MESSAGE,COLUMN_LOCATION,COLUMN_IMAGE};

    public static final String CREATE_TABLE_NOTE = "create table " + NOTE_TABLE +" ( "+COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE +" text not null, "
            +COLUMN_MESSAGE+" text not null, "
            +COLUMN_LOCATION+" text not null, "
            +COLUMN_IMAGE+" blob not null "
            +");";

    private SQLiteDatabase sqlDB;
    private Context context;

    private NotebookDbHelper notebookDbHelper;

    public NotebookDbAdapter(Context ctx){

        context=ctx;
    }
    public NotebookDbAdapter open() throws  android.database.SQLException{
        notebookDbHelper = new NotebookDbHelper(context);
        sqlDB = notebookDbHelper.getWritableDatabase();
        return this;
    }
    public void close (){
        notebookDbHelper.close();
    }
    public  Note createNote(String title,String message,String location,Bitmap image){
        ContentValues values = new ContentValues();
        byte[] bArray;
        if(image!=null) {
            Bitmap photo = image;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
             bArray = bos.toByteArray();
        }
        else {
            bArray = new byte[0];
        }
        values.put(COLUMN_TITLE,title);
        values.put(COLUMN_MESSAGE,message);
        values.put(COLUMN_LOCATION,location);
        values.put(COLUMN_IMAGE,bArray);


        long insertId = sqlDB.insert(NOTE_TABLE,null,values);

        Cursor cursor= sqlDB.query(NOTE_TABLE,allCollumn, COLUMN_ID+" = " + insertId,null,null,null,null);

        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }
    public long deleteNote(long idToDelete){
        return sqlDB.delete(NOTE_TABLE, COLUMN_ID + " = " + idToDelete, null);

    }


    public long updateNote(long idToUpdate,String newTitle,String newMessage,String newLocation,Bitmap newImage)
    {
        ContentValues values = new ContentValues();
        byte[] bArray;
        if(newImage!=null) {
            Bitmap photo = newImage;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bArray = bos.toByteArray();
        }
        else
        {
             bArray=new byte[0];
        }
        values.put(COLUMN_TITLE,newTitle);
        values.put(COLUMN_MESSAGE,newMessage);
        values.put(COLUMN_LOCATION, newLocation);
        values.put(COLUMN_IMAGE,bArray);
        return sqlDB.update(NOTE_TABLE, values, COLUMN_ID + " = " + idToUpdate, null);

    }
    public ArrayList<Note> getAllNotes(){
        ArrayList<Note> notes = new ArrayList<Note>();

        Cursor cursor = sqlDB.query(NOTE_TABLE,allCollumn,null,null,null,null,null);

        for(cursor.moveToLast(); !cursor.isBeforeFirst();cursor.moveToPrevious()){
            Note note = cursorToNote(cursor);
            notes.add(note);
        }
        cursor.close();

        return notes;
    }
    private Note cursorToNote(Cursor cursor){
        Note newNote = new Note(cursor.getString(1),cursor.getString(2),cursor.getLong(0),cursor.getString(3),cursor.getBlob(4));
        return  newNote;
    }

    private static class NotebookDbHelper extends SQLiteOpenHelper{

        NotebookDbHelper(Context ctx){

            super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_TABLE_NOTE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ NOTE_TABLE);
            onCreate(db);

        }
    }







}
