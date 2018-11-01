package com.cs310.notebook;

import android.graphics.Bitmap;

/**
 * Created by erdembocugoz on 13/05/16.
 */
public class Note {
    public void setTitle(String title) {
        this.title = title;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String title,message,location;
    private long noteId;

    public byte[] getImage() {
        return byteArray;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    private Bitmap image;
    private byte[] byteArray;

    public String getTitle() {
        return title;
    }
    public String getLocation() {
        return location;
    }

    public String getMessage() {
        return message;
    }

    public long getNoteId() {
        return noteId;
    }
    public int getAssociatedDrawable()
    {
        return R.drawable.block_orange;
    }

    public Note(String title,String message,String location)
{
    this.title=title;
    this.message=message;
    this.location=location;
    this.noteId=0;
}
    public Note(String title,String message,long noteId,String location,byte[] byteArray)
    {
        this.title=title;
        this.message=message;
        this.location=location;
        this.noteId=noteId;
        this.byteArray=byteArray;
    }
public String toString(){
    return "ID: "+noteId+ " Title: "+title+" Message: "+message;
}


}
