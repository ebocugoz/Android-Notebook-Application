package com.cs310.notebook;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {


    private int PICK_IMAGE_REQUEST = 1;
    private boolean newNote = false;
    private long noteId = 0;
    public NoteEditFragment() {
        // Required empty public constructor
    }
    GPSTracker mGPS;
private EditText title,message;
    private TextView myLocation;
    private ImageView myImage;
    private GetLocation mGetLocation = new GetLocation();
    private String mLocation="LOCATION:";
    private Bitmap image;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//grab the bundle thaat sens along wheter or note our noteedit fragment is creating a nea note
        Bundle bundle = this.getArguments();
         mGPS = new GPSTracker(getActivity());
        if(bundle!=null)
        {
            newNote=bundle.getBoolean(NoteDetailActivity.NEW_NOTE_EXTRA,false);

        }

        View fragmentLayout = inflater.inflate(R.layout.fragment_note_edit, container, false);
         title = (EditText) fragmentLayout.findViewById(R.id.editNoteTitle);

         message = (EditText) fragmentLayout.findViewById(R.id.editNoteMessage);
        myLocation= (TextView)fragmentLayout.findViewById(R.id.location);
        myImage = (ImageView)fragmentLayout.findViewById(R.id.editPhoto);


        //image bttn
        Intent intent = getActivity().getIntent();


        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA,""));
        message.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA, ""));
        myLocation.setText(intent.getExtras().getString(MainActivity.NOTE_LOCATION_EXTRA, mLocation));

        byte[] byteArray = intent.getByteArrayExtra(MainActivity.NOTE_IMAGE_EXTRA);
        if(byteArray!=null) {
            image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            myImage.setImageBitmap(image);
        }

        noteId = intent.getExtras().getLong(MainActivity.NOTE_ID_EXTRA,0);

        Button saveButton  = (Button) fragmentLayout.findViewById(R.id.saveNote);
        Button deleteButton = (Button)fragmentLayout.findViewById(R.id.delete_bttn);
        Button locationButton = (Button)fragmentLayout.findViewById(R.id.location_button);
        Button imageButton = (Button)fragmentLayout.findViewById(R.id.add_photo);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){

            if(mGPS.canGetLocation ){
                mGPS.getLocation();
                mLocation="Location: " + mGPS.getLatitude() + "/" + mGPS.getLongitude();
                myLocation.setText("Location: " + mGPS.getLatitude() + "/" + mGPS.getLongitude());
            }else{
                myLocation.setText("Unabletofind");
                System.out.println("Unable");
            }
        }

        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Log.d("Save Note","Note title"+title.getText());
                NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                if (newNote) {
                    Log.d("Location ",mLocation);
                    dbAdapter.createNote(title.getText() + "", message.getText() + "", mLocation,image);
                } else {
                    Log.d("Location ",mLocation);
                    dbAdapter.updateNote(noteId, title.getText() + "", message.getText() + "",mLocation,image);

                }
                dbAdapter.close();
                Log.d("title",title.getText().toString()+"");


                if(title.getText().toString().equals("")) {


                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Title Alert");
                    alertDialog.setMessage("You have to fill title");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity(intent);
                }


            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Log.d("Save Note","Note title"+title.getText());
                NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteNote(noteId);
                dbAdapter.close();

                Intent intent =new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });



        return fragmentLayout;    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                image=bitmap;
                myImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

  /*  @Override
    public void onSavedInstanceState(Bundle savedInstaceState)
    {
        super.onSaveInstanceState(savedInstaceState);
        savedInstaceState.putSerializable();

    }*/


}
