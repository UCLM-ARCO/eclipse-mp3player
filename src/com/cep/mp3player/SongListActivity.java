package com.cep.mp3player;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class SongListActivity extends ListActivity {
        
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        String[] song_names = getIntent().getExtras().getStringArray("com.cep.mp3player.available");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                                                android.R.layout.simple_list_item_1, song_names);
        setListAdapter(adapter);
    }
}
