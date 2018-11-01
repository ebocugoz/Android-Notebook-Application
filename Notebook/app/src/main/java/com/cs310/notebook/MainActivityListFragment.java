package com.cs310.notebook;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {


    public MainActivityListFragment() {
        // Required empty public constructor
    }
private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
    super.onActivityCreated(savedInstanceState);
/*
    String[] values = new String[]{"Android","Iphone","Windowsmobile","Android","Iphone","Windowsmobile","Android","Iphone","Windowsmobile","Android","Iphone","Windowsmobile","Android","Iphone","Windowsmobile","Android","Iphone","Windowsmobile"};
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,values);

    setListAdapter(adapter);
    */
     NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        notes=dbAdapter.getAllNotes();
        dbAdapter.close();


        noteAdapter = new NoteAdapter(getActivity(),notes);
        setListAdapter(noteAdapter);

        registerForContextMenu(getListView());

}
    @Override
    public void onListItemClick(ListView l, View v , int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        launchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT, position);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuinfo)
    {
        super.onCreateContextMenu(menu, v, menuinfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
    }


    private void launchNoteDetailActivity(MainActivity.FragmentToLaunch ftl, int position){
        Note note = (Note) getListAdapter().getItem(position);

        Intent intent = new Intent(getActivity(),NoteDetailActivity.class);

        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA,note.getTitle());
        intent.putExtra(MainActivity.NOTE_MESSAGE_EXTRA,note.getMessage());
        intent.putExtra(MainActivity.NOTE_LOCATION_EXTRA,note.getLocation());
        intent.putExtra(MainActivity.NOTE_ID_EXTRA, note.getNoteId());


        intent.putExtra(MainActivity.NOTE_IMAGE_EXTRA,note.getImage());

        switch (ftl){
            case VIEW:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.EDIT);
        }


        startActivity(intent);


    }

}
