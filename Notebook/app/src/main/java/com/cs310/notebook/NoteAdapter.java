package com.cs310.notebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by erdembocugoz on 14/05/16.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    public static class ViewHolder{
    TextView title;
        TextView note;
        ImageView noteIcon;



    }
    public NoteAdapter(Context context,ArrayList<Note> notes)
    {
        super(context,0,notes);
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent)
    {
        Note note=getItem(position);
        ViewHolder viewHolder;


        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row,parent,false);

            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.note = (TextView)convertView.findViewById(R.id.body);
            viewHolder.noteIcon =(ImageView)convertView.findViewById(R.id.notephoto);

        convertView.setTag(viewHolder);


        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

        }


        viewHolder.title.setText(note.getTitle());
        viewHolder.note.setText(note.getMessage());


        if(note.getImage()!=null) {
            Bitmap image = BitmapFactory.decodeByteArray(note.getImage(), 0, note.getImage().length);
            viewHolder.noteIcon.setImageBitmap(image);
        }
        return convertView;
    }
}
