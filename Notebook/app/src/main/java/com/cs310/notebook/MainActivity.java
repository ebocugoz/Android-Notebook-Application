package com.cs310.notebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE_ID_EXTRA = "com.cs310.notebook.Identifier";
    public static final String NOTE_TITLE_EXTRA ="com.cs310.notebook.Title";
    public static final String NOTE_MESSAGE_EXTRA ="com.cs310.notebook.Message";
    public static final String NOTE_LOCATION_EXTRA="com.cs310.notebook.Location";
    public static final String NOTE_IMAGE_EXTRA="com.cs310.notebook.Image";

    public static final String NOTE_FRAGMENT_TO_LOAD_EXTRA ="com.cs310.notebook.Fragment_to_load";
     public enum FragmentToLaunch{VIEW,EDIT,CREATE};
final Activity me =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.string.takeNotes);
        setSupportActionBar(toolbar);
        Button newNote = (Button)findViewById(R.id.new_bttn);
        Button about = (Button)findViewById(R.id.about_bttn);
        Button home = (Button)findViewById(R.id.home_bttn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(me,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                Intent intent = new Intent(me, AboutActivity.class);
                int a = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

                String x = Context.WIFI_SERVICE ;
                startActivity(intent);
            }
        });

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Log.d("Save Note","Note title"+title.getText());
                /*
                Intent intent = new Intent(me, NoteDetailActivity.class);
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.CREATE);
                startActivity(intent);
                ;*/



            }
        });





    }




}
