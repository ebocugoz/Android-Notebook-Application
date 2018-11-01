package com.cs310.notebook;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

public class NoteDetailActivity extends AppCompatActivity {
public static final  String NEW_NOTE_EXTRA ="New Note";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ImageView view = (ImageView)findViewById(R.id.viewNotePhoto);

        createAndFragment();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    private void createAndFragment()
    {
        Intent intent = getIntent();
        MainActivity.FragmentToLaunch fragmentToLaunch =
                (MainActivity.FragmentToLaunch) intent.getSerializableExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragmentToLaunch)
        {
            case EDIT:
                NoteEditFragment noteEditFragment = new NoteEditFragment();
                setTitle(R.string.editFragmentTitle);
                fragmentTransaction.add(R.id.note_container,noteEditFragment,"NOTE_EDIT_FRAGMENT");
                break;

            case CREATE:
                NoteEditFragment noteCreateFragment = new NoteEditFragment();
                setTitle(R.string.createFragmentTitle);
                Bundle bundle = new Bundle();
                bundle.putBoolean(NEW_NOTE_EXTRA, true);
                noteCreateFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.note_container,noteCreateFragment,"NOTE_CREATE_FRAGMENT");
                break;
        }




        fragmentTransaction.commit();
    }


}
